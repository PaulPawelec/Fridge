package com.example.fridge;

import java.io.Serializable;

public class Products implements Serializable {
    private String id;
    private String name;
    private int quantity;
    private Boolean checked;

    public Products(String id, String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        checked = false;
    }
    public Products(String id, String name){
        this.id = id;
        this.name = name;
        checked = false;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() { return quantity; }

    public Boolean getChecked() { return checked; }

    public void setChecked(Boolean checked) { this.checked = checked; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int increaseQuantity(int quantity){
        System.out.println(name+": "+(this.quantity+quantity));
        return this.quantity+=quantity;
    }

    public int decreaseQuantity(int quantity){
        this.quantity-=quantity;
        if(this.quantity<0) this.quantity = 0;
        System.out.println(name+": "+this.quantity);
        return this.quantity;
    }
}
