package tournament_classes;

import java.io.Serializable;
import java.util.ArrayList;

import classes.EnumTypes;
import tournament_activities.MatchResult.MatchResultEnum;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import asynchronious_classes.getTeamsFromFixture;

public class TeamResultHelper implements Parcelable
{
	private Fixture fixture;
	private Tournament tournament;
	private MatchResultEnum type;
	private ArrayList<ResultForTeam> individualResults = new ArrayList<ResultForTeam>();
	private int index, winnerFinalScore, loserFinalScore, teamA_Id, teamB_Id, winningTeamId, losingTeamId;
	
	public TeamResultHelper(Fixture fixture, Tournament tournament)
	{
		this.fixture = fixture;
		this.tournament = tournament;
		index = 1;
	}
	
	public Fixture Fixture()
	{
		return fixture;
	}
	
	public Tournament Tournament()
	{
		return tournament;
	}

	public ArrayList<ResultForTeam> IndividualResults()
	{
		return individualResults;
	}
	
	public void ReplaceIndividualResult(ResultForTeam result)
	{	
		individualResults.remove(Index()-1);
		individualResults.add(Index()-1, result);
	}
	
 	public void AddResult(ResultForTeam result)
	{
		individualResults.add(result);
	}

	public void GetTeams(Context context)
	{
		new getTeamsFromFixture(context, TeamA_ID(), TeamB_ID()).execute();
	}

	public int TeamA_ID()
	{
		return teamA_Id;
	}

	public void SetTeamA_ID(int id)
	{
		teamA_Id = id;
	}

	public int TeamB_ID()
	{
		return teamB_Id;
	}

	public void SetTeamB_ID(int id)
	{
		teamB_Id = id;
	}

	public void setIndex(int _index)
	{
		index = _index;
	}
	
	public void IncrementIndex()
	{
		index++;
	}
	
	public void DecrementIndex()
	{
		index--;
	}
	
	public int Index()
	{
		return index;
	}

	public void setType(MatchResultEnum Type)
	{
		type = Type;
	}
	
	public MatchResultEnum Type()
	{
		return type;
	}

	public void SetWinningTeamId(int id, int score)
	{
		winningTeamId = id;
		winnerFinalScore = score;
	}

	public int WinningTeamId()
	{
		return winningTeamId;
	}

	public int WinnerFinalScore()
	{
		return winnerFinalScore;
	}

	public void SetLosingTeamId(int id, int score)
	{
		losingTeamId = id;
		loserFinalScore = score;
	}

	public int LosingTeamId()
	{
		return losingTeamId;
	}

	public int LoserFinalScore()
	{
		return loserFinalScore;
	}
	

	//PARCELABLE STUFF
	public TeamResultHelper(Parcel in)
	{
		this.type = (MatchResultEnum) in.readSerializable();
		this.index = in.readInt();
		this.teamA_Id = in.readInt();
		this.teamB_Id = in.readInt();
		this.fixture = (Fixture) in.readSerializable();
		this.tournament = in.readParcelable(Tournament.class.getClassLoader());
		this.individualResults = in.createTypedArrayList(ResultForTeam.CREATOR);
		this.winnerFinalScore = in.readInt();
		this.loserFinalScore = in.readInt();
		this.winningTeamId = in.readInt();
		this.losingTeamId = in.readInt();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeSerializable((Serializable) type);
		dest.writeInt(index);
		dest.writeInt(teamA_Id);
		dest.writeInt(teamB_Id);
		dest.writeSerializable((Serializable) fixture);
		dest.writeParcelable(tournament, flags);
		dest.writeTypedList(individualResults);
		dest.writeInt(winnerFinalScore);
		dest.writeInt(loserFinalScore);
		dest.writeInt(winningTeamId);
		dest.writeInt(losingTeamId);
	}


	public static final Parcelable.Creator<TeamResultHelper> CREATOR = new Parcelable.Creator<TeamResultHelper>()
	{

		@Override
		public TeamResultHelper createFromParcel(Parcel source)
		{
			return new TeamResultHelper(source);
		}

		@Override
		public TeamResultHelper[] newArray(int size)
		{
			return null;
		}
		
	};
}
