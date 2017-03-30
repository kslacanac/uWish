package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");

        TextView name = (TextView) findViewById(R.id.item_name);
        name.setText(item.getName());

        TextView description = (TextView) findViewById(R.id.item_description);
        description.setText(item.getDescription());

        TextView link = (TextView) findViewById(R.id.item_link);
        link.setText(item.getLink());

        ImageView image = (ImageView) findViewById(R.id.item_image);
        image.setImageResource(item.getImage());

        Button delete = (Button) findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //brise item
            }
        });

        Button edit = (Button) findViewById(R.id.edit_btn);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //otvara activity za uredivanje
            }
        });

    }
}
