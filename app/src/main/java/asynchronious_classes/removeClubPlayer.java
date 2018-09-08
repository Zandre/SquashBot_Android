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

public class removeClubPlayer extends AsyncTask<String, String, String>
{

	Context context;
	String table, code, name;
	int id, clubID;
	LoadingCircle loading;
	boolean IsSuccessfull = false;
	
	public removeClubPlayer(Context context, String table, int id, String code, int clubID, String name)
	{
		this.context = context;
		this.table = table;
		this.id = id;
		this.clubID = clubID;
		this.code = code;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "Removing Player", "Removing "+name);
		loading.ShowLoadingCircle();
	}
	
	@Override
	protected String doInBackground(String... params) 
	{
		removeClubPlayer();
		return null;
	}
	
	private void removeClubPlayer()
	{
    	String TAG_SUCCESS = "success";
    	
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("Id", Integer.toString(id)));
		parameters.add(new BasicNameValuePair("ClubId", Integer.toString(clubID)));
		parameters.add(new BasicNameValuePair("Table", table));
		parameters.add(new BasicNameValuePair("Code", code));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/removeClubPlayer.php", parameters);
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
