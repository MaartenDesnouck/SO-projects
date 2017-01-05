package serialization;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    private static ArrayList<Person> db = new ArrayList<Person>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        fillInPhoneNumberDataBase();
        
        ServerSocket serverSocket=null;
              Socket socket = null;
	      int serverPort=1024;
              ObjectOutputStream outputStream = null;
              ObjectInputStream inputStream = null;
	      
	      try {
	    	  serverSocket=new ServerSocket(serverPort);
	    	  while(true) {
                    socket = serverSocket.accept();
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.flush();
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    
                    Person p = (Person)inputStream.readObject();
                    p = lookUpPhoneNumber(p);
                    outputStream.writeObject(p);
                    outputStream.flush();
                    
                    outputStream.close();
                    inputStream.close();
                    socket.close();
	    	  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
              } finally{
                  if(outputStream!=null) outputStream.close();
                  if(inputStream!=null) inputStream.close();
                  if(serverSocket!=null) serverSocket.close();
                  if(socket!=null) socket.close();
	      }
        
    }

    private static void fillInPhoneNumberDataBase() {
        Person[] p = {new Person("Jan", "Janssens", new PhoneNumber("32", "9", "44 55 66")),
            new Person("Piet", "Pieters", new PhoneNumber("32", "50", "11 22 33")),
            new Person("Giovanni", "Totti", new PhoneNumber("49", "22", "00 99 88")),
            new Person("Jean", "Lefevre", new PhoneNumber("33", "4", "12 34 56"))};
        for (Person i : p) {
            db.add(i);
        }
    }

    private static Person lookUpPhoneNumber(Person p) {
        int index = db.indexOf(p);
        if (index >= 0) {
            return db.get(index);
        } else {
            p.setPhoneNumber(new PhoneNumber());
            return p;
        }
    }
}
