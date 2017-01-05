package alarmevent;

import java.io.*;
import java.net.InetAddress;
import network.Network;

public class Client {

    public static void main(String[] args) throws IOException {
        
        int port = 1234;
        InetAddress address = InetAddress.getLocalHost();
        
        Network network = new Network();
        network.connect(address, port);
        
        network.handleEvent(new AlarmEvent("crash","Plateaustraat"));
        network.handleEvent(new AlarmEvent("assualt","Veldstraat"));
        network.handleEvent(new AlarmEvent("fire","Zwijnaardse Steenweg"));
        network.handleEvent(new AlarmEvent("assault","Overpoortstraat"));
        
        network.terminate();
        
    }
}
