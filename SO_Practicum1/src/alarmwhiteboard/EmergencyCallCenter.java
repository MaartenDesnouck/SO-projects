/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmwhiteboard;

import java.util.Set;


/**
 *
 * @author maartendesnouck
 */
public class EmergencyCallCenter {
    
    private String emergencyNumber;
    private String lastHospital;
    private boolean hospitalSent;
    
    public EmergencyCallCenter(String number){
        this.emergencyNumber = number;
        lastHospital = "";
        hospitalSent=false;
    }
    
    public void incomingCall(String alarm, String location){
        System.out.println("Incoming call on number "+emergencyNumber);
        alarm.Alarm al = new alarm.Alarm(alarm,location);
        Set<alarm.AlarmListener> listeners = Whiteboard.getWhiteboard().getAlarmListeners(alarm);
        hospitalSent=false;
        
        for(alarm.AlarmListener l : listeners){
            if(l instanceof alarm.Hospital){
                if ((!((alarm.Hospital)l).getName().equals(lastHospital))&&!hospitalSent){
                    l.alarm(al);
                    lastHospital = ((alarm.Hospital)l).getName();
                    hospitalSent=true;
                }
            }else{
                l.alarm(al);
            }
        }
    }
}
