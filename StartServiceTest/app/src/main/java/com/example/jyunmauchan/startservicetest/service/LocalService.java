package com.example.jyunmauchan.startservicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalService extends Service {
    private final static String TAG = "wzj";
    private int count;
    private boolean quit;
    private Thread thread;
    private LocalBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        // 声明一个方法，getService。（提供给客户端调用）
        public LocalService getService() {
            // 返回当前对象LocalService,这样就可在客户端端调用Service的公共方法了
            return LocalService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is invoke Created");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 每间隔一秒count加1 ，直到quit为true。
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        thread.start();
    }

    /**
     * 公共方法
     * @return
     */
    public int getCount(){
        return count;
    }
    /**
     * 解除绑定时调用
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service is invoke onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service is invoke Destroyed");
        this.quit = true;
        super.onDestroy();
    }

}
