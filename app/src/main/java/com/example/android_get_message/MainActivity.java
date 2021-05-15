package com.example.android_get_message;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    IntentFilter filter;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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
//                Toast.makeText(context, val.substring(0, i), Toast.LENGTH_SHORT).show();
                addData(key, Float.parseFloat(val.substring(0, i)));
//            mainActivity.addData(key, Float.parseFloat(val.substring(0, i)));
            }
        }
    };

    public LineChart lineChart;
    private HashMap<String, LineDataSet> lineDataSetHashMap = new HashMap<String, LineDataSet>();
    private int dataCount = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.chart);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

        registerReceiver(broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        addData("test", 1000f);

//        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//        lineDataSets.addAll(lineDataSetHashMap.values());
//        LineData data = new LineData(lineDataSetHashMap.get("test"));
//        lineChart.setData(data);
//        lineChart.invalidate();
    }

    public void addData(String key, Float val) {
        System.out.println("key: " + key + ", val: " + val);
        LineDataSet dataSet = lineDataSetHashMap.get(key);
        if (dataSet == null) {
            ArrayList<Entry> values = new ArrayList<Entry>();
            values.add(new Entry(dataCount, val));
            LineDataSet newDataSet = new LineDataSet(values, key);
            lineDataSetHashMap.put(key, newDataSet);
            dataSet = newDataSet;
        }
        dataSet.addEntry(new Entry(dataCount, val));
        dataCount++;
        LineData data = new LineData(lineDataSetHashMap.get("test"));
        lineChart.setData(data);
        lineChart.invalidate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults){
        if (requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
