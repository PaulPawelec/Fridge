package com.example.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button signUpTabButton = findViewById(R.id.signUpTabButton);
        Button loginButton = findViewById(R.id.loginButton);

        View layout = LayoutInflater.from(this).inflate(R.layout.login_error,null);
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        signUpTabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpTabActivity();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginButtonActivity(toast);
            }
        });
    }

    public void signUpTabActivity(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void loginButtonActivity(Toast toast){
        EditText usernameInput = findViewById(R.id.usernameText);
        String usernameString = usernameInput.getText().toString();
        EditText passwordInput = findViewById(R.id.passwordText);
        String passwordString = passwordInput.getText().toString();

        View layout = LayoutInflater.from(this).inflate(R.layout.login_accept,null);
        final Toast toast1 = new Toast(getApplicationContext());
        toast1.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast1.setDuration(Toast.LENGTH_SHORT);
        toast1.setView(layout);

        Encryption passwordEncryption = new Encryption();
        String p = passwordEncryption.encrypt(passwordString);
        //String messageToServer = "00\n"+usernameString+"\n"+passwordString+"\nq";
        String messageToServer = "00\n"+usernameString+"\n"+p+"\nq";
        AsyncConnection login = new AsyncConnection();
        try {
            login.execute(messageToServer).get();
            if(login.correctConnection){
                toast1.show();
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }
            else{
                toast.show();
                System.out.println("Logging went wrong.");
            }
        } catch (ExecutionException ex) {
            System.out.println("ExecutionException error: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException: " + ex.getMessage());
        }
    }
}
