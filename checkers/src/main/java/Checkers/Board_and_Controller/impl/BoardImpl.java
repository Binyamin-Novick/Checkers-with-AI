package Checkers.Board_and_Controller.impl;

import Checkers.Board_and_Controller.Board;
import Checkers.Board_and_Controller.Piece;

import java.util.HashSet;
import java.util.Set;

public class BoardImpl implements Board {

    private Piece[][] board;
    private Set<Piece> pieces;

    public BoardImpl(){
        this.board = new Piece[8][8];
        this.pieces = new HashSet<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i%2 != j%2){
                    continue;
                }
                if(j < 3){
                    Piece add = new PieceImpl(Piece.Color.RED, new int[]{i,j}, false);
                    this.board[i][j] = add;
                    this.pieces.add(add);
                }
                if(j>4){
                    Piece add = new PieceImpl(Piece.Color.BLACK, new int[]{i,j}, false);
                    this.board[i][j] = add;
                    this.pieces.add(add);
                }
            }
        }
    }

    @Override
    public double getBoardValue() {

        double red=0;
        double black=0;
        for (Piece piece: pieces){
            if(piece.isRed()){
                if (piece.isKing()){
                    red= red+1.5;
                }else {
                    red++;
                }
            }else {
                if (piece.isKing()){
                    black= black+1.5;
                }else {
                    black++;
                }
            }
        }
        return (red-black)/(red+black);
    }

    @Override
    public Piece getPiece(int[] location) {
        return this.board[location[0]][location[1]];
    }

    @Override
    public boolean deletePiece(Piece piece) {
        if(!this.pieces.remove(piece)){
            return false;
        }
        this.board[piece.getLocation()[0]][piece.getLocation()[1]] = null;
        return true;
    }

    @Override
    public boolean putPiece(Piece piece, int[] location) {
        if(this.board[location[0]][location[1]] != null){
            return false;
        }
        if((piece.isRed() && location[1] == 7) || (!piece.isRed() && location[1] == 0)){
            Piece.Color color = Piece.Color.BLACK;
            if(piece.isRed()){
                color = Piece.Color.RED;
            }
            Piece king = new PieceImpl(color, piece.getLocation(), true);
            piece = king;
        }
        this.board[location[0]][location[1]] = piece;
        this.pieces.add(piece);
        piece.movePiece(location);
        return true;
    }

    @Override
    public Piece[][] getBoardCopy() {
        Piece[][] setup= new Piece[8][8];
        for (int i=0;i<board.length;i++){
            for (int t=0;t<board.length;t++){
                setup[i][t] =board[i][t];
            }
        }
        return setup;
    }

    @Override
    public void setBoard(Piece[][] setup) {
        for (int i=0;i<board.length;i++){
            for (int t=0;t<board.length;t++){
                board[i][t]=setup[i][t];
            }
        }

        maintenance();
    }

    private void maintenance() {
        this.pieces = new HashSet<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board[i][j] == null){
                    continue;
                }
                else{
                    this.board[i][j].movePiece(new int[]{i,j});
                    this.pieces.add(this.board[i][j]);
                }
            }
        }
    }

    @Override
    public Set<Piece> getAllPieces() {
        return this.pieces;
    }

}
