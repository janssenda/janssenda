/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cardobjects;

/**
 *
 * @author Danimaetrix
 */
public class main {

    public static void main(String[] args) {
        int handsize = 5, rounds = 5;
        card p1hand[], p2hand[];
                
        System.out.println("\n\n");

        deck d1 = new deck();
        deck d2 = new deck();

        for (int i = 0; i < rounds; i++) {
            System.out.println("Player 1 gets: ");
            p1hand = d1.dealHand(handsize);

            System.out.println("\nPlayer 2 gets: ");
            p2hand = d1.dealHand(handsize);
            
            PlayerHand p_1hand = new PlayerHand(p1hand);            
            PlayerHand p_2hand = new PlayerHand(p2hand);      
            
            System.out.println("Player 1 Scores: " + p_1hand.getCurrentScore());
            System.out.println("Player 2 Scores: " + p_2hand.getCurrentScore());
            System.out.println("\n");
            
        }

        System.out.println("\n\n");

    }
}
