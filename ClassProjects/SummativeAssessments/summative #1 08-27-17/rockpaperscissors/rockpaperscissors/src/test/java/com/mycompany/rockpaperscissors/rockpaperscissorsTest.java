/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rockpaperscissors;

import static com.mycompany.rockpaperscissors.rockpaperscissors.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 * @author danimaetrix
 */
public class rockpaperscissorsTest {

    public rockpaperscissorsTest() {
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
     * Test of main method, of class rockpaperscissors.
     */
    @Test
    public void testMain() {
    }

    /**
     * Test of showResults method, of class rockpaperscissors.
     */
    @Test
    public void testShowResults() {
    }

    /**
     * Test of getNumGames method, of class rockpaperscissors.
     */
    @Test
    public void testGetNumGames() {
    }

    /**
     * Test of getChoice method, of class rockpaperscissors.
     */
    @Test
    public void testGetChoice() {
    }

    /**
     * Test of playRound method, of class rockpaperscissors.
     */
    @Test
    public void testPlayRound() {
    }

    /**
     * Test of selectWinner method, of class rockpaperscissors.
     */
    @Test
    public void testSelectWinner() {
        String userguess = "rock";
        String cpuguess = "paper";
        assertEquals("Computer",selectWinner(userguess, cpuguess));

        userguess = "rock";
        cpuguess = "scissors";
        assertEquals("You",selectWinner(userguess, cpuguess));

        userguess = "rock";
        cpuguess = "rock";
        assertEquals("Draw",selectWinner(userguess, cpuguess));

        userguess = "paper";
        cpuguess = "scissors";
        assertEquals("Computer",selectWinner(userguess, cpuguess));

        userguess = "paper";
        cpuguess = "rock";
        assertEquals("You",selectWinner(userguess, cpuguess));

        userguess = "scissors";
        cpuguess = "rock";
        assertEquals("Computer",selectWinner(userguess, cpuguess));

        userguess = "scissors";
        cpuguess = "paper";
        assertEquals("You",selectWinner(userguess, cpuguess));

        userguess = "cats";
        cpuguess = "rock";
        assertEquals("Cats",selectWinner(userguess, cpuguess));
    }

    @Test
    public void testGetAIGuess() {
        String guess;

        for (int i = 0; i < 30; i++) {
            guess = getAIGuess();
            if (guess != "paper" && guess != "rock" && guess != "scissors") {
                fail("AI guess is invalid...");
            }
        }

    }

}
