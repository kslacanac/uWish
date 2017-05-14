package hr.spacecontrol.uwish.activities;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import hr.spacecontrol.uwish.R;

import static hr.spacecontrol.uwish.R.id.emailField;
import static hr.spacecontrol.uwish.R.id.mailButton;

public class InviteFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());

        Typeface lregular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        Button invite = (Button)findViewById(R.id.button);
        TextView text = (TextView)findViewById(R.id.textView);
        Button mailButton = (Button)findViewById(R.id.mailButton);
        TextView emailField = (TextView)findViewById(R.id.emailField);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String appLinkUrl, previewImageUrl;

                appLinkUrl = "https://fb.me/1801998363461471";
                previewImageUrl = "http://i.imgur.com/33JFFh6.png";

                AppInviteContent content = new AppInviteContent.Builder()
                        .setApplinkUrl(appLinkUrl)
                        .setPreviewImageUrl(previewImageUrl)
                        .build();
                AppInviteDialog.show(InviteFriendsActivity.this, content);


            }
        });

        invite.setTypeface(lregular);
        text.setTypeface(lregular);
        mailButton.setTypeface(lregular);
        emailField.setTypeface(lregular);
    }
}
