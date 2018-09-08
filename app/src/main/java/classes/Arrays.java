package classes;

import java.util.ArrayList;

public class Arrays 
{

	public Arrays() 
	{
	}

	public ArrayList<String> getVenues()
	{
		ArrayList<String> courts = new ArrayList<String>();
		
		String court = "PCC Squash Club";
		courts.add(court);
		court = "Puk Squash Courts";
		courts.add(court);
		court = "PMSC - Defence";
		courts.add(court);
		
		return courts;
	}
	
	public ArrayList<String> getAvatarColours()
	{
		ArrayList<String> colours = new ArrayList<String>();
		
		String colour = "Red";
		colours.add(colour);
		 	colour = "Blue";
		colours.add(colour);
		 	colour = "Black";
		colours.add(colour);
			colour = "White";
		colours.add(colour);
			colour = "Orange";
		colours.add(colour);
		 	colour = "Green";
		colours.add(colour);
		 	colour = "Pink";
		colours.add(colour);
			colour = "Yellow";
		colours.add(colour);
			colour = "Gray";
		colours.add(colour);
			colour = "Purple";
		colours.add(colour);

		return colours;
	}
	
	public ArrayList<String> getTimeSlots()
	{
		ArrayList<String> times = new ArrayList<String>();
		
		String time = "09:00:00";
		times.add(time);
		 time = "10:00:00";
		times.add(time);
		 time = "11:00:00";
		times.add(time);
		 time = "12:00:00";
		times.add(time);
		 time = "13:00:00";
		times.add(time);
		 time = "14:00:00";
		times.add(time);
		 time = "15:00:00";
		times.add(time);
		 time = "16:00:00";
		times.add(time);
		
		return times;
	}
	
	public ArrayList<String> getSections()
	{
		ArrayList<String> sections = new ArrayList<String>();
		
		String section = "A";
		sections.add(section);
		 section = "B";
		sections.add(section);
		 section = "C";
		sections.add(section);
		 section = "D";
		sections.add(section);
		
		return sections;
	}

	public ArrayList<Integer> getCourts()
	{
		ArrayList<Integer> courts = new ArrayList<Integer>();
		
		int court = 1;
		courts.add(court);
		 court = 2;
		courts.add(court);
		 court = 3;
		courts.add(court);
		 court = 4;
		courts.add(court);
		 court = 5;
		courts.add(court);
		 court = 6;
		courts.add(court);
		
		return courts;
	}
}
