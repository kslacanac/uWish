package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView eventName = (TextView)findViewById(R.id.event_name);
        TextView eventDate = (TextView)findViewById(R.id.event_date);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");
        eventName.setText(event.getName());
        eventDate.setText(event.getDate());
        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        //set typeface for textview
        eventName.setTypeface(lregular);
        eventDate.setTypeface(lregular);

    }


}
