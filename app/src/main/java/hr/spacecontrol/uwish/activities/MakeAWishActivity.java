package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

import static android.app.Activity.RESULT_OK;

public class MakeAWishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText title;
    private EditText description;
    private EditText whereToBuy;
    private MultiAutoCompleteTextView groups;
    private Button makeWishBtn;
    private ImageView imageView;
    private Button galleryUploadBtn;
    private EditText imageUrl;
    private Button urlUploadBtn;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private StorageReference filePath;
    private Uri selectedImage;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_wish);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        whereToBuy = (EditText)findViewById(R.id.wheretobuy);
        groups = (MultiAutoCompleteTextView) findViewById(R.id.groups);
        makeWishBtn = (Button)findViewById(R.id.make_wish_btn);
        imageView = (ImageView)findViewById(R.id.imgView);
        galleryUploadBtn = (Button)findViewById(R.id.galleryBtn);
        urlUploadBtn = (Button)findViewById(R.id.urlBtn);
        imageUrl = (EditText)findViewById(R.id.url);

        title.setTypeface(lregular);
        description.setTypeface(lregular);
        whereToBuy.setTypeface(lregular);
        groups.setTypeface(lregular);
        makeWishBtn.setTypeface(lregular);
        galleryUploadBtn.setTypeface(lregular);
        urlUploadBtn.setTypeface(lregular);
        imageUrl.setTypeface(lregular);

        // TODO get list from user's friend groups
        String[] autoCompleteList = {"Everyone", "Work people", "Family", "Best friends"};
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this, R.layout.autocomplete_list, autoCompleteList);
        groups.setAdapter(autoCompleteAdapter);
        groups.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        galleryUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        urlUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUrl.getText() == null || imageUrl.getText().toString().equals("")) {
                    Toast.makeText(MakeAWishActivity.this, "Please enter image URL", Toast.LENGTH_LONG);
                } else {
                    Picasso.with(getApplicationContext()).load(imageUrl.getText().toString()).into(imageView);
                }
            }
        });

        makeWishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(title.getText().toString());
                item.setDescription(description.getText().toString());
                item.setLink(whereToBuy.getText().toString());
                item.setGroups(groups.getText().toString());
                if (imageUrl.getText() == null || imageUrl.getText().toString().equals("")) {
                    if (selectedImage != null) {
                        filePath = mStorage.child("Wishes").child(selectedImage.getLastPathSegment());
                        filePath.putFile(selectedImage);
                        item.setImage(selectedImage.getLastPathSegment().toString());
                    }
                } else item.setImageUri(imageUrl.getText().toString());
                String key = generateKey(10);
                item.setKey(key);
                mDatabase.child("Wishlist").child(key).setValue(item);
                openItemDetailActivity(item);
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
                imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private String generateKey(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void openItemDetailActivity(Item item) {
        Intent intent = new Intent(MakeAWishActivity.this, ItemDetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
        finish();
    }
}
