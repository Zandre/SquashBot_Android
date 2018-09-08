package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import rankings.RankingEditor;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;
import classes.RankedPlayer;

public class insertClubPlayerResult extends AsyncTask<String, String, String>
{
	int clubID;
	double winnerPoints = 0.00, loserPoints = 0.00;
	RankedPlayer winner, loser;
	String code, table;
	LoadingCircle loading;
	Context context;
	boolean IsSuccessfull = false;
	
	public insertClubPlayerResult(Context context, RankedPlayer winner, RankedPlayer loser, int clubID, String code, String table)
	{
		this.context = context;
		this.winner = winner;
		this.loser = loser;
		this.clubID = clubID;
		this.code = code;
		this.table = table;
		
		MakeLoadingCircle();
	}
		
		
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "New Result", "Adding new result to rankings");
		loading.ShowLoadingCircle();
	}


	@Override
	protected String doInBackground(String... arg0) 
	{
		CalculatePoints();
		insertNewResult();
		return null;
	}
	
	private void CalculatePoints()
	{
		double difference = winner.getPoints() - loser.getPoints();
		
		if(-230.00 > difference)
		{
			//Freak Win
			winnerPoints = 70.00;
			loserPoints = -25.00;
		}
		else if(difference >= -230.00 && -220.00 > difference)
		{
			winnerPoints = 40.00;
			loserPoints = -20.00;
		}
		else if(difference >= -220.00 && -210.00 > difference)
		{
			winnerPoints = 40.00;
			loserPoints = -20.00;
		}
		else if(difference >= -210.00 && -200.00 > difference)
		{
			winnerPoints = 40.00;
			loserPoints = -20.00;
		}
		else if(difference >= -200.00 && -190.00 > difference)
		{
			winnerPoints = 40.00;
			loserPoints = -20.00;
		}
		else if(difference >= -190.00 && -180.00 > difference)
		{
			winnerPoints = 38.20;
			loserPoints = -19.20;
		}
		else if(difference >= -180.00 && -170.00 > difference)
		{
			winnerPoints = 36.40;
			loserPoints = -18.40;
		}
		else if(difference >= -170.00 && -160.00 > difference)
		{
			winnerPoints = 34.60;
			loserPoints = -17.60;
		}
		else if(difference >= -160.00 && -150.00 > difference)
		{
			winnerPoints = 32.80;
			loserPoints = -16.80;
		}
		else if(difference >= -150.00 && -140.00 > difference)
		{
			winnerPoints = 31.00;
			loserPoints = -16.00;
		}
		else if(difference >= -140.00 && -130.00 > difference)
		{
			winnerPoints = 29.20;
			loserPoints = -15.20;
		}
		else if(difference >= -130.00 && -120.00 > difference)
		{
			winnerPoints = 27.40;
			loserPoints = -14.40;
		}
		else if(difference >= -120.00 && -110.00 > difference)
		{
			winnerPoints = 25.60;
			loserPoints = -13.75;
		}
		else if(difference >= -110.00 && -100.00 > difference)
		{
			winnerPoints = 23.80;
			loserPoints = -12.80;
		}
		else if(difference >= -100.00 && -90.00 > difference)
		{
			winnerPoints = 22.00;
			loserPoints = -12.00;
		}
		else if(difference >= -90.00 && -80.00 > difference)
		{
			winnerPoints = 20.20;
			loserPoints = -11.20;
		}
		else if(difference >= -80.00 && -70.00 > difference)
		{
			winnerPoints = 18.40;
			loserPoints = -10.40;
		}
		else if(difference >= -70.00 && -60.00 > difference)
		{
			winnerPoints = 16.60;
			loserPoints = -9.60;
		}
		else if(difference >= -60.00 && -50.00 > difference)
		{
			winnerPoints = 14.80;
			loserPoints = -8.80;
		}
		else if(difference >= -50.00 && -40.00 > difference)
		{
			winnerPoints = 13.00;
			loserPoints = -8.00;
		}
		else if(difference >= -40.00 && -30.00 > difference)
		{
			winnerPoints = 11.20;
			loserPoints = -7.20;
		}
		else if(difference >= -30.00 && -20.00 > difference)
		{
			winnerPoints = 9.40;
			loserPoints = -6.40;
		}
		else if(difference >= -20.00 && -10.00 > difference)
		{
			winnerPoints = 7.60;
			loserPoints = -5.60;
		}
		else if(difference >= -10.00 && 0.00 > difference)
		{
			winnerPoints = 5.80;
			loserPoints = -4.80;
		}
		//###########################
		else if(difference == 0.00)
		{
			winnerPoints = 4.00;
			loserPoints = -4.00;
		}
		//###########################
		else if(difference > 0.00 && 10.00 >= difference)
		{
			winnerPoints = 3.48;
			loserPoints = -3.48;
		}
		else if(difference > 10.00 && 20.00 >= difference)
		{
			winnerPoints = 3.08;
			loserPoints = -3.08;
		}
		else if(difference > 20.00 && 30.00 >= difference)
		{
			winnerPoints = 2.76;
			loserPoints = -2.76;
		}
		else if(difference > 30.00 && 40.00 >= difference)
		{
			winnerPoints = 2.50;
			loserPoints = -2.50;
		}
		else if(difference > 40.00 && 50.00 >= difference)
		{
			winnerPoints = 2.29;
			loserPoints = -2.29;
		}
		else if(difference > 50.00 && 60.00 >= difference)
		{
			winnerPoints = 2.11;
			loserPoints = -2.11;
		}
		else if(difference > 60.00 && 70.00 >= difference)
		{
			winnerPoints = 1.95;
			loserPoints = -1.95;
		}
		else if(difference > 70.00 && 80.00 >= difference)
		{
			winnerPoints = 1.82;
			loserPoints = -1.82;
		}
		else if(difference > 80.00 && 90.00 >= difference)
		{
			winnerPoints = 1.70;
			loserPoints = -1.70;
		}
		else if(difference > 90.00 && 100.00 >= difference)
		{
			winnerPoints = 1.60;
			loserPoints = -1.60;
		}
		else if(difference > 100.00 && 110.00 >= difference)
		{
			winnerPoints = 1.51;
			loserPoints = -1.51;
		}
		else if(difference > 110.00 && 120.00 >= difference)
		{
			winnerPoints = 1.43;
			loserPoints = -1.43;
		}
		else if(difference > 120.00 && 130.00 >= difference)
		{
			winnerPoints = 1.36;
			loserPoints = -1.63;
		}
		else if(difference > 130.00 && 140.00 >= difference)
		{
			winnerPoints = 1.29;
			loserPoints = -1.29;
		}
		else if(difference > 140.00 && 150.00 >= difference)
		{
			winnerPoints = 1.23;
			loserPoints = -1.23;
		}
		else if(difference > 150.00 && 160.00 >= difference)
		{
			winnerPoints = 1.18;
			loserPoints = -1.18;
		}
		else if(difference > 160.00 && 170.00 >= difference)
		{
			winnerPoints = 1.13;
			loserPoints = -1.13;
		}
		else if(difference > 170.00 && 180.00 >= difference)
		{
			winnerPoints = 1.08;
			loserPoints = -1.08;
		}
		else if(difference > 180.00 && 190.00 >= difference)
		{
			winnerPoints = 1.04;
			loserPoints = -1.04;
		}
		else if(difference > 190.00 && 200.00 >= difference)
		{
			winnerPoints = 1.00;
			loserPoints = -1.00;
		}
		else if(difference > 200.00 && 210.00 >= difference)
		{
			winnerPoints = 1.00;
			loserPoints = -1.00;
		}
		else if(difference > 210.00 && 220.00 >= difference)
		{
			winnerPoints = 1.00;
			loserPoints = -1.00;
		}
		else if(difference > 220.00 && 230.00 >= difference)
		{
			winnerPoints = 1.00;
			loserPoints = -1.00;
		}
		else if(difference > 230.00)
		{
			//Freak win - not included in SA Points system
			//but logically makes sense
			winnerPoints = 1.00;
			loserPoints = -1.00;
		}
	}
	
	private void insertNewResult()
	{
    	String TAG_SUCCESS = "success";
	    
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("ClubId", Integer.toString(clubID)));
		parameters.add(new BasicNameValuePair("Code", code));
		parameters.add(new BasicNameValuePair("Table", table));
		parameters.add(new BasicNameValuePair("WinnerId", Integer.toString(winner.getID())));
		parameters.add(new BasicNameValuePair("LoserId", Integer.toString(loser.getID())));
		parameters.add(new BasicNameValuePair("WinnerPoints", Double.toString(winnerPoints)));
		parameters.add(new BasicNameValuePair("LoserPoints", Double.toString(loserPoints)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/insertClubPlayerResult.php", parameters);
		
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
