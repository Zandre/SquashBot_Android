package ExternalApplications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCall 
{
	Context context;
	String number;
	public PhoneCall(Context context, String number) 
	{
		this.context = context;
		this.number  = number;
		Call();
	}
	
	private void Call()
	{
		String numberToCall = "tel:" + number.toString().trim();
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(numberToCall));
		context.startActivity(intent);
	}

}
