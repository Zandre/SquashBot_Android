package adapters;

import java.util.ArrayList;

import tournament_classes.Contact;

import com.squashbot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactAdapter  extends ArrayAdapter<Contact>
{

	  private final Context context;
	  ArrayList<Contact> contacts;

	
	public ContactAdapter(Context context, ArrayList<Contact> contacts) 
	{
	    super(context, R.layout.contact, contacts);
	    this.context = context;
	    this.contacts = contacts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.contact, parent, false);
	    

	    TextView name = (TextView)rowView.findViewById(R.id.tv_contactName);
	    TextView number = (TextView)rowView.findViewById(R.id.tv_contactNumber);
	    TextView role = (TextView)rowView.findViewById(R.id.tv_contactRole);
	    
	    name.setText(contacts.get(position).Name());
	    number.setText(contacts.get(position).Number());
	    
	    switch (contacts.get(position).ContactType())
	    {
		case TournamentOrganizer:
			role.setText(contacts.get(position).Role());
			break;
			
		case MensCaptain:
			role.setText(contacts.get(position).Section() + " Section, "+contacts.get(position).Team());
			break;
			
		case WomensCaptain:
			role.setText(contacts.get(position).Section() + " Section, "+contacts.get(position).Team());
			break;

		default:
			break;
		}

    
		return rowView;
	}
	
}
