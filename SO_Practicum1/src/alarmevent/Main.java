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
public class Main {
    
    public static void main(String[] args){
    
        Hospital hospital1 = new Hospital("UZ");
        Hospital hospital2 = new Hospital("AZ");
        PoliceDepartment police = new PoliceDepartment();
        FireDepartment fire = new FireDepartment();

        EmergencyCallCenter callCenter1 = new EmergencyCallCenter("112");
        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");

        callCenter1.incomingCall(new AlarmEvent("crash", "Plateaustraat"));
        callCenter1.incomingCall(new AlarmEvent("assault", "Veldstraat"));
        callCenter1.incomingCall(new AlarmEvent("fire", "Zwijnaardse Steenweg"));
        callCenter2.incomingCall(new AlarmEvent("murder", "Plateaustraat"));
    }
}
