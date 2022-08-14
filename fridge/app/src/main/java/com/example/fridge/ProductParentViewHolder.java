package com.example.fridge;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

public class ProductParentViewHolder extends ParentViewHolder {
    TextView productName;
    Button expandOptions;
    public ProductParentViewHolder(View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.ProductNameRecyclerviewAddProductsToList);
        expandOptions = itemView.findViewById(R.id.ExpandProductOptionsParentButton);
    }
}
