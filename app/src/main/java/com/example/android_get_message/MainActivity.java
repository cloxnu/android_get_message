package com.example.android_get_message;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    IntentFilter filter;
//    GetMessage receiver;
//    private SmsObserver smsObserver;


//    @SuppressLint("HandlerLeak")
//    private final Handler smsHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            System.out.println("Get3");
//            System.out.println(msg.toString());
//        }
//
//    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }
//
//        if(ContextCompat.checkSelfPermission(this, "android.permission_RECEIVE_SMS") == PackageManager.PERMISSION_GRANTED){
//            System.out.println("permission got");
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
//
//            } else  {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 0);
//            }
//        }
//        Toast.makeText(this, "new", Toast.LENGTH_LONG).show();

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
//        unregisterReceiver(receiver);//解绑广播接收器
    }


//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    public void askPermissions() {
//
//        String[] PERMISSIONS = {Manifest.permission.READ_SMS};
//
//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
//        }
//        System.out.println("----------------------------" + hasPermissions(this,PERMISSIONS));
//
//    }

}

//class SmsObserver extends ContentObserver {
//    private Uri uri;
//    private Context mContext;
//    private Handler mHandler;
//    private String[] projection = new String[]{"_id","body"};
//    private Cursor cursor = null;
//    public SmsObserver(Handler handler) {
//        super(handler);
//    }
//
//    public SmsObserver(Context context,Handler handler){
//        super(handler);
//        this.mContext = context;
//        this.mHandler = handler;
//    }
//
//    @Override
//    public void onChange(boolean selfChange, Uri uri) {
//        super.onChange(selfChange);
//        if (uri.toString().equals("content://sms/raw")) {
//
//            System.out.println("Get1");
//            return;
//        }
//        System.out.println("Get");
//        cursor = mContext.getContentResolver().query(Uri.parse("content://sms/inbox"), projection,null, null, "_id desc");
//        if (cursor != null && cursor.getCount() != 0) {
//            cursor.moveToNext();
//            int smsbodyColumn = cursor.getColumnIndex("body");
//            String smsBody = cursor.getString(smsbodyColumn);
//            System.out.println("Haha: " + smsBody);
//        }
//        cursor.close();
//    }
//}
