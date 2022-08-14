package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddProductToList extends AppCompatActivity implements ProductsAdapter.SelectedProduct {
    RecyclerView recyclerview;
    FloatingActionButton acceptButton;

    List<Products> productsList = new ArrayList<>();
    HashSet<Integer> productsToAdd = new HashSet<>();

    String[] products = {"Chicken", "Pork", "Bacon", "Fish", "Lamb", "Ham"};

    ProductsAdapter productsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_to_list);

        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_error_toast,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        recyclerview = findViewById(R.id.RecyclerviewAddProductsToList);
        acceptButton = findViewById(R.id.AcceptFloatingButtonAddProducts);
        acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                acceptListActivity(toast);
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for (String s : products) {
            Products product = new Products(s);
            productsList.add(product);
        }

        productsAdapter = new ProductsAdapter(productsList, this, new ProductsAdapter.MyAdapterListener() {
            @Override
            public void plusOnClick(View v, int position) {
                productsList.get(position).increaseQuantity(1);
                changeQuantity(position);
                TextView textView = findViewById(R.id.ProductQuantityText);
                textView.setText(String.valueOf(productsList.get(position).getQuantity()));
            }

            @Override
            public void minusOnClick(View v, int position) {
                productsList.get(position).decreaseQuantity(1);
                TextView textView = findViewById(R.id.ProductQuantityText);
                textView.setText(String.valueOf(productsList.get(position).getQuantity()));
            }
        });
        recyclerview.setAdapter(productsAdapter);
    }

    private void changeQuantity(int number){
        productsToAdd.add(number);
    }
    private void acceptListActivity(Toast toast) {
        String message="0304\n";
        for(int i:productsToAdd){
            if(productsList.get(i).getQuantity()>0){
                message+=productsList.get(i).getName()+productsList.get(i).getQuantity()+" ";
            }
        }
        System.out.println(message);
        AsyncConnection connection = new AsyncConnection();
        try {
            connection.execute(message).get();
            if(connection.correctConnection){
            //wyświetl komunikat o powodzeniu
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }
            else{
                //wyświetl komunikat o niepowodzeniu
                toast.show();
                System.out.println("Logging went wrong.");
            }
        } catch (ExecutionException ex) {
            System.out.println("ExecutionException error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException: " + ex.getMessage());
        }
    }

    @Override
    public void selectedProducts(Products product) {
        startActivity(new Intent(AddProductToList.this, SelectedProduct.class).putExtra("data", product));
    }
}