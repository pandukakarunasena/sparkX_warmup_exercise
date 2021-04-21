package models;

import java.util.Random;
import utils.FiveOversGameRules;
import utils.PlayerState;

public class BattingPlayer extends Player {

    public BattingPlayer( int no ) {
        super( no );
    }

    @Override
    public PlayerState bat(){
        PlayerState playerState;
        super.setStriking( true );
        super.setNumbersOfBallsFaced();
        //generate a random play
        int play = new Random().nextInt(8);

        //conditionally make the play
        if (play == FiveOversGameRules.CAUGHT_OUT || play == FiveOversGameRules.BOWLED_OUT) {
            super.setOut( true );

            if (play == FiveOversGameRules.BOWLED_OUT) {
                super.setOutBy(PlayerState.BOWLED_OUT);
                System.out.println("bowled");
            }

            if (play == FiveOversGameRules.CAUGHT_OUT) {
                super.setOutBy(PlayerState.CAUGHT_OUT);
                System.out.println("caught");
            }

            System.out.println("player " + super.getNo() + " is out");
            playerState =  PlayerState.OUT;
            super.setCurrentPlay(0);

        }else{
            if(play%2 == 0){
                super.setScore(play);
                playerState = PlayerState.STRIKING;
            }else{
                super.setScore(play);
                playerState = PlayerState.SIDE_CHANGE;
            }
            super.setCurrentPlay(play);
            System.out.println("player "+ super.getNo()+ " scored "+ play);
        }
        return playerState;
    }
}
