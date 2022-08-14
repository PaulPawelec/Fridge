package com.example.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class Menu extends AppCompatActivity implements AddShoppingList.DialogListener{
    String userId;
    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button manageShoppingList = findViewById(R.id.manageListButton);
        Button manageFridge = findViewById(R.id.manageFridgeButton);
        Button manageDiet = findViewById(R.id.manageDietButton);
        Button suggestMeal = findViewById(R.id.suggestMealButton);
        Button addProductToList = findViewById(R.id.addProductToListButton);
        Button markProductAsBought = findViewById(R.id.markAsBoughtButton);
        Button addUsersToList = findViewById(R.id.addUsersToListButton);
        Button addList = findViewById(R.id.addListButton);
        Button removeList = findViewById(R.id.removeListButton);
        Button addProductToFridge = findViewById(R.id.addProductsToFridgeButton);//addcategoriesTofridge
        Button removeProductsFromFridge = findViewById(R.id.removeProductFromFridgeButton);
        Button customiseMeals = findViewById(R.id.customiseMealsInDietButton);
        //Button customiseDietType = findViewById(R.id.customiseDietTypeButton);
        Button addDiet = findViewById(R.id.addDietButton);
        Button removeDiet = findViewById(R.id.removeDietButton);

        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_error_toast,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        manageShoppingList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                manageShoppingListActivity();
            }
        });
        manageFridge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                manageFridgeActivity();
            }
        });
        manageDiet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                manageDietActivity();
            }
        });
        suggestMeal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                suggestMealActivity();
            }
        });
        addProductToList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addProductToListActivity();
            }
        });
        markProductAsBought.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                markProductAsBoughtActivity();
            }
        });
        addUsersToList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addUsersToListActivity();
            }
        });
        addList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addListActivity();
            }
        });
        removeList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeListActivity();
            }
        });
        addProductToFridge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addProductToFridgeActivity();
            }
        });
        removeProductsFromFridge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeProductsFromFridgeActivity();
            }
        });
        customiseMeals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                customiseMealsActivity();
            }
        });
        /*
        customiseDietType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                customiseDietTypeActivity();
            }
        });
         */
        addDiet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addDietActivity();
            }
        });
        removeDiet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeDietActivity();
            }
        });
    }

    public void manageShoppingListActivity(){
        TextView labelView = findViewById(R.id.shoppingListLabel);
        ScrollView scrollView = findViewById(R.id.menuScrollView);

        scrollView.smoothScrollTo(0, labelView.getTop());
    }
    public void manageFridgeActivity(){
        TextView labelView = findViewById(R.id.fridgeLabel);
        ScrollView scrollView = findViewById(R.id.menuScrollView);

        scrollView.smoothScrollTo(0, labelView.getTop());
    }
    public void manageDietActivity(){
        TextView labelView = findViewById(R.id.dietsLabel);
        ScrollView scrollView = findViewById(R.id.menuScrollView);

        scrollView.smoothScrollTo(0, labelView.getTop());
    }
    public void suggestMealActivity(){
        Intent intent = new Intent(this, SuggestMeal.class);
        startActivity(intent);
    }
    public void addProductToListActivity(){
        Intent intent = new Intent(this, AddCategoriesToList.class);
        startActivity(intent);
    }
    public void markProductAsBoughtActivity(){
        Intent intent = new Intent(this, MarkProductAsBought.class);
        startActivity(intent);
    }
    public void addUsersToListActivity(){
        Intent intent = new Intent(this, AddUsersToShoppingList.class);
        startActivity(intent);
    }
    public void addListActivity(){
        AddShoppingList dialog = new AddShoppingList();
        dialog.show(getSupportFragmentManager(),"bla bla");
    }
    public void removeListActivity(){
        Intent intent = new Intent(this, RemoveShoppingList.class);
        startActivity(intent);
    }
    public void addProductToFridgeActivity(){
        Intent intent = new Intent(this, AddProductToFridge.class);//Categories
        startActivity(intent);
    }
    public void removeProductsFromFridgeActivity(){
        Intent intent = new Intent(this, RemoveProductsFromFridge.class);
        startActivity(intent);
    }
    public void customiseMealsActivity(){
        Intent intent = new Intent(this, Diet.class);
        startActivity(intent);
    }
    public void customiseDietTypeActivity(){
        Intent intent = new Intent(this, CustomiseDietTypes.class);
        startActivity(intent);
    }
    public void addDietActivity(){
        Intent intent = new Intent(this, Diet_New.class);
        startActivity(intent);
    }
    public void removeDietActivity(){
        Intent intent = new Intent(this, Diet_Remove.class);
        startActivity(intent);
    }

    @Override
    public void applyTextAddShoppingList(String listName) {
        String message = "0300\n";
        if (listName.isEmpty()) {
            System.out.println("Brak id");
        } else {
            message = message + "Nazwa Listy\n";
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
                System.out.println("Adding shopping list went wrong.");
            }
        } catch (ExecutionException ex) {
            System.out.println("ExecutionException error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException: " + ex.getMessage());
        }
        }
    }
}
