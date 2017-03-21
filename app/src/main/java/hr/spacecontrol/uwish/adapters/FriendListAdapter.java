package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Person;

/**
 * Created by Karmela on 21/03/2017.
 */

public class FriendListAdapter extends BaseAdapter {

    private Context context;
    private List<Person> friendList;

    public FriendListAdapter(Context context, List<Person> friendList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.listview_friend, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.friend_image);
        imageView.setImageResource(friendList.get(position).getImage());

        TextView friendName = (TextView) v.findViewById(R.id.friend_name);
        friendName.setText(friendList.get(position).getName());

        return v;
    }
}
