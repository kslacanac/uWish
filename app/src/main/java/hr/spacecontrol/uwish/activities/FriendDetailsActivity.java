package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendWishAdapter;
import hr.spacecontrol.uwish.adapters.ItemGridAdapter;
import hr.spacecontrol.uwish.objects.User;

public class FriendDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView itemGridView;
    private ItemGridAdapter itemGridAdapter;
    private TextView listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        Intent intent = getIntent();
        User friend = (User) intent.getSerializableExtra("friend"); // gets selected friend
        // TODO fetch friend info from firebase

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        itemGridView = (GridView) findViewById(R.id.item_grid);

        itemGridAdapter = new FriendWishAdapter(getApplicationContext(), friend.getWishList());
        itemGridView.setAdapter(itemGridAdapter);

        listTitle = (TextView) findViewById(R.id.list_title);
        String title = friend.getName().concat("'s list");
        listTitle.setText(title);
    }
}
