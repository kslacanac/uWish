package hr.spacecontrol.uwish.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karmela on 17/03/2017.
 */

public class User implements Serializable {
    String username;
    String password;
    String name;
    String email;
    int image;
    PersonalInfo details;
    List<Item> wishList;

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

    public User(String name, int image) {
        this.name = name;
        this.image = image;

    }

    /* GETTERS AND SETTERS */
    public List<Item> getWishList() {
        return wishList;
    }

    public void setWishList(List<Item> wishList) {
        this.wishList = wishList;
    }

    public PersonalInfo getDetails() {
        return details;
    }

    public void setDetails(PersonalInfo details) {
        this.details = details;
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

    public class PersonalInfo {
        private String clothingSize;
        private String shoeSize;
        private String favouriteColor;
        private String favouriteFood;
        private String sports;
        private String hobbies;
        private String interests;
        private String other;

        public PersonalInfo(){

        }

        public String getClothingSize() {
            return clothingSize;
        }

        public void setClothingSize(String clothingSize) {
            this.clothingSize = clothingSize;
        }

        public String getShoeSize() {
            return shoeSize;
        }

        public void setShoeSize(String shoeSize) {
            this.shoeSize = shoeSize;
        }

        public String getFavouriteFood() {
            return favouriteFood;
        }

        public void setFavouriteFood(String favouriteFood) {
            this.favouriteFood = favouriteFood;
        }

        public String getFavouriteColor() {
            return favouriteColor;
        }

        public void setFavouriteColor(String favouriteColor) {
            this.favouriteColor = favouriteColor;
        }

        public String getSports() {
            return sports;
        }

        public void setSports(String sports) {
            this.sports = sports;
        }

        public String getInterests() {
            return interests;
        }

        public void setInterests(String interests) {
            this.interests = interests;
        }

        public String getHobbies() {
            return hobbies;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}
