package display_notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ErrorDialog
{
	Activity activity;

	public ErrorDialog(Activity activity) 
	{

		this.activity = activity;
	}
	
	public void makeErrorDialog(String message)
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(activity);
		popup.setTitle("ERROR");
		popup.setMessage(message);
		popup.setNeutralButton("OK", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{

			}
		}) ;
		popup.show();
	}
	
	public ErrorDialog NoNetworkConnection()
	{
		AlertDialog.Builder popup = new AlertDialog.Builder(activity);
		popup.setTitle("ERROR");
		popup.setMessage("No network connection available");
		popup.setNeutralButton("OK", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{

			}
		}) ;
		popup.show();
		return this;
	}
	
}


