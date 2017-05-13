package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import android.widget.Toast;
import android.widget.ViewSwitcher;

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

import org.w3c.dom.Text;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private StorageReference filePath;
    private Uri selectedImage;

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
    private Button editImageBtn;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar firstToolbar = (Toolbar) findViewById(R.id.firstToolbar);
        setSupportActionBar(firstToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        mStorage = FirebaseStorage.getInstance().getReference().child("Profiles");

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
        editImageBtn = (Button) findViewById(R.id.editImageBtn);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                showUserData();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
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
                saveChanges();
                viewSwitcher.showPrevious();
                //showUserData();
            }
        });

        editImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                editImage.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void showUserData() {
        if (user.getImage() != null) {
            filePath = mStorage.child(user.getImage().toString());
            Glide.with(ProfileActivity.this).using(new FirebaseImageLoader()).load(filePath).into(imageView);
        }
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
        if (user.getImage() != null) {
            filePath = mStorage.child(user.getImage().toString());
            Glide.with(ProfileActivity.this).using(new FirebaseImageLoader()).load(filePath).into(editImage);
        }
        editName.setText(user.getName());
        if (user.getDetails() != null) {
            if (user.getDetails().getClothingSize() != null)
                editClothingSize.setText(user.getDetails().getClothingSize());
            if (user.getDetails().getShoeSize() != null)
                editShoeSize.setText(user.getDetails().getShoeSize());
            if (user.getDetails().getFavouriteColor() != null)
                editFavColor.setText(user.getDetails().getFavouriteColor());
            if (user.getDetails().getFavouriteFood() != null)
                editFavFood.setText(user.getDetails().getFavouriteFood());
            if (user.getDetails().getSports() != null)
                editSports.setText(user.getDetails().getSports());
            if (user.getDetails().getHobbies() != null)
                editHobbies.setText(user.getDetails().getHobbies());
            if (user.getDetails().getInterests() != null)
                editInterests.setText(user.getDetails().getInterests());
            if (user.getDetails().getOther() != null)
                editOther.setText(user.getDetails().getOther());
        }
    }

    private void saveChanges() {
        if (selectedImage != null) {
            filePath = mStorage.child(selectedImage.getLastPathSegment());
            filePath.putFile(selectedImage);
            user.setImage(selectedImage.getLastPathSegment().toString());
        }
        String name = editName.getText().toString();
        user.setName(name);
        User.PersonalInfo personalInfo = new User.PersonalInfo();
        personalInfo.setClothingSize(editClothingSize.getText().toString());
        personalInfo.setShoeSize(editShoeSize.getText().toString());
        personalInfo.setFavouriteColor(editFavColor.getText().toString());
        personalInfo.setFavouriteFood(editFavFood.getText().toString());
        personalInfo.setSports(editSports.getText().toString());
        personalInfo.setHobbies(editHobbies.getText().toString());
        personalInfo.setInterests(editInterests.getText().toString());
        personalInfo.setOther(editOther.getText().toString());
        user.setDetails(personalInfo);

        mDatabase.child("details").setValue(personalInfo);
        mDatabase.child("name").setValue(name);
        mDatabase.child("image").setValue(user.getImage());
    }
}
