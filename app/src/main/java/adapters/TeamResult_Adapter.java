package adapters;

import java.util.ArrayList;

import tournament_classes.TeamResult;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TeamResult_Adapter extends ArrayAdapter<TeamResult>
{

	  private final Context context;
	  ArrayList<TeamResult> teamResult;
	TextView text;
	  
	public TeamResult_Adapter(Context context, ArrayList<TeamResult> teamResult) 
	{
		super(context, R.layout.teamresult_layout, teamResult);
		this.context = context;
		this.teamResult = teamResult;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.teamresult_layout, parent, false);

		TextView result = (TextView) rowView.findViewById(R.id.tv_teamHeader);
		TextView finalScore = (TextView) rowView.findViewById(R.id.tv_teamFinalScore);
		TextView matchesPlayed = (TextView) rowView.findViewById(R.id.tv_resultTotalMatchesPlayed);
		TextView matchesWon = (TextView) rowView.findViewById(R.id.tv_resultTotalMatchesWon);
		TextView matchesLost = (TextView) rowView.findViewById(R.id.tv_resultTotalMatchesLost);
		TextView gamesPlayed = (TextView) rowView.findViewById(R.id.tv_resultTotalGamesPlayed);
		TextView gamesWon = (TextView) rowView.findViewById(R.id.tv_resultTotalGamesWon);
		TextView gamesLost = (TextView) rowView.findViewById(R.id.tv_resultTotalGamesLost);
		TextView pointsPlayed = (TextView) rowView.findViewById(R.id.tv_resultsTotalPointsPlayed);
		TextView pointsWon = (TextView) rowView.findViewById(R.id.tv_resultTotalPointsWon);
		TextView pointsLost = (TextView) rowView.findViewById(R.id.tv_resultTotalPointsLost);
		TextView date = (TextView) rowView.findViewById(R.id.tv_resultDatePlayed);
		TextView time = (TextView) rowView.findViewById(R.id.tv_resultTimePlayed);
		TextView venue = (TextView) rowView.findViewById(R.id.tv_resultVenuePlayed);
		TextView court = (TextView) rowView.findViewById(R.id.tv_resultCourtPlayed);
		text = (TextView) rowView.findViewById(R.id.tv_resultText);



		result.setText(teamResult.get(position).getWinningTeam() + " beat " + teamResult.get(position).getLosingTeam());
		finalScore.setText("Final Score: " + teamResult.get(position).getWinnerScore() + " - " + teamResult.get(position).getLoserScore());

		date.setText(teamResult.get(position).FriendlyDate());
		time.setText(teamResult.get(position).FriendlyTime());
		venue.setText(teamResult.get(position).VenueName());
		court.setText(teamResult.get(position).CourtNumber());
		text.setText(teamResult.get(position).Result_SquashBotFormat());


		//MATCHES
		matchesPlayed.setText("" + teamResult.get(position).getMatchesPlayed());
		matchesWon.setText(teamResult.get(position).getMatchesWon() + " /");
		matchesLost.setText(" " + teamResult.get(position).getMatchesLost());
		//GAMES
		gamesPlayed.setText("" + teamResult.get(position).getGamesPlayed());
		gamesWon.setText(teamResult.get(position).getGamesWon() + " /");
		gamesLost.setText(" " + teamResult.get(position).getGamesLost());
		//POINTS
		pointsPlayed.setText("" + teamResult.get(position).getPointsPlayed());
		pointsWon.setText(teamResult.get(position).getPointsWon() + " /");
		pointsLost.setText(" " + teamResult.get(position).getPointsLost());


		return rowView;
	}
}
