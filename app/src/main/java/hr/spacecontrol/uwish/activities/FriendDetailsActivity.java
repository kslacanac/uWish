package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendWishAdapter;
import hr.spacecontrol.uwish.adapters.ItemGridAdapter;
import hr.spacecontrol.uwish.objects.Person;

public class FriendDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        Intent intent = getIntent();
        Person friend = (Person) intent.getSerializableExtra("friend");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        GridView itemGridView = (GridView) findViewById(R.id.item_grid);

        ItemGridAdapter itemGridAdapter = new FriendWishAdapter(getApplicationContext(), friend.getWishList());
        itemGridView.setAdapter(itemGridAdapter);

        TextView listTitle = (TextView) findViewById(R.id.list_title);
        String title = friend.getName().concat("'s list");
        listTitle.setText(title);
    }
}
