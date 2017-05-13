package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView eventName;
    private TextView eventDate;
    private TextView eventDescription;
    private TextView about;
    private ImageView categoryImage;
    private Button editBtn;
    private Button deleteBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        eventName = (TextView)findViewById(R.id.event_name);
        eventDate = (TextView)findViewById(R.id.event_date);
        eventDescription = (TextView)findViewById(R.id.event_description);
        categoryImage = (ImageView)findViewById(R.id.categoryImage);
        about = (TextView)findViewById(R.id.about);
        editBtn = (Button)findViewById(R.id.edit_btn);
        deleteBtn = (Button)findViewById(R.id.delete_btn);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        eventName.setText(event.getName());
        eventDate.setText(event.getDate());
        eventDescription.setText(event.getDescription());
        String cat = event.getCategory();
        if (cat.equals("Birthday"))
            categoryImage.setImageResource(R.drawable.ic_birthday_cat);
        else if (cat.equals("Prom"))
            categoryImage.setImageResource(R.drawable.ic_prom_cat);
        else if (cat.equals("Wedding"))
            categoryImage.setImageResource(R.drawable.ic_wedding_cat);
        else if (cat.equals("Baby shower"))
            categoryImage.setImageResource(R.drawable.ic_baby_shower_cat);
        else if (cat.equals("Graduation"))
            categoryImage.setImageResource(R.drawable.ic_graduation_cat);
        else categoryImage.setImageResource(R.drawable.ic_other_cat);

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        //set typeface for textview
        eventName.setTypeface(lregular);
        eventDate.setTypeface(lregular);
        eventDescription.setTypeface(lregular);
        about.setTypeface(lregular);

    }


}
