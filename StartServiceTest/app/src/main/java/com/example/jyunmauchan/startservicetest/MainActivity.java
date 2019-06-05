package com.example.jyunmauchan.startservicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jyunmauchan.startservicetest.service.ServiceTheSimple;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn;
    private Button stopBtn;
    private Button switchBtn;
    private Button switchBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.startService);
        stopBtn = (Button) findViewById(R.id.stopService);
        switchBtn = (Button) findViewById(R.id.switch2Bind);
        switchBtn1 = (Button) findViewById(R.id.switch2weird);
        startBtn.setOnClickListener(this);
        assert stopBtn != null;
        stopBtn.setOnClickListener(this);
        switchBtn.setOnClickListener(this);
        switchBtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, ServiceTheSimple.class);
        Intent intent = new Intent(MainActivity.this, BindActivity.class);
        Intent intent1 = new Intent(MainActivity.this, WeirdActivity.class);
        switch (v.getId()) {
        case R.id.startService:
            startService(it);
            break;
        case R.id.stopService:
            stopService(it);
            break;
        case R.id.switch2Bind:
            startActivity(intent);
            MainActivity.this.finish();
            break;
        case R.id.switch2weird:
            startActivity(intent1);
            break;
        }
    }
}
