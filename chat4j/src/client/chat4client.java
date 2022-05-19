package client;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.desktop.*;
import javax.swing.*;
import java.awt.event.*;

import client.UI.UI;

public class chat4client extends UI{
	
	private String server;
	private static int port;
	public Socket client;
	private BufferedReader read;
	private BufferedWriter write;
	private static String username = System.getProperty("user.name");
	


	/*
	
	GETTERS AND SETTERS
	
	*/

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		chat4client.port = port;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public BufferedReader getRead() {
		return read;
	}

	public void setRead(BufferedReader read) {
		this.read = read;
	}

	public BufferedWriter getWrite() {
		return write;
	}

	public void setWrite(BufferedWriter write) {
		this.write = write;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		chat4client.username = username;
	}

	/**
	 * 
	 * 
	 * CONSTRUCTORS
	 * 
	 * @param client
	 * @param username
	*/
	
	public chat4client(){

	}

	public chat4client(Socket client, String username){
		
		super(username, client);
		JButton button = new JButton();
		super.setSend(button, new buttonListen());
		
		this.client = client;
		try {
			
			this.write = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			this.read = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			messageListener();
			sendMessage(" ");


		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 * 
	 * Replace Scanner Object with a getter from the UI class later..
	 * Best is a JTextField
	 * 
	 * 
	 * */
	
	

	public void sendMessage(String messageToSend) {
		
		try {
			write.write(System.getProperty("user.name"));
			write.newLine();
			write.flush();
			
			Scanner scan = new Scanner(System.in);
			if (client.isConnected()) {
				

				write.write(messageToSend);
				write.newLine();
				write.flush();
				
			} else{
				scan.close();
				return;
			}
			
			
		} catch(IOException e) {
			System.exit(1);
				}
	}


	/*
	

	Await other messages from other users.
	
	
	*/


	public void messageListener() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String gcMessage;
				
				while(client.isConnected()) {
					
					try {
						
						gcMessage = read.readLine();
						System.out.println(gcMessage);
						UI.textArea.append(gcMessage);
						
					}
					catch(IOException e) {
						System.exit(1);
					}
					
				}
				
			}
			
		}).start();
		
	}
	

	/*
	
	
	ACTION LISTENER FOR BUTTON::

	
	
	*/ 
	
    private class buttonListen implements ActionListener{

        public void actionPerformed(ActionEvent e){

            String Message = textField.getText()+"\n";
            System.out.println(Message);
            sendMessage(Message);
            textField.setText(" ");
            textArea.append(Message);

        }

    }



	public static void main(String arguments[]) throws IOException{
		
       /*
        * 
        * Use the hostname as a username:
        * 
        * */

		try{
			System.out.println("Start Client \n\n\n");
			InetAddress addy = InetAddress.getLocalHost();
			username = addy.getHostAddress();
			port = 9691;
			Socket sock = new Socket ("192.168.0.25", port);
			new chat4client(sock, username);
	
		} catch(java.net.ConnectException e ){
			JOptionPane.showMessageDialog(null, "Failed to connect to the server!", "Connection failed at port: " + port , JOptionPane.ERROR_MESSAGE, null);
			System.exit(1);
		}


	}
	
	
	
}

