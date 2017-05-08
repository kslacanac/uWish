package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name;
    private TextView description;
    private TextView link;
    private ImageView image;
    private TextView find;
    private ImageButton delete;
    private ImageButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item"); // gets selected item from list
        // TODO fetch item from firebase to display info

        name = (TextView) findViewById(R.id.item_name);
        name.setText(item.getName());

        description = (TextView) findViewById(R.id.item_description);
        description.setText(item.getDescription());

        link = (TextView) findViewById(R.id.item_link);
        link.setText(item.getLink());

        image = (ImageView) findViewById(R.id.item_image);
        image.setImageResource(item.getImage());

        find = (TextView) findViewById(R.id.textView);

        //set typeface for textview
        name.setTypeface(lregular);
        description.setTypeface(lregular);
        find.setTypeface(lregular);
        link.setTypeface(lregular);

        delete = (ImageButton) findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //brise item
            }
        });

        edit = (ImageButton) findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //otvara activity za uredivanje
            }
        });

    }

}
