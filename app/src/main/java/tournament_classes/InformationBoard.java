package tournament_classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by CisForCookie on 2016/01/13.
 */
public class InformationBoard
{
    int  id, tournamentId;
    String title, message, author, date;
    Calendar calendar = Calendar.getInstance();

    public InformationBoard(int id, int tournamentId, String title, String message, String author, String date)
    {
        this.id = id;
        this.tournamentId = tournamentId;

        this.title = title;
        this.message = message;
        this.author = author;
        this.date = date;

        setCalendar();
    }


    public int ID()
    {
        return id;
    }

    public int TournamentID()
    {
        return tournamentId;
    }

    public String Title()
    {
        return title;
    }

    public String Message()
    {
        return message;
    }

    public String Author()
    {
        return author;
    }

    private void setCalendar()
    {
        String stringDate = date;
        String[] dateSegments = stringDate.split("-");

        calendar.set(Integer.parseInt(dateSegments[0]),
                Integer.parseInt(dateSegments[1]) - 1,
                Integer.parseInt(dateSegments[2]));
    }

    public String FriendlyDate()
    {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        String _date = formatDate.format(calendar.getTime());
        return _date;
    }
}
