package com.example.spikee.smartirrigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    TextView click;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        click = findViewById(R.id.click);
        firebaseAuth = FirebaseAuth.getInstance();

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent in = new Intent(Splash.this, Select.class);
                    startActivity(in);
                } else {
                    Intent in = new Intent(Splash.this, Login.class);
                    startActivity(in);
                }
            }
        });
    }
}
