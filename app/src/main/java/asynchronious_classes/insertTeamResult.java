package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tournament_activities.VerifyResult;
import tournament_classes.TeamResultHelper;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import classes.LoadingCircle;


public class insertTeamResult extends AsyncTask<String, String, String>
{
	LoadingCircle loading;
	Context context;
	TeamResultHelper helper;
	
	public insertTeamResult(Context context, TeamResultHelper helper)
	{
		this.context = context;
		this.helper = helper;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "Sending Result", "Please be patient while the result is being sent to the online database");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		insertResult();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		loading.HideLoadingCirlce();
		VerifyResult.GoToDashBoard();
	}
	
	private void insertResult()
	{
		Gson gson = new Gson();
		String json = gson.toJson(helper);
		
		JSONParser jParser = new JSONParser();
		jParser.makeHttpRequest_WithJSONVariable("http://www.squashbot.co.za/Tournaments/insertTeamResult.php", json);
	}
	
}