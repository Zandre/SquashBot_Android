package ExternalApplications;

import java.util.ArrayList;

import tournament_classes.Event;
import tournament_classes.Fixture;
import android.content.Context;
import android.content.Intent;

public class AddCalendarEvent
{

	Context context;
	public AddCalendarEvent(Context context)
	{
		this.context = context;
	}
	
	public void AddFixturesToCalendar(ArrayList<Fixture> fixtures)
	{
		for(int x = 0; x < fixtures.size(); x++)
		{
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", fixtures.get(x).DateTime().getTimeInMillis());
			intent.putExtra("endTime", fixtures.get(x).DateTime().getTimeInMillis()+60*60*2000);		
			intent.putExtra("title", fixtures.get(x).TeamA_Name() + " vs " + fixtures.get(x).TeamB_Name());
			intent.putExtra("eventLocation", fixtures.get(x).VenueName());
			context.startActivity(intent);
		}
	}
	
	public void AddEventsToCalendar(ArrayList<Event> events)
	{
		for(int x = 0; x < events.size(); x++)
		{
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", events.get(x).DateTime().getTimeInMillis());
			intent.putExtra("endTime", events.get(x).DateTime().getTimeInMillis()+60*60*2000);
			intent.putExtra("title", events.get(x).EventName());
			intent.putExtra("eventLocation", events.get(x).VenueName());
			intent.putExtra("description", events.get(x).EventDescription());
			context.startActivity(intent);
		}
	}
}
