package tournament_activities;

import java.util.ArrayList;

import squashbot.dashboard.Dashboard;
import squashbot.dashboard.EnterNames;
import tournament_classes.Contact;
import tournament_classes.Event;
import tournament_classes.Fixture;
import tournament_classes.InformationBoard;
import tournament_classes.Log;
import tournament_classes.TeamResult;
import tournament_classes.Tournament;
import tournament_classes.Venue;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import asynchronious_classes.getFixtures;
import asynchronious_classes.getLogs;
import asynchronious_classes.getTeamResults;
import asynchronious_classes.getInfo;
import classes.EnumTypes.EnterNamesType;
import classes.EnumTypes.SectionType;
import classes.NetworkConnection;

import com.squashbot.R;

import display_notification.RedToast;

public class TournamentDashboard extends Activity 
{

	static SectionType sectionType;
	public static Tournament selectedTournament;
	ArrayList<Tournament> tournaments;
	static Activity activity;

	//LOGS
	public static ArrayList<Log> logs = new ArrayList<Log>();
	//FIXTURES
	public static ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	//TEAM RESULTS
	public static ArrayList<TeamResult> results = new ArrayList<TeamResult>();
	//EVENTS
	public static ArrayList<Event> events = new ArrayList<Event>();
	//VENUES
	public static ArrayList<Venue> venues = new ArrayList<Venue>();
	//CONTACTS
	public static ArrayList<Contact> menCaptains = new ArrayList<Contact>();
	public static ArrayList<Contact> womenCaptains = new ArrayList<Contact>();
	public static ArrayList<Contact> tournamentOrganizers = new ArrayList<Contact>();
	//INFORMATION BOARD
	public static ArrayList<InformationBoard> messages = new ArrayList<InformationBoard>();

	private static Context context; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jarvis);
		
		tournaments = getIntent().getParcelableArrayListExtra("tournaments");
		selectedTournament = tournaments.get(0);
		
		ArrayList<String> menuItems = new ArrayList<String>();
		for(int x = 0; x < tournaments.size(); x++)
		{
			menuItems.add(tournaments.get(x).TournamentName());
		}
		
		context = TournamentDashboard.this;
		activity = this;
		final NetworkConnection connection = new NetworkConnection(context);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, menuItems);	

		OnNavigationListener callback = new OnNavigationListener()
		{
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId)
			{
				selectedTournament = tournaments.get(itemPosition);

				setTournametLogo();

				return false;
			}
		};
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionbar.setListNavigationCallbacks(adapter, callback);
		 		 
		 Button info = (Button)findViewById(R.id.btn_tournamentInfo);
		 info.setOnClickListener(new View.OnClickListener() 
		 {
			@Override
			public void onClick(View v)
			{
				if(connection.NetworkIsAvailable())
				{
					getTournamentInfo();
				}
				else
				{
					NoNetwork();
				}
			}
		});
		 
		 Button logs = (Button)findViewById(R.id.btn_tournamentLogs);
		 logs.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v)
			{
				sectionType = SectionType.Log;
				
				if(connection.NetworkIsAvailable())
				{
					getLogs();
				}
				else
				{
					NoNetwork();
				}
			}
		});
	
		 Button fixtures = (Button)findViewById(R.id.btn_tournamentFixtures);
		 fixtures.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				sectionType = SectionType.Fixture;
				
				if(connection.NetworkIsAvailable())
				{
					getFixtures(GetFixturesFor.ViewingFixtues);
				}
				else
				{
					NoNetwork();
				}
			}
		});
		 
		 Button results = (Button)findViewById(R.id.btn_tournamentResults);
		 results.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				sectionType = SectionType.TeamResult;
				if(connection.NetworkIsAvailable())
				{
					getTeamResults();
				}
				else
				{
					NoNetwork();
				}
			}
		});
	}

	private void setTournametLogo()
	{
		ImageView image = (ImageView)findViewById(R.id.tournamentDashboard_Image);
		if(selectedTournament.ID() == 11 || selectedTournament.ID() == 12)
		{
			image.setImageResource(R.drawable.jk_red);
		}
		else
		{
			image.setImageResource(R.drawable.squashbot_splash);
		}
	}

	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(this, Dashboard.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.tournamentdashboard, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.EnterTeamResult) 
		{
			EnterTeamResult();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void EnterTeamResult()
	{
		if (selectedTournament.isActive())
		{
			NetworkConnection connection = new NetworkConnection(context);
			if (connection.NetworkIsAvailable())
			{
				getFixtures(GetFixturesFor.EnteringTeamResultsFromDashboard);
			} else
			{
				RedToast toast = new RedToast(context);
				toast.NoNetworkConnection();
			}

		} else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "Tournament is not currently active", true, false);
		}
	}


	private void getLogs()
	{
		new getLogs(this, selectedTournament.ID()).execute();
	}
	
	private void getFixtures(GetFixturesFor ref)
	{
		new getFixtures(this, selectedTournament.ID(), ref).execute();
	}
	
	private void getTeamResults()
	{
		new getTeamResults(this, selectedTournament.ID()).execute();
	}
	
	private void getTournamentInfo()
	{
		new getInfo(context, selectedTournament.ID()).execute();
	}
	
	public static void GoToSections()
	{
		Intent intent = new Intent(context, Sections.class);
		intent.putExtra("selectedTournament", (Parcelable)selectedTournament);
		
		//this gets set on the button press
		intent.putExtra("sectionType", sectionType);
		
		context.startActivity(intent);
	}
	
	public static void GoToTournamentInfo()
	{
		if(messages.size() == 0 &&
				events.size() == 0 &&
				venues.size() == 0 &&
				menCaptains.size() == 0 &&
				womenCaptains.size() == 0 &&
				tournamentOrganizers.size() == 0)
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "No tournament information available", false, false);
		}
		else
		{
			Intent intent = new Intent(context, TournamentInfo.class);
			context.startActivity(intent);
		}
	}
	
	public static void GoToEnterTeamResults()
	{
		boolean unplayedFixtures = false;
		for(Fixture _fixture : fixtures)
		{
			if(!_fixture.ResultHasBeenSubmitted())
			{
				unplayedFixtures = true;
				break;
			}
		}

		if(unplayedFixtures || fixtures.size() == 0)
		{
			Intent intent = new Intent(context, EnterNames.class);
			intent.putExtra("resultType", EnterNamesType.EnterTeamResult);
			context.startActivity(intent);
			activity.finish();
		}
		else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "No fixtures available", true, false);
		}
	}
	
  	private void NoNetwork()
	{
		RedToast toast = new RedToast(context);
		toast.NoNetworkConnection();
	}

  	public enum GetFixturesFor
  	{
  		ViewingFixtues,
		EnteringTeamResultsFromDashboard,
		EnteringTeamResultsFromResults
  	}
}
