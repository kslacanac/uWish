package hr.spacecontrol.uwish.objects;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Item implements Serializable {
    String name;
    String image;
    String imageUri;
    String description;
    String link;
    String groups;
    //List<String> tags;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String image, String description, String link) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.link = link;
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

    public String getImage() {
        return image;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setImage(String image) {
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

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }
}
