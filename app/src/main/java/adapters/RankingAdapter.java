package adapters;

import java.util.ArrayList;

import tournament_classes.Log;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import classes.RankedPlayer;

public class RankingAdapter extends ArrayAdapter<RankedPlayer>
{

	  private final Context context;
	  ArrayList<RankedPlayer> rankings;
	
	public RankingAdapter(Context context, ArrayList<RankedPlayer> rankings) 
	{
		super(context, R.layout.log_layout, rankings);
		this.context = context;
		this.rankings = rankings;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
	    convertView = inflater.inflate(R.layout.ranking_layout, parent, false);
	    
	    TextView rank = (TextView)convertView.findViewById(R.id.tv_venueAdr);
	    TextView name = (TextView)convertView.findViewById(R.id.tvRanking_name);
	    TextView club = (TextView)convertView.findViewById(R.id.tvRanking_club);
	    TextView points = (TextView)convertView.findViewById(R.id.tv_venueG);
	    
	    rank.setText((position+1)+".");
	    name.setText("\t"+rankings.get(position).getName());
		points.setText(rankings.get(position).getPoints()+"\t");

		if(rankings.get(position).getClub().length() >= 1)
		{
			club.setText("\t("+rankings.get(position).getClub()+")");
		}
		else
		{
			club.setText("");
		}
	    return convertView;
	}

}
