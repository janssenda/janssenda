/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unit_testing_2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Danimaetrix
 */
public class MakePiTest {

    MakePi mypi = new MakePi();

    public MakePiTest() {
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
     * Test of makePi method, of class MakePi.
     */
    @Test
    public void testMakePi5() {
        //PI = 3.1415   92653589793
        int[] pi5 = {3,1,4,1,5};
        int[] pi;
        
        pi = mypi.makePi(5);
        
        Assert.assertArrayEquals(pi5,pi);
    }

    @Test
    public void testMakePi10() {
        //PI = 3.141592653    589793
        int[] pi10 = {3,1,4,1,5,9,2,6,5,3};
        int[] pi;
        
        pi = mypi.makePi(10);
        
        Assert.assertArrayEquals(pi10,pi);
    }

}
