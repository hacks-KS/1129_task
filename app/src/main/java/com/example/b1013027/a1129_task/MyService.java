package com.example.b1013027.a1129_task;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service{

    private IMyAidlInterface.Stub isvc = new IMyAidlInterface.Stub() {
        @Override
        public float BMI(float kg, float m) throws RemoteException {
            return kg / (m * m) * 10000;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // インスタンスを返す
        return isvc;
    }
}
