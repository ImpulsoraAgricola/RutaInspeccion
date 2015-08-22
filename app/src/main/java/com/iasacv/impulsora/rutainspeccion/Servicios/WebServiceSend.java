package com.iasacv.impulsora.rutainspeccion.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.RutaInspeccionDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 21/08/2015.
 */
public class WebServiceSend extends Service {

    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;
    Usuario _objUsuario;

    private static final int NOTIF_ALERTA_ID = 1;

    WebServiceBP _objWebServiceBP;
    RutaInspeccionDA _objRutaInspeccionDA;
    PlaneacionRutaDA _objPlaneacionRutaDA;
    EntLibDBTools _objEntLibDBTools;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            _objWebServiceBP = new WebServiceBP(WebServiceSend.this);
            _objRutaInspeccionDA = new RutaInspeccionDA(WebServiceSend.this);
            _objPlaneacionRutaDA = new PlaneacionRutaDA(WebServiceSend.this);
            _objEntLibDBTools = new EntLibDBTools(WebServiceSend.this);
            getValues(intent);
            timerTask = new MyTimerTask();
            timer.scheduleAtFixedRate(timerTask, 60 * 1000, 60 * 2000);
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
                boolean resultSend = true;
                try {
                    listRutaInspeccion = _objRutaInspeccionDA.GetAllRutaInspeccion();
                    listRelacionRiego = _objRutaInspeccionDA.GetAllRelacionRiego();
                    listRelacionRecomendacion = _objRutaInspeccionDA.GetAllRelacionRecomendacion();
                    if (listRutaInspeccion.size() > 0) {
                        for (int i = 0; i < listRutaInspeccion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRutaInspeccion.get(i);
                            _objWebServiceBP.insertRutaInspeccion(_objUsuario.RFC, objRutaInspeccion);
                        }
                        for (int i = 0; i < listRelacionRiego.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRelacionRiego.get(i);
                            _objWebServiceBP.insertRelacionTipoRiego(_objUsuario.RFC, objRutaInspeccion);
                        }
                        for (int i = 0; i < listRelacionRecomendacion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRelacionRecomendacion.get(i);
                            _objWebServiceBP.insertRelacionRecomendacion(_objUsuario.RFC, objRutaInspeccion);
                        }
                    } else
                        resultSend = false;
                } catch (Exception e) {
                    resultSend = false;
                    e.printStackTrace();
                }
                return resultSend;
            }

            protected void onPostExecute(Boolean result) {
                if (result) {
                    _objEntLibDBTools.exportDataBase();
                    if (listRutaInspeccion.size() > 0) {
                        for (int i = 0; i < listRutaInspeccion.size(); i++) {
                            RutaInspeccion objRutaInspeccion = (RutaInspeccion) listRutaInspeccion.get(i);
                            objRutaInspeccion.Estatus = "R";
                            boolean resul = _objRutaInspeccionDA.UpdateRutaInspeccion(objRutaInspeccion);
                            PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
                            objPlaneacionRuta.UsuarioClave = objRutaInspeccion.UsuarioClave;
                            objPlaneacionRuta.CicloClave = objRutaInspeccion.CicloClave;
                            objPlaneacionRuta.Fecha = objRutaInspeccion.Fecha;
                            objPlaneacionRuta.Folio = objRutaInspeccion.Folio;
                            objPlaneacionRuta.Estatus = "R";
                            _objPlaneacionRutaDA.UpdatePlaneacionRutaEstatus(objPlaneacionRuta);
                        }
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(WebServiceSend.this)
                                        .setSmallIcon(android.R.drawable.ic_dialog_map)
                                        .setContentTitle("Rutas de Inspecci\u00F3n")
                                        .setContentText("La informaci\u00F3n fue enviada correctamente.")
                                        .setTicker("La informacion fue enviada correctamente.");
                        Intent notIntent = new Intent(WebServiceSend.this, WebServiceSend.class);
                        PendingIntent contIntent = PendingIntent.getActivity(WebServiceSend.this, 0, notIntent, 0);
                        mBuilder.setContentIntent(contIntent);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
                    }
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

