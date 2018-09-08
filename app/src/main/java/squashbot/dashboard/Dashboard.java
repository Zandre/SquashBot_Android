package squashbot.dashboard;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.squashbot.R;

import java.io.IOException;
import java.util.ArrayList;

import asynchronious_classes.getClubs;
import asynchronious_classes.getTournaments;
import classes.Club;
import classes.NetworkConnection;
import database.IndividualResults_Helper;
import display_notification.RedToast;
import individual_results.Scoreboard;
import rankings.Rankings;
import tournament_activities.TournamentDashboard;
import tournament_classes.Tournament;

public class Dashboard extends Activity
{
	public static ArrayList<Club> clubs;
	public static ArrayList<Tournament> tournaments;
	private static Context context;
	Activity activity;
	
	@Override
 	protected void onCreate(Bundle savedInstanceState) 
	{
	    if (getIntent().getBooleanExtra("EXIT", false)) 
	    {
	         this.finish();
	    }
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		context = Dashboard.this;
		activity = this;

		IndividualResults_Helper database = new IndividualResults_Helper(null);
		database = new IndividualResults_Helper(this);
		try 
		{
			database.create();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			throw new Error("Unable to create database");
		}
		
		try
		{
			database.open();
		}
		catch(SQLException sqle)
		{
			throw sqle;
		}
		database.close();
		
		
		
		
		//Setup the ActionBar Title
		String versionName = "";
		try 
		{
			versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ActionBar actionbar = getActionBar();
		actionbar.setTitle("SquashBot\t\t"+versionName);
		
		Button score = (Button)findViewById(R.id.dashboard_score_game);
		score.setOnClickListener(new View.OnClickListener() 
		 {	
			@Override
			public void onClick(View v) 
			{
				Next();
			}
		});
		 
		Button tournamentButton = (Button)findViewById(R.id.dashboard_jarvis);
		tournamentButton.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				NetworkConnection connection = new NetworkConnection(context);
				if(connection.NetworkIsAvailable())
				{
					getTournaments();
				}
				else
				{
					NoNetwork();
				}
			}
		});
	
		Button results = (Button)findViewById(R.id.dashboard_Results);
		results.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				IndividualScoreboard();
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		LeaveApp();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == R.id.dashboard_leave) 
		{
			LeaveApp();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Next()
	{
			Intent intent = new Intent(this, OptionsMenu.class);
			startActivity(intent);
	}
	
	public static void GoToTournaments()
	{
		if(tournaments.size() >= 1)
		{
			Intent intent = new Intent(context, TournamentDashboard.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("tournaments", tournaments);
			context.startActivity(intent);
		}
		else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "Currently no active tournaments", true, false);
		}
	}
	
	public void getClubs()
	{
		new getClubs(this).execute();
	}
	
	public void getTournaments()
	{
		new getTournaments(this).execute();
	}
	
	public static void GoToRankings()
	{
		if(clubs.size() >= 1)
		{
			Intent intent = new Intent(context, Rankings.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("clubs", clubs);
			context.startActivity(intent);
		}
		else
		{
			RedToast toast = new RedToast(context);
			toast.MakeRedToast(context, "No active clubs", true, false);
		}
	}
	
	public void IndividualScoreboard()
	{
		Intent intent = new Intent(this, Scoreboard.class);
		startActivity(intent);
	}

	public void LeaveApp()
	{
		activity.finish();
	}
	
	private void NoNetwork()
	{
		RedToast toast = new RedToast(context);
		toast.NoNetworkConnection();
	}
}
