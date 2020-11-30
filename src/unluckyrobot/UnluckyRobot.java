/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kofi Osel
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int itrCount;
        int totalScore = 300;
        char direction = 0;
        boolean limit = false;
        int xCoord = 0;
        int yCoord = 0;
        int reward = rand.nextInt(6)+1;
        int points10 = 10;
        int points50 = 50;
        int over = 2000;
        int prize;
        int punishment;
        boolean game = true;
        String str = "";    
        
        for(itrCount = 0; itrCount <= 20; itrCount++){
            game = isGameOver(x,y,totalScore,itrCount);
        if(game){
           System.out.print("\nPlease enter your name (only two words): ");
           str = scan.nextLine();
           evaluation(totalScore,toTitleCase(str));
           return;
        }
        displayInfo(x,y,itrCount,totalScore);
        System.out.println();
        char directionxy = inputDirection();
        limit = doesExceed(xCoord,yCoord,directionxy);
        prize = reward();
        punishment = punishOrMercy(directionxy, prize);
        if(directionxy == 'r' && x < 4 && limit == false){
            xCoord = x++;
            totalScore -= points50;
            System.out.println();
            totalScore += prize;      
        }
        else if(directionxy == 'l' && x > 0 && limit == false){
            xCoord = x--;
            totalScore -= points50;
            System.out.println();
            totalScore += prize;    
        }
        else if(directionxy == 'u' && y < 4 && limit == false){
            yCoord = y++;
            totalScore-= points10;
            System.out.println();
            totalScore += prize;
            totalScore -= punishment;
            System.out.println();   
        }
        else if(directionxy == 'd' && y > 0 && limit == false){
            yCoord = y--;
            totalScore -= points50;
            System.out.println();
            totalScore += prize;    
        }
        else if(directionxy == 'r' && x == 4){
            yCoord = y;
            xCoord = x-1;
            System.out.print("Exceed boundery, -2000 applied\n");
            System.out.println();
            totalScore -= 2000;
            totalScore -= prize;     
        }
        else if(directionxy == 'l' && x == 0){
            yCoord = y;
            xCoord = x + 1;
            totalScore -= over;
            System.out.print("Exceed boundery, -2000 applied\n");
            System.out.println();
            totalScore += prize;    
        }
        else if(directionxy == 'u' && y == 4){
            yCoord = y - 1;
            xCoord = x;
            totalScore -= over;
            System.out.print("Exceed boundery, -2000 applied\n");
            System.out.println();
            totalScore += prize;
            totalScore -= punishment;
            System.out.println();     
        }
        else if(directionxy == 'd' && y == 0){
            yCoord = y+1;
            xCoord = x;
            totalScore -= over;
            System.out.print("Exceed boundery, -2000 applied\n");
            System.out.println();
            totalScore -= prize;
            System.out.println();
        }else{
            System.out.println();
        }
        }
    }
    /**
     * it displays the coordinate of x and y the iteration and the score
     * @param x this is the coordinate of x
     * @param y this is the coordinate of y
     * @param itrCount this is the the number of times the program run
     * @param totalScore this is the score
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        int yy;
        int xx;
        xx = x;
        yy = y;
        int score;
        score = totalScore;
        itrCount += 0;
        System.out.print("For point(X=" + xx + ", Y=" + yy + ") at iterations: " + 
                itrCount + " the total score is: " + score);
    }
    /**
     * checks if the direction exceed the limit
     * @param x this displays the x coordinate
     * @param y this displays the y coordinate
     * @param direction this is the direction that you want to go with your x 
     * and y coordinate
     * @return if the direction is over the limit
     */
    public static boolean doesExceed(int x, int y, char direction){
        if(x > 4){
            x--;
            return true;
        }
        else if(x < 0){
            x++;
            return true;
        }
        else if(y > 4){
            y--;
            return true;
        }  
        else if(y < 0){
            y++;
            return true;
        }
        else
            return false;
    }
    /**
     * this function is called when the robot goes in a cell and it gives either a reward
     * or punishment
     * @return it returns the number as a reward or a punishment
     */
    public static int reward(){
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        switch (dice) {
            case 1:
                System.out.print("Dice: 1, reward:-100\n");
                return -100;
            case 2:
                System.out.print("Dice: 2, reward:-200\n");
                return -200;
            case 3:
                System.out.print("Dice: 3, reward:-300\n");
                return -300;
            case 4:
                System.out.print("Dice: 4, reward:300\n");
                return 300;
            case 5:
                System.out.print("Dice: 5, reward:200\n");
                return 200;
            case 6:
                System.out.print("Dice: 6, reward:100\n");
                return 100;
        }
        return dice;
    }
    /**
     * this function is called when the reward is negative and it is maid to help the robot
     * @param direction this is the direction that you want to go with your x 
     * and y coordinate
     * @param reward this is the reward
     * @return this will either return 0 or reward
     */
    public static int punishOrMercy(char direction, int reward){
        int prize;
        prize = reward;
        Random rand = new Random();
        int coin = rand.nextInt(2); 
        
        if(coin == 0 && direction == 'u' && prize < 0){
        System.out.print("Coin: tail | Mercy, the negative reward is removed.\n");
            return prize;
        }
        else if(coin == 1 && direction == 'u' && prize < 0){ 
        System.out.print("Coin: head | The negative reward is applied.\n");
            return 0;        
        } else
            return 0;
    }
    /**
     * this function is called to capitalize each letter the two words in  the string
     * @param str this capitalize the each letter
     * @return brings a string to title case
     */
    public static String toTitleCase(String str){
        int space = str.indexOf(" ");
        String fname = str.substring(0, space);
        fname = Character.toUpperCase(fname.charAt(0))+
               fname.substring(1).toLowerCase();
        String lname = str.substring(space + 1);
        lname = Character.toUpperCase(lname.charAt(0)) +
                lname.substring(1).toLowerCase();
        str = fname + " " + lname;
        return str;
       }
    /**
     * this takes the score of the robot and prints a statement a based on the total score
     * @param totalScore this is the total score
     * @param str this is the name
     */
    public static void evaluation(int totalScore, String str){
        int space = str.indexOf(" ");
        String fname = str.substring(0, space);
        fname = Character.toUpperCase(fname.charAt(0))+
               fname.substring(1).toLowerCase();
        String lname = str.substring(space + 1);
        lname = Character.toUpperCase(lname.charAt(0)) +
                lname.substring(1).toLowerCase();
        str = fname + " " + lname;
        if(totalScore >= 2000){
            System.out.print("Victory," + str + " , your score is " + totalScore);
        }else{
            System.out.print("Mission failed " + str + " , your score is " + totalScore);
        }    
    }
    /**
     * this checks if the user input a valid direction
     * @param direction this is the input of the direction
     * @return a valid direction
     */
    public static boolean directionIsValid(char direction){
        return direction == 'r'|| direction == 'l' || direction == 'u' || direction 
                == 'd';  
    }
    /**
     * it will ask to enter a new char until the entered char matches one of them
     * @return the inputDirection question
     */
    public static char inputDirection(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter valid direction: ");
        char direction = scan.next().charAt(0);
        while(!directionIsValid(direction)){
            System.out.print("Please enter a valid direction: ");
            direction = scan.next().charAt(0);
        }
        return direction;
    }
    /**
     * this function is to recognize when the when game is over
     * @param x this is the x coordinate
     * @param y this is the y coordinate
     * @param totalScore this is the total score of the robot 
     * @param itrCount this is the the number of times the program run
     * @return return true if the game is over 
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        if(itrCount == 20)
            return true;
        else if(totalScore <= -1000)
            return true;
        else if(totalScore >= 2000)
            return true;
        else if(x == 4 && y == 4 || x == 4 && y == 0)
            return true;
        else
            return false;
    }
}



