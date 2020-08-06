package com.example.loginapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.alium.trackerlibrary.Alium;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnLogout;
    private TextView textView2;
    private EditText editText;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Alium alium ;

    SessionManager sessionManager;
    private SecondActivity secondActivity;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView2);
        btnLogout  = findViewById(R.id.btnlogout);
        textView2 = findViewById(R.id.tv_username);
        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);

        secondActivity = this;
        sessionManager = new SessionManager(getApplicationContext());

        String sUsername = sessionManager.getUsername();

        textView2.setText(sUsername);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure to logout ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sessionManager.setLogin(false);
                        sessionManager.setUsername("");

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        });

        alium = new Alium(secondActivity);
        alium.onClickTracker(textView);
        alium.onClickTracker(btnLogout);
        alium.onClickTracker(textView2);
        alium.onClickTracker(radioButtonMale);
        alium.onClickTracker(radioButtonFemale);


    }
}
