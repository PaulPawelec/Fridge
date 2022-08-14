package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SuggestMeal_View extends AppCompatActivity {
    String namerecipes;
    String category;
    String picturerecipe;
    String preparationtime;
    String ingridients;
    String descrecipe;

    Boolean productinfridge = false;

    public String getPreparationtime() {
        return preparationtime;
    }

    public void setPreparationtime(String preparationtime) {
        this.preparationtime = preparationtime;
    }

    public String getNamerecipes() {
        return namerecipes;
    }

    public void setNamerecipes(String namerecipes) {
        this.namerecipes = namerecipes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicturerecipe() {
        return picturerecipe;
    }

    public void setPicturerecipe(String picturerecipe) {
        this.picturerecipe = picturerecipe;
    }

    public String getIngridients() {
        return ingridients;
    }

    public void setIngridients(String ingridients) {
        this.ingridients = ingridients;
    }

    public String getDescrecipe() {
        return descrecipe;
    }

    public void setDescrecipe(String descrecipe) {
        this.descrecipe = descrecipe;
    }

    private MyCustomAdapter adapter;
    ArrayList<String> ingredients_list = new ArrayList<>();;
    ArrayList<Integer> ingredients_listids = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_meal_view);

        Recipe recipe;
        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("name");

        String messageToServer = "0503\n"+recipe.getIDrecipe()+"\nq";
        //String messageToServer = "0503\n"+"jajo"+"\nq";

        MessageSender messageSender = new MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView suggestMealViewLabel = findViewById(R.id.suggestMealViewLabel);
        suggestMealViewLabel.setText(recipe.getName());

        TextView nameView = findViewById(R.id.nameView);
        nameView.setText(namerecipes);
        //nameView.setText(recipe.getIDrecipe().toString());

        TextView categoryView = findViewById(R.id.categoryView);
        categoryView.setText(category);

        TextView prepView = findViewById(R.id.prepView);
        prepView.setText(preparationtime);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //imageView.setImageResource();

        TextView descView = findViewById(R.id.descView);
        descView.setText(descrecipe);

        ListView list = (ListView) findViewById(R.id.ingredientsList);

        adapter = new MyCustomAdapter(ingredients_list, SuggestMeal_View.this);
        list.setAdapter(adapter);

    }
    public class MyCustomAdapter extends BaseAdapter{
        private ArrayList<String> list;
        private Context context;

        public MyCustomAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
            //just return 0 if your list items do not have an Id variable.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.ingredients_listview, parent,false);
            }

            TextView txtProduct= (TextView)view.findViewById(R.id.txtProduct);
            txtProduct.setText(list.get(position));
            //System.out.println("ID:"+ingredients_listids.get(ingredients_list.indexOf(list.get(position))));

            Button callbtn= (Button)view.findViewById(R.id.btnAction);

            callbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    View layout = LayoutInflater.from(SuggestMeal_View.this).inflate(R.layout.message_box_accept_toast,null);
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);

                    toast.show();

                    String messageToServer = "0304\nNazwa listy\n1\n"+ingredients_listids.get(ingredients_list.indexOf(list.get(position)))+"\n1\nq";
                    MessageSender messageSender = new MessageSender();
                    try {
                        messageSender.execute(messageToServer).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            return view;
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
            Odbjur odbjur = new Odbjur();
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
                    else if(serverMessage.startsWith("0503")){
                        System.out.println("Correct connection");
                        String namerecipes = reader.readLine(); //nazwa

                        String category = reader.readLine(); //kategoria

                        String preptime = reader.readLine(); //czas przygotowania

                        //String picturerecipe = reader.readLine(); //obrazek

                        String descrecipe = reader.readLine(); //opis
                        System.out.println(descrecipe);

                        int n = Integer.parseInt(reader.readLine()); //ilosc skladnikow

                        for (int i = 0; i < n; i++) {
                            String ingridients = reader.readLine(); //skladnik
                            int ingridientsid = Integer.parseInt(reader.readLine());
                            ingredients_list.add(ingridients);
                            ingredients_listids.add(ingridientsid);
                        }

                        setNamerecipes(namerecipes);
                        setCategory(category);
                        setDescrecipe(descrecipe);
                        setPreparationtime(preptime);
                    }else if (serverMessage.startsWith("ok")) {
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
