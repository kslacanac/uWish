package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

/**
 * Custom adapter for list of events. Makes the connection between a List<Event> and a ListView.
 * listview_event.xml represents the look of a single event in the ListView (the item of the ListView).
 * Created by Karmela on 18/03/2017.
 */

public class EventListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> eventList;

    public EventListAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.listview_event, null);

        TextView eventName = (TextView)v.findViewById(R.id.event_name);
        TextView eventDate = (TextView)v.findViewById(R.id.event_date);

        //set text for textview
        eventName.setText(eventList.get(position).getName());
        eventDate.setText(eventList.get(position).getDate());

        //v.setTag(mEventList.get(position).getId());

        Button deleteBtn = (Button) v.findViewById(R.id.delete_btn);
        Button editBtn = (Button) v.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //brise event iz liste
                eventList.remove(position);
                notifyDataSetChanged();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //otvara activity za uredivanje eventa
                notifyDataSetChanged();
            }
        });

        return v;
    }
}
