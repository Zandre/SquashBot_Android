package tournament_classes;

public class Contact
{
	int id, tournamentId;
	ContactType contactType;
	String number, name, section, gender, team, role;
	
	public Contact(int id, int tournamentId, ContactType contactType, String number, String name, String section, String gender, String team)
	{
		//Mens and Womens Captains
		
		this.id = id;
		this.tournamentId = tournamentId;		
		
		this.contactType = contactType;
		this.number = number;
		this.name = name;
		this.section = section;
		this.gender = gender;
		this.team = team;
	}
	
	public Contact(int id, int tournamentId, ContactType contactType, String number, String name, String role)
	{
		//Tournament Organizers
		
		this.id = id;
		this.tournamentId = tournamentId;
		
		this.contactType = contactType;
		this.number = number;
		this.name = name;
		this.role = role;
	}
	
	
	
	public int ID()
	{
		return id;
	}
	
	public int TournamentID()
	{
		return tournamentId;
	}
	
	
	
	public ContactType ContactType()
	{
		return contactType;
	}
	
	public String Number()
	{
		return number;
	}
	
	public String Name()
	{
		return name;
	}
	
	public String Section()
	{
		return section;
	}
	
	public String Gender()
	{
		return gender;
	}
		
	public String Team()
	{
		return team;
	}

	public String Role()
	{
		return role;
	}
	
	public enum ContactType
	{
		MensCaptain,
		WomensCaptain,
		TournamentOrganizer
	}
}
