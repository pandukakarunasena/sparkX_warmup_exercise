package models;

import java.util.Random;
import sun.misc.Signal;

public class SuperBattingPlayer extends BattingPlayer{

    public SuperBattingPlayer( int no ) {
        super( no );
    }

    @Override
    public int generatePlays(){
        int min = 3;
        int max = 8;
        return new Random().nextInt(max - min) + min;
    }
}
