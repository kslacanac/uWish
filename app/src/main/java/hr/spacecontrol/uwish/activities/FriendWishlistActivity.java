package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendWishAdapter;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.User;

public class FriendWishlistActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView itemGridView;
    private FriendWishAdapter itemGridAdapter;

    private static User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_wishlist);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        friend = (User) intent.getSerializableExtra("friend");

        itemGridView = (GridView) findViewById(R.id.item_grid);
        Collection<Item> collection = friend.getWishlist().values();
        final List<Item> wishes = new ArrayList<>(collection);
        itemGridAdapter = new FriendWishAdapter(getApplicationContext(), wishes);
        itemGridView.setAdapter(itemGridAdapter);

        itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendWishlistActivity.this, FriendWishActivity.class);
                intent.putExtra("item", wishes.get(position));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }
}
