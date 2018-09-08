package classes;

public class PressResult
{
	String date, gender, section, result, text, textWithScores, tournament;
	
	public PressResult(String date, String gender, String section, String result, String text, String textWithScores, String tournament)
	{
		this.date = date;
		this.gender = gender;
		this.section = section;
		this.result = result;
		this.text = text;
		this.textWithScores = textWithScores;
		this.tournament = tournament;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getSection()
	{
		return section;
	}
	
	public String getGender()
	{
		return gender;
	}
	
	public String getResult()
	{
		return result;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getTextWithScores()
	{
		return textWithScores;
	}
	
	public String getTournament()
	{
		return tournament;
	}
}

