package hr.spacecontrol.uwish.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import hr.spacecontrol.uwish.R;

public class InviteFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_invite_friends);
        Button invite = (Button)findViewById(R.id.button);

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
    }
}
