package fragments;

import java.util.ArrayList;

import tournament_activities.Filter;
import tournament_activities.TournamentDashboard;
import tournament_activities.VerifyResult;
import tournament_activities.Filter.FilterType;
import tournament_classes.Fixture;
import tournament_classes.Log;
import tournament_classes.TeamResult;
import ExternalApplications.AddCalendarEvent;
import ExternalApplications.SharingIsCaring;
import ExternalApplications.SharingIsCaring.ShareFormat;
import adapters.Fixture_Adapter;
import adapters.Log_Adapter;
import adapters.TeamResult_Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import classes.EnumTypes.SectionType;

import com.squashbot.R;

@SuppressLint("ValidFragment")
public class Section_Fragment extends ListFragment
{
	String gender, section;
	SectionType type;
	
	ArrayList<Log> log = new ArrayList<Log>();
	ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	ArrayList<TeamResult> results = new ArrayList<TeamResult>();
	
	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);

		String Gender = (String)getArguments().getSerializable("Gender");
		String Section = (String)getArguments().getSerializable("Section");
		SectionType sectionType = (SectionType) getArguments().getSerializable("sectionType");
		type = sectionType;
		registerForContextMenu(getListView());
		
		switch (sectionType) 
		{
			case Log:
			{
				System.out.println("Logs:"+TournamentDashboard.logs.size());
					for(int x = 0; x < TournamentDashboard.logs.size(); x++)
					{
						if(TournamentDashboard.logs.get(x).Gender().contains(Gender) &&
								TournamentDashboard.logs.get(x).Section().contains(Section))
						{
							log.add(TournamentDashboard.logs.get(x));
						}
					}
					
					getListView().setDividerHeight(0);
					
					Log_Adapter adapter = new Log_Adapter(getActivity(), log);
					setListAdapter(adapter);
			}
			break;
				
			
			case Fixture:
			{
					for(int x = 0; x < TournamentDashboard.fixtures.size(); x++)
					{
						if(TournamentDashboard.fixtures.get(x).Gender().contains(Gender) &&
								TournamentDashboard.fixtures.get(x).Section().contains(Section))
						{
							fixtures.add(TournamentDashboard.fixtures.get(x));
						}
					}
					
					getListView().setDividerHeight(0);
					
					Fixture_Adapter adapter = new Fixture_Adapter(getActivity(), fixtures);
					setListAdapter(adapter);
			}
			break;
			
			
			case TeamResult:
			{
					for(int x = 0; x < TournamentDashboard.results.size(); x++)
					{
						if(TournamentDashboard.results.get(x).Gender().contains(Gender) &&
								TournamentDashboard.results.get(x).Section().contains(Section))
						{
							results.add(TournamentDashboard.results.get(x));
						}
					}
					
					getListView().setDividerHeight(0);
					
					TeamResult_Adapter adapter = new TeamResult_Adapter(getActivity(), results);
					setListAdapter(adapter);
			}
			break;
	
			default:
				break;
		}
	}
	
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;

	    MenuInflater inflater = this.getActivity().getMenuInflater();

		switch (type) 
		{
			case Log:
			{
				Log _log = log.get(selectedItem.position);
				menu.setHeaderTitle(_log.TeamName());

				inflater.inflate(R.menu.contextualmenu_log, menu);
			}
			break;
			
			case Fixture:
			{
				Fixture fixture = fixtures.get(selectedItem.position);
				menu.setHeaderTitle(fixture.TeamA_Name() + " vs " + fixture.TeamB_Name());

				inflater.inflate(R.menu.contextualmenu_fixtures, menu);
			}
			break;
			
			case TeamResult:
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
			//Remember that whatever you do here also needs to be added to the Filter.java class
		
			case Log:
			{
				switch(item.getItemId())
				{
					case R.id.log_shareLog:
						ShareLog();
						break;
						
					case R.id.log_viewFixtures:
						Intent intent = new Intent(getActivity(), Filter.class);
						intent.putExtra("type", FilterType.FilterOnFixtures);
						intent.putExtra("logFile", log.get(position));
						startActivity(intent);
						break;

					case R.id.log_viewUpcomingFixture:
						Intent __intent = new Intent(getActivity(), Filter.class);
						__intent.putExtra("type", FilterType.FilterOnUpcomingFixture);
						__intent.putExtra("logFile", log.get(position));
						startActivity(__intent);
						break;
						
					case R.id.log_viewResults:
						Intent _intent = new Intent(getActivity(), Filter.class);
						_intent.putExtra("type", FilterType.FilterOnResults);
						_intent.putExtra("logFile", log.get(position));
						startActivity(_intent);
						break;
				}
			}
			break;
			
			
			
			case Fixture:
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
			
			
			
			case TeamResult:
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
		AlertDialog.Builder popup = new AlertDialog.Builder(getActivity());
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
	
	private void ShareLog()
	{
		SharingIsCaring share = new SharingIsCaring(getActivity());
		share.ShareLog(log);
	}

	private void ShareFixtures(ArrayList<Fixture> fixtures)
	{
		SharingIsCaring share = new SharingIsCaring(getActivity());
		share.ShareFixtures(fixtures);
	}
	
	private void AddFixturesToCalendar(ArrayList<Fixture> fixtures)
	{
		AddCalendarEvent addCalendar = new AddCalendarEvent(getActivity());
		addCalendar.AddFixturesToCalendar(fixtures);
	}
	
	private void ShareResults(ArrayList<TeamResult> results, ShareFormat format)
	{
		SharingIsCaring share = new SharingIsCaring(getActivity());
		share.ShareTeamResult(results, format);
	}
}
