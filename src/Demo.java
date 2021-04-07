import controllers.FiveOversGame;
import controllers.Game;

public class Demo {
    public static void main(String[] args) {
      Game game = new FiveOversGame();
      game.toss();
      game.start();

    }
}
