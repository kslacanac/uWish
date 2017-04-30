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

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.FriendDetailsActivity;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.Person;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFriendsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFriendsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFriendsList extends Fragment {

    //private OnFragmentInteractionListener mListener;

    ListView friendListView;
    List<Person> friends;
    FriendListAdapter friendListAdapter;

    public MyFriendsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * @return A new instance of fragment MyFriendsList.
     */
    public static MyFriendsList newInstance() {
        MyFriendsList fragment = new MyFriendsList();
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_friends_list, container, false);

        friendListView = (ListView) view.findViewById(R.id.friend_list);

        // Making some friends and their wish lists
        Person Amelie = new Person("Amelie Sanders", R.drawable.friend02);
        List<Item> items = new ArrayList<>();
        items.add(new Item("Sun glasses",R.drawable.item01));
        items.add(new Item("Skateboard",R.drawable.item02));
        items.add(new Item("Water bottle", R.drawable.item03));
        Amelie.setWishList(items);

        friends = new ArrayList<>();
        friends.add(new Person("Mate Matičar", R.drawable.friend05));
        friends.add(Amelie);
        friends.add(new Person("Donna Paulsen", R.drawable.friend01));
        friends.add(new Person("Cassey Cho", R.drawable.friend03));
        friends.add(new Person("Michael Ross", R.drawable.friend04));

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

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
