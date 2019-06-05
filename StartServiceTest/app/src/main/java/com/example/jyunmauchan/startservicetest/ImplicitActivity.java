package com.example.jyunmauchan.startservicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ImplicitActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.startImplicitService);
        stopBtn = (Button) findViewById(R.id.stopImplicitService);
        startBtn.setOnClickListener(this);
        assert stopBtn != null;
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent();
        it.setAction("com.android.ImplicitService");
        switch (v.getId()) {
        case R.id.startImplicitService:
            startService(it);
            break;
        case R.id.stopImplicitService:
            stopService(it);
            break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
