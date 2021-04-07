package models;

import controllers.FiveOversGameRules;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Team {
    private String name;
    private List<BattingPlayer> players = new ArrayList<>();
    private int score;
    private int wickets;
    private int balls;
    private  BattingPlayer currentBatter;
    private List<BattingPlayer> nowPlaying = new ArrayList<>();
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
            players.add(new BattingPlayer(i));
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
    public List<BattingPlayer> getBattingPlayers() {
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
                    BattingPlayerState playerState = currentBatter.bat();

                    if(playerState.equals(BattingPlayerState.OUT)){
                        wickets += 1;
                        if(wickets == FiveOversGameRules.WICKETS){
                            this.allOut = true;
                            return TeamState.INNING_OVER;
                        }
                        currentBatter = selectNextBatter(currentBatter, players, nowPlaying);
                        currentBatter.setOut(true);
                        currentBatter.setStriking(false);
                    }

                    if(playerState.equals(BattingPlayerState.SIDE_CHANGE)){
                        score += currentBatter.getCurrentPlay();
                        currentBatter.setStriking(false);
                        currentBatter = changeTheSides(nowPlaying, currentBatter);
                        currentBatter.setStriking(true);
                    }

                    if(playerState.equals(BattingPlayerState.STRIKING)){
                        score += currentBatter.getCurrentPlay();
                    }
                    System.out.println(name + " : "+ (score+1) + "/" + wickets + " (" + balls/ FiveOversGameRules.BALLS_PER_OVER + "."+ balls% FiveOversGameRules.BALLS_PER_OVER +" overs )");
                }else{
                    System.out.println("error");
                }
            }else{
                if(wickets == FiveOversGameRules.WICKETS){
                    this.allOut = true;
                    System.out.println(name+ " all out");
                }
                if(balls == FiveOversGameRules.BALLS_PER_OVER* FiveOversGameRules.OVERS){
                    this.oversFinished = true;
                    System.out.println(name+ " overs finished");

                }
                System.out.println(name + " inning is over: SCORE " + score);
                return TeamState.INNING_OVER;
            }
        }
        return TeamState.INNING_OVER;
    }

    //send two batsmen to the ground choose one as the opening batman
    private void initializeTeam(){
        this.nowPlaying.clear();
        this.currentBatter = null;
        this.nowPlaying.add(players.get(0));
        this.nowPlaying.add(players.get(1));
        this.currentBatter = players.get(0);
        this.currentBatter.setStriking(true);
    }

    //select next batter when the current batter is dismissed
    private BattingPlayer selectNextBatter(BattingPlayer dismissedBattingPlayer, List<BattingPlayer> teamBattingPlayers, List<BattingPlayer> nowPlaying){
        BattingPlayer nextBattingPlayer;

        //take the no of the nextBatter
        int next = Math.max(nowPlaying.get(0).getNo(), nowPlaying.get(1).getNo()) + 1;

        //remove the dismissed player from the nowPlaying
        nowPlaying.remove(dismissedBattingPlayer);

        //add the next PLayer to the nowPlaying
        nextBattingPlayer = teamBattingPlayers.get(next);
        nowPlaying.add(nextBattingPlayer);

        //return the nextBatter as the currentBatter
        System.out.println("next to bat: PLAYER "+ nextBattingPlayer.getNo());
        return nextBattingPlayer;
    }

    //change the sides when a Batter scores a odd number of runs
    private BattingPlayer changeTheSides(List<BattingPlayer> nowPlaying, BattingPlayer currentBatter){
        BattingPlayer sideChangedBattingPlayer = null;
        for(BattingPlayer p: nowPlaying){
            if(currentBatter.getNo() != p.getNo()){
                sideChangedBattingPlayer = p;
            }
        }
        return sideChangedBattingPlayer;
    }

    private boolean inputValidator(Scanner input){
        System.out.println();
        System.out.println("balling...., press B to bat");
        while(!input.nextLine().equalsIgnoreCase( "B" )){
            System.out.println("B for BAT");
            input.nextLine();
        }
        return true;
    }
}
