/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpclient;

import java.io.*;
import java.net.*;

/**
 *
 * @author andrej
 */
public class Client {

    private Socket s;
    final String host = "localhost";
    final int port = 8000;

    private BufferedReader sis;
    private BufferedWriter os;
    private BufferedReader is;

    private String msg;

    /*public void initTCPClient() {

        new Thread(new TCPClient()).start();
    }*/

    public boolean initClient() {

        try {
            s = new Socket(host, port);
            sis = new BufferedReader(new InputStreamReader(System.in));
            os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //is = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } 
        catch(ConnectException e) {
            return false;
        }
        catch (IOException e) {
            System.err.println(e);
        }
        
        return true;
    }

    public void sendMessage(String message) {

        try {
            os.write(message); 
            os.newLine();
            os.flush();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    
    public String readMessage() {
        
        try {
            System.out.println("Reading message");
            if(is == null) {
                s.setSoTimeout(50);            //reading timeout
                is = new BufferedReader(new InputStreamReader(s.getInputStream()));
                
            }
            msg = is.readLine();
        }
        catch(SocketTimeoutException e) {
            System.out.println("Waiting for message cancelled.");
        }
        catch(IOException e) {
            System.err.println(e);
        }
        System.out.println("Reading done");
        return msg; 
    }
    
    public void closeClientConnection() {
        
        try {
            s.close();
        }
        catch(IOException e) {
            System.err.println(e);
        }
        
        
    }
}
