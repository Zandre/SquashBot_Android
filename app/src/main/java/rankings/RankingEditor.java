package rankings;

import java.util.ArrayList;

import rankings.Rankings.RankingEditorMode;
import com.squashbot.R;
import classes.Club;
import classes.NetworkConnection;
import classes.RankedPlayer;
import display_notification.BlueToast;
import display_notification.RedToast;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import asynchronious_classes.insertClubPlayer;
import asynchronious_classes.insertClubPlayerResult;
import asynchronious_classes.removeClubPlayer;
import asynchronious_classes.updateClubPlayer;

public class RankingEditor extends Activity 
{

	static RankingEditorMode mode;
	ArrayList<RankedPlayer> Women, Men;
	ArrayList<String> WomenNames = new ArrayList<String>();
	ArrayList<String> MenNames = new ArrayList<String>();
	Club club;
	static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_editor);
		
		context = getApplicationContext();
		
		mode = (RankingEditorMode)getIntent().getSerializableExtra("mode");
		Women = Rankings.Women;
		Men = Rankings.Men;
		
		for(int x = 0; x < Men.size(); x++)
		{
			MenNames.add(Men.get(x).getName());
		}
		
		for(int x = 0; x < Women.size(); x++)
		{
			WomenNames.add(Women.get(x).getName());
		}
		

		
		club = getIntent().getParcelableExtra("club");
		
		String clubName = club.getName();
		ActionBar actionbar = getActionBar();
		actionbar.setTitle(""+clubName);
		
		
		
		Button btn = (Button)findViewById(R.id.btn_rankingEditor);
		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DisplayInformation();
			}
		});
	
		RadioGroup selectedGender = (RadioGroup)findViewById(R.id.rdogrp_rankingEditor_gender);
		selectedGender.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				SetupSpinners();
			}
		});
	
		Spinner A = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		A.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
					if(mode == RankingEditorMode.EditMember)
					{
						UpdateEditTexts();
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		SetupScreen();
	}
	
	public void SetupScreen()
	{
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		TextView _playerA = (TextView)findViewById(R.id.tv_rankingEditor_playerA);
		TextView _playerB = (TextView)findViewById(R.id.tv_rankingEditor_playerB);
		TextView _name = (TextView)findViewById(R.id.tv_rankingEditor_name);
		TextView _points = (TextView)findViewById(R.id.tv_rankingEditor_points);
		TextView _club = (TextView)findViewById(R.id.tv_rankingEditor_club);
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		EditText club = (EditText)findViewById(R.id.et_rankingEditor_club);
		Button btn = (Button)findViewById(R.id.btn_rankingEditor);
		
		switch(mode)
		{
			case AddResult:
				playerA.setVisibility(View.VISIBLE);
				playerB.setVisibility(View.VISIBLE);
				_playerA.setVisibility(View.VISIBLE);
				_playerB.setVisibility(View.VISIBLE);
				_playerA.setText("Match Winner: ");
				_playerB.setText("Match Loser: ");
				_name.setVisibility(View.INVISIBLE);
				_points.setVisibility(View.INVISIBLE);
				_club.setVisibility(View.INVISIBLE);
				name.setVisibility(View.INVISIBLE);
				points.setVisibility(View.INVISIBLE);
				club.setVisibility(View.INVISIBLE);
				btn.setText("Add Result");
				break;
				
				
			case AddMember:
				playerA.setVisibility(View.INVISIBLE);
				playerB.setVisibility(View.INVISIBLE);
				_playerA.setVisibility(View.INVISIBLE);
				_playerB.setVisibility(View.INVISIBLE);
				_name.setVisibility(View.VISIBLE);
				_points.setVisibility(View.VISIBLE);
				_club.setVisibility(View.VISIBLE);
				name.setVisibility(View.VISIBLE);
				points.setVisibility(View.VISIBLE);
				club.setVisibility(View.VISIBLE);
				btn.setText("Add Member");
				break;
				
				
			case EditMember:
				playerA.setVisibility(View.VISIBLE);
				playerB.setVisibility(View.INVISIBLE);
				_playerA.setVisibility(View.VISIBLE);
				_playerB.setVisibility(View.INVISIBLE);
				_playerA.setText("Select Member: ");
				_name.setVisibility(View.VISIBLE);
				_points.setVisibility(View.VISIBLE);
				_club.setVisibility(View.VISIBLE);
				name.setVisibility(View.VISIBLE);
				points.setVisibility(View.VISIBLE);
				club.setVisibility(View.VISIBLE);
				btn.setText("Update Member");
				break;
				
				
			case RemoveMember:
				playerA.setVisibility(View.VISIBLE);
				playerB.setVisibility(View.INVISIBLE);
				_playerA.setVisibility(View.VISIBLE);
				_playerB.setVisibility(View.INVISIBLE);
				_playerA.setText("Select Member: ");
				_name.setVisibility(View.INVISIBLE);
				_points.setVisibility(View.INVISIBLE);
				_club.setVisibility(View.INVISIBLE);
				name.setVisibility(View.INVISIBLE);
				points.setVisibility(View.INVISIBLE);
				club.setVisibility(View.INVISIBLE);
				btn.setText("Remove Member");
				break;
		}
		
		SetupSpinners();
	}

	private void SetupSpinners()
	{
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		EditText club = (EditText)findViewById(R.id.et_rankingEditor_club);
		
		if(getGenderSelected().contains("Men"))
		{
			switch(mode)
			{
				case AddResult:
					playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MenNames));
					playerB.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MenNames));
					playerA.setSelection(0);
					if(MenNames.size() > 1)
					{
						playerB.setSelection(1);
					}
					break;				
					
				case EditMember:
					playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MenNames));
					name.setText(""+getText_SpinnerA());
					points.setText(""+getPointsOfPlayerInSpinner());
					club.setText(""+getClubOfPlayerInSpinner());
					break;
					
				case RemoveMember:
					playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MenNames));
					break;
			}
		}
		else if(getGenderSelected().contains("Women"))
		{
			switch(mode)
			{
			case AddResult:
				playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WomenNames));
				playerB.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WomenNames));
				playerA.setSelection(0);
				if(WomenNames.size() > 1)
				{
					playerB.setSelection(1);
				}
				break;				
				
			case EditMember:
				playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WomenNames));
				name.setText(""+getText_SpinnerA());
				points.setText(""+playerA.getSelectedItemPosition());
				club.setText(""+getClubOfPlayerInSpinner());
				break;
				
			case RemoveMember:
				playerA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WomenNames));
				break;
			}
		}

	}
	
	public void DisplayInformation()
	{
		if(!ValidateScreen())
		{
			InvalidScreenToast();
		}
		else
		{
			AlertDialog.Builder popup = new AlertDialog.Builder(RankingEditor.this);
			popup.setCancelable(true);

			final TextView text = new TextView(RankingEditor.this);
			switch(mode)
			{
				case AddResult:
					popup.setTitle("Add Result");
					text.setText(getText_SpinnerA() +" has beaten "+ getText_SpinnerB());
					break;
					
					
				case AddMember:
					popup.setTitle("Add New Member");
					if(getClub().length() > 0)
					{
						text.setText("Name:\t\t"+getName()+"\nGender:\t\t"+getGenderSelected()+"\nPoints:\t\t"+getPoints()+"\nClub:\t\t"+getClub());
					}
					else
					{
						text.setText("Name:\t\t"+getName()+"\nGender:\t\t"+getGenderSelected()+"\nPoints:\t\t"+getPoints());
					}
					break;
					
					
				case EditMember:
					popup.setTitle("Update Member");
					text.setText("Name:\t\t"+getText_SpinnerA()+" to "+getName()+"\nPoints:\t\t"+getPointsOfPlayerInSpinner() +" to "+getPoints()+"\nClub:\t\t"+getClubOfPlayerInSpinner()+ " to " + getClub());
					break;
					
					
				case RemoveMember:
					popup.setTitle("Remove Member");
					text.setText("Remove "+getText_SpinnerA()+"?");
					break;
			}

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			text.setLayoutParams(lp);
			popup.setView(text);

			popup.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					GetCode();
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

	public void GetCode()
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(RankingEditor.this);
		popup.setTitle("INSERT PASSWORD");
		popup.setCancelable(true);
		
		final EditText et_password = new EditText(RankingEditor.this);
		et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																		LinearLayout.LayoutParams.MATCH_PARENT);
		et_password.setLayoutParams(lp);
		popup.setView(et_password);
		
		popup.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String password = et_password.getText().toString();
				NetworkConnection connection = new NetworkConnection(context);
				if(connection.NetworkIsAvailable())
				{
					switch(mode)
					{
						case AddResult:
							insertNewClubPlayerResult(password);
							break;
							
							
						case AddMember:
							insertClubPlayer(password);
							break;
							
							
						case EditMember:
							updateClubPlayer(password);
							break;
							
							
						case RemoveMember:
							removeClubPlayer(password);
							break;
					}
				}
				else
				{
					RedToast toast = new RedToast(context);
					toast.NoNetworkConnection();
				}
			}
		});
		popup.show();
	}
	
	private boolean ValidateScreen()
	{
		boolean ScreenIsCorrect = true;
		
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		TextView _playerA = (TextView)findViewById(R.id.tv_rankingEditor_playerA);
		TextView _playerB = (TextView)findViewById(R.id.tv_rankingEditor_playerB);
		TextView _name = (TextView)findViewById(R.id.tv_rankingEditor_name);
		TextView _points = (TextView)findViewById(R.id.tv_rankingEditor_points);
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		Button btn = (Button)findViewById(R.id.btn_rankingEditor);
		
		switch(mode)
		{
			case AddResult:
				if(getId_SpinnerA() == getId_SpinnerB())
				{
					ScreenIsCorrect = false;
					break;
				}
				break;
				
				
			case AddMember:
				if(getName().length() < 1)
				{
					ScreenIsCorrect = false;
					break;
				}
				if(getPoints() == 0)
				{
					ScreenIsCorrect = false;
					break;
				}
				break;
				
				
			case EditMember:
				if(getName().length() < 1)
				{
					ScreenIsCorrect = false;
					break;
				}
				if(getPoints() == 0)
				{
					ScreenIsCorrect = false;
					break;
				}
				break;
				
		}
		
		return ScreenIsCorrect;
	}
	
	private void InvalidScreenToast()
	{
		BlueToast toast = new BlueToast(this);
		String message = "";
		switch(mode)
		{
			case AddResult:
				message = "Invalid\nDifferent members must be selected";
				break;
				
				
			case AddMember:
				message = "Invalid\nName fields may not be empty.\nPoints field must be greater than 0";
				break;
				
				
			case EditMember:
				message = "Invalid\nName fields may not be empty.\nPoints field must be greater than 0";
				break;
				
		}
		toast.MakeBlueToast(this, message, true, false);
	}
	
	public static void SuccessToast(boolean success)
	{
		BlueToast toast = new BlueToast(context);
		
		
		switch(mode)
		{
			case AddResult:
				if(success)
				{
					toast.MakeBlueToast(context, "Match Result was successfully added", true, false);
				}
				else
				{
					toast.MakeBlueToast(context, "Match Result was NOT added", true, false);
				}
				
				break;
				
				
			case AddMember:
				if(success)
				{
					toast.MakeBlueToast(context, "Member was successfully added", true, false);
				}
				else
				{
					toast.MakeBlueToast(context, "Member was NOT added", true, false);
				}
				break;
				
				
			case EditMember:
				if(success)
				{
					toast.MakeBlueToast(context, "Member was successfully updated", true, false);
				}
				else
				{
					toast.MakeBlueToast(context, "Member was NOT updated", true, false);
				}
				break;
				
				
			case RemoveMember:
				if(success)
				{
					toast.MakeBlueToast(context, "Member was successfully removed", true, false);
				}
				else
				{
					toast.MakeBlueToast(context, "Member was NOT removed", true, false);
				}
				break;
		}
	}

	private void insertNewClubPlayerResult(String password)
	{
		new insertClubPlayerResult(this, getWinner(), getLoser(), club.getID(), password, club.getTableName()).execute();
	}
	
	private void insertClubPlayer(String password)
	{
		new insertClubPlayer(this, password, club.getTableName(), getName(), getGenderSelected(), getPoints(), club.getID(), getClub()).execute();
		ClearEditTexts();
	}
	
	private void updateClubPlayer(String password)
	{
		new updateClubPlayer(this, password, club.getTableName(), getId_SpinnerA(), getName(), getPoints(), club.getID(), getClub()).execute();
	}
	
	private void removeClubPlayer(String password)
	{
		new removeClubPlayer(this, club.getTableName(), getId_SpinnerA(), password, club.getID(), getText_SpinnerA()).execute();
	}
	
	private void UpdateEditTexts()
	{
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		EditText club = (EditText)findViewById(R.id.et_rankingEditor_club);
		
		name.setText(""+getText_SpinnerA());
		points.setText(""+getPointsOfPlayerInSpinner());
		club.setText(""+getClubOfPlayerInSpinner());
	}
	
	private void ClearEditTexts()
	{
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		EditText club = (EditText)findViewById(R.id.et_rankingEditor_club);
		
		name.setText("");
		points.setText("");
		club.setText("");
	}
	
 	private String getName()
	{
		EditText name = (EditText)findViewById(R.id.et_rankingEditor_player);
		String Name = name.getText().toString();
		if(Name.length() == 0)
		{
			Name = "";
		}
		return Name;
	}
		
 	private double getPoints()
	{
		EditText points = (EditText)findViewById(R.id.et_rankingEditor_points);
		String _points = points.getText().toString();
		if(_points.length() == 0)
		{
			_points = "0";
		}
		double Points = Double.parseDouble(_points);
		return Points;
	}	

 	private String getClub()
 	{
		EditText club = (EditText)findViewById(R.id.et_rankingEditor_club);
		String Club = club.getText().toString();
		if(Club.length() == 0)
		{
			Club = "";
		}
		return Club;
 	}
 	
 	private double getPointsOfPlayerInSpinner()
 	{
 		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
 		int index = playerA.getSelectedItemPosition();
 		double points = 0.00;
 		if(playerA.getSelectedItem() != null)
 		{
 	 		if(getGenderSelected().contains("Men"))
 	 		{
 	 			points = Men.get(index).getPoints();
 	 		}
 	 		else if(getGenderSelected().contains("Women"))
 	 		{
 	 			points = Women.get(index).getPoints();
 	 		}
 		}
 		return points;
 	}
 	
 	private String getClubOfPlayerInSpinner()
 	{
 		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
 		int index = playerA.getSelectedItemPosition();
 		String club = "";
 		if(playerA.getSelectedItem() != null)
 		{
 	 		if(getGenderSelected().contains("Men"))
 	 		{
 	 			if(Men.get(index).getClub() != null)
 	 			{
 	 				club = Men.get(index).getClub();
 	 			}
 	 		}
 	 		else if(getGenderSelected().contains("Women"))
 	 		{
 	 			if(Women.get(index).getClub() != null)
 	 			{
 	 	 			club = Women.get(index).getClub();
 	 			}
 	 		}
 		}
 		return club;
 	}
 	
	private String getGenderSelected()
	{
		RadioButton men = (RadioButton)findViewById(R.id.rdo_rankingEditor_Men);
		String gender = "";
		if(men.isChecked())
		{
			gender = "Men";
		}
		else
		{
			gender = "Women";
		}
		return gender;
	}

	private String getText_SpinnerA()
	{
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		String text = "";
		if(playerA.getSelectedItem() != null)
		{
			text = playerA.getSelectedItem().toString();
		}
		return text;	
	}
	
	private String getText_SpinnerB()
	{
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		String text = "";
		if(playerB.getSelectedItem() != null)
		{
			text = playerB.getSelectedItem().toString();
		}
		return text;
	}
	
	private int getId_SpinnerA()
	{
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		int id = 0;
		int index = playerA.getSelectedItemPosition();
 		if(getGenderSelected().contains("Men"))
 		{
 			id = Men.get(index).getID();
 		}
 		else if(getGenderSelected().contains("Women"))
 		{
 			id = Women.get(index).getID();
 		}
		return id;
	}
	
	private int getId_SpinnerB()
	{
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		int id = 0;
		int index = playerB.getSelectedItemPosition();
 		if(getGenderSelected().contains("Men"))
 		{
 			id = Men.get(index).getID();
 		}
 		else if(getGenderSelected().contains("Women"))
 		{
 			id = Women.get(index).getID();
 		}
 		return id;
	}
	
	private RankedPlayer getWinner()
	{
		Spinner playerA = (Spinner)findViewById(R.id.spinner_rankingEditor_playerA);
		RankedPlayer winner = new RankedPlayer();
		int index = playerA.getSelectedItemPosition();
 		if(getGenderSelected().contains("Men"))
 		{
 			winner = Men.get(index);
 		}
 		else if(getGenderSelected().contains("Women"))
 		{
 			winner = Women.get(index);
 		}
		return winner;
	}
	
	private RankedPlayer getLoser()
	{
		Spinner playerB = (Spinner)findViewById(R.id.spinner_rankingEditor_playerB);
		RankedPlayer loser = new RankedPlayer();
		int index = playerB.getSelectedItemPosition();
 		if(getGenderSelected().contains("Men"))
 		{
 			loser = Men.get(index);
 		}
 		else if(getGenderSelected().contains("Women"))
 		{
 			loser = Women.get(index);
 		}
		return loser;
	}	
}
