import models.Player;
import models.PlayerState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

class PlayerTest {
    Player player;

    @DisplayName("Should create a PlayerState Object")
    @RepeatedTest( 10 )
    void shouldCreatePlayerStateObject() {
        player = new Player( 01 );
        PlayerState playerState = player.bat();
        int currentPlay = player.getCurrentPlay();
        if(currentPlay == 7 || currentPlay == 8){
            Assertions.assertEquals( PlayerState.OUT, playerState );
        }
        if(currentPlay == 1 || currentPlay == 3){
            Assertions.assertEquals( PlayerState.SIDE_CHANGE, playerState );
        }
        if(currentPlay == 2 || currentPlay == 4 || currentPlay == 6){
            Assertions.assertEquals( PlayerState.STRIKING, playerState );
        }

    }

}