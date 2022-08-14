package com.example.fridge;
import android.os.AsyncTask;

public class AsyncConnection extends AsyncTask<String, Void, Void>{
    public boolean correctConnection;
    private String cookies;
    private String ServerMessage;

    public AsyncConnection() {
        correctConnection = false;
        cookies = "";
    }

    @Override
    protected Void doInBackground(String... arg0) {
        System.out.println("Do in background & create server connection");
        Server server = new Server();
        correctConnection = server.serverRequest(arg0[0]);
        cookies=server.getCookies();
        ServerMessage=server.getMessage();
        System.out.println("Connection ended");
        System.out.println(correctConnection);
        return null;
    }

    public String getCookies(){
        return cookies;
    }

    public String getServerMessage(){
        return ServerMessage;
    }
}