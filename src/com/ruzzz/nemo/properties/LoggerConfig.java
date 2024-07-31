/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.properties;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {

    public static final Logger infoLogger = Logger.getLogger("infoLogger");
    public static final Logger errorLogger = Logger.getLogger("errorLogger");

    static {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            LogManager.getLogManager().reset();

            // Configure infoLogger for INFO and below
            FileHandler infoFileHandler = new FileHandler("logs/user.log", 50000, 1, true);
            infoFileHandler.setFormatter(new SimpleFormatter());
            infoFileHandler.setLevel(Level.INFO);
            infoLogger.addHandler(infoFileHandler);

            ConsoleHandler infoConsoleHandler = new ConsoleHandler();
            infoConsoleHandler.setFormatter(new SimpleFormatter());
            infoConsoleHandler.setLevel(Level.INFO);
            infoLogger.addHandler(infoConsoleHandler);

            // Configure errorLogger for WARNING and above
            FileHandler errorFileHandler = new FileHandler("logs/error.log", 50000, 1, true);
            errorFileHandler.setFormatter(new SimpleFormatter());
            errorFileHandler.setLevel(Level.WARNING);
            errorLogger.addHandler(errorFileHandler);

            ConsoleHandler errorConsoleHandler = new ConsoleHandler();
            errorConsoleHandler.setFormatter(new SimpleFormatter());
            errorConsoleHandler.setLevel(Level.WARNING);
            errorLogger.addHandler(errorConsoleHandler);

            infoLogger.setLevel(Level.INFO);
            errorLogger.setLevel(Level.WARNING);

        } catch (IOException e) {
            errorLogger.log(Level.SEVERE, "Error in setting up logger configuration", e);
        }
    }
}
