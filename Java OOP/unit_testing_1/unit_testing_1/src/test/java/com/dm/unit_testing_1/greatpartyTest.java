/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.unit_testing_1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danimaetrix
 */
public class greatpartyTest {

    greatparty party = new greatparty();

    public greatpartyTest() {
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

//    greatParty(30, false) → false
//    greatParty(50, false → true
//    greatParty(70, true) → true
//    greatParty(39, true) → false
//    greatParty(39, false) → false
//    greatParty(40, true) → true
//    greatParty(40, false) → true
//    greatParty(60, true) → true
//    greatParty(60, false) → true
//    greatParty(61, true) → true
//    greatParty(61, false) → false
    @Test
    public void test61false() {
        assertFalse(party.greatParty(61, false));
    }

    @Test
    public void test61true() {
        assertTrue(party.greatParty(61, true));
    }

    @Test
    public void test60false() {
        assertTrue(party.greatParty(60, false));
    }

    @Test
    public void test60true() {
        assertTrue(party.greatParty(60, true));
    }

    @Test
    public void test40false() {
        assertTrue(party.greatParty(40, false));
    }

    @Test
    public void test40true() {
        assertTrue(party.greatParty(40, true));
    }

    @Test
    public void test39false() {
        assertFalse(party.greatParty(39, false));
    }

    @Test
    public void test39true() {
        assertFalse(party.greatParty(39, true));
    }

    @Test
    public void test30false() {
        assertFalse(party.greatParty(30, false));
    }

    @Test
    public void test50false() {
        assertTrue(party.greatParty(50, false));
    }

    @Test
    public void test70true() {
        assertTrue(party.greatParty(70, true));
    }

}
