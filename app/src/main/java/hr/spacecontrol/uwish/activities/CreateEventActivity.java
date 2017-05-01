package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import hr.spacecontrol.uwish.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        EditText title = (EditText)findViewById(R.id.make_event_title);
        EditText description = (EditText)findViewById(R.id.make_event_description);
        DatePicker datePicker = (DatePicker)findViewById(R.id.make_event_datepicker);
        Spinner category = (Spinner)findViewById(R.id.make_event_category_spinner);
        Button createBtn = (Button)findViewById(R.id.create_event_btn);

    }
}
