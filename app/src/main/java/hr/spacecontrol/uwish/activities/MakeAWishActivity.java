package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import hr.spacecontrol.uwish.R;

public class MakeAWishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_wish);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        EditText title = (EditText)findViewById(R.id.title);
        EditText description = (EditText)findViewById(R.id.description);
        EditText whereToBuy = (EditText)findViewById(R.id.wheretobuy);
        Button makeWishBtn = (Button)findViewById(R.id.make_wish_btn);
    }
}
