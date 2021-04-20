package models;

import utils.FiveOversGameRules;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.PlayerState;
import utils.TeamState;

public class Team {
    private String name;
    private List<Player> players = new ArrayList<>();
    private int score;
    private int wickets;
    private int balls;
    private  Player currentBatter;
    private List<Player> nowPlaying = new ArrayList<>();
    private boolean allOut;
    private boolean oversFinished;
    private boolean won;

    public Team(String name){
        this.name = name;
        this.allOut = false;
        this.oversFinished = false;
        this.won = false;
        this.score = -1;
        this.wickets = 0;
        this.balls = 0;
        for(int i = 0; i < FiveOversGameRules.NO_OF_PLAYERS; i++){
            players.add(new Player(i));
        }
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    public int getBalls() { return balls; }
    public int getWickets() {
        return wickets;
    }
    public void setGameWon(boolean won) { this.won = won; }
    public List<Player> getPlayers() {
        return players;
    }
    public boolean isOversFinished() {
        return oversFinished;
    }
    public boolean getAllOut(){
        return this.allOut;
    }


    public TeamState play(int targetScore){
        Scanner input = new Scanner( System.in );
        initializeTeam();

        int NO_OF_BALLS = FiveOversGameRules.OVERS* FiveOversGameRules.BALLS_PER_OVER;

        for(int balls = 0; balls <= NO_OF_BALLS; balls++){
            if(this.score > targetScore && targetScore != -1){
                return TeamState.GAME_WON;
            }
            if(wickets < FiveOversGameRules.WICKETS && balls < NO_OF_BALLS){
                if( inputValidator( input )){
                    PlayerState playerState = currentBatter.bat();

                    if(playerState.equals(PlayerState.OUT)){
                        this.wickets += 1;
                        if(this.wickets == FiveOversGameRules.WICKETS){
                            this.allOut = true;
                            return TeamState.INNING_OVER;
                        }
                        currentBatter = selectNextBatter(currentBatter, players, nowPlaying);
                        currentBatter.setOut(true);
                        currentBatter.setStriking(false);
                    }

                    if(playerState.equals(PlayerState.SIDE_CHANGE)){
                        System.out.println("side changed");
                        score += currentBatter.getCurrentPlay();
                        currentBatter.setStriking(false);
                        currentBatter = changeTheSides(nowPlaying, currentBatter);
                        currentBatter.setStriking(true);
                    }

                    if(playerState.equals(PlayerState.STRIKING)){
                        score += currentBatter.getCurrentPlay();
                    }
                    System.out.println(name + " : "+ (score+1) + "/" + wickets + " (" + balls/ FiveOversGameRules.BALLS_PER_OVER + "."+ balls% FiveOversGameRules.BALLS_PER_OVER +" overs )");
                }else{
                    System.out.println("error");
                }
            }else{
                if(wickets == FiveOversGameRules.WICKETS){
                    this.allOut = true;
                    System.out.println(this.name+ " all out");
                }
                if(balls == FiveOversGameRules.BALLS_PER_OVER* FiveOversGameRules.OVERS){
                    this.oversFinished = true;
                    System.out.println(this.name+ " overs finished");

                }
                System.out.println(this.name + " inning is over: SCORE " + score);
                return TeamState.INNING_OVER;
            }
        }
        return TeamState.INNING_OVER;
    }

    //send two batsmen to the ground choose one as the opening batman
    private void initializeTeam(){
//        this.nowPlaying.clear();
//        this.currentBatter = null;
        this.nowPlaying.add(players.get(0));
        this.nowPlaying.add(players.get(1));
        this.currentBatter = players.get(0);
        this.currentBatter.setStriking(true);
    }

    //select next batter when the current batter is dismissed
    private Player selectNextBatter(Player dismissedPlayer, List<Player> teamPlayers, List<Player> nowPlaying){
        Player nextPlayer;

        //take the no of the nextBatter
        int next = Math.max(nowPlaying.get(0).getNo(), nowPlaying.get(1).getNo()) + 1;

        //remove the dismissed player from the nowPlaying
        nowPlaying.remove(dismissedPlayer);

        //add the next PLayer to the nowPlaying
        nextPlayer = teamPlayers.get(next);
        nowPlaying.add(nextPlayer);

        //return the nextBatter as the currentBatter
        System.out.println("next to bat: PLAYER "+ nextPlayer.getNo());
        return nextPlayer;
    }

    //change the sides when a Batter scores a odd number of runs
    private Player changeTheSides(List<Player> nowPlaying, Player currentBatter){
        Player sideChangedPlayer = null;
        for(Player p: nowPlaying){
            if(currentBatter.getNo() != p.getNo()){
                sideChangedPlayer = p;
            }
        }
        return sideChangedPlayer;
    }

    private boolean inputValidator(Scanner input){
        System.out.println();
        System.out.println("balling...., press B/b to bat");
        while(!input.nextLine().equalsIgnoreCase( "B" )){
            System.out.println("B/b for BAT");
            input.nextLine();
        }
        return true;
    }
}
