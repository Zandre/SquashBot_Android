package tournament_classes;

import java.text.DecimalFormat;

import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable
{
	String team, gender, section, pool;
	int teamId, tournamentId, 
	log_points, 
	fixtures_won, fixtures_lost, 
	matches_won, matches_lost, 
	games_won, games_lost, 
	points_won, points_lost;
	
	//CONSTRUCTOR

	public Log(int id, int tournamentId, String team, String gender, String section, String pool)
	{
		this.teamId = id;
		this.tournamentId = tournamentId;

		this.team = team;
		this.gender = gender;
		this.section = section;
		this.pool = pool;
	}

	public Log(int id, int tournamentId, String team, String gender, String section, String pool, 
				int log_points, 
				int fixtures_won, int fixtures_lost,
				int matches_won, int matches_lost,
				int games_won, int games_lost,
				int points_won, int points_lost) 
	{
		this.teamId = id;
		this.tournamentId = tournamentId;
		
		this.team = team;
		this.gender = gender;
		this.section = section;
		this.pool = pool;
		
		this.log_points = log_points;
		
		this.fixtures_won = fixtures_won;
		this.fixtures_lost = fixtures_lost;
		
		this.matches_won = matches_won;
		this.matches_lost = matches_lost;
		
		this.games_won = games_won;
		this.games_lost = games_lost;
		
		this.points_won = points_won;
		this.points_lost = points_lost;
	}
	
	//ID's
	public int ID()
	{
		return teamId;
	}
	
	public int TournamentID()
	{
		return tournamentId;
	}
	
	
	
	
	public String TeamName()
	{
		return team;
	}
	
	public String Section()
	{
		return section;
	}
	
	public String Pool()
	{
		return pool;
	}
	
	public String Gender()
	{
		return gender;
	}
	
	public int LogPoints()
	{
		return log_points;
	}
	
	
	
	
	//FIXTURES
	public int getFixturesPlayed()
	{
		return getFixturesWon() + getFixturesLost();
	}
	
	public int getFixturesWon()
	{
		return fixtures_won;
	}
	
	public int getFixturesLost()
	{
		return fixtures_lost;
	}
	
	public String FixturesPercentage()
	{
		if(getFixturesPlayed() > 0)
		{
			float fixturesPercentage = (getFixturesWon() * 100.0f) / getFixturesPlayed();
			return DecimalFormat(fixturesPercentage);
		}
		String val = "";
		return val;
	}
	
	//MATCHES
	public int getMatchesPlayed()
	{
		return getMatchesWon() + getMatchesLost();
	}
	
	public int getMatchesWon()
	{
		return matches_won;
	}
	
	public int getMatchesLost()
	{
		return matches_lost;
	}
	
	public String MatchesPercentage()
	{
		if(getMatchesPlayed() > 0)
		{
			float mathesPercentage = (getMatchesWon() * 100.0f) / getMatchesPlayed(); 
			return DecimalFormat(mathesPercentage);
		}
		String val = "";
		return val;
	}
	
	//GAMES
 	public int getGamesPlayed()
	{
		return getGamesWon() + getGamesLost();
	}
	
	public int getGamesWon()
	{
		return games_won;
	}
	
	public int getGamesLost()
	{
		return games_lost;
	}

	public String GamesPercentage()
	{
		if(getGamesPlayed() > 0)
		{
			float gamesPercentage = (getGamesWon() * 100.0f) / getGamesPlayed();
			return DecimalFormat(gamesPercentage);
		}
		String val = "";
		return val;
	}
	
	//POINTS
 	public int getPointsPlayed()
	{
		return getPointsWon() + getPointsLost();
	}
	
	public int getPointsWon()
	{
		return points_won;
	}
	
	public int getPointsLost()
	{
		return points_lost;
	}

	public String PointsPercentage()
	{
		if(getPointsPlayed() > 0)
		{
			float pointsPercentage = (getPointsWon() * 100.0f) / getPointsPlayed();
			return DecimalFormat(pointsPercentage);
		}
		String val = "";
		return val;
	}
	
	//Decimal Method - return string with 2 spaces after decimal	
	public String DecimalFormat(float value)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return df.format(value);
	}
	
	
	//PARCELABLE STUFF
	public Log(Parcel in)
	{
		this.teamId = in.readInt();
		this.tournamentId = in.readInt();
		
		this.team = in.readString();
		this.gender = in.readString();
		this.section = in.readString();
		this.pool = in.readString();
		
		this.log_points = in.readInt();
		this.fixtures_won = in.readInt();
		this.fixtures_lost = in.readInt();
		this.games_won = in.readInt();
		this.games_lost = in.readInt();
		this.points_won = in.readInt();
		this.points_lost = in.readInt();
	}
	
	@Override
	public int describeContents() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeInt(teamId);
		dest.writeInt(tournamentId);
		
		dest.writeString(team);
		dest.writeString(gender);
		dest.writeString(section);
		dest.writeString(pool);
		
		dest.writeInt(log_points);
		dest.writeInt(fixtures_won);
		dest.writeInt(fixtures_lost);
		dest.writeInt(games_won);
		dest.writeInt(games_lost);
		dest.writeInt(points_won);
		dest.writeInt(points_lost);
	}
	
	public static final Parcelable.Creator<Log> CREATOR = new Parcelable.Creator<Log>()
	{
		@Override
		public Log createFromParcel(Parcel source)
		{
			return new Log(source);
		}

		@Override
		public Log[] newArray(int size) 
		{
			return null;
		}
	};
	
}
