/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kalenda.server.two;

/**
 *
 * @author Richie Frost
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerController {
    
    
    private Socket socket;
    private ServerSocket server = null;
    
    // needs to be accessed by other classes and passing does not make sense
    protected static ArrayList<Group> groupList;
    
    public ServerController(int port) throws IOException {
        
        groupList = new ArrayList<Group>();
        server = new ServerSocket(port);
        System.out.println("Starting...");
        
        while (true) {
            socket = null;
            
            try {
                // creates a new handler object whenever there is a connection from the client
                socket = server.accept();
                System.out.println("Connection Established on " + socket);
                
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Creating new Thread for connection...");
                Handler h = new Handler(socket, in, out);
                h.start();
                
            }
            catch (Exception ioe) {
               socket.close();
               // aaaaa is what you will say if this code ever executes
               System.out.println("aaaaaa");
               ioe.printStackTrace();
            }
            
        }
    
    }
    
    

    
    
}
