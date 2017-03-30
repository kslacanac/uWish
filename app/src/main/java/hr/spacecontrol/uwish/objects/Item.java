package hr.spacecontrol.uwish.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Item implements Serializable {
    String name;
    int image;
    String description;
    String link;
    List<String> tags;

    public Item(String name, int image) {
        this.name = name;
        this.image = image;

    }

    public Item(String name, int image, String description, String link) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.link = link;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
