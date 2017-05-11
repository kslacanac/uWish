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
import android.widget.GridView;

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
import hr.spacecontrol.uwish.activities.ItemDetailsActivity;
import hr.spacecontrol.uwish.adapters.ItemGridAdapter;
import hr.spacecontrol.uwish.adapters.MyWishAdapter;
import hr.spacecontrol.uwish.objects.Item;

public class MyWishList extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;

    GridView itemGridView;
    List<Item> items;
    ItemGridAdapter itemGridAdapter;

    public MyWishList() {
        // Required empty public constructor
    }

    public static MyWishList newInstance() {
        MyWishList fragment = new MyWishList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("Wishlist");
        items = new ArrayList<>();
        itemGridView = (GridView) view.findViewById(R.id.item_grid);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items.add(snapshot.getValue(Item.class));
                }
                itemGridAdapter = new MyWishAdapter(getActivity().getApplicationContext(), items);
                itemGridView.setAdapter(itemGridAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
                intent.putExtra("item", items.get(position));
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

}
