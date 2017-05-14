package hr.spacecontrol.uwish.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.User;

/**
 * Created by tom on 14.05.17..
 */

public class SearchResultActivity extends AppCompatActivity {

    ListView listView;
    FirebaseUser firebaseUser;
    DatabaseReference friendsDB;
    DatabaseReference usersDB;
    FriendListAdapter listAdapter;
    List<User> searchResults;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        query = (String) (intent.getSerializableExtra("friends"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        friendsDB = FirebaseDatabase.getInstance().getReference().child("Friends");
        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");

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

        listView = (ListView) findViewById(R.id.friend_list);
        listAdapter = new FriendListAdapter(this, searchResults);
        listView.setAdapter(listAdapter);

    }
}
