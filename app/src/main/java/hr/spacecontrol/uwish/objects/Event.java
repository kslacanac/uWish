package hr.spacecontrol.uwish.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Event implements Serializable{
    String name;
    String date;
    String time;
    String description;
    String location;
    String category;
    String username; //of the person who created the event
    List<User> invitedPeople;

    public Event() {
        //empty constructor
    }

    public Event(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<User> getInvitedPeople() {
        return invitedPeople;
    }

    public void setInvitedPeople(List<User> invitedPeople) {
        this.invitedPeople = invitedPeople;
    }


}
