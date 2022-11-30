/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kalenda.server.two;

/**
 *
 * @author Richie Frost
 */

import packages.User;
import java.io.*;
import java.net.*;

public class Handler extends Thread {
    
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    
    public Handler(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        
        System.out.println("Handler Created");
        this.socket = socket;
        this.in = in;
        this.out = out;
        
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                
                User user = (User) in.readObject();
                

                long groupID = user.getGroupNumber();
                Boolean isGroup = false;
                Group currentGroup = null;
                
                for (Group g : ServerController.groupList) {
                    if (groupID == g.getID()) {
                        isGroup = true;
                        currentGroup = g;
                    }
                }

                
                if (isGroup) {
                    System.out.println("Adding user to group " + groupID);
                    currentGroup.addMember(user);
                }
                else {
                    System.out.println("Creating Group: " + groupID);
                    System.out.println("Adding user: " + user.getName() + " to group");
                    currentGroup = new Group(groupID , user.getGroupSize());
                    currentGroup.addMember(user);
                    ServerController.groupList.add(currentGroup);
                    System.out.println(ServerController.groupList);
                }
                
                



            
            }
            catch (IOException ioe) {
//                ioe.printStackTrace();
                break;
            }
            catch (ClassNotFoundException cnf) {
                cnf.printStackTrace();
            }
            
            try {
                this.in.close();
                this.out.close();
                socket.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            
        }
    }
    
    
}



