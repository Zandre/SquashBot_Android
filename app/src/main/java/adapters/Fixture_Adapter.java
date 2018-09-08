package adapters;

import java.util.ArrayList;
import tournament_classes.Fixture;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import asynchronious_classes.getRankings;
import com.squashbot.R;

public class Fixture_Adapter extends ArrayAdapter<Fixture>
{

	  private final Context context;
	  ArrayList<Fixture> fixtures;
	  
	public Fixture_Adapter(Context context, ArrayList<Fixture> fixtures) 
	{
		super(context, R.layout.fixture_layout, fixtures);
		this.context = context;
		this.fixtures = fixtures;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.fixture_layout, parent, false);
	    
	    TextView date = (TextView)rowView.findViewById(R.id.tv_fixtureDate);
	    TextView time = (TextView)rowView.findViewById(R.id.tv_fixtureTime);
	    TextView fixture = (TextView)rowView.findViewById(R.id.tv_fixtureFixture);
	    TextView venue = (TextView)rowView.findViewById(R.id.tv_fixtureVenue);
	    TextView court = (TextView)rowView.findViewById(R.id.tv_fixtureCourt);
	    TextView resultSubmitted = (TextView)rowView.findViewById(R.id.tv_fixtureResultSubmitted);

	    date.setText(fixtures.get(position).FriendlyDate());
	    time.setText(fixtures.get(position).FriendlyTime());
	    fixture.setText(fixtures.get(position).TeamA_Name()+ " vs "+ fixtures.get(position).TeamB_Name());
	    venue.setText(fixtures.get(position).VenueName());
	    court.setText(fixtures.get(position).CourtNumber());
	    
	    if(fixtures.get(position).ResultHasBeenSubmitted())
	    {
	    	resultSubmitted.setText("result submitted");
	    	resultSubmitted.setTextColor(context.getResources().getColor(R.color.LimeGreen));
	    }
	    else
	    {
	    	resultSubmitted.setText("...result pending");
	    	resultSubmitted.setTextColor(context.getResources().getColor(R.color.Red));
	    }
	    
	    return rowView;
	}
	
}
