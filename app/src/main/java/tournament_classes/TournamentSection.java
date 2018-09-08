package tournament_classes;

import android.os.Parcel;
import android.os.Parcelable;

public class TournamentSection implements Parcelable
{
	int tournamentId, gamePar, playersPerTeam, scoringMethod;
	String section, gender;
	
	public TournamentSection(int tournamentId, String gender, String section, int gamePar, int playersPerTeam, int scoringMethod)
	{
		this.tournamentId = tournamentId;
		this.gender = gender;
		this.section = section;	
		this.gamePar = gamePar;
		this.playersPerTeam = playersPerTeam;
		this.scoringMethod = scoringMethod;
	}
	
	public TournamentSection()
	{
		
	}
	
 	public int TournamentID()
	{
		return tournamentId;
	}
	
	public String Gender()
	{
		return gender;
	}
	
	public String Section()
	{
		return section;
	}

	public int GamePar()
	{
		return gamePar;
	}

	public int PlayersPerTeam()
	{
		return playersPerTeam;
	}

	public int ScoringMethod()
	{
		return scoringMethod;
	}
	
	//PARCELABLE STUFF
	public TournamentSection(Parcel in) 
	{
		this.tournamentId = in.readInt();
		this.section = in.readString();
		this.gender = in.readString();
		this.gamePar = in.readInt();
		this.playersPerTeam = in.readInt();
		this.scoringMethod = in.readInt();
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
		dest.writeInt(tournamentId);
		dest.writeString(section);
		dest.writeString(gender);
		dest.writeInt(gamePar);
		dest.writeInt(playersPerTeam);
		dest.writeInt(scoringMethod);
	}
	
	public static final Parcelable.Creator<TournamentSection> CREATOR = new Parcelable.Creator<TournamentSection>()
	{
		@Override
		public TournamentSection createFromParcel(Parcel source)
		{
			return new TournamentSection(source);
		}

		@Override
		public TournamentSection[] newArray(int size) 
		{
			return null;
		}
	};
}
