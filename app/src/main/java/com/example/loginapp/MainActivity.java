package com.example.loginapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import org.alium.trackerlibrary.Alium;
import org.alium.trackerlibrary.Tracker;


public class MainActivity extends AppCompatActivity {

    private TextView LoginPage;
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;

    private Alium alium ;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginPage = findViewById(R.id.tvLoginPage);
        Name =  findViewById(R.id.etName);
        Password =  findViewById(R.id.etPassword);
        Info =  findViewById(R.id.tvInfo);
        Login =  findViewById(R.id.btnLogin);
        Register =findViewById(R.id.btnRegister);

        Info.setText("No of attempts remaining: 5");
        sessionManager = new SessionManager(getApplicationContext());


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUsername = Name.getText().toString().trim();
                String sPassword = Password.getText().toString().trim();

                if (sPassword.equals("")) {

                    Password.setError("Please enter password");
                }

                if (sPassword.equals("root")) {

                    String sessionId = sessionManager.getSessionId();
                    Log.d("Session :", "Session ID-------------------" + sessionId);

                    sessionManager.setLogin(true);
                    sessionManager.setUsername(sUsername);
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));

                    finish();
                }

                else {

                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (sessionManager.getLogin()) {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
        }



        alium = new Alium(this);
//        alium.init("client-2kk6pcewke9i7wl1","SDK-android-2kk6pcewke9i7wl4"); // Client-ID | sdk-Id,
        alium.onClickTracker(LoginPage);
        alium.onClickTracker(Name);
        alium.onClickTracker(Password);
        alium.onClickTracker(Info);
        alium.onClickTracker(Login);
        alium.onClickTracker(Register);
//        alium.initMethod(this);
        alium.init("client-2kk6pcewke9i7wl1","SDK-android-2kk6pcewke9i7wl4",this); // Client-ID | sdk-Id,

    }

}