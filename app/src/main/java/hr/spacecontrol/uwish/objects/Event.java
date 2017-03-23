package hr.spacecontrol.uwish.objects;

import java.io.Serializable;
import java.util.Date;
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
    List<Person> invitedPeople;

    public Event() {
        //empty constructor
    }

    public Event(String name, String date) {
        this.name = name;
        this.date = date;
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

    public List<Person> getInvitedPeople() {
        return invitedPeople;
    }

    public void setInvitedPeople(List<Person> invitedPeople) {
        this.invitedPeople = invitedPeople;
    }


}
