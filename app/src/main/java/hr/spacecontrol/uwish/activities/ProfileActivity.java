package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import hr.spacecontrol.uwish.R;

public class ProfileActivity extends AppCompatActivity {

    /* razmisliti o dinamickom dodavanju atributa koji bi pisali na profilu
    * npr. netko zeli istaknuti da ima macku, a to nije default opcija kod uredivanja profila
    * zato dodati opciju da korisnik moze sam izmisliti neki atribut*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();


    }
}
