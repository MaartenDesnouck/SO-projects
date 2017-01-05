/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmevent;

import java.util.Random;

/**
 *
 * @author maartendesnouck
 */
public class FireDepartment implements eventbroker.EventListener {

    Random random = new Random();

    public FireDepartment(){
        eventbroker.EventBroker.getEventBroker().addEventListener("fire",this);
    }
    
    @Override
    public void handleEvent(eventbroker.Event event) {
        System.out.println("Fire Department "+random.nextInt(10)+" is checking out the "+event.getType());
    }
}
