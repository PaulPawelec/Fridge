package com.example.fridge;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Diet_Find extends AppCompatActivity {
    ListView list;
    ArrayList<Recipe> dietesnames = new ArrayList<>();
    ArrayList<String> mydiets;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_find);

        String messageToServer = "0609\n"+"dietlist"+"\nq";

        Diet_Find.MessageSender messageSender = new Diet_Find.MessageSender();
        try {
            messageSender.execute(messageToServer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //fillData();
        Intent intent = getIntent();
        mydiets = (ArrayList<String>) intent.getSerializableExtra("myrecipes");

        SearchView Filter = (SearchView) findViewById(R.id.searchFilter);
        list = (ListView) findViewById(R.id.theList);

        customAdapter = new CustomAdapter(dietesnames, this);
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
    private void fillData(){
        Recipe r = new Recipe("SuperDieta", "general");
        dietesnames.add(r);
        r = new Recipe("Yes yes yes", "vegan");
        dietesnames.add(r);
        r = new Recipe("Chicken", "meat");
        dietesnames.add(r);
        r = new Recipe("Yes", "general");
        dietesnames.add(r);
        r = new Recipe("I Like Food", "general");
        dietesnames.add(r);
        r = new Recipe("Muu muuu", "vegan");
        dietesnames.add(r);
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
            View view = getLayoutInflater().inflate(R.layout.row_items_2, null);

            TextView itemName = view.findViewById(R.id.itemName);
            TextView itemCategory = view.findViewById(R.id.itemCategory);

            itemName.setText(recipesModelListFiltered.get(position).getName());
            itemCategory.setText(recipesModelListFiltered.get(position).getCategory());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Diet_Find.this, recipesModelListFiltered.get(position).getName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Diet_Find.this, Diet_View.class).putExtra("name", recipesModelListFiltered.get(position)));
                }
            });

            final Button addButton= (Button)view.findViewById(R.id.addButton);
            if(mydiets.contains(dietesnames.get(position).name)){
                addButton.setVisibility(View.INVISIBLE);
            }
            addButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String messageToServer = "0607\n"+recipesModelListFiltered.get(position).getIDrecipe()+"\nq";

                    Diet_Find.MessageSender messageSender = new Diet_Find.MessageSender();
                    try {
                        messageSender.execute(messageToServer).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    addButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(Diet_Find.this, recipesModelListFiltered.get(position).getName(), Toast.LENGTH_SHORT).show();
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
            Diet_Find.Odbjur odbjur = new Diet_Find.Odbjur();
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
                    else if (serverMessage.startsWith("0609")) {
                        int n = Integer.parseInt(reader.readLine());

                        for (int i = 0; i < n; i++) {
                            int id = Integer.parseInt(reader.readLine());
                            String name = reader.readLine();
                            String category = reader.readLine();
                            Recipe recipe = new Recipe(id, name, category);
                            //Recipe recipe = new Recipe(name, category);
                            dietesnames.add(recipe);
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
}
