package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

/**
 * Created by Karmela on 19/03/2017.
 */

public class ItemGridAdapter extends BaseAdapter {

    public Context context;
    public List<Item> itemList;

    public ItemGridAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @Override
    public int getCount() {
        if(itemList==null) return 0;
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {

        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.gridview_item, null);

        Typeface lregular = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");

        ImageView imageView = (ImageView) v.findViewById(R.id.item_image);

        Item selectedItem = itemList.get(position);

        if (selectedItem.isReceived()) {
            Drawable highlight = v.getResources().getDrawable(R.drawable.border_recieved);
            imageView.setBackground(highlight);
        }

        if (selectedItem.getImageUri() == null || selectedItem.getImageUri().equals("")) {
            if (selectedItem.getImage() != null) {
                StorageReference reference = FirebaseStorage.getInstance().getReference().child("Wishes").child(selectedItem.getImage());
                Glide.with(v.getContext()).using(new FirebaseImageLoader()).load(reference).into(imageView);
            }
        } else {
            Picasso.with(v.getContext()).load(selectedItem.getImageUri()).into(imageView);
        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView itemName = (TextView)v.findViewById(R.id.item_name);
        itemName.setText(itemList.get(position).getName());

        itemName.setTypeface(lregular);

        return v;
    }


}
