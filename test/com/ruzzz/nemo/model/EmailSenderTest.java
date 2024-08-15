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
public class EmailSenderTest {

    public EmailSenderTest() {
    }

    EmailSender es;

    @Before
    public void setUp() {
        es = new EmailSender();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSendEmail() {
        es.sendEmail("Test", "<h1>Test</h1>", "thiwainm@gmail.");
    }

}
