package com.example.fridge;

import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {
    private String id;
    private String name;
    private List<User> usersList;

    public ShoppingList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
