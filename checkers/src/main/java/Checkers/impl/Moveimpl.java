package Checkers.impl;
import Checkers.Board_and_Controller.Piece;
import Checkers.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Moveimpl implements Move  {
    private int[]StartLocation;
    private int[]EndLocation;
    private double score;
    private ArrayList<int[]> jumps= new ArrayList<int[]>();
    public Moveimpl(int[] StartLocation,int[] EndLocation){// format array[0]=x array[1]= y
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
        score=0;
        this.jumps = new ArrayList<>();
    }
    public Moveimpl(int[] StartLocation,int[] EndLocation, ArrayList<int[]> jumps){// format array[0]=x array[1]= y
        score=0;
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
        for(int[] jump : jumps){
            this.jumps.add(jump);
        }
    }
    public Moveimpl(int[] StartLocation,int[] EndLocation,double Score){
        this.score=Score;
        this.StartLocation = StartLocation;
        this.EndLocation = EndLocation;
    }
    @Override
    public int[] getStartingLocation() {
        int[]temp=new int[2];
        for (int i=0; i<2;i++){
           temp[i]= this.StartLocation[i] ;
        }
        return temp;

    }

    @Override
    public int[] getEndingLocation() {
        int[]temp=new int[2];
        for (int i=0; i<2;i++){
            temp[i]= this.EndLocation[i] ;
        }
        return temp;

    }

    @Override
    public ArrayList<int[]> getJumpLocations() {
        return this.jumps;
    }

    @Override
    public void setMoveScore(double score){
        this.score = score;
    }

    @Override
    public double getMoveScore() {
        double temp = score;
        return temp;
    }

    public void addJump(int[] jump){
        this.jumps.add(jump);
    }

    @Override
    public int compareTo(Move o) {
        if (o.getMoveScore() > this.score) {
            return -1;
        }
        if (o.getMoveScore() < this.score) {
            return 1;

        } else {
            return 0;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moveimpl moveimpl = (Moveimpl) o;
        return Double.compare(moveimpl.score, score) == 0 && Arrays.equals(StartLocation, moveimpl.StartLocation) && Arrays.equals(EndLocation, moveimpl.EndLocation) && Objects.equals(jumps, moveimpl.jumps);
    }

}
