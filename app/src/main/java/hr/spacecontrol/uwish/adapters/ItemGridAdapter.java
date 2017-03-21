package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

/**
 * Created by Karmela on 19/03/2017.
 */

public class ItemGridAdapter extends BaseAdapter {

    private Context context;
    private List<Item> itemList;

    private Integer[] itemThumbs = {
            R.drawable.item01,
            R.drawable.item02,
            R.drawable.item03,
            R.drawable.item04,
            R.drawable.item05,
            R.drawable.item06,
            R.drawable.item07
    };

    public ItemGridAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }
    @Override
    public int getCount() {

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

        ImageView imageView = (ImageView) v.findViewById(R.id.item_image);
        imageView.setImageResource(itemList.get(position).getImage());

        TextView itemName = (TextView)v.findViewById(R.id.item_name);
        itemName.setText(itemList.get(position).getName());

        Button deleteBtn = (Button) v.findViewById(R.id.delete_btn);
        Button editBtn = (Button) v.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //brise item iz liste
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //otvara activity za uredivanje itema
                notifyDataSetChanged();
            }
        });

        return v;
    }


}
