package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

/**
 * Created by Karmela on 30/03/2017.
 */

public class FriendWishAdapter extends ItemGridAdapter {

    public FriendWishAdapter(Context context, List<Item> itemList) {
        super(context, itemList);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.gridview_friend_wish, null);

        Typeface lregular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");

        ImageView imageView = (ImageView) v.findViewById(R.id.item_image);
        Glide.with(v.getContext()).load(itemList.get(position).getImage()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView itemName = (TextView)v.findViewById(R.id.item_name);
        itemName.setText(itemList.get(position).getName());

        itemName.setTypeface(lregular);
        return v;
    }
}
