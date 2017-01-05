package greeter;
import java.net.*;
import java.io.*;
import java.nio.CharBuffer;

public class Server {
	 public static void main(String[] args) throws IOException {
	      ServerSocket serverSocket=null;
              Socket socket = null;
              BufferedWriter writer = null;
              BufferedReader reader = null;
	      int serverPort=1024;
              boolean running = true;
	      
	      try {
	    	  serverSocket=new ServerSocket(serverPort);
                  socket = serverSocket.accept();               
                  writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                  reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	  while(running) {
                          String line = "";
                          line = reader.readLine();
                          if(line.equals("stop")){
                            writer.write("stopped\n");
                            writer.flush();
                            running=false;
                          }else{
                            System.out.println("Read!");
                            writer.write("Hello, "+ line+"\n");
                            writer.flush();
                            System.out.println("Written!");
                          }
	    	  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  if(serverSocket!=null) serverSocket.close();
                  if(reader!=null) reader.close();
                  if(writer!=null) writer.close();
                  if(socket!=null) socket.close();
	      }
	   }

}
