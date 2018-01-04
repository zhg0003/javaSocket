import java.net.*;
import java.io.*;
import java.util.*;

public class Socket{
	
	public static void print(String msg){
		System.out.print(msg);
	}
	
	public static void println(String msg){
		System.out.println(msg);
	}
		
	public static void main (String []args){
		int id = 0;
		ArrayList<CThread> c = new ArrayList<CThread>();		
		
		println("listening on port 50000");
		try(
			ServerSocket server = new ServerSocket(50000); //create a server socket 
		){
			while(true){
				java.net.Socket client = server.accept();
				
				DataInputStream in = new DataInputStream(client.getInputStream()); //server socket's input is connected to client socket input
				DataOutputStream out = new DataOutputStream(client.getOutputStream()); 
				CThread ct = new CThread(client,server,id++, c);
				
				System.out.println("a client has connected, printing client info");
				System.out.println("InetAddress: "+client.getInetAddress());
				System.out.println("local address: "+client.getLocalAddress());
				System.out.println("remote address: "+client.getRemoteSocketAddress());
				System.out.println("waiting for client to enter a name...");
				System.out.println("adding client to thread list");	
				
				boolean status = c.add(ct);											
				if(status == true){
					out.writeBoolean(status);//send server add client status to client
					
					print("Current connected clients are (printing thread id)\n");
					for(int i=0;i<c.size();i++){
						println(Integer.toString(c.get(i).id()));
					}	
									
					System.out.println("client added successfully, starting client thread");
					c.get(c.size()-1).start();
				}
				else{
					System.out.println("Server full, closing client socket");
					out.writeBoolean(status);
					client.close();
				}
				print("\n");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

