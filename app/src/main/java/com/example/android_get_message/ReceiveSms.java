package com.example.android_get_message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceiveSms extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages = null;
            String msg_from;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = messages[i].getOriginatingAddress();
                        String msgBody = messages[i].getMessageBody();
                        MessageProcess(context, msg_from, msgBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void MessageProcess(Context context, String msg_from, String msg_body) {
        Toast.makeText(context, msg_body + ", From: " + msg_from, Toast.LENGTH_SHORT).show();
//        MainActivity mainActivity = (MainActivity) context;
        String[] items = msg_body.split(",");
        for (String item : items) {
            String key = item.split(":")[0];
            String val = item.split(":")[1];
            int i = 0;
            while (i < val.length() && (Character.isDigit(val.charAt(i)) || val.charAt(i) == '.')) i++;
//            Float value = Float.parseFloat(val.substring(0, i));
            Toast.makeText(context, val.substring(0, i), Toast.LENGTH_SHORT).show();
//            mainActivity.addData(key, Float.parseFloat(val.substring(0, i)));
        }
    }
}
