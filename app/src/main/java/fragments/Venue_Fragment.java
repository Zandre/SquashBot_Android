package fragments;

import java.util.ArrayList;
import com.squashbot.R;
import tournament_classes.Venue;
import ExternalApplications.GoogleMaps;
import adapters.VenueAdapter;
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
public class Venue_Fragment extends ListFragment
{
	ArrayList<Venue> venues;
	
	public Venue_Fragment(ArrayList<Venue> venues)
	{
		this.venues = venues;
	}
	
	public Venue_Fragment()
	{
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		registerForContextMenu(getListView());
		
		getListView().setDividerHeight(0);
		
		VenueAdapter adapter = new VenueAdapter(getActivity(), venues);
		setListAdapter(adapter);
	}
	
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;
		Venue venue = venues.get(selectedItem.position);
		menu.setHeaderTitle(venue.getVenueName());

	    MenuInflater inflater = this.getActivity().getMenuInflater();
	    inflater.inflate(R.menu.contextualmenu_venue, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		android.widget.AdapterView.AdapterContextMenuInfo selectedItem = (android.widget.AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int position = selectedItem.position;
		
		switch(item.getItemId())
		{
			case R.id.venues_ViewOnGoogleMaps:
				GoogleMaps map = new GoogleMaps(getActivity(), venues.get(position).getAddress(), venues.get(position).getVenueName());
				break;
		}
		return false;
	}
	

}
