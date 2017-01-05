package parallelgreeter;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) throws IOException {
	      ServerSocket serverSocket=null;
	      int serverPort=1024;
              boolean running = true;
	      
              
                class ServerThread implements Runnable{
                    Socket socket = null;
                    BufferedWriter writer = null;
                    BufferedReader reader = null;
                    boolean running = true;
                  
                    public ServerThread(Socket socket){
                        this.socket=socket;
                    }

                    @Override
                    public void run(){
                        try {              
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
                            if(reader!=null) try {
                                reader.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(writer!=null) try {
                                writer.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(socket!=null) try {
                                socket.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
              
              
	      try {
	    	  serverSocket=new ServerSocket(serverPort);
                  while(true){
                    Socket socket = serverSocket.accept();
                    Thread t = new Thread(new ServerThread(socket));
                    t.start();
                  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  if(serverSocket!=null) serverSocket.close();
	      }
	   }

}

