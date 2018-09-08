package ExternalApplications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SMS 
{
	Context context;
	String number;
	
	public SMS(Context context, String number) 
	{
		this.context = context;
		this.number = number;
		SendSMS();
	}
	
	private void SendSMS()
	{
		String numberToSMS = "smsto:"+number.toString().trim();
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(numberToSMS));
		intent.putExtra("compose_mode", true);
		context.startActivity(intent);
	}

}
