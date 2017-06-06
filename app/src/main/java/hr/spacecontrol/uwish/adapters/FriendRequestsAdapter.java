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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.fragments.FriendRequests;
import hr.spacecontrol.uwish.objects.User;

/**
 * Created by tom on 13.05.17..
 */

public class FriendRequestsAdapter extends BaseAdapter{

    private Context context;
    private List<User> friendList;
   // private ImageButton acceptButton;
   // private ImageButton declineButton;
    DatabaseReference mDatabase;
    DatabaseReference fDatabase;
    DatabaseReference myselfDB;
    FirebaseUser firebaseUser;
    User friend;
    User myself;

    public FriendRequestsAdapter(Context context, List<User> friendList) {
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

        View v = View.inflate(context, R.layout.listview_friend, null);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid())
                .child("FriendRequests");
        fDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        myselfDB = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("image");

        ImageView imageView = (ImageView) v.findViewById(R.id.friend_image);
        //imageView.setImageResource(friendList.get(position).getImage());
        try {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("Profiles").child(friendList.get(position).getImage());
            Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(ref).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView friendName = (TextView) v.findViewById(R.id.friend_name);
        friendName.setText(friendList.get(position).getName());

        ImageButton declineButton = (ImageButton) v.findViewById(R.id.declineButton);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friendList.get(position).getUID();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabase.child(friendList.get(position).getUID()).removeValue();
                        friendList.remove(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                Toast.makeText(v.getContext(), "You declined the friend request", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton acceptButton = (ImageButton) v.findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend = friendList.get(position);
                myself = new User();
                myself.setUID(firebaseUser.getUid());
                myself.setEmail(firebaseUser.getEmail());
                myself.setName(firebaseUser.getDisplayName());
                myselfDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            myself.setImage(dataSnapshot.getValue().toString());
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });


                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
              //          User friend = null;
                //        try {
                 //           friend = friendList.get(position);
                  //      } catch (Exception e) {
                            //e.printStackTrace();
                   //     }
                        fDatabase.child(firebaseUser.getUid()).child(friend.getUID()).setValue(friend);
                        fDatabase.child(friend.getUID()).child(firebaseUser.getUid()).setValue(myself);
                        mDatabase.child(friend.getUID()).removeValue();
                        friendList.remove(position);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                Toast.makeText(v.getContext(), "You accepted the friend request", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }


}
