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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.FriendDetailsActivity;
import hr.spacecontrol.uwish.adapters.EventListAdapter;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.adapters.FriendRequestsAdapter;
import hr.spacecontrol.uwish.adapters.MyEventListAdapter;
import hr.spacecontrol.uwish.objects.Event;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.User;

public class FriendRequests extends Fragment {

    ListView friendRequestsListView;
    List<User> friendRequests;


    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;
    FriendRequestsAdapter friendRequestsAdapter;

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
        View view = inflater.inflate(R.layout.fragment_my_friends_list, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("FriendRequests");

        ListView requestListView = (ListView) view.findViewById(R.id.friend_list);

        final List<User> requests = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requests.removeAll(requests);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    requests.add(snapshot.getValue(User.class));
                    friendRequestsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //events.add(new Event("Mom's Birthday", "Monday, 20/03/2017"));



        //initialize adapter
        friendRequestsAdapter = new FriendRequestsAdapter(getActivity().getApplicationContext(), requests);
        requestListView.setAdapter(friendRequestsAdapter);

        return view;
    }


}
