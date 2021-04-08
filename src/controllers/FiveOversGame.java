package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import models.BattingPlayer;
import models.ScoreBoard;
import models.Team;
import models.TeamState;

public class FiveOversGame implements Game {

    private List<Team> teams;
    private ScoreBoard scoreBoard;

    public FiveOversGame(){
        teams = new ArrayList<>();
        scoreBoard = new ScoreBoard();
        Scanner input = new Scanner(System.in);
        createTeams( teams, input );
    }

    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void toss(){
        //generate 0 or 1 from random number
        int toss = new Random().nextInt(2);
        //select the team from the list
        Team wonTossTeam = (Team) teams.get(toss);
        //set the toss won team to as the first element in the list
        if(toss == 1){
            teams.add( 0, wonTossTeam );
            teams.remove(2);
        }
        System.out.println("team "+ wonTossTeam.getName()+ " won the toss");
    }


    @Override
    public void start(){
        Team firstBattingTeam = teams.get(0);
        firstBattingTeam.play(-1);
        Team secondBattingTeam = teams.get(1);
        TeamState teamState = secondBattingTeam.play( firstBattingTeam.getScore() );
        scoreBoard.showGameSummary( firstBattingTeam, secondBattingTeam,teamState );
        scoreBoard.showScoreBoard(teams);
    }

    private void createTeams(List<Team> teams, Scanner scanner){
        for(int i= 0; i < 2; i++){
            System.out.println("team "+ (i+1) + " name:");
            String input = scanner.nextLine();
            while(input == null || input.isEmpty()){
                System.out.println("error");
                input = scanner.nextLine();
            }
            Team team = new Team(input);
            teams.add(team);
        }
    };




}
