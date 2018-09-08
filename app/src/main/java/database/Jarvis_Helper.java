package database;

import fragments.ResultsFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import tournament_classes.Contact;
import tournament_classes.Event;
import tournament_classes.Fixture;
import tournament_classes.Log;
import tournament_classes.TeamResult;
import tournament_classes.Venue;
import classes.Player;
import classes.Result;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Jarvis_Helper extends SQLiteOpenHelper 
{

    private static String DB_PATH = "/data/data/com.squashbot/databases/";
    private static String DB_NAME = "squashbot.db";
    private SQLiteDatabase database; 
    private final Context myContext;
 
    public Jarvis_Helper(Context context) 
    {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Generic Methods
    public boolean MustUpdateThisTable(String column)
    {//generic method to see if certain table must update
    	boolean mustUpdate = true;
    	
    	database = getReadableDatabase();
    	
    	String SQL = "SELECT * FROM SyncData "
    				+ "WHERE Id ='1'";
    	Cursor cursor = database.rawQuery(SQL, null);
    	
    	while(cursor.moveToNext())
    	{
    		int update = cursor.getInt(cursor.getColumnIndex(column));
    		if(update == 1)
    		{
    			mustUpdate = false;
    		}	
    	}
    	return mustUpdate;
    }
    
    public void TableIsUpdated(String column)
    {
    	database = getWritableDatabase();
    	database.execSQL("UPDATE SyncData SET fixtures = '1' WHERE Id = '1'");
    	
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Contacts 
    public ArrayList<Contact> getCourtMarshalls()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisContacts"
    			+ " WHERE Type = 'Court Marshall'"
    			+ " ORDER BY Name ASC";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Contact> contacts = new ArrayList<Contact>();
    	
    	while(cursor.moveToNext())
    	{
    		String type = cursor.getString(cursor.getColumnIndex("Type"));
    		String number = cursor.getString(cursor.getColumnIndex("Number"));
    		String name = cursor.getString(cursor.getColumnIndex("Name"));
    		String courts = cursor.getString(cursor.getColumnIndex("Courts"));
    		
    		//contacts.add(new Contact(type, number, name, courts));
    	}
    	cursor.close();
    	return contacts;
    }
    
    public ArrayList<Contact> getTournamentOrganizers()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisContacts"
    			+ " WHERE Type = 'TO'"
    			+ " ORDER BY Name ASC";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Contact> contacts = new ArrayList<Contact>();
    	
    	while(cursor.moveToNext())
    	{
    		String type = cursor.getString(cursor.getColumnIndex("Type"));
    		String number = cursor.getString(cursor.getColumnIndex("Number"));
    		String name = cursor.getString(cursor.getColumnIndex("Name"));
    		
    		//contacts.add(new Contact(type, number, name));
    	}
    	cursor.close();
    	return contacts;
    }
    
    public ArrayList<Contact> getCaptains(String Gender)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisContacts"
    			+ " WHERE Type = 'Captain' AND Gender = '"+Gender+"'"
    			+ " ORDER BY Section ASC";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Contact> contacts = new ArrayList<Contact>();
    	
    	while(cursor.moveToNext())
    	{
    		String type = cursor.getString(cursor.getColumnIndex("Type"));
    		String number = cursor.getString(cursor.getColumnIndex("Number"));
    		String name = cursor.getString(cursor.getColumnIndex("Name"));
    		String gender = cursor.getString(cursor.getColumnIndex("Gender"));
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
    		String team = cursor.getString(cursor.getColumnIndex("Team"));
    		
    		//contacts.add(new Contact(type, number, name, section, gender, team));
    	}
    	cursor.close();
    	return contacts;
    }
   
    public void SyncContacts(ArrayList<Contact> contacts)
    {
    	database = getWritableDatabase();
    	database.execSQL("DELETE FROM jarvisContacts");
    	
    	for(int x = 0; x < contacts.size(); x++)
    	{
			ContentValues element = new ContentValues();
    		
    		int id = x;
    		//String type = contacts.get(x).getType();
    		String number = contacts.get(x).Number();
    		String name = contacts.get(x).Name();
    		
    		element.put("Id", id);
    		//element.put("Type", type);
    		element.put("Number", number);
    		element.put("Name", name);
    		
    		//if(type.equals("Captain"))
    		{
    			String section = contacts.get(x).Section();
    			String gender = contacts.get(x).Gender();
    			String team =  contacts.get(x).Team();
    			
        		element.put("Gender", gender);
        		element.put("Section", section);
        		element.put("Team", team);
    		}
    		//else if(type.equals("Court Marshall"))
    		{
    			//String courts = contacts.get(x).getCourts();
    			
    			//element.put("Courts", courts);
    		}
    		database.insert("jarvisContacts", null, element);
    	}
    	System.out.println("SYNCHRONIZED -> jarvis contacts");
    }
	
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Venue
    public ArrayList<Venue> getVenues()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM Venues";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Venue> venues = new ArrayList<Venue>();
    	
    	while(cursor.moveToNext())
    	{
    		String name = cursor.getString(cursor.getColumnIndex("Name"));
    		String address = cursor.getString(cursor.getColumnIndex("Address"));
    		String gps = cursor.getString(cursor.getColumnIndex("GPS"));
    		
    		//venues.add(new Venue(name, address, gps));
    	}
    	cursor.close();
    	return venues;
    }
    
    public void SyncVenues(ArrayList<Venue> venues)
    {
    	database = getWritableDatabase();
    	database.execSQL("DELETE FROM Venues");
    	
    	for(int x = 0; x < venues.size(); x++)
    	{
    		String name = venues.get(x).getVenueName();
    		String address = venues.get(x).getAddress();
    		//String gps = venues.get(x).getGPS();
    		
       		ContentValues element = new ContentValues();
       		element.put("Name", name);
       		element.put("Address", address);
       		//element.put("GPS", gps);
       		
       		database.insert("Venues", null, element);
    	}
    	System.out.println("SYNCHRONIZED -> jarvis venues");
    }
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Functions
    public ArrayList<Event> getFuncions()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM Functions";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Event> functions = new ArrayList<Event>();
    	
    	while(cursor.moveToNext())
    	{
    		String date = cursor.getString(cursor.getColumnIndex("Date"));
    		String time = cursor.getString(cursor.getColumnIndex("Time"));
    		String name = cursor.getString(cursor.getColumnIndex("Name"));
    		String venue = cursor.getString(cursor.getColumnIndex("Venue"));
    		String text = cursor.getString(cursor.getColumnIndex("Text"));
    		
    		//functions.add(new Event(date, time, name, text, venue));
    	}
    	cursor.close();
    	return functions;
    }
    
    public void SyncFunctions(ArrayList<Event> functions)
    {
    	database = getWritableDatabase();
    	database.execSQL("DELETE FROM Functions");
    	
    	for(int x = 0; x < functions.size(); x++)
    	{
    		String date = functions.get(x).FriendlyDate();
    		String time = functions.get(x).FriendlyTime();
    		String name = functions.get(x).EventName();
    		String text = functions.get(x).EventDescription();
    		String venue = functions.get(x).VenueName();
    		
    		ContentValues element = new ContentValues();
    		element.put("Date", date);
    		element.put("Time", time);
    		element.put("Name", name);
    		element.put("Text", text);
    		element.put("Venue", venue);
    		
    		database.insert("Functions", null, element);
    	}
    	System.out.println("SYNCHRONIZED -> jarvis functions");
    }
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Section
    public ArrayList<String> getDistinctSection(String Gender)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT DISTINCT Section FROM jarvisLog WHERE Gender = '"+Gender+"'"
    				+ " ORDER BY Section asc";
    	
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<String> sections = new ArrayList<String>();
    	
    	while(cursor.moveToNext())
    	{
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
        	sections.add(section);
    	}
    	cursor.close();
    	return sections;
    }
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Logs
    public ArrayList<Log> getLog(String Gender, String Section)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisLog"
    			+ " WHERE Gender = '"+Gender+"' AND Section = '"+Section+"'"
    			+ " ORDER BY Pool ASC, LogPoints DESC";
    	
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Log> log = new ArrayList<Log>();
    	
    	while(cursor.moveToNext())
    	{
    		String gender = cursor.getString(cursor.getColumnIndex("Gender"));
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
			String pool = cursor.getString(cursor.getColumnIndex("Pool"));
    		String team = cursor.getString(cursor.getColumnIndex("Team"));
    		int log_points = cursor.getInt(cursor.getColumnIndex("LogPoints"));
    		int played = cursor.getInt(cursor.getColumnIndex("Played"));
    		int won = cursor.getInt(cursor.getColumnIndex("Won"));
    		int lost = cursor.getInt(cursor.getColumnIndex("Lost"));
    		
    		//log.add(new Log(team, gender, section, pool, log_points, played, won, lost));
    	}
    	cursor.close();
    	return log;
    }
    
    public void SyncLogs(ArrayList<Log> logs)
    {
    	database = getWritableDatabase();
    	database.execSQL("DELETE FROM jarvisLog");
    	
    	for(int x = 0; x < logs.size(); x++)
    	{
    		int id = x;
    		//String team = logs.get(x).getTeam();
    		String gender = logs.get(x).Gender();
    		String section = logs.get(x).Section();
			String pool = logs.get(x).Pool();
    		int logPoints = logs.get(x).LogPoints();
    		int gamesPlayed = logs.get(x).getGamesPlayed();
    		int gamesWon = logs.get(x).getGamesWon();
    		int gamesLost = logs.get(x).getGamesLost();
    		
       		ContentValues element = new ContentValues();
    		element.put("Gender", gender);
    		element.put("Section", section);
			element.put("Pool", pool);
    		//element.put("Team", team);
    		element.put("LogPoints", logPoints);
    		element.put("Played", gamesPlayed);
    		element.put("Won", gamesWon);
    		element.put("Lost", gamesLost);
    		
    		database.insert("jarvisLog", null, element);
    	}
    	System.out.println("SYNCHRONIZED -> jarvis logs");
    }
  
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Fixtures
    public ArrayList<Fixture> getFixtures(String Gender, String Section)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisFixtures"
    			+ " WHERE Gender = '"+Gender+"' AND Section = '"+Section+"'"
    			+ " ORDER BY Date asc";
    	
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
    	
    	while(cursor.moveToNext())
    	{
    		String gender = cursor.getString(cursor.getColumnIndex("Gender"));
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
    		String team1 = cursor.getString(cursor.getColumnIndex("Team1"));
    		String team2 = cursor.getString(cursor.getColumnIndex("Team2"));
    		String date = cursor.getString(cursor.getColumnIndex("Date"));
    		String time = cursor.getString(cursor.getColumnIndex("Time"));
    		String venue = cursor.getString(cursor.getColumnIndex("Venue"));
    		String court = cursor.getString(cursor.getColumnIndex("Court"));
    		
    		//fixtures.add(new Fixture(gender, section, team1, team2, date, time, venue, court));
    	}
    	cursor.close();
    	return fixtures;
    }
    
    public void SyncFixtures(ArrayList<Fixture> fixtures)
    {
    	database = getWritableDatabase();
    	database.execSQL("DELETE FROM jarvisFixtures");
    	
    	for(int x = 0; x < fixtures.size(); x++)
    	{
    		int id = x;
    		String gender = fixtures.get(x).Gender();
    		String section = fixtures.get(x).Section();
    		String team1 = fixtures.get(x).TeamA_Name();
    		String team2 = fixtures.get(x).TeamB_Name();
    		String date = fixtures.get(x).FriendlyDate();
    		String time = fixtures.get(x).FriendlyTime();
    		String venue = fixtures.get(x).VenueName();
    		String court = fixtures.get(x).CourtNumber();
    		
    		ContentValues element = new ContentValues();
    		element.put("Id", id);
    		element.put("Gender", gender);
    		element.put("Section", section);
    		element.put("Team1", team1);
    		element.put("Team2", team2);
    		element.put("Date", date);
    		element.put("Time", time);
    		element.put("Venue", venue);
    		element.put("Court", court);
    		
    		database.insert("jarvisFixtures", null, element);
    	}
    	
    	System.out.println("SYNCHRONIZED -> jarvis fixtures");
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Team Results
    public ArrayList<TeamResult> getTeamResults(String Gender, String Section)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM jarvisTeamResults"
    			+ " WHERE Gender = '"+Gender+"' AND Section = '"+Section+"'"
				+ " ORDER BY Date desc, Time desc";
    	
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<TeamResult> results = new ArrayList<TeamResult>();
    	
    	while(cursor.moveToNext())
    	{
    		String gender = cursor.getString(cursor.getColumnIndex("Gender"));
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
    		String Wteam = cursor.getString(cursor.getColumnIndex("Winning_Team"));
    		String Lteam = cursor.getString(cursor.getColumnIndex("Losing_Team"));
    		String date = cursor.getString(cursor.getColumnIndex("Date"));
    		String time = cursor.getString(cursor.getColumnIndex("Time"));
    		String venue = cursor.getString(cursor.getColumnIndex("Venue"));
    		String court = cursor.getString(cursor.getColumnIndex("Court"));
    		String text = cursor.getString(cursor.getColumnIndex("Text"));
    		int sets_won = cursor.getInt(cursor.getColumnIndex("Sets_Won"));
    		int sets_lost = cursor.getInt(cursor.getColumnIndex("Sets_Lost"));
    		int winnerScore = cursor.getInt(cursor.getColumnIndex("WinnerScore"));
    		int loserScore = cursor.getInt(cursor.getColumnIndex("LoserScore"));
    		
    		//results.add(new TeamResult(gender, section, Wteam, Lteam, date, time, venue, court, text, sets_won, sets_lost, winnerScore, loserScore));
    	}
    	cursor.close();
    	return results;
    }

    public void InsertTeamResult(TeamResult results)
    {
    	ContentValues element = new ContentValues();
    	database = getWritableDatabase();
    	
    	element.put("Gender", results.Gender());
    	element.put("Section", results.Section());
    	element.put("Winning_Team", results.getWinningTeam());
    	element.put("Losing_Team", results.getLosingTeam());
    	element.put("Sets_Won", results.getGamesWon());
    	element.put("Sets_Lost", results.getGamesLost());
    	element.put("WinnerScore", results.getWinnerScore());
    	element.put("LoserScore", results.getLoserScore());
    	element.put("Venue", results.VenueName());
    	element.put("Court", results.CourtNumber());
    	element.put("Date", results.FriendlyDate());
    	element.put("Time", results.FriendlyTime());
    	//element.put("Text", results.getText());
    	
    	database.insert("jarvisTeamResults", null, element);
    }
    
	public void SyncTeamResults(ArrayList<TeamResult> results)
	{
		database = getWritableDatabase();
    	database.execSQL("DELETE FROM jarvisTeamResults");
		
		for(int x = 0; x < results.size(); x++)
    	{
			int id = x;
    		String gender = results.get(x).Gender();
    		String section = results.get(x).Section();
			String winning_team = results.get(x).getWinningTeam();
			String losing_team = results.get(x).getLosingTeam();
			String date = results.get(x).FriendlyDate();
			String time = results.get(x).FriendlyTime();
			String venue = results.get(x).VenueName();
			//String texts = results.get(x).getText();
			String court = results.get(x).CourtNumber();
			int sets_won = results.get(x).getGamesWon();
			int sets_lost = results.get(x).getGamesLost();
			int winner_score = results.get(x).getWinnerScore();
			int loser_score = results.get(x).getLoserScore();
			
			ContentValues element = new ContentValues();
			element.put("Id", id);
    		element.put("Gender", gender);
    		element.put("Section", section);
			element.put("Winning_Team", winning_team);
			element.put("Losing_Team", losing_team);
			element.put("Date", date);
    		element.put("Time", time);
			//element.put("Text", texts);
    		element.put("Venue", venue);
    		element.put("Court", court);
			element.put("Sets_Won", sets_won);
			element.put("Sets_Lost", sets_lost);
			element.put("WinnerScore", winner_score);
			element.put("LoserScore", loser_score);
			
			database.insert("jarvisTeamResults", null, element);
		}
		
		System.out.println("SYNCHRONIZED -> jarvis fixtures");
	}
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Enter Team Results - stuff for EnterNames Activity
    public ArrayList<String> getSectionsForGender(String Gender)
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT DISTINCT Section FROM jarvisFixtures WHERE Gender = '"+Gender+"' ORDER BY Section";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<String> sections = new ArrayList<String>();
    	
    	while(cursor.moveToNext())
    	{
    		String section = cursor.getString(cursor.getColumnIndex("Section"));
    		sections.add(section);
    	}
    	cursor.close();
    	return sections;
    }

    public ArrayList<String> getTeamsForSectionAndGender(String Gender, String Section)
    {//setup for TeamA spinner
    	database = getReadableDatabase();
    	String SQL = "SELECT DISTINCT Team1, Team2 FROM jarvisFixtures WHERE Gender = '"+Gender+"' AND Section = '"+Section+"'";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<String> teams = new ArrayList<String>();
    	
    	while(cursor.moveToNext())
    	{
    		String team1 = cursor.getString(cursor.getColumnIndex("Team1"));
    		String team2 = cursor.getString(cursor.getColumnIndex("Team2"));
    		if(!teams.contains(team1))
    		{
        		teams.add(team1);
    		}
    		
    		if(!teams.contains(team2))
    		{
        		teams.add(team2);
    		}		
    	}
    	cursor.close();
       	return teams;
    }
    
    public ArrayList<String> getTeamsForSectionAndGenderNotTeamA(String Gender, String Section, String TeamA)
    {//setup for TeamB spinner
    	database = getReadableDatabase();
    	String SQL = "SELECT DISTINCT Team1, Team2 FROM jarvisFixtures WHERE Gender = '"+Gender+"' AND Section = '"+Section+"'";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<String> teams = new ArrayList<String>();
    	
    	while(cursor.moveToNext())
    	{
    		String team1 = cursor.getString(cursor.getColumnIndex("Team1"));
    		String team2 = cursor.getString(cursor.getColumnIndex("Team2"));
    		
    		if(!team1.equals(TeamA) && !teams.contains(team1))
    		{
        		teams.add(team1);
    		}
    		
    		if(!team2.equals(TeamA) && !teams.contains(team2))
    		{
        		teams.add(team2);
    		}		
    	}
    	cursor.close();
       	return teams;
    }
    
    public ArrayList<String> getDistinctVenues()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT DISTINCT Venue FROM jarvisFixtures";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<String> venues = new ArrayList<String>();
    	
    	while(cursor.moveToNext())
    	{
    		String venue = cursor.getString(cursor.getColumnIndex("Venue"));
    		venues.add(venue);
    	}
    	cursor.close();
       	return venues;
    }
    
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Other stuff I don't use
    public void create() throws IOException
    {//Creates a empty database on the system and rewrites it with your own database

    	boolean dbExist = checkDataBase();
 
    	if(dbExist)
    	{
    		//do nothing - database already exist
    	}
    	else
    	{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try 
        	{
    			copy();
    		} 
        	catch (IOException e) 
        	{
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    private boolean checkDataBase()
    {// Check if the database already exist to avoid re-copying the file each time you open the application
    	//@return true if it exists, false if it doesn'
    	
    	SQLiteDatabase checkDB = null;
    	try
    	{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    	}
    	catch(SQLiteException e)
    	{
    		//database does't exist yet.
    	}
 
    	if(checkDB != null)
    	{
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }

    private void copy() throws IOException
    {
//         Copies your database from your local assets-folder to the just created empty database in the
//         system folder, from where it can be accessed and handled.
//         This is done by transfering bytestream.
         
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0)
    	{
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void open() throws SQLException
    {
        String myPath = DB_PATH + DB_NAME;
    	database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    @Override
	public synchronized void close() 
    {
    	    if(database != null)
    		    database.close();
 
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
 
	}
}
