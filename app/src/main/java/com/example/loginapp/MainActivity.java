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

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;

    private int counter = 0;

    private Tracker tracker ;
    private Alium alium ;


    SessionManager sessionManager;
    private MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

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



        alium = new Alium(mainActivity);
        tracker = new Tracker();

        alium.init(this.getBaseContext());
        tracker.onClickTracker(Name);
        tracker.onClickTracker(Password);
        tracker.onClickTracker(Info);
        tracker.onClickTracker(Login);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validate(String userName, String userPassword) {

        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            counter--;

            Info.setText("No of attempts remaining: " + counter);

            if (counter == 0) {
                Login.setEnabled(false);
            }
        }
    }
}