package models;

import java.util.Random;


public class BattingPlayer extends Player{

    private int score;
    private boolean out;
    private String outBy;
    private int numberOfBallsFaced;
    private boolean striking;
    private int currentPlay;
    private int[] plays = {0,1,2,3,4,6,7,8};

    public BattingPlayer(int no){
        super(no);
        this.score = 0;
        this.striking = false;
        this.numberOfBallsFaced=0;
    }

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

    public BattingPlayerState bat() {
        BattingPlayerState playerState;
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
            System.out.println("player " + super.getNo() + " is out");
            playerState =  BattingPlayerState.OUT;
            this.currentPlay = 0;
        }else{
            if(play%2 == 0){
                this.setScore(play);
                playerState = BattingPlayerState.STRIKING;
            }else{
                this.setScore(play);
                playerState = BattingPlayerState.SIDE_CHANGE;
            }
            this.currentPlay = play;
            System.out.println("player "+ super.getNo()+ " scored "+ play);
        }
        return playerState;
    }
}
