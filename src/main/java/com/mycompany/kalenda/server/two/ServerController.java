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
//    private ObjectInputStream in = null;
    protected static ArrayList<Group> groupList;
    
    public ServerController(int port) throws IOException {
        
        groupList = new ArrayList<Group>();
        server = new ServerSocket(port);
        System.out.println("Starting...");
        
        while (true) {
            socket = null;
            
            try {
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
               System.out.println("aaaaaa");
               ioe.printStackTrace();
            }
            
        }
    
    }
    
    
//    private Socket socket = null;
//    private ServerSocket server = null;
//    private DataInputStream inData = null;
//
//    private ArrayList<Group> groupList;
//    
//    public ServerController(int port) {
//        
//        groupList = new ArrayList<Group>();
//        
//        try {
//        
//            server = new ServerSocket(port);
//            System.out.println("Starting...");
//            
//            socket = server.accept();
//            System.out.println("Client connected");
//            
//            inData = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//            
//            
//            while (true) {
//                
//                
//                try {
//                    int actionNumber = inData.readInt();
//                    
//                    switch (actionNumber) {
//                        case 1:
//                            System.out.println("we gotta 1!");
//                            this.createGroup();
//                            //create group
//                            break;
//                        case 2:
//                            joinGroup();
//                            break;
//                        default:
//                            System.out.println("Unkonwn command recieved! " + actionNumber);
//                            break;
//                    }
//                    
//                }
//                catch (IOException ioe) {
////                    System.out.println(ioe);
//                }
//            
//            }
//            
//        }
//        catch (IOException ioe) {
//            System.out.println(ioe);
//        }
//    }
//    
//    private void createGroup() {
//        DataInputStream gIn = null;
//        try {
//            socket.getOutputStream().flush();
//            gIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//        }
//        catch(IOException ioe) {
//            System.out.println(ioe);
//        }
//
//        
//        try {
//            
//            
//            
//            long groupID = gIn.readLong();
//            int groupSize = gIn.readInt();
//            System.out.println("Creating New Group " + groupID + " Size: " + groupSize);
//        
//            Group g = new Group(groupID , groupSize);
//            groupList.add(g);
//            gIn.close();
//        }
//        catch(IOException ioe) {
//            System.out.println(ioe);
//        }
//    }
//    
//    private void joinGroup() {
//        
//        try {
//            InputStream gInput = socket.getInputStream();
//            ObjectInputStream gObjIn = new ObjectInputStream(gInput);
//        
//            User user = (User) gObjIn.readObject();
//            System.out.println("Adding user to a group");
//            long id = user.getGroupNumber();
//            for (Group g : groupList) {
//                if (g.getID() == id) {
//                    g.addMember(user);
//                }
//            }
//            gObjIn.close();
//            gInput.close();
//        
//        
//        }
//        catch (IOException ioe) {
//            System.out.println(ioe);
//        }
//        catch (ClassNotFoundException cnf) {
//            System.out.println(cnf);
//        }

//    }
    
    
}
