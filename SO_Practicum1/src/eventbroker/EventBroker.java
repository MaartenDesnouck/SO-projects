package eventbroker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final public class EventBroker {

    protected Set<EventListener> listeners = new HashSet<>();
    protected final static EventBroker broker = new EventBroker();
    protected Map<String,Set<EventListener>> listenermap= new HashMap<>();

    private EventBroker() {
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

    void addEvent(EventPublisher source, Event e) {
        process(source, e);
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
}
