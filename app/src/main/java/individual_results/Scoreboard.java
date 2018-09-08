package individual_results;

import java.util.ArrayList;

import squashbot.dashboard.Dashboard;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import classes.Result;

import com.squashbot.R;

import database.IndividualResults_Helper;
import display_notification.RedToast;
import fragments.ResultsFragment;

public class Scoreboard extends Activity 
{
	ArrayList<Result> results;
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard);
		
		context = Scoreboard.this;
		
		ActionBar actionbar = getActionBar();
		actionbar.setTitle("Individual Results");
		actionbar.setLogo(R.drawable.ic_action_view_as_list);

		
		IndividualResults_Helper db = new IndividualResults_Helper(Scoreboard.this);
		results = db.getResults();
		
		ListFragment list = new ResultsFragment(results);
		
		getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(this, Dashboard.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.scoreboard, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		boolean action;
		int id = item.getItemId();
		if (id == R.id.delete) 
		{
			if(results.size() > 0)
			{
				Intent intent = new Intent(this, SelectResult.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				action = true;
				intent.putExtra("action", action);
				finish();
				startActivity(intent);

			}
			else
			{
				NoResultsMessage("delete");
			}
			return true;
		}
		else if (id == R.id.share)
		{
			if(results.size() > 0)
			{
				Intent intent = new Intent(this, SelectResult.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				action = false;
				intent.putExtra("action", action);
				finish();
				startActivity(intent);
			}
			else
			{
				NoResultsMessage("share");
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void NoResultsMessage(String type)
	{
		RedToast toast = new RedToast(context);
		toast.MakeRedToast(context, "No results to "+type, false, false);
	}

}
