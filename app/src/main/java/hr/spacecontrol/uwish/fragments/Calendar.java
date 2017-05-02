package hr.spacecontrol.uwish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;

public class Calendar extends Fragment {


    public Calendar() {
        // Required empty public constructor
    }

    public static Calendar newInstance() {
        Calendar fragment = new Calendar();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendarView);

        return view;
    }

}
