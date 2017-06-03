package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

public class FriendWishActivity extends AppCompatActivity {

    private Item wish;

    private ImageView imageView;
    private TextView name;
    private TextView description;
    private TextView whereToFind;
    private CheckBox checkBox;
    private TextView wishInfo;

    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_wish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        wish = (Item) intent.getSerializableExtra("item");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(wish.getUid()).child("Wishlist").child(wish.getKey());

        imageView = (ImageView)findViewById(R.id.item_image);
        name = (TextView)findViewById(R.id.item_name);
        description = (TextView)findViewById(R.id.item_description);
        whereToFind = (TextView)findViewById(R.id.item_link);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        wishInfo = (TextView)findViewById(R.id.reserved_received);

        if (wish.getImageUri() == null || wish.getImageUri().toString().equals("")) {
            if (wish.getImage() != null) {
                StorageReference reference = FirebaseStorage.getInstance().getReference().child("Wishes").child(wish.getImage());
                Glide.with(FriendWishActivity.this).using(new FirebaseImageLoader()).load(reference).into(imageView);
            }
        } else Picasso.with(this).load(wish.getImageUri()).into(imageView);

        name.setText(wish.getName());
        description.setText(wish.getDescription());
        whereToFind.setText(wish.getLink());

        if (wish.isReserved()) {
            if (wish.isReceived() == false) {
                wishInfo.setText("This item is reserved.");
                checkBox.setVisibility(View.INVISIBLE);
            }
        } else if (wish.isReceived()) {
            wishInfo.setText("I received this item!");
            checkBox.setVisibility(View.INVISIBLE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wish.setReserved(true);
                mDatabase.child("reserved").setValue(wish.isReserved());
                checkBox.setVisibility(View.INVISIBLE);
                wishInfo.setText("You reserved this item.");
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
