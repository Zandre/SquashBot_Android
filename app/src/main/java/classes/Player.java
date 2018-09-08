package classes;

import java.util.ArrayList;

public class Player
{
	boolean winner;
	String name;
	int score, game_total;
	Player player;
	ArrayList<Integer> scores = new ArrayList<Integer>();
	PlayerState state;
	Side side;
	AvatarColour colour;
	
	public Player(String name, int score, int game_total, AvatarColour colour) 
	{
		this.name = name;
		this.score = score;
		this.game_total = game_total;
		this.colour = colour;
	}
	
	public Player(Player anotherPlayer)
	{
		this.player = anotherPlayer.player;
	}
	
	public PlayerState isServer()
	{
		//true == server
		//false == receiver
		return state;
	}
	public Side getSide()
	{
		//true == right
		//false == left
		return side;
	}
	public AvatarColour getColour()
	{
		return colour;
	}
	public String getName()
	{
		return name;
	}
	public int getScore()
	{
		return score;
	}
	public int getGameTotal()
	{
		return game_total;
	}
	public ArrayList<Integer> getScores()
	{
		return scores;
	}

	public void setServer(PlayerState State)
	{
		state = State;
	}
	public void setSide(Side Side)
	{
		side = Side;
	}
	public void setColour(AvatarColour Colour)
	{
		colour = Colour;
	}
	public void setName(String Name)
	{
		name = Name;
	}
	public void setScore(int Score)
	{
		score = Score;
	}
	public void setGameTotal(int GameTotal)
	{
		game_total = GameTotal;
	}
	public void setScores(ArrayList<Integer> Scores)
	{
		scores = Scores;
	}
	public void addScore(int Score)
	{
		scores.add(Integer.valueOf(Score));
	}

	public enum PlayerState
	{
		isServing,
		isReceiving;
	}
	
	public enum Side
	{
		LEFT,
		RIGHT;
	}
	
	public enum AvatarColour
	{
		Red,
		Blue,
		Black,
		White,
		Green,
		Orange,
		Pink,
		Yellow,
		Gray,
		Purple;
	}
}
