package com.example.jyunmauchan.startservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jyunmauchan.startservicetest.service.MessengerService;

public class MessengerActivity extends AppCompatActivity {
    Messenger mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    /**
     * 实现与服务端链接的对象
     */
    public ServiceConnection conn;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            /**
             * 通过服务端传递的IBinder对象,创建相应的Messenger
             * 通过该Messenger对象与服务端进行交互
             */
            Log.d("zj","onServiceConnected");
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };

    public void sayHello(View v) {
        if (!mBound) return;
        // 创建与服务交互的消息实体Message
        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
        try {
            //发送消息
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Button bindService= (Button) findViewById(R.id.bindService);
        Button unbindService= (Button) findViewById(R.id.unbindService);
        Button sendMsg= (Button) findViewById(R.id.sendMsgToService);

        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zj","onClick-->bindService");
                //当前Activity绑定服务端
                boolean b = bindService(new Intent(MessengerActivity.this, MessengerService.class), mConnection,
                        Context.BIND_AUTO_CREATE);
                Log.d("zj", String.valueOf(b));
            }
        });

        //发送消息给服务端
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(v);
            }
        });


        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Unbind from the service
                if (mBound) {
                    Log.d("zj","onClick-->unbindService");
                    unbindService(mConnection);
                    mBound = false;
                }
            }
        });
    }

    /**
     * 用于接收服务器返回的信息
     */
    private Messenger mRecevierReplyMsg= new Messenger(new ReceiverReplyMsgHandler());


    private static class ReceiverReplyMsgHandler extends Handler {
        private static final String TAG = "zj";

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //接收服务端回复
                case MessengerService.MSG_SAY_HELLO:
                    Log.i(TAG, "receiver message from service:"+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
