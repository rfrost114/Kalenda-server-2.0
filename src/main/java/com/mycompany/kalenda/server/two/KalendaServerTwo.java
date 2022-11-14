/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.kalenda.server.two;

/**
 *
 * @author Richie Frost
 */

import java.io.*;

public class KalendaServerTwo {

    public static void main(String[] args) throws IOException {
        ServerController server = new ServerController(5000);
    }
}
