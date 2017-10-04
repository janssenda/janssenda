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

    private boolean shuffled = false;
    private card[] cards, handp;
    private int numDecks = 1, cardsRemaining = 52;

    deck() {
        cards = constructDeck(numDecks);
    }

    deck(int numDecks) {
        this.numDecks = numDecks;
        cardsRemaining = 52 * numDecks;
        cards = constructDeck(numDecks);
    }

    private card[] constructDeck(int numDecks) {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        int counter = 0;
        card[] cards = new card[52 * numDecks];

        for (int k = 0; k < numDecks; k++) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 13; i++) {
                    card c = new card();

                    if (i > 9) {
                        c.setValue(10);

                    } else {

                        c.setValue(i + 1);
                    }

                    c.setRank(String.valueOf(i + 1));

                    switch (i) {
                        case 0: {
                            c.setRank("Ace");
                            break;
                        }
                        case 10: {
                            c.setRank("Jack");
                            break;
                        }
                        case 11: {
                            c.setRank("Queen");
                            break;
                        }
                        case 12: {
                            c.setRank("King");
                            break;
                        }
                    }

                    c.setSuit(suits[j]);
                    cards[counter] = c;
                    counter = counter + 1;

                }
            }
        }
        return cards;

    }

    public void showCard(int cnum) {

        System.out.printf("Card %2d: %s of %s \n", cnum + 1, cards[cnum].getRank(), cards[cnum].getSuit());
    }

    public void showDeck() {

        for (int i = 0; i < 52 * numDecks; i++) {
            showCard(i);
        }

    }

    public boolean isShuffled() {
        return shuffled;
    }

    public card[] dealHand(int sizeHand) {
        int card_selection;
        boolean valid = false;

        card[] hand = new card[sizeHand];

        if (sizeHand <= cardsRemaining) {
            //System.out.println("**************************");
            for (int i = 0; i < sizeHand; i++) {
                if (!shuffled) {
                    while (valid == false) {
                        card_selection = trueRandom.randInt(0, 51 * numDecks);

                        if (cards[card_selection] != null) {
                            hand[i] = cards[card_selection];
                            cards[card_selection] = null;
                            cardsRemaining = cardsRemaining - 1;
                            valid = true;
                        }
                    }
                    valid = false;
                } 
                else {

                    hand[i] = cards[i];
                    cards[i] = null;
                    cardsRemaining = cardsRemaining - 1;
                }

                //System.out.printf("Card %2d: %s of %s ", i + 1, hand[i].getRank(), hand[i].getSuit());
                //System.out.println();
            }
        } else {
            System.out.println("Sorry! The deck is out of cards! Please shuffle a new deck to continue.");
        }
        //System.out.println("**************************");
        handp = hand;
        return hand;
    }

    public void shuffleDeck() {
        boolean finished = false;
        card[] store = new card[cards.length];
        int testindex;
        //System.out.println(cards.length);
        //System.out.println(cards[51].getRank());

        for (int i = 0; i < cards.length; i++) {

            while (!finished) {
                testindex = trueRandom.randInt(0, cards.length - 1);
                //testCard = cards[testindex];
                if (cards[testindex] != null) {
                    store[i] = cards[testindex];
                    cards[testindex] = null;
                    finished = true;
                }
            }
            finished = false;
        }

        cards = store;
        shuffled = true;

    }

    public void getCardsRemaining() {

        System.out.println("There are " + cardsRemaining + " cards remaining in the deck.");
    }

    public card[] getCardsDealt() {

        return handp;

    }

}
