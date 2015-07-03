package com.iasacv.impulsora.rutainspeccion.Broadcast;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.iasacv.impulsora.rutainspeccion.Adaptador.CustomGridViewAdapter;
import com.iasacv.impulsora.rutainspeccion.Administrador;
import com.iasacv.impulsora.rutainspeccion.Modelo.Item;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;
import com.iasacv.impulsora.rutainspeccion.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 02/07/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{

    Context _objContext;
    Intent _objIntent;
    Usuario _objUsuario;
    WebServiceBP _objWebServiceBP;
    ComunBP _objComunBP;
    GridView gridView;
    ArrayList<Item> listaRutaInspeccion;
    RutaInspeccionBP _objRutaInspeccionBP;
    CustomGridViewAdapter customGridAdapter;

    @Override
    public void onReceive(Context context, Intent intent) {
        _objContext = context;
        _objIntent = intent;
        getValues(intent);
        _objWebServiceBP = new WebServiceBP(context);
        _objComunBP = new ComunBP(context);
        _objRutaInspeccionBP = new RutaInspeccionBP(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_administrador, null);
        gridView = (GridView) v.findViewById(R.id.gridView);
        if(ConexionInternet()) {
            getPlaneacionRuta jobGetPlaneacionRuta = new getPlaneacionRuta();
            jobGetPlaneacionRuta.execute();
        }
    }

    private class getPlaneacionRuta extends AsyncTask<String, Integer, Boolean> {
        //Variables
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

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
                _objComunBP.Mensaje(e.toString(), _objContext);
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                llenarGrid();
            }
        }
    }

    private void getValues(Intent intent) {
        Bundle b = intent.getExtras();
        _objUsuario = new Usuario();
        _objUsuario.Clave = b.getInt("Clave");
        _objUsuario.RFC = b.getString("RFC");
    }

    private boolean ConexionInternet(){
        ConnectivityManager cm = (ConnectivityManager) _objContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void llenarGrid(){
        listaRutaInspeccion = _objRutaInspeccionBP.GetAllPlaneacionRutaImage();
        Administrador objAdmin = new Administrador();
        objAdmin.refresh(listaRutaInspeccion);
    }
}
