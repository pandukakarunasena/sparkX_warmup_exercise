import static org.junit.jupiter.api.Assertions.*;

import models.BattingPlayer;
import models.BattingPlayerState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

class BattingPlayerTest {
    BattingPlayer battingPlayer;

    @DisplayName("Should create a BattingPlayerState Object")
    @RepeatedTest( 10 )
    void shouldCreateBattingPlayerStateObject() {
        battingPlayer = new BattingPlayer( 01 );
        BattingPlayerState battingPlayerState = battingPlayer.bat();
        int currentPlay = battingPlayer.getCurrentPlay();
        if(currentPlay == 7 || currentPlay == 8){
            Assertions.assertEquals( BattingPlayerState.OUT, battingPlayerState );
        }
        if(currentPlay == 1 || currentPlay == 3){
            Assertions.assertEquals( BattingPlayerState.SIDE_CHANGE, battingPlayerState );
        }
        if(currentPlay == 2 || currentPlay == 4 || currentPlay == 6){
            Assertions.assertEquals( BattingPlayerState.STRIKING, battingPlayerState );
        }

    }

}