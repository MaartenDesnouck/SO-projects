package alarmevent;

public class Hospital implements eventbroker.EventListener {

    private String name;
    
    public Hospital(String name){
        eventbroker.EventBroker.getEventBroker().addEventListener("fire",this);
        eventbroker.EventBroker.getEventBroker().addEventListener("crash",this);
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public void handleEvent(eventbroker.Event event) {
        System.out.println(name+" sends an ambulance for "+event.getType());
    }
    
}
