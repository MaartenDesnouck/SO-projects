package alarmwhiteboard;


public class Main {
    
    public static void main(String[] args){
        
        alarm.Hospital hospital1 = new alarm.Hospital("UZ");
        alarm.Hospital hospital2 = new alarm.Hospital("AZ");
        alarm.PoliceDepartment police = new alarm.PoliceDepartment();
        alarm.FireDepartment fire = new alarm.FireDepartment();
        
        EmergencyCallCenter callCenter1 = new EmergencyCallCenter("112");
        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");
        Whiteboard.getWhiteboard().addAlarmListener("fire",hospital1);
        Whiteboard.getWhiteboard().addAlarmListener("crash",hospital1);
        Whiteboard.getWhiteboard().addAlarmListener(police);
        Whiteboard.getWhiteboard().addAlarmListener("fire",fire);
        Whiteboard.getWhiteboard().addAlarmListener("fire",hospital2);
        Whiteboard.getWhiteboard().addAlarmListener("crash",hospital2);
        
        
        callCenter1.incomingCall("crash", "Plateaustraat");
        callCenter1.incomingCall("assault", "Veldstraat");
        callCenter1.incomingCall("fire", "Zwijnaardse Steenweg");
        callCenter2.incomingCall("murder", "Plateaustraat");
        callCenter2.incomingCall("crash", "Sint-Pietersnieuwstraat");
    }
}
