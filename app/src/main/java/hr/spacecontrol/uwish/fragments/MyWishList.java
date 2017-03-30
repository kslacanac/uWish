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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyWishList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyWishList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyWishList extends Fragment {

    GridView itemGridView;
    List<Item> items;
    ItemGridAdapter itemGridAdapter;

    //private OnFragmentInteractionListener mListener;

    public MyWishList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * @return A new instance of fragment MyWishList.
     */
    public static MyWishList newInstance() {
        MyWishList fragment = new MyWishList();
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
        View view = inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        itemGridView = (GridView) view.findViewById(R.id.item_grid);

        items = new ArrayList<>();

        items.add(new Item("Sun glasses",R.drawable.item01, "Glasses with black glass so I can look directly into the Sun. Please.", "www.glasses.com"));
        items.add(new Item("Skateboard",R.drawable.item02, "A board with wheels so I can roll around and make stupid noise.", "www.hateboard.com"));
        items.add(new Item("Water bottle", R.drawable.item03, "BPA free bottle, no poison plis.", "www.equa.com"));
        items.add(new Item("Wallet", R.drawable.item04, "Leather wallet with a lot of money inside of it.", "www.wallets.com"));
        items.add(new Item("Notebook", R.drawable.item05, "Not the stupid movie, just a regular paper notebook for me to write notes.", "www.notebooks.com"));
        items.add(new Item("iPhone mask", R.drawable.item06, "This stupid ass iPhone mask because I totally have an iPhone.", "www.ebay.com/chingchongshop"));
        items.add(new Item("Elf cloak", R.drawable.item07, "LOTR style Legolas motherfucking invisibility elf cloak with a big ass hoodie.", "www.lotr.com"));

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
