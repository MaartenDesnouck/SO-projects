package alarm;

public class Main {
    
    public static void main(String[] args){
        
        Hospital hospital1 = new Hospital("UZ");
        Hospital hospital2 = new Hospital("AZ");
        PoliceDepartment police = new PoliceDepartment();
        FireDepartment fire = new FireDepartment();
        
        EmergencyCallCenter callCenter1 = new EmergencyCallCenter("112");
        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");
        callCenter1.addListener(hospital1);
        callCenter1.addListener(police);
        callCenter1.addListener(fire);
        callCenter1.addListener(hospital2);
        callCenter2.addListener(police);
        
        
        callCenter1.incomingCall("crash", "Plateaustraat");
        callCenter1.incomingCall("assault", "Veldstraat");
        callCenter1.incomingCall("fire", "Zwijnaardse Steenweg");
        callCenter2.incomingCall("murder", "Plateaustraat");
    }
}
