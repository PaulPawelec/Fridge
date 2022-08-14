package com.example.fridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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

public class SuggestMeal extends AppCompatActivity {

    private static final String TAG = "";

    ArrayList<Recipe> recipesnames = new ArrayList<>();
    ListView list;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_meal);

        list = (ListView) findViewById(R.id.theList);
        SearchView Filter = (SearchView) findViewById(R.id.searchFilter);
        Log.d(TAG, "onCreate: Started.");

        String messageToServer = "0509\n"+"recipelist"+"\nq";

        SuggestMeal.MessageSender messageSender = new SuggestMeal.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //fillData();

        customAdapter = new CustomAdapter(recipesnames, this);

        list.setAdapter(customAdapter);

        Filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }
    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<Recipe> recipesModelList;
        private List<Recipe> recipesModelListFiltered;
        private Context context;

        public CustomAdapter(List<Recipe> recipesModelList, Context context) {
            this.recipesModelList = recipesModelList;
            this.recipesModelListFiltered = recipesModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return recipesModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return recipesModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items, null);

            TextView itemName = view.findViewById(R.id.itemName);
            TextView itemCategory = view.findViewById(R.id.itemCategory);

            itemName.setText(recipesModelListFiltered.get(position).getName());
            itemCategory.setText(recipesModelListFiltered.get(position).getCategory());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SuggestMeal.this, recipesModelListFiltered.get(position).getName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SuggestMeal.this, SuggestMeal_View.class).putExtra("name", recipesModelListFiltered.get(position)));
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = recipesModelList.size();
                        filterResults.values = recipesModelList;
                    }else {
                        String searchStr = constraint.toString().toLowerCase();
                        List<Recipe> resultData = new ArrayList<>();

                        for(Recipe recipe:recipesModelList){
                            if (recipe.getName().toLowerCase().contains(searchStr) || recipe.getCategory().toLowerCase().contains(searchStr)){
                                resultData.add(recipe);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    recipesModelListFiltered = (List<Recipe>) results.values;

                    notifyDataSetChanged();

                }
            };

            return filter;
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
            SuggestMeal.Odbjur odbjur = new SuggestMeal.Odbjur();
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
                    else if (serverMessage.startsWith("0509")) {
                        int n = Integer.parseInt(reader.readLine());

                        int id = Integer.parseInt(reader.readLine());   //CHOOSE RECIPE
                        String name = reader.readLine();                //CHOOSE RECIPE
                        String category = reader.readLine();            //CHOOSE RECIPE

                        for (int i = 0; i < n-1; i++) {
                            id = Integer.parseInt(reader.readLine());
                            name = reader.readLine();
                            category = reader.readLine();
                            Recipe recipe = new Recipe(id, name, category);
                            recipesnames.add(recipe);
                        }
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
    private void fillData(){
        Recipe r = new Recipe("Pancakes", "breakfast");
        recipesnames.add(r);
        r = new Recipe("Scrambled Eggs", "breakfast");
        recipesnames.add(r);
        r = new Recipe("Roast Chicken", "main");
        recipesnames.add(r);
        r = new Recipe("Spaghetti Carbonara", "main");
        recipesnames.add(r);
        r = new Recipe("French Fries", "main");
        recipesnames.add(r);
    }
    public void ViewActivity(String x){
        Intent intent = new Intent(this, SuggestMeal_View.class);
        intent.putExtra("name", x);
        startActivity(intent);
    }
}
