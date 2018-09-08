package display_notification;

import com.squashbot.R;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

public class BlueToast
{

Context context;
String message;
boolean displayLongToast, displayAtTop;
LayoutInflater inflater;
View layout;
Toast toast;
TextView tv;


public BlueToast(Context context)
{
	this.context = context;
	inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	layout = inflater.inflate(R.layout.blue_toast, null, false);
	tv = (TextView) layout.findViewById(R.id.tv_toast);
	toast = new Toast(context);
}

public void MakeBlueToast(Context context, String message, boolean displayLongToast, boolean displayAtTop)
{
	if(displayLongToast)
	{
		toast.setDuration(Toast.LENGTH_LONG);
	}
	else
	{
		toast.setDuration(Toast.LENGTH_SHORT);
	}
	
	if(displayAtTop)
	{
		toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
	}
	tv.setText(message);
	toast.setView(layout);
	toast.show();
}

public void MatchBall()
{
	tv.setText("MATCH BALL");
	toast.setView(layout);
	toast.setDuration(Toast.LENGTH_SHORT);
	toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
	toast.show();
}

public void GameBall()
{
	tv.setText("GAME BALL");
	toast.setView(layout);
	toast.setDuration(Toast.LENGTH_SHORT);
	toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
	toast.show();
}
}