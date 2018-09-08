package classes;

import classes.Player.PlayerState;
import classes.Player.Side;

import com.squashbot.R;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ButtonStuff 
{
	TextView aboveButton, displayName, displayScore, displayGames;
	Player player;
	Context context;
	
	public ButtonStuff(TextView aboveButton, TextView displayName, TextView displayScore, TextView displayGames, Player player, Context context)
	{
		this.aboveButton = aboveButton;
		this.displayName = displayName;
		this.displayScore = displayScore;
		this.displayGames = displayGames;
		this.player = player;
		this.context = context;
	}
	
	public ButtonStuff(Player player, Context context)
	{
		this.player = player;
		this.context = context;
	}
	
	public void setButtonPosition()
	{
		///////////////////////////////////Button positions
		RelativeLayout.LayoutParams serve_right = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		serve_right.addRule(RelativeLayout.CENTER_VERTICAL);
		serve_right.addRule(RelativeLayout.ALIGN_RIGHT, R.id.squashcourt);
		serve_right.setMargins(0, 0, 15, 0);
		
		RelativeLayout.LayoutParams serve_left = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		serve_left.addRule(RelativeLayout.CENTER_VERTICAL);
		serve_left.addRule(RelativeLayout.ALIGN_LEFT, R.id.squashcourt);
		serve_left.setMargins(15, 0, 0, 0);
		
		RelativeLayout.LayoutParams receive_right = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		receive_right.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.squashcourt);
		receive_right.addRule(RelativeLayout.ALIGN_RIGHT, R.id.squashcourt);
		receive_right.setMargins(0, 0, 15, 15);
		
		RelativeLayout.LayoutParams receive_left = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		receive_left.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.squashcourt);
		receive_left.addRule(RelativeLayout.ALIGN_LEFT, R.id.squashcourt);
		receive_left.setMargins(15, 0, 0, 15);
		
		if(player.isServer() == PlayerState.isServing)
		{
			if(player.getSide() == Side.RIGHT)
			{
				aboveButton.setLayoutParams(serve_right);
			}
			else if(player.getSide() == Side.LEFT)
			{
				aboveButton.setLayoutParams(serve_left);
			}
		}
		else if(player.isServer() == PlayerState.isReceiving)
		{
			if(player.getSide() == Side.RIGHT)
			{
				aboveButton.setLayoutParams(receive_right);
			}
			else if(player.getSide() == Side.LEFT)
			{
				aboveButton.setLayoutParams(receive_left);
			}
		}
	}

	public void setAvatar()
	{
		if(player.getSide() == Side.RIGHT)
		{
			switch(player.getColour())
			{
				case Red:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.red_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Red));
					displayName.setTextColor(context.getResources().getColor(R.color.Red));
					displayScore.setTextColor(context.getResources().getColor(R.color.Red));
					displayGames.setTextColor(context.getResources().getColor(R.color.Red));
				break;
				
				case Blue:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.blue_right);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Navy));
					displayName.setTextColor(context.getResources().getColor(R.color.Navy));
					displayScore.setTextColor(context.getResources().getColor(R.color.Navy));
					displayGames.setTextColor(context.getResources().getColor(R.color.Navy));
				break;
				
				case Black:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.black_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Black));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;
				
				case White:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.white_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.White));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;
				
				case Green:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.green_right);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Green));
					displayName.setTextColor(context.getResources().getColor(R.color.Green));
					displayScore.setTextColor(context.getResources().getColor(R.color.Green));
					displayGames.setTextColor(context.getResources().getColor(R.color.Green));			
				break;
				
				case Orange:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.orange_right);
					aboveButton.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayName.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayScore.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayGames.setTextColor(context.getResources().getColor(R.color.OrangeRed));			
				break;
				
				case Pink:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.pink_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayName.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayScore.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayGames.setTextColor(context.getResources().getColor(R.color.Magenta));
				break;
				
				case Yellow:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.yellow_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayName.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayScore.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayGames.setTextColor(context.getResources().getColor(R.color.Yellow));
				break;
				
				case Gray:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.gray_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Black));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;
				
				case Purple:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.purple_right);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayName.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayScore.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayGames.setTextColor(context.getResources().getColor(R.color.BlueViolet));
				break;
			}
		}
		else if(player.getSide() == Side.LEFT)
		{
			switch(player.getColour())
			{
				case Red:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.red_left);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Red));
					displayName.setTextColor(context.getResources().getColor(R.color.Red));
					displayScore.setTextColor(context.getResources().getColor(R.color.Red));
					displayGames.setTextColor(context.getResources().getColor(R.color.Red));
				break;
				
				case Blue:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.blue_left);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Navy));
					displayName.setTextColor(context.getResources().getColor(R.color.Navy));
					displayScore.setTextColor(context.getResources().getColor(R.color.Navy));
					displayGames.setTextColor(context.getResources().getColor(R.color.Navy));
				break;
				
				case Black:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.black_left);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Black));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;
				
				case White:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.white_left);
					aboveButton.setTextColor(context.getResources().getColor(R.color.White));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;
				
				case Green:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.green_left);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Green));
					displayName.setTextColor(context.getResources().getColor(R.color.Green));
					displayScore.setTextColor(context.getResources().getColor(R.color.Green));
					displayGames.setTextColor(context.getResources().getColor(R.color.Green));
				break;
				
				case Orange:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.orange_left);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayName.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayScore.setTextColor(context.getResources().getColor(R.color.OrangeRed));
					displayGames.setTextColor(context.getResources().getColor(R.color.OrangeRed));
				break;
				
				case Pink:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.pink_left);
					aboveButton.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayName.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayScore.setTextColor(context.getResources().getColor(R.color.Magenta));
					displayGames.setTextColor(context.getResources().getColor(R.color.Magenta));
				break;
				
				case Yellow:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.yellow_left);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayName.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayScore.setTextColor(context.getResources().getColor(R.color.Yellow));
					displayGames.setTextColor(context.getResources().getColor(R.color.Yellow));
				break;	
				
				case Gray:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.gray_left);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.Black));
					displayName.setTextColor(context.getResources().getColor(R.color.Black));
					displayScore.setTextColor(context.getResources().getColor(R.color.Black));
					displayGames.setTextColor(context.getResources().getColor(R.color.Black));
				break;	
				
				case Purple:
					aboveButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0 ,R.drawable.purple_left);	
					aboveButton.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayName.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayScore.setTextColor(context.getResources().getColor(R.color.BlueViolet));
					displayGames.setTextColor(context.getResources().getColor(R.color.BlueViolet));
				break;				
			}
		}
	}

	public void setTextColour(TextView tv)
	{
		switch(player.getColour())
		{
		case Red:
			tv.setTextColor(context.getResources().getColor(R.color.Red));
		break;
		
		case Blue:
			tv.setTextColor(context.getResources().getColor(R.color.Navy));
		break;
		
		case Black:
			tv.setTextColor(context.getResources().getColor(R.color.Black));
		break;
		
		case White:
			tv.setTextColor(context.getResources().getColor(R.color.Black));
		break;
		
		case Green:
			tv.setTextColor(context.getResources().getColor(R.color.Green));
		break;
		
		case Orange:
			tv.setTextColor(context.getResources().getColor(R.color.OrangeRed));
		break;
		
		case Pink:
			tv.setTextColor(context.getResources().getColor(R.color.Magenta));
		break;
		
		case Yellow:
			tv.setTextColor(context.getResources().getColor(R.color.Yellow));
			break;
			
		case Gray:
			tv.setTextColor(context.getResources().getColor(R.color.Black));
			break;
			
		case Purple:
			tv.setTextColor(context.getResources().getColor(R.color.BlueViolet));
			break;
		}
	}
}
