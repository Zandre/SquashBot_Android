package adapters;

import java.util.ArrayList;

import tournament_classes.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squashbot.R;

public class Event_Apapter extends ArrayAdapter<Event>
{
	  private final Context context;
	  ArrayList<Event> functions;
	  
	public Event_Apapter(Context context, ArrayList<Event> functions) 
	{
		super(context, R.layout.event_layout, functions);
		this.context = context;
		this.functions = functions;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.event_layout, parent, false);

	    TextView date = (TextView)rowView.findViewById(R.id.tv_eventDate);
	    TextView time = (TextView)rowView.findViewById(R.id.tv_eventTime);
	    TextView name = (TextView)rowView.findViewById(R.id.tv_eventName);
	    TextView text = (TextView)rowView.findViewById(R.id.tv_eventDescription);
	    TextView venue = (TextView)rowView.findViewById(R.id.tv_eventVenue);

	    date.setText(""+ functions.get(position).FriendlyDate());
	    time.setText(""+ functions.get(position).FriendlyTime());
	    name.setText(""+ functions.get(position).EventName());
	    text.setText(""+ functions.get(position).EventDescription());
	    venue.setText(""+ functions.get(position).VenueName());
	    
		return rowView;
	}

}
