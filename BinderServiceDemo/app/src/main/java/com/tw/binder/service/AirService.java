package com.tw.binder.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class AirService extends Service{

    private static final String TAG = "AirService";

    public static final int MSG_AIR_LEFT_TEMP = 0xff00;
    public static final int MSG_AIR_RIGHT_TEMP = 0xff01;
    public static final int MSG_AIR_WIND_TYPE = 0xff02;
    public static final int MSG_AIR_WIND_LEVEL = 0xff03;
    public static final int MSG_AIR_LOOP_MODE = 0xff04;

    private RemoteCallbackList<IAirState> remoteCallbackList = new RemoteCallbackList<IAirState>();

    @Override
    public IBinder onBind(Intent intent){
        return new MyBinder();
    }

    private class MyBinder extends IAirController.Stub{

        @Override
        public void registerAirCallBack(IAirState iAirState) throws RemoteException{
            if(iAirState != null){
                remoteCallbackList.register(iAirState);
            }
        }

        @Override
        public void setLeftTemp(float temp) throws RemoteException{
            Log.d(TAG,"setLeftTemp: temp:" + temp);
            mHandler.sendEmptyMessage(MSG_AIR_LEFT_TEMP);
        }

        @Override
        public void setRightTemp(float temp) throws RemoteException{
            Log.d(TAG,"setRightTemp: temp:" + temp);
            mHandler.sendEmptyMessage(MSG_AIR_RIGHT_TEMP);
        }

        @Override
        public void setWindType(int type) throws RemoteException{
            Log.d(TAG,"setWindType: type:" + type);
            mHandler.sendEmptyMessage(MSG_AIR_WIND_TYPE);
        }

        @Override
        public void setWindLevel(int level) throws RemoteException{
            Log.d(TAG,"setWindLevel: type:" + level);
            mHandler.sendEmptyMessage(MSG_AIR_WIND_LEVEL);
        }

        @Override
        public void setLoop(int type) throws RemoteException{
            Log.d(TAG,"setLoop: type:" + type);
            mHandler.sendEmptyMessage(MSG_AIR_LOOP_MODE);
        }

        @Override
        public void unRegisterAirCallBack(IAirState iAirState) throws RemoteException{
            remoteCallbackList.unregister(iAirState);
        }
    }

    private Handler mHandler = new Handler(new Callback(){
        @Override
        public boolean handleMessage(Message msg){
            try{
                switch(msg.what){
                    case MSG_AIR_LEFT_TEMP:{
                        onLeftTemp(20.0f);
                        break;
                    }
                    case MSG_AIR_RIGHT_TEMP:{
                        onRightTemp(25.0f);
                        break;
                    }
                    case MSG_AIR_WIND_TYPE:{
                        onWindType(1);
                        break;
                    }
                    case MSG_AIR_WIND_LEVEL:{
                        onWindLevel(4);
                        break;
                    }
                    case MSG_AIR_LOOP_MODE:{
                        onLoop(2);
                        break;
                    }
                }
            }catch(Exception e){
                Log.e(TAG,"handleMessage: " + e.getMessage());
            }

            return true;
        }
    });

    /*空调状态发生改变的时候,通知客户端*/

    public void onLeftTemp(float temp) throws RemoteException{
        try{
            int len = remoteCallbackList.beginBroadcast();
            for(int i = 0;i < len;i++){
                remoteCallbackList.getBroadcastItem(i).onLeftTemp(temp);
            }
            remoteCallbackList.finishBroadcast();
        }catch(RemoteException e){
            Log.e(TAG,"onLeftTemp: " + e.getMessage());
        }
    }


    public void onRightTemp(float temp) throws RemoteException{
        try{
            int len = remoteCallbackList.beginBroadcast();
            for(int i = 0;i < len;i++){
                remoteCallbackList.getBroadcastItem(i).onRightTemp(temp);
            }
            remoteCallbackList.finishBroadcast();
        }catch(RemoteException e){
            Log.e(TAG,"onRightTemp: " + e.getMessage());
        }
    }


    public void onWindType(int type) throws RemoteException{
        try{
            int len = remoteCallbackList.beginBroadcast();
            for(int i = 0;i < len;i++){
                remoteCallbackList.getBroadcastItem(i).onWindType(type);
            }
            remoteCallbackList.finishBroadcast();
        }catch(RemoteException e){
            Log.e(TAG,"onWindType: " + e.getMessage());
        }
    }


    public void onWindLevel(int level) throws RemoteException{
        try{
            int len = remoteCallbackList.beginBroadcast();
            for(int i = 0;i < len;i++){
                remoteCallbackList.getBroadcastItem(i).onWindLevel(level);
            }
            remoteCallbackList.finishBroadcast();
        }catch(RemoteException e){
            Log.e(TAG,"onWindLevel: " + e.getMessage());
        }
    }


    public void onLoop(int type) throws RemoteException{
        try{
            int len = remoteCallbackList.beginBroadcast();
            for(int i = 0;i < len;i++){
                remoteCallbackList.getBroadcastItem(i).onLoop(type);
            }
            remoteCallbackList.finishBroadcast();
        }catch(RemoteException e){
            Log.e(TAG,"onLoop: " + e.getMessage());
        }
    }
}
