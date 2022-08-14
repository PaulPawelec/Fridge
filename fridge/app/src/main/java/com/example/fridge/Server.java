package com.example.fridge;
import java.net.*;
import java.io.*;

public class Server {

    private String cookies;
    private String message;

    public Server() {
        cookies = "";
        message = "";
    }

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
            message = serverMessage;

            if(serverMessage.equals("null")) System.out.println("Connection with the server lost");
            else{
                //System.out.println(serverMessage);
                if(serverMessage.startsWith("-1")){
                    System.out.println("Correct connection & wrong data");
                    return false;
                }
                else if(serverMessage.startsWith("ok")){
                    LoginSessions.getInstance().setSession(serverMessage);//LOGINSESJA
                    System.out.println("ServerMess: "+serverMessage);
                    System.out.println("Correct connection & data");
                    cookies=serverMessage.substring(2);
                    return true;
                }
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
        return false;
    }

    public String getCookies(){
        return cookies;
    }

    public String getMessage() {
        return message;
    }
}