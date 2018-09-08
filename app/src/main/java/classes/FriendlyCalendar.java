package classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FriendlyCalendar 
{
	
	Calendar calendar = Calendar.getInstance();
	
	public FriendlyCalendar()
	{
	}
	
	public String getFriendlyCurrentTime()
	{
		SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a");
		String _time = formatTime.format(calendar.getTime());
		return _time;
	}
	
	public String getFriendlyCurrentDate()
	{
		SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
		String _date = formatDate.format(calendar.getTime());
		return _date;
	}

}
