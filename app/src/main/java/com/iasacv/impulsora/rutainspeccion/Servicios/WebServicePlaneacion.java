package com.iasacv.impulsora.rutainspeccion.Servicios;

import java.io.IOException;
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
import com.iasacv.impulsora.rutainspeccion.Datos.RutaInspeccionDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by Administrator on 02/07/2015.
 */
public class WebServicePlaneacion extends Service {

    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;
    Usuario _objUsuario;

    WebServiceBP _objWebServiceBP;
    RutaInspeccionDA _objRutaInspeccionDA;
    EntLibDBTools _objEntLibDBTools;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            _objWebServiceBP = new WebServiceBP(this);
            _objRutaInspeccionDA = new RutaInspeccionDA(this);
            _objEntLibDBTools = new EntLibDBTools(this);
            resultReceiver = intent.getParcelableExtra("receiver");
            getValues(intent);
            timerTask = new MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 1 * 60 * 1000, 10 * 60 * 1000);
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
                boolean result = true;
                try {
                    result = _objWebServiceBP.getPlaneacionRuta(_objUsuario.RFC, _objUsuario.Clave, currentDateandTime);
                    listRutaInspeccion = _objRutaInspeccionDA.GetAllRutaInspeccion();
                    listRelacionRiego = _objRutaInspeccionDA.GetAllRelacionRiego();
                    listRelacionRecomendacion = _objRutaInspeccionDA.GetAllRelacionRecomendacion();
                    if (listRutaInspeccion.size() > 0) {
                        for (int i = 0; i < listRutaInspeccion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRutaInspeccion.get(i);
                            result = _objWebServiceBP.insertRutaInspeccion(_objUsuario.RFC, objRutaInspeccion);
                        }
                        for (int i = 0; i < listRelacionRiego.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRelacionRiego.get(i);
                            result = _objWebServiceBP.insertRelacionTipoRiego(_objUsuario.RFC, objRutaInspeccion);
                        }
                        for (int i = 0; i < listRelacionRecomendacion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRelacionRecomendacion.get(i);
                            result = _objWebServiceBP.insertRelacionRecomendacion(_objUsuario.RFC, objRutaInspeccion);
                        }
                    } else
                        result = false;
                } catch (Exception e) {
                    result = false;
                    e.printStackTrace();
                }
                return result;
            }

            protected void onPostExecute(Boolean result) {
                if (result) {
                    _objEntLibDBTools.exportDataBase();
                    if (listRutaInspeccion.size() > 0) {
                        for (int i = 0; i < listRutaInspeccion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRutaInspeccion.get(i);
                            objRutaInspeccion.Estatus = "R";
                            boolean resul = _objRutaInspeccionDA.UpdateRutaInspeccion(objRutaInspeccion);
                        }
                    }
                    resultReceiver.send(0, null);
                }
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
