package squashbot.dashboard;

import com.squashbot.R;

import classes.EnumTypes.EnterNamesType;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsMenu extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);

		 ActionBar actionbar = getActionBar();
		 actionbar.setTitle("Options Menu");
	}

	@Override
	public void onBackPressed()
	{	
		Intent intent = new Intent(this, Dashboard.class);
		finish();
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (id == R.id.next) 
		{
			Next();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Next()
	{
		Intent intent = new Intent(this, EnterNames.class);
		boolean use_warmup = Use_Warmup();
		boolean use_rest = Use_Rest();
		int game_points = Points();
		int sets = Sets();
		
		boolean score_game = true;
		
		intent.putExtra("use_rest", use_rest);
		intent.putExtra("use_warmup", use_warmup);
		intent.putExtra("game_points", game_points);
		intent.putExtra("sets", sets);
		intent.putExtra("score_game", score_game);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("resultType", EnterNamesType.ScoreNewGame);
		startActivity(intent);
	}
	
	public boolean Use_Warmup()
	{
		boolean use_warmup = true;
		CheckBox box = (CheckBox)findViewById(R.id.options_warmup);
		if(!box.isChecked())
		{
			use_warmup = false;
		}
		return use_warmup;
	}
	
	public boolean Use_Rest()
	{
		boolean use_rest = true;
		CheckBox box = (CheckBox)findViewById(R.id.options_between_games);
		if(!box.isChecked())
		{
			use_rest = false;
		}
		return use_rest;
	}

	public int Points()
	{
		int points = 0;
		RadioGroup group =  (RadioGroup)findViewById(R.id.rg_points);
		String selected_option = ((RadioButton)findViewById(group.getCheckedRadioButtonId())).getText().toString();
		if(selected_option.equals("9 points"))
		{
			points = 9;
		}
		else if(selected_option.equals("11 points"))
		{
			points = 11;
		}
		else
		{
			points = 15;
		}
		return points;
	}
	
	public int Sets()
	{
		int sets = 0;
		RadioGroup group = (RadioGroup)findViewById(R.id.rg_sets);
		String selected_option = ((RadioButton)findViewById(group.getCheckedRadioButtonId())).getText().toString();
		if(selected_option.equals("1 set"))
		{
			sets = 1;
		}
		else if(selected_option.equals("2 sets"))
		{
			sets = 2;
		}
		else
		{
			sets = 3;
		}
		return sets;
	}
}
