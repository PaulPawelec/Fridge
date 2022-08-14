package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RemoveShoppingList extends AppCompatActivity implements RemoveShoppingListAdapter.SelectedList {
    RecyclerView recyclerview;
    FloatingActionButton acceptButton;

    List<ShoppingList> shoppingListList = new ArrayList<>();
    HashSet<Integer> listsToRemove = new HashSet<>();
    List<User> userList = new ArrayList<>();

    RemoveShoppingListAdapter removeListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_shopping_list);

        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_error_toast,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        recyclerview = findViewById(R.id.RecyclerviewRemoveList);
        acceptButton = findViewById(R.id.AcceptFloatingButtonRemoveList);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptListActivity(toast);
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        setShoppingListList(toast);

        removeListAdapter = new RemoveShoppingListAdapter(shoppingListList, this, new RemoveShoppingListAdapter.MyAdapterListener() {
            @Override
            public void switchOnClick(View v, int position) {
                if(listsToRemove.contains(position))
                    listsToRemove.remove(position);
                else
                    listsToRemove.add(position);
            }
        });
        recyclerview.setAdapter(removeListAdapter);
    }

    private void acceptListActivity(Toast toast) {
        if(listsToRemove.isEmpty()){
            toast.show();
            return;
        }
        String message = "0301\n"+listsToRemove.size()+"\n";
        for (int i : listsToRemove)
            message = message + shoppingListList.get(i).getId() + "\n";
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

    void setShoppingListList(Toast toast){
        String message="0309\n";
        System.out.println(message);
        AsyncConnection connection = new AsyncConnection();
        try {
            connection.execute(message).get();
            if(connection.correctConnection){
                //wyświetl komunikat o powodzeniu
                String [] serverMessageConcat = connection.getServerMessage().split("\n");
                int cat_num = Integer.valueOf(serverMessageConcat[1]);
                for(int i=0;i<cat_num;i++){
                    shoppingListList.add(new ShoppingList(serverMessageConcat[2+(2*i)],serverMessageConcat[2+(2*i)+1]));
                }
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
    public void selectedList(ShoppingList shoppingList) {
        startActivity(new Intent(RemoveShoppingList.this, SelectedProduct.class).putExtra("data", shoppingList));
    }
}