package hr.spacecontrol.uwish.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.EventDetailsActivity;
import hr.spacecontrol.uwish.adapters.EventListAdapter;
import hr.spacecontrol.uwish.adapters.MyEventListAdapter;
import hr.spacecontrol.uwish.objects.Event;

public class MyEvents extends Fragment {

    ListView eventListView;
    List<Event> events;
    MyEventListAdapter eventListAdapter;

    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;

    public MyEvents() {
        // Required empty public constructor
    }

    public static MyEvents newInstance() {
        MyEvents fragment = new MyEvents();
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(firebaseUser.getUid());

        eventListView = (ListView) view.findViewById(R.id.event_list);

        events = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.removeAll(events);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    events.add(snapshot.getValue(Event.class));
                    eventListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eventListAdapter = new MyEventListAdapter(getActivity().getApplicationContext(), events);
        eventListView.setAdapter(eventListAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                intent.putExtra("event", events.get(position));
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
