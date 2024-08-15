/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.ruzzz.nemo.gui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Acer
 */
public class SignInTest {

    public SignInTest() {
    }

    private static int result;
    int expResult;

    @Before
    public void setUp() {
        result = SignIn.generateSixDigitNumber();
        expResult = result;
    }

    @After
    public void tearDown() {

    }

    /**
     * Test of generateSixDigitNumber method, of class SignIn.
     */
    @Test
    public void testGenerateSixDigitNumber() {
        System.out.println("generateSixDigitNumber");

//        int result = SignIn.generateSixDigitNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class SignIn.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        SignIn.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
