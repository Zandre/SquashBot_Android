package asynchronious_classes;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import classes.LoadingCircle;
import tournament_activities.TournamentDashboard;
import tournament_classes.Contact;
import tournament_classes.Event;
import tournament_classes.InformationBoard;
import tournament_classes.Tournament;
import tournament_classes.Venue;

/**
 * Created by CisForCookie on 2016/01/13.
 */
public class getInfo extends AsyncTask<String, String, String>
{
    Context context;
    int tournamentId;
    LoadingCircle loading;
    ArrayList<InformationBoard> messages = new ArrayList<InformationBoard>();
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Venue> venues = new ArrayList<Venue>();
    ArrayList<Contact> menCaptains = new ArrayList<Contact>();
    ArrayList<Contact> womenCaptains = new ArrayList<Contact>();
    ArrayList<Contact> tournamentOrganizers = new ArrayList<Contact>();

    public getInfo(Context context, int tournamentId)
    {
        this.context = context;
        this.tournamentId = tournamentId;
        MakeLoadingCircle();
    }

    private void MakeLoadingCircle()
    {
        loading = new LoadingCircle(context, "SquashBot Tournament Info", "Getting tournament information...");
        loading.ShowLoadingCircle();
    }

    @Override
    protected String doInBackground(String... strings)
    {
        getInformation();
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        TournamentDashboard.venues = venues;
        TournamentDashboard.events = events;
        TournamentDashboard.menCaptains = menCaptains;
        TournamentDashboard.womenCaptains = womenCaptains;
        TournamentDashboard.tournamentOrganizers = tournamentOrganizers;
        TournamentDashboard.messages = messages;

        loading.HideLoadingCirlce();

        TournamentDashboard.GoToTournamentInfo();
    }

    private void getInformation()
    {
        String TAG_SUCCESS = "success";

        ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("TournamentId", Integer.toString(tournamentId)));

        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.makeHttpRequest("http://www.squashbot.co.za/Tournaments/getInfo.php", parameters);

        try
        {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1)
            {
                //VENUES

                JSONArray VenueArray = json.getJSONArray("Venues");
                for (int v = 0; v <= VenueArray.length() - 1; v++)
                {
                    JSONObject venueCursor = VenueArray.getJSONObject(v);

                    String _id = venueCursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = venueCursor.getString("TournamentId");
                    int tournamentid = Integer.parseInt(_tournamentId);

                    String _venue = venueCursor.getString("Venue");
                    String address = venueCursor.getString("Address");

                    Venue venue = new Venue(id, tournamentid, _venue, address);
                    venues.add(venue);
                }


                //EVENTS

                JSONArray EventArray = json.getJSONArray("Events");
                for (int x = 0; x <= EventArray.length() - 1; x++)
                {
                    JSONObject cursor = EventArray.getJSONObject(x);

                    String _id = cursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = cursor.getString("TournamentId");
                    int tournamentid = Integer.parseInt(_tournamentId);

                    String date = cursor.getString("Date");
                    String time = cursor.getString("Time");
                    String eventName = cursor.getString("Name");
                    String description = cursor.getString("Description");
                    String venue = cursor.getString("Venue");

                    Event event = new Event(id, tournamentid, date, time, eventName, description, venue);
                    events.add(event);
                }

                //MEN CAPTAINS
                JSONArray MensCapArray = json.getJSONArray("MenCaptains");
                for(int x = 0; x <= MensCapArray.length() - 1; x++)
                {
                    JSONObject cursor = MensCapArray.getJSONObject(x);

                    String _id = cursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = cursor.getString("TournamentId");
                    int tournamentid = Integer.parseInt(_tournamentId);

                    String name = cursor.getString("ContactName");
                    String number = cursor.getString("Number");

                    Contact.ContactType Type = Contact.ContactType.MensCaptain;
                    String gender = cursor.getString("Gender");
                    String section = cursor.getString("Section");
                    String team = cursor.getString("Team");
                    Contact contact = new Contact(id, tournamentid, Type, number, name, section, gender, team);

                    menCaptains.add(contact);
                }


                //WOMEN CAPTAINS
                JSONArray WomensCapArray = json.getJSONArray("WomenCaptains");
                for(int x = 0; x <= WomensCapArray.length() - 1; x++)
                {
                    JSONObject cursor = WomensCapArray.getJSONObject(x);

                    String _id = cursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = cursor.getString("TournamentId");
                    int tournamentid = Integer.parseInt(_tournamentId);

                    String name = cursor.getString("ContactName");
                    String number = cursor.getString("Number");

                    Contact.ContactType Type = Contact.ContactType.WomensCaptain;
                    String gender = cursor.getString("Gender");
                    String section = cursor.getString("Section");
                    String team = cursor.getString("Team");
                    Contact contact = new Contact(id, tournamentid, Type, number, name, section, gender, team);

                    womenCaptains.add(contact);
                }


                //Tournament Organizers

                JSONArray TOArray = json.getJSONArray("TournamentOrganizers");
                for(int x = 0; x <= TOArray.length() - 1; x++)
                {
                    JSONObject cursor = TOArray.getJSONObject(x);

                    String _id = cursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = cursor.getString("TournamentId");
                    int tournamentid = Integer.parseInt(_tournamentId);

                    String name = cursor.getString("ContactName");
                    String number = cursor.getString("Number");
                    String role = cursor.getString("Role");
                    Contact.ContactType Type = Contact.ContactType.TournamentOrganizer;

                    Contact contact = new Contact(id, tournamentid, Type, number, name, role);

                    tournamentOrganizers.add(contact);
                }


                //INFORMATION BOARD

                JSONArray InfoArray = json.getJSONArray("InformationBoard");
                for(int x = 0; x <= InfoArray.length() - 1; x++)
                {
                    JSONObject cursor = InfoArray.getJSONObject(x);

                    String _id = cursor.getString("Id");
                    int id = Integer.parseInt(_id);
                    String _tournamentId = cursor.getString("TournamentId");
                    int __tournamentid = Integer.parseInt(_tournamentId);

                    String title = cursor.getString("Title");
                    String _message = cursor.getString("Message");
                    String author = cursor.getString("Author");
                    String date = cursor.getString("Date");

                    System.out.println("Date: "+date);


                    InformationBoard message = new InformationBoard(id, __tournamentid, title, _message, author, date);
                    messages.add(message);
                }

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    }

