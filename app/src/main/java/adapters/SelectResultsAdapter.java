package adapters;

import individual_results.SelectResult;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import classes.Result;

import com.squashbot.R;

public class SelectResultsAdapter extends ArrayAdapter<Result>
{
	private final Context context;
	ArrayList<Result> results;

	boolean action;
	
	public SelectResultsAdapter(Context context, ArrayList<Result> results, boolean action) 
	{
		super(context, R.layout.select_result, results);
		this.context = context;
		this.results = results;
		this.action = action;
	}
	
	@Override
	public View getView (final int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.select_result, parent, false);
	    
	    final TextView result = (TextView)rowView.findViewById(R.id.tv_select_result);
	    TextView venue = (TextView)rowView.findViewById(R.id.tv_select_venue);
	    TextView date = (TextView)rowView.findViewById(R.id.tv_select_date);
	    CheckBox select = (CheckBox)rowView.findViewById(R.id.cb_select);
	    
	    result.setText(results.get(position).getWinner()+" beat "+results.get(position).getLoser()+" "+results.get(position).getResult());
	    venue.setText(results.get(position).getVenue());
	    date.setText(results.get(position).getDate());
	    if(action == true)
	    {
	    	select.setText("Delete");
	    }
	    else
	    {
	    	select.setText("Share");
	    }
	    
	    select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
	    {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				SelectResult.addSelectedResult(results.get(position));
			}
	    });
	    
		return rowView;
	}
}
