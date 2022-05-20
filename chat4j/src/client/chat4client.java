package client;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.desktop.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.*;

import client.UI.UI;

public class chat4client extends JFrame{
	
	private String server;
	private static int port;
	public Socket client;
	private BufferedReader read;
	private BufferedWriter write;
	private static String username = System.getProperty("user.name");
	private JButton button = new JButton("Send!");
	
	private JTabbedPane tabbedPane;
    private JPanel panel1;
    public static JTextArea textArea;
    public JTextField textField;
    private JLabel label;
    private JButton send;
    private JScrollPane scrollPane;
    //public chat4client client;
    
    
    /*
    
    GETTERS AND SETTERS
    
    */
    
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JButton getSend() {
        return send;
    }

    public void setSend(JButton send, ActionListener a) {

        this.send = send;
        send.addActionListener(a);
        
    }
    

    /*
    
    CONSTRUCTOR
    
    */
    

    public void UI(String username, Socket socket){

        //client = new chat4client(socket, username);

        this.setTitle("Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(720,620);
        setFirstPanel();
        this.add(tabbedPane);
        this.setResizable(false);
        this.setVisible(true);
        

    }


    /* 
    
    Set first panel and components
    
    */

    public void setFirstPanel(){

        panel1 = new JPanel();
        tabbedPane = new JTabbedPane();
        
        textArea = new JTextArea(30,60);
        textArea.setText("Testing text\n");
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "The Group Chat:",TitledBorder.RIGHT, TitledBorder.TOP));
        textArea.setBackground(Color.WHITE);
        send = new JButton("SEND!!");
        send.addActionListener(new buttonListen());

        textField = new JTextField(20);

        panel1.add(textArea);
        panel1.add(textField);
        panel1.add(send);

        tabbedPane.add("Chat", panel1);

    }


    /*
    
    ACTION LISTENERS::
    
    */

/*
    private class buttonListen implements ActionListener{

        public void actionPerformed(ActionEvent e){

            String Message = textField.getText()+"\n";
            System.out.println(Message);
            sendMessage(Message);
            textField.setText(" ");
            textArea.append(Message);

        }

    }
*/

    /*
    
    MAIN FOR TESTING ONLY!!

    */



    //public static void main(String[] args) throws IOException{
        //new UI();
    //}


	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * SEPARATE UI AND BACKEND
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */


	/*
	
	GETTERS AND SETTERS
	
	*/


	public JButton getButton(){

		button.addActionListener(new buttonListen());
		return button;

	}

	public void setButton(){

		button.addActionListener(new buttonListen());

	}

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
		
		UI(username, client);
		//setButton();
		
		this.client = client;
		try {
			
			this.write = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			this.read = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			messageListener();
			sendMessage(username);


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
            sendMessage(username + ": " +Message);
            textField.setText(" ");
            textArea.append(username + ": " +Message);

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

