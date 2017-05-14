package hr.spacecontrol.uwish.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.EventDetailsActivity;
import hr.spacecontrol.uwish.adapters.EventListAdapter;
import hr.spacecontrol.uwish.objects.Event;

public class Dashboard extends Fragment {

    DatabaseReference friendsDatabase;
    DatabaseReference eventsDatabase;
    FirebaseUser firebaseUser;
    List<Event> events;
    List<String> friendUid;
    EventListAdapter eventListAdapter;
    ListView eventListView;
    TextView welcomeText;
    View view;

    public Dashboard() {
        // Required empty public constructor
    }

    public static Dashboard newInstance() {
        Dashboard fragment = new Dashboard();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        eventListView = (ListView) view.findViewById(R.id.event_list);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        friendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(firebaseUser.getUid());

        events = new ArrayList<>();
        friendUid = new ArrayList<>();

        friendsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.removeAll(events);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    friendUid.add(snapshot.getKey());
                }
                for (String uid : friendUid) {
                    eventsDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(uid);
                    dohvatiEventove(uid);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

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

    private void dohvatiEventove(String uid) {
        eventsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    events.add(snapshot.getValue(Event.class));
                }
                eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), events);
                eventListView.setAdapter(eventListAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

}
