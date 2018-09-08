package tournament_activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import squashbot.dashboard.Dashboard;
import tournament_classes.GameResult;
import tournament_classes.ResultForTeam;
import tournament_classes.TeamResultHelper;
import tournament_classes.TournamentSection;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import asynchronious_classes.JSONParser;
import classes.LoadingCircle;
import classes.NetworkConnection;
import classes.Result;

import com.google.gson.Gson;
import com.squashbot.R;

import database.IndividualResults_Helper;
import display_notification.RedToast;

import static android.R.layout.simple_list_item_1;
//import classes.ResultForTeam;

public class MatchResult extends Activity 
{
	TeamResultHelper helper;
	private static Context context;
	private ArrayList<Result> databaseResults = new ArrayList<Result>();
	private Menu menu;
	Activity activity;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_result);
		
		context = MatchResult.this;
		helper = (TeamResultHelper) getIntent().getParcelableExtra("helper");
		activity = this;
		 
		IndividualResults_Helper databaseHelper = new IndividualResults_Helper(context);
		databaseResults = databaseHelper.getResults();
		
		 SetupScreen();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.match_result, menu);
		this.menu = menu;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.matchResult_next) 
		{
			if(MayProceed())
			{
				//CREATE NEW RESULT AND ADD IT TO THE REST
				ResultForTeam newResult = CreateNewResult();
				if(helper.IndividualResults().size() >= helper.Index())
				{
					helper.ReplaceIndividualResult(newResult);
				}
				else
				{
					helper.AddResult(newResult);
				}

				
				//DECIDE WHERE TO GO
				if(helper.Type() == MatchResultEnum.EditResult)
				{
					//helper.CalculateFinalScore();
					new CalculateFinalTeamScore(this).execute();
				}
				else if (helper.Type() == MatchResultEnum.AddNewResult || helper.Type() == MatchResultEnum.PreviousNextMode)
				{
					if(helper.Index() < helper.Fixture().PlayersPerTeam())
					{
						NEXT();
					}
					else
					{
						//helper.CalculateFinalScore();
						NetworkConnection connection = new NetworkConnection(context);
						if(connection.NetworkIsAvailable())
						{
							new CalculateFinalTeamScore(this).execute();
						}
						else
						{
							RedToast toast = new RedToast(context);
							toast.NoNetworkConnection();
						}
					}
				}
			}
			return true;
		}
		else if(id == R.id.matchResult_previous)
		{
			if(helper.Index() == 1)
			{
				onBackPressed();
			}
			else
			{
				//return to the score of the previous player
				PREVIOUS();
			}
		}
		else if(id == R.id.matchResult_import)
		{
			RelativeLayout layout = (RelativeLayout)findViewById(R.id.MatchResult_resultList);
			if(layout.getVisibility() == View.INVISIBLE)
			{
				if(databaseResults.size() > 0)
				{
					layout.setVisibility(View.VISIBLE);
					layout.setFocusable(true);
					layout.bringToFront();
					item.setTitle("Hide");
				}
				else
				{
					RedToast toast = new RedToast(context);
					toast.MakeRedToast(context, "You do not currently have any saved results", false, false);
				}
			}
			else
			{
				layout.setVisibility(View.INVISIBLE);
				item.setTitle("Import");
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void SetupScreen()
	{
		 ActionBar actionbar = getActionBar();
		 actionbar.setTitle(helper.Tournament().TournamentName());
		 
		 TextView header = (TextView)findViewById(R.id.tv_MatchResultHeader);
		 EditText etA = (EditText)findViewById(R.id.etA_MatchResult);
		 EditText etB = (EditText)findViewById(R.id.etB_MatchResult);
		 ListView resultsList = (ListView)findViewById(R.id.lv_MatcResultresults);
		 Spinner A1 = (Spinner)findViewById(R.id.spinnerA1);
		 Spinner A2 = (Spinner)findViewById(R.id.spinnerA2);
		 Spinner A3 = (Spinner)findViewById(R.id.spinnerA3);
		 Spinner A4 = (Spinner)findViewById(R.id.spinnerA4);
		 Spinner A5 = (Spinner)findViewById(R.id.spinnerA5);
		 Spinner B1 = (Spinner)findViewById(R.id.spinnerB1);
		 Spinner B2 = (Spinner)findViewById(R.id.spinnerB2);
		 Spinner B3 = (Spinner)findViewById(R.id.spinnerB3);
		 Spinner B4 = (Spinner)findViewById(R.id.spinnerB4);
		 Spinner B5 = (Spinner)findViewById(R.id.spinnerB5);
		ArrayList<Spinner> OrangeSpinners = new ArrayList<Spinner>();
		ArrayList<Spinner> BlueSpinners = new ArrayList<Spinner>();
		OrangeSpinners.add(A1);
		OrangeSpinners.add(A2);
		OrangeSpinners.add(A3);
		OrangeSpinners.add(A4);
		OrangeSpinners.add(A5);
		BlueSpinners.add(B1);
		BlueSpinners.add(B2);
		BlueSpinners.add(B3);
		BlueSpinners.add(B4);
		BlueSpinners.add(B5);

		Integer[] scores = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};

		for(Spinner spinner: BlueSpinners)
		{
			ArrayAdapter adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_expandable_list_item_1, scores)
			{
				@Override
				public View getView(int position, View convertView, ViewGroup parent)
				{
					View view = super.getView(position, convertView, parent);
					TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
					tv1.setTextColor(getResources().getColor(R.color.Black));
					return view;
				};

				@Override
				public View getDropDownView(int position, View convertView, ViewGroup parent)
				{
					View view = super.getView(position, convertView, parent);

					view.setBackgroundColor(getResources().getColor(R.color.LightCyan));
					TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
					tv1.setTextColor(getResources().getColor(R.color.OrangeRed));
					return view;
				}
			};
			spinner.setAdapter(adapter);
			spinner.setSelection(0);
		}

		for(Spinner spinner: OrangeSpinners)
		{
			ArrayAdapter adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_expandable_list_item_1, scores)
			{
				@Override
				public View getView(int position, View convertView, ViewGroup parent)
				{
					View view = super.getView(position, convertView, parent);
					TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
					tv1.setTextColor(getResources().getColor(R.color.Navy));
					return view;
				}

				@Override
				public View getDropDownView(int position, View convertView, ViewGroup parent)
				{
					View view = super.getView(position, convertView, parent);
					view.setBackgroundColor(getResources().getColor(R.color.Orange));
					TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
					tv1.setTextColor(getResources().getColor(R.color.Navy));
					return view;
				}
			};
			
			spinner.setAdapter(adapter);
			spinner.setSelection(0);
		}
		
		 header.setText(helper.Fixture().Gender()+", "+helper.Fixture().Section()+" "+helper.Tournament().NamingConvention());
		 
		//SET TEXTS AND SPINNERS 
		if(helper.Type() == MatchResultEnum.AddNewResult)
		{
			 etA.setHint(helper.Fixture().TeamA_Name() + " player #" + helper.Index());
			 etB.setHint(helper.Fixture().TeamB_Name()  + " player #" + helper.Index());
		}
		else if(helper.Type() == MatchResultEnum.PreviousNextMode || helper.Type() == MatchResultEnum.EditResult)
		{
			etA.setText(helper.IndividualResults().get(helper.Index()-1).PlayerA());
			etB.setText(helper.IndividualResults().get(helper.Index()-1).PlayerB());
		}
		
		
		//LISTVIEW WITH DATABASE RESULTS
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for(int x = 0; x < databaseResults.size(); x++)
		{
			Map<String, String> F = new HashMap<String, String>(2);
			F.put("Result", databaseResults.get(x).Outcome());
			F.put("Date", databaseResults.get(x).FriendlyDate());
			data.add(F);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(context, data,
													android.R.layout.simple_expandable_list_item_2,
													new String[]{"Result", "Date"},
													new int[]{android.R.id.text1, android.R.id.text2})
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
				TextView tv2 = (TextView)view.findViewById(android.R.id.text2);
				
				tv1.setTextColor(getResources().getColor(R.color.Black));
				tv2.setTextColor(getResources().getColor(R.color.Blue));
				
				return view;
			};
		};
		
		resultsList.setAdapter(adapter);
		
		resultsList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				MenuItem importResult = menu.findItem(R.id.matchResult_import);
				importResult.setTitle("Import");
				
				Result selectedResult = databaseResults.get(position);
				RelativeLayout layout = (RelativeLayout)findViewById(R.id.MatchResult_resultList);
				layout.setVisibility(View.INVISIBLE);
				SelectTeamDialog(selectedResult);
			}
		});
	}
	
	private void PrepareScreenForWalkover(SelectedTeam team)
	{
		 Spinner A1 = (Spinner)findViewById(R.id.spinnerA1);
		 Spinner A2 = (Spinner)findViewById(R.id.spinnerA2);
		 Spinner A3 = (Spinner)findViewById(R.id.spinnerA3);
		 Spinner A4 = (Spinner)findViewById(R.id.spinnerA4);
		 Spinner A5 = (Spinner)findViewById(R.id.spinnerA5);
		 Spinner B1 = (Spinner)findViewById(R.id.spinnerB1);
		 Spinner B2 = (Spinner)findViewById(R.id.spinnerB2);
		 Spinner B3 = (Spinner)findViewById(R.id.spinnerB3);
		 Spinner B4 = (Spinner)findViewById(R.id.spinnerB4);
		 Spinner B5 = (Spinner)findViewById(R.id.spinnerB5);
		 
		 TournamentSection selectedSection = new TournamentSection();
		 
		 for(int x = 0; x < helper.Tournament().getTournamentSections().size(); x++)
		 {
			 if(helper.Tournament().getTournamentSections().get(x).Gender().contains(helper.Fixture().Gender()) &&
					 helper.Tournament().getTournamentSections().get(x).Section().contains(helper.Fixture().Section()))
			 {
				 selectedSection = helper.Tournament().getTournamentSections().get(x);
			 }
		 }
		 
		 if(team == SelectedTeam.TeamA)
		 {
			 A1.setSelection(selectedSection.GamePar());
			 A2.setSelection(selectedSection.GamePar());
			 A3.setSelection(selectedSection.GamePar());
			 
			 A4.setSelection(0);
			 A5.setSelection(0);
			 B1.setSelection(0);
			 B2.setSelection(0);
			 B3.setSelection(0);
			 B4.setSelection(0);
			 B5.setSelection(0);
		 }
		 else if (team == SelectedTeam.TeamB)
		 {
			 B1.setSelection(selectedSection.GamePar());
			 B2.setSelection(selectedSection.GamePar());
			 B3.setSelection(selectedSection.GamePar());
			 
			 B4.setSelection(0);
			 B5.setSelection(0);
			 A1.setSelection(0);
			 A2.setSelection(0);
			 A3.setSelection(0);
			 A4.setSelection(0);
			 A5.setSelection(0);
		 }
	}
	
	private void SelectTeamDialog(final Result result)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(MatchResult.this);
		popup.setTitle("Choose Team");
		popup.setMessage("For which team has "+ result.getWinner() + " played for?");
		popup.setCancelable(false);
		
		popup.setPositiveButton("Team A", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{	
				SelectedTeam team = SelectedTeam.TeamA;
				PrepareScreenFromImportedResult(result, team);
				dialog.cancel();
			}
		});
		popup.setNegativeButton("TeamB", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{		
				SelectedTeam team = SelectedTeam.TeamB;
				PrepareScreenFromImportedResult(result, team);
				dialog.cancel();
			}
		});
	
		popup.show();
	}
	
	private void PrepareScreenFromImportedResult(Result result, SelectedTeam team)
	{
		 EditText etA = (EditText)findViewById(R.id.etA_MatchResult);
		 EditText etB = (EditText)findViewById(R.id.etB_MatchResult);
		 Spinner A1 = (Spinner)findViewById(R.id.spinnerA1);
		 Spinner A2 = (Spinner)findViewById(R.id.spinnerA2);
		 Spinner A3 = (Spinner)findViewById(R.id.spinnerA3);
		 Spinner A4 = (Spinner)findViewById(R.id.spinnerA4);
		 Spinner A5 = (Spinner)findViewById(R.id.spinnerA5);
		 Spinner B1 = (Spinner)findViewById(R.id.spinnerB1);
		 Spinner B2 = (Spinner)findViewById(R.id.spinnerB2);
		 Spinner B3 = (Spinner)findViewById(R.id.spinnerB3);
		 Spinner B4 = (Spinner)findViewById(R.id.spinnerB4);
		 Spinner B5 = (Spinner)findViewById(R.id.spinnerB5);
		 
		 //PARSE SCORES INTO ARRAY
		 String scores = result.getScores();	 
		 scores = scores.replaceAll("[^0-9]+$", "");
		 String[] stringArray = scores.split(",");
		 
		 ArrayList<Integer> scoresA = new ArrayList<Integer>();
		 ArrayList<Integer> scoresB = new ArrayList<Integer>();	 
		 
		 for(int x = 0; x <= stringArray.length-1; x++)
		 {
			 String[] individualScores = stringArray[x].split("-");
			 
			 individualScores[0] = individualScores[0].replaceAll("[^0-9]+$", "");
			 individualScores[1] = individualScores[1].replaceAll("[^0-9]+$", "");
			 
			 int score1 = Integer.parseInt(individualScores[0].trim());
			 int score2 = Integer.parseInt(individualScores[1].trim());
			 
			 if(team == SelectedTeam.TeamA)
			 {
				 scoresA.add(score1);
				 scoresB.add(score2);
			 }
			 else
			 {
				 scoresB.add(score1);
				 scoresA.add(score2);
			 }
		 }
		 
		 if(scoresA.size() == 1)
		 {
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
		 }
		 else if(scoresA.size() == 2)
		 {
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
		 }
		 else if(scoresA.size() == 3)
		 {
			 scoresA.add(0);
			 scoresA.add(0);
			 scoresB.add(0);
			 scoresB.add(0);
		 }
		 else if (scoresA.size() == 4)
		 {
			 scoresA.add(0);
			 scoresB.add(0);
		 }
			 
		 
		 
		 
		 if(team == SelectedTeam.TeamA)
		 {
			//NAMES 
			 etA.setText(result.getWinner());
			 etB.setText(result.getLoser());
		 }
		 else
		 {
			//NAMES
			 etA.setText(result.getLoser());
			 etB.setText(result.getWinner());
		 }
		 //SCORES
		 A1.setSelection(scoresA.get(0));
		 A2.setSelection(scoresA.get(1));
		 A3.setSelection(scoresA.get(2));
		 A4.setSelection(scoresA.get(3));
		 A5.setSelection(scoresA.get(4));
		 B1.setSelection(scoresB.get(0));
		 B2.setSelection(scoresB.get(1));
		 B3.setSelection(scoresB.get(2));
		 B4.setSelection(scoresB.get(3));
		 B5.setSelection(scoresB.get(4));
	}
	
	private void NEXT()
	{
		Intent intent = new Intent(this, MatchResult.class);
		helper.IncrementIndex();
		if(helper.Index() <= helper.IndividualResults().size() && 
				helper.IndividualResults() != null)
		{
			helper.setType(MatchResultEnum.PreviousNextMode);
		}
		else
		{
			helper.setType(MatchResultEnum.AddNewResult);
		}
		intent.putExtra("helper", helper);
		startActivity(intent);
		activity.finish();
	}
	
	private void PREVIOUS()
	{
		Intent intent = new Intent(this, MatchResult.class);
		helper.DecrementIndex();
		if(helper.Index() > 0)
		{
			helper.setType(MatchResultEnum.PreviousNextMode);
			intent.putExtra("helper", helper);
			startActivity(intent);
			activity.finish();
		}
		else
		{
			Intent _intent = new Intent(context, Dashboard.class);
			context.startActivity(_intent);
			activity.finish();
		}
	}
	
	private void VerifyEntireTeamMatch()
	{
		Intent intent = new Intent(this, VerifyResult.class);
		intent.putExtra("helper", helper);
		startActivity(intent);
		activity.finish();
	}
	
	private ResultForTeam CreateNewResult()
	{
		//PLAYER NAMES - UPPER CASE FIRST LETTER
		EditText etA = (EditText)findViewById(R.id.etA_MatchResult);
		String player_A = etA.getText().toString();
		player_A = UpperCaseFirstWord(player_A);
		
		EditText etB = (EditText)findViewById(R.id.etB_MatchResult);
		String player_B = etB.getText().toString();
		player_B = UpperCaseFirstWord(player_B);
		
		Spinner A1 = (Spinner)findViewById(R.id.spinnerA1);
		Spinner A2 = (Spinner)findViewById(R.id.spinnerA2);
		Spinner A3 = (Spinner)findViewById(R.id.spinnerA3);
		Spinner A4 = (Spinner)findViewById(R.id.spinnerA4);
		Spinner A5 = (Spinner)findViewById(R.id.spinnerA5);
		Spinner B1 = (Spinner)findViewById(R.id.spinnerB1);
		Spinner B2 = (Spinner)findViewById(R.id.spinnerB2);
		Spinner B3 = (Spinner)findViewById(R.id.spinnerB3);
		Spinner B4 = (Spinner)findViewById(R.id.spinnerB4);
		Spinner B5 = (Spinner)findViewById(R.id.spinnerB5);
		
		int a1 = (Integer) A1.getSelectedItem();
		int b1 = (Integer) B1.getSelectedItem();
		int a2 = (Integer) A2.getSelectedItem();
		int b2 = (Integer) B2.getSelectedItem();
		int a3 = (Integer) A3.getSelectedItem();
		int b3 = (Integer) B3.getSelectedItem();
		int a4 = (Integer) A4.getSelectedItem();
		int b4 = (Integer) B4.getSelectedItem();
		int a5 = (Integer) A5.getSelectedItem();
		int b5 = (Integer) B5.getSelectedItem();

		
		int[] playerA_scores = {a1,a2,a3,a4,a5};
		int[] playerB_scores = {b1,b2,b3,b4,b5};
		ArrayList<GameResult> gameResults = new ArrayList<GameResult>();
		for(int x = 0; x < 5; x++)
		{
			if(!(playerA_scores[x] == 0 && playerB_scores[x] == 0))
			{
				GameResult result = new GameResult(playerA_scores[x], playerB_scores[x], x+1);
				gameResults.add(result);
			}
		}

		ResultForTeam newResult = new ResultForTeam(player_A,
													player_B,
													helper.TeamA_ID(),
													helper.TeamB_ID(),
													gameResults,
													helper.Index());

		return newResult;
	}
	
	@Override
	public void onBackPressed()
	{
		PREVIOUS();
	}
	
	private boolean MayProceed()
	{
		boolean mayProceed = true;
		if (!NamesIsCorrect())
		{
			mayProceed = false;
		}

		if (!ScoresIsCorrect())
		{
			mayProceed = false;
		}
		return mayProceed;
	}

	
	private boolean NamesIsCorrect()
	{
		boolean namesIsCorrect = true;
		
		EditText etA = (EditText)findViewById(R.id.etA_MatchResult);
		EditText etB = (EditText)findViewById(R.id.etB_MatchResult);
		String player_A = etA.getText().toString();
		String player_B = etB.getText().toString();
		
		ArrayList<String> names = new ArrayList<String>();
		names.add(player_A);
		names.add(player_B);
		
		for(String name : names)
		{
			if(name == null || name.length() > 45)
			{
				namesIsCorrect = false;
				RedToast toast = new RedToast(context);
				toast.MakeRedToast(context, "Invalid Name: "+name, true, false);
			}
		}
		return namesIsCorrect;
	}
	
	private boolean ScoresIsCorrect()
	{
		boolean scoreIsCorrect = true;
		
		Spinner A1 = (Spinner)findViewById(R.id.spinnerA1);
		Spinner A2 = (Spinner)findViewById(R.id.spinnerA2);
		Spinner A3 = (Spinner)findViewById(R.id.spinnerA3);
		Spinner A4 = (Spinner)findViewById(R.id.spinnerA4);
		Spinner A5 = (Spinner)findViewById(R.id.spinnerA5);
		Spinner B1 = (Spinner)findViewById(R.id.spinnerB1);
		Spinner B2 = (Spinner)findViewById(R.id.spinnerB2);
		Spinner B3 = (Spinner)findViewById(R.id.spinnerB3);
		Spinner B4 = (Spinner)findViewById(R.id.spinnerB4);
		Spinner B5 = (Spinner)findViewById(R.id.spinnerB5);
		
		int a1 = (Integer) A1.getSelectedItem();
		int b1 = (Integer) B1.getSelectedItem();
		int a2 = (Integer) A2.getSelectedItem();
		int b2 = (Integer) B2.getSelectedItem();
		int a3 = (Integer) A3.getSelectedItem();
		int b3 = (Integer) B3.getSelectedItem();
		int a4 = (Integer) A4.getSelectedItem();
		int b4 = (Integer) B4.getSelectedItem();
		int a5 = (Integer) A5.getSelectedItem();
		int b5 = (Integer) B5.getSelectedItem();
		
		int gamesA = 0, gamesB = 0;
		
		int[] scoresA = {a1, a2, a3, a4, a5};
		int[] scoresB = {b1, b2, b3, b4, b5};
		
		
		for(int x  = 0; x < 5; x++)
		{
			if(gamesA < 3 && gamesB < 3)
			{
				//BOTH SCORES ARE ZERO
				if((scoresA[x] == 0) && (scoresB[x] == 0))
				{
					IncorrectScore();
					scoreIsCorrect = false;
					break;
				}
				
				//SCORES ARE EQUAL TO EACH OTHER
				if(scoresA[x] == scoresB[x])
				{
					IncorrectScore();
					scoreIsCorrect = false;
					break;
				}
				
				//BOTH SCORES ARE LOWER THAN GAME PAR
				if((scoresA[x] < helper.Fixture().GamePar()) && (scoresB[x] < helper.Fixture().GamePar()))
				{
					IncorrectScore();
					scoreIsCorrect = false;
					break;
				}
				
				//MUST WIN BY 2 IF BOTH SCORES ARE GREATER THAN GAME PAR
				if((scoresA[x] >= helper.Fixture().GamePar()) && (scoresB[x] >= helper.Fixture().GamePar()))
				{
					if(!((scoresA[x] - scoresB[x] == 2)||(scoresB[x] - scoresA[x] == 2)))
					{
						IncorrectScore();
						scoreIsCorrect = false;
						break;
					}
				}
				
				//INCREMENT SCORE
				if(scoresA[x] > scoresB[x])
				{
					gamesA++;
				}
				else
				{
					gamesB++;
				}
			}
		}

		return scoreIsCorrect;
	}
	
	private void IncorrectScore()
	{
		RedToast toast = new RedToast(context);
		toast.MakeRedToast(context, "Invalid Score", false, false);
	}

	
	public String UpperCaseFirstWord(String text)
	{
		text = text.toLowerCase();
		String[] words = text.split("\\s");
		String _text = "";
		for(int x = 0; x < words.length; x++)
		{
			if(words[x].length() > 1)
			{
				_text += words[x].substring(0, 1).toUpperCase() + words[x].substring(1).toLowerCase() + " ";
			}
			else
			{
				_text += words[x].toUpperCase() + " ";
			}
		}
		return _text;
	}
	
	public enum MatchResultEnum
	{
		AddNewResult,
		PreviousNextMode,
		EditResult
	}

	public enum SelectedTeam
	{
		TeamA, TeamB;
	}



	private class CalculateFinalTeamScore extends AsyncTask<String, String, String>
	{
		LoadingCircle loading;
		Context _context;
		
		public CalculateFinalTeamScore(Context _context)
		{
			this._context = _context;
			MakeLoadingCircle();
		}
		
		private void MakeLoadingCircle() 
		{
			loading = new LoadingCircle(_context, "SquashBot", "Calculating final score...");
			loading.ShowLoadingCircle();
		}
		
		@Override
		protected String doInBackground(String... arg0)
		{
			CalculateScore();
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			loading.DismissLoadingCircle();
			VerifyEntireTeamMatch();
		}
		
		private void CalculateScore()
		{
			//Convert all data to JSON and send to php as 1 variable
			Gson gson = new Gson();
			String jsonObject = gson.toJson(helper);

			//Make HHTP request with variable
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.makeHttpRequest_WithJSONVariable("http://www.squashbot.co.za/Tournaments/CalculateFinalScore.php", jsonObject);
			
			String TAG_NODES = "resultData";
			JSONArray NodesArray = null;
			
			try
			{
				NodesArray = json.getJSONArray(TAG_NODES);
				
				for(int x = 0; x <= NodesArray.length()-1; x++)
				{
					JSONObject cursor = NodesArray.getJSONObject(x);
					
					String _winnerId = cursor.getString("WinnerId");
					String _loserId = cursor.getString("LoserId");
					String _winnerScore = cursor.getString("WinnerScore");
					String _loserScore = cursor.getString("LoserScore");

					helper.SetWinningTeamId(Integer.parseInt(_winnerId), Integer.parseInt(_winnerScore));
					helper.SetLosingTeamId(Integer.parseInt(_loserId), Integer.parseInt(_loserScore));
				}
			}
			catch (JSONException d)
			{
				d.printStackTrace();
			}

		}
	}
}
