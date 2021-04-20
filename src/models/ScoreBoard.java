package models;

import utils.FiveOversGameRules;
import java.util.List;
import utils.TeamState;

public class ScoreBoard {
    public ScoreBoard(){ }

    public void showScoreBoard( List<Team> teams ){
        String formatPlayer =  "|%1$-10s|%2$-10s|\n";
        System.out.format( formatPlayer, "PLAYER", "SCORE");

        for(Team t: teams ){
            for ( Player p: t.getPlayers()){
                String scoreDetails = p.getScore() + " (" + p.getNumberOfBallsFaced() + ") balls";
                System.out.format( formatPlayer, p.getNo(),scoreDetails);
            }
            System.out.println();
        }
    }

    public void showGameSummary(Team team1 , Team team2 , TeamState teamState){
        System.out.println();
        System.out.println("team " + team1.getName() + " won the toss and selected to bat first");
        if(teamState.equals( TeamState.GAME_WON )){
            team2.setGameWon( true );
            System.out.println("team "+ team2.getName() + " WON BY " + (FiveOversGameRules.WICKETS - team2.getWickets()) + " WICKETS");
        }else{
            team1.setGameWon( true );
            System.out.println("team "+ team1.getName() + " WON BY " + (team1.getScore() - team2.getScore()) + " runs");
        }
    }
}
