/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggingExample {
    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    static {
        try {
            // Ensure logs directory exists
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Remove the default console handler
            LogManager.getLogManager().reset();

            // Set up a file handler
            FileHandler fileHandler = new FileHandler("logs/myapp.log", 50000, 1, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            logger.addHandler(fileHandler);

            // Set up a console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            // Set the logger level
            logger.setLevel(Level.INFO);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in setting up logger configuration", e);
        }
    }

    public static void main(String[] args) {
        logger.info("This is an info message");
        logger.warning("This is a warning message");
        logger.severe("This is a severe message");

        try {
            // Simulate an exception
            throw new Exception("Simulated Exception");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }
}
