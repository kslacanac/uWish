package hr.spacecontrol.uwish.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.spacecontrol.uwish.R;

public class FindFriends extends Fragment {

    public FindFriends() {
        // Required empty public constructor
    }

    public static FindFriends newInstance() {
        FindFriends fragment = new FindFriends();
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_find_friends, container, false);
    }

}
