package asynchronious_classes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squashbot.R;

import tournament_activities.Filter;
import tournament_activities.TournamentDashboard;
import tournament_classes.Fixture;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import classes.LoadingCircle;

public class getFixturesForSpecificTeam extends AsyncTask<String, String, String>
{

	Context context;
	int tournamentId, teamId;
	String teamName, gender, section;
	LoadingCircle loading;
	public ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	Filter.FilterType type;
	
	public getFixturesForSpecificTeam(Context context, int tournamentId, int teamId, String teamName, String gender, String section, Filter.FilterType type)
	{
		this.context = context;
		this.tournamentId = tournamentId;
		this.teamId = teamId;
		this.teamName = teamName;
		this.gender = gender;
		this.section = section;
		this.type = type;
		
		MakeLoadingCircle();
	}
	
	private void MakeLoadingCircle() 
	{
		loading = new LoadingCircle(context, "SquashBot Tournaments", "Getting fixtures for "+teamName+" ("+gender+", "+section+" "+TournamentDashboard.selectedTournament.NamingConvention()+")");
		loading.ShowLoadingCircle();
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		getFixtures();
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		Filter.fixtures = fixtures;
		loading.HideLoadingCirlce();
		
		ListFragment list = new Filter.FixturesForSpecificTeamFragment();
		Activity activity = (Activity)context;
		activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
		activity.getFragmentManager().removeOnBackStackChangedListener(null);
	}
	
	private void getFixtures()
	{
		String TAG_SUCCESS = "success";
		
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();

		parameters.add(new BasicNameValuePair("TournamentId", Integer.toString(tournamentId)));
		parameters.add(new BasicNameValuePair("TeamId", Integer.toString(teamId)));
		
		JSONParser jParser = new JSONParser();
		JSONObject json = new JSONObject();

		switch (type)
		{
			case FilterOnFixtures:
				json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getFixturesForSpecificTeam.php", parameters);
				break;

			case FilterOnUpcomingFixture:
				json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getNextFixtureForSpecificTeam.php", parameters);
				break;
		}

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
