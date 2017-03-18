package hr.spacecontrol.uwish.objects;

import java.util.Date;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Event {
    String name;
    String date;

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
}
