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
public class PlayerHand {

    private card[] hcards;

    PlayerHand(card[] hcards) {
        this.hcards = hcards;
    }

    public void showHand() {

        for (int i = 0; i < hcards.length; i++) {

            System.out.printf("Card %2d: %s of %s \n", i + 1, hcards[i].getRank(), hcards[i].getSuit());

        }

    }

    public int getCurrentScore() {

        return addCards(hcards);

    }

    private int addCards(card[] hcards) {
        int sum = 0;

        for (int i = 0; i < hcards.length; i++) {

            sum = sum + hcards[i].getValue();
        }

        return sum;
    }

}
