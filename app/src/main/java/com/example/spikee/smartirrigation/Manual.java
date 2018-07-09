package com.example.spikee.smartirrigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Manual extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    private String message;
    private String number;

    private SmsManager smsManager;

    @BindView(R.id.on)
    Button on;

    @BindView(R.id.off)
    Button off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        ButterKnife.bind(this);

        smsManager = SmsManager.getDefault();
        number = "9997781991";

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Turn On";
                turnon();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Turn Off";
                turnoff();
            }
        });
    }

    public void turnon() {
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS Sent",
                Toast.LENGTH_LONG).show();
    }


    public void turnoff() {
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS Sent",
                Toast.LENGTH_LONG).show();
    }
}
