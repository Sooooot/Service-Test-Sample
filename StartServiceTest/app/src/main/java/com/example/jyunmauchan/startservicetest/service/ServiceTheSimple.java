package com.example.jyunmauchan.startservicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jyunmauchan.startservicetest.usage.testUsage;

public class ServiceTheSimple extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("onCreate invoke");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand invoke");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy invoke");
        super.onDestroy();
        System.out.println("shoud have a null-pointer exception here");
        new testUsage().usageData();
    }
}
