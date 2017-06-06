package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

/**
 * Created by Karmela on 02/05/2017.
 */

public class MyEventListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> eventList;

    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(firebaseUser.getUid());

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
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabase.child(eventList.get(position).getKey()).removeValue();
                        eventList.remove(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                Toast.makeText(v.getContext(), "Event deleted", Toast.LENGTH_LONG).show();
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
