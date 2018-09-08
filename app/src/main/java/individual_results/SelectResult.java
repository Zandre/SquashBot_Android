package individual_results;

import java.util.ArrayList;

import com.squashbot.R;

import classes.Result;
import database.IndividualResults_Helper;
import display_notification.BlueToast;
import display_notification.RedToast;
import fragments.SelectResultsFragment;
import ExternalApplications.SharingIsCaring;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

public class SelectResult extends Activity 
{
	private Menu menu;
	boolean action;
	static ArrayList<Result> selected_results = new ArrayList<Result>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_result);

		
		ActionBar actionbar = getActionBar();
		 actionbar.setTitle("Select a result");
		actionbar.setLogo(R.drawable.ic_action_view_as_list);
		
		//true -> delete
		//false -> share
		action = getIntent().getBooleanExtra("action", false);
		Bundle bundle = new Bundle();
		bundle.putBoolean("action", action);
		
		ListFragment select_results = new SelectResultsFragment();
		select_results.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.container, select_results).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		this.menu = menu;
		getMenuInflater().inflate(R.menu.select_result, menu);
		setOptionTitle();
		return true;
	}
	
	private void setOptionTitle()
	{
		MenuItem item = menu.findItem(R.id.share_or_delete);
		if(action == false)
		{
			item.setTitle("Share");
		}
		else
		{
			item.setTitle("Delete");
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if(id == R.id.share_or_delete)
		{
			if(action == true)
			{
				DeleteSelectedResults();
			}
			else
			{
				ShareSelectedResults();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		ReturnToScoreBoard();
	}

	private void DeleteSelectedResults()
	{
		if(selected_results.size() > 0)
		{
			IndividualResults_Helper db = new IndividualResults_Helper(getApplicationContext());
			db.RemoveResults(selected_results);
			ReturnToScoreBoard();
		}
		else
		{
			NoResultsSelected();
		}
	}
	
	private void ShareSelectedResults()
	{
		if(selected_results.size() > 0)
		{
			SharingIsCaring share = new SharingIsCaring(this);
			share.ShareIndividualResults(selected_results);
		}
		else
		{
			NoResultsSelected();
		}

	}
	
	private void NoResultsSelected()
	{
		RedToast toast = new RedToast(this);
		toast.NoResultsSelected();
	}
	
	private void ReturnToScoreBoard()
	{
		Intent intent = new Intent(getApplicationContext(), Scoreboard.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
	public static void addSelectedResult(Result result)
	{
		selected_results.add(result);
	}
}
