package com.example.spikee.smartirrigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Select extends AppCompatActivity {

    @BindView(R.id.manual)
    Button manual;

    @BindView(R.id.semi_automatic)
    Button semi_automatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ButterKnife.bind(this);

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Select.this, Manual.class);
                startActivity(in);
            }
        });

        semi_automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Select.this,receivesms.class);
                startActivity(in);
            }
        });
    }
}
