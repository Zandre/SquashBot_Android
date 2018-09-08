package database;

import fragments.ResultsFragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import classes.Player;
import classes.Result;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class IndividualResults_Helper extends SQLiteOpenHelper 
{

    private static String DB_PATH = "/data/data/com.squashbot/databases/";
    private static String DB_NAME = "squashbot.db";
    private SQLiteDatabase database; 
    private final Context myContext;
 
    public IndividualResults_Helper(Context context) 
    {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
    public void InsertMatchResult(Player winner, Player loser, long duration, String venue)
    {
    	ContentValues element = new ContentValues();
    	database = getWritableDatabase();
    	
    	//SCORES
    	String score = "";
    	for(int x = 0; x < winner.getScores().size(); x++)
    	{
    		score = score+winner.getScores().get(x)+"-"+loser.getScores().get(x)+",\t";
    	}
    	
    	//DATE
    	String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    	
    	//MATCH DURATION
    	int Duration = Math.round((int)duration/1000);
    	
    	element.put("winner", winner.getName());
    	element.put("loser", loser.getName());
    	element.put("scores", score);
    	element.put("date", date);
    	element.put("duration", Duration);
    	element.put("venue", venue);
    	element.put("winner_sets", winner.getGameTotal());
    	element.put("loser_sets", loser.getGameTotal());
    	
    	database.insert("Results", null, element);
    }
    
    public void RemoveResults(ArrayList<Result> results)
    {
    	database = getWritableDatabase();
    	
    	for(int x = 0; x < results.size(); x++)
    	{
    		database.execSQL("DELETE FROM Results WHERE id = '"+results.get(x).getID()+"'");
    	}
    }
    
    public ArrayList<Result> getResults()
    {
    	database = getReadableDatabase();
    	String SQL = "SELECT * FROM Results ORDER BY date DESC";
    	Cursor cursor = database.rawQuery(SQL, null);
    	ArrayList<Result> results = new ArrayList<Result>();
    	
    	while(cursor.moveToNext())
    	{
    		String winner = cursor.getString(cursor.getColumnIndex("winner"));
    		String loser = cursor.getString(cursor.getColumnIndex("loser"));
    		String scores = cursor.getString(cursor.getColumnIndex("scores"));
    		String date = cursor.getString(cursor.getColumnIndex("date"));
    		String venue = cursor.getString(cursor.getColumnIndex("venue"));
    		
    		int winner_sets = cursor.getInt(cursor.getColumnIndex("winner_sets"));
    		int loser_sets = cursor.getInt(cursor.getColumnIndex("loser_sets"));
    		String result = winner_sets+"-"+loser_sets;
    		int id = cursor.getInt(cursor.getColumnIndex("id"));
    		
    		results.add(new Result(winner, loser, scores, date, venue, result, id));
    	}
    	cursor.close();
    	return results;
    }
    
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

