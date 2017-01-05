package network;

import java.net.*;
import java.io.*;
import eventbroker.*;

public class Connection {
    
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Network network;
    
    public Connection(Socket socket, Network network){
        this.socket = socket;
        this.network = network;
        
        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void send(Event e){
        try{
            output.writeObject(e);
            output.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void receive(){
        Thread t = new ReceiverThread();
        t.start();
    }
    
    public void close(){
        try{
            if(input != null) input.close();
            if(output != null) output.close();
            if(socket != null) socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private class ReceiverThread extends Thread{
        
        @Override
        public void run(){
            try{
                while(!network.isClosed()){
                        Event e = (Event)input.readObject();
                        network.publishEvent(e);
                }
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        
    }
    
}
