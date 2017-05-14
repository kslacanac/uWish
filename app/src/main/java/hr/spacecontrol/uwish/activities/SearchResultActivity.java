package hr.spacecontrol.uwish.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendListAdapter;
import hr.spacecontrol.uwish.objects.User;

/**
 * Created by tom on 14.05.17..
 */

public class SearchResultActivity extends AppCompatActivity{

    ListView listView;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    FriendListAdapter listAdapter;
    List<User> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_friends_list);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        listView = (ListView)findViewById(R.id.friend_list);

}
