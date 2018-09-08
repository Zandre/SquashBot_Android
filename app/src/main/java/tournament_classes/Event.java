package tournament_classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event 
{
	int id, tournamentId;
	String date, time, eventName, description, venue;
	
	Calendar calendar = Calendar.getInstance();
	
	
	public Event(int id, int tournamentId, String date, String time, String eventName, String description, String venue) 
	{
		this.id = id;
		this.tournamentId = tournamentId;
		
		this.date = date;
		this.time = time;
		this.eventName = eventName;
		this.description = description;
		this.venue = venue;
		
		setCalendar();
	}

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

	public int ID()
	{
		return id;
	}

	public int TournamentID()
	{
		return tournamentId;
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
		//not friendy
		return calendar;
	}
	
	public String EventName()
	{
		return eventName;
	}
	
	public String EventDescription()
	{
		return description;
	}
	
	public String VenueName()
	{
		return venue;
	}

}
