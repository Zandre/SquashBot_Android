package classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result 
{
	String winner, loser, scores, date, venue, result;
	int id;
	public Result(String winner, String loser, String scores, String date, String venue, String result, int id)
	{
		this.winner = winner;
		this.loser = loser;
		this.scores = scores;
		this.date = date;
		this.venue = venue;
		this.result = result;
		this.id = id;
	}
	
	public String getWinner()
	{
		return winner;
	}
	public String getLoser()
	{
		return loser;
	}
	public String getScores()
	{
		return scores;
	}
	public String getDate()
	{
		return date;
	}
	
	public String FriendlyDate()
	{
	    String stringDate = getDate();
	    String[] dateSegments = stringDate.split("-");
	    
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Integer.parseInt(dateSegments[0]), 
						Integer.parseInt(dateSegments[1]) - 1, 
						Integer.parseInt(dateSegments[2]));
	    
		SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
		String _date = formatDate.format(calendar.getTime());
		return _date;
	}
	
	public String Outcome()
	{
		return getWinner() + " beat " + getLoser();
	}
	

	public String getVenue()
	{
		return venue;
	}
	public String getResult()
	{
		return result;
	}
	public int getID()
	{
		return id;
	}

}
