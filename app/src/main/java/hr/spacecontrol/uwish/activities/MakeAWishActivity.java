package hr.spacecontrol.uwish.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

public class MakeAWishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText title;
    private EditText description;
    private EditText whereToBuy;
    private Button makeWishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_wish);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        title = (EditText)findViewById(R.id.title);
        description = (EditText)findViewById(R.id.description);
        whereToBuy = (EditText)findViewById(R.id.wheretobuy);
        makeWishBtn = (Button)findViewById(R.id.make_wish_btn);

        // TODO uploading image

        makeWishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setName(title.getText().toString());
                item.setDescription(description.getText().toString());
                item.setLink(whereToBuy.getText().toString());
                // TODO set image
                // TODO send item to firebase
            }
        });
    }
}
