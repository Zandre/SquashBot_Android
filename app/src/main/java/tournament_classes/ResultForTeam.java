package tournament_classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class ResultForTeam implements Parcelable
{

	private String playerA, playerB;
	private int ordinal, teamA_ID, teamB_ID;
	private ArrayList<GameResult> gameResults = new ArrayList<GameResult>();
	
	public ResultForTeam(String playerA,
						 	String playerB,
							int teamA_ID,
						 	int teamB_ID,
						 	ArrayList<GameResult> gameResults,
						 	int ordinal)
	{
		this.playerA = playerA;
		this.playerB = playerB;
		this.teamA_ID = teamA_ID;
		this.teamB_ID = teamB_ID;
		this.gameResults = gameResults;
		this.ordinal = ordinal;
	}

	public String PlayerA()
	{
		return playerA.trim();
	}
	
	public String PlayerB()
	{
		return playerB.trim();
	}

	private String teamA = "";
	public void SetTeamAName(String Team)
	{
		teamA = Team;
	}

	public String TeamA_Name()
	{
		return teamA;
	}

	private String teamB= "";
	public void SetTeamBName(String Team)
	{
		teamB = Team;
	}

	public String TeamB_Name()
	{
		return teamB;
	}


	public ArrayList<GameResult> GameResults()
	{
		return gameResults;
	}


	//Parcel Class
	public ResultForTeam(Parcel in) 
	{
		this.playerA = in.readString();
		this.playerB = in.readString();
		this.ordinal = in.readInt();
		this.teamA_ID = in.readInt();
		this.teamB_ID = in.readInt();
		this.gameResults = in.createTypedArrayList(GameResult.CREATOR);
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(playerA);
		dest.writeString(playerB);
		dest.writeInt(ordinal);
		dest.writeInt(teamA_ID);
		dest.writeInt(teamB_ID);
		dest.writeTypedList(gameResults);
	}

	public static final Parcelable.Creator<ResultForTeam> CREATOR = new Parcelable.Creator<ResultForTeam>()
	{
		@Override
		public ResultForTeam createFromParcel(Parcel source)
		{
			return new ResultForTeam(source);
		}

		@Override
		public ResultForTeam[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
