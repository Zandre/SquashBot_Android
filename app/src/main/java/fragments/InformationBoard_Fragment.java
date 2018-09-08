package fragments;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.squashbot.R;

import java.util.ArrayList;

import ExternalApplications.SharingIsCaring;
import adapters.InformationBoard_Adapter;
import tournament_classes.Contact;
import tournament_classes.InformationBoard;

/**
 * Created by CisForCookie on 2016/01/14.
 */

@SuppressLint("ValidFragment")
public class InformationBoard_Fragment extends ListFragment
{
    ArrayList<InformationBoard> messages;


    public InformationBoard_Fragment(ArrayList<InformationBoard> messages)
    {
        this.messages = messages;
    }

    public InformationBoard_Fragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        registerForContextMenu(getListView());


        getListView().setDividerHeight(0);

        InformationBoard_Adapter adapter = new InformationBoard_Adapter(getActivity(), messages);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo selectedItem = (AdapterView.AdapterContextMenuInfo)menuInfo;
        InformationBoard message = messages.get(selectedItem.position);
        menu.setHeaderTitle(message.Title());

        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextualmenu_informationboard, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        android.widget.AdapterView.AdapterContextMenuInfo selectedItem = (android.widget.AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = selectedItem.position;

        switch(item.getItemId())
        {
            case R.id.informationboard_share:
                SharingIsCaring share = new SharingIsCaring(getActivity());
                InformationBoard message = messages.get(selectedItem.position);
                share.ShareInformationBoard(message);
                break;
        }
        return false;
    }
}