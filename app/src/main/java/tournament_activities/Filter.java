package tournament_activities;

import java.util.ArrayList;

import com.squashbot.R;
import com.squashbot.R.id;
import com.squashbot.R.layout;
import com.squashbot.R.menu;

import tournament_classes.Fixture;
import tournament_classes.Log;
import tournament_classes.TeamResult;
import ExternalApplications.AddCalendarEvent;
import ExternalApplications.SharingIsCaring;
import ExternalApplications.SharingIsCaring.ShareFormat;
import adapters.Fixture_Adapter;
import adapters.TeamResult_Adapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import asynchronious_classes.getFixturesForSpecificTeam;
import asynchronious_classes.getTeamResultsForSpecificTeam;

public class Filter extends Activity 
{
	
	static FilterType type;
	Log logFile;
	Context context;
	
	public static ArrayList<TeamResult> results = new ArrayList<TeamResult>();
	public static ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		context = Filter.this;
		
		type = (FilterType)getIntent().getSerializableExtra("type");
		logFile = getIntent().getParcelableExtra("logFile");
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(logFile.TeamName() + " (" + logFile.Gender()+", "+logFile.Section()+ " "+TournamentDashboard.selectedTournament.NamingConvention()+")");
		
		getData();
	}

	private void getData()
	{
		switch (type)
		{
			case FilterOnFixtures:
				new getFixturesForSpecificTeam(this, logFile.TournamentID(), logFile.ID(), logFile.TeamName(), logFile.Gender(), logFile.Section(), FilterType.FilterOnFixtures).execute();
				break;

			case FilterOnUpcomingFixture:
				new getFixturesForSpecificTeam(this, logFile.TournamentID(), logFile.ID(), logFile.TeamName(), logFile.Gender(), logFile.Section(), FilterType.FilterOnUpcomingFixture).execute();
				break;
				
			case FilterOnResults:
				new getTeamResultsForSpecificTeam(this, logFile.TournamentID(), logFile.ID(), logFile.TeamName(), logFile.Gender(), logFile.Section()).execute();
				break;
		}
	}
	
	public static class FixturesForSpecificTeamFragment extends ListFragment
	{
		@Override
		public void onActivityCreated(Bundle bundle)
		{
			super.onActivityCreated(bundle);
			
			getListView().setDividerHeight(0);
			registerForContextMenu(getListView());
			
			Fixture_Adapter adapter = new Fixture_Adapter(getActivity(), fixtures);
			setListAdapter(adapter);
		}
	}
	
	public static class ResultsForSpecificTeamFragment extends ListFragment
	{
		@Override
		public void onActivityCreated(Bundle bundle)
		{
			super.onActivityCreated(bundle);
			
			getListView().setDividerHeight(0);
			registerForContextMenu(getListView());
			
			TeamResult_Adapter adapter = new TeamResult_Adapter(getActivity(), results);
			setListAdapter(adapter);
		}
	}
	
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;

		MenuInflater inflater = getMenuInflater();

		switch (type) 
		{
			case FilterOnFixtures:
			{
				Fixture fixture = fixtures.get(selectedItem.position);
				menu.setHeaderTitle(fixture.TeamA_Name() + " vs " + fixture.TeamB_Name());

				inflater.inflate(R.menu.contextualmenu_fixtures, menu);
			}
			break;
			
			case FilterOnResults:
			{
				TeamResult teamResult = results.get(selectedItem.position);
				menu.setHeaderTitle(teamResult.getWinningTeam() + " vs " + teamResult.getLosingTeam());

				inflater.inflate(R.menu.contextualmenu_results, menu);
			}
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		android.widget.AdapterView.AdapterContextMenuInfo selectedItem = (android.widget.AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int position = selectedItem.position;
		
		switch (type) 
		{
			case FilterOnFixtures:
			{
				switch (item.getItemId())
				{
					case R.id.fixtures_shareThisFixture:
						ArrayList<Fixture> _fixtures = new ArrayList<Fixture>();
						_fixtures.add(fixtures.get(position));
						ShareFixtures(_fixtures);
						break;
						
					case R.id.fixtures_shareAllFixtures:
						ShareFixtures(fixtures);
						break;
						
					case R.id.fixtures_addThisToCalendar:
						ArrayList<Fixture> _Fixtures = new ArrayList<Fixture>();
						_Fixtures.add(fixtures.get(position));
						AddFixturesToCalendar(_Fixtures);
						break;
						
					case R.id.fixtures_addAllToCalendar:
						AddFixturesToCalendar(fixtures);
				}
			}
			break;
			
			
			
			case FilterOnResults:
			{
				switch(item.getItemId())
				{
				case R.id.results_shareThisResult:
					ArrayList<TeamResult> _results = new ArrayList<TeamResult>();
					_results.add(results.get(position));
					ChooseResultFormat(_results);
					break;
					
				case R.id.results_shareAllResults:
					ChooseResultFormat(results);
					break;
				}
			}
			break;
		}
		return false;
	}
	
	private void ChooseResultFormat(final ArrayList<TeamResult> results)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(context);
		popup.setTitle("Choose Format");
		popup.setCancelable(true);
		
		popup.setPositiveButton("SquashBot Format", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ShareResults(results, ShareFormat.SquashBotFormat);
			}
		});
		popup.setNeutralButton("Result Only", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ShareResults(results, ShareFormat.ResultOnly);
			}
		});
		popup.setNegativeButton("Scores Only", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ShareResults(results, ShareFormat.ScoresOnly);
			}
		});
		popup.show();

	}
	
	private void ShareFixtures(ArrayList<Fixture> fixtures)
	{
		SharingIsCaring share = new SharingIsCaring(context);
		share.ShareFixtures(fixtures);
	}
	
	private void ShareResults(ArrayList<TeamResult> results, ShareFormat format)
	{
		SharingIsCaring share = new SharingIsCaring(context);
		share.ShareTeamResult(results, format);
	}
	
	private void AddFixturesToCalendar(ArrayList<Fixture> fixtures)
	{
		AddCalendarEvent addCalendar = new AddCalendarEvent(context);
		addCalendar.AddFixturesToCalendar(fixtures);
	}
	
	public enum FilterType
	{
		FilterOnFixtures,
		FilterOnUpcomingFixture,
		FilterOnResults
	};
}
