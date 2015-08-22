package com.iasacv.impulsora.rutainspeccion.Servicios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.RutaInspeccionDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

/**
 * Created by Administrator on 02/07/2015.
 */
public class WebServiceUpdate extends Service {

    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;
    Usuario _objUsuario;

    WebServiceBP _objWebServiceBP;
    RutaInspeccionDA _objRutaInspeccionDA;
    PlaneacionRutaDA _objPlaneacionRutaDA;
    EntLibDBTools _objEntLibDBTools;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            _objWebServiceBP = new WebServiceBP(WebServiceUpdate.this);
            _objRutaInspeccionDA = new RutaInspeccionDA(WebServiceUpdate.this);
            _objPlaneacionRutaDA = new PlaneacionRutaDA(WebServiceUpdate.this);
            _objEntLibDBTools = new EntLibDBTools(WebServiceUpdate.this);
            resultReceiver = intent.getParcelableExtra("receiver");
            getValues(intent);
            timerTask = new MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 60 * 1000, 60 * 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void getValues(Intent intent) {
        Bundle b = intent.getExtras();
        _objUsuario = new Usuario();
        _objUsuario.Clave = b.getInt("Clave");
        _objUsuario.RFC = b.getString("RFC");
    }

    class MyTimerTask extends TimerTask {
        public MyTimerTask() {
        }

        @Override
        public void run() {
            if (ConexionInternet()) {
                getPlaneacionRuta jobGetPlaneacionRuta = new getPlaneacionRuta();
                jobGetPlaneacionRuta.execute();
            }
        }

        private class getPlaneacionRuta extends AsyncTask<String, Integer, Boolean> {
            //Variables
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            List<RutaInspeccion> listRutaInspeccion;
            List<RutaInspeccion> listRelacionRiego;
            List<RutaInspeccion> listRelacionRecomendacion;

            protected Boolean doInBackground(String... params) {
                boolean resultUpdate = true;
                try {
                    resultUpdate = _objWebServiceBP.getPlaneacionRuta(_objUsuario.RFC, _objUsuario.Clave, currentDateandTime);
                } catch (Exception e) {
                    resultUpdate = false;
                    e.printStackTrace();
                }
                return resultUpdate;
            }

            protected void onPostExecute(Boolean result) {
                if (result)
                    resultReceiver.send(0, null);
            }
        }
    }

    private boolean ConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
