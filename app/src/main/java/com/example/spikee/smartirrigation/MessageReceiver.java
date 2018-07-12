package com.example.spikee.smartirrigation;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {
    String str = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.abortBroadcast();
        Bundle bundle = intent.getExtras();

        SmsMessage[] messages;


        if (bundle != null) {

            Object[] pdus = (Object[]) bundle.get("pdus");
            messages = new SmsMessage[pdus != null ? pdus.length : 0];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) (pdus != null ? pdus[i] : null));
                String nu = messages[i].getDisplayOriginatingAddress();
                Log.i("nu", nu);

                if (nu.equals(" +919997781991")) {
                    str += messages[i].getDisplayOriginatingAddress();
                    str += " : ";
                    str += messages[i].getDisplayMessageBody();
                    str += "\n";
                    todo(context);

                } else {
                    Log.i("FAIL", messages[i].getDisplayOriginatingAddress());
                }
            }
//            Intent in = new Intent(context, SMS_Receive.class);
//            in.setAction("SMS_RECEIVED_ACTION");
//            in.putExtra("message", str);
//            context.startActivity(in);
        }
    }

    public void todo(Context context) {
        // Toast.makeText(context, str, Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Sensor Value")
                        .setContentText(str);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }
}
