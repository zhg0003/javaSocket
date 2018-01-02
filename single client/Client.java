import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client {	
	public static void main (String []args){
		try{
			Scanner s = new Scanner(System.in);
			String buffer = new String();
			System.out.println("connectiong to localhost port 50000");
			java.net.Socket client = new java.net.Socket("localhost",50000);
			
			DataOutputStream out = new DataOutputStream(client.getOutputStream());			
			DataInputStream in = new DataInputStream(client.getInputStream());
			receive r = new receive(in, client);
			r.start();
			
			out.writeUTF("hello from socket "+client.getLocalAddress()); //write UTF for sending bytes as string format
			
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
			System.out.println("closing socket");
			client.close();			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

class receive extends Thread{ //maybe unneccesary threading?
	private DataInputStream in;
	private java.net.Socket client;
	public receive(DataInputStream input, java.net.Socket c){
		in = input;
		client = c;
	}
	
	public void run(){
		System.out.print("\nclient starting receive thread\n");			
		try{
			while(true){
				if(!client.isConnected()){
					System.out.println("server closed!!!!!");
					break;
				}
				while(in.available() == 0); //waiting for client to write something 
				String a = new String(in.readUTF());
				System.out.print("\rserver says: "+a+"\nSay something: ");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("exiting recieve thread, buh bye");
		return;
	};
}
