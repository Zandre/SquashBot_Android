package fragments;

import java.util.ArrayList;

import adapters.SelectResultsAdapter;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import classes.Result;
import database.IndividualResults_Helper;

@SuppressLint("ValidFragment")
public class SelectResultsFragment extends ListFragment
{
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		IndividualResults_Helper db = new IndividualResults_Helper(getActivity());
		ArrayList<Result> results = db.getResults();
		
		Bundle bundle = this.getArguments();
		boolean action = bundle.getBoolean("action", false);
		
		SelectResultsAdapter adapter = new SelectResultsAdapter(getActivity(), results, action);
		setListAdapter(adapter);
	}
}
