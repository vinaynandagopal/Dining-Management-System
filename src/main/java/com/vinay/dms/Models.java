package com.vinay.dms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Models {

}

class Users {
    String userID;
    String name;
    String phone;
    String email;
    int user_type;
    String user_address;

    public Users(String uID, String name, String phone, String email, int user_type, String user_address) {
        this.userID = uID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.user_type = user_type;
        this.user_address = user_address;
    }
}

class Restaurants {
    int restaurantID;
    String restaurant_name;
    String address;
    String phone;

    public Restaurants(int rID, String restaurant_name, String address, String phone) {
        this.restaurantID = rID;
        this.restaurant_name = restaurant_name;
        this.phone = phone;
        this.address = address;
    }
}

class Order {
    String order_id;
    String user_id;
    String restaurant_id;
    float total_amount;
    String order_item;
    String delivery_id;

    public Order(String oID, String uID, String rID, float total_amount, String order_item, String dID) {
        this.order_id = oID;
        this.user_id = uID;
        this.restaurant_id = rID;
        this.total_amount = total_amount;
        this.order_item = order_item;
        this.delivery_id = dID;

    }
}

class Dish implements Serializable {
    public String dish_name;
    public String restaurant_id;
    public String dish_image_url;
    public float price;
    public boolean non_veg;
    public String dish_type;

    public Dish(String dish_name, String rID, String dish_image_url, float price, boolean non_veg, String dish_type) {
        this.dish_name = dish_name;
        this.restaurant_id = rID;
        this.dish_image_url = dish_image_url;
        this.price = price;
        this.non_veg = non_veg;
        this.dish_type = dish_type;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class DishInOrder implements Serializable {
    public String dish_name;
    public float price;
    public int quantity;

    // Getters and setters (optional, but good practice)
    public String getDishName() {
        return dish_name;
    }

    public void setDishName(String dish_name) {
        this.dish_name = dish_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return String.format("%s-%f", this.dish_name, this.price);
    }
}

class Category {
    int category_id;
    String category_name;

    public Category(int cID, String category_name) {
        this.category_id = cID;
        this.category_name = category_name;
    }
}

class Catering_event {
    int event_id;
    int user_id;
    int restaurant_id;
    float event_date;
    int guest_count;
    String event_type;

    public Catering_event(int eID, int uID, int rID, float event_date, int guest_count, String event_type) {
        this.event_id = eID;
        this.user_id = uID;
        this.restaurant_id = rID;
        this.event_date = event_date;
        this.guest_count = guest_count;
        this.event_type = event_type;
    }
}
