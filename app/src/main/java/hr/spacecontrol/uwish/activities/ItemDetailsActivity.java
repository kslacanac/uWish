package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import hr.spacecontrol.uwish.fragments.MyWishList;
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
    private Button delete;
    private Button edit;
    private CheckBox checkBox;

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
        checkBox = (CheckBox)findViewById(R.id.checkBox);

        viewSwitcher.setMeasureAllChildren(false);

        image = (ImageView) findViewById(R.id.item_image);
        if (item.getImageUri() == null || item.getImageUri().toString().equals("")) {
            if (item.getImage() != null) {
                StorageReference reference = mStorage.child("Wishes").child(item.getImage());
                Glide.with(ItemDetailsActivity.this).using(new FirebaseImageLoader()).load(reference).into(image);
            }
        } else Picasso.with(this).load(item.getImageUri()).into(image);

        if (item.isReceived()) {
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Drawable highlight = getResources().getDrawable(R.drawable.border_recieved);
                    //image.setBackground(highlight);
                    item.setReceived(true);
                    mDatabase.child(item.getKey()).child("received").setValue(item.isReceived());
                }
            }
        });
        delete = (Button) findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDatabase.child(item.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue();
                        if (item.getImage() != null) mStorage.child("Wishes").child(item.getImage()).delete();
                        finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        edit = (Button) findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            viewSwitcher.showNext();
                showEditableData();
            }
        });

        editName = (EditText)findViewById(R.id.editName);
        editDescription = (EditText)findViewById(R.id.editDescription);
        editLink = (EditText)findViewById(R.id.editLink);
        editGroups = (MultiAutoCompleteTextView)findViewById(R.id.editGroups);
        editImage = (ImageView)findViewById(R.id.editImage);
        editUrl = (EditText)findViewById(R.id.editUrl);
        galleryBtn = (Button)findViewById(R.id.galleryBtn);
        urlBtn = (Button)findViewById(R.id.urlBtn);


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
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUrl.getText() == null || editUrl.getText().toString().equals("")) {
                    Toast.makeText(ItemDetailsActivity.this, "Please enter image URL", Toast.LENGTH_LONG);
                } else {
                    Picasso.with(getApplicationContext()).load(editUrl.getText().toString()).into(editImage);
                }
            }
        });

        //set typeface for textview
        name.setTypeface(lregular);
        description.setTypeface(lregular);
        find.setTypeface(lregular);
        link.setTypeface(lregular);
        editName.setTypeface(lregular);
        editDescription.setTypeface(lregular);
        editLink.setTypeface(lregular);
        editGroups.setTypeface(lregular);
        editUrl.setTypeface(lregular);
        galleryBtn.setTypeface(lregular);
        urlBtn.setTypeface(lregular);
        saveChangesBtn.setTypeface(lregular);
        checkBox.setTypeface(lregular);
        edit.setTypeface(lregular);
        delete.setTypeface(lregular);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
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
        editLink.setText(item.getLink());
        if (item.getImageUri() == null || item.getImageUri().equals("")) {
            if (item.getImage() != null) {
                StorageReference reference = mStorage.child("Wishes").child(item.getImage());
                Glide.with(ItemDetailsActivity.this).using(new FirebaseImageLoader()).load(reference).into(editImage);
            }
        } else {
            editUrl.setText(item.getImageUri());
            Picasso.with(getApplicationContext()).load(item.getImageUri()).into(editImage);
        }
        //TODO MultiAutoCompleteTextView edit
    }

    private void saveChanges() {
        item.setName(editName.getText().toString());
        item.setDescription(editDescription.getText().toString());
        item.setLink(editLink.getText().toString());
        if (editUrl.getText().toString().equals("") || editUrl.getText() == null) {
            if (selectedImage != null) {
                filePath = mStorage.child("Wishes").child(selectedImage.getLastPathSegment());
                filePath.putFile(selectedImage);
                item.setImage(selectedImage.getLastPathSegment().toString());
            }
        } else item.setImageUri(editUrl.getText().toString());
        mDatabase.child(item.getKey()).setValue(item);
    }

}
