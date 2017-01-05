package network;

import java.net.*;
import java.io.*;

// listens for incoming connections
public class ConnectionListener extends Thread{
    
    private ServerSocket serverSocket;
    private Network network;
    
    public ConnectionListener(Network network, int serverPort){
        this.network = network;
        try{
            serverSocket = new ServerSocket(serverPort);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
            Socket socket = serverSocket.accept();
            network.connect(socket);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void terminate(){
        try{
            if(serverSocket != null) serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
