
package main;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.BlacklistOrderProcessor;
import order.Customer;
import order.OrderProcessor;


public class Main {
    
    public static int noOrders = 2;
    
    private static class AnonymousRunnable implements Runnable {
        
        private Customer c;
        private int num;
        
        public AnonymousRunnable(Customer c, int i) {
            this.c=c;
            this.num=i;
        }
        
        @Override
        public void run (){
            c.buy("item-"+num);
            try {
                Thread.sleep(2000);

            } catch (InterruptedException ex) {
                Logger.getLogger(AnonymousRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args){
       
        
        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        Set<Thread> threads = new HashSet<>();
        
        
        OrderProcessor orderProcessor = OrderProcessor.getOrderProcessor();
        eventbroker.EventBroker.getEventBroker().start();
        
        for(String name : names){
            Customer customer = new Customer(name);
            for(int i=0;i<noOrders;i++){
                Thread t = new Thread(new AnonymousRunnable(customer,i));
                t.start();
                threads.add(t);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        BlacklistOrderProcessor b = BlacklistOrderProcessor.getInstance();
        
        for(Thread t : threads){
            try{
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        
        eventbroker.EventBroker.getEventBroker().stop();
        orderProcessor.Stop();
        
        System.out.println("Orders verwerkt:"+OrderProcessor.getNumberOfOrders());
    }
    
}
