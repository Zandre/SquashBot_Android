
package tournament_classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.regex.MatchResult;

public class TeamResult 
{
	int tournamentId,
		matchesWon, matchesLost, gamesWon, gamesLost, pointsWon, pointsLost, 
		winnerScore, loserScore;
	String gender, section, winningTeam, losingTeam, date, time, venue, court, matchArray;
	Calendar calendar = Calendar.getInstance();
	
	//CONSTRUCTOR
	
	public TeamResult(int tournamentId,
						String gender, String section, String winningTeam, String losingTeam, 
						String date, String time, String venue, String court, String matchArray,
						int matchesWon, int matchesLost, int gamesWon, int gamesLost, int pointsWon, int pointsLost,
						int winnerScore, int loserScore) 
	{
		this.tournamentId = tournamentId;
		
		this.gender = gender;
		this.section = section;
		this.winningTeam = winningTeam;
		this.losingTeam = losingTeam;
		
		this.date = date;
		this.time = time;
		this.venue = venue;
		this.court = court;
		
		this.matchesWon = matchesWon;
		this.matchesLost = matchesLost;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.pointsWon = pointsWon;
		this.pointsLost = pointsLost;

		this.winnerScore = winnerScore;
		this.loserScore = loserScore;

		this.matchArray = matchArray;
		
		setCalendar();
		ParseMatchResults();
	}
	

	public int TournamentID()
	{
		return tournamentId;
	}

	
	//DATE & TIME
	
	private void setCalendar()
	{
	    String stringDate = date;
	    String[] dateSegments = stringDate.split("-");
	    
	    String stringTime = time;
	    String[] timeSegments = stringTime.split(":");
	    
	    calendar.set(Integer.parseInt(dateSegments[0]), 
	    				Integer.parseInt(dateSegments[1]) - 1, 
	    				Integer.parseInt(dateSegments[2]),
	    				Integer.parseInt(timeSegments[0]),
	    				Integer.parseInt(timeSegments[1]),
	    				Integer.parseInt(timeSegments[2]));
	}
	
	public String FriendlyDate()
	{
		SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
		String _date = formatDate.format(calendar.getTime());
		return _date;
	}
	
	public String FriendlyTime()
	{
		SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a");
		String _time = formatTime.format(calendar.getTime());
		return _time;
	}
	
	
	//FIXTURE DETAILS
	
	public String Section()
	{
		return section;
	}
	
	public String Gender()
	{
		return gender;
	}

	public String VenueName()
	{
		return venue;
	}
	
	public String CourtNumber()
	{
		return court;
	}


	
	//FIXTURE STATS
	public int getMatchesPlayed()
	{
		return getMatchesWon() + getMatchesLost();
	}
	
	public int getMatchesWon()
	{
		return matchesWon;
	}
	
	public int getMatchesLost()
	{
		return matchesLost;
	}
	
	public int getGamesPlayed()
	{
		return getGamesWon() + getGamesLost();
	}
	
	public int getGamesWon()
	{
		return gamesWon;
	}
	
	public int getGamesLost()
	{
		return gamesLost;
	}
	
	public int getPointsPlayed()
	{
		return getPointsWon() + getPointsLost();
	}
	
	public int getPointsWon()
	{
		return pointsWon;
	}
	
	public int getPointsLost()
	{
		return pointsLost;
	}

	//WINNING TEAM ; LOSING TEAM
	public String getWinningTeam()
	{
		return winningTeam;
	}
	
	public String getLosingTeam()
	{
		return losingTeam;
	}
	
	public int getWinnerScore()
	{
		return winnerScore;
	}
	
	public int getLoserScore()
	{
		return loserScore;
	}

	private ArrayList<ResultForTeam> matches = new ArrayList<ResultForTeam>();
	public ArrayList<ResultForTeam> Matches()
	{
		return matches;
	}

	private void ParseMatchResults()
	{
		try
		{
			String TAG_NODES = "MatchArray";
			JSONObject jsnobject = new JSONObject(matchArray);
			JSONArray NodesArray = jsnobject.getJSONArray(TAG_NODES);

			for(int x = 0; x <= NodesArray.length()-1; x++)
			{
				JSONObject cursor = NodesArray.getJSONObject(x);

				String playerAName = cursor.getString("PlayerAName");
				String playerBName = cursor.getString("PlayerBName");
				String TeamAName = cursor.getString("TeamA");
				String TeamBName = cursor.getString("TeamB");
				String _playerATeamId = cursor.getString("PlayerATeamId");
				int playerATeamId = Integer.parseInt(_playerATeamId);
				String _playerBTeamId = cursor.getString("PlayerBTeamId");
				int playerBTeamId = Integer.parseInt(_playerBTeamId);
				String _ordinal = cursor.getString("Ordinal");
				int ordinal = Integer.parseInt(_ordinal);


				String GAME_NODES = "GameArray";
				String gamesArray = cursor.getString("GamesArray");
				JSONObject jsonGames = new JSONObject(gamesArray);
				JSONArray gamesArrayJSON = jsonGames.getJSONArray(GAME_NODES);

				ArrayList<GameResult> games = new ArrayList<GameResult>();

				for(int z = 0; z <= gamesArrayJSON.length()-1; z++)
				{
					JSONObject gamesCursor = gamesArrayJSON.getJSONObject(z);

					String _playerAPoints = gamesCursor.getString("PlayerAPoints");
					int playerAPoints = Integer.parseInt(_playerAPoints);
					String _playerBPoints = gamesCursor.getString("PlayerBPoints");
					int playerBPoints = Integer.parseInt(_playerBPoints);
					String _Ordinal = gamesCursor.getString("Ordinal");
					int Ordinal = Integer.parseInt(_Ordinal);

					GameResult gameResult = new GameResult(playerAPoints, playerBPoints, Ordinal);
					games.add(gameResult);
				}

				ResultForTeam matchResult = new ResultForTeam(playerAName, playerBName, playerATeamId, playerBTeamId, games, ordinal);
				matchResult.SetTeamAName(TeamAName);
				matchResult.SetTeamBName(TeamBName);
				matches.add(matchResult);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public String Result_SquashBotFormat()
	{
		String results = "";
		results += "\n\n";
		for (ResultForTeam match : Matches())
		{
			int gamesWonA = 0;
			int gamesWonB = 0;
			for (GameResult game: match.GameResults())
			{
				if(game.PlayerAPoints() > game.PlayerBPoints())
				{
					gamesWonA++;
				}
				else
				{
					gamesWonB++;
				}
			}

			if(gamesWonA > gamesWonB)
			{
				results += match.PlayerA() + " (" + match.TeamA_Name() + ") beat " + match.PlayerB() + " (" + match.TeamB_Name() + ") " + gamesWonA + " - " + gamesWonB + "\n";
				for(GameResult game : match.GameResults())
				{
					results += game.PlayerAPoints() + "-" + game.PlayerBPoints() + ", ";
				}
			}
			else
			{
				results += match.PlayerB() + " (" + match.TeamB_Name() + ") beat " + match.PlayerA() + " (" + match.TeamA_Name() + ") " + gamesWonB + " - " + gamesWonA + "\n";
				for(GameResult game : match.GameResults())
				{
					results += game.PlayerBPoints() + "-" + game.PlayerAPoints() + ", ";
				}
			}

			results = results.substring(0, results.length() - 2);
			results += "\n\n";
		}
		return results;
	}

	public String Format1_ResultOnly()
	{
		String results = "";
		results += "\n\n";
		for (ResultForTeam match : Matches())
		{
				int gamesWonA = 0;
				int gamesWonB = 0;
				for (GameResult game: match.GameResults())
				{
					if(game.PlayerAPoints() > game.PlayerBPoints())
					{
						gamesWonA++;
					}
					else
					{
						gamesWonB++;
					}
				}

				if(gamesWonA > gamesWonB)
				{
					results += match.PlayerA() + " (" + match.TeamA_Name() + ") beat " + match.PlayerB() + " (" + match.TeamB_Name() + ") " + gamesWonA + " - " + gamesWonB + "\n";
				}
				else
				{
					results += match.PlayerB() + " (" + match.TeamB_Name() + ") beat " + match.PlayerA() + " (" + match.TeamA_Name() + ") " + gamesWonB + " - " + gamesWonA + "\n";
				}

				results += "\n\n";
		}
		return results;
	}

	public String Format2_ScoresOnly()
	{
		String results = "";
		results += "\n\n";
		for (ResultForTeam match : Matches())
		{
			int gamesWonA = 0;
			int gamesWonB = 0;
			for (GameResult game: match.GameResults())
			{
				if(game.PlayerAPoints() > game.PlayerBPoints())
				{
					gamesWonA++;
				}
				else
				{
					gamesWonB++;
				}
			}

			if(gamesWonA > gamesWonB)
			{
				results += match.PlayerA() + " (" + match.TeamA_Name() + ") beat " + match.PlayerB() + " (" + match.TeamB_Name() + ")\n";
				for(GameResult game : match.GameResults())
				{
					results += game.PlayerAPoints() + "-" + game.PlayerBPoints() + ", ";
				}
			}
			else
			{
				results += match.PlayerB() + " (" + match.TeamB_Name() + ") beat " + match.PlayerA() + " (" + match.TeamA_Name() + ")\n";
				for(GameResult game : match.GameResults())
				{
					results += game.PlayerBPoints() + "-" + game.PlayerAPoints() + ", ";
				}
			}
			results = results.substring(0, results.length() - 2);
			results += "\n\n";
		}
		return results;
	}

	private ArrayList<GameResult> gameResults = new ArrayList<GameResult>();
}

