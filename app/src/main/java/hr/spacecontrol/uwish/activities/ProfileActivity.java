package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    private User user;

    private ViewSwitcher viewSwitcher;
    private Button editProfileBtn;
    private Button saveChangesBtn;

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

    private ImageView editImage;
    private EditText editName;
    private EditText editClothingSize;
    private EditText editShoeSize;
    private EditText editFavColor;
    private EditText editFavFood;
    private EditText editSports;
    private EditText editHobbies;
    private EditText editInterests;
    private EditText editOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        viewSwitcher = (ViewSwitcher)findViewById(R.id.viewswitcher);
        editProfileBtn = (Button)findViewById(R.id.editProfileBtn);
        saveChangesBtn = (Button)findViewById(R.id.saveChangesBtn);

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

        editImage = (ImageView)findViewById(R.id.editImage);
        editName = (EditText) findViewById(R.id.editName);
        editClothingSize = (EditText) findViewById(R.id.editClothingSize);
        editShoeSize = (EditText) findViewById(R.id.editShoeSize);
        editFavColor = (EditText) findViewById(R.id.editColor);
        editFavFood = (EditText) findViewById(R.id.editFood);
        editSports = (EditText) findViewById(R.id.editSports);
        editHobbies = (EditText) findViewById(R.id.editHobbies);
        editInterests = (EditText) findViewById(R.id.editInterests);
        editOther = (EditText) findViewById(R.id.editOther);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                showUserData(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showNext();
                showEditableData();
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showPrevious();
                saveChanges();
            }
        });

    }

    private void showUserData(User user) {
        imageView.setImageResource(user.getImage());
        name.setText(user.getName());
        if (user.getDetails() != null) {
            if (user.getDetails().getClothingSize() != null)
                clothingSize.setText(user.getDetails().getClothingSize());
            if (user.getDetails().getShoeSize() != null)
                shoeSize.setText(user.getDetails().getShoeSize());
            if (user.getDetails().getFavouriteColor() != null)
                favColor.setText(user.getDetails().getFavouriteColor());
            if (user.getDetails().getFavouriteFood() != null)
                favFood.setText(user.getDetails().getFavouriteFood());
            if (user.getDetails().getSports() != null)
                sports.setText(user.getDetails().getSports());
            if (user.getDetails().getHobbies() != null)
                hobbies.setText(user.getDetails().getHobbies());
            if (user.getDetails().getInterests() != null)
                interests.setText(user.getDetails().getInterests());
            if (user.getDetails().getOther() != null) other.setText(user.getDetails().getOther());
        }
    }

    private void showEditableData() {
        editImage.setImageResource(user.getImage());
        editName.setText(user.getName());
        if (user.getDetails().getClothingSize() != null) editClothingSize.setText(user.getDetails().getClothingSize());
        if (user.getDetails().getShoeSize() != null) editShoeSize.setText(user.getDetails().getShoeSize());
        if (user.getDetails().getFavouriteColor() != null) editFavColor.setText(user.getDetails().getFavouriteColor());
        if (user.getDetails().getFavouriteFood() != null) editFavFood.setText(user.getDetails().getFavouriteFood());
        if (user.getDetails().getSports() != null) editSports.setText(user.getDetails().getSports());
        if (user.getDetails().getHobbies() != null) editHobbies.setText(user.getDetails().getHobbies());
        if (user.getDetails().getInterests() != null) editInterests.setText(user.getDetails().getInterests());
        if (user.getDetails().getOther() != null) editOther.setText(user.getDetails().getOther());
    }

    private void saveChanges() {
        user.setName(editName.getText().toString());
        user.getDetails().setClothingSize(editClothingSize.getText().toString());
        user.getDetails().setShoeSize(editShoeSize.getText().toString());
        user.getDetails().setFavouriteColor(editFavColor.getText().toString());
        user.getDetails().setFavouriteFood(editFavFood.getText().toString());
        user.getDetails().setSports(editSports.getText().toString());
        user.getDetails().setHobbies(editHobbies.getText().toString());
        user.getDetails().setInterests(editInterests.getText().toString());
        user.getDetails().setOther(editOther.getText().toString());

        mDatabase.setValue(user);
    }
}
