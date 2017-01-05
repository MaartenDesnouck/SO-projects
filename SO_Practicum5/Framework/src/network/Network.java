package network;

import java.net.*;
import java.io.*;
import eventbroker.*;

public class Network extends EventPublisher implements EventListener{
    
    private Socket socket;
    private ConnectionListener listener;
    private Connection connection;
    private boolean closed;
    private boolean stopped;
    
    public Network(){
        closed = false;
        EventBroker.getEventBroker().addEventListener(this);
    }
    
    public Network(int serverPort){
        closed = false;
        listener = new ConnectionListener(this,serverPort);
        listener.start();
        EventBroker.getEventBroker().addEventListener(this);
    }
    
    public Connection connect(InetAddress address, int port){
        try{
            socket = new Socket(address, port);
        }catch(IOException e){
            e.printStackTrace();
        }
        return connect(socket);
    }
    
    public Connection connect(Socket socket){
        this.socket = socket;
        connection = new Connection(socket, this);
        connection.receive();
        return connection;
    }
    
    @Override
    public void handleEvent(Event e){
        connection.send(e);
    }
    
    public void terminate(){
        try{
            closed = true;
            if(connection != null) connection.close();
            if(socket != null) socket.close();
            if(listener != null) listener.terminate();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public boolean isClosed(){
        return closed;
    }
}
