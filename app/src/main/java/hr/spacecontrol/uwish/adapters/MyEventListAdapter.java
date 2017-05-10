package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

/**
 * Created by Karmela on 02/05/2017.
 */

public class MyEventListAdapter extends BaseAdapter {
    private Context context;
    private List<Event> eventList;

    public MyEventListAdapter(Context context, List<Event> eventList) {
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

        View v = View.inflate(context, R.layout.listview_myevent, null);

        ImageView icon = (ImageView)v.findViewById(R.id.iconLeft);
        TextView eventName = (TextView)v.findViewById(R.id.event_name);
        TextView eventDate = (TextView)v.findViewById(R.id.event_date);
        Typeface lregular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");

        //set text for textview
        eventName.setText(eventList.get(position).getName());
        eventDate.setText(eventList.get(position).getDate());

        //set typeface for textview
        eventName.setTypeface(lregular);
        eventDate.setTypeface(lregular);

        //v.setTag(mEventList.get(position).getId());

        ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
        ImageButton editBtn = (ImageButton) v.findViewById(R.id.edit_btn);

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