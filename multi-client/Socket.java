/* Socket .java is the server file,
 * Chat room will be different ClientHolder objects,
 * Server will keep track of chat room, user will be put into a global chat room when connected
 * CInfo will be clients, and it will reside in ClientHolder
 * 
 * 
 * 
 * */
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
		
		if(args.length == 0){
			System.out.println("Usage: java Socket <size>");
			return;
		}
		ClientHolder clients = new ClientHolder(Integer.parseInt(args[0]),"Global");
		//~ c = new List<Thread>();
		
		println("listening on port 50000");
		try(
			ServerSocket server = new ServerSocket(50000); //create a server socket 
		){
			while(true){
				java.net.Socket client = server.accept();
				
				DataInputStream in = new DataInputStream(client.getInputStream()); //server socket's input is connected to client socket input
				DataOutputStream out = new DataOutputStream(client.getOutputStream()); 
				CThread ct = new CThread(client,server,id++);
				System.out.println("a client has connected, printing client info");
				System.out.println("InetAddress: "+client.getInetAddress());
				System.out.println("local address: "+client.getLocalAddress());
				System.out.println("remote address: "+client.getRemoteSocketAddress());
				System.out.println("waiting for client to enter a name...");
				System.out.println("adding client to thread list");				
				//~ int status = clients.addClient(client, name);
				c.add(ct);
				print("Current connected clients are (printing thread id)\n");
				for(int i=0;i<c.size();i++){
					println(Integer.toString(c.get(i).id()));
				}
				//~ if(status == 0){
					//~ out.writeBoolean(true);
					//~ System.out.println("client added successfully");
				//~ }
				//~ else{
					//~ System.out.println("Server full, closing client socket");
					//~ out.writeBoolean(false);
					//~ client.close();
				//~ }
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

