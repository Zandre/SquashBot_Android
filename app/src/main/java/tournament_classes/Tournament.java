package tournament_classes;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Tournament implements Parcelable
{

	String tournamentName, adminPassword, casualPassword, namingConvention;
	int id;
	ArrayList<TournamentSection> sections = new ArrayList<TournamentSection>();
	boolean active;
	
	
	//CONTRUCTOR
	
	public Tournament(int id, String tournamentName, String adminPassword, String clubPassword, boolean active, String namingConvention)
	{
		this.id = id;
		this.tournamentName = tournamentName;
		this.adminPassword = adminPassword;
		this.casualPassword = clubPassword;
		this.active = active;
		this.namingConvention = namingConvention;
	}

	
	//ID'S
	
	public int ID()
	{
		return id;
	}
	
	
	//TOURNAMENT DETAILS
	
	public String TournamentName()
	{
		return tournamentName;
	}
	
	public String AdminPassword()
	{
		return adminPassword;
	}
	
	public String CasualPassword()
	{
		return casualPassword;
	}

	public boolean isActive()
	{
		return active;
	}
	
	public String NamingConvention()
	{
		return namingConvention;
	}
	
	public void setTournamentSections(ArrayList<TournamentSection> Sections)
	{
		sections = Sections;
	}
	
	public ArrayList<TournamentSection> getTournamentSections()
	{
		return sections;
	}
	
	public void addTournamentSection(TournamentSection section)
	{
		sections.add(section);
	}
	

	//PARCELABLE STUFF
 	public Tournament(Parcel in)
	{
		this.id = in.readInt();
		this.tournamentName = in.readString();
		this.adminPassword = in.readString();
		this.casualPassword = in.readString();
		//this.playersPerTeam =in.readInt();
		this.active = in.readByte() != 0;
		this.sections = in.readArrayList(TournamentSection.class.getClassLoader());
		this.namingConvention = in.readString();
		//this.scoringMethod = in.readInt();
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
		dest.writeInt(id);
		dest.writeString(tournamentName);
		dest.writeString(adminPassword);
		dest.writeString(casualPassword);
		//dest.writeInt(playersPerTeam);
		dest.writeByte((byte)(active ? 1 :0));
		dest.writeList(sections);
		dest.writeString(namingConvention);
		//dest.writeInt(scoringMethod);
	}
	
	
	public static final Parcelable.Creator<Tournament> CREATOR = new Parcelable.Creator<Tournament>()
	{
		@Override
		public Tournament createFromParcel(Parcel source)
		{
			return new Tournament(source);
		}

		@Override
		public Tournament[] newArray(int size) 
		{
			return null;
		}
	};
	

}
