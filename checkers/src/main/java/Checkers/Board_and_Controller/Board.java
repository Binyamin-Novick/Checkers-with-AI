package Checkers.Board_and_Controller;

import java.util.Set;

public interface Board {
    double getBoardValue();
    Piece getPiece(int[] location);
    boolean deletePiece(Piece piece);
    boolean putPiece(Piece piece, int[] location);
    Piece[][] getBoardCopy();
    void setBoard(Piece[][] setup);
    Set<Piece> getAllPieces();
}