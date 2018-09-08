package adapters;

import java.util.ArrayList;

import tournament_classes.Fixture;
import tournament_classes.Log;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Log_Adapter extends ArrayAdapter<Log>
{

	  private final Context context;
	  ArrayList<Log> log;
	  boolean displayPool;
	  
	public Log_Adapter(Context context, ArrayList<Log> log) 
	{
		super(context, R.layout.log_layout, log);
		this.context = context;
		this.log = log;
		displayPool = DisplayPool();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.log_layout, parent, false);
	    
	    //HEADER ; LOG POINTS; POOL
		    TextView header = (TextView)rowView.findViewById(R.id.tv_logHeader);
		    TextView pool = (TextView)rowView.findViewById(R.id.tv_logPool);
		    TextView logpoints = (TextView)rowView.findViewById(R.id.tv_logPoints);
		    
		    header.setText(log.get(position).TeamName());
		    pool.setText("Pool " + log.get(position).Pool());
		    if(!displayPool)
		    {
			    pool.setVisibility(View.INVISIBLE);
		    }
			logpoints.setText(""+log.get(position).LogPoints());

			
		//FIXTURES
			TextView fixturesPlayed = (TextView)rowView.findViewById(R.id.tv_totalFixturesPlayed);
			TextView fixturesWon = (TextView)rowView.findViewById(R.id.tv_totalFixturesWon);
			TextView fixturesLost = (TextView)rowView.findViewById(R.id.tv_totalFixturesLost);
			TextView fixturesPercentage = (TextView)rowView.findViewById(R.id.tv_fixturesPercentage);
			
			fixturesPlayed.setText(""+log.get(position).getFixturesPlayed());
			if(log.get(position).getFixturesPlayed() == 0)
			{
				fixturesWon.setVisibility(View.INVISIBLE);
				fixturesLost.setVisibility(View.INVISIBLE);
				fixturesPercentage.setVisibility(View.INVISIBLE);
			}
			else
			{
				fixturesWon.setText(log.get(position).getFixturesWon()+" /");
				fixturesLost.setText(" "+log.get(position).getFixturesLost());
				fixturesPercentage.setText(log.get(position).FixturesPercentage()+"%");	
			}


		//MATCHES
			TextView matchesPlayed = (TextView)rowView.findViewById(R.id.tv_totalMatchesPlayed);
			TextView matchesWon = (TextView)rowView.findViewById(R.id.tv_totalMatchesWon);
			TextView matchesLost = (TextView)rowView.findViewById(R.id.tv_totalMatchesLost);
			TextView matchesPercentage = (TextView)rowView.findViewById(R.id.tv_matchesPercentage);
			
			matchesPlayed.setText(""+log.get(position).getMatchesPlayed());
			if(log.get(position).getMatchesPlayed() == 0)
			{
				matchesWon.setVisibility(View.INVISIBLE);
				matchesLost.setVisibility(View.INVISIBLE);
				matchesPercentage.setVisibility(View.INVISIBLE);
			}
			else
			{
				matchesWon.setText(log.get(position).getMatchesWon()+" /");
				matchesLost.setText(" "+log.get(position).getMatchesLost());
				matchesPercentage.setText(log.get(position).MatchesPercentage()+"%");
			}


		//GAMES
			TextView gamesPlayed = (TextView)rowView.findViewById(R.id.tv_eventV);
			TextView gamesWon = (TextView)rowView.findViewById(R.id.tv_totalGamesWon);
			TextView gamesLost = (TextView)rowView.findViewById(R.id.tv_totalGamesLost);
			TextView gamesPercentage = (TextView)rowView.findViewById(R.id.tv_gamesPercentage);
			
			gamesPlayed.setText(""+log.get(position).getGamesPlayed());
			if(log.get(position).getGamesPlayed() == 0)
			{
				gamesWon.setVisibility(View.INVISIBLE);
				gamesLost.setVisibility(View.INVISIBLE);
				gamesPercentage.setVisibility(View.INVISIBLE);
			}
			else
			{
				gamesWon.setText(log.get(position).getGamesWon()+" /");
				gamesLost.setText(" "+log.get(position).getGamesLost());
				gamesPercentage.setText(log.get(position).GamesPercentage()+"%");
			}
			
		
		//POINTS
			TextView pointsPlayed = (TextView)rowView.findViewById(R.id.tv_resultsTotalPointsPlayed);
			TextView pointsWon = (TextView)rowView.findViewById(R.id.tv_totalPointsWon);
			TextView pointsLost = (TextView)rowView.findViewById(R.id.tv_totalPointsLost);
			TextView pointsPercentage = (TextView)rowView.findViewById(R.id.tv_pointsPercentage);
			
			pointsPlayed.setText(""+log.get(position).getPointsPlayed());
			if(log.get(position).getPointsPlayed() == 0)
			{
				pointsWon.setVisibility(View.INVISIBLE);
				pointsLost.setVisibility(View.INVISIBLE);
				pointsPercentage.setVisibility(View.INVISIBLE);
			}
			else
			{
				pointsWon.setText(log.get(position).getPointsWon()+" /");
				pointsLost.setText(" "+log.get(position).getPointsLost());
				pointsPercentage.setText(log.get(position).PointsPercentage()+"%");	
			}
	    
	    return rowView;
	}
	
	private boolean DisplayPool()
	{
		System.out.println("DisplayPool()\t\tLog Size:\t"+log.size());
		boolean display = false;
		
		String Pool = log.get(0).Pool();
		
		for(int x = 0; x < log.size(); x++)
		{
			if(!log.get(x).Pool().contains(Pool))
			{
				display = true;
				break;
			}
		}
		return display;
	}
}
