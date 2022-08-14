package com.example.fridge;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

public class ProductChildViewHolder extends ChildViewHolder {
    Button add;
    Button remove;
    EditText note;
    TextView quantity;
    public ProductChildViewHolder(View itemView) {
        super(itemView);
        add = itemView.findViewById(R.id.PlusProductButton);
        remove = itemView.findViewById(R.id.MinusProductButton);
        note = itemView.findViewById(R.id.Notetext);
        quantity = itemView.findViewById(R.id.ProductQuantityText);
    }
}
