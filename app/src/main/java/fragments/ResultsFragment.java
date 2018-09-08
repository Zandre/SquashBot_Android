package fragments;

import java.util.ArrayList;

import classes.Result;
import database.IndividualResults_Helper;
import adapters.ResultsAdapter;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class ResultsFragment extends ListFragment
{
	
	ArrayList<Result> results;


	public ResultsFragment(ArrayList<Result> results)
	{
		this.results = results;
	}
	
	public ResultsFragment()
	{

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		getListView().setDividerHeight(0);
		
		ResultsAdapter adapter = new ResultsAdapter(getActivity(), results);
		setListAdapter(adapter);
	}

}
