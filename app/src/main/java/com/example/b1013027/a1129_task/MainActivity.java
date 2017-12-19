package com.example.b1013027.a1129_task;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    IMyAidlInterface aidl = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        // MyServiceにbindする
        Intent i = new Intent(this, MyService.class);
        bindService(i, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // MyServiceからアンバイド
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        // サービスとの接続時に呼ばれる
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            aidl = IMyAidlInterface.Stub.asInterface(ibinder);
        }
        // サービスとの切断時に呼ばれる
        public void onServiceDisconnected(ComponentName name)
        {
            aidl = null;
        }
    };

    public void onClick(View view){
        try {
            EditText m = (EditText)findViewById(R.id.m);
            EditText kg = (EditText)findViewById(R.id.kg);
            Editable getm = m.getText();
            Editable getkg = kg.getText();
            float f_m = Float.parseFloat(getm.toString());
            float f_kg = Float.parseFloat(getkg.toString());
            float result = aidl.BMI(f_kg, f_m);
            TextView explain = (TextView)findViewById(R.id.BMI);
            explain.setText(String.valueOf(result));
        }catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}
