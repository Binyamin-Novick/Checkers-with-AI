package Checkers.Board_and_Controller;

public interface Piece {
    enum Color{RED, BLACK};
    int[] getLocation();
    boolean movePiece(int[] location);
    boolean isKing();
    boolean isRed();
}
