/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.model;

import static com.ruzzz.nemo.properties.ThemeManager.applyTheme;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class CheckInternetConnection {

    public CheckInternetConnection() {
        applyTheme();
    }

    public static boolean checkConnectivity() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
//            System.out.println("Connection Successful");
        } catch (Exception e) {
//            System.out.println("Internet Not Connected");
            JOptionPane.showMessageDialog(null, "Internet Not Connected, Please Restart");
            System.exit(0);
            return false;
        }
    }
}
