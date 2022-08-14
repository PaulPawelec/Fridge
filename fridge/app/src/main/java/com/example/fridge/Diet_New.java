package com.example.fridge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.concurrent.ExecutionException;

public class Diet_New extends AppCompatActivity {
    String [] category ={"general", "vegan", "vegetarian", "meat", "pescetarian"};
    ArrayList<String> recipesnames = new ArrayList<>();
    ArrayList<String> recipesid = new ArrayList<>();

    EditText namediet, descdiet;
    Spinner categorySpinner;
    Spinner recipe1, recipe2, recipe3, recipe4, recipe5, recipe6, recipe7, recipe8, recipe9, recipe10, recipe11, recipe12, recipe13, recipe14, recipe15, recipe16, recipe17, recipe18, recipe19, recipe20, recipe21;
    Button acceptbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_new);

        String messageToServer = "0509\n"+"recipes"+"\nq";

        Diet_New.MessageSender messageSender = new Diet_New.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        namediet = findViewById(R.id.namediet);
        descdiet = findViewById(R.id.descdiet);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adaptercat = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, category);
        categorySpinner.setAdapter(adaptercat);
        //categorySpinner.setEnabled(false);

        //AutoCompleteTextView recipe1 = findViewById(R.id.recipe1);
        recipe1 = findViewById(R.id.recipe1);
        ArrayAdapter<String> adapterrecipes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recipesnames);
        recipe1.setAdapter(adapterrecipes);

        recipe2 = findViewById(R.id.recipe2);
        recipe2.setAdapter(adapterrecipes);

        recipe3 = findViewById(R.id.recipe3);
        recipe3.setAdapter(adapterrecipes);

        recipe4 = findViewById(R.id.recipe4);
        recipe4.setAdapter(adapterrecipes);

        recipe5 = findViewById(R.id.recipe5);
        recipe5.setAdapter(adapterrecipes);

        recipe6 = findViewById(R.id.recipe6);
        recipe6.setAdapter(adapterrecipes);

        recipe7 = findViewById(R.id.recipe7);
        recipe7.setAdapter(adapterrecipes);

        recipe8 = findViewById(R.id.recipe8);
        recipe8.setAdapter(adapterrecipes);

        recipe9 = findViewById(R.id.recipe9);
        recipe9.setAdapter(adapterrecipes);

        recipe10 = findViewById(R.id.recipe10);
        recipe10.setAdapter(adapterrecipes);

        recipe11 = findViewById(R.id.recipe11);
        recipe11.setAdapter(adapterrecipes);

        recipe12 = findViewById(R.id.recipe12);
        recipe12.setAdapter(adapterrecipes);

        recipe13 = findViewById(R.id.recipe13);
        recipe13.setAdapter(adapterrecipes);

        recipe14 = findViewById(R.id.recipe14);
        recipe14.setAdapter(adapterrecipes);

        recipe15 = findViewById(R.id.recipe15);
        recipe15.setAdapter(adapterrecipes);

        recipe16 = findViewById(R.id.recipe16);
        recipe16.setAdapter(adapterrecipes);

        recipe17 = findViewById(R.id.recipe17);
        recipe17.setAdapter(adapterrecipes);

        recipe18 = findViewById(R.id.recipe18);
        recipe18.setAdapter(adapterrecipes);

        recipe19 = findViewById(R.id.recipe19);
        recipe19.setAdapter(adapterrecipes);

        recipe20 = findViewById(R.id.recipe20);
        recipe20.setAdapter(adapterrecipes);

        recipe21 = findViewById(R.id.recipe21);
        recipe21.setAdapter(adapterrecipes);

        acceptbutton = findViewById(R.id.acceptbutton);
        acceptbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = LayoutInflater.from(Diet_New.this).inflate(R.layout.message_box_accept_toast,null);
                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);

                toast.show();
                String messageToServer = "0600\n"+namediet.getText().toString()+"\n"+categorySpinner.getSelectedItem().toString()+"\n"+descdiet.getText().toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe1.getSelectedItem().toString())).toString() + "\n"+recipesid.get(recipesnames.indexOf(recipe2.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe3.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe4.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe5.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe6.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe7.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe8.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe9.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe10.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe11.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe12.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe13.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe14.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe15.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe16.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe17.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe18.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe19.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe20.getSelectedItem().toString())).toString()+"\n"+recipesid.get(recipesnames.indexOf(recipe21.getSelectedItem().toString())).toString()+"\nq";

                Diet_New.MessageSender messageSender = new Diet_New.MessageSender();
                try {
                    messageSender.execute(messageToServer).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Diet_New.this, Menu.class);
                startActivity(intent);
            }
        });
    }
    class MessageSender extends AsyncTask<String,Void,Void> {
        public boolean correctConnection;
        Socket socket;
        DataOutputStream output;
        PrintWriter writer;

        @Override
        protected Void doInBackground(String... voids) {
            System.out.println("Do in background & create server connection");
            Diet_New.Odbjur odbjur = new Diet_New.Odbjur();
            correctConnection = odbjur.serverRequest(voids[0]);

            return null;
        }
    }

    class Odbjur{
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

                if(serverMessage.equals("null")) System.out.println("Connection with the server lost");
                else{
                    System.out.println(serverMessage);
                    if(serverMessage.startsWith("-1"))
                        System.out.println("Correct connection & wrong data");
                    else if(serverMessage.startsWith("0509")){
                        int n = Integer.parseInt(reader.readLine());
                        for (int i = 0; i < n; i++) {
                            String id = reader.readLine();
                            String name = reader.readLine();
                            String category = reader.readLine();

                            recipesid.add(id);
                            recipesnames.add(name);
                        }
                    }
                    else if(serverMessage.startsWith("0600")){
                        System.out.println("Correct 0600");
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
