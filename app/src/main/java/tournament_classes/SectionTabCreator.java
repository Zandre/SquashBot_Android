package tournament_classes;

import tournament_activities.Sections;

import com.squashbot.R;

import fragments.Venue_Fragment;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Context;

public class SectionTabCreator 
{
	Context context;
	ActionBar actionbar;
	Tournament tournament;
	
	public SectionTabCreator(Context context, ActionBar actionbar, Tournament tournament)
	{
		this.context = context;
		this.actionbar = actionbar;
		this.tournament = tournament;
		
		CreateTabs();
	}
	
	private void CreateTabs()
	{
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle(""+tournament.TournamentName());
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.setDisplayShowHomeEnabled(false);
		
		for(int x = 0; x < tournament.getTournamentSections().size(); x++)
		{
			Tab tab = actionbar.newTab();
			
			//TITLE TEXT
			tab.setText(tournament.getTournamentSections().get(x).Section() + " " + tournament.NamingConvention());
			
			
			//GENDER
			if(tournament.getTournamentSections().get(x).Gender().contains("Men"))
			{
				tab.setIcon(R.drawable.men_40x40);
			}
			else if(tournament.getTournamentSections().get(x).Gender().contains("Women"))
			{
				tab.setIcon(R.drawable.women_40x40);
			}
			
			//LISTENER
			final int index = x;
			tab.setTabListener(new TabListener()
			{
				@Override
				public void onTabUnselected(Tab arg0, FragmentTransaction arg1) 
				{

				}
				
				@Override
				public void onTabSelected(Tab arg0, FragmentTransaction arg1) 
				{
					
					Sections.GoToFragment(tournament.getTournamentSections().get(index).Gender(), tournament.getTournamentSections().get(index).Section());
				}
				
				@Override
				public void onTabReselected(Tab arg0, FragmentTransaction arg1) 
				{

				}
			});
			
			
			//ADD TAB TO ACTIONBAR
			actionbar.addTab(tab);
		}
	}

}
