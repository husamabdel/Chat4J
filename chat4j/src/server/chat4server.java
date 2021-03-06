	/*
	 * Project: Chat4j
	 * @author: Husam Abdalla
	 * Date started: 05/16/2022
	 * 
	 * chat4server Class for the server side
	 * 
	 * */

package server;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.desktop.*;
import javax.swing.*;

public class chat4server {

	private int port;
	private ServerSocket socket;

	
	public chat4server(ServerSocket socket) {
		
		this.socket = socket;
		
	}
	

	void closeServer() throws IOException {
		
		if(socket != null) {
			socket.close();
		}
	}
	
	
	/*
	 * 
	 * 
	 * The method to start the server socket
	 * 
	 * 
	 * */
	
	void StartServer() {
		
		try {
			
			
			while(!socket.isClosed()) {
			
				socket = new ServerSocket(port);
				Socket sock = socket.accept();
				handleClients handler = new handleClients(sock);
				Thread thread = new Thread(handler);
				thread.start();
				
			}
	
		
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String arguments[]) throws IOException{
		
		System.out.println("Starting server::: \n\n");
		ServerSocket sock = new ServerSocket(9691);
		chat4server server = new chat4server(sock);
		server.StartServer();
		
	}
	
}