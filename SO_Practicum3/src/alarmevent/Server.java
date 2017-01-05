
package alarmevent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import network.Network;


public class Server {

    public static void main(String[] args) throws IOException{
        
        int port = 1234;
        
        eventbroker.EventBroker.getEventBroker().start();
        
        Hospital hospital1 = new Hospital("AZ");
        Hospital hospital2 = new Hospital("UZ");
        PoliceDepartment police = new PoliceDepartment();
        FireDepartment fire = new FireDepartment();
        
        Network network = new Network(port);
    }
}
