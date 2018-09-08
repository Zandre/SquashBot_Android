package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import squashbot.dashboard.Dashboard;
import tournament_classes.TournamentSection;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getTournamentSections extends AsyncTask<String, String, String>
{
	Context context;
	LoadingCircle loading;
	ArrayList<TournamentSection> sections = new ArrayList<TournamentSection>();
	
	public getTournamentSections(Context context)
	{
		this.context = context;
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting tournament & league details...");
		loading.ShowLoadingCircle();
	}
	
	@Override
	protected String doInBackground(String... arg0) 
	{
		getTournament_Sections();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		for(int x = 0; x < sections.size(); x++)
		{//loop through sections collection
			for(int y = 0; y < Dashboard.tournaments.size(); y++)
			{//loop trough tournaments collection
				if(sections.get(x).TournamentID() == Dashboard.tournaments.get(y).ID())
				{
					//Sections belongs to tournament, add to collection
					Dashboard.tournaments.get(y).addTournamentSection(sections.get(x));
				}
			}
		}
		loading.HideLoadingCirlce();
		Dashboard.GoToTournaments();
	}
	
	private void getTournament_Sections()
	{
		String TAG_SUCCESS = "success";
		
		String TournamentID = "";
		String Gender = "";
		String Section = "";
		
		ArrayList<NameValuePair> _sections = new ArrayList<NameValuePair>();
		_sections.add(new BasicNameValuePair("TournamentID", TournamentID));
		_sections.add(new BasicNameValuePair("Gender", Gender));
		_sections.add(new BasicNameValuePair("Section", Section));
		
		JSONParser jParser = new JSONParser();
		
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getTournamentSections.php", _sections);
		
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
					
					String _id = cursor.getString("TournamentId");
					int tournament_id = Integer.parseInt(_id);
					String gender = cursor.getString("Gender");
					String section = cursor.getString("Section");
					String _gamePar = cursor.getString("GamePar");
					int gamePar = Integer.parseInt(_gamePar);
					String _playersPerTeam = cursor.getString("PlayersPerTeam");
					int playersPerTeam = Integer.parseInt(_playersPerTeam);
					String _scoringMethod = cursor.getString("ScoringMethod");
					int scoringMethod = Integer.parseInt(_scoringMethod);
					
					TournamentSection _section = new TournamentSection(tournament_id, gender, section, gamePar, playersPerTeam, scoringMethod);
					sections.add(_section);
				}
			}		
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}
}
