package classes;

import display_notification.BlueToast;
import android.content.Context;

public class SquashMatch 
{
	int gamePar, setsRequired;
	Context context;
	public SquashMatch(int gamePar, int setsRequired, Context context) 
	{
		this.gamePar = gamePar;
		this.setsRequired = setsRequired;
		this.context = context;
	}
	
	public Boolean GameIsWon(Player rally_winner, Player rally_loser)
	{
		boolean game_is_won = false;
		if((rally_winner.getScore()-1) > rally_loser.getScore() && 
				(rally_winner.getScore() >= gamePar))
		{
			game_is_won = true;
		}
		return game_is_won;
	}
	
	public Boolean MatchIsWon(Player rally_winner)
	{
		boolean match_is_won = false;
		if(rally_winner.getGameTotal() == setsRequired)
		{
			match_is_won = true;
		}
		return match_is_won;
	}

	public void isMatchBall(Player rally_winner, Player rally_loser)
	{
		if((rally_winner.getGameTotal() == setsRequired -1) &&
					(rally_winner.getScore() == gamePar -1) &&
					(rally_winner.getScore() != rally_loser.getScore()))
		{
			//Display Toast "Match Ball"
			DisplayToast(MatchState.MatchBall);
		}
		else if((rally_winner.getGameTotal() == setsRequired -1) &&
					((rally_winner.getScore() >= gamePar -1) && (rally_loser.getScore() >= gamePar -1)) &&
					(rally_winner.getScore() != rally_loser.getScore()))
		{
			if(rally_winner.getScore() - rally_loser.getScore() == 1)
			{
				//Display Toast "Match Ball"
				DisplayToast(MatchState.MatchBall);
			}
		}
		else
		{
			//Check if it is game ball
			isGameBall(rally_winner, rally_loser);
		}
	}
	
	public void isGameBall(Player rally_winner, Player rally_loser)
	{
		if((rally_winner.getScore() == gamePar -1) && (rally_winner.getScore() != rally_loser.getScore()))
		{
			//Display Toast "Game Ball"
			DisplayToast(MatchState.GameBall);
		}
		else if(((rally_winner.getScore() >= gamePar -1) && (rally_loser.getScore() >= gamePar -1)) && (rally_winner.getScore() != rally_loser.getScore()))
		{
			if(rally_winner.getScore() - rally_loser.getScore() == 1)
			{
				//Display Toast "Game Ball"
				DisplayToast(MatchState.GameBall);
			}
		}
	}
	
	public enum MatchState
	{
		GameBall,
		MatchBall;
	}
	
	public void DisplayToast(MatchState matchState)
	{
		BlueToast blueToast = new BlueToast(context);
		if(matchState == MatchState.GameBall)
		{
			blueToast.GameBall();
		}
		else if(matchState == MatchState.MatchBall)
		{
			blueToast.MatchBall();
		}
	}
	
}
