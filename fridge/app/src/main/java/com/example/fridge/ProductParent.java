package com.example.fridge;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

public class ProductParent implements ParentObject {
    private List<Object> mObjectList;
    private UUID id;
    private String productName;

    public ProductParent(String productName) {
        this.productName = productName;
        id=UUID.randomUUID();
    }

    public UUID get_id() {
        return id;
    }

    public void set_id(UUID _id) {
        this.id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    @Override
    public List<Object> getChildObjectList() {
        return mObjectList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mObjectList = list;
    }
}
