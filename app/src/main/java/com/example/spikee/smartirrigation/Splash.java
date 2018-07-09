package com.example.spikee.smartirrigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        click=findViewById(R.id.click);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Splash.this,Select.class);
                startActivity(in);
            }
        });
    }
}
