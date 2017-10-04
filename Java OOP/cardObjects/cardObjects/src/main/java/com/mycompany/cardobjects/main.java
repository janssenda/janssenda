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
        System.out.println("\n\n");

        deck d1 = new deck();

        
        d1.shuffleDeck();
        //d1.showDeck();
        
        
        PlayerHand p1 = new PlayerHand(d1.dealHand(9));
        
        p1.showHand();
        

        

        System.out.println("\n\n");

    }
}
