/*
 * CThread.java
 * 
 * Copyright 2018 g <g@g-VirtualBox>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;


class CThread extends Thread{ //for every client connected, this thread is created for sending/receiving
	private java.net.Socket clients;
	private DataInputStream in;
	private DataOutputStream out;
	private ServerSocket server;
	private int threadId;
	
	public void print(String msg){
		System.out.print(msg);
	}
	public void println(String msg){
		System.out.println(msg);
	}
		
	public CThread(java.net.Socket c, ServerSocket s, int id){
		clients = c;
		server = s;
		threadId = id;
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
	
	public void run(){
		System.out.print("\nStarting receiving infomation \n");
		while(true){
			try{
				if(!clients.isConnected())
					break;
				if(server.isClosed())
					break;
				while(in.available() == 0);
				String s = in.readUTF();
				print("Client send "+s);
			}
			catch(IOException e){
				
			}
		}			
		return;
	};
}
