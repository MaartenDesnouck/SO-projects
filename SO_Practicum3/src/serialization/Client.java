package serialization;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        InetAddress host = null;
        int serverPort = 1024;
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        
        try {
                host = InetAddress.getLocalHost();
                socket = new Socket(host,serverPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(socket.getInputStream());
                
                Person p = new Person("Jan","Janssens");
                outputStream.writeObject(p);
                outputStream.flush();
                p=(Person)inputStream.readObject();
                System.out.println(p);
        } catch (UnknownHostException e) {
                System.err.println(e);
        } catch (SocketException e) {
                System.err.println(e);
        } catch (IOException e) {
                System.err.println(e);
        } finally {
          if(outputStream!=null) outputStream.close();
          if(inputStream!=null) inputStream.close();
          if(socket!=null) socket.close();
        }
    }
}
