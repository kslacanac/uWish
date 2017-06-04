package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.User;

import static hr.spacecontrol.uwish.R.id.imageView;

/**
 * Created by Karmela on 21/03/2017.
 */

public class AddFriendAdapter extends BaseAdapter {

    private Context context;
    private List<User> friendList;
    private ImageButton addButton;
    private FirebaseUser firebaseUser;
    private DatabaseReference myselfDB;
    private DatabaseReference usersDB;
    private User myself;


    public AddFriendAdapter(Context context, List<User> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        if (friendList == null) return 0;
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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myselfDB = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("image");
        usersDB = FirebaseDatabase.getInstance().getReference().child("Users");

        getMyself();

        View v = View.inflate(context, R.layout.listview_addfriend, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.friend_image);

        try {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("Profiles").child(friendList.get(position).getImage());
            Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(reference).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        TextView friendName = (TextView) v.findViewById(R.id.friend_name);
        friendName.setText(friendList.get(position).getName());

        ImageButton addButton = (ImageButton) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDB.child(friendList.get(position).getUID()).child("FriendRequests").child(myself.getUID()).setValue(myself);
                Toast.makeText(v.getContext(), "You've sent a friend request", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    public void getMyself(){
        myself = new User();
        myself.setUID(firebaseUser.getUid());
        myself.setEmail(firebaseUser.getEmail());
        myself.setName(firebaseUser.getDisplayName());

        myselfDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myself.setImage(dataSnapshot.getValue().toString());
                notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
