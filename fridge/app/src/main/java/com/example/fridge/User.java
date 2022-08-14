package com.example.fridge;

public class User {
    private String name;
    private String id;
    private ShoppingList shoppingListId;

    public User(String id, String name) {
        this.name = name;
        this.id = id;
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

    public ShoppingList getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(ShoppingList shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}
