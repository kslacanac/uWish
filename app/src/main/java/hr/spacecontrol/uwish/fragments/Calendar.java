package hr.spacecontrol.uwish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
