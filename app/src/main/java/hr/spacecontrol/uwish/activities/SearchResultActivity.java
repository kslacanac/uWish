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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.AddFriendAdapter;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.User;

/**
 * Created by tom on 14.05.17..
 */

public class SearchResultActivity extends AppCompatActivity {

    ListView listView;
    FirebaseUser firebaseUser;
    DatabaseReference friendsDB;
    DatabaseReference usersDB, resultsDB;
    AddFriendAdapter listAdapter;
    List<User> searchResults;
    List<String> users, friends, results;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        listView = (ListView) findViewById(R.id.friend_list);

        Intent intent = getIntent();
        query = (String) (intent.getSerializableExtra("friends"));

        searchResults = new ArrayList<>();
        users = new ArrayList<>();
        friends = new ArrayList<>();
        results = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        friendsDB = FirebaseDatabase.getInstance().getReference().child("Friends").child(firebaseUser.getUid());
        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");
        resultsDB = FirebaseDatabase.getInstance().getReference().child("Users");

        usersDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    users.add(dataSnapshot1.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        friendsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    friends.add(dataSnapshot1.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        resultsDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(String user : users){
                    if(user.equals(firebaseUser.getUid()) || friends.contains(user))
                        continue;
                    results.add(user);
                }
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(results != null && snapshot != null && query != null) {
                        User userClass = snapshot.getValue(User.class);
                        if (results.contains(snapshot.getKey())
                                && userClass.getName().toLowerCase().contains(query.toLowerCase())) {
                            searchResults.add(userClass);
                        }
                    }
                }
                listAdapter = new AddFriendAdapter(SearchResultActivity.this, searchResults);
                listView.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
