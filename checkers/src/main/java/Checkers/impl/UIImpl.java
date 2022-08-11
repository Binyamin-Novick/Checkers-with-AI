package Checkers.impl;


import Checkers.Board_and_Controller.Piece;
import Checkers.Move;
import Checkers.UI;
import java.util.Scanner;

public class UIImpl implements UI {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void printBord(Piece[][] boardArray) {
        System.out.println("\tA\tB\tC\tD\tE\tF\tG\tH");
        for(int j = 0; j < 8; j++){
            System.out.print(8-j + "\t");
            for(int i = 0; i < 8; i++){
                if(boardArray[i][7-j] == null){
                    System.out.print("[]");
                }
                else if(boardArray[i][7-j].isRed()){
                    if(boardArray[i][7-j].isKing()){
                        System.out.print("Ⓡ");
                    }
                    else {
                        System.out.print("ⓡ");
                    }
                }
                else{
                    if(boardArray[i][7-j].isKing()){
                        System.out.print("Ⓑ");
                    }
                    else {
                        System.out.print("ⓑ");
                    }
                }
                System.out.print("\t");
            }
            System.out.print("\n");
        }
    }

    @Override
    public void printWinner(Piece.Color C) {
        if(C.equals(Piece.Color.RED)){
            System.out.println("RED WINS!!!!!!!!!");
        }
        else {
            System.out.println("BLACK WINS!!!!!!!!!");
        }
    }

    @Override
    public Move askPlayerForMove() {

        System.out.println("Please input the next move");
        String input = scanner.nextLine();
        if(input.length() != 8){
            insultPlayer();
            return askPlayerForMove();
        }
        int startX = input.charAt(0) - 65;
        int startY = Character.getNumericValue(input.charAt(1)) - 1;
        int endX = input.charAt(6) - 65;
        int endY = Character.getNumericValue(input.charAt(7)) - 1;
        Move move = new Moveimpl(new int[]{startX,startY},new int[]{endX,endY});
        return move;
    }

    @Override
    public void TellPlayThatMoveIsIllegal() {
        System.out.println("Illegal move");
    }

    @Override
    public void insultPlayer() {
        String[] insultList = {"I will destroy you", "Prepare for DOOOOM", "Oof ouch owie you bones", "Soup", "Peanut M&Ms are clearly trail mix", };
    }

    @Override
    public int numberOfPlayers() {
        boolean fail = true;
        while (fail){
            System.out.println("Input number of players:");
            String input = scanner.nextLine();
            try{
                int number = Integer.parseInt(input);
                if(number >= 3){
                    System.out.println("Too many players");
                }
                else if(number <= -1){
                    System.out.println("Too few players");
                }
                else{
                    return number;
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("That is not a number");
            }
        }
        return -1;
    }

    @Override
    public boolean doYouWantAnotherGame() {
        System.out.println("Do you want to play again?");
        String input = scanner.nextLine();
        if(input.equals("Yes")){
            return true;
        }
        return false;
    }
}
