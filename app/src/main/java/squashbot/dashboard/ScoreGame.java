package squashbot.dashboard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import classes.ButtonStuff;
import classes.Player;
import classes.Player.AvatarColour;
import classes.Player.PlayerState;
import classes.Player.Side;
import classes.SquashMatch;

import com.squashbot.R;

import database.IndividualResults_Helper;
import display_notification.BlueToast;

public class ScoreGame extends Activity 
{
	boolean use_warmup, use_rest, handoutSaved;
	boolean start_next_game = false;
	boolean start_match = false;
	long setDuration = 0L;
	int game_points;
	int sets;
	String playerA, playerB;
	
	Player player_A, player_B, saved_playerA, saved_playerB;
	AvatarColour colourA, colourB;
	CountDownTimer timer;
	Context context;
	SquashMatch squashMatch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_game);
		
		context = getApplicationContext();
			
		use_warmup = getIntent().getBooleanExtra("use_warmup", true);
		use_rest = getIntent().getBooleanExtra("use_rest", true);
		game_points = getIntent().getIntExtra("game_points", 11);
		sets = getIntent().getIntExtra("sets", 3);
		playerA = getIntent().getStringExtra("playerA");
		playerB = getIntent().getStringExtra("playerB");
		
		squashMatch = new SquashMatch(game_points, sets, context);
		
		colourA = (AvatarColour)getIntent().getSerializableExtra("colourA");
		colourB = (AvatarColour)getIntent().getSerializableExtra("colourB");
		
		player_A = new Player(playerA, 0, 0, colourA);
		player_B = new Player(playerB, 0, 0, colourB);
		
		 ActionBar actionbar = getActionBar();
		 actionbar.setTitle(player_A.getName() + " vs " + player_B.getName());
		
		TextView ho = (TextView)findViewById(R.id.tv_handout);
		TextView score = (TextView)findViewById(R.id.tv_score);
		TextView scoreServer = (TextView)findViewById(R.id.tv_scoreServer);
		TextView scoreReceiver = (TextView)findViewById(R.id.tv_scoreReceiver);
		TextView side = (TextView)findViewById(R.id.tv_side);
		TextView name_A = (TextView)findViewById(R.id.tv_displaynameA);
		TextView name_B = (TextView)findViewById(R.id.tv_displaynameB);
		TextView scoresA1 = (TextView)findViewById(R.id.tv_scoreA1);
		TextView scoresA2 = (TextView)findViewById(R.id.tv_scoreA2);
		TextView scoresA3 = (TextView)findViewById(R.id.tv_scoreA3);
		TextView scoresA4 = (TextView)findViewById(R.id.tv_scoreA4);
		TextView scoresA5 = (TextView)findViewById(R.id.tv_scoreA5);
		TextView scoresB1 = (TextView)findViewById(R.id.tv_scoreB1);
		TextView scoresB2 = (TextView)findViewById(R.id.tv_scoreB2);
		TextView scoresB3 = (TextView)findViewById(R.id.tv_scoreB3);
		TextView scoresB4 = (TextView)findViewById(R.id.tv_scoreB4);
		TextView scoresB5 = (TextView)findViewById(R.id.tv_scoreB5);
		TextView gamesA = (TextView)findViewById(R.id.tv_gametotalA);
		TextView gamesB	= (TextView)findViewById(R.id.tv_gametotalB);
		TextView aboveA = (TextView)findViewById(R.id.tv_aboveA);
		TextView aboveB = (TextView)findViewById(R.id.tv_aboveB);
		
		ho.setVisibility(View.INVISIBLE);
		score.setVisibility(View.INVISIBLE);
		scoreServer.setVisibility(View.INVISIBLE);
		scoreReceiver.setVisibility(View.INVISIBLE);
		side.setVisibility(View.INVISIBLE);
		name_A.setVisibility(View.INVISIBLE);
		name_B.setVisibility(View.INVISIBLE);
		scoresA1.setVisibility(View.INVISIBLE);
		scoresA2.setVisibility(View.INVISIBLE);
		scoresA3.setVisibility(View.INVISIBLE);
		scoresA4.setVisibility(View.INVISIBLE);
		scoresA5.setVisibility(View.INVISIBLE);
		scoresB1.setVisibility(View.INVISIBLE);
		scoresB2.setVisibility(View.INVISIBLE);
		scoresB3.setVisibility(View.INVISIBLE);
		scoresB4.setVisibility(View.INVISIBLE);
		scoresB5.setVisibility(View.INVISIBLE);
		gamesA.setVisibility(View.INVISIBLE);
		gamesB.setVisibility(View.INVISIBLE);
		aboveA.setVisibility(View.INVISIBLE);
		aboveB.setVisibility(View.INVISIBLE);
		
		
		GenericTwoButtonDialog("Warmup", player_A, player_B);
		
		aboveA.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				PlayerWinsPoint(player_A, player_B);
			}
		});
		aboveB.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				PlayerWinsPoint(player_B, player_A);
			}
		});

	}
	
	@Override
	public void onBackPressed()
	{
		GenericTwoButtonDialog("Abandon", player_A, player_B);
	}
	
	//###########################
	//########## 	ACTION BAR
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.score_game, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == R.id.edit) 
		{
			EditDialog();
			return true;
		}
		else if(id == R.id.swop_sides)
		{
			SwopSides();
		}
		else if(id == R.id.undo)
		{
			Undo();
		}
		return super.onOptionsItemSelected(item);
	}
	//###########################
	//##########	ActionBar Item Selected
	public void SwopSides()
	{
		//Set player to opposite sides
		//Player A
		if(player_A.getSide() == Side.RIGHT)
		{
			player_A.setSide(Side.LEFT);
		}
		else
		{
			player_A.setSide(Side.RIGHT);
		}
		//Player B
		if(player_B.getSide() == Side.RIGHT)
		{
			player_B.setSide(Side.LEFT);
		}
		else
		{
			player_B.setSide(Side.RIGHT);
		}
		
		//Update Displayes
		if(player_A.isServer() == PlayerState.isServing)
		{
			UpdateSideDisplayed(player_A);
			UpdateButtonPositions(player_A, player_B);
		}
		else
		{
			UpdateSideDisplayed(player_B);
			UpdateButtonPositions(player_B, player_A);
		}
	}
	
	public void Undo()
	{
		if(saved_playerA == null || saved_playerB == null || (player_A.getScore() == 0 && player_B.getScore() == 0))
		{
			//nothing happens
		}
		else
		{
			//return players to previous state
			
			player_A = new Player(saved_playerA);
			player_A.setName(saved_playerA.getName());
			player_A.setServer(saved_playerA.isServer());
			if(saved_playerA.getSide() == Side.RIGHT)
			{
				player_A.setSide(Side.LEFT);
			}
			else
			{
				player_A.setSide(Side.RIGHT);
			}
			player_A.setScore(saved_playerA.getScore());
			player_A.setGameTotal(saved_playerA.getGameTotal());
			player_A.setScores(saved_playerA.getScores());
			player_A.setColour(saved_playerA.getColour());
			
			player_B = new Player(saved_playerB);
			player_B.setName(saved_playerB.getName());
			player_B.setServer(saved_playerB.isServer());
			if(saved_playerB.getSide() == Side.RIGHT)
			{
				player_B.setSide(Side.LEFT);
			}
			else
			{
				player_B.setSide(Side.RIGHT);
			}
			player_B.setScore(saved_playerB.getScore());
			player_B.setGameTotal(saved_playerB.getGameTotal());
			player_B.setScores(saved_playerB.getScores());
			player_B.setColour(saved_playerB.getColour());
			
			
			if(player_A.isServer() == PlayerState.isServing)
			{
				UpdateDisplays(player_A, player_B);
			}
			else
			{
				UpdateDisplays(player_B, player_A);
			}
			
			TextView handout = (TextView)findViewById(R.id.tv_handout);
			if(handoutSaved)
			{
				handout.setVisibility(View.VISIBLE);
			}
			else
			{
				handout.setVisibility(View.INVISIBLE);
			}
		}
	}
	//###########################
	//########## 	DIALOGS & DISPLAYS
	public void WarmupDialog()
	{
		if(use_warmup == true)
		{
			final ProgressDialog spinner = new ProgressDialog(this);
			spinner.setTitle("Warm up");
			spinner.setCancelable(false);
			spinner.setCanceledOnTouchOutside(false);
			timer = new CountDownTimer(300000, 1000)//5 minutes
			{
				@Override
				public void onFinish() 
				{
					spinner.cancel();
				}

				@Override
				public void onTick(long l) 
				{
					spinner.setMessage(((int)Math.round(l/1000.0)-1)+" secs remaining of warm up");
				}
			};
			
			spinner.setButton(DialogInterface.BUTTON_NEUTRAL, "Start Match", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					spinner.cancel();
				}
			});
			
			timer.start();
			spinner.show();
			spinner.setOnCancelListener(new DialogInterface.OnCancelListener() 
			{	
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					timer.cancel();
					ChooseServer();
				}
			});

		}
		else
		{
			ChooseServer();
		}
	}
	
	public void ChooseServer()
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
		popup.setTitle("Choose Server");
		popup.setCancelable(false);
		
		popup.setPositiveButton(player_A.getName(), new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				player_A.setServer(PlayerState.isServing);
				player_B.setServer(PlayerState.isReceiving);
				
				player_A.setSide(Side.RIGHT);
				player_B.setSide(Side.LEFT);
				
				dialog.cancel();
			}
		});
		popup.setNegativeButton(player_B.getName(), new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				player_A.setServer(PlayerState.isReceiving);
				player_B.setServer(PlayerState.isServing);
				
				player_A.setSide(Side.LEFT);
				player_B.setSide(Side.RIGHT);
				
				dialog.cancel();
			}
		});
		popup.setOnCancelListener(new DialogInterface.OnCancelListener() 
		{
			@Override
			public void onCancel(DialogInterface dialog) 
			{
				UpdateScoreboardScores();
				UpdateScoreboardGames();
				
				if(player_A.isServer() == PlayerState.isServing)
				{
					InitializeScreen(player_A,  player_B);
				}
				else
				{
					InitializeScreen(player_B,  player_A);
				}
				
				Chronometer set = (Chronometer)findViewById(R.id.cm_set);
				Chronometer match = (Chronometer)findViewById(R.id.cm_match);
				
				set.setBase(SystemClock.elapsedRealtime());
				match.setBase(SystemClock.elapsedRealtime());
				set.start();
				match.start();
			}
		});
		popup.show();
	}
	
	public void RestDialog(final Player game_winner, final Player game_loser)
	{
			final ProgressDialog rest = new ProgressDialog(this);
			rest.setTitle("Rest");
			rest.setCancelable(false);
			rest.setCanceledOnTouchOutside(false);
			timer = new CountDownTimer(90000, 1000)//1.5 minutes
			{
				@Override
				public void onFinish() 
				{
					rest.cancel();
				}

				@Override
				public void onTick(long l) 
				{
					rest.setMessage(((int)Math.round(l/1000.0)-1)+" seconds remaining");
				}
			};
			rest.setButton(DialogInterface.BUTTON_NEUTRAL, "Start Next Game", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					rest.cancel();
				}
			});
			timer.start();
			rest.show();
			rest.setOnCancelListener(new DialogInterface.OnCancelListener() 
			{	
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					timer.cancel();
					InitializeScreen(game_winner, game_loser);
					
					Chronometer set = (Chronometer)findViewById(R.id.cm_set);
					set.setBase(SystemClock.elapsedRealtime());
					set.start();
				}
			});
	}
	
	public void GenericTwoButtonDialog(String type, final Player game_winner, final Player game_loser)
	{
		if(type.contains("Abandon"))
		{
			AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
			popup.setTitle("Abandon Match?");
			popup.setCancelable(false);
			
			popup.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					Dashboard();
					dialog.cancel();
				}
			});
			popup.setNegativeButton("No", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					dialog.cancel();
				}
			});
			popup.show();
		}
		else if(type.contains("Warmup"))
		{
			if(use_warmup)
			{
				AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
				popup.setTitle("Warm up");
				popup.setCancelable(false);
				
				popup.setPositiveButton("Start Timer", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						WarmupDialog();
						dialog.cancel();
					}
				});
				popup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						ChooseServer();
						dialog.cancel();
					}
				});
				popup.show();
			}
			else
			{
				ChooseServer();
			}

		}
		else if(type.contains("Rest"))
		{
			if(use_rest)
			{
				AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
				popup.setTitle("Rest");
				popup.setCancelable(false);
				
				popup.setPositiveButton("Start Timer", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						RestDialog(game_winner, game_loser);
						dialog.cancel();
					}
				});
				popup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						InitializeScreen(game_winner, game_loser);
						
						Chronometer set = (Chronometer)findViewById(R.id.cm_set);
						set.setBase(SystemClock.elapsedRealtime());
						set.start();
						dialog.cancel();
					}
				});
				popup.show();
			}
			else
			{
				InitializeScreen(game_winner, game_loser);
				
				Chronometer set = (Chronometer)findViewById(R.id.cm_set);
				set.setBase(SystemClock.elapsedRealtime());
				set.start();
			}

		}
	}
	
	public void GamesIsWonToast(Player gameWinner, Player gameLoser)
	{	
		BlueToast blueToast = new BlueToast(context);
		String message = gameWinner.getName() + " won the game " + gameWinner.getScore() + " - " + gameLoser.getScore() + "\n";
		
		if(gameWinner.getGameTotal() > gameLoser.getGameTotal())
		{
			message += gameWinner.getName() + " leads " + gameWinner.getGameTotal() + " - " + gameLoser.getGameTotal(); 
		}
		else if (gameLoser.getGameTotal() > gameWinner.getGameTotal())
		{
			message += gameLoser.getName() + " leads " + gameLoser.getGameTotal() + " - " + gameWinner.getGameTotal();
		}
		else if (gameLoser.getGameTotal() == gameLoser.getGameTotal())
		{
			message += "Game scores are " + gameWinner.getGameTotal() + " - " + gameLoser.getGameTotal();
		}
		
		//Duration of set
		Chronometer set = (Chronometer)findViewById(R.id.cm_set);
		setDuration = SystemClock.elapsedRealtime() - set.getBase();
    	int Duration = Math.round((int)setDuration/1000);
	    int minutes = (Duration % 3600)/60;
	    int seconds = (Duration % 60);
		message += "\nDuration- "+minutes + ":" + seconds;
		
		//Display
		blueToast.MakeBlueToast(context, message, true, true);
	}
	
	public void MatchIsOverDialog(final Player winner, final Player loser)
	{
		TextView ho = (TextView)findViewById(R.id.tv_handout);
		TextView score = (TextView)findViewById(R.id.tv_score);
		TextView side = (TextView)findViewById(R.id.tv_side);
		TextView underA = (TextView)findViewById(R.id.tv_aboveA);
		TextView underB = (TextView)findViewById(R.id.tv_aboveB);
		TextView scoreServer = (TextView)findViewById(R.id.tv_scoreServer);
		TextView scoreReceiver = (TextView)findViewById(R.id.tv_scoreReceiver);
		
		ho.setVisibility(View.INVISIBLE);
		score.setVisibility(View.INVISIBLE);
		side.setVisibility(View.INVISIBLE);
		underA.setVisibility(View.INVISIBLE);
		underB.setVisibility(View.INVISIBLE);
		scoreServer.setVisibility(View.INVISIBLE);
		scoreReceiver.setVisibility(View.INVISIBLE);
		
		AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
		popup.setTitle("MATCH IS OVER");
		popup.setMessage(winner.getName()+" has won the match "+ winner.getGameTotal()+"-"+loser.getGameTotal()+" against "+loser.getName());

		popup.setPositiveButton("Save Result", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				SaveGameDialog(winner, loser);
			}
		});	
		popup.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				DontSaveDialog(winner, loser);
			}
		});
		popup.show();
	}
	
	public void SaveGameDialog(final Player winner, final Player loser)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
		popup.setTitle("ENTER THE VENUE");
		popup.setCancelable(false);
		
		Chronometer cm = (Chronometer)findViewById(R.id.cm_match);
		final long duration = SystemClock.elapsedRealtime() - cm.getBase();
		
		final IndividualResults_Helper db = new IndividualResults_Helper(this);
		
		final EditText et_venue = new EditText(ScoreGame.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
																		LinearLayout.LayoutParams.MATCH_PARENT);
		et_venue.setLayoutParams(lp);
		popup.setView(et_venue);
		
		popup.setPositiveButton("Save", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{			
				String venue = et_venue.getText().toString();
				db.InsertMatchResult(winner, loser, duration, venue);
				Dashboard();
			}
		}); 

		popup.show();
	}
	
	public void DontSaveDialog(final Player winner, final Player loser)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
		popup.setTitle("ARE YOU SURE");
		popup.setMessage("Are you sure you want to exit?");
		popup.setCancelable(false);
		popup.setPositiveButton("Yes, return to dashboard", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Dashboard();
			}
		});	
		popup.setNegativeButton("No,  save result", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				SaveGameDialog(winner, loser);
			}
		});
		popup.show();
	}
	
	public void EditDialog()
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(ScoreGame.this);
		popup.setTitle("EDIT");
		popup.setIcon(R.drawable.dark_ic_action_edit);
		LayoutInflater inflater = getLayoutInflater();
		View edit_view = inflater.inflate(R.layout.edit_dialog, null);
		popup.setView(edit_view);
		popup.setCancelable(false);
		
		final Switch serve = (Switch)edit_view.findViewById(R.id.toggle_serve);
		final Switch side = (Switch)edit_view.findViewById(R.id.toggle_side);
		serve.setTextOn(player_A.getName());
		serve.setTextOff(player_B.getName());
		side.setEnabled(true);
		serve.setEnabled(true);
		
		if(player_A.isServer() == PlayerState.isServing)
		{
			serve.setChecked(true);
			if(player_A.getSide()== Side.RIGHT)
			{
				side.setChecked(true);
			}
			else
			{
				side.setChecked(false);
			}
		}
		else
		{
			serve.setChecked(false);
			if(player_B.getSide()== Side.RIGHT)
			{
				side.setChecked(true);
			}
			else
			{
				side.setChecked(false);;
			}
		}
		
		TextView nameA = (TextView)edit_view.findViewById(R.id.tv_edit_nameA);
		TextView nameB = (TextView)edit_view.findViewById(R.id.tv_edit_nameB);
		nameA.setText(player_A.getName());
		nameB.setText(player_B.getName());
		
		final NumberPicker scoreA = (NumberPicker)edit_view.findViewById(R.id.np_A);
		final NumberPicker scoreB = (NumberPicker)edit_view.findViewById(R.id.np_B);
		
		scoreA.setMaxValue(game_points);
		scoreA.setMinValue(0);
		scoreA.setValue(player_A.getScore());
		scoreA.setWrapSelectorWheel(true);
		
		scoreB.setMaxValue(game_points);
		scoreB.setMinValue(0);
		scoreB.setValue(player_B.getScore());
		scoreB.setWrapSelectorWheel(true);
		
		popup.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//Set to opposite side.
				//UpdateDisplays -> UpdateSideDisplayed
				//will set back to correct side...
				if(serve.isChecked())
				{
					if(side.isChecked())
					{
						player_A.setServer(PlayerState.isServing);
						player_A.setSide(Side.LEFT);
						player_B.setServer(PlayerState.isReceiving);
						player_B.setSide(Side.RIGHT);
					}
					else
					{
						player_A.setServer(PlayerState.isServing);
						player_A.setSide(Side.RIGHT);
						player_B.setServer(PlayerState.isReceiving);
						player_B.setSide(Side.LEFT);
					}
				}
				else
				{
					if(side.isChecked())
					{
						player_A.setServer(PlayerState.isReceiving);
						player_A.setSide(Side.RIGHT);
						player_B.setServer(PlayerState.isServing);
						player_B.setSide(Side.LEFT);
					}
					else
					{
						player_A.setServer(PlayerState.isReceiving);
						player_A.setSide(Side.LEFT);
						player_B.setServer(PlayerState.isServing);
						player_B.setSide(Side.RIGHT);
					}
				}
				
				player_A.setScore(scoreA.getValue());
				player_B.setScore(scoreB.getValue());
				dialog.cancel();
			}
		});
		popup.setOnCancelListener(new DialogInterface.OnCancelListener()
		{	
			@Override
			public void onCancel(DialogInterface dialog) 
			{
				if(player_A.isServer() == PlayerState.isServing)
				{
					UpdateDisplays(player_A, player_B);
				}
				else
				{
					UpdateDisplays(player_B, player_A);
				}
			}
		});
		popup.show();
	}
	
	//###########################
	//#########		DECISION TREE
	public void DecisionTree(Player rally_winner, Player rally_loser)
	{
		IncrementScore(rally_winner);
		
		//method will check is it is MatchBall, and if not
		//it will check if it is GameBall
		squashMatch.isMatchBall(rally_winner, rally_loser);
		//check for both players
		squashMatch.isMatchBall(rally_loser, rally_winner);
		
		//player wins a set but the match is not over
		if(squashMatch.GameIsWon(rally_winner, rally_loser)==true && squashMatch.MatchIsWon(rally_winner)== false)
		{
			IncrementGameTotal(rally_winner);
		}
		
		//Match Update
		if(squashMatch.GameIsWon(rally_winner, rally_loser)==true && squashMatch.MatchIsWon(rally_winner)== false)
		{
			GamesIsWonToast(rally_winner, rally_loser);
		}
		
		//player wins the match
		if(squashMatch.MatchIsWon(rally_winner) == true)
		{
			ResetScreen(rally_winner, rally_loser);
			InitializeScreen(rally_winner, rally_loser);
			
			
			Chronometer cm = (Chronometer)findViewById(R.id.cm_match);
			cm.stop();
			Chronometer set = (Chronometer)findViewById(R.id.cm_set);
			set.stop();
			
			MatchIsOverDialog(rally_winner, rally_loser);
		}
		//player wins a game
		else if(squashMatch.GameIsWon(rally_winner, rally_loser)== true)
		{	
			ResetScreen(rally_winner, rally_loser);
			GenericTwoButtonDialog("Rest", rally_winner, rally_loser);
			start_next_game = false;
		}
		else
		{
			UpdateDisplays(rally_winner, rally_loser);
		}
	}
	
	//###########################
	//########		DISPLAYS
	public void InitializeScreen(final Player game_winner, final Player game_loser)
	{
		TextView score = (TextView)findViewById(R.id.tv_score);
		TextView scoreServer = (TextView)findViewById(R.id.tv_scoreServer);
		TextView scoreReceiver = (TextView)findViewById(R.id.tv_scoreReceiver);
		TextView side = (TextView)findViewById(R.id.tv_side);
		TextView name_A = (TextView)findViewById(R.id.tv_displaynameA);
		TextView name_B = (TextView)findViewById(R.id.tv_displaynameB);
		TextView gamesA = (TextView)findViewById(R.id.tv_gametotalA);
		TextView gamesB	= (TextView)findViewById(R.id.tv_gametotalB);
		TextView underA = (TextView)findViewById(R.id.tv_aboveA);
		TextView underB = (TextView)findViewById(R.id.tv_aboveB);
		TextView set_duration = (TextView)findViewById(R.id.tv_setduration);
		Chronometer set = (Chronometer)findViewById(R.id.cm_set);
		
		
		//Make everything visible
		score.setVisibility(View.VISIBLE);
		scoreServer.setVisibility(View.VISIBLE);
		scoreReceiver.setVisibility(View.VISIBLE);
		side.setVisibility(View.VISIBLE);
		name_A.setVisibility(View.VISIBLE);
		name_B.setVisibility(View.VISIBLE);
		underA.setVisibility(View.VISIBLE);
		underB.setVisibility(View.VISIBLE);
		gamesA.setVisibility(View.VISIBLE);
		gamesB.setVisibility(View.VISIBLE);
		set_duration.setVisibility(View.VISIBLE);
		set.setVisibility(View.VISIBLE);
		
		name_A.setText(player_A.getName());
		underA.setText(player_A.getName());
		name_B.setText(player_B.getName());
		underB.setText(player_B.getName());
		
		SetupScoreBoardColors();
		UpdateScoreboardGames();
		UpdateScoreboardScores();
		UpdateDisplays(game_winner, game_loser);
	}

	public void ResetScreen(Player game_winner, Player game_loser)
	{
		TextView ho = (TextView)findViewById(R.id.tv_handout);
		TextView score = (TextView)findViewById(R.id.tv_score);
		TextView scoreServer = (TextView)findViewById(R.id.tv_scoreServer);
		TextView scoreReceiver = (TextView)findViewById(R.id.tv_scoreReceiver);
		TextView side = (TextView)findViewById(R.id.tv_side);
		TextView underA = (TextView)findViewById(R.id.tv_aboveA);
		TextView underB = (TextView)findViewById(R.id.tv_aboveB);
		TextView set_duration = (TextView)findViewById(R.id.tv_setduration);
		Chronometer set = (Chronometer)findViewById(R.id.cm_set);
		
		ho.setVisibility(View.INVISIBLE);
		score.setVisibility(View.INVISIBLE);
		scoreServer.setVisibility(View.INVISIBLE);
		scoreReceiver.setVisibility(View.INVISIBLE);
		side.setVisibility(View.INVISIBLE);
		underA.setVisibility(View.INVISIBLE);
		underB.setVisibility(View.INVISIBLE);
		set_duration.setVisibility(View.INVISIBLE);
		set.setVisibility(View.INVISIBLE);
		
		game_winner.setSide(Side.RIGHT);
		game_loser.setSide(Side.LEFT);
		game_winner.setServer(PlayerState.isServing);
		game_loser.setServer(PlayerState.isReceiving);
		game_winner.addScore(game_winner.getScore());
		game_loser.addScore(game_loser.getScore());
		game_winner.setScore(0);
		game_loser.setScore(0);
		
		UpdateScoreboardGames();
		UpdateScoreboardScores();
	}
	
	public void IncrementScore(Player rally_winner)
	{
		int score = rally_winner.getScore();
		score++;
		rally_winner.setScore(score);
	}
	
	public void IncrementGameTotal(Player game_winner)
	{
		int games_score = game_winner.getGameTotal();
		games_score++;
		game_winner.setGameTotal(games_score);
	}
	
	public void UpdateDisplays(Player rally_winner, Player rally_loser)
	{
		UpdateHandout(rally_winner);
		UpdateServer(rally_winner, rally_loser);
		UpdateScoresDisplayed();
		UpdateSideDisplayed(rally_winner);
		UpdateButtonPositions(rally_winner, rally_loser);
	}
	
	public void UpdateHandout(Player rally_winner)
	{
		TextView handout = (TextView)findViewById(R.id.tv_handout);
		
		if(rally_winner.isServer() == PlayerState.isReceiving  && rally_winner.getScore() != 0)
		{
			handout.setVisibility(View.VISIBLE);
		}
		else
		{
			handout.setVisibility(View.INVISIBLE);
		}
	}
	
	public void UpdateServer(Player rally_winner, Player rally_loser)
	{
		if(rally_winner.isServer()== PlayerState.isServing && rally_winner.getScore() != 0)
		{
			//rally_winner was serving anyway
			if(rally_winner.getSide()== Side.RIGHT)	//winner was serving right
			{
				rally_winner.setSide(Side.LEFT);		//winner moves left	
				rally_loser.setSide(Side.RIGHT);			//loser moves right
			}
			else									//winner was serving left
			{
				rally_winner.setSide(Side.RIGHT);			//winner moves right
				rally_loser.setSide(Side.LEFT);			//loser moves left
			}
		}
		else										//Handout
		{
			rally_winner.setServer(PlayerState.isServing);			//winner is now serving
			rally_loser.setServer(PlayerState.isReceiving);			//loser is now receiving
			
			rally_winner.setSide(Side.RIGHT);				//winner moves right
			rally_loser.setSide(Side.LEFT);			//loser moves left
		}
	}
	
	public void UpdateButtonPositions(Player rally_winner, Player rally_loser)
	{
		TextView aboveA = (TextView)findViewById(R.id.tv_aboveA);
		TextView nameA = (TextView)findViewById(R.id.tv_displaynameA);
		TextView gamesA = (TextView)findViewById(R.id.tv_gametotalA);
		TextView aboveB = (TextView)findViewById(R.id.tv_aboveB);
		TextView nameB = (TextView)findViewById(R.id.tv_displaynameB);
		TextView gamesB = (TextView)findViewById(R.id.tv_gametotalB);
		ButtonStuff buttonA;
		ButtonStuff buttonB;
		
		switch(GamesPlayed())
		{
		case 0:
		case 1:
			TextView _scoresA1 = (TextView)findViewById(R.id.tv_scoreA1);
			buttonA = new ButtonStuff(aboveA, nameA, _scoresA1, gamesA, player_A, context);
			TextView _scoresB1 = (TextView)findViewById(R.id.tv_scoreB1);
			buttonB = new ButtonStuff(aboveB, nameB, _scoresB1, gamesB, player_B, context);
			
			buttonA.setButtonPosition();
			buttonA.setAvatar();
			buttonB.setButtonPosition();
			buttonB.setAvatar();
			break;
		case 2:
			TextView scoresA2 = (TextView)findViewById(R.id.tv_scoreA2);
			buttonA = new ButtonStuff(aboveA, nameA, scoresA2, gamesA, player_A, context);		
			TextView scoresB2 = (TextView)findViewById(R.id.tv_scoreB2);
			buttonB = new ButtonStuff(aboveB, nameB, scoresB2, gamesB, player_B, context);
			
			buttonA.setButtonPosition();
			buttonA.setAvatar();
			buttonB.setButtonPosition();
			buttonB.setAvatar();
			break;
		case 3:
			TextView scoresA3 = (TextView)findViewById(R.id.tv_scoreA3);
			buttonA = new ButtonStuff(aboveA, nameA, scoresA3, gamesA, player_A, context);		
			TextView scoresB3 = (TextView)findViewById(R.id.tv_scoreB3);
			buttonB = new ButtonStuff(aboveB, nameB, scoresB3, gamesB, player_B, context);
			
			buttonA.setButtonPosition();
			buttonA.setAvatar();
			buttonB.setButtonPosition();
			buttonB.setAvatar();
			break;
		case 4:
			TextView scoresA4 = (TextView)findViewById(R.id.tv_scoreA4);
			buttonA = new ButtonStuff(aboveA, nameA, scoresA4, gamesA, player_A, context);		
			TextView scoresB4 = (TextView)findViewById(R.id.tv_scoreB4);
			buttonB = new ButtonStuff(aboveB, nameB, scoresB4, gamesB, player_B, context);
			
			buttonA.setButtonPosition();
			buttonA.setAvatar();
			buttonB.setButtonPosition();
			buttonB.setAvatar();
			break;
		case 5:
			TextView scoresA5 = (TextView)findViewById(R.id.tv_scoreA5);
			buttonA = new ButtonStuff(aboveA, nameA, scoresA5, gamesA, player_A, context);		
			TextView scoresB5 = (TextView)findViewById(R.id.tv_scoreB5);
			buttonB = new ButtonStuff(aboveB, nameB, scoresB5, gamesB, player_B, context);
			
			buttonA.setButtonPosition();
			buttonA.setAvatar();
			buttonB.setButtonPosition();
			buttonB.setAvatar();
			break;
		}
}
	
	public void UpdateScoresDisplayed()
	{
		TextView scoreServer = (TextView)findViewById(R.id.tv_scoreServer);
		TextView scoreReceiver = (TextView)findViewById(R.id.tv_scoreReceiver);
		
		if(player_A.isServer()== PlayerState.isServing)
		{
			scoreServer.setText(""+player_A.getScore());
			scoreReceiver.setText(""+player_B.getScore());
		}
		else
		{
			scoreServer.setText(""+player_B.getScore());
			scoreReceiver.setText(""+player_A.getScore());
		}
	}

	public void UpdateSideDisplayed(Player rally_winner)
	{
		TextView side = (TextView)findViewById(R.id.tv_side);
		if(rally_winner.getSide()== Side.RIGHT)
		{
			side.setText("Right");
		}
		else
		{
			side.setText("Left");
		}
	}

	public void UpdateScoreboardScores()
	{
		TextView scoresA1 = (TextView)findViewById(R.id.tv_scoreA1);
		TextView scoresA2 = (TextView)findViewById(R.id.tv_scoreA2);
		TextView scoresA3 = (TextView)findViewById(R.id.tv_scoreA3);
		TextView scoresA4 = (TextView)findViewById(R.id.tv_scoreA4);
		TextView scoresA5 = (TextView)findViewById(R.id.tv_scoreA5);
		TextView scoresB1 = (TextView)findViewById(R.id.tv_scoreB1);
		TextView scoresB2 = (TextView)findViewById(R.id.tv_scoreB2);
		TextView scoresB3 = (TextView)findViewById(R.id.tv_scoreB3);
		TextView scoresB4 = (TextView)findViewById(R.id.tv_scoreB4);
		TextView scoresB5 = (TextView)findViewById(R.id.tv_scoreB5);
		
		
		switch(GamesPlayed())
		{
		case 0:
			break;
		case 1:
			scoresA1.setVisibility(View.VISIBLE);
			scoresB1.setVisibility(View.VISIBLE);
			scoresA1.setText(""+player_A.getScores().get(0));
			scoresB1.setText(""+player_B.getScores().get(0));
			break;
		case 2:
			scoresA2.setVisibility(View.VISIBLE);
			scoresB2.setVisibility(View.VISIBLE);
			scoresA2.setText(""+player_A.getScores().get(1));
			scoresB2.setText(""+player_B.getScores().get(1));
			break;
		case 3:
			scoresA3.setVisibility(View.VISIBLE);
			scoresB3.setVisibility(View.VISIBLE);
			scoresA3.setText(""+player_A.getScores().get(2));
			scoresB3.setText(""+player_B.getScores().get(2));
			break;
		case 4:
			scoresA4.setVisibility(View.VISIBLE);
			scoresB4.setVisibility(View.VISIBLE);
			scoresA4.setText(""+player_A.getScores().get(3));
			scoresB4.setText(""+player_B.getScores().get(3));
			break;
		case 5:
			scoresA5.setVisibility(View.VISIBLE);
			scoresB5.setVisibility(View.VISIBLE);
			scoresA5.setText(""+player_A.getScores().get(4));
			scoresB5.setText(""+player_B.getScores().get(4));
			break;
		}
	}
	
	private int GamesPlayed()
	{
		int gamesPlayed = player_A.getGameTotal() + player_B.getGameTotal();
		return gamesPlayed;
	}
	
	public void UpdateScoreboardGames()
	{
		TextView gamesA = (TextView)findViewById(R.id.tv_gametotalA);
		TextView gamesB	= (TextView)findViewById(R.id.tv_gametotalB);
		
		gamesA.setText(""+player_A.getGameTotal());
		gamesB.setText(""+player_B.getGameTotal());
	}

	public void Dashboard()
	{
		Intent intent = new Intent(this, Dashboard.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(intent);
	}

	public void PlayerWinsPoint(Player rally_winner, Player rally_loser)
	{
		saved_playerA = new Player(player_A);
		saved_playerA.setName(player_A.getName());
		saved_playerA.setServer(player_A.isServer());
		saved_playerA.setSide(player_A.getSide());
		saved_playerA.setScore(player_A.getScore());
		saved_playerA.setGameTotal(player_A.getGameTotal());
		saved_playerA.setScores(player_A.getScores());
		saved_playerA.setColour(player_A.getColour());
		
		saved_playerB = new Player(player_B);
		saved_playerB.setName(player_B.getName());
		saved_playerB.setServer(player_B.isServer());
		saved_playerB.setSide(player_B.getSide());
		saved_playerB.setScore(player_B.getScore());
		saved_playerB.setGameTotal(player_B.getGameTotal());
		saved_playerB.setScores(player_B.getScores());
		saved_playerB.setColour(player_B.getColour());
		
		
		TextView handout = (TextView)findViewById(R.id.tv_handout);
		if(handout.getVisibility() == View.VISIBLE)
		{
			handoutSaved = true;
		}
		else
		{
			handoutSaved = false;
		}
		
		DecisionTree(rally_winner, rally_loser);
	}
	
	private void SetupScoreBoardColors()
	{
		TextView gamesA = (TextView)findViewById(R.id.tv_gametotalA);
		TextView gamesB	= (TextView)findViewById(R.id.tv_gametotalB);
		
		TextView scoresA1 = (TextView)findViewById(R.id.tv_scoreA1);
		TextView scoresA2 = (TextView)findViewById(R.id.tv_scoreA2);
		TextView scoresA3 = (TextView)findViewById(R.id.tv_scoreA3);
		TextView scoresA4 = (TextView)findViewById(R.id.tv_scoreA4);
		TextView scoresA5 = (TextView)findViewById(R.id.tv_scoreA5);
		TextView scoresB1 = (TextView)findViewById(R.id.tv_scoreB1);
		TextView scoresB2 = (TextView)findViewById(R.id.tv_scoreB2);
		TextView scoresB3 = (TextView)findViewById(R.id.tv_scoreB3);
		TextView scoresB4 = (TextView)findViewById(R.id.tv_scoreB4);
		TextView scoresB5 = (TextView)findViewById(R.id.tv_scoreB5);
		
		ButtonStuff tvColoursA = new ButtonStuff(player_A, context);
		tvColoursA.setTextColour(gamesA);
		tvColoursA.setTextColour(scoresA1);
		tvColoursA.setTextColour(scoresA2);
		tvColoursA.setTextColour(scoresA3);
		tvColoursA.setTextColour(scoresA4);
		tvColoursA.setTextColour(scoresA5);
		
		ButtonStuff tvColoursB = new ButtonStuff(player_B, context);
		tvColoursB.setTextColour(gamesB);
		tvColoursB.setTextColour(scoresB1);
		tvColoursB.setTextColour(scoresB2);
		tvColoursB.setTextColour(scoresB3);
		tvColoursB.setTextColour(scoresB4);
		tvColoursB.setTextColour(scoresB5);
	}
}
