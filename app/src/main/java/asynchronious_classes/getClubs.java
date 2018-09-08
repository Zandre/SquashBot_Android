package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import squashbot.dashboard.Dashboard;
import android.content.Context;
import android.os.AsyncTask;
import classes.Club;
import classes.LoadingCircle;

public class getClubs extends AsyncTask<String, String, String>
{
	public ArrayList<Club> clubs = new ArrayList<Club>();
	Context context;
	LoadingCircle loading;
	
	public getClubs(Context context)
	{
		this.context = context;
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Rankings", "Getting clubs...");
		loading.ShowLoadingCircle();
	}
	
	@Override
	protected String doInBackground(String... arg0)
	{
		getClubsAndLinks();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		Dashboard.clubs = clubs;
		loading.HideLoadingCirlce();
		Dashboard.GoToRankings();
	}

	private void getClubsAndLinks() 
	{
		String TAG_SUCCESS = "success";
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/getClubs.php");
		
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
					String link = cursor.getString("Link");
					String useSBranker = cursor.getString("useSquashBotRanker");
					if(useSBranker.contains("1"))
					{
						String tableName = cursor.getString("TableName");
						Club _Club = new Club(id, name, link, true, tableName);
						clubs.add(_Club);
					}
					else
					{
						Club _Club = new Club(id, name, link, false, "");
						clubs.add(_Club);
					}
				}
			}
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}

}
