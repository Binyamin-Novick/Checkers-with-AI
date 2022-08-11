package Checkers.impl;
import Checkers.Board_and_Controller.Board;
import Checkers.Board_and_Controller.Piece;
import Checkers.Controller;
import Checkers.Move;

import java.util.*;

public class ControllerImpl implements Controller {
Stack<Piece[][]> PastBordStates= new Stack<Piece[][]>();
Board Board;
public  ControllerImpl(Board bord){
    this.Board=bord;
}






    @Override
    public List<Move> GetAllRedMoves() {
    List<Move> out=new ArrayList<>();
    Set<Piece>red=new HashSet<Piece>();
        for (Piece p :Board.getAllPieces()){
            if(p.isRed()) {
                red.add(p);
            }
        }
        for(Piece rp:red){
            out.addAll(GetMovesOfPiece(rp.getLocation()));
        }
        return out;
    }

    @Override
    public List<Move> GetAllBlackMoves() {
        List<Move> out=new ArrayList<>();
        Set<Piece>Black=new HashSet<Piece>();
        for (Piece p :Board.getAllPieces()){
            if(!(p.isRed())) {
               Black.add(p);
            }
        }
        for(Piece bp:Black){
            out.addAll(GetMovesOfPiece(bp.getLocation()));
        }
        return out;
    }

    @Override
    public List<Move> GetMovesOfPiece(int[] location) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getNormalMoves(location));
        if(Board.getPiece(location).isKing()){
            moves.addAll(getKingJumps(location));
        }
        else if(Board.getPiece(location).isRed()){
           moves.addAll(upleftjump(location,new Moveimpl(location,new int[]{1,1}),false));
            moves.addAll(uprightjump(location,new Moveimpl(location,new int[]{1,1}),false));
        }
        else {
            moves.addAll(downleftjump(location,new Moveimpl( location,new int[]{1,1}),false));
            moves.addAll(downRighjump(location,new Moveimpl( location,new int[]{1,1}),false));
        }
        return moves;
    }

    private List<Move> getKingJumps(int[] location){
    List<Move> out = new ArrayList<Move>();
        out.addAll(downleftjump(location,new Moveimpl( location,new int[]{1,1}),true));
        out.addAll(upleftjump(location,new Moveimpl(location,new int[]{1,1}),true));
        out.addAll(downRighjump(location,new Moveimpl( location,new int[]{1,1}),true));
        out.addAll(uprightjump(location,new Moveimpl( location,new int[]{1,1}),true));
        return out;
    }

    private HashSet<Move> getRedJumps(int[] location, HashSet<Move> jumps) {
        Piece piece = Board.getPiece(location);
        if(!canUpLeftJump(location, piece) && !canUpRightJump(location, piece)){
            return jumps;
        }
        if(canUpLeftJump(location, piece)){
            Moveimpl jump = new Moveimpl(location, upLeft(upLeft(location)));
            jump.addJump(upLeft(location));
            jumps.add(jump);
        }
        if(canUpRightJump(location, piece)){
            Moveimpl jump = new Moveimpl(location, upLeft(upRight(location)));
            jump.addJump(upRight(location));
            jumps.add(jump);
        }
        Set<Move> moveSet = new HashSet<>();
        moveSet.addAll(jumps);
        int count = jumps.size();
        while(count <= jumps.size()){
            count = jumps.size() + 1;
            for(Move move : moveSet){
                if(canUpLeftJump(move.getEndingLocation(), piece)){
                    Moveimpl jump = new Moveimpl(location, upLeft(upLeft(move.getEndingLocation())), move.getJumpLocations());
                    jump.addJump(upLeft(move.getEndingLocation()));
                }
                if(canUpRightJump(move.getEndingLocation(), piece)){
                    Moveimpl jump = new Moveimpl(location, upRight(upRight(move.getEndingLocation())), move.getJumpLocations());
                    jump.addJump(upRight(move.getEndingLocation()));
                }
            }
            Set<Move> thirdSet = new HashSet<>();
            thirdSet.addAll(jumps);
            thirdSet.removeAll(moveSet);
            moveSet.clear();
            moveSet.addAll(thirdSet);
        }
        return jumps;
    }
    private HashSet<Move> getBlackJumps(int[] location, HashSet<Move> jumps) {
        Piece piece = Board.getPiece(location);
        if(!canDownLeftJump(location, piece) && !canDownRightJump(location, piece)){
            return jumps;
        }
        if(canDownLeftJump(location, piece)){
            Moveimpl jump = new Moveimpl(location, downLeft(downLeft(location)));
            jump.addJump(downLeft(location));
            jumps.add(jump);
        }
        if(canDownRightJump(location, piece)){
            Moveimpl jump = new Moveimpl(location, downLeft(downRight(location)));
            jump.addJump(downRight(location));
            jumps.add(jump);
        }
        Set<Move> moveSet = new HashSet<>();
        moveSet.addAll(jumps);
        int count = jumps.size();
        while(count <= jumps.size()){
            count = jumps.size() + 1;
            for(Move move : moveSet){
                if(canDownLeftJump(move.getEndingLocation(), piece)){
                    Moveimpl jump = new Moveimpl(location, downLeft(downLeft(move.getEndingLocation())), move.getJumpLocations());
                    jump.addJump(downLeft(move.getEndingLocation()));
                }
                if(canDownRightJump(move.getEndingLocation(), piece)){
                    Moveimpl jump = new Moveimpl(location, downRight(downRight(move.getEndingLocation())), move.getJumpLocations());
                    jump.addJump(downRight(move.getEndingLocation()));
                }
            }
            Set<Move> thirdSet = new HashSet<>();
            thirdSet.addAll(jumps);
            thirdSet.removeAll(moveSet);
            moveSet.clear();
            moveSet.addAll(thirdSet);
        }
        return jumps;
    }

    private boolean canUpRightJump(int[] location, Piece piece) {
        if (inBounds(upRight(location)) == true) {
            if ((Board.getPiece(upRight(location)) == null) || (piece == null)) {
                return false;
            }
            if (inBounds(upRight(location)) && Board.getPiece(upRight(location)).isRed() != piece.isRed() && inBounds(upRight(upRight(location))) && Board.getPiece(upRight(upRight(location))) == null) {
                return true;
            }}
            return false;

    }
    private boolean canUpLeftJump(int[] location, Piece piece){
        if (inBounds(upLeft(location)) == true) {
            if ((Board.getPiece(upLeft(location)) == null) || (piece == null)) {
                return false;
            }
            if (inBounds(upLeft(location)) && Board.getPiece(upLeft(location)).isRed() != piece.isRed() && inBounds(upLeft(upLeft(location))) && Board.getPiece(upLeft(upLeft(location))) == null) {
                return true;
            }
        }
        return false;
    }


    private List<Move> uprightjump(int[]location,Move pastmove, boolean kingstart){
        List<int[]> pastlocs = new ArrayList<>();
        pastlocs.addAll( pastmove.getJumpLocations());
    boolean canj=false;
    Piece[][] pastbord;
    List<Move> moves= new ArrayList<Move>();
    Piece piece = Board.getPiece(location);
    if(canUpRightJump(location,Board.getPiece(location))){
        canj=true;

        if(canj==true){
            pastbord=Board.getBoardCopy();
            Board.deletePiece(Board.getPiece(upRight(location)));
            Board.deletePiece(piece);
            Board.putPiece(piece,upRight(upRight(location)));
            pastlocs.add(upRight(location));
            Move m = new Moveimpl(pastmove.getStartingLocation(),upRight(upRight(location)), (ArrayList<int[]>) pastlocs);
            moves.add(m);
            moves.addAll(uprightjump(upRight(upRight(location)),m,kingstart));
            moves.addAll(upleftjump(upRight(upRight(location)),m,kingstart));
            if(kingstart){
                moves.addAll(downRighjump(upRight(upRight(location)),m,kingstart));
                moves.addAll(downleftjump(upRight(upRight(location)),m,kingstart));
            }
            Board.setBoard(pastbord);

        }


    }
        return moves;
    }
    private List<Move> downleftjump(int[]location,Move pastmove,boolean kingstart){
        List<int[]> pastlocs = new ArrayList<>();
               pastlocs.addAll( pastmove.getJumpLocations());
        boolean canj=false;
        Piece[][] pastbord;
        List<Move> moves= new ArrayList<Move>();
        Piece piece = Board.getPiece(location);
        if(canDownLeftJump(location,Board.getPiece(location))){
            canj=true;
            if(canj==true){
                pastbord=Board.getBoardCopy();
                Board.deletePiece(Board.getPiece(downLeft(location)));
                Board.deletePiece(piece);
                Board.putPiece(piece,downLeft(downLeft(location)));
                pastlocs.add(downLeft(location));
                Move m = new Moveimpl(pastmove.getStartingLocation(),downLeft(downLeft(location)), (ArrayList<int[]>) pastlocs);
                moves.add(m);
                moves.addAll(downRighjump(downLeft(downLeft(location)),m,kingstart));
                moves.addAll(downleftjump(downLeft(downLeft(location)),m,kingstart));
                if(kingstart){
                    moves.addAll(uprightjump(downLeft(downLeft(location)),m,kingstart));
                    moves.addAll(upleftjump(downLeft(downLeft(location)),m,kingstart));
                }
                Board.setBoard(pastbord);
            }
        }
        return moves;
    }


    private List<Move> downRighjump(int[]location,Move pastmove,boolean kingstart){
        List<int[]> pastlocs = new ArrayList<>();
        pastlocs.addAll( pastmove.getJumpLocations());
        boolean canj=false;
        Piece[][] pastbord;
        List<Move> moves= new ArrayList<Move>();
        Piece piece = Board.getPiece(location);
        if(canDownRightJump(location,Board.getPiece(location))){
            canj=true;
            if(canj==true){
                pastbord=Board.getBoardCopy();
                Board.deletePiece(Board.getPiece(downRight(location)));
                Board.deletePiece(piece);
                Board.putPiece(piece,downRight(downRight(location)));
                pastlocs.add(downRight(location));
                Move m = new Moveimpl(pastmove.getStartingLocation(),downRight(downRight(location)), (ArrayList<int[]>) pastlocs);
                moves.add(m);
                moves.addAll(downRighjump(downRight(downRight(location)),m,kingstart));
                moves.addAll(downleftjump(downRight(downRight(location)),m,kingstart));
                if(kingstart){
                    moves.addAll(uprightjump(downRight(downRight(location)),m,kingstart));
                    moves.addAll(upleftjump(downRight(downRight(location)),m,kingstart));
                }
                Board.setBoard(pastbord);
            }
        }
        return moves;
    }

    private List<Move> upleftjump(int[]location,Move pastmove,boolean kingstart){
        List<int[]> pastlocs = new ArrayList<>();
        pastlocs.addAll( pastmove.getJumpLocations());
        boolean canj=false;
        Piece[][] pastbord;
        List<Move> moves= new ArrayList<Move>();
        Piece piece = Board.getPiece(location);
        if(canUpLeftJump(location,Board.getPiece(location))){
            canj=true;
            if(canj==true){
                pastbord=Board.getBoardCopy();
                Board.deletePiece(Board.getPiece(upLeft(location)));
                Board.deletePiece(piece);
                Board.putPiece(piece,upLeft(upLeft(location)));
                pastlocs.add(upLeft(location));
                Move m = new Moveimpl(pastmove.getStartingLocation(),upLeft(upLeft(location)), (ArrayList<int[]>) pastlocs);
                moves.add(m);
                moves.addAll(uprightjump(upLeft(upLeft(location)),m,kingstart));
                moves.addAll(upleftjump(upLeft(upLeft(location)),m,kingstart));
                if(kingstart){
                    moves.addAll(downRighjump(upLeft(upLeft(location)),m,kingstart));
                    moves.addAll(downleftjump(upLeft(upLeft(location)),m,kingstart));

                }
                Board.setBoard(pastbord);

            }


        }
        return moves;
    }


    private boolean canDownLeftJump(int[] location, Piece piece){
        if (inBounds(downLeft(location)) == true) {
        if((Board.getPiece(downLeft(location))==null)||(piece==null)){
            return false;
        }
        if(inBounds(downLeft(location)) && Board.getPiece(downLeft(location)).isRed() != piece.isRed() && inBounds(downLeft(downLeft(location))) && Board.getPiece(downLeft(downLeft(location))) == null){
            return true;
        }}
        return false;
    }

    private boolean canDownRightJump(int[] location, Piece piece){
        if (inBounds(downRight(location)) == true) {
        if((Board.getPiece(downRight(location))==null)||(piece==null)){
            return false;
        }
        if(inBounds(downRight(location)) && Board.getPiece(downRight(location)).isRed() != piece.isRed() && inBounds(downRight(downRight(location))) && Board.getPiece(downRight(downRight(location))) == null){
            return true;
        }}
        return false;
    }

    private int[] upRight(int[] location){
       int[] array = new int[2];
       array[0] = location[0] + 1;
       array[1] = location[1] + 1;
       return array;
    }
    private int[] upLeft(int[] location){
        int[] array = new int[2];
        array[0] = location[0] - 1;
        array[1] = location[1] + 1;
        return array;
    }
    private int[] downRight(int[] location){
        int[] array = new int[2];
        array[0] = location[0] + 1;
        array[1] = location[1] - 1;
        return array;
    }
    private int[] downLeft(int[] location){
        int[] array = new int[2];
        array[0] = location[0] - 1;
        array[1] = location[1] - 1;
        return array;
    }


    private boolean inBounds(int[] location){
        if(location[0] < 0 || location[0] > 7 || location[1] < 0 || location[1] > 7){
            return false;
        }
        return true;
    }

    private List<Move> getNormalMoves(int[] location) {
    Piece piece= Board.getPiece(location);
    List<Move> out = new ArrayList<Move>();
    if(piece.isKing()){
        out.addAll(getRedMove(piece));
        out.addAll(getBlackMove(piece));
        return out;
    }
    if(piece.isRed()){
        out.addAll(getRedMove(piece));
    }else {
        out.addAll(getBlackMove(piece));
    }
return out;

    }

    private List<Move> getRedMove(Piece p){
    List<Move> list = new ArrayList<Move>();
        if((inBounds(upLeft(p.getLocation())))&&((Board.getPiece(upLeft(p.getLocation())))==null)){
            list.add(new Moveimpl(p.getLocation(),upLeft(p.getLocation())));
        }
        if((inBounds(upRight(p.getLocation())))&&((Board.getPiece(upRight(p.getLocation())))==null)){
            list.add(new Moveimpl(p.getLocation(),upRight(p.getLocation())));
        }
        return  list;

    }
    private List<Move> getBlackMove(Piece p){
        List<Move> list = new ArrayList<Move>();
        if((inBounds(downLeft(p.getLocation())))&&((Board.getPiece(downLeft(p.getLocation())))==null)){
            list.add(new Moveimpl(p.getLocation(),downLeft(p.getLocation())));
        }
        if((inBounds(downRight(p.getLocation())))&&((Board.getPiece(downRight(p.getLocation())))==null)){
            list.add(new Moveimpl(p.getLocation(),downRight(p.getLocation())));
        }
        return  list;

    }

    @Override
    public boolean DoBlackMove(Move m) {
        Move mtodo=null;
        if (CheckBlackMove(m)) {
            this.putBoardInStack();
            List<Move> realmoves = GetMovesOfPiece(m.getStartingLocation());
            int numberofj = 0;
            for (Move seemove : realmoves) {
                if ((seemove.getEndingLocation()[0] == m.getEndingLocation()[0]) && (m.getEndingLocation()[1] == seemove.getEndingLocation()[1])) {
                    if (seemove.getJumpLocations().size() >= numberofj) {// because the moves created from the ui do not contain thespots to jump it will find the move with the same and spot but the must jumps
                        mtodo = seemove;
                        numberofj = seemove.getJumpLocations().size();
                    }
                }
            }
            if (!(mtodo == null)) {
                Piece piece = Board.getPiece(mtodo.getStartingLocation());
                Board.deletePiece(piece);
                Board.putPiece(piece, mtodo.getEndingLocation());
                for (int[] js : mtodo.getJumpLocations()) {
                    Board.deletePiece(Board.getPiece(js));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean DoRedMove(Move m) {

        Move mtodo=null;
        if (CheckRedMove(m)) {
            this.putBoardInStack();
            List<Move> realmoves = GetMovesOfPiece(m.getStartingLocation());
            int numberofj = 0;
            for (Move seemove : realmoves) {
                if ((seemove.getEndingLocation()[0] == m.getEndingLocation()[0]) && (m.getEndingLocation()[1] == seemove.getEndingLocation()[1])) {
                    if (seemove.getJumpLocations().size() >= numberofj) {// because the moves created from the ui do not contain thespots to jump it will find the move with the same and spot but the must jumps
                        mtodo = seemove;
                        numberofj = seemove.getJumpLocations().size();
                    }
                }
            }
            if (!(mtodo == null)) {
                Piece piece = Board.getPiece(mtodo.getStartingLocation());
                Board.deletePiece(piece);
                Board.putPiece(piece, mtodo.getEndingLocation());
                for (int[] js : mtodo.getJumpLocations()) {
                    Board.deletePiece(Board.getPiece(js));
                }
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean CheckRedMove(Move m) {
        if(m==null){
            return false;
        }
    Move mtodo = null;
    List<Move> realmoves;
    int numberofj=0;
    if(inBounds(m.getStartingLocation())) {
        if (!(Board.getPiece(m.getStartingLocation()) == null)) {

            if ((Board.getPiece(m.getStartingLocation()).isRed()) && (inBounds(m.getEndingLocation()))) {
                realmoves = GetMovesOfPiece(m.getStartingLocation());
                for (Move seemove : realmoves) {
                    if ((seemove.getEndingLocation()[0] == m.getEndingLocation()[0]) && (m.getEndingLocation()[1] == seemove.getEndingLocation()[1])) {
                        return true;
                    }
                }

            }
        }
    }
        return false;
    }

    @Override
    public boolean CheckBlackMove(Move m) {
        if(m==null){
            return false;
        }
        Move mtodo=null;
        List<Move> realmoves;
        int numberofj=0;
        if(inBounds(m.getStartingLocation())) { // if in bounds;
            if (!(Board.getPiece(m.getStartingLocation()) == null)) {// if there actuly is a pice there
                if (!(Board.getPiece(m.getStartingLocation()).isRed()) && (inBounds(m.getEndingLocation()))) {
                    realmoves = GetMovesOfPiece(m.getStartingLocation());
                    for (Move seemove : realmoves) {
                        if ((seemove.getEndingLocation()[0] == m.getEndingLocation()[0]) && (m.getEndingLocation()[1] == seemove.getEndingLocation()[1])) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public boolean Undo() {
    Board.setBoard(PastBordStates.pop());
        return false;
    }
    private boolean putBoardInStack(){
        PastBordStates.push(Board.getBoardCopy());
        return true;
    }



}
