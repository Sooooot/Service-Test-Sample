package com.example.jyunmauchan.startservicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jyunmauchan.startservicetest.service.WeirdService;

public class WeirdActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weird);
        startBtn = (Button) findViewById(R.id.startWeirdService);
        startBtn.setOnClickListener(this);
        Intent it = new Intent(this, WeirdService.class);
        startService(it);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, WeirdService.class);
        switch (v.getId()) {
            case R.id.startWeirdService:
                startService(it);
                break;
        }
    }
}