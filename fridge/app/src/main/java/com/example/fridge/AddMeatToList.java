package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

public class AddMeatToList extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meat_to_list);

        recyclerView = findViewById(R.id.RecyclerviewAddMeatToList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductExpandAdapter adapter = new ProductExpandAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);
    }

    private List<ParentObject> initData(){
        ProductCreator productCreator = ProductCreator.get(this);
        List<ProductParent> products  = productCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        for(ProductParent parent:products){
            List<Object> childList = new ArrayList<>();
            childList.add(new ProductChild("option1","option2"));
            parent.setChildObjectList(childList);
            parentObjects.add(parent);
        }
        return parentObjects;
    }
}
