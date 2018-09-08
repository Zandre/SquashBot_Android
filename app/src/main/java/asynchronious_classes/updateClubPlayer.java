package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import rankings.RankingEditor;
import classes.LoadingCircle;
import android.content.Context;
import android.os.AsyncTask;

public class updateClubPlayer extends AsyncTask<String, String, String>
{
	Context context;
	String table, code, name, playerClub;
	int id, clubID;
	double points;
	LoadingCircle loading;
	boolean IsSuccessfull = false;
	
	public updateClubPlayer(Context context, String code, String table, int id, String name, double points, int clubID, String playerClub)
	{
		this.context = context;
		this.table = table;
		this.code = code;
		this.name = name;
		this.id = id;
		this.points = points;
		this.clubID = clubID;
		this.playerClub = playerClub;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "Updating Player", "Name:\t\t"+name+"\nPoints:\t\t"+points+"\nClub:\t\t"+playerClub);
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		updateClubPlayer();
		return null;
	}
	
	private void updateClubPlayer()
	{
	    	String TAG_SUCCESS = "success";
	    
			ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("Name", name));
			parameters.add(new BasicNameValuePair("Points", Double.toString(points)));
			parameters.add(new BasicNameValuePair("Id", Integer.toString(id)));
			parameters.add(new BasicNameValuePair("ClubId", Integer.toString(clubID)));
			parameters.add(new BasicNameValuePair("PlayerClub", playerClub));
			parameters.add(new BasicNameValuePair("Code", code));
			parameters.add(new BasicNameValuePair("Table", table));
			
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/updateClubPlayer.php", parameters);
			
			try
			{
				int success = (json.getInt(TAG_SUCCESS));
				if(success == 1)
				{
					IsSuccessfull = true;
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
		loading.HideLoadingCirlce();
		RankingEditor.SuccessToast(IsSuccessfull);
	}

}
