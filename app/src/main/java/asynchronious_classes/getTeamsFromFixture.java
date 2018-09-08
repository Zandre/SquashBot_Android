package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import squashbot.dashboard.EnterNames;
import tournament_activities.TournamentDashboard;
import tournament_classes.Log;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getTeamsFromFixture extends AsyncTask<String, String, String>
{
	Context context;
	int teamA_Id, teamB_Id;
	LoadingCircle loading;
	public ArrayList<Log> logs = new ArrayList<Log>();
	
	public getTeamsFromFixture(Context context, int teamA_Id, int teamB_Id) 
	{
		this.context = context;
		this.teamA_Id = teamA_Id;
		this.teamB_Id = teamB_Id;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Retrieving fixture information");
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
		EnterNames.helper.SetTeamA_ID(logs.get(0).ID());
		EnterNames.helper.SetTeamB_ID(logs.get(1).ID());
		loading.HideLoadingCirlce();
		EnterNames.GoToEnterTeamResult();
	}
	
	private void getLogs()
	{
		String TAG_SUCCESS = "success";
		
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();

		parameters.add(new BasicNameValuePair("TeamA_Id", Integer.toString(teamA_Id)));
		parameters.add(new BasicNameValuePair("TeamB_Id", Integer.toString(teamB_Id)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getTeamsFromFixture.php", parameters);
		
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

					Log log = new Log(id, tournamentid, team, gender, section, pool);
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
