package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView eventName;
    private TextView eventDate;
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

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        // TODO fetch chosen event from firebase
        eventName.setText(event.getName());
        eventDate.setText(event.getDate());

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        //set typeface for textview
        eventName.setTypeface(lregular);
        eventDate.setTypeface(lregular);

    }


}
