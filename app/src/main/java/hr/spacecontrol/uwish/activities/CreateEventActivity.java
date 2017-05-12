package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

public class CreateEventActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner category;
    private Button createBtn;
    private Toolbar toolbar;

    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(firebaseUser.getUid());

        title = (EditText)findViewById(R.id.make_event_title);
        description = (EditText)findViewById(R.id.make_event_description);
        datePicker = (DatePicker)findViewById(R.id.make_event_datepicker);
        category = (Spinner)findViewById(R.id.make_event_category_spinner);
        createBtn = (Button)findViewById(R.id.create_event_btn);

        createBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setName(title.getText().toString());
                event.setDescription(description.getText().toString());
                String date = String.valueOf(datePicker.getDayOfMonth()).concat("/")
                        .concat(String.valueOf(datePicker.getMonth())).concat("/")
                        .concat(String.valueOf(datePicker.getYear()));
                event.setDate(date);
                //event.setTime(timePicker.getHour().concat(":").concat(timePicker.getMinute()));
                event.setCategory(category.getSelectedItem().toString());
                String key = mDatabase.push().getKey();
                event.setKey(key);
                mDatabase.child(key).setValue(event);
                showEventDetailsActivity(event);
            }
        });
    }

    private void showEventDetailsActivity(Event event) {
        Intent intent = new Intent(CreateEventActivity.this, EventDetailsActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
        finish();
    }
}
