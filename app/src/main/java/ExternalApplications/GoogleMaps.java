package ExternalApplications;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GoogleMaps 
{
	String address, venueName;
	Context context;
	public GoogleMaps(Context context, String address, String venueName) 
	{
		this.context = context;
		this.address = address;
		this.venueName = venueName;
		OpenMap();
	}
	
	private void OpenMap()
	{
		String location = String.format(Locale.ENGLISH, "geo:0,0?q=%s(%s)", address, venueName);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
		context.startActivity(intent);
	}
}
