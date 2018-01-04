import java.net.*;
import java.io.*;
import java.util.*;

class CThread extends Thread{ //for every client connected, this thread is created for sending/receiving
	private java.net.Socket clients;
	private DataInputStream in;
	private DataOutputStream out;
	private ServerSocket server;
	private int threadId;
	private ArrayList<CThread> room;
	
	public void print(String msg){
		System.out.print(msg);
	}
	
	public void println(String msg){
		System.out.println(msg);
	}
	
	public void send(String from,String msg, CThread ct){//send to specific client
		try{
			ct.out.writeUTF("["+from+"] says: "+msg);
		}
		catch(IOException e){
			
		}
	}
	
	public CThread(java.net.Socket c, ServerSocket s, int id, ArrayList<CThread> r){
		clients = c;
		server = s;
		threadId = id;
		room = r;
		try{
			in = new DataInputStream(c.getInputStream());
			out = new DataOutputStream(c.getOutputStream());
		}
		catch(IOException e){
			
		}
	}
	
	public int id(){
		return threadId;
	}
	
	public void broadcast(String msg){
		println("broadcasting to "+(room.size()-1)+" other users");
		for(int i=0;i<room.size();i++){
			if(room.get(i) != this)
				send(this.clients.getInetAddress().getHostAddress(),msg,room.get(i));
		}
	}
	
	public void run(){
		send("Server","you can start sending messages \n",this);
		while(true){
			try{
				if(!clients.isConnected() || server.isClosed()){
					room.remove(this);
					this.clients.close();
					break;
				}

				while(in.available() == 0);
				String s = in.readUTF();
				print("\nClient send "+s+"\n");
				broadcast(s);
			}
			catch(IOException e){
				
			}
		}			
		return;
	};
}
