package hr.spacecontrol.uwish.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.User;

public class SearchableActivity extends ListActivity {

    ListView listView;
    FriendListAdapter listAdapter;
    List<User> searchResults;
    FirebaseUser firebaseUser;
    DatabaseReference friendsDB;
    DatabaseReference usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
/*
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        friendsDB = FirebaseDatabase.getInstance().getReference().child("Friends");
        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");*/

        listView = (ListView)findViewById(R.id.list);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doFriendSearch(query);
        }
    }

    public void doFriendSearch(String query) {
        // do the search and fill the searchResults list
        //searchResults = new ArrayList<>();
        /*usersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersDB.child(friend.getUID()).child("FriendRequests").child(myself.getUID()).setValue(myself);

                listAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });*/


        User miro = new User("miro", "miro@gmail.com");
        miro.setUID("29132138912dasd");
        miro.setImage("9603");
        searchResults.add(miro);

        listAdapter = new FriendListAdapter(getApplicationContext(), searchResults);
        listView.setAdapter(listAdapter);

        Intent intent = new Intent(SearchableActivity.this, SearchResultActivity.class);
        intent.putExtra("friends", query);
        //intent.putParcelableArrayListExtra("friends", (ArrayList<? extends Parcelable>) searchResults);
        startActivity(intent);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchableActivity.this, SearchResultActivity.class);
                intent.putExtra("friends", (Parcelable) searchResults);
                //intent.putParcelableArrayListExtra("friends", (ArrayList<? extends Parcelable>) searchResults);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search for friends");
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        //searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(SearchableActivity.this, SearchResultActivity.class);
                intent.putExtra("friends", s);
                //intent.putParcelableArrayListExtra("friends", (ArrayList<? extends Parcelable>) searchResults);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

  /*  public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (selectionArgs != null && selectionArgs.length > 0 && selectionArgs[0].length() > 0) {
            // the entered text can be found in selectionArgs[0]
            // return a cursor with appropriate data
            return;
        }
        else {
            // user hasn’t entered anything
            // thus return a default cursor
            return;
        }
    }*/

}
