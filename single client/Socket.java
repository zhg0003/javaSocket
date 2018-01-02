import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Socket{
	public static void main (String []args){
		System.out.println("listening on port 50000");
		try(
			ServerSocket server = new ServerSocket(50000); //create a server socket 
			java.net.Socket client = server.accept();
		){
			System.out.println("a client has connected, printing client info");
			System.out.println("InetAddress: "+client.getInetAddress());
			System.out.println("local address: "+client.getLocalAddress());
			System.out.println("remote address: "+client.getRemoteSocketAddress());
			
			DataInputStream in = new DataInputStream(client.getInputStream()); //server socket's input is connected to client socket input
			DataOutputStream out = new DataOutputStream(client.getOutputStream()); 
			msgClient toClient = new msgClient(out, server, client);
			toClient.start();
			
			while(true){
				if(client.isConnected()){
					while(in.available() == 0); //waiting for client to write something 
					String a = new String(in.readUTF());
					System.out.print("\rClient says: "+a+"\nsay something: ");
				}
				else
					break;
			}
			System.out.println("closing server socket");
			if(toClient.isAlive()){
				System.out.println("input thread closed successfully");
			}
			server.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
class msgClient extends Thread{ //maybe unneccesary threading?
	private DataOutputStream out;
	private ServerSocket  server;
	private java.net.Socket client;
	
	public msgClient(DataOutputStream output, ServerSocket s, java.net.Socket c){
		out = output;
		server = s;
		client = c;
	}
	public void run(){
		System.out.print("\nserver starting input thread\n");
		Scanner s = new Scanner(System.in);
		String buffer = new String();
		try{
			while (true){
				System.out.print("Say something: ");
				buffer = s.nextLine();
				
				if(buffer.equals("quit")){
					System.out.println("closing socket");
					client.close();
					break;
				}
				
				if(!client.isConnected()){
					System.out.println("client socket is closed");
					break;
				}
				else{	
					out.writeUTF(buffer);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("exiting input thread, buh bye");
		return;
	};
}
//~ while(true){
	//~ accept connection
	//~ create thread deal with connection
//~ }

