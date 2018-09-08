package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tournament_activities.TournamentDashboard;
import tournament_classes.Log;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getLogs extends AsyncTask<String, String, String>
{
	Context context;
	int tournamentId;
	LoadingCircle loading;
	public ArrayList<Log> logs = new ArrayList<Log>();
	
	public getLogs(Context context, int tournamentId) 
	{
		this.context = context;
		this.tournamentId = tournamentId;
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting logs...");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		getLogs();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		TournamentDashboard.logs = logs;
		loading.HideLoadingCirlce();
		TournamentDashboard.GoToSections();
	}
	
	private void getLogs()
	{
		String TAG_SUCCESS = "success";
		
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("TournamentId", Integer.toString(tournamentId)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getLog.php", parameters);
		
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
					
					String _id = cursor.getString("Id");
					int id = Integer.parseInt(_id);
					String _tournamentId = cursor.getString("TournamentId");
					int tournamentid = Integer.parseInt(_tournamentId);
					
					String gender = cursor.getString("Gender");
					String section = cursor.getString("Section");
					String team = cursor.getString("Team");
					String pool = cursor.getString("Pool");
					
					String _logPoints = cursor.getString("LogPoints");
					int logPoints = Integer.parseInt(_logPoints);
					
					
					String _fixturesWon = cursor.getString("FixturesWon");
					String _fixturesLost = cursor.getString("FixturesLost");
					int fixturesWon = Integer.parseInt(_fixturesWon);
					int fixturesLost = Integer.parseInt(_fixturesLost);
					
					String _matchesWon = cursor.getString("MatchesWon");
					String _matchesLost = cursor.getString("MatchesLost");
					int matchesWon = Integer.parseInt(_matchesWon);
					int matchesLost = Integer.parseInt(_matchesLost);
					
					String _gamesWon = cursor.getString("GamesWon");
					String _gamesLost = cursor.getString("GamesLost");
					int gamesWon = Integer.parseInt(_gamesWon);
					int gamesLost = Integer.parseInt(_gamesLost);
					
					String _pointsWon = cursor.getString("PointsWon");
					String _pointsLost = cursor.getString("PointsLost");
					int pointsWon = Integer.parseInt(_pointsWon);
					int pointsLost = Integer.parseInt(_pointsLost);
					
					Log log = new Log(id, tournamentid, 
										team, gender, section, pool,
										logPoints,
										fixturesWon, fixturesLost,
										matchesWon, matchesLost,
										gamesWon, gamesLost,
										pointsWon, pointsLost);
					
					logs.add(log);
				}
			}		
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}

}
