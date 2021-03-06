package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.User;

/**
 * ?????????????????????????????????????????????
 * Created by tom on 14.05.17..
 */

public class SearchResultAdapter extends BaseAdapter{

    private Context context;
    private List<User> friendList;
    DatabaseReference userDatabase;
    DatabaseReference friendDatabase;
    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;
    User friend;
    User myself;

    public SearchResultAdapter(Context context, List<User> friendList) {
        this.context = context;
        this.friendList = friendList;
    }
    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.listview_addfriend, null);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        friendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myself = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        ImageView imageView = (ImageView) v.findViewById(R.id.friend_image);
        //imageView.setImageResource(friendList.get(position).getImage());
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("Profiles").child(friendList.get(position).getImage());
        Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(ref).into(imageView);

        TextView friendName = (TextView) v.findViewById(R.id.friend_name);
        friendName.setText(friendList.get(position).getName());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend = friendList.get(position);

                userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userDatabase.child(friend.getUID()).child("FriendRequests").child(myself.getUID()).setValue(myself);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                Toast.makeText(v.getContext(), "Friend request sent!", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }


}


