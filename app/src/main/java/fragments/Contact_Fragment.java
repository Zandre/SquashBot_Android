package fragments;

import java.util.ArrayList;

import com.squashbot.R;

import tournament_activities.TournamentDashboard;
import tournament_classes.Contact;
import database.Jarvis_Helper;
import ExternalApplications.AddToContacts;
import ExternalApplications.GoogleMaps;
import ExternalApplications.PhoneCall;
import ExternalApplications.SMS;
import adapters.ContactAdapter;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;

@SuppressLint("ValidFragment")
public class Contact_Fragment extends ListFragment
{
	
	ArrayList<Contact> contacts;
	

	public Contact_Fragment(ArrayList<Contact> contacts)
	{
		this.contacts = contacts;
	}
	
	public Contact_Fragment()
	{
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		registerForContextMenu(getListView());
		
		getListView().setDividerHeight(0);

		
		ContactAdapter adapter = new ContactAdapter(getActivity(), contacts);
		setListAdapter(adapter);
	}
	
	@Override 
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;
		Contact contact = contacts.get(selectedItem.position);
		menu.setHeaderTitle(contact.Name());

	    MenuInflater inflater = this.getActivity().getMenuInflater();
	    inflater.inflate(R.menu.contextualmenu_contact, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		android.widget.AdapterView.AdapterContextMenuInfo selectedItem = (android.widget.AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int position = selectedItem.position;
		
		switch(item.getItemId())
		{
			case R.id.contact_AddToContacts:
				
				Contact contact = contacts.get(position);
				switch(contact.ContactType())
				{
					case TournamentOrganizer:
							AddToContacts newContact = new AddToContacts(getActivity(), contact.Number(), contact.Name(), contact.Role());
							break;
							
					case MensCaptain:
					case WomensCaptain:
						String role = contact.Section() + " " + TournamentDashboard.selectedTournament.NamingConvention()+ ", "+contact.Team();
						AddToContacts _newContact = new AddToContacts(getActivity(), contact.Number(), contact.Name(), role);
				}

				break;
				
			case R.id.contact_Message:
				SMS sms = new SMS(getActivity(), contacts.get(position).Number());
				break;
				
			case R.id.contact_Call:
				PhoneCall call = new PhoneCall(getActivity(), contacts.get(position).Number());
				break;
		}
		return false;
	}
}
