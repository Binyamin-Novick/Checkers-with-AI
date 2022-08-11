package Checkers.Board_and_Controller.impl;

import Checkers.Board_and_Controller.Piece;

public class PieceImpl implements Piece {
    private Piece.Color color;
    private int[] location;
    private boolean isKing;


    public PieceImpl(Piece.Color color, int[] location, boolean isKing){
        this.color = color;
        this.location = location;
        this.isKing = isKing;
    }



    @Override
    public int[] getLocation() {
        return this.location;
    }

    @Override
    public boolean movePiece(int[] location) {
        this.location = location;
        return true;
    }

    @Override
    public boolean isKing() {
        return isKing;
    }


    @Override
    public boolean isRed() {
        if(this.color == Color.RED){
            return true;
        }
        return false;
    }
}
