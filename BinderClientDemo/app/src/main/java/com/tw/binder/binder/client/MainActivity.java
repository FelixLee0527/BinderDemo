package com.tw.binder.binder.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tw.binder.service.IAirController;
import com.tw.binder.service.IAirState;
import com.tw.binder.service.IAirState.Stub;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = "BinderClientDemo";

    private IAirController iAirController;

    private Button btn_set_left_air_temp;
    private Button btn_set_right_air_temp;
    private Button btn_set_air_wind_type;
    private Button btn_set_air_wind_level;
    private Button btn_set_air_loop_mode;

    private TextView tv_left_air_temp;
    private TextView tv_right_air_temp;
    private TextView tv_air_wind_type;
    private TextView tv_air_wind_level;
    private TextView tv_air_loop_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initService();
    }

    private void initView(){
        btn_set_left_air_temp = findViewById(R.id.btn_set_left_air_temp);
        btn_set_right_air_temp = findViewById(R.id.btn_set_right_air_temp);
        btn_set_air_wind_type = findViewById(R.id.btn_set_air_wind_type);
        btn_set_air_wind_level = findViewById(R.id.btn_set_air_wind_level);
        btn_set_air_loop_mode = findViewById(R.id.btn_set_air_loop_mode);

        tv_left_air_temp = findViewById(R.id.tv_left_air_temp);
        tv_right_air_temp = findViewById(R.id.tv_right_air_temp);
        tv_air_wind_type = findViewById(R.id.tv_air_wind_type);
        tv_air_wind_level = findViewById(R.id.tv_air_wind_level);
        tv_air_loop_mode = findViewById(R.id.tv_air_loop_mode);

        btn_set_left_air_temp.setOnClickListener(this);
        btn_set_right_air_temp.setOnClickListener(this);
        btn_set_air_wind_type.setOnClickListener(this);
        btn_set_air_wind_level.setOnClickListener(this);
        btn_set_air_loop_mode.setOnClickListener(this);
    }

    private void initService(){
        Intent intent = new Intent();
        intent.setClassName("com.tw.binder.service","com.tw.binder.service.AirService");
        getApplicationContext().bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName,IBinder iBinder){
            Log.d(TAG,"onServiceConnected: ");
            try{
                iAirController = IAirController.Stub.asInterface(iBinder);
                iAirController.registerAirCallBack(iAirStateImp);
                Toast.makeText(MainActivity.this,"服务绑定成功",Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Log.e(TAG,"onServiceConnected: " + e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName){
            Log.d(TAG,"onServiceDisconnected: ");
            if(iAirController != null){
                try{
                    iAirController.unRegisterAirCallBack(iAirStateImp);
                }catch(Exception e){
                    Log.e(TAG,"onServiceDisconnected: " + e.getMessage());
                }
            }
        }
    };

    /*空调状态或者数据发生改变*/
    private IAirState.Stub iAirStateImp = new Stub(){
        @Override
        public void onLeftTemp(float temp){
            Log.d(TAG,"onLeftTemp: temp:" + temp);
            tv_left_air_temp.setText(String.format("左边空调温度为：%s",temp));
        }

        @Override
        public void onRightTemp(float temp){
            Log.d(TAG,"onRightTemp: temp:" + temp);
            tv_right_air_temp.setText(String.format("右边空调温度为：%s",temp));
        }

        @Override
        public void onWindType(int type){
            Log.d(TAG,"onWindType: type:" + type);
            tv_air_wind_type.setText(String.format("吹风类型为：%s",type));
        }

        @Override
        public void onWindLevel(int level){
            Log.d(TAG,"onWindLevel: level:" + level);
            tv_air_wind_level.setText(String.format("风量为：%s",level));
        }

        @Override
        public void onLoop(int type){
            Log.d(TAG,"onLoop: type:" + type);
            tv_air_loop_mode.setText(String.format("循环模式为：%s",type));
        }
    };

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_set_left_air_temp:{
                setLeftTemp(20.0f);
                break;
            }
            case R.id.btn_set_right_air_temp:{
                setRightTemp(25.0f);
                break;
            }
            case R.id.btn_set_air_wind_type:{
                setWindType(1);
                break;
            }
            case R.id.btn_set_air_wind_level:{
                setWindLevel(4);
                break;
            }
            case R.id.btn_set_air_loop_mode:{
                setLoop(2);
                break;
            }
        }
    }

    /*手动改变空调状态或者数据*/

    public void setLeftTemp(float temp){
        try{
            Log.d(TAG,"setLeftTemp: temp:" + temp);
            iAirController.setLeftTemp(temp);
        }catch(RemoteException e){
            Log.e(TAG,"setLeftTemp: " + e.getMessage());
        }
    }

    public void setRightTemp(float temp){
        try{
            Log.d(TAG,"setRightTemp: temp:" + temp);
            iAirController.setRightTemp(temp);
        }catch(RemoteException e){
            Log.e(TAG,"setRightTemp: " + e.getMessage());
        }
    }


    public void setWindType(int type){
        try{
            Log.d(TAG,"setWindType: type:" + type);
            iAirController.setWindType(type);
        }catch(RemoteException e){
            Log.e(TAG,"setWindType: " + e.getMessage());
        }
    }


    public void setWindLevel(int level){
        try{
            Log.d(TAG,"setWindLevel: type:" + level);
            iAirController.setWindLevel(level);
        }catch(RemoteException e){
            Log.e(TAG,"setWindLevel: " + e.getMessage());
        }
    }

    public void setLoop(int type){
        try{
            Log.d(TAG,"setLoop: type:" + type);
            iAirController.setLoop(type);
        }catch(RemoteException e){
            Log.e(TAG,"setLoop: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy(){
        try{
            getApplicationContext().unbindService(connection);
        }catch(Exception e){
            Log.e(TAG,"onDetachedFromWindow: " + e.getMessage());
        }
        super.onDestroy();
    }

}
