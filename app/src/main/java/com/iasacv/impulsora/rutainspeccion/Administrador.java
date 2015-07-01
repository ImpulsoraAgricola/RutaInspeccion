package com.iasacv.impulsora.rutainspeccion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Negocios.CicloBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28/06/2015.
 */
public class Administrador extends ActionBarActivity {

    CicloBP objCicloBP;
    ComunBP _objComunBP;
    private GridView gridView;
    public static ArrayList<String> ArrayofName;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objCicloBP = new CicloBP(this);
        _objComunBP = new ComunBP(this);
        setContentView(R.layout.activity_administrador);
        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                _objComunBP.Mensaje(((TextView) v).getText().toString(), getApplicationContext());
                Intent i = new Intent(getApplicationContext(), RutaInspeccion.class);
                startActivity(i);
            }
        });

        //Crear el refrescar al momento de deslizar
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorScheme(R.color.blue,
                R.color.green, R.color.orange, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCiclos();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //Llnenar grid
        getCiclos();
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
                //Revisar conexion a Internet
                /*if (ConexionInternet()) {
                    getCatalogos tarea = new getCatalogos();
                    tarea.execute();
                } else
                    Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet");*/
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

    private void getCiclos() {
        ArrayofName = new ArrayList<String>();
        List<Ciclo> listaCiclos = new ArrayList<Ciclo>();
        listaCiclos = objCicloBP.GetAllCiclosList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, ArrayofName);
        gridView.setAdapter(adapter);
    }
}
