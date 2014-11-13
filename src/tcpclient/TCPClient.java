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
 * @author Peter
 */
public class TCPClient implements Runnable { 
     
    public static void main(String argv[]) {   
        
        Socket s;
        final String host = "localhost";
        final int port = 8000;
        
        new Thread(new TCPClient()).start();

        try { 
            
            s = new Socket(host, port); 
            BufferedReader sis = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String msg;

            do {                   
                System.out.print("Type a message: ");
                msg = sis.readLine();
                os.write(msg); 
                os.newLine(); 
                os.flush();
                System.out.println("Server_Main: " + is.readLine());  
            }
            while ((!msg.equals("exit") && !msg.equals("down")));
           
            s.close();

        } 
        catch (IOException e) {
             System.err.println(e); 
        } 
        
    } 

    @Override
    public void run() {

        Socket s;
        
        try {
            s = new Socket("localhost", 8000);
            BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String msg;

            do {                   
                msg = is.readLine();
                System.out.println("Server_Thread: " + msg);  
            }
            while (true);
        } 
        catch (IOException e) {
            System.err.println(e); 
        }
    }
} 




