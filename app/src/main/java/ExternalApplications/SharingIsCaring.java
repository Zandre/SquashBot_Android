package ExternalApplications;

import java.util.ArrayList;

import tournament_activities.TournamentDashboard;
import tournament_classes.Fixture;
import tournament_classes.InformationBoard;
import tournament_classes.Log;
import tournament_classes.TeamResult;
import android.content.Context;
import android.content.Intent;
import classes.FriendlyCalendar;
import classes.RankedPlayer;
import classes.Result;

public class SharingIsCaring 
{
	Context context;
	
	public SharingIsCaring(Context context)
	{
		this.context = context;
	}
	
	public void ShareRankings(ArrayList<RankedPlayer> players, String clubName, String gender)
	{
		Intent sharing = new Intent();
		sharing.setAction(Intent.ACTION_SEND);
		
		String message = "SQUASHBOT RANKINGS\n\n";
		message += clubName + "  -  "+gender+"\n\n";
		
		for(int x = 0; x < players.size(); x++)
		{
			message += (x+1) + ".  "+players.get(x).getName()+"  ";
			
			if(players.get(x).getClub().length() > 0)
			{
				message += "("+players.get(x).getClub()+")  ";
			}
			
			message += players.get(x).getPoints()+"\n";	
		}
		
		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");
		context.startActivity(Intent.createChooser(sharing, "Share log with"));
	}

	public void ShareIndividualResults(ArrayList<Result> results)
	{
		Intent sharing = new Intent();
		sharing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharing.setAction(Intent.ACTION_SEND);
		
		String message = "SQUASHBOT RESULTS\n\n";
		for(int x = 0; x < results.size(); x++)
		{
		    String scores = results.get(x).getScores();
		    scores = scores.replaceAll("[^a-zA-Z0-9]+$", "");
		    scores = "("+scores+")";
			
			message += results.get(x).getWinner()+" beat "
								+results.get(x).getLoser()+
								" "+results.get(x).getResult()+
								" "+scores+
								" The match took place at "+results.get(x).getVenue()+
								" on "+results.get(x).FriendlyDate()+".\n\n";							
		}
		
		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");
				
		context.startActivity(Intent.createChooser(sharing, "Share result with"));
	}
	
	public void ShareLog(ArrayList<Log> log)
	{
		Intent sharing = new Intent();
		sharing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharing.setAction(Intent.ACTION_SEND);
		
		String message = "SQUASHBOT LOG\n\n";
		
		FriendlyCalendar date = new FriendlyCalendar();
		message += date.getFriendlyCurrentDate()+"\n\n";
		
		message += TournamentDashboard.selectedTournament.TournamentName()+"\n";
		message += log.get(0).Gender()+", "+log.get(0).Section()+ " "+ TournamentDashboard.selectedTournament.NamingConvention() +"\n\n";
		
		for(int x = 0; x < log.size(); x++)
		{
				message += (x+1)+") ";
				message += log.get(x).LogPoints()+" "+log.get(x).TeamName()+" ";
				message += "(P:"+log.get(x).getFixturesPlayed()+"/";
				message += "W:"+log.get(x).getFixturesWon()+"/";
				message += "L:"+log.get(x).getFixturesLost()+")\n";
		}
		
		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");
				
		context.startActivity(Intent.createChooser(sharing, "Share log with"));
	}

	public void ShareFixtures(ArrayList<Fixture> fixtures)
	{
		Intent sharing = new Intent();
		sharing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharing.setAction(Intent.ACTION_SEND);
		
		String message = "SQUASHBOT FIXTURES\n\n";
		
		message += TournamentDashboard.selectedTournament.TournamentName()+"\n";
		message += fixtures.get(0).Gender()+", "+fixtures.get(0).Section()+ " "+ TournamentDashboard.selectedTournament.NamingConvention() +"\n\n";
		
		for(int x = 0; x < fixtures.size(); x++)
		{
			message += fixtures.get(x).TeamA_Name() + " vs " + fixtures.get(x).TeamB_Name()+"\n";
			message += fixtures.get(x).FriendlyDate()+", "+fixtures.get(x).FriendlyTime()+"\n";
			message += fixtures.get(x).VenueName() +", Court "+fixtures.get(x).CourtNumber()+"\n\n";
		}
		
		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");
				
		context.startActivity(Intent.createChooser(sharing, "Share fixtures with"));
	}

	public void ShareTeamResult(ArrayList<TeamResult> results, ShareFormat format)
	{
		Intent sharing = new Intent();
		sharing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharing.setAction(Intent.ACTION_SEND);
		
		String message = "SQUASHBOT TEAM RESULTS\n\n";
		
		message += TournamentDashboard.selectedTournament.TournamentName()+"\n";
		message += results.get(0).Gender() + ", " + results.get(0).Section() + " " + TournamentDashboard.selectedTournament.NamingConvention()+"\n\n\n";

		for(int x = 0; x < results.size(); x++)
		{
			message += results.get(x).FriendlyDate() +", "+results.get(x).FriendlyTime()+"\n";
			message += "Venue: "+results.get(x).VenueName() +", Court"+results.get(x).CourtNumber()+"\n";
			message += results.get(x).getWinningTeam() + " beat " + results.get(x).getLosingTeam() + "\n";
			message += "FINAL SCORE: "+results.get(x).getWinnerScore() + " - " + results.get(x).getLoserScore() + "\n\n";
			if(format == ShareFormat.SquashBotFormat)
			{
				message += results.get(x).Result_SquashBotFormat()+"\n\n\n";
			}
			else if(format == ShareFormat.ResultOnly)
			{
				message += results.get(x).Format1_ResultOnly()+"\n\n\n";
			}
			else if(format == ShareFormat.ScoresOnly)
			{
				message += results.get(x).Format2_ScoresOnly()+"\n\n\n";
			}
		}
		
		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");
				
		context.startActivity(Intent.createChooser(sharing, "Share results with"));
	}

	public void ShareInformationBoard(InformationBoard Message)
	{
		Intent sharing = new Intent();
		sharing.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharing.setAction(Intent.ACTION_SEND);

		String message = "SQUASHBOT INFORMATION BOARD\n\n";
		message += Message.Title()+"\n";
		message += Message.FriendlyDate()+"\n\n";
		message += Message.Message()+"\n";
		message += Message.Author();

		sharing.putExtra(Intent.EXTRA_TEXT, message);
		sharing.setType("text/plain");

		context.startActivity(Intent.createChooser(sharing, "Share results with"));
	}

	public enum ShareFormat
	{
		SquashBotFormat,
		ResultOnly,
		ScoresOnly
	}
}
