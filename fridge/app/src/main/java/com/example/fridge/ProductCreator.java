package com.example.fridge;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ProductCreator {
    static ProductCreator productCreator;
    List<ProductParent> productParentsList;

    public ProductCreator(Context context){
        productParentsList = new ArrayList<>();

        for(int i=0;i<3;i++) {
            ProductParent product = new ProductParent(String.format("Nr#%d",i));
            productParentsList.add(product);
        }
    }

    public static ProductCreator get(Context context){
        if(productCreator == null)  productCreator = new ProductCreator(context);
        return productCreator;
    }

    public List<ProductParent> getAll() {
        return productParentsList;
    }
}
