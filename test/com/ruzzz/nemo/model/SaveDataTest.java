/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.ruzzz.nemo.model;

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
public class SaveDataTest {

    public SaveDataTest() {
    }



    @BeforeClass
    public static void setUpClass() {
  
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of SaveData method, of class SaveData.
     */
    @Test
    public void testSaveData() {
        System.out.println("SaveData");
        SaveData.SaveData();
        assertEquals(null, this);
        fail("The test case is a prototype.");
    }

}
