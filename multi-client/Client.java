import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client {	
	public void print(String msg){
		System.out.print(msg);
	}
	public void println(String msg){
		System.out.println(msg);
	}
	public static void main (String []args){
		try{
			//~ if(args.length == 0){
				//~ System.out.println("Usage: java client <port>");
				//~ return;
			//~ }
			//~ int port = Integer.parseInt(args[0]);
			Scanner s = new Scanner(System.in);
			String buffer = new String();
			//~ System.out.println("connectiong to localhost port "+port);
			java.net.Socket client = new java.net.Socket("localhost",50000);
			
			DataOutputStream out = new DataOutputStream(client.getOutputStream());			
			DataInputStream in = new DataInputStream(client.getInputStream());
			
			while(in.available() == 0);//waiting for server response regarding adding to container
			boolean status = in.readBoolean();
			
			
			System.out.println("server response is "+status);
			if(!status){
				System.out.println("Server full, closing socket, buh bye");
				client.close();
				return;
			}
			else{
				receive r = new receive(in, client);
				r.start();			
				
				while (true){
					System.out.print("Say something: ");
					buffer = s.nextLine();
					if(buffer.equals("quit")){
						System.out.println("closing socket");
						client.close();							
						break;
					}
					if(!client.isConnected()){
						System.out.println("oh no! server connection closed! PANIC!!");
						client.close();	
						break;
					}
					else{	
						out.writeUTF(buffer);
					}
				}
				if(r.isAlive())
					System.out.println("receive thread closed successfully");
				else
					System.out.println("receive thread still running, why?");				
			}		
		}
		catch(IOException e){
			e.printStackTrace();
			return;
		}
	}
}

class receive extends Thread{ //maybe unneccesary threading?
	private volatile boolean isRunning = false;
	private DataInputStream in;
	private java.net.Socket client;
	
	public receive(DataInputStream input, java.net.Socket c){
		in = input;
		client = c;
	}
	
	public void run(){
		System.out.print("\nclient starting receive thread\n");			
		try{
			while(client.isConnected()){
				if(!client.isConnected()){
					System.out.println("server closed!!!!!");
					break;
				}
				while(in.available() == 0); //waiting for client to write something 
				String a = new String(in.readUTF());
				System.out.print("\r"+a+"\nSay something: ");
			}
		}
		catch(IOException e){
			e.printStackTrace();
			return;
		}
	};
}
