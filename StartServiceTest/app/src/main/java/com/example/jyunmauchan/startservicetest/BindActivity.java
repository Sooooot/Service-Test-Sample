package com.example.jyunmauchan.startservicetest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jyunmauchan.startservicetest.service.LocalService;

public class BindActivity extends AppCompatActivity {
    protected static final String TAG = "wzj";
    Button btnBind;
    Button btnUnBind;
    Button btnGetDatas;
    Button btnSwitch;
    private ServiceConnection conn;
    private LocalService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        btnBind = (Button) findViewById(R.id.BindService);
        btnUnBind = (Button) findViewById(R.id.unBindService);
        btnGetDatas = (Button) findViewById(R.id.getServiceDatas);
        btnSwitch = (Button) findViewById(R.id.switch2messenger);
        // 创建绑定对象
        final Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        // 开启绑定
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "绑定调用：bindService");
                // 调用绑定方法
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });
        // 解除绑定
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "解除绑定调用：unbindService");
                // 解除绑定
                if (mService != null) {
                    mService = null;
                    unbindService(conn);
                }
            }
        });

        // 获取数据
        btnGetDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null) {
                    // 通过绑定服务传递的Binder对象，获取Service暴露出来的数据

                    Log.d(TAG, "从服务端获取数据：" + mService.getCount());
                } else {

                    Log.d(TAG, "还没绑定呢，先绑定,无法从服务端获取数据");
                }
            }
        });
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(BindActivity.this, MessengerActivity.class);

            @Override
            public void onClick(View v) {
                startActivity(intent);
                BindActivity.this.finish();
            }
        });

        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "绑定成功调用：onServiceConnected");
                // 获取Binder
                LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
                mService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };
    }

}
