package hr.spacecontrol.uwish.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
        //set layout to view
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        //set id to ListView
        eventListView = (ListView) view.findViewById(R.id.event_list);

        events = new ArrayList<>();
        //add some data to list
        events.add(new Event("Mom's Birthday", "Monday, 20/03/2017"));
        events.add(new Event("Uncle's Wedding", "Saturday, 25/03/2017"));
        events.add(new Event("Baby shower", "15/04/2017"));
        events.add(new Event("Kerry's birthday", "17/05/2017"));
        events.add(new Event("Prom night", "28/05/2017"));
        events.add(new Event("Paula's graduation", "15/06/2017"));
        events.add(new Event("My cat's birthday", "20/06/2017"));

        //initialize adapter
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
