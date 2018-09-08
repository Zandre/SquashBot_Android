package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rankings.RankingEditor;
import classes.LoadingCircle;
import android.content.Context;
import android.os.AsyncTask;

public class insertClubPlayer extends AsyncTask<String, String, String>
{

	int clubID;
	double points;
	String name, gender, code, table, playerClub;
	LoadingCircle loading;
	Context context;
	boolean IsSuccessfull = false;
	
	public insertClubPlayer(Context context, String code, String table, String name, String gender, double points, int clubID, String playerClub)
	{
		this.context = context;
		this.code = code;
		this.table = table;
		this.name = name;
		this.gender = gender;
		this.points = points;
		this.clubID = clubID;
		this.playerClub = playerClub;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "Adding New Player", "Name:\t"+name+"\nGender:\t"+gender+"\nPoints:\t"+points);
		loading.ShowLoadingCircle();
	}
	
	@Override
	protected String doInBackground(String... arg0) 
	{
		insertNewPlayer();
		return null;
	}
	
	private void insertNewPlayer()
	{			
	    String TAG_SUCCESS = "success";
	    
			ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("Name", name));
			parameters.add(new BasicNameValuePair("Gender", gender));
			parameters.add(new BasicNameValuePair("Points", Double.toString(points)));
			parameters.add(new BasicNameValuePair("ClubId", Double.toString(clubID)));
			parameters.add(new BasicNameValuePair("Code", code));
			parameters.add(new BasicNameValuePair("Table", table));
			parameters.add(new BasicNameValuePair("PlayerClub", playerClub));
			
			JSONParser jParser = new JSONParser();
			
			JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/insertClubPlayer.php", parameters);
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
