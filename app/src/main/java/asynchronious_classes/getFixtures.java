package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tournament_activities.Sections;
import tournament_activities.TournamentDashboard;
import tournament_activities.TournamentDashboard.GetFixturesFor;
import tournament_classes.Fixture;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getFixtures extends AsyncTask<String, String, String>
{

	Context context;
	int tournamentId;
	LoadingCircle loading;
	public ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	GetFixturesFor ref;
	
	public getFixtures(Context context, int tournamentId, GetFixturesFor ref) 
	{
		this.context = context;
		this.tournamentId = tournamentId;
		this.ref = ref;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting fixtures...");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0) 
	{
		_getFixtures();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		loading.HideLoadingCirlce();
		
		if(ref == GetFixturesFor.ViewingFixtues)
		{
			TournamentDashboard.fixtures = fixtures;
			TournamentDashboard.GoToSections();
		}
		else if (ref == GetFixturesFor.EnteringTeamResultsFromDashboard)
		{
			TournamentDashboard.fixtures = fixtures;
			TournamentDashboard.GoToEnterTeamResults();
		}
		else if (ref == GetFixturesFor.EnteringTeamResultsFromResults)
		{
			Sections.fixtures = fixtures;
			Sections.GoToEnterTeamResults();
		}

	}
	
	private void _getFixtures()
	{
		String TAG_SUCCESS = "success";
		
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("TournamentId", Integer.toString(tournamentId)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getFixtures.php", parameters);
		
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
					String teamA = cursor.getString("TeamA");
					String _teamA_ID = cursor.getString("TeamA_ID");
					int teamA_ID = Integer.parseInt(_teamA_ID);
					String teamB = cursor.getString("TeamB");
					String _teamB_ID = cursor.getString("TeamB_ID");
					int teamB_ID = Integer.parseInt(_teamB_ID);
					
					String date = cursor.getString("Date");
					String time = cursor.getString("Time");
					String venue = cursor.getString("Venue");
					String court = cursor.getString("Court");
					
					String _resultSubmitted = cursor.getString("ResultSubmitted");
					boolean resultSubmitted = false;
					if(_resultSubmitted.contains("1"))
					{
						resultSubmitted = true;
					}
					String _knockoutFixture = cursor.getString("KnockoutFixture");
					boolean knockoutFixture = false;
					if(_knockoutFixture.contains("1"))
					{
						knockoutFixture = true;
					}
					
					String _venueId = cursor.getString("VenueId");
					int venueId = Integer.parseInt(_venueId);
					
					String _sectionId = cursor.getString("SectionId");
					int sectionId = Integer.parseInt(_sectionId);
					
					String _gamePar = cursor.getString("GamePar");
					int gamePar = Integer.parseInt(_gamePar);
					String _playersPerTeam = cursor.getString("PlayersPerTeam");
					int playersPerTeam = Integer.parseInt(_playersPerTeam);
					String _scoringMethod = cursor.getString("ScoringMethod");
					int scoringMethod = Integer.parseInt(_scoringMethod);
					
					Fixture fixture = new Fixture(id, tournamentid, 
							gender, section, 
							teamA, teamB, teamA_ID, teamB_ID, 
							date, time, venue, court, 
							resultSubmitted, knockoutFixture, 
							venueId, sectionId, gamePar, scoringMethod, playersPerTeam);

					fixtures.add(fixture);
			}
			}		
		}
		catch (JSONException d)
		{
			d.printStackTrace();
		}
	}

}
