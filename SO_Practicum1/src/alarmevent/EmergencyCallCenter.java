/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmevent;

/**
 *
 * @author maartendesnouck
 */
public class EmergencyCallCenter extends eventbroker.EventPublisher {
    
    private String emergencyNumber;
    
    public EmergencyCallCenter(String number){
        this.emergencyNumber = number;
    }
    
    public void incomingCall(eventbroker.Event event){
        System.out.println("Incoming call on number "+emergencyNumber);
        System.out.println(event.getMessage());
        publishEvent(event);
    }
}
