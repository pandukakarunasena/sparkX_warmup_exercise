package models;

import utils.PlayerState;

public abstract class Player{
    private int no;
    private int score;
    private boolean out;
    private PlayerState outBy;
    private int numberOfBallsFaced;
    private boolean striking;
    private int currentPlay;


    public Player(int no){
        this.no = no;
        this.score = 0;
        this.striking = false;
        this.numberOfBallsFaced=0;
    }

    public int getNo() { return no; }
    public void setNo( int no ) { this.no = no; }
    public void setStriking(boolean striking){
        this.striking = striking;
    }
    public boolean isStriking(){
        return this.striking;
    }
    public int getScore() {
        return score;
    }
    public int getNumberOfBallsFaced(){return numberOfBallsFaced;}
    public void setScore(int score) {
        this.score += score;
    }
    public boolean isOut() {
        return out;
    }
    public void setOut(boolean out) {
        this.out = out;
    }
    public int getCurrentPlay(){return this.currentPlay; }
    protected void setCurrentPlay( int play ) {
        this.currentPlay = play;
    }
    protected void setOutBy( PlayerState outBy ) {
        this.outBy = outBy;
    }
    protected void setNumbersOfBallsFaced() {
        this.numberOfBallsFaced++;
    }

    public abstract PlayerState bat();
    public void ball(){};
    public void field(){};
}
