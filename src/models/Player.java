package models;

import java.util.Random;

public class Player implements IPlayer{
    private int no;
    private int score;
    private boolean out;
    private String outBy;
    private int numberOfBallsFaced;
    private boolean striking;
    private int currentPlay;
    private int[] plays = {0,1,2,3,4,6,7,8};

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

    @Override
    public PlayerState bat() {
        PlayerState playerState;
        this.striking = true;
        this.numberOfBallsFaced++;
        //generate a random play
        int play = this.plays[new Random().nextInt(8)];

        //conditionally make the play
        if (play == 7 || play == 8) {
            this.out = true;

            if (play == 7) {
                this.outBy = "bowled";
                System.out.println("bowled");
            }
            if (play == 8) {
                this.outBy = "caught";
                System.out.println("caught");
            }
            System.out.println("player " + this.no + " is out");
            playerState =  PlayerState.OUT;
            this.currentPlay = 0;
        }else{
            if(play%2 == 0){
                this.setScore(play);
                playerState = PlayerState.STRIKING;
            }else{
                this.setScore(play);
                playerState = PlayerState.SIDE_CHANGE;
            }
            this.currentPlay = play;
            System.out.println("player "+ this.no+ " scored "+ play);
        }
        return playerState;
    }


    @Override
    public void ball() {

    }


    @Override
    public void field() {

    }

}
