package com.pratico4.activdad2.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.Message;
import android.util.Log;
import androidx.annotation.Nullable;

public class ServiceOne extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private static final String TAG = "MyActivity";

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }



    public ServiceOne(){
        Log.i(TAG,"ServiceOne RUN...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"Nuevo Servicio...");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        try{
            Log.i(TAG,"Creando el subproceso dentro del servicio...");
            HandlerThread thread = new HandlerThread("ServiceStartArguments",
                    Process.THREAD_PRIORITY_BACKGROUND);
            Log.i(TAG,"El subproceso fue creado correctamente.");

            thread.start();
            Log.i(TAG,"Incio del subproceso.");

            serviceLooper = thread.getLooper();
            Log.i(TAG,"Nro del subproceso "+ serviceLooper.getThread().getId());
            serviceHandler = new ServiceHandler(serviceLooper);

            super.onCreate();
            Log.i(TAG,"Servicio creado...");
        }catch (Exception e){
            Log.w(TAG,"Al intentar crear el subproceso ocurrio el siguiente problema: " + e.getMessage() + ". El subproceso no se creo correctamente");
        }
    }

    @Override
    public void onDestroy() {
        try{
            Message msg = serviceHandler.obtainMessage();
            Log.i(TAG,"Nro del subproceso "+ serviceLooper.getThread().getId());
            super.stopForeground(msg.arg1);
            Log.i(TAG,"Borrando el subproceso "  + serviceLooper.getThread().getId() +  "...");
            super.onDestroy();
            Log.i(TAG,"El subproceso fue borrado correctamente.");
        }catch (Exception e){
            Log.w(TAG,"Al intentar borrar el subproceso, ocurrio el siguiente problema: " + e.getMessage() + ". El subproceso no se borro correctamente");
        }
    }

}
