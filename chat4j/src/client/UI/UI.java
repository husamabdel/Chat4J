package client.UI;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.desktop.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.chat4client;


public class UI extends JFrame{
    

    private JTabbedPane tabbedPane;
    private JPanel panel1;
    public static JTextArea textArea;
    public JTextField textField;
    private JLabel label;
    private JButton send;
    private JScrollPane scrollPane;
    public chat4client client;
    
    
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
    

    public UI(){

    }

    public UI(String username, Socket socket){

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
        textArea.setText("Testing text");
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "The Group Chat:",TitledBorder.RIGHT, TitledBorder.TOP));
        textArea.setBackground(Color.WHITE);
        send = new JButton();
        //send.addActionListener(new buttonListen());

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

    

}