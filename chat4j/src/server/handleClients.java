package server;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.desktop.*;
import javax.swing.*;

public class handleClients implements Runnable{

	public static ArrayList <handleClients> clientList = new ArrayList<>();
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private String username; 
	
	public handleClients(Socket socket) {
		
		this.socket = socket;
		try {
			
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username = reader.readLine();
			clientList.add(this);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
		String clientMessage;
		while(socket.isConnected()) {
			
			try {
				
				clientMessage = reader.readLine();
				System.out.println(clientMessage);
				broadcast(clientMessage);
				
			} catch(IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
		
	}
	
	public void broadcast(String Messages) {
		
		for(handleClients client : clientList) {
			try {
				
				if(!client.username.equals(username)) {
					client.writer.write(Messages);
					client.writer.newLine();
					client.writer.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void removeClient() {
		
		clientList.remove(this);
		broadcast("SERVER: " + username + " Disconnected");
		
	}
	
}
