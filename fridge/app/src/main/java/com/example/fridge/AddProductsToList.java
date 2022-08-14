package com.example.fridge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddProductsToList extends AppCompatActivity implements ProductsAdapter.SelectedProduct {
    RecyclerView recyclerview;
    FloatingActionButton acceptButton;
    List<Products> productsList = new ArrayList<>();
    HashSet<Integer> productsToAdd = new HashSet<>();
    ProductsAdapter productsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_to_list);

        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_error_toast, null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        
        recyclerview = findViewById(R.id.RecyclerviewAddProductsToList);
        acceptButton = findViewById(R.id.AcceptFloatingButtonAddProducts);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptListActivity(toast);
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        String categoryId="";
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Categories product = (Categories) intent.getSerializableExtra("data");
            categoryId = product.getName()+"\n";
        }
        //setProductsList(categoryId, toast);

        String messageToServer = "0803\n"+categoryId+"\nq";
        System.out.println(messageToServer);
        AddProductsToList.MessageSender messageSender = new AddProductsToList.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        productsAdapter = new ProductsAdapter(productsList,this, new ProductsAdapter.MyAdapterListener() {
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

    private void changeQuantity(int number) {
        productsToAdd.add(number);
    }

    private void acceptListActivity(Toast toast) {
        String message = "0304\nNazwa listy\n"+productsToAdd.size()+"\n";
        for (int i : productsToAdd) {
            if (productsList.get(i).getQuantity() > 0) {
                message += productsList.get(i).getId() + "\n" + productsList.get(i).getQuantity() + "\n";
            }
        }
        System.out.println(message);
        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_accept_toast,null);
        final Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast1.setDuration(Toast.LENGTH_SHORT);
        toast1.setView(layout);

        toast1.show();
        AddProductsToList.MessageSender messageSender = new AddProductsToList.MessageSender();
        try {
            messageSender.execute(message).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

        /*
        String message = "0304\nNazwa listy\n"+productsToAdd.size()+"\n";
        for (int i : productsToAdd) {
            if (productsList.get(i).getQuantity() > 0) {
                message += productsList.get(i).getName() + "\n" + productsList.get(i).getQuantity() + "\n";
            }
        }
        System.out.println(message);
        AsyncConnection connection = new AsyncConnection();
        try {
            connection.execute(message).get();
            if (connection.correctConnection) {
                //wyświetl komunikat o powodzeniu
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            } else {
                //wyświetl komunikat o niepowodzeniu
                toast.show();
                System.out.println("Logging went wrong.");
            }
        } catch (ExecutionException ex) {
            System.out.println("ExecutionException error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException: " + ex.getMessage());
        }
         */
    }

    void setProductsList(String category, Toast toast){
        String message="0803\n"+category+"\n";
        System.out.println(message);
        AsyncConnection connection = new AsyncConnection();
        try {
            connection.execute(message).get();
            if(connection.correctConnection){
                //wyświetl komunikat o powodzeniu
                String [] serverMessageConcat = connection.getServerMessage().split("\n");
                int cat_num = Integer.valueOf(serverMessageConcat[1]);
                for(int i=0;i<cat_num;i++){
                   productsList.add(new Products(serverMessageConcat[2+(3*i)],serverMessageConcat[2+(3*i)+1], Integer.valueOf(serverMessageConcat[2+(3*i)+2])));
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
    public void selectedProducts(Products product) {
        startActivity(new Intent(this, SelectedProduct.class).putExtra("data",product));
    }
    class MessageSender extends AsyncTask<String,Void,Void> {
        public boolean correctConnection;
        Socket socket;
        DataOutputStream output;
        PrintWriter writer;

        @Override
        protected Void doInBackground(String... voids) {
            System.out.println("Do in background & create server connection");
            AddProductsToList.Odbjur odbjur = new AddProductsToList.Odbjur();
            correctConnection = odbjur.serverRequest(voids[0]);

            return null;
        }
    }

    class Odbjur {
        public boolean serverRequest(String request) {
            int port;
            port = 50080;
            String hostname = "192.168.1.2";

            try (Socket socket = new Socket(hostname, port)) {

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                if(!LoginSessions.getInstance().session.equals("")){
                    writer.println("session"+LoginSessions.getInstance().session);
                }else{
                    writer.println("nosession");
                }

                writer.println(request);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String serverMessage = reader.readLine();

                if (serverMessage.equals("null"))
                    System.out.println("Connection with the server lost");
                else {
                    System.out.println(serverMessage);
                    if (serverMessage.startsWith("-1"))
                        System.out.println("Correct connection & wrong data");
                    else if (serverMessage.startsWith("0803")) {
                        int n = Integer.parseInt(reader.readLine());

                        for (int i = 0; i < n; i++) {
                            String id = reader.readLine().toString();
                            String name = reader.readLine();
                            int quant = Integer.parseInt(reader.readLine());
                            Products products = new Products(id, name);
                            productsList.add(products);
                        }
                    }
                    else if (serverMessage.startsWith("ok")) {
                        System.out.println("Correct 0304");
                    }

                }
            } catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
            }
            return false;
        }
    }

}
