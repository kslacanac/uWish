package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.User;

import static hr.spacecontrol.uwish.R.id.imageView;

/**
 * Created by Karmela on 21/03/2017.
 */

public class FriendListAdapter extends BaseAdapter {

    private Context context;
    private List<User> friendList;
    private ImageButton acceptButton;
    private ImageButton declineButton;

    public FriendListAdapter(Context context, List<User> friendList) {
        this.context = context;
        this.friendList = friendList;
    }
    @Override
    public int getCount() {
        if(friendList == null) return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.listview_friend, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.friend_image);

        try {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("Profiles").child(friendList.get(position).getImage());
            Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(reference).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        TextView friendName = (TextView) v.findViewById(R.id.friend_name);
        friendName.setText(friendList.get(position).getName());

        ImageButton acceptButton = (ImageButton) v.findViewById(R.id.acceptButton);
        acceptButton.setVisibility(View.INVISIBLE);
        ImageButton declineButton = (ImageButton) v.findViewById(R.id.declineButton);
        declineButton.setVisibility(View.INVISIBLE);
        return v;
    }
}
