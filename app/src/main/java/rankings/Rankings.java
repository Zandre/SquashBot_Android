package rankings;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import asynchronious_classes.getRankings;
import classes.Club;
import classes.RankedPlayer;

import com.squashbot.R;

import fragments.ranking_fragment;

public class Rankings extends Activity
{

	ArrayList<Club> clubs;
	public static ArrayList<RankedPlayer> Women = new ArrayList<RankedPlayer>();
	public static ArrayList<RankedPlayer> Men = new ArrayList<RankedPlayer>();
	int menuPosition = 0;
	private static Context context;
	public static TabSelected _tab;
	RankingEditorMode mode;
	static Club selectedClub;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rankings);
		
		clubs = getIntent().getParcelableArrayListExtra("clubs");
		
		context = Rankings.this;
		
		final ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab men = actionbar.newTab();
		men.setText("Men");
		men.setIcon(R.drawable.men_40x40);
		men.setTabListener(new TabListener() 
		{
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) 
			{
				_tab = TabSelected.Men;
				GoToFragment();
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Tab women = actionbar.newTab();
		women.setText("Women");
		women.setIcon(R.drawable.women_40x40);
		women.setTabListener(new TabListener() 
		{
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) 
			{
				_tab = TabSelected.Women;
				GoToFragment();
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) 
			{
				// TODO Auto-generated method stub
				
			}
		});

		actionbar.addTab(men);
		actionbar.addTab(women);

		
		GetRankings();
	
		selectedClub = clubs.get(menuPosition);
	}
	
	public void GetRankings()
	{
		String phpLink = clubs.get(menuPosition).getLink();
		Women.clear();
		Men.clear();
		selectedClub = clubs.get(menuPosition);
		new getRankings(this, phpLink, selectedClub, true).execute();
	}
	
	public static void GoToFragment()
	{
		boolean gender = true;
		if(_tab == TabSelected.Men)
		{
			gender = true;
		}
		else
		{
			gender = false;
		}
		
		ListFragment list = new ranking_fragment(selectedClub, gender);
		Activity activity = (Activity)context;
		activity.getFragmentManager().beginTransaction().replace(R.id.container, list).commit();
		activity.getFragmentManager().removeOnBackStackChangedListener(null);
	}
	
	public void GoToRankingEditor()
	{
		Intent intent = new Intent(this, RankingEditor.class);
		intent.putExtra("mode", mode);
		intent.putExtra("club", clubs.get(menuPosition));
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.rankings, menu);
		//Make string array
		ArrayList<String> menuItems = new ArrayList<String>();
		for(int x = 0; x < clubs.size(); x++)
		{
			menuItems.add(clubs.get(x).getName());
		}
		
		//Add array into  overflow menu
		for(int x = 0; x < menuItems.size(); x++)
		{
			menu.add(menuItems.get(x));
		}
		
		
		MenuItem enterResult = menu.findItem(R.id.rankings_enterResult);
		MenuItem addMember = menu.findItem(R.id.rankings_AddMember);
		MenuItem editMember = menu.findItem(R.id.rankings_EditMember);
		MenuItem removeMember = menu.findItem(R.id.rankings_RemoveMember);
		
		if(clubs.get(menuPosition).getUseSquashBotRanker())
		{
			enterResult.setEnabled(true);
			addMember.setEnabled(true);
			editMember.setEnabled(true);
			removeMember.setEnabled(true);
		}
		else
		{
			enterResult.setEnabled(false);
			addMember.setEnabled(false);
			editMember.setEnabled(false);
			removeMember.setEnabled(false);
		}
		
		ActionBar actionbar = getActionBar();
		actionbar.setTitle(""+clubs.get(menuPosition).getName());
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		String menuText = (String) item.getTitle();
		

		if(id == R.id.rankings_Advertisement)
		{
			RankingsAdvertisement();
			return true;
		}
		else if(id == R.id.rankings_enterResult)
		{
			mode = RankingEditorMode.AddResult;
			GoToRankingEditor();
			return true;
		}
		else if(id == R.id.rankings_AddMember)
		{
			mode = RankingEditorMode.AddMember;
			GoToRankingEditor();
			return true;
		}
		else if(id == R.id.rankings_EditMember)
		{
			mode = RankingEditorMode.EditMember;
			GoToRankingEditor();
			return true;
		}
		else if(id == R.id.rankings_RemoveMember)
		{
			mode = RankingEditorMode.RemoveMember;
			GoToRankingEditor();
			return true;
		}
		else
		{
			for(int x = 0; x < clubs.size(); x++)
			{
				if(clubs.get(x).getName().equals(menuText))
				{
					menuPosition = x;

					//recreate Options Menu
					invalidateOptionsMenu();
					
					break;
				}
			}
			GetRankings();
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void RankingsAdvertisement()
	{
		Builder advertisement = new AlertDialog.Builder(this);
		advertisement.setTitle("SquashBot Rankings");
		advertisement.setMessage("Want to display your rankings on SquashBot? Easy! Just contact the SquashBot developers. SquashBot is customizeable to display the rankings of any country, province, or club.");
		advertisement.setCancelable(false);
		advertisement.setNeutralButton("OK", null);
		advertisement.show();
	}
	
	public enum TabSelected
	{
		Men,
		Women
	};

	public enum RankingEditorMode
	{
		AddResult,
		AddMember,
		EditMember,
		RemoveMember
	};

}
