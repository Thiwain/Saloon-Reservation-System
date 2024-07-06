/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.properties;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Window;

import javax.swing.*;
import java.io.*;

public class ThemeManager {

    private static boolean isDarkTheme = false;

    static {
        loadTheme();
        applyTheme();
    }

    public static void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        applyTheme();
        saveTheme();
    }

    public static void applyTheme() {
        try {
            if (isDarkTheme) {
                FlatMacDarkLaf.setup();
            } else {
                FlatMacLightLaf.setup();
            }
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateUI() {
        for (Window window : JFrame.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    private static void saveTheme() {
        try (FileOutputStream fos = new FileOutputStream("theme.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeBoolean(isDarkTheme);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTheme() {
        try (FileInputStream fis = new FileInputStream("theme.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            isDarkTheme = ois.readBoolean();
        } catch (IOException e) {
            // Default to light theme if loading fails
            isDarkTheme = false;
        }
    }

    public static boolean isDarkTheme() {
        return isDarkTheme;
    }
}
