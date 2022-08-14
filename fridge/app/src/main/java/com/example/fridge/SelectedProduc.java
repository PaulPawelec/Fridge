package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedProduc extends AppCompatActivity {

    TextView productName;
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
