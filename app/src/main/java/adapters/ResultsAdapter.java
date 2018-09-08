package adapters;

import java.util.ArrayList;

import classes.Result;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultsAdapter extends ArrayAdapter<Result>
{
	private final Context context;
	ArrayList<Result> results;
	
	public ResultsAdapter(Context context, ArrayList<Result> results)
	{
		super(context, R.layout.scoreboard_result, results);
		this.context = context;
		this.results = results;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.scoreboard_result, parent, false);
	    
	    TextView header = (TextView)rowView.findViewById(R.id.scoreboard_header);
	    TextView result = (TextView)rowView.findViewById(R.id.scoreboard_result);
	    TextView scores = (TextView)rowView.findViewById(R.id.scoreboard_scores);
	    TextView date = (TextView)rowView.findViewById(R.id.scoreboard_date);
	    TextView venue = (TextView)rowView.findViewById(R.id.scoreboard_venue);

	    String Scores = results.get(position).getScores();
	    Scores = Scores.replaceAll("[^a-zA-Z0-9]+$", "");
	    
	    header.setText(results.get(position).getWinner()+" VS "+results.get(position).getLoser());
	    result.setText(results.get(position).getWinner()+" wins "+results.get(position).getResult());
	    scores.setText(Scores);
	    date.setText(results.get(position).FriendlyDate());
	    venue.setText(results.get(position).getVenue());

	    return rowView;
	}

}
