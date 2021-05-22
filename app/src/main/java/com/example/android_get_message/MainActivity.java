package com.example.android_get_message;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    IntentFilter filter;
    int dataCount = 0;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                SmsMessage[] messages;
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
            if (settings.getAllowMessageFrom() != null && !msg_from.equals(settings.getAllowMessageFrom()))
                return;
            if (settings.isDisplayMessageToast())
                Toast.makeText(context, msg_body + ", From: " + msg_from, Toast.LENGTH_SHORT).show();

            String[] items = msg_body.split(",");
            for (String item : items) {
                String key = item.split(":")[0];
                String val = item.split(":")[1];
                int i = 0;
                while (i < val.length() && (Character.isDigit(val.charAt(i)) || val.charAt(i) == '.')) i++;
                addData(key, dataCount, Float.parseFloat(val.substring(0, i)), val);
            }
            dataCount++;
        }
    };

    private Settings settings;
    private Analysis analysis = new Analysis();
    public LineChart lineChart;
    public TableLayout tableLayout;
    private final HashMap<String, LineDataSet> lineDataSetHashMap = new HashMap<>();
    private float currentColorHue = -1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = Settings.getInstance(this);

        lineChart = findViewById(R.id.chart);
        tableLayout = findViewById(R.id.table);
//        tableLayout.addView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }

        registerReceiver(broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

//        addData("test", 1000f);

//        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//        lineDataSets.addAll(lineDataSetHashMap.values());
//        LineData data = new LineData(new ArrayList<>(lineDataSetHashMap.values()));
//        lineChart.setData(data);
//        lineChart.invalidate();
    }

    public void addData(String key, int t, Float val, String desc) {
        System.out.println("key: " + key + ", val: " + val);
        LineDataSet dataSet = lineDataSetHashMap.get(key);
        if (dataSet == null) {
            ArrayList<Entry> values = new ArrayList<>();
//            values.add(new Entry(dataCount, val, desc));
            LineDataSet newDataSet = new LineDataSet(values, key);
            Random random = new Random();
            if (currentColorHue == -1)
                currentColorHue = random.nextFloat() * 360;
            currentColorHue += random.nextFloat() * 30 + 30;
            currentColorHue %= 360;
            int color = Color.HSVToColor(new float[] {currentColorHue, 0.8f, 0.8f});
//            Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW
            newDataSet.setColor(color);
            newDataSet.setCircleColor(color);
            lineDataSetHashMap.put(key, newDataSet);
            dataSet = newDataSet;
        }
        dataSet.addEntry(new Entry(t, val, desc));

        TextView textView = findViewById(-1000 + t);
        if (textView == null) {
            TableRow row = new TableRow(this);
            tableLayout.addView(row);
            textView = new TextView(this);
            textView.setId(-1000 + t);
            String s = "\n数据 " + t;
            textView.setText(s);
            row.addView(textView);
        }
        String text = textView.getText() + "\n" + key + ": " + desc;
        textView.setText(text);

        if (key.equals("PM2.5")) {
            analysis.pm2_5 = val;
        } else if (key.equals("PM10")) {
            analysis.pm10 = val;
        }

        if (analysis.pm10 != -1 && analysis.pm2_5 != -1) {
            analysis.calculate();
            String text2 = textView.getText() + analysis.desc;
            textView.setText(text2);
            analysis.pm2_5 = -1;
            analysis.pm10 = -1;
        }

        LineData data = new LineData(new ArrayList<>(lineDataSetHashMap.values()));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_settings:
                SettingsActivity activity = new SettingsActivity();
                Intent intent = new Intent(this, activity.getClass());
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
