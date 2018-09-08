package tournament_activities;


import squashbot.dashboard.Dashboard;
import tournament_activities.MatchResult.MatchResultEnum;
import tournament_classes.GameResult;
import tournament_classes.ResultForTeam;
import tournament_classes.TeamResultHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import asynchronious_classes.insertTeamResult;
import classes.NetworkConnection;

import com.squashbot.R;

import display_notification.BlueToast;
import display_notification.RedToast;

public class VerifyResult extends Activity
{

	TeamResultHelper helper;
	private static Context context;
	static Activity activity;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_result);

		ActionBar actionbar = getActionBar();
		actionbar.setTitle("Verify result");

		activity = this;
		
		context = VerifyResult.this;
		helper = (TeamResultHelper) getIntent().getParcelableExtra("helper");

		SetupScreen();
		
		Button submit = (Button)findViewById(R.id.btn_verifysubmit);
		submit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				getPassword();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		for(int x  = 0; x < helper.IndividualResults().size(); x++)
		{
			menu.add(0, x, 0, "Edit Match #"+(x+1));
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		GoToEditResult(item.getItemId()+1);
		return true;
	}

	private void SetupScreen()
	{
		TextView details = (TextView)findViewById(R.id.tv_verifyResult_FixtureDetails);
		String detailText = "";
		detailText += helper.Tournament().TournamentName()+"\n";
		detailText += helper.Fixture().Gender() + ", " + helper.Fixture().Section() + " " + helper.Tournament().NamingConvention() + "\n"; 
		detailText += helper.Fixture().TeamA_Name() + " vs " + helper.Fixture().TeamB_Name() + "\n\n";
		detailText += helper.Fixture().VenueName() + ", Court " + helper.Fixture().CourtNumber() + "\n";
		detailText += helper.Fixture().FriendlyDate() + ", " + helper.Fixture().FriendlyTime();
		details.setText(detailText);
		
		TextView results = (TextView)findViewById(R.id.tv_verifyResult_FixtureResults);
		String resultText = "";

		String winningTeamName = "";
		String losingTeamName = "";

		if(helper.WinningTeamId() == helper.Fixture().TeamA_ID())
		{
			winningTeamName = helper.Fixture().TeamA_Name();
			losingTeamName = helper.Fixture().TeamB_Name();
		}
		else
		{
			losingTeamName = helper.Fixture().TeamA_Name();
			winningTeamName = helper.Fixture().TeamB_Name();
		}

		resultText += winningTeamName + " beats " + losingTeamName + "\n\n";
		resultText += helper.WinnerFinalScore() + " - " + helper.LoserFinalScore() + "\n\n\n";

		resultText += AddResultText(helper);

		results.setText(resultText);
	}

	@Override
	public void onBackPressed()
	{
		GoToEditResult(helper.Fixture().PlayersPerTeam()); //last index
	}
	
	public void GoToEditResult(int index)
	{
		helper.setIndex(index);
		helper.setType(MatchResultEnum.EditResult);
		
		Intent intent = new Intent(this, MatchResult.class);
		intent.putExtra("helper", helper);
		startActivity(intent);
	}
	
	public void getPassword()
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(VerifyResult.this);
		popup.setTitle("INSERT PASSWORD");
		popup.setCancelable(true);
		
		final EditText et_password = new EditText(VerifyResult.this);
		et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																		LinearLayout.LayoutParams.MATCH_PARENT);
		et_password.setLayoutParams(lp);
		popup.setView(et_password);
		
		popup.setPositiveButton("Submit to database", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if(et_password.getText().toString().contains(helper.Tournament().CasualPassword()) || et_password.getText().toString().contains(helper.Tournament().AdminPassword()))
				{
					NetworkConnection connection = new NetworkConnection(context);
					if(connection.NetworkIsAvailable())
					{
						new insertTeamResult(context, helper).execute();
					}
					else
					{
						RedToast toast = new RedToast(context);
						toast.NoNetworkConnection();
					}
				}
				else
				{
					RedToast toast = new RedToast(context);
					toast.MakeRedToast(context, "Invalid Password", false, false);
				}
			}
		});
		popup.show();
	}
	
	public static void GoToDashBoard()
	{
		BlueToast toast = new BlueToast(context);
		toast.MakeBlueToast(context, "Result submitted sucessfully", false, false);
		Intent intent = new Intent(context, Dashboard.class);
		context.startActivity(intent);
		activity.finish();
	}

	public String AddResultText(TeamResultHelper helper)
	{
		String results = "";

		for(ResultForTeam matchResult : helper.IndividualResults())
		{
			int playerA_Games = 0;
			int playerB_Games = 0;
			for(GameResult gameResult : matchResult.GameResults())
			{
				if(gameResult.PlayerAPoints() > gameResult.PlayerBPoints())
				{
					playerA_Games++;
				}
				else
				{
					playerB_Games++;
				}
			}
			String singleResult = "";
			if(playerA_Games > playerB_Games)
			{
				singleResult += matchResult.PlayerA() + " (" + helper.Fixture().TeamA_Name() + ") beats " +  matchResult.PlayerB() + " (" + helper.Fixture().TeamB_Name() + ")\n";
				singleResult += playerA_Games + " - " + playerB_Games + " :\t";
				for(GameResult gameResult : matchResult.GameResults())
				{
					singleResult += gameResult.PlayerAPoints() + "-" + gameResult.PlayerBPoints() + ", ";
				}
			}
			else
			{
				singleResult += matchResult.PlayerB() + " (" + helper.Fixture().TeamB_Name() + ") beats " +  matchResult.PlayerA() + " (" + helper.Fixture().TeamA_Name() + ")\n";
				singleResult += playerB_Games + " - " + playerA_Games + " :\t";
				for(GameResult gameResult : matchResult.GameResults())
				{
					singleResult += gameResult.PlayerBPoints() + "-" + gameResult.PlayerAPoints() + ", ";
				}
			}

			singleResult = singleResult.substring(0, singleResult.length() - 2);
			results += singleResult + "\n\n";
		}

		return results;
	}
}
