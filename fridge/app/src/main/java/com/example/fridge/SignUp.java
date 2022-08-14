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

public class SignUp extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button signUpWithGoogleButton = findViewById(R.id.signUpWithGoogleButton);
        Button loginTabButton = findViewById(R.id.loginTabButton2);
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpWithGoogleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpWithGoogleActivity();
            }
        });
        loginTabButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginTabActivity();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpButtonActivity();
            }
        });
    }

    public void signUpWithGoogleActivity(){

    }
    public void loginTabActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void signUpButtonActivity(){
        EditText usernameInput = findViewById(R.id.usernameText2);
        String usernameString = usernameInput.getText().toString();
        EditText passwordInput = findViewById(R.id.passwordText2);
        String passwordString = passwordInput.getText().toString();
        EditText emailInput = findViewById(R.id.emailText);
        String emailString = emailInput.getText().toString();

        if(usernameInput.getText().toString().matches("") || passwordInput.getText().toString().matches("") || emailInput.getText().toString().matches("")){
            View layout = LayoutInflater.from(this).inflate(R.layout.login_error,null);
            final Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);

            toast.show();
        }
        else{
            Encryption passwordEncryption = new Encryption();
            String p = passwordEncryption.encrypt(passwordString);
            String messageToServer = "01\n"+usernameString+"\n"+p+emailString+"\nq";

            AsyncConnection signUp = new AsyncConnection();
            try {
                signUp.execute(messageToServer).get();
                if(signUp.correctConnection){
                    View layout = LayoutInflater.from(this).inflate(R.layout.signup_accept,null);
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);

                    toast.show();

                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                }
                else{
                    View layout = LayoutInflater.from(this).inflate(R.layout.login_error,null);
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);

                    toast.show();

                    System.out.println("Sigging went wrong.");
                }
            } catch (ExecutionException ex) {
                System.out.println("ExecutionException error: " + ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException: " + ex.getMessage());
            }
        }
    }
}
