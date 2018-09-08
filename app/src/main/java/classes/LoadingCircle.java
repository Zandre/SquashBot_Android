package classes;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingCircle 
{
	public static ProgressDialog spinner;
	Context context;
	String title, message;
	public LoadingCircle(Context context, String title, String message)
	{
		spinner = new ProgressDialog(context);
		spinner.setTitle(title);
		spinner.setMessage(message);
		spinner.setCancelable(false);
		spinner.setCanceledOnTouchOutside(false);
	}
	
	public void ShowLoadingCircle()
	{
		spinner.show();
	}
	
	public static void DismissLoadingCircle()
	{
		spinner.dismiss();
	}
	
	public void HideLoadingCirlce()
	{
		spinner.dismiss();
	}


}
