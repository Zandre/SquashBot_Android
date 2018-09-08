package tournament_classes;

public class Venue 
{
	int id, tournamentId;
	String venue, address;
	
	
	//CONSTRUCTOR
	
	public Venue(int id, int tournamentId, String venue, String address) 
	{
		this.id = id;
		this.tournamentId = tournamentId;
		
		this.venue = venue;
		this.address = address;
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
	
	
	//VENUE DETAILS
	
	public String getVenueName()
	{
		return venue;
	}
	
	public String getAddress()
	{
		return address;
	}
}
