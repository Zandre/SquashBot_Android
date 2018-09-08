package fragments;

import java.util.ArrayList;

import rankings.Rankings;
import rankings.Rankings.TabSelected;
import classes.Club;
import classes.RankedPlayer;
import ExternalApplications.SharingIsCaring;
import adapters.RankingAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class ranking_fragment extends ListFragment
{
	Club club;
	boolean gender;
		
	@SuppressLint("ValidFragment")
	public ranking_fragment(Club club, boolean gender)
	{
		this.club = club;
		this.gender = gender;
	}
	
	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);
		ArrayList<RankedPlayer> rankings;
		if(Rankings._tab == TabSelected.Men)
		{
			rankings = Rankings.Men;
		}
		else
		{
			rankings = Rankings.Women;
		}

		getListView().setDividerHeight(0);
		
		RankingAdapter adapter = new RankingAdapter(getActivity(), rankings);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int position, long id)
	{
		super.onListItemClick(list, view, position, id);
		
		AlertDialog.Builder popup = new AlertDialog.Builder(getActivity());
		popup.setCancelable(true);
		popup.setTitle("Share Log?");
		
		String message = club.getName();
		if(gender)
		{
			message += "\t-\t Men";
		}
		else
		{
			message += "\t-\t Women";
		}
		popup.setMessage(message);
		
		popup.setPositiveButton("Share", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				SharingIsCaring share = new SharingIsCaring(getActivity());
				if(Rankings._tab == TabSelected.Men)
				{
					share.ShareRankings(Rankings.Men, club.getName(), "Men");
				}
				else
				{
					share.ShareRankings(Rankings.Women, club.getName(), "Women");
				}
			}
		});
		
		popup.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
			}
		});
		popup.show();
	}
}
