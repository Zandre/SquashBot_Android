package tournament_activities;


import java.util.ArrayList;

import fragments.InformationBoard_Fragment;
import tournament_classes.Contact;
import tournament_classes.Event;
import tournament_classes.InformationBoard;
import tournament_classes.Venue;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

import com.squashbot.R;

import fragments.Contact_Fragment;
import fragments.Event_Fragment;
import fragments.Venue_Fragment;


public class TournamentInfo extends Activity 
{
	//VENUES
	ArrayList<Venue> _venues = new ArrayList<Venue>();
	//EVENTS
	ArrayList<Event> _events = new ArrayList<Event>();
	//CONTACTS
	ArrayList<Contact> _organizers = new ArrayList<Contact>();
	ArrayList<Contact> mensCaptains = new ArrayList<Contact>();
	ArrayList<Contact> womensCaptains = new ArrayList<Contact>();
	//INFORMATION BOARD
	ArrayList<InformationBoard> messages = new ArrayList<InformationBoard>();
	
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jarvis_contacts);
		
		context = TournamentInfo.this;
		
		populateLists();
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayShowTitleEnabled(false);
		
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.setDisplayShowHomeEnabled(false);

		if(messages.size() > 0)
		{
			Tab messageTab = actionbar.newTab();
			messageTab.setIcon(R.drawable.ic_action_view_as_list);
			messageTab.setText("Message Board");
			messageTab.setTabListener(new TabListener()
			{
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction)
				{
					ListFragment list = new InformationBoard_Fragment(messages);
					Activity activity = (Activity) context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction)
				{

				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction)
				{

				}
			});
			actionbar.addTab(messageTab);
		}
		
		if(_venues.size() > 0)
		{
			Tab venues = actionbar.newTab();
			venues.setIcon(R.drawable.ic_action_place);
			venues.setText("Venues");
			venues.setTabListener(new TabListener()
			{
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) 
				{
					ListFragment list = new Venue_Fragment(_venues);
					Activity activity = (Activity)context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			});
			actionbar.addTab(venues);
		}

		if(_events.size() > 0)
		{
			Tab events = actionbar.newTab();
			events.setIcon(R.drawable.ic_action_time);
			events.setText("Events");
			events.setTabListener(new TabListener()
			{
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) 
				{
					ListFragment list = new Event_Fragment(_events);
					Activity activity = (Activity)context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			});
			actionbar.addTab(events);
		}

		if(mensCaptains.size() > 0)
		{
			Tab menCaptains = actionbar.newTab();
			menCaptains.setIcon(R.drawable.men_40x40);
			menCaptains.setText("Captains");
			menCaptains.setTabListener(new TabListener() 
			{
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) 
				{
					ListFragment list = new Contact_Fragment(mensCaptains);
					Activity activity = (Activity)context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			});
			actionbar.addTab(menCaptains);
		}

		if(womensCaptains.size() > 0)
		{
			Tab ladiesCaptains  = actionbar.newTab();
			ladiesCaptains.setIcon(R.drawable.women_40x40);
			ladiesCaptains.setText("Captains");
			ladiesCaptains.setTabListener(new TabListener()
			{
				
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft)
				{
					ListFragment list = new Contact_Fragment(womensCaptains);
					Activity activity = (Activity)context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			});
			actionbar.addTab(ladiesCaptains);
		}

		if(_organizers.size() > 0)
		{
			Tab tournamentOrganizers = actionbar.newTab();
			tournamentOrganizers.setIcon(R.drawable.ic_action_help);
			tournamentOrganizers.setText("Organizers");
			tournamentOrganizers.setTabListener(new TabListener()
			{
				
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) 
				{
					ListFragment list = new Contact_Fragment(_organizers);
					Activity activity = (Activity)context;
					activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
					activity.getFragmentManager().removeOnBackStackChangedListener(null);
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
			});
			actionbar.addTab(tournamentOrganizers);
		}
	}

	private void populateLists()
	{
		_venues = TournamentDashboard.venues;
		_events = TournamentDashboard.events;
		mensCaptains = TournamentDashboard.menCaptains;
		womensCaptains = TournamentDashboard.womenCaptains;
		_organizers = TournamentDashboard.tournamentOrganizers;
		messages = TournamentDashboard.messages;
	}
}
