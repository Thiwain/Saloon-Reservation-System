/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JPanelToImage {

    public static void exportPanelAsImage(JPanel panel, String filePath) {
        int width = panel.getWidth();
        int height = panel.getHeight();

        // Create a BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Paint the panel's contents onto the BufferedImage
        Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();

        // Save the image to a file
        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage
        JFrame frame = new JFrame("JPanel to Image Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 300));
        panel.add(new JLabel("Hello, World!"));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // Export the panel as an image
        exportPanelAsImage(panel, "C:\\Users\\Acer\\Documents\\NetBeansProjects\\SaloonNemo\\chart_img\\panel_image.png");
    }
}
