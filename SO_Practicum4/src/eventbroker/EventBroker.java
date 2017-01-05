package eventbroker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

final public class EventBroker implements Runnable {

    protected Set<EventListener> listeners = new HashSet<>();
    protected final static EventBroker broker = new EventBroker();
    protected Map<String,Set<EventListener>> listenermap= new HashMap<>();
    protected volatile LinkedList<QueueItem> queue = new LinkedList<>();
    protected volatile boolean stopped;
    protected volatile boolean running;
    private Thread t;
    private final Object lock = new Object();

    private EventBroker() {
        stopped=false;
        running=false;
    }

    public static EventBroker getEventBroker() {
        return broker;
    }

    public void addEventListener(EventListener s) {
        listeners.add(s);
    }

    public void removeEventListener(EventListener s) {
        listeners.remove(s);
    }
    
    public void addEventListener(String type, EventListener s) {
        if (!listenermap.containsKey(type)){
            listenermap.put(type, new HashSet<EventListener>());
        }
        listenermap.get(type).add(s);
    }
    
    public void removeEventListener(String type, EventListener s){
        if(listenermap.containsKey(type)){
            if(listenermap.get(type).contains(s)){
                listenermap.get(type).remove(s);
            }
            if(listenermap.get(type).isEmpty()){
                listenermap.remove(type);
            }
        }
    }

    synchronized void addEvent(EventPublisher source, Event e) {
        if(!stopped){
            synchronized(lock){
                queue.add(new QueueItem(e,source));
                System.out.println("added");
                lock.notifyAll();
            }
        }
    }

    private void process(EventPublisher source, Event e) {
        for (EventListener l : listeners) {
            if (l != source) {
                l.handleEvent(e); // prevent loops !
            }
        }
        if(listenermap.containsKey(e.getType())){
            for (EventListener l : listenermap.get(e.getType())) {
                if (l != source) {
                    l.handleEvent(e); // prevent loops !
                }
            }
        }
    }
    
    @Override
    public void run(){
        synchronized(lock){
            while(running){
                if(queue.isEmpty()){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {}
                }
                QueueItem item = queue.poll();
                if(item != null){
                    process(item.source,item.event);
                    System.out.println("processed!");
                }
            }
        }   
    }
    
    public void start(){
        if(!running){
            running=true;
            t = new Thread(this);
            t.start();
        }
    }
    
    public void stop(){
        stopped=true;
        while(!queue.isEmpty()){
            
        }
        System.out.println("stop!");
        running=false;
        t.interrupt();
        stopped=false;
        
    }
    
    private class QueueItem {
        
        public Event event;
        public EventPublisher source;
        
        public QueueItem(Event event, EventPublisher source){
            this.event=event;
            this.source=source;
        }
        
    }
}
