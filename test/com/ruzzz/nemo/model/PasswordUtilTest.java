/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.ruzzz.nemo.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class PasswordUtilTest {

    public PasswordUtilTest() {
    }

    PasswordUtil pwu;
    String pw = "123#Pasword";
    String hpw;

    @Before
    public void setUp() {
        pwu = new PasswordUtil();
        hpw = pwu.hashPassword(pw);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHashPassword() {
        pwu.hashPassword(pw);
    }

    @Test
    public void testCheckPassword() {

        pwu.checkPassword(pw, hpw);

    }

}
