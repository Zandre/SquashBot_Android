package asynchronious_classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rankings.Rankings;
import squashbot.dashboard.Dashboard;
import android.content.Context;
import android.os.AsyncTask;
import classes.Club;
import classes.LoadingCircle;
import classes.RankedPlayer;

public class getRankings extends AsyncTask<String, String, String>
{
	public ArrayList<RankedPlayer> rankings = new ArrayList<RankedPlayer>();
	Context context;
	LoadingCircle loading;
	String phpLink;
	boolean useLoadingCircle;
	Club selectedClub;
	
	public getRankings(Context context, String phpLink, Club selectedClub, boolean useLoadingCircle)
	{
		this.context = context;
		this.phpLink = phpLink;
		this.selectedClub = selectedClub;
		this.useLoadingCircle = useLoadingCircle;
		
		if(useLoadingCircle)
		{
			MakeLoadingCircle();
		}
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Rankings", "Getting player rankings...");
		loading.ShowLoadingCircle();
	}
	
	@Override
	protected String doInBackground(String... params) 
	{
		getPlayersAndRankings();
		return null;
	}
	
	private void getPlayersAndRankings()
	{
		String TAG_SUCCESS = "success";

		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest(phpLink);
		
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
					
					String name = cursor.getString("Name");
					String club = cursor.getString("Club");
					String gender = cursor.getString("Gender");
					double points = cursor.getDouble("Points");
					
					RankedPlayer _rankedPlayer;
					
					if(selectedClub.getUseSquashBotRanker())
					{
						int id = cursor.getInt("Id");
						_rankedPlayer = new RankedPlayer(id, name, club, gender, points);
					}
					else
					{
						_rankedPlayer = new RankedPlayer(name, club, gender, points);
					}

					rankings.add(_rankedPlayer);
				}
			}		
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		for(int x = 0; x < rankings.size(); x++)
		{
			RankedPlayer player = rankings.get(x);
			
			if(player.getGender().contains("Men"))
			{
				Rankings.Men.add(player);
			}
			else
			{
				Rankings.Women.add(player);
			}
		}
		
		if(useLoadingCircle)
		{
			loading.HideLoadingCirlce();
		}

		Rankings.GoToFragment();
	}
}
