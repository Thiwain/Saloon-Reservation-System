/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Acer
 */
import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

class InternetConnectivity {

    public static void checkConnectivity() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();

            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Internet Not Connected");
        }
    }
}
