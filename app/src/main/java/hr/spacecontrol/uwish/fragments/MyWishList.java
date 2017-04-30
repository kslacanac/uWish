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

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.activities.ItemDetailsActivity;
import hr.spacecontrol.uwish.adapters.ItemGridAdapter;
import hr.spacecontrol.uwish.adapters.MyWishAdapter;
import hr.spacecontrol.uwish.objects.Item;

public class MyWishList extends Fragment {

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        itemGridView = (GridView) view.findViewById(R.id.item_grid);

        items = new ArrayList<>();

        items.add(new Item("Sun glasses",R.drawable.item01, "Glasses with black glass so I can look directly into the Sun.", "www.glasses.com"));
        items.add(new Item("Skateboard",R.drawable.item02, "A board with wheels so I can roll around and make stupid noise.", "www.hateboard.com"));
        items.add(new Item("Water bottle", R.drawable.item03, "BPA free bottle, with cute design.", "www.equa.com"));
        items.add(new Item("Wallet", R.drawable.item04, "Leather wallet with a lot of money inside of it.", "www.wallets.com"));
        items.add(new Item("Notebook", R.drawable.item05, "Just a regular paper notebook with lines.", "www.notebooks.com"));
        items.add(new Item("iPhone mask", R.drawable.item06, "iPhone mask because I totally have an iPhone.", "www.ebay.com/chingchongshop"));
        items.add(new Item("Elf cloak", R.drawable.item07, "LOTR style Legolas invisibility elf cloak with a big hoodie.", "www.lotr.com"));
        items.add(new Item("Perfume", R.drawable.item08, "Daisy by Marc Jacobs", "www.marcjacobs.com"));
        items.add(new Item("Bag", R.drawable.item09, "Lady shoulder bag, pinkish color.", "www.ladybags.com"));
        items.add(new Item("Mouse", R.drawable.item10, "Wireless mouse in dark red color.", "www.mouses.com"));
        items.add(new Item("Headphones", R.drawable.item11, "Wireless headphones in red color.", "www.headphones.com"));
        items.add(new Item("Lipstick", R.drawable.item12, "Red lipstick by NARS", "www.nars.com"));
        items.add(new Item("Blanket", R.drawable.item13, "Pink soft blanket.", "www.blankets.com"));

        itemGridAdapter = new MyWishAdapter(getActivity().getApplicationContext(), items);
        itemGridView.setAdapter(itemGridAdapter);

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
