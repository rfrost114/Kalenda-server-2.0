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
import java.util.ArrayList;

public class Handler extends Thread {
    
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
//    private ArrayList<Group> groupList;
    
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
                
//                user.getAvail().report();
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


//                int actionNumber = in.readInt();
//                in.skip(in.available());
//                out.writeBoolean(false);
//
//                
//                
//                if (actionNumber == 1) {
//                    //create group
//                    System.out.println("Ready to create group");
//                    long groupNumber = 0;
//                    int groupSize = 0;
//                    
//                    try {
//                        groupNumber = in.readLong();
//                        System.out.println("got groupID: " + groupNumber);
//                    }
//                    catch (IOException ioe) {
//                        System.out.println("Trouble reading groupNumber");
//                        ioe.printStackTrace();
//                    }
//                    
//                    try {
//                        groupSize = in.readInt();
//                        
//                    }
//                    catch (IOException ioe) {
//                        System.out.println("Trouble reading groupSize");
//                        ioe.printStackTrace();
//                    }
//                    
//                    if (groupNumber != 0 && groupSize != 0) {
//                        System.out.println("Creating New Group " + groupNumber + " Size: " + groupSize);
//                        Group g = new Group(groupNumber, groupSize);
//                        ServerController.groupList.add(g);
//                    }
//                }
//                else if (actionNumber == 2) {
//                    System.out.println("Ready to add user to group");
//                    try {
//                        User user = (User) in.readObject();
//                        System.out.println("Adding user to a group");
//                        System.out.println(user.getName());
//                        long id = user.getGroupNumber();
//                        
//                        for (Group g : ServerController.groupList) {
//                            if (g.getID() == id) {
//                                g.addMember(user);
//                            }
//                        }
//                    }
//                    catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    }
//                    catch (ClassNotFoundException cnf) {
//                        cnf.printStackTrace();
//                    }
//                    
//                }
//                else {
//                    System.out.println("Unkown Action Number Recieved: " + actionNumber);
//                }
