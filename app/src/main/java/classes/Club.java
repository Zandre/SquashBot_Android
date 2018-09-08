package classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Club implements Parcelable
{
	int id;
	String name, phpLink, tableName;
	boolean useSquashBotRanker;
	
	public Club(int id, String name, String phpLink, boolean useSquashBotRanker, String tableName) 
	{
		this.id = id;
		this.name = name;
		this.phpLink = phpLink;
		this.useSquashBotRanker = useSquashBotRanker;
		this.tableName = tableName;
	}
	
	public Club(Parcel in) 
	{
		this.id = in.readInt();
		this.name = in.readString();
		this.phpLink = in.readString();
		this.useSquashBotRanker = in.readByte() != 0;
		this.tableName = in.readString();
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLink()
	{
		return phpLink;
	}

	public boolean getUseSquashBotRanker()
	{
		return useSquashBotRanker;
	}
	
	public String getTableName()
	{
		return tableName;
	}
	
	//Parcel Class
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(phpLink);
		dest.writeByte((byte)(useSquashBotRanker ? 1 :0));
		dest.writeString(tableName);
	}

	public static final Parcelable.Creator<Club> CREATOR = new Parcelable.Creator<Club>()
	{
		@Override
		public Club createFromParcel(Parcel source)
		{
			return new Club(source);
		}

		@Override
		public Club[] newArray(int size) 
		{
			return null;
		}
	};
}
