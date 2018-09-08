package squashbot.dashboard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tournament_activities.MatchResult;
import tournament_activities.MatchResult.MatchResultEnum;
import tournament_activities.TournamentDashboard;
import tournament_classes.Fixture;
import tournament_classes.TeamResultHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import classes.Arrays;
import classes.EnumTypes.EnterNamesType;
import classes.Player.AvatarColour;

import com.squashbot.R;

import display_notification.RedToast;

public class EnterNames extends Activity 
{
	boolean use_warmup;
	boolean use_rest;
	int game_points;
	int sets;
	EnterNamesType enterNamesType;
	static Context context;
	
	ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
	Fixture selectedFixture;
	public static TeamResultHelper helper;
	static Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_names);
		
		context = getApplicationContext();
		activity = this;
		
		enterNamesType = (EnterNamesType)getIntent().getSerializableExtra("resultType");
		ActionBar actionbar = getActionBar();
		
		if(enterNamesType == EnterNamesType.EnterTeamResult)
		{
			actionbar.setTitle("Enter Teams");
			
			SetupScreenForTeamResult();
		}
		else if (enterNamesType == enterNamesType.ScoreNewGame)
		{
			use_warmup = getIntent().getBooleanExtra("use_warmup", true);
			use_rest = getIntent().getBooleanExtra("use_rest", true);
			game_points = getIntent().getIntExtra("game_points", 11);
			sets = getIntent().getIntExtra("sets", 3);
			
			actionbar.setTitle("Enter names");
			
			SetupScreenForScoringNewGame();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.enter_names, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.next) 
		{
			if(enterNamesType == EnterNamesType.ScoreNewGame)
			{
				GoToScoreNewGame();
			}
			else if(enterNamesType == EnterNamesType.EnterTeamResult)
			{		
				if(selectedFixture != null)
				{
					helper = new TeamResultHelper(selectedFixture, TournamentDashboard.selectedTournament);
					helper.setType(MatchResultEnum.AddNewResult);
					helper.SetTeamA_ID(selectedFixture.TeamA_ID());
					helper.SetTeamB_ID(selectedFixture.TeamB_ID());
					helper.GetTeams(this);
				}
				else
				{
					RedToast toast = new RedToast(context);
					toast.MakeRedToast(context, "Select a valid fixture", false, false);
				}

			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void GoToScoreNewGame()
	{
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		
		Intent intent = new Intent(this, ScoreGame.class);
		
		String playerA = PlayerA_Name();
		String playerB = PlayerB_Name();
		
		Spinner AvatarA = (Spinner)findViewById(R.id.spinner_AvatarA);
		Spinner AvatarB = (Spinner)findViewById(R.id.spinner_AvatarB);
		AvatarColour colourA = getColourSelected(AvatarA);
		AvatarColour colourB = getColourSelected(AvatarB);
		
		
		intent.putExtra("use_warmup", use_warmup);
		intent.putExtra("use_rest", use_rest);
		intent.putExtra("game_points", game_points);
		intent.putExtra("sets", sets);
		intent.putExtra("playerA", playerA);
		intent.putExtra("playerB", playerB);
		intent.putExtra("colourA", colourA);
		intent.putExtra("colourB", colourB);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		if(SecurityCheckForNames(playerA) == false)
		{
			DisplayInvalidNameToast(playerA, "Player A");
		}
		else if(SecurityCheckForNames(playerB) == false)
		{
			DisplayInvalidNameToast(playerB, "Player B");
		}
		else if(SecurityCheckForAvatarColours() == false)
		{
			DisplayInvalidAvatarColours();
		}
		else
		{
			startActivity(intent);
		}
	}
	
	public static void GoToEnterTeamResult()
	{
		//called from async class once both teams are retrieved
		Intent intent = new Intent(context, MatchResult.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("helper", helper);
		context.startActivity(intent);
		activity.finish();
	}
	
	@Override
	public void onBackPressed()
	{	
		Intent intent = new Intent(this, Dashboard.class);
		finish();
		startActivity(intent);
	}
	
	public String PlayerA_Name()
	{
		EditText et = (EditText)findViewById(R.id.et_playerA);
		String playerA = et.getText().toString();
		return playerA;
	}
	
	public String PlayerB_Name()
	{
		EditText et = (EditText)findViewById(R.id.et_playerB);
		String playerB = et.getText().toString();
		return playerB;
	}
	
	public void SetupScreenForTeamResult()
	{
		//INVISIBLE
		TextView header = (TextView)findViewById(R.id.scoreboard_header);
		EditText etA = (EditText)findViewById(R.id.et_playerA);
		EditText etB = (EditText)findViewById(R.id.et_playerB);
		Spinner AvatarA = (Spinner)findViewById(R.id.spinner_AvatarA);
		Spinner AvatarB = (Spinner)findViewById(R.id.spinner_AvatarB);
		header.setVisibility(View.INVISIBLE);
		etA.setVisibility(View.INVISIBLE);
		etB.setVisibility(View.INVISIBLE);
		AvatarA.setVisibility(View.INVISIBLE);
		AvatarB.setVisibility(View.INVISIBLE);	
		
		//VISIBLE
		TextView gender = (TextView)findViewById(R.id.tv_teamresultGender);
		TextView section = (TextView)findViewById(R.id.tv_teamresultSection);
		TextView fixture = (TextView)findViewById(R.id.tv_teamresultFixture);
		Spinner spinnerGender = (Spinner)findViewById(R.id.spinner_Gender);
		Spinner spinnerSection = (Spinner)findViewById(R.id.spinner_Section);
		Spinner spinnerFixture = (Spinner)findViewById(R.id.spinner_Fixture);
		gender.setVisibility(View.VISIBLE);
		section.setVisibility(View.VISIBLE);
		fixture.setVisibility(View.VISIBLE);
		spinnerGender.setVisibility(View.VISIBLE);
		spinnerSection.setVisibility(View.VISIBLE);
		spinnerFixture.setVisibility(View.VISIBLE);
		
		section.setText(TournamentDashboard.selectedTournament.NamingConvention());
		
		//POPULATE FIXTURES
		for(int x = 0; x < TournamentDashboard.fixtures.size(); x++)
		{
			if(!TournamentDashboard.fixtures.get(x).ResultHasBeenSubmitted())
			{
				fixtures.add(TournamentDashboard.fixtures.get(x));
			}
		}		

		PopulateGenders();
		PopulateSections();
		PopulateFixtures();
	}
	
	private void PopulateGenders()
	{
		ArrayList<String> genders = new ArrayList<String>();
		
		for(int x  = 0; x < fixtures.size(); x++)
		{
			boolean genderExistsInList = false;
			for(int y = 0; y < genders.size(); y++)
			{
				if(genders.contains(fixtures.get(x).Gender()))
				{
					genderExistsInList = true;
					break;
				}
			}
			
			if(!genderExistsInList)
			{
				genders.add(fixtures.get(x).Gender());
			}
		}
		
		Spinner spinnerGender = (Spinner)findViewById(R.id.spinner_Gender);
		spinnerGender.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders)
		{
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				view.setBackgroundColor(getResources().getColor(R.color.Orange));
				return view;
			}
		});

		
		spinnerGender.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				PopulateSections();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
			
		});
	}
	
	private void PopulateSections()
	{
		ArrayList<String> sections = new ArrayList<String>();
		
		String gender = getGenderSelected();
		
		for(int x  = 0; x < fixtures.size(); x++)
		{
			if(fixtures.get(x).Gender().contains(gender))
			{
				boolean sectionExistsInList = false;
				for(int y = 0; y < sections.size(); y++)
				{
					if(sections.contains(fixtures.get(x).Section()))
					{
						sectionExistsInList = true;
						break;
					}
				}
				
				if(!sectionExistsInList)
				{
					sections.add(fixtures.get(x).Section());
				}
			}
		}
		
		Spinner spinnerSection = (Spinner)findViewById(R.id.spinner_Section);
		
		spinnerSection.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sections)
		{
			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				view.setBackgroundColor(getResources().getColor(R.color.Orange));
				return view;
			}
		});
		
		spinnerSection.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				PopulateFixtures();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
			}
		});
	}
	
	private void PopulateFixtures()
	{
		final ArrayList<Fixture> validFixtures = new ArrayList<Fixture>();
		String gender = getGenderSelected();
		String section = getSectionSelected();
		
		for(int x  = 0; x < fixtures.size(); x++)
		{
			if(fixtures.get(x).Gender().contains(gender) && 
					fixtures.get(x).Section().contains(section))
			{
				boolean fixtureExistsInList = false;
				for(int y = 0; y < validFixtures.size(); y++)
				{
					if(validFixtures.get(y).ID() == fixtures.get(x).ID())
					{
						fixtureExistsInList = true;
						break;
					}
				}
				
				if(!fixtureExistsInList)
				{
					validFixtures.add(fixtures.get(x));
				}
			}
		}
		
		if(validFixtures.size() == 0)
		{
			selectedFixture = null;
		}
		
		final Spinner spinnerFixture = (Spinner)findViewById(R.id.spinner_Fixture);
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		for(int x = 0; x < validFixtures.size(); x++)
		{
			Map<String, String> F = new HashMap<String, String>(2);
			F.put("Fixture", validFixtures.get(x).TeamA_Name() + " vs "+validFixtures.get(x).TeamB_Name());
			F.put("Date", validFixtures.get(x).FriendlyDate()+", "+validFixtures.get(x).FriendlyTime());
			data.add(F);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(context, data, 
													android.R.layout.simple_expandable_list_item_2, 
													new String[]{"Fixture","Date"},
													new int[] {android.R.id.text1, android.R.id.text2})
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
			

			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);
				
				view.setBackgroundColor(getResources().getColor(R.color.Orange));
				TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
				TextView tv2 = (TextView)view.findViewById(android.R.id.text2);
				
				tv1.setTextColor(getResources().getColor(R.color.Black));
				tv2.setTextColor(getResources().getColor(R.color.Blue));
				
				return view;
			}
		};
		
		spinnerFixture.setAdapter(adapter);
		spinnerFixture.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				selectedFixture = validFixtures.get(spinnerFixture.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
			
		});
	}
	
	private String getGenderSelected()
	{
		Spinner spinnerGender = (Spinner)findViewById(R.id.spinner_Gender);
		String gender = "";
		if(spinnerGender.getSelectedItem() != null)
		{
			gender = spinnerGender.getSelectedItem().toString();
		}
		return gender;
	}
	
	private String getSectionSelected()
	{
		Spinner spinnerSection = (Spinner)findViewById(R.id.spinner_Section);
		String section = "";
		if(spinnerSection.getSelectedItem() != null)
		{
			section = spinnerSection.getSelectedItem().toString();
		}
		return section;
	}
	
	public void SetupScreenForScoringNewGame()
	{
		//INVISIBLE
		TextView gender = (TextView)findViewById(R.id.tv_teamresultGender);
		TextView section = (TextView)findViewById(R.id.tv_teamresultSection);
		TextView fixture = (TextView)findViewById(R.id.tv_teamresultFixture);
		Spinner spinnerGender = (Spinner)findViewById(R.id.spinner_Gender);
		Spinner spinnerSection = (Spinner)findViewById(R.id.spinner_Section);
		Spinner spinnerFixture = (Spinner)findViewById(R.id.spinner_Fixture);
		gender.setVisibility(View.INVISIBLE);
		section.setVisibility(View.INVISIBLE);
		fixture.setVisibility(View.INVISIBLE);
		spinnerGender.setVisibility(View.INVISIBLE);
		spinnerSection.setVisibility(View.INVISIBLE);
		spinnerFixture.setVisibility(View.INVISIBLE);
		
		
		//VISIBLE
		TextView header = (TextView)findViewById(R.id.scoreboard_header);
		EditText etA = (EditText)findViewById(R.id.et_playerA);
		EditText etB = (EditText)findViewById(R.id.et_playerB);
		Spinner AvatarA = (Spinner)findViewById(R.id.spinner_AvatarA);
		Spinner AvatarB = (Spinner)findViewById(R.id.spinner_AvatarB);
		header.setVisibility(View.VISIBLE);
		etA.setVisibility(View.VISIBLE);
		etB.setVisibility(View.VISIBLE);
		AvatarA.setVisibility(View.VISIBLE);
		AvatarB.setVisibility(View.VISIBLE);
		
		SetupAvatarSpinners();
	}
	
	private void SetupAvatarSpinners()
	{
		Spinner AvatarA = (Spinner)findViewById(R.id.spinner_AvatarA);
		Spinner AvatarB = (Spinner)findViewById(R.id.spinner_AvatarB);
		Arrays arrays = new Arrays();
		ArrayList<String> colours = arrays.getAvatarColours();
		
		ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, colours)
				{
					@Override
					public View getView(int position, View convertView, ViewGroup parent)
					{
						View view = super.getView(position, convertView, parent);
						TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
						tv1.setTextColor(getResources().getColor(R.color.Blue));
						return view;
					};
			
					@Override
					public View getDropDownView(int position, View convertView, ViewGroup parent)
					{
						View view = super.getView(position, convertView, parent);
						
						view.setBackgroundColor(getResources().getColor(R.color.Orange));
						TextView tv1 = (TextView)view.findViewById(android.R.id.text1);
						tv1.setTextColor(getResources().getColor(R.color.Blue));
						
						return view;
					}
				};
		
		AvatarA.setAdapter(adapter);
		AvatarA.setSelection(0);
		AvatarB.setAdapter(adapter);
		AvatarB.setSelection(1);
	}
	
	public AvatarColour getColourSelected(Spinner spinner)
	{
		AvatarColour colour = AvatarColour.Red;
	
		switch(spinner.getSelectedItemPosition())
		{
			case 0:
				colour = AvatarColour.Red;
				break;
			case 1:
				colour = AvatarColour.Blue;
				break;
			case 2:
				colour = AvatarColour.Black;
				break;
			case 3:
				colour = AvatarColour.White;
				break;
			case 4:
				colour = AvatarColour.Orange;
				break;
			case 5:
				colour = AvatarColour.Green;
				break;
			case 6:
				colour = AvatarColour.Pink;
				break;
			case 7:
				colour = AvatarColour.Yellow;
				break;
			case 8:
				colour = AvatarColour.Gray;
				break;
			case 9:
				colour = AvatarColour.Purple;
				break;
		}
		
		return colour;
	}
	
	public boolean SecurityCheckForNames(String name)
	{
		boolean isValid = false;
		if(!name.isEmpty() && name.length() <= 10)
		{
			isValid = true;
		}
		return isValid;
	}

	public boolean SecurityCheckForAvatarColours()
	{
		boolean isValid = false;
		
		Spinner AvatarA = (Spinner)findViewById(R.id.spinner_AvatarA);
		Spinner AvatarB = (Spinner)findViewById(R.id.spinner_AvatarB);
		
		int a = AvatarA.getSelectedItemPosition();
		int b = AvatarB.getSelectedItemPosition();
		if(a != b)
		{
			isValid = true;
		}
		
		return isValid;
	}
	
	public void DisplayInvalidNameToast(String invalid_name, String player)
	{
		if(invalid_name.isEmpty()==true)
		{
			invalid_name = player+" name is empty...";
		}

		RedToast toast = new RedToast(this);
		toast.MakeRedToast(this, "INVALID NAME:\t"+invalid_name, true, false);
	}
	
	public void DisplayInvalidAvatarColours()
	{
		RedToast toast = new RedToast(this);
		toast.MakeRedToast(this, "Avatars must be of different colours", true, false);
	}
}
