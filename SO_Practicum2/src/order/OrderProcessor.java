
package order;


import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OrderProcessor implements EventListener {

    protected static int processedOrders = 0;
    private static final ExecutorService threadpool=Executors.newFixedThreadPool(8);
    
    //factory method: call constructor and return new created object
    public static OrderProcessor getOrderProcessor(){
        OrderProcessor n = new OrderProcessor();
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, n);
        System.out.println("OrderProcessor toegevoegd.");
        return n;
    }
    
    public void Stop(){
        this.threadpool.shutdown();
        System.out.println("Threadpool is gestopt.");
    }
    
    public OrderProcessor(){
    }
    
    @Override
    public void handleEvent(Event e) {  
        OrderEvent order = (OrderEvent) e;
        processOrder(order);
    }

    
    protected void processOrder(OrderEvent order){
        try {
            processedOrders++;
            Runnable task = new work();
            System.out.println("Order van "+order.getItem()+" voor "+order.getCustomer()+ " toegevoegd.");
            threadpool.execute(task);
        } catch (Exception e) {
        }
    }

    public static int getNumberOfOrders(){
        return processedOrders;
    }
   
    // do some dummy work for @milliseconds ms
    private void doWork(int milliseconds){
        long t1 = System.currentTimeMillis();
        int counter = 0;
        while(System.currentTimeMillis()-t1 < milliseconds){
            counter++;
        }
    }
    
    public static OrderProcessor getInstance(){
        OrderProcessor od = new OrderProcessor();
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, od);
        return od;
    }
    
    class work implements Runnable{
        @Override
        public void run() {
            doWork(1000);
        }
    }
}
