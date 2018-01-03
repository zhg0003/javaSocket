/*
 * CInfo.java
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

public class CInfo {
	private java.net.Socket client;
	private String name;
	private boolean isConnected;
	DataOutputStream out;
	DataInputStream in;	
	
	public CInfo(){
		client = null;
		name = null;
		isConnected = false;
		out = null;
		in = null;
	}
	
	public String getName(){
		return name;
	}
	
	public java.net.Socket getSocket(){
		return client;
	}
	
	public void setInfo(java.net.Socket c, String n){
		client = c;
		name = n;
		isConnected = true;
		try{
			out = new DataOutputStream(c.getOutputStream());			
			in = new DataInputStream(c.getInputStream());	
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}

