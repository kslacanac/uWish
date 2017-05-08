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

import java.util.Date;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Event;

public class CreateEventActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private DatePicker datePicker;
    private Spinner category;
    private Button createBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

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
                event.setDate(datePicker.toString());
                event.setCategory(category.toString());
                // TODO add event to firebase
            }
        });

    }
}
