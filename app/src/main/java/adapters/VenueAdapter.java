package adapters;


import java.util.ArrayList;

import tournament_classes.Venue;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VenueAdapter extends ArrayAdapter<Venue>
{
	  private final Context context;
	  ArrayList<Venue> venues;
	  
	public VenueAdapter(Context context, ArrayList<Venue> venues)
	{
		super(context, R.layout.venue, venues);
		this.context = context;
		this.venues = venues;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.venue, parent, false);

	    TextView name = (TextView)rowView.findViewById(R.id.tv_venueName);
	    TextView address = (TextView)rowView.findViewById(R.id.tv_venueAddress);
	    
	    name.setText(""+ venues.get(position).getVenueName());
	    address.setText("" +venues.get(position).getAddress());
    
		return rowView;
	}

}
