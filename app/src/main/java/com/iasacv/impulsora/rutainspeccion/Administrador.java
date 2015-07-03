package com.iasacv.impulsora.rutainspeccion;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Adaptador.CustomGridViewAdapter;
import com.iasacv.impulsora.rutainspeccion.Broadcast.AlarmReceiver;
import com.iasacv.impulsora.rutainspeccion.Modelo.*;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 28/06/2015.
 */
public class Administrador extends ActionBarActivity {

    //Variables
    CatalogosBP objCatalogosBP;
    ComunBP _objComunBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    WebServiceBP _objWebServiceBP;
    Usuario _objUsuario;

    GridView gridView;
    ArrayList<Item> listaRutaInspeccion;
    CustomGridViewAdapter customGridAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String currentDateandTime;

    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // correct

        objCatalogosBP = new CatalogosBP(this);
        _objComunBP = new ComunBP(this);
        _objRutaInspeccionBP = new RutaInspeccionBP(this);
        _objWebServiceBP = new WebServiceBP(this);

        gridView = (GridView) findViewById(R.id.gridView);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        //Obtener usuario
        getPreferences();

        //Llenar grid
        getPlaneacionRuta();

        //Crear el refrescar al momento de deslizar
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlaneacionRuta();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                parent.getItemAtPosition(position);
                Item objItem = listaRutaInspeccion.get(position);
                //Creamos el nuevo formulario
                Intent i = new Intent(Administrador.this, RutaInspeccion.class);
                i.putExtra("Folio", objItem.getFolio());
                startActivity(i);
            }
        });

        callWebServiceBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_administrador_actualizar:
                getPlaneacionRuta();
                return true;
            case R.id.menu_administrador_salir:
                confirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removePreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    private void confirmDialog() {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage("\u00BFDeseas cerrar sesi\u00F3n?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.shutdown);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        removePreferences();
                        //Creamos el nuevo formulario
                        Intent i = new Intent(Administrador.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                });
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });
        alert.show();
    }

    public void getPlaneacionRuta() {
        try {
            if (ConexionInternet()) {
                getPlaneacionRuta jobGetPlaneacionRuta = new getPlaneacionRuta();
                jobGetPlaneacionRuta.execute();
            } else {
                listaRutaInspeccion = _objRutaInspeccionBP.GetAllPlaneacionRutaImage();
                customGridAdapter = new CustomGridViewAdapter(Administrador.this, R.layout.activity_gridrow, listaRutaInspeccion);
                gridView.setAdapter(customGridAdapter);
                _objComunBP.Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet", getApplicationContext());
            }
        } catch (Exception e) {
            _objComunBP.Mensaje(e.toString(), getApplicationContext());
        }
    }

    private class getPlaneacionRuta extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Administrador.this, "Rutas de Inspecci\u00F3n", "Actualizando planeaci\u00F3n de rutas de inspecci\u00F3n...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                try {
                    result = _objWebServiceBP.getPlaneacionRuta(_objUsuario.RFC,_objUsuario.Clave,currentDateandTime);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                _objComunBP.Mensaje(e.toString(), getApplicationContext());
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (!result){
                _objComunBP.Mensaje("Error: La informaci\u00F3n no se pudo actualizar",getApplicationContext());
            }
            else {
                listaRutaInspeccion = _objRutaInspeccionBP.GetAllPlaneacionRutaImage();
                refresh(listaRutaInspeccion);
            }
            loadProgressDialog.dismiss();
        }
    }

    private void getPreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        _objUsuario = new Usuario();
        _objUsuario.RFC = prefs.getString("RFC", "");
        _objUsuario.Clave = Integer.valueOf(prefs.getString("Clave", ""));
    }

    private boolean ConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void callWebServiceBackground(){
        Date when = new Date(System.currentTimeMillis());
        try{
            Intent i = new Intent(this,AlarmReceiver.class);
            i.putExtra("Clave", _objUsuario.Clave);
            i.putExtra("RFC", _objUsuario.RFC);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            //alarms.setRepeating(AlarmManager.RTC_WAKEUP,when.getTime(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
            alarms.setRepeating(AlarmManager.RTC_WAKEUP,when.getTime(),2*60*1000,pendingIntent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refresh(ArrayList<Item> objlistaRutaInspeccion) {
        customGridAdapter = new CustomGridViewAdapter(Administrador.this, R.layout.activity_gridrow, objlistaRutaInspeccion);
        gridView.setAdapter(customGridAdapter);
    }
}
