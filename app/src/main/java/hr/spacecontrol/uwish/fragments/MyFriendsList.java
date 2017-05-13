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

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.FriendDetailsActivity;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.Event;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.User;

public class MyFriendsList extends Fragment {

    ListView friendListView;
    List<User> friends;
    FriendListAdapter friendListAdapter;

    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;

    public MyFriendsList() {
        // Required empty public constructor
    }

    public static MyFriendsList newInstance() {
        MyFriendsList fragment = new MyFriendsList();
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_friends_list, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(firebaseUser.getUid());

        friendListView = (ListView) view.findViewById(R.id.friend_list);
        friends = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friends.removeAll(friends);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    friends.add(snapshot.getValue(User.class));
                    friendListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendListAdapter = new FriendListAdapter(getActivity().getApplicationContext(), friends);
        friendListView.setAdapter(friendListAdapter);

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FriendDetailsActivity.class);
                intent.putExtra("friend", friends.get(position));
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
