package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squashbot.R;

import java.util.ArrayList;

import tournament_classes.InformationBoard;

/**
 * Created by CisForCookie on 2016/01/14.
 */
public class InformationBoard_Adapter extends ArrayAdapter<InformationBoard>
{
    private final Context context;
    ArrayList<InformationBoard> messages;

    public InformationBoard_Adapter(Context context,  ArrayList<InformationBoard> messages)
    {
        super(context, R.layout.layout_information_board, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_information_board, parent, false);

        TextView title = (TextView)rowView.findViewById(R.id.tv_messageTitle);
        TextView date = (TextView)rowView.findViewById(R.id.tv_messageDate);
        TextView message = (TextView)rowView.findViewById(R.id.tv_messageMessage);
        TextView author = (TextView)rowView.findViewById(R.id.tv_messageAuthor);

        title.setText(""+ messages.get(position).Title());
        date.setText(""+ messages.get(position).FriendlyDate());
        message.setText(""+ messages.get(position).Message());
        author.setText(""+ messages.get(position).Author());

        return rowView;
    }
}
