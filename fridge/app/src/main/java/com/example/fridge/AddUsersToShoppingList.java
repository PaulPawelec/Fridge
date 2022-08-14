package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddUsersToShoppingList extends AppCompatActivity implements ListAdapter.SelectedList, SelectedList.DialogListener {
    RecyclerView recyclerview;
    List<ShoppingList> shoppingListsList = new ArrayList<>();
    ListAdapter listAdapter;
    Toast toast;
    int adapterPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_users_to_shopping_list);

        View layout = LayoutInflater.from(this).inflate(R.layout.message_box_error_toast,null);
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        recyclerview = findViewById(R.id.RecycylerviewAddUsersToList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //setShoppingListList(toast);

        String messageToServer = "0309\nq";
        System.out.println(messageToServer);
        AddUsersToShoppingList.MessageSender messageSender = new AddUsersToShoppingList.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listAdapter = new ListAdapter(shoppingListsList, this, new ListAdapter.MyAdapterListener() {
            @Override
            public void itemOnClick(View v, int position) {
                adapterPosition = position;
            }
        });
        recyclerview.setAdapter(listAdapter);
    }

    @Override
    public void selectedList(ShoppingList shoppingList) {
        SelectedList selectedList = new SelectedList();
        selectedList.show(getSupportFragmentManager(),"bla bla");
    }

    @Override
    public void applytext(String userId) {
        if(userId.isEmpty()){
            System.out.println("Brak id");
        }
        else{
            String message="0307\n"+listAdapter.IDshoplist+"\n"+userId+"\nq";
            System.out.println(message);

            AddUsersToShoppingList.MessageSender messageSender = new AddUsersToShoppingList.MessageSender();
            try {
                messageSender.execute(message).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
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
                    System.out.println("Adding user went wrong.");
                }
            } catch (ExecutionException ex) {
                System.out.println("ExecutionException error: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException: " + ex.getMessage());
            }
             */
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
                    shoppingListsList.add(new ShoppingList(serverMessageConcat[2+(2*i)],serverMessageConcat[2+(2*i)+1]));
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
    class MessageSender extends AsyncTask<String,Void,Void> {
        public boolean correctConnection;
        Socket socket;
        DataOutputStream output;
        PrintWriter writer;

        @Override
        protected Void doInBackground(String... voids) {
            System.out.println("Do in background & create server connection");
            AddUsersToShoppingList.Odbjur odbjur = new AddUsersToShoppingList.Odbjur();
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
                    else if (serverMessage.startsWith("0309")) {
                        int n = Integer.parseInt(reader.readLine());
                        for (int i = 0; i < n; i++) {
                            String id = reader.readLine().toString();
                            String name = reader.readLine().toString();

                            ShoppingList shoppingList = new ShoppingList(id, name);
                            shoppingListsList.add(shoppingList);
                        }
                    }
                    else if (serverMessage.startsWith("ok")) {
                        System.out.println("Correct 0307");
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
