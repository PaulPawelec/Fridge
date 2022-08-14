package com.example.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedProduct extends AppCompatActivity {
    private Products product;
    private TextView productName;

    public SelectedProduct(Products products){
        this.product = products;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);

        productName = findViewById(R.id.selectedProduct);
        Intent intent = getIntent();

        if(intent.getExtras() != null){
            Products product = (Products) intent.getSerializableExtra("data");
            productName.setText(product.getName());
        }
    }
}
