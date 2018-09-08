package classes;

public class RankedPlayer 
{
	int id;
	double points;
	String name, club, gender;
	public RankedPlayer(int id, String name, String club, String gender, double points)
	{
		this.id = id;
		this.name = name;
		this.club = club;
		this.gender = gender;
		this.points = points;
	}
	
	public RankedPlayer(String name, String club, String gender, double points)
	{
		this.name = name;
		this.club = club;
		this.gender = gender;
		this.points = points;
	}
	
	public RankedPlayer()
	{
		
	}

	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getClub()
	{
		return club;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public double getPoints()
	{
		return points;
	}

}
