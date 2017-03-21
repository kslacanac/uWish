package hr.spacecontrol.uwish.objects;

/**
 * Created by Karmela on 17/03/2017.
 */

public class Person {
    String username;
    String password;
    String name;
    String email;
    int image;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int image) {
        this.name = name;
        this.image = image;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
