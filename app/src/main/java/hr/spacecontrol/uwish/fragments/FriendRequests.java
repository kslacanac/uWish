package hr.spacecontrol.uwish.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.spacecontrol.uwish.R;

public class FriendRequests extends Fragment {

    public FriendRequests() {
        // Required empty public constructor
    }

    public static FriendRequests newInstance() {
        FriendRequests fragment = new FriendRequests();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests, container, false);
    }

}
