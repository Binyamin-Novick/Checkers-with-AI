package Checkers;

import Checkers.Board_and_Controller.Piece;
import Checkers.Move;

public interface UI {
    void printBord(Piece[][] boardArray);
    void printWinner(Piece.Color C);
    Move askPlayerForMove();
    void TellPlayThatMoveIsIllegal();
    void insultPlayer();
    int numberOfPlayers();
    boolean doYouWantAnotherGame();

}

