/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unit_testing_2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Danimaetrix
 */
public class Diff21Test {

    Diff21 dif = new Diff21();

    public Diff21Test() {
    }

    @Test
    public void smalln() {
        assertEquals(16, dif.diff21(5));
    }

    @Test
    public void largen() {
        assertEquals(58, dif.diff21(50));
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

    @Test
    public void testDiff21() {
    }

}
