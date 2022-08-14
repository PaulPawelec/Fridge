package com.example.fridge;

import android.widget.ImageView;

import java.io.Serializable;

public class Categories implements Serializable {

    private String name;

    public Categories(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
