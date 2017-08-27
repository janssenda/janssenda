/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rockpaperscissors;

import java.util.Scanner;

/**
 *
 * @author Danimaetrix
 */

public class rockpaperscissors {
    
    
    public static void main(String[] args) {
        int [][] totalStats = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        int numgames;
        boolean tryAgain = true;

        //Pretty welcome art
        introArt();
  
        // MAIN GAME LOOP
        // Loop continuously until user is finished playing
        while (tryAgain == true){
            
            // Get number of rounds to play
            numgames = getNumGames();   

            // Initialize stats array
            for(int j = 0; j<4; j++){
                totalStats[j][0] = 0;
            }

            totalStats[0][0] = numgames;
            totalStats[0][1] = totalStats[0][1] + numgames;

            // Play all the rounds!!    
            for (int i = 1; i <= numgames; i++){     
               playRound(totalStats); 
            }
                
            // Output the summary
            showResults(totalStats);
        
            System.out.print("\nWould you like to try again (y/n)? ");
        
            // Decide whether to play again
            tryAgain = getChoice();
        
        }
        
        //Exit when user is finished
        System.out.println("\n\nGOODBYE!!!!");
        System.out.println("\n\n");    
        
    }
    
    public static void showResults(int totalStats[][]){        
        
        System.out.println("Final Scores:");
        System.out.println("                    Current        All Time    ");
        System.out.println("                 ------------    ------------");
        System.out.println("Rounds played:        "  
                +  totalStats[0][0] + "               " + totalStats[0][1]);
        System.out.println("Your wins:            "  
                +  totalStats[2][0] + "               " + totalStats[2][1]);  
        System.out.println("Computer wins:        "  
                +  totalStats[3][0] + "               " + totalStats[3][1]);        
        System.out.println("Draws:                "  
                +  totalStats[1][0] + "               " + totalStats[1][1]);        
    }
    
    public static int getNumGames(){
        boolean valid = false;
        int numgames = 0;
        Scanner userInput = new Scanner(System.in);
        
        while(valid == false){
            System.out.print("How many rounds would you like to play? ");
            try{
                numgames = Math.abs(userInput.nextInt());
                valid = true;
               }
            catch (Exception e) {
                System.out.println("Please enter an integer ONLY...");
                userInput.next();
                valid = false;
            }
        } 
        System.out.println("Great! Lets get started :) :) :) :)");
        return numgames;
    }
    
    
    public static boolean getChoice(){
        boolean valid = false, tryAgain = false;
        String rematch;
        
        Scanner userInput = new Scanner(System.in);
        while (valid == false){
            
            rematch = userInput.nextLine();
            rematch = rematch.toLowerCase();            
            
            if (rematch.equals("y")){
                tryAgain = true;
                valid = true;
            
            }
            else if (rematch.equals("n")){
                tryAgain = false;
                valid = true;
            }
            else {
                System.out.println("Please respond with 'y' or 'n'...");
                System.out.print("Would you like to try again (y/n)? ");                
            }

        }
        
        return tryAgain;
        
    }
            
    public static void playRound(int [][] totalStats){
        String AIguess, userguess="rock", winner;
        boolean valid = false;
        
        /* There is a (not so) hidden bonus if the user enters cat as their choice.
        In this case, they automatically win the round and are given a bonus
        of 15 points!        
        */
        
        Scanner userInput = new Scanner(System.in);

        // Acquire valid guess from user
        while (valid == false){           
            System.out.print("\nDo you want rock, paper or scissors? ");

    
            // Get the user choice and convert it to lower case;
            userguess = userInput.nextLine();
            userguess = userguess.toLowerCase();
            System.out.println("--------------------------------------");
            
            
            // Check validity of input
            if (userguess.equals("rock") || userguess.equals("paper") || userguess.equals("scissors")
                    || userguess.equals("cats")){
                valid = true;            
            }
            else{
                System.out.println("Your input was not recognized, please try again... ");
            }
        
        
        }
       
     
        // Get the AI guess
        AIguess = getAIGuess();
        //userguess = getAIGuess();
        
        
        // Compare the user guess with the AI guess
        winner = selectWinner(userguess, AIguess);  
        
        if (winner.equals("Draw")){
            totalStats[1][0] = totalStats[1][0]+1;
            totalStats[1][1] = totalStats[1][1]+1;
        }
        else if (winner.equals("You")){
            totalStats[2][0] = totalStats[2][0]+1;
            totalStats[2][1] = totalStats[2][1]+1;
        }        
        else if (winner.equals("Computer")){
            totalStats[3][0] = totalStats[3][0]+1;
            totalStats[3][1] = totalStats[3][1]+1;
        }
        
        // Secret case for user entering "cats" as their guess!
        else if (winner.equals("Cats")){
            totalStats[2][0] = totalStats[2][0]+15;
            totalStats[2][1] = totalStats[2][1]+15;
            winner = "You!!!!!!!";
            
            System.out.println("***********************************************************");
            
            System.out.println("                                               .--.\n" +
"                                               `.  \\\n" +
"                                                 \\  \\\n" +
"                                                  .  \\\n" +
"                                                  :   .\n" +
"                                                  |    .\n" +
"                                                  |    :\n" +
"                                                  |    |\n" +
"  ..._  ___                                       |    |\n" +
" `.\"\".`''''\"\"--..___                              |    |\n" +
" ,-\\  \\             \"\"-...__         _____________/    |\n" +
" / ` \" '                    `\"\"\"\"\"\"\"\"                  .\n" +
" \\                                                      L\n" +
" (>                                                      \\\n" +
"/                                                         \\\n" +
"\\_    ___..---.               +15 Points!!!!              L\n" +
"  `--'         '.                                           \\\n" +
"                 .                                           \\_\n" +
"                _/`.                                           `.._\n" +
"             .'     -.                                             `.\n" +
"            /     __.-Y     /''''''-...___,...--------.._            |\n" +
"           /   _.\"    |    /                ' .      \\   '---..._    |\n" +
"          /   /      /    /                _,. '    ,/           |   |\n" +
"          \\_,'     _.'   /              /''     _,-'            _|   |\n" +
"                  '     /               `-----''               /     |\n" +
"                  `...-'                                       `...-'\n" +
"\n" +
"");
            
            
            
            System.out.println("\n\n");
            System.out.println("MEEEEEOOWWWWWWWWWWW!");
            System.out.println("Here's a little present from me to you!!");
            System.out.println("You get 15 points!");
            System.out.println("\n\n********************************************");
            System.out.println("\n");
            
        }
        
        
        // Print the outcome of the round
        System.out.println("You picked:          " + userguess);
        System.out.println("The computer picks:  " + AIguess);
        System.out.println("The winner is:       "   +  winner);
        System.out.println("");
        
       

    }
    
    public static String selectWinner(String userguess, String AIguess){
        String winner="";   
        
        if ( AIguess.equals(userguess)){
                winner = "Draw";
        }        
        
        
        else if (userguess.equals("rock")){            
            if (AIguess.equals("scissors")){
                winner = "You";
            }
            else if (AIguess.equals("paper")){
                winner = "Computer";
            }
           
        }

        else if (userguess.equals("paper")){
           
            if (AIguess.equals("rock")){
                winner = "You";
            }
            else if (AIguess.equals("scissors")){
                winner = "Computer";
            }            
        }        
        else if (userguess.equals("scissors")){   
            if (AIguess.equals("paper")){
                winner = "You";
            }
            else if (AIguess.equals("rock")){
                winner = "Computer";
            }            
        }   
        
        else if (userguess.equals("cats")){            
            winner = "Cats";
        }

                      
        
        
        return winner;
    }
        
    
    public static String getAIGuess() {
        String AIguess = "";
        
        switch (trueRandom.randInt(1, 3)){
            case 1: {
                AIguess = "rock";
                break;
            }
            case 2: {
                AIguess = "paper";
                break;
            }
            case 3: {
                AIguess = "scissors";
                break;
            }           
        }
        return AIguess;
    }
    
    
    
    public static void introArt(){
        System.out.println("\n\n");
        System.out.println("               ,@@@@@@@,\n" +
"       ,,,.   ,@@@@@@/@@,  .oo8888o.\n" +
"    ,&%%&%&&%,@@@@@/@@@@@@,8888\\88/8o\n" +
"   ,%&\\%&&%&&%,@@@\\@@@/@@@88\\88888/88'\n" +
"   %&&%&%&/%&&%@@\\@@/ /@@@88888\\88888'\n" +
"   %&&%/ %&%%&&@@\\ V /@@' `88\\8 `/88'\n" +
"   `&%\\ ` /%&'    |.|        \\ '|8'\n" +
"       |o|        | |         | |\n" +
"       |.|        | |         | |\n" +
"\\\\/ ._\\//_/__/  ,\\_//__\\\\/.  \\_//__/_");
        
        System.out.println("----------------------------------------\n"
                + "|  Welcome to Rock Paper Kittens v1.0  |         "
                + "\n----------------------------------------");
        
        System.out.println("");
        
    }
        
    
      
       
    }
    

    
    
