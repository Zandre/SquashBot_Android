package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import squashbot.dashboard.Dashboard;
import tournament_classes.Tournament;
import classes.LoadingCircle;
import classes.RankedPlayer;
import display_notification.RedToast;
import android.content.Context;
import android.os.AsyncTask;

public class getTournaments extends AsyncTask<String, String, String>
{
	Context context;
	LoadingCircle loading;
	public ArrayList<Tournament> tournaments = new ArrayList<Tournament>();
	
	
	public getTournaments(Context context)
	{
		this.context = context;
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting tournaments & leagues...");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... params) 
	{
		getTournamentsAndLeagues();
		return null;
	}
	
	
	@Override
	protected void onPostExecute(String result) 
	{
		Dashboard.tournaments = tournaments;
		loading.HideLoadingCirlce();
		if(tournaments.size() >= 1)
		{
			new getTournamentSections(context).execute();
		}
		else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "Currently no active tournaments", true, false);
		}
	}
	
	private void getTournamentsAndLeagues()
	{
		try
		{
			String TAG_SUCCESS = "success";

			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getTournaments.php");

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
						String name = cursor.getString("Name");
						String adminPassword = cursor.getString("AdminPW");
						String clubPassword = cursor.getString("ClubPW");
						String _active = cursor.getString("Active");
						boolean active = false;
						if(_active.contains("1"))
						{
							active = true;
						}
						String namingConvention = cursor.getString("NamingConvention");

						Tournament tournament = new Tournament(id, name, adminPassword, clubPassword, active, namingConvention);
						tournaments.add(tournament);
					}
				}
			}
			catch (JSONException d)
			{
				d.printStackTrace();
			}
		}
		catch (Exception ex)
		{
			int x = 3;
		}
	}
}
