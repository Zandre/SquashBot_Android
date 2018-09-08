package tournament_classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fixture implements Serializable
{
	int id, tournamentId, teamA_Id, teamB_Id, venueId, sectionId, gamePar, scoringMethod, playersPerTeam;
	String gender, section, teamA_name, teamB_name, date, time, venue, court;
	boolean resultSubmitted, knockoutFixture;
	
	Calendar calendar = Calendar.getInstance();
	
	
	//CONSTRUCTOR
	
	public Fixture(int id, int tournamentId, 
					String gender, String section, 
					String team1, String team2, 
					int teamA_Id, int teamB_Id,
					String date, String time, String venue, String court,
					boolean resultSubmitted, boolean knockoutFixture, 
					int venueId, int sectionId, int gamePar, int scoringMethod, int playersPerTeam)
	{
		this.id = id;
		this.tournamentId = tournamentId;
		
		this.gender = gender;
		this.section = section;
		
		this.teamA_name = team1;
		this.teamA_Id = teamA_Id;
		this.teamB_name = team2;
		this.teamB_Id = teamB_Id;
		
		this.date = date;
		this.time = time;
		this.venue = venue;
		this.court = court;
		
		this.resultSubmitted = resultSubmitted;
		this.knockoutFixture = knockoutFixture;
		
		this.venueId = venueId;
		this.sectionId = sectionId;
		
		this.gamePar = gamePar;
		this.scoringMethod = scoringMethod;
		this.playersPerTeam = playersPerTeam;
		setCalendar();
	}
	
	
	//ID's
	
	public int ID()
	{
		return id;
	}
	
	public int TournamentID()
	{
		return tournamentId;
	}
	
	public int SectionID()
	{
		return sectionId;
	}
	
	public int VenueID()
	{
		return venueId;
	}
	
	public int TeamA_ID()
	{
		return teamA_Id;
	}
	
	public int TeamB_ID()
	{
		return teamB_Id;
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
	
	public String Date()
	{
		return date;
	}	
	
	public String Time()
	{
		return time;
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
	
	public Calendar DateTime()
	{
		//not friendly
		return calendar;
	}
	
	public String Section()
	{
		return section;
	}
		
	public String Gender()
	{
		return gender;
	}
	
	public String TeamA_Name()
	{
		return teamA_name;
	}
	
	public String TeamB_Name()
	{
		return teamB_name;
	}
			
	public String VenueName()
	{
		return venue;
	}
	
	public String CourtNumber()
	{
		return court;
	}
	
	public boolean ResultHasBeenSubmitted()
	{
		return resultSubmitted;
	}

	public boolean isKnockoutFixture()
	{
		return knockoutFixture;
	}
	
	public int GamePar()
	{
		return gamePar;
	}

	public int ScoringMethod()
	{
		return scoringMethod;
	}

	public int PlayersPerTeam()
	{
		return playersPerTeam;
	}
	
}
