/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.component;

import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

/**
 *
 * @author Acer
 */
public class RoundedTextField extends JTextField {

    public RoundedTextField() {
        init();
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc:999; "
                + "margin:0, 10, 0,10");
    }

}
