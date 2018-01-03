/*
 * ClientHolder.java
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

public class ClientHolder {
	private CInfo[] clients;
	private int c_size; //current size
	private int id;
	private String name;
	
	public ClientHolder(int size, String n){
		id = 0;
		name = n;
		c_size = 0;
		clients = new CInfo[size];
		System.out.println("ClientHolder initialization, size is "+clients.length+" name is "+name);
	}
	
	public int addClient(java.net.Socket c, String name){
		//~ 1 indicate full server
		//~ 2 indicate repeat name 
		//~ 0 indicate successful add
		
		int i = 0;
		if(c_size == clients.length){
			return 1;
		}
		
		//search for repeat name
		for(int j = 0;j<clients.length;j++){
			if(clients[j].getName().equals(name)){
				return 2;
			}
		}
		
		//search for empty spot
		for(i = 0;i<clients.length;i++){
			if(clients[i]==null){
				break;
			}
		}
		clients[i].setInfo(c, name);
		c_size++;
		return 0;
	}
	
	public CInfo getClient(int i){//get client by index
		return clients[i];
	}
	
	public int size(){
		return c_size;
	}
	//~ public void broadcast(CInfo c, String msg){
		//~ for(int i=0;i<client.length;i++){
			//~ CInfo.getSocket().out
		//~ }
	//~ };
}
