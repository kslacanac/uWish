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
import hr.spacecontrol.uwish.activities.FriendDetailsActivity;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.User;

public class MyFriendsList extends Fragment {

    ListView friendListView;
    List<User> friends;
    FriendListAdapter friendListAdapter;

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

        friendListView = (ListView) view.findViewById(R.id.friend_list);

        // Making some friends and their wish lists
        User Amelie = new User("Amelie Sanders", R.drawable.friend02);
        List<Item> items = new ArrayList<>();
        items.add(new Item("Sun glasses",R.drawable.item01));
        items.add(new Item("Skateboard",R.drawable.item02));
        items.add(new Item("Water bottle", R.drawable.item03));
        Amelie.setWishList(items);

        friends = new ArrayList<>();
        friends.add(new User("Mate Matičar", R.drawable.friend05));
        friends.add(Amelie);
        friends.add(new User("Donna Paulsen", R.drawable.friend01));
        friends.add(new User("Cassey Cho", R.drawable.friend03));
        friends.add(new User("Michael Ross", R.drawable.friend04));

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
