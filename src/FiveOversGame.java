import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FiveOversGame extends Game{

    private List<Team> teams;

    public FiveOversGame(){
        teams = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        createTeams( teams, input );
    }

    public List<Team> getTeams() {
        return teams;
    }

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

    public void start(){
        Team firstBattingTeam = teams.get(0);
        firstBattingTeam.play(-1);

        Team secondBattingTeam = teams.get(1);
        TeamState teamState = secondBattingTeam.play( firstBattingTeam.getScore() );

        System.out.println();
        System.out.println("team " + firstBattingTeam.getName() + " won the toss and selected to bat first");
        if(teamState.equals( TeamState.GAME_WON )){
            secondBattingTeam.setGameWon( true );
            System.out.println("team "+ secondBattingTeam.getName() + " WON BY " + (FiveOversGameRules.WICKETS - secondBattingTeam.getWickets()) + " WICKETS");
        }else{
            firstBattingTeam.setGameWon( true );
            System.out.println("team "+ firstBattingTeam.getName() + " WON BY " + (firstBattingTeam.getScore() - secondBattingTeam.getScore()) + " runs");
        }
        scoreboard();
    }

    private  void createTeams(List<Team> teams, Scanner scanner){
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

    private void scoreboard(){
        String formatPlayer =  "|%1$-10s|%2$-10s|\n";
        System.out.format( formatPlayer, "PLAYER", "SCORE");

        for(Team t: teams ){
            for (BattingPlayer p: t.getBattingPlayers()){
                String scoreDetails = p.getScore() + " (" + p.getNumberOfBallsFaced() + ") balls";
                System.out.format( formatPlayer, p.getNo(),scoreDetails);
            }
            System.out.println();
        }


    }


}
