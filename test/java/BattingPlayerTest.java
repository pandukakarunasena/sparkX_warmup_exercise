import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BattingPlayerTest {
    BattingPlayer battingPlayer = new BattingPlayer( 01 );

    @Test
    void bat() {
        assertNotNull(battingPlayer.bat());
    }

}