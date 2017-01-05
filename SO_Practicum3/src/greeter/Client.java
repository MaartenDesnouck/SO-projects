package greeter;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {
		InetAddress host = null;
		int serverPort = 1024;
		Socket socket = null;
                BufferedWriter writer = null;
                BufferedReader reader = null;
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String name = "";
		try {
			host = InetAddress.getLocalHost();
			socket = new Socket(host,serverPort);
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  
			do {
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(System.in));
				name = consoleInput.readLine();

				if (!(name.equals("stop"))) {
					// send the name
					writer.write(name+"\n");
                                        writer.flush();
                                        System.out.println("Send!");

					// receive reply
					String line = "";
					line = reader.readLine();
                                        System.out.println("Received!");

					// print the greeting
					System.out.println("Reply from server = "
							+ line);
				}
			} while (!(name.equals("stop")));
                        writer.write("stop\n");
                        writer.flush();
                        String line = "";
                        while(!line.equals("stopped")){
                            line = reader.readLine();
                        }
		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (SocketException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
                  if(reader!=null) reader.close();
                  if(writer!=null) writer.close();
                  if(socket!=null) socket.close();
		}
	}
}

