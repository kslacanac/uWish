package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.MultiAutoCompleteTextView;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;

    private Item item;

    private Toolbar toolbar;
    private ViewSwitcher viewSwitcher;

    private TextView name;
    private TextView description;
    private TextView link;
    private ImageView image;
    private TextView find;
    private ImageButton delete;
    private ImageButton edit;

    private EditText editName;
    private EditText editDescription;
    private EditText editLink;
    private MultiAutoCompleteTextView editGroups;
    private ImageView editImage;
    private Button galleryBtn;
    private Button urlBtn;
    private EditText editUrl;
    private Button saveChangesBtn;

    private static int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private StorageReference filePath;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        viewSwitcher = (ViewSwitcher)findViewById(R.id.viewswitcher);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("Wishlist");
        mStorage = FirebaseStorage.getInstance().getReference();

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        name = (TextView) findViewById(R.id.item_name);
        name.setText(item.getName());
        description = (TextView) findViewById(R.id.item_description);
        description.setText(item.getDescription());
        link = (TextView) findViewById(R.id.item_link);
        link.setText(item.getLink());
        find = (TextView)findViewById(R.id.textView);
        //set typeface for textview
        name.setTypeface(lregular);
        description.setTypeface(lregular);
        find.setTypeface(lregular);
        link.setTypeface(lregular);

        editName = (EditText)findViewById(R.id.editName);
        editDescription = (EditText)findViewById(R.id.editDescription);
        editLink = (EditText)findViewById(R.id.editLink);
        editGroups = (MultiAutoCompleteTextView)findViewById(R.id.editGroups);
        editImage = (ImageView)findViewById(R.id.editImage);
        editUrl = (EditText)findViewById(R.id.editUrl);
        galleryBtn = (Button)findViewById(R.id.galleryBtn);
        urlBtn = (Button)findViewById(R.id.urlBtn);

        image = (ImageView) findViewById(R.id.item_image);
        if (item.getImageUri() == "" || item.getImage() != null) {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("Wishes").child(item.getImage());
            Glide.with(ItemDetailsActivity.this).using(new FirebaseImageLoader()).load(reference).into(image);
        } else {
            Picasso.with(this).load(item.getImageUri()).into(image);
        }

        delete = (ImageButton) findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDatabase.child(item.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        edit = (ImageButton) findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            viewSwitcher.getNextView();
                showEditableData();
            }
        });

        saveChangesBtn = (Button)findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                viewSwitcher.showPrevious();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUrl.getText() == null) {
                    Toast.makeText(ItemDetailsActivity.this, "Please enter image URL", Toast.LENGTH_LONG);
                } else {
                    Picasso.with(getApplicationContext()).load(editUrl.getText().toString()).into(editImage);
                }
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

    private void showEditableData() {
        editName.setText(item.getName());
        editDescription.setText(item.getDescription());
        editLink.setText(item.getDescription());
        if (item.getImageUri() == "" || item.getImage() != null) {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("Wishes").child(item.getImage());
            Glide.with(ItemDetailsActivity.this).using(new FirebaseImageLoader()).load(reference).into(image);
        } else {
            editUrl.setText(item.getImageUri());
            Picasso.with(this).load(item.getImageUri()).into(image);
        }
        //TODO MultiAutoCompleteTextView edit
    }

    private void saveChanges() {
        item.setName(editName.getText().toString());
        item.setDescription(editDescription.getText().toString());
        item.setLink(editLink.getText().toString());
        if (editUrl.getText().toString() == "") {
            filePath = mStorage.child("Wishes").child(selectedImage.getLastPathSegment());
            filePath.putFile(selectedImage);
            item.setImage(selectedImage.getLastPathSegment().toString());
        } else item.setImageUri(editUrl.getText().toString());
        mDatabase.child("Wishlist").child(item.getKey()).setValue(item);
    }

}
