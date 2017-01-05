package alarmevent;

import java.util.Random;

public class PoliceDepartment implements eventbroker.EventListener {

    Random random = new Random();
    
    public PoliceDepartment(){
        eventbroker.EventBroker.getEventBroker().addEventListener(this);
    }
    
    @Override
    public void handleEvent(eventbroker.Event event) {
        System.out.println("Police unit "+random.nextInt(10)+" is checking out the "+event.getType());
    }
    
}
