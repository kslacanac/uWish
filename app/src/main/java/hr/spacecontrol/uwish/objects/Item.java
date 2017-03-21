package hr.spacecontrol.uwish.objects;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Item {
    String name;
    int image;
    String description;
    String link;

    public Item(String name, int image) {
        this.name = name;
        this.image = image;

    }

    public Item(String name) {
        this.name = name;
    }

    public Item() {
        //empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
