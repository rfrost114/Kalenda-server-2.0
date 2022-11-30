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

// craeted to handle a single client connection
public class Handler extends Thread {
    
    private final Socket socket;
    private final ObjectInputStream in;
    //output steam is never used but must exist
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
                
                // get the user from the client
                User user = (User) in.readObject();
                

                long groupID = user.getGroupNumber();
                Boolean isGroup = false;
                Group currentGroup = null;
                
                // check if the user needs to join or create a group
                // assumption is that if the group already exists then the user wants to join it
                // and if the group doesn't exist then they want to create it
                // is this a bad assumption ... possibly
                for (Group g : ServerController.groupList) {
                    if (groupID == g.getID()) {
                        isGroup = true;
                        currentGroup = g;
                    }
                }

                
                if (isGroup) {
                    //add user to group
                    System.out.println("Adding user to group " + groupID);
                    currentGroup.addMember(user);
                }
                else {
                    //create new group and add user to it
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



