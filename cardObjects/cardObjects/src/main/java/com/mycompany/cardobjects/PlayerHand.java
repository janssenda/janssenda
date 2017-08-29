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
    private card[] cards;
    
    PlayerHand(card[] cards){
        this.cards = cards;
    }
    
    
    public int getCurrentScore(){
       
        return addCards(cards);
  
    }
    
    private int addCards(card[] cards){
        int sum = 0;
        
        for (int i=0; i < cards.length; i++){
            
            sum = sum + cards[i].getValue();
        }
        
        
        return sum;
    }
    
}
