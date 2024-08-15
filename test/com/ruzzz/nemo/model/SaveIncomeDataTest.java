/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.ruzzz.nemo.model;

import java.io.File;
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
public class SaveIncomeDataTest {

    public SaveIncomeDataTest() {
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
     * Test of saveDataToXML method, of class SaveIncomeData.
     */
    @Test
    public void testSaveDataToXML() throws Exception {
        System.out.println("saveDataToXML");
        String year = "2023";
        try {
            File xmlFile = new File("incomeData_2023.xml");
            SaveIncomeData.saveDataToXML(year, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
