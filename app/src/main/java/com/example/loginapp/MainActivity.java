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

    private TextView LoginPage;
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;


//    private Tracker tracker ;
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

        LoginPage = findViewById(R.id.tvLoginPage);
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);
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

        alium = new Alium(mainActivity);
        alium.onClickTracker(LoginPage);
        alium.onClickTracker(Name);
        alium.onClickTracker(Password);
        alium.onClickTracker(Info);
        alium.onClickTracker(Login);
        alium.onClickTracker(Register);
        alium.init(this.getBaseContext()); //sdk-Id, Client-ID
    }

}