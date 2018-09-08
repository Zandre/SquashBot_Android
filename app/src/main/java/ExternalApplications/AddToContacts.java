package ExternalApplications;

import java.util.ArrayList;

import display_notification.BlueToast;
import display_notification.RedToast;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;

public class AddToContacts
{
	Context context;
	String number, name, label;
	
	public AddToContacts(Context context, String number, String name, String label) 
	{
		this.context = context;
		this.number = number;
		this.name = name;
		this.label = label;
		_AddToContacts();
	}
	
	private void _AddToContacts()
	{
		 String DisplayName = name;
		 String MobileNumber = number.trim();
		 String Label = label;

		 ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

		 ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());

		 //------------------------------------------------------ Names
		 if (DisplayName != null) 
		 {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, DisplayName)
		         .build());
		 }

		 //------------------------------------------------------ Mobile Number                     
		 if (MobileNumber != null)
		 {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
		         .build());
		 }
		 //------------------------------------------------------ Organization
		 if (!Label.equals(""))
		 {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Note.NOTE, Label)
		         .build());
		 }


		 // Asking the Contact provider to create a new contact                 
		 try
		 {
		     context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		     
		     BlueToast toast = new BlueToast(context);
		     toast.MakeBlueToast(context, name + " was successfully added to your personal contacts", false, false);
		 } 
		 catch (Exception e) 
		 {
		     e.printStackTrace();
		     RedToast toast = new RedToast(context);
		     toast.MakeRedToast(context, "Failed to add "+name, false, false);
		 } 
	}

}
