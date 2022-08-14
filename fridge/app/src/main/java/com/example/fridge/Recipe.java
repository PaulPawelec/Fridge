package com.example.fridge;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Recipe implements Serializable {
    Integer IDrecipe;
    String name;
    String category;

    public Recipe(Integer IDrecipe, String name, String category) {
        this.IDrecipe = IDrecipe;
        this.name = name;
        this.category = category;
    }

    public Recipe(String name, String category) {
        this.name = name;
        this.category = category;
    }
    public Recipe(){

    }

    public Integer getIDrecipe() {
        return IDrecipe;
    }

    public void setIDrecipe(Integer IDrecipe) {
        this.IDrecipe = IDrecipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
