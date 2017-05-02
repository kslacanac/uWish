package hr.spacecontrol.uwish.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.EventListAdapter;
import hr.spacecontrol.uwish.objects.Event;

public class Dashboard extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ListView eventListView = (ListView) view.findViewById(R.id.event_list);

        List<Event> events = new ArrayList<>();
        //add some data to list
        events.add(new Event("Mom's Birthday", "Monday, 20/03/2017"));
        events.add(new Event("Uncle's Wedding", "Saturday, 25/03/2017"));
        events.add(new Event("Baby shower", "15/04/2017"));
        events.add(new Event("Kerry's birthday", "17/05/2017"));
        events.add(new Event("Prom night", "28/05/2017"));
        events.add(new Event("Paula's graduation", "15/06/2017"));
        events.add(new Event("My cat's birthday", "20/06/2017"));

        //initialize adapter
        EventListAdapter eventListAdapter = new EventListAdapter(getActivity().getApplicationContext(), events);
        eventListView.setAdapter(eventListAdapter);

        return view;
    }

}
