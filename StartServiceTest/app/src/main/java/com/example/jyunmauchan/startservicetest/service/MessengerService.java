package com.example.jyunmauchan.startservicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessengerService extends Service {

    public static final int MSG_SAY_HELLO = 1;
    private static final String TAG ="msg" ;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.i(TAG, "thanks,Service had receiver message from client!");
                    //回复客户端信息,该对象由客户端传递过来
                    Messenger client=msg.replyTo;
                    //获取回复信息的消息实体
                    Message replyMsg=Message.obtain(null,MessengerService.MSG_SAY_HELLO);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","ok~,I had receiver message from you! ");
                    replyMsg.setData(bundle);
                    //向客户端发送消息
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate(){
        Log.i(TAG, "Service is invoke onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service is invoke onBind");
        return mMessenger.getBinder();
    }

}
