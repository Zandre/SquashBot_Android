package tournament_activities;

import asynchronious_classes.getFixtures;
import classes.EnumTypes;
import classes.NetworkConnection;
import display_notification.RedToast;
import squashbot.dashboard.EnterNames;
import tournament_classes.Fixture;
import tournament_classes.SectionTabCreator;
import tournament_classes.Tournament;
import classes.EnumTypes.SectionType;

import com.squashbot.R;

import fragments.Section_Fragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Sections extends Activity 
{
	static SectionType sectionType;
	Tournament selectedTournament;
	static Context context;
	static Activity activity;

	//FIXTURES
	public static ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logs);
		
		sectionType = (SectionType)getIntent().getSerializableExtra("sectionType");
		selectedTournament = getIntent().getParcelableExtra("selectedTournament");
		ActionBar actionbar = getActionBar();
		context = Sections.this;
		activity = this;
		
		SectionTabCreator tabCreator = new SectionTabCreator(context, actionbar, selectedTournament);
	}
	
	public static void GoToFragment(String gender, String section)
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable("Gender", gender);
		bundle.putSerializable("Section", section);
		bundle.putSerializable("sectionType", sectionType);

		ListFragment list = new Section_Fragment();
		list.setArguments(bundle);
		((Activity) context).getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if(sectionType == SectionType.TeamResult || sectionType == SectionType.Fixture)
		{
			getMenuInflater().inflate(R.menu.tournamentdashboard, menu);
		}
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
				getFixtures(TournamentDashboard.GetFixturesFor.EnteringTeamResultsFromResults);
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

	private void getFixtures(TournamentDashboard.GetFixturesFor ref)
	{
		new getFixtures(this, selectedTournament.ID(), ref).execute();
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
			intent.putExtra("resultType", EnumTypes.EnterNamesType.EnterTeamResult);
			context.startActivity(intent);
			activity.finish();
		}
		else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "No fixtures available", true, false);
		}
	}
}
