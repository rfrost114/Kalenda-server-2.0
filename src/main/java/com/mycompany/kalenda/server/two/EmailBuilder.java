/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kalenda.server.two;

/**
 *
 * @author Richie Frost
 * code adapted from Digital Ocean gmail tutorial 
 */


import java.util.Date;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailBuilder {
    
    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("kalenda.merger@gmail.com", "NoReply-Kalenda"));
            message.setReplyTo(InternetAddress.parse("kalenda.merger@gmail.com", false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            message.setSentDate(new Date());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Sending to " + toEmail);
            Transport.send(message);
            System.out.println("Sent");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
