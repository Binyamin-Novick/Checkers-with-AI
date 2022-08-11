package Checkers;


import java.util.List;

public interface Controller {
    List<Checkers.Move> GetAllRedMoves();
    List<Checkers.Move> GetAllBlackMoves();
    List<Checkers.Move> GetMovesOfPiece(int[] location);
    boolean DoBlackMove(Checkers.Move m);
    boolean DoRedMove(Checkers.Move m);
    boolean CheckRedMove(Checkers.Move m);
    boolean CheckBlackMove(Checkers.Move m);
    boolean Undo();

}