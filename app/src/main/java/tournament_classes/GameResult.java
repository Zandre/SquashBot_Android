package tournament_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 08 Feb 2017.
 */

public class GameResult implements Parcelable
{
    private int playerAPoints, playerBPoints, ordinal;
    public GameResult(int playerAPoints, int playerBPoints, int ordinal)
    {
        this.playerAPoints = playerAPoints;
        this.playerBPoints = playerBPoints;
        this.ordinal = ordinal;
    }

    public int PlayerAPoints()
    {
        return playerAPoints;
    }

    public int PlayerBPoints()
    {
        return playerBPoints;
    }

    public int Ordinal()
    {
        return ordinal;
    }




    //Parcel Class
    public GameResult(Parcel in)
    {
        this.playerAPoints = in.readInt();
        this.playerBPoints = in.readInt();
        this.ordinal = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(playerAPoints);
        parcel.writeInt(playerBPoints);
        parcel.writeInt(ordinal);
    }

    public static final Parcelable.Creator<GameResult> CREATOR = new Parcelable.Creator<GameResult>()
    {
        @Override
        public GameResult createFromParcel(Parcel source)
        {
            return new GameResult(source);
        }

        @Override
        public GameResult[] newArray(int size) {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
