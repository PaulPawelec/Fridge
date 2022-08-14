package com.example.fridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class ProductExpandAdapter extends ExpandableRecyclerAdapter<ProductParentViewHolder, ProductChildViewHolder> {
    LayoutInflater inflater;

    public ProductExpandAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ProductParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.row_products_parent,viewGroup,false);
        return new ProductParentViewHolder(view);
    }

    @Override
    public ProductChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.row_products_child,viewGroup,false);
        return new ProductChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ProductParentViewHolder productParentViewHolder, int i, Object o) {
        ProductParent productParent = (ProductParent)o;
        productParentViewHolder.productName.setText(productParent.getProductName());
    }

    @Override
    public void onBindChildViewHolder(ProductChildViewHolder productChildViewHolder, int i, Object o) {
        ProductChild productChild = (ProductChild)o;
        productChildViewHolder.quantity.setText(0);
    }
}
