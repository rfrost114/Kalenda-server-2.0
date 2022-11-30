/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kalenda.server.two;

import java.util.Properties;
import javax.mail.*;
import packages.DayNames;
import packages.User;

/**
 *
 * @author Richie Frost
 */
public class Group {
    
    private User[] users;
    private int currentMembers;
    private long groupID;
    
    // Constructor
    public Group(long groupID, int groupSize) {
        
        users = new User[groupSize];
        this.groupID = groupID;
        currentMembers = 0;
    }
    
    //add member to group
    public void addMember(User newUser) {
        
        users[currentMembers] = newUser;
        currentMembers++;
        System.out.println("New user with email " + newUser.getName() + " added to group " + groupID + " Current size: " + currentMembers + "out of " + users.length);
        
        if (currentMembers >= users.length) {
            merge();
        }
    }
    
    //merge algorithm
    private void merge() {
        
        Boolean[][] mergedWeekAvail = new Boolean[7][24];
        int counter = 0;
        
        for (DayNames dayName : DayNames.values()) {
            
            //create array of all trues (base state)
            Boolean[] mergedDay = new Boolean[24];
            for (int i = 0; i < 24; i++) {
                mergedDay[i] = true;
            }
            
            for (User u : users) {

                Boolean[] x = u.getAvail().getDay(dayName).getTimes();
                
                // AND together all users in group
                for (int i = 0; i < 24; i++) {
                    mergedDay[i] = mergedDay[i] && x[i];
                }
            
            }
            mergedWeekAvail[counter] = mergedDay;
            counter++;
            
        }
        System.out.println(mergedWeekAvail);

        String output = generateOutputString(mergedWeekAvail);
        System.out.println(output);
        sendEmails(output);
    }
    
    public long getID() {
        return groupID;
    }
    
    //starts to send emails
    public void sendEmails (String mergedWeek) {
        // largely stock code
        String from = "kalenda.merger@gmail.com";
        //wont work for you b/c of secuirty settings :) either contact me or make your own account
        //
        String pswd = "********";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pswd);
            }
        };
        Session session = Session.getDefaultInstance(properties, authenticator);
        
        //send emails to all users
        for (User u : this.users) {
            String email = u.getName();
            EmailBuilder.sendEmail(session, email, "Your Merged Schedule", mergedWeek);
        }
        
    }
    
    // a complicated method to turn the boolean[][] from merge into something readable
    public String generateOutputString (Boolean[][] mergedWeek) {
        String outputString = "Here are all of the times your group is available this week\n";
        String spacer = "==========\n";
        String[] days = {"Monday:\n" , "Tuesday:\n", "Wednesday:\n" , "Thursday:\n" , "Friday:\n" , "Saturday:\n" , "Sunday:\n"};
        String[] times = {
            "12 AM\n",
            "1 AM\n",
            "2 AM\n",
            "3 AM\n",
            "4 AM\n",
            "5 AM\n",
            "6 AM\n",
            "7 AM\n",
            "8 AM\n",
            "9 AM\n",
            "10 AM\n",
            "11 AM\n",
            "12 PM\n",
            "1 PM\n",
            "2 PM\n",
            "3 PM\n",
            "4 PM\n",
            "5 PM\n",
            "6 PM\n",
            "7 PM\n",
            "8 PM\n",
            "9 PM\n",
            "10 PM\n",
            "11 PM\n"
        };
        for (int day = 0; day < 7; day++) {
            outputString += days[day];
            outputString += spacer;
            for (int time = 0; time < 24; time++) {
                if (mergedWeek[day][time] == true) {
                    outputString += times[time];
                }
            }
            outputString += spacer;
            outputString += "\n";
        
        }
        
        
        
        
        return outputString;
    
    }
            
    
}
