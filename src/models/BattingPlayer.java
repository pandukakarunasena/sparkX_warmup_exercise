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
        setStriking( true );
        setNumbersOfBallsFaced();
        //generate a random play
        int play = generatePlays();

        //conditionally make the play
        if (play == FiveOversGameRules.CAUGHT_OUT || play == FiveOversGameRules.BOWLED_OUT) {
            setOut( true );
            playerState =  playerOutScenarios( play );
            setCurrentPlay(0);
        }else{
            playerState = playerPlayScenarios( play );
        }
        return playerState;
    }

    private PlayerState playerOutScenarios(int play){
        if (play == FiveOversGameRules.BOWLED_OUT) {
            setOutBy(PlayerState.BOWLED_OUT);
            System.out.println("bowled");
        }
        if (play == FiveOversGameRules.CAUGHT_OUT) {
            setOutBy(PlayerState.CAUGHT_OUT);
            System.out.println("caught");
        }
        System.out.println("player " + getNo() + " is out");
        return PlayerState.OUT;

    }

    private PlayerState playerPlayScenarios(int play){
        PlayerState playerState;
        if(play%2 == 0){
            setScore(play);
            playerState =  PlayerState.STRIKING;
        }else{
            setScore(play);
            playerState = PlayerState.SIDE_CHANGE;
        }
        setCurrentPlay(play);
        System.out.println("player "+ getNo()+ " scored "+ play);

        return playerState;
    }

    public int generatePlays(){
        return new Random().nextInt(8);
    }
}
