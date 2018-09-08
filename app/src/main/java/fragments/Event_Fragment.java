package fragments;

import java.util.ArrayList;

import com.squashbot.R;

import tournament_activities.TournamentDashboard;
import tournament_classes.Contact;
import tournament_classes.Event;
import tournament_classes.Venue;
import ExternalApplications.AddCalendarEvent;
import ExternalApplications.AddToContacts;
import ExternalApplications.PhoneCall;
import ExternalApplications.SMS;
import adapters.Event_Apapter;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;

import database.Jarvis_Helper;

@SuppressLint("ValidFragment")
public class Event_Fragment extends ListFragment
{

	ArrayList<Event> events;
	
	@SuppressLint("ValidFragment")
	public Event_Fragment(ArrayList<Event> events)
	{
		this.events = events;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		registerForContextMenu(getListView());
		
		Event_Apapter adapter = new Event_Apapter(getActivity(), events);
		setListAdapter(adapter);
	}

	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;
		Event event = events.get(selectedItem.position);
		menu.setHeaderTitle(event.EventName());

	    MenuInflater inflater = this.getActivity().getMenuInflater();
	    inflater.inflate(R.menu.contextualmenu_events, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		android.widget.AdapterView.AdapterContextMenuInfo selectedItem = (android.widget.AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int position = selectedItem.position;
		
		switch(item.getItemId())
		{
			case R.id.events_addThisEventToCalendar:
				ArrayList<Event> _events = new ArrayList<Event>();
				_events.add(events.get(position));
				AddEventToCalendar(_events);
				break;
				
			case R.id.events_addAllEventsToCalendar:
				AddEventToCalendar(events);
				break;
		}
		return false;
	}
	
	private void AddEventToCalendar(ArrayList<Event> _events)
	{
		AddCalendarEvent addCalendar = new AddCalendarEvent(getActivity());
		addCalendar.AddEventsToCalendar(_events);
	}
}
