package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;
import hr.spacecontrol.uwish.objects.User;

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
        final ImageView imageView = (ImageView)v.findViewById(R.id.profile_image);
        final TextView hostName = (TextView)v.findViewById(R.id.host_name);
        ImageView iconLeft = (ImageView)v.findViewById(R.id.iconLeft);

        Typeface lregular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");

        //set text for textview
        eventName.setText(eventList.get(position).getName());
        eventDate.setText(eventList.get(position).getDate());
        String cat = eventList.get(position).getCategory();
        if (cat.equals("Birthday"))
            iconLeft.setImageResource(R.drawable.ic_birthday_cat);
        else if (cat.equals("Prom"))
            iconLeft.setImageResource(R.drawable.ic_prom_cat);
        else if (cat.equals("Wedding"))
            iconLeft.setImageResource(R.drawable.ic_wedding_cat);
        else if (cat.equals("Baby shower"))
            iconLeft.setImageResource(R.drawable.ic_baby_shower_cat);
        else if (cat.equals("Graduation"))
            iconLeft.setImageResource(R.drawable.ic_graduation_cat);
        else iconLeft.setImageResource(R.drawable.ic_other_cat);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(eventList.get(position).getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                StorageReference storage = FirebaseStorage.getInstance().getReference().child("Profiles").child(user.getImage());
                Glide.with(context).using(new FirebaseImageLoader()).load(storage).into(imageView);
                hostName.setText(user.getName().concat(" created an event!"));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //set typeface for textview
        eventName.setTypeface(lregular);
        eventDate.setTypeface(lregular);

        //v.setTag(mEventList.get(position).getId());

        /*ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
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
        });*/

        return v;
    }

}
