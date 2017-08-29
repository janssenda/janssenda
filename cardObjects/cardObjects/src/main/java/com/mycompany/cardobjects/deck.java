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
public class deck {

    private int deckSize = 52, cardsRemaining = deckSize;
    private card[] cards = new card[deckSize];
    private String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private int counter = 0;
    private card[] handp;

    deck() {

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < deckSize / 4; i++) {
                card c = new card();

                if (i > 9) {
                    c.setValue(10);
                } else {
                    c.setValue(i + 1);
                }

                c.setSuit(suits[j]);
                cards[counter] = c;
                counter = counter + 1;

            }
        }
    }

    public void showCard(int cnum) {

        System.out.println(cards[cnum].getValue());
        System.out.println(cards[cnum].getSuit());
    }

    public card[] dealHand(int sizeHand) {
        int card_selection;
        boolean valid = false;

        card[] hand = new card[sizeHand];

        if (sizeHand <= cardsRemaining) {
            System.out.println("**************************");
            for (int i = 0; i < sizeHand; i++) {

                while (valid == false) {
                    card_selection = trueRandom.randInt(0, 51);

                    if (cards[card_selection] != null) {
                        hand[i] = cards[card_selection];
                        cards[card_selection] = null;
                        cardsRemaining = cardsRemaining - 1;
                        valid = true;
                    }
                }
                valid = false;

                System.out.printf("Card %2d: %2d of %s ", i + 1, hand[i].getValue(), hand[i].getSuit());
                System.out.println();

            }
        } else {
            System.out.println("Sorry! The deck is out of cards! Please shuffle a new deck to continue.");
        }
        System.out.println("**************************");
        handp = hand;
        return hand;
    }

    public void getCardsRemaining() {

        System.out.println("There are " + cardsRemaining + " cards remaining in the deck.");
    }
    
    public card[] getCardsDealt(){
        
        return handp;
        
    }

}
