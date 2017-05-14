package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.adapters.FriendWishAdapter;
import hr.spacecontrol.uwish.adapters.ItemGridAdapter;
import hr.spacecontrol.uwish.objects.Item;
import hr.spacecontrol.uwish.objects.User;

public class FriendDetailsActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private StorageReference mStorage;
    private StorageReference filePath;

    private ImageView imageView;
    private TextView name;
    private TextView clothingSize;
    private TextView shoeSize;
    private TextView favColor;
    private TextView favFood;
    private TextView sports;
    private TextView hobbies;
    private TextView interests;
    private TextView other;
    private Button wishlistBtn;

    private Toolbar toolbar;

    private User friend;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        final Intent intent = getIntent();
        friend = (User) intent.getSerializableExtra("friend"); // gets selected friend

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference().child("Profiles");

        imageView = (ImageView)findViewById(R.id.imageView);
        name = (TextView)findViewById(R.id.name);
        clothingSize = (TextView)findViewById(R.id.clothingSize);
        shoeSize = (TextView) findViewById(R.id.shoeSize);
        favColor = (TextView)findViewById(R.id.color);
        favFood = (TextView)findViewById(R.id.food);
        sports = (TextView)findViewById(R.id.sports);
        hobbies = (TextView) findViewById(R.id.hobbies);
        interests = (TextView)findViewById(R.id.interests);
        other = (TextView)findViewById(R.id.other);
        wishlistBtn = (Button)findViewById(R.id.wishlistBtn);

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        name.setTypeface(lregular);
        clothingSize.setTypeface(lregular);
        shoeSize.setTypeface(lregular);
        favColor.setTypeface(lregular);
        favFood.setTypeface(lregular);
        sports.setTypeface(lregular);
        hobbies.setTypeface(lregular);
        interests.setTypeface(lregular);
        other.setTypeface(lregular);

        showUserData();

        wishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FriendDetailsActivity.this, FriendWishlistActivity.class);
                intent1.putExtra("friend", friend);
                startActivity(intent1);
            }
        });

    }

    private void showUserData() {
        if (friend.getImage() != null) {
            filePath = mStorage.child(friend.getImage().toString());
            Glide.with(FriendDetailsActivity.this).using(new FirebaseImageLoader()).load(filePath).into(imageView);
        }
        name.setText(friend.getName());
        if (friend.getDetails() != null) {
            if (friend.getDetails().getClothingSize() != null)
                clothingSize.setText(friend.getDetails().getClothingSize());
            if (friend.getDetails().getShoeSize() != null)
                shoeSize.setText(friend.getDetails().getShoeSize());
            if (friend.getDetails().getFavouriteColor() != null)
                favColor.setText(friend.getDetails().getFavouriteColor());
            if (friend.getDetails().getFavouriteFood() != null)
                favFood.setText(friend.getDetails().getFavouriteFood());
            if (friend.getDetails().getSports() != null)
                sports.setText(friend.getDetails().getSports());
            if (friend.getDetails().getHobbies() != null)
                hobbies.setText(friend.getDetails().getHobbies());
            if (friend.getDetails().getInterests() != null)
                interests.setText(friend.getDetails().getInterests());
            if (friend.getDetails().getOther() != null) other.setText(friend.getDetails().getOther());
        }
    }
}
