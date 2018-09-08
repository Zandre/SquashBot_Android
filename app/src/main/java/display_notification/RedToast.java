package display_notification;

import com.squashbot.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RedToast 
{

	Context context;
	String message;
	boolean displayLongToast, displayAtTop;
	LayoutInflater inflater;
	View layout;
	Toast toast;
	TextView tv;
	
	public RedToast(Context context)
	{
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.red_toast, null, false);
		tv = (TextView) layout.findViewById(R.id.tv_redtoast);
		toast = new Toast(context);
	}
	
	public void MakeRedToast(Context context, String message, boolean displayLongToast, boolean displayAtTop)
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
	
	public void NoResultsSelected()
	{
		tv.setText("No results selected");
		toast.setView(layout);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void NoNetworkConnection()
	{
		tv.setText("No network connection available");
		toast.setView(layout);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}
	
	
}
