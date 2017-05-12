package hr.spacecontrol.uwish.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import hr.spacecontrol.uwish.R;
import hr.spacecontrol.uwish.objects.Item;

/**
 * Created by Karmela on 30/03/2017.
 */

public class MyWishAdapter extends ItemGridAdapter {

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        ImageButton deleteBtn = (ImageButton) v.findViewById(R.id.delete_btn);
        //  Button editBtn = (Button) v.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //brise item iz liste
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });

     /*   editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //otvara activity za uredivanje itema
                notifyDataSetChanged();
            }
        });*/

        return v;
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

    public MyWishAdapter(Context context, List<Item> itemList) {
        super(context, itemList);
    }


}
