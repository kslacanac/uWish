package hr.spacecontrol.uwish.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Karmela on 17/03/2017.
 */

public class User implements Serializable {
    String username;
    String password;
    String name;
    String email;
    String UID;
    String image;
    PersonalInfo details;
    HashMap<String,Item> Wishlist;
    List<User> friendList;
    List<Event> eventList;

    /* CONSTRUCTORS */
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name) {
        this.name = name;
    }


    /* GETTERS AND SETTERS */
    public HashMap<String,Item> getWishlist() {
        return Wishlist;
    }

    public void setWishlist(HashMap<String,Item> Wishlist) {
        this.Wishlist = Wishlist;
    }

    public PersonalInfo getDetails() {
        return details;
    }

    public void setDetails(PersonalInfo details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
