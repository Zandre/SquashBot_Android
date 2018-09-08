package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tournament_activities.TournamentDashboard;
import tournament_classes.TeamResult;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getTeamResults extends AsyncTask<String, String, String> 
{

	Context context;
	int tournamentId;
	LoadingCircle loading;
	public ArrayList<TeamResult> results = new ArrayList<TeamResult>();
	
	public getTeamResults(Context context, int tournamentId)
	{
		this.context = context;
		this.tournamentId = tournamentId;
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting team results...");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		getResults();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		TournamentDashboard.results = results;
		loading.HideLoadingCirlce();
		TournamentDashboard.GoToSections();
	}
	
	private void getResults()
	{
		String TAG_SUCCESS = "success";
		
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("TournamentId", Integer.toString(tournamentId)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getTeamResults.php", parameters);
		
		String TAG_NODES = "no";
		JSONArray NodesArray = null;
		
		try
		{
			int success = (json.getInt(TAG_SUCCESS));
			if(success == 1)
			{
				NodesArray = json.getJSONArray(TAG_NODES);
				
				for(int x = 0; x <= NodesArray.length()-1; x++)
				{
					JSONObject cursor = NodesArray.getJSONObject(x);

					String _tournamentId = cursor.getString("TournamentId");
					int tournamentid = Integer.parseInt(_tournamentId);

					String gender = cursor.getString("Gender");
					String section = cursor.getString("Section");
					String date = cursor.getString("Date");
					String time = cursor.getString("Time");
					String venue = cursor.getString("Venue");
					String court = cursor.getString("Court");

					String winningTeam = cursor.getString("WinningTeam");
					String losingTeam = cursor.getString("LosingTeam");
					String _winnerScore = cursor.getString("WinnerScore");
					int winnerScore = Integer.parseInt(_winnerScore);
					String _loserScore = cursor.getString("LoserScore");
					int loserScore = Integer.parseInt(_loserScore);

					String _matchesWon = cursor.getString("MatchesWon");
					String _matchesLost = cursor.getString("MatchesLost");
					String _gamesWon = cursor.getString("GamesWon");
					String _gamesLost = cursor.getString("GamesLost");
					String _pointsWon = cursor.getString("PointsWon");
					String _pointsLost = cursor.getString("PointsLost");
					int matchesWon = Integer.parseInt(_matchesWon);
					int matchesLost = Integer.parseInt(_matchesLost);
					int gamesWon = Integer.parseInt(_gamesWon);
					int gamesLost = Integer.parseInt(_gamesLost);
					int pointsWon = Integer.parseInt(_pointsWon);
					int pointsLost = Integer.parseInt(_pointsLost);

					String MatchArray = cursor.getString("MatchesArray");

					TeamResult result = new TeamResult(tournamentid, gender, section, winningTeam, losingTeam, date, time, venue, court, MatchArray, matchesWon, matchesLost, gamesWon, gamesLost, pointsWon, pointsLost, winnerScore, loserScore);
					results.add(result);
				}
			}		
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}

}
