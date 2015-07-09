package com.iasacv.impulsora.rutainspeccion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;

import com.iasacv.impulsora.rutainspeccion.Adaptador.CustomGridViewAdapter;
import com.iasacv.impulsora.rutainspeccion.Conexion.GPSTracker;
import com.iasacv.impulsora.rutainspeccion.Modelo.*;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;
import com.iasacv.impulsora.rutainspeccion.Servicios.WebService;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 28/06/2015.
 */
public class Administrador extends ActionBarActivity {

    //Variables
    CatalogosBP _objCatalogosBP;
    ComunBP _objComunBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    WebServiceBP _objWebServiceBP;
    Usuario _objUsuario;

    GridView gridView;
    ArrayList<Item> listaRutaInspeccion;
    CustomGridViewAdapter customGridAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SimpleDateFormat formatFecha;
    String currentDate;

    Intent intent;
    MyResultReceiver resultReceiver;

    private String mYear;
    private String mMonth;
    private String mDay;
    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        _objCatalogosBP = new CatalogosBP(this);
        _objComunBP = new ComunBP(this);
        _objRutaInspeccionBP = new RutaInspeccionBP(this);
        _objWebServiceBP = new WebServiceBP(this);

        formatFecha = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = formatFecha.format(new Date());

        gridView = (GridView) findViewById(R.id.gridView);

        //Obtener usuario
        getPreferences();

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
                try {
                    Item objItem = listaRutaInspeccion.get(position);
                    confirmDialogStart(objItem);
                } catch (Exception e) {
                    Mensaje(e.toString(), getApplicationContext());
                }
            }
        });

        //Llenar grid
        getPlaneacionRuta();

        //Iniciar servicio
        callService();
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
                confirmDialogExit();
                return true;
            case R.id.search:
                showDialog(DATE_DIALOG_ID);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removePreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }

    private void confirmDialogExit() {
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
                formatFecha = new SimpleDateFormat("yyyy-MM-dd");
                currentDate = formatFecha.format(new Date());
                refresh(currentDate);
                Mensaje("Se debe contar con una conexi\u00F3n a Internet", getApplicationContext());
            }
        } catch (Exception e) {
            Mensaje(e.toString(), getApplicationContext());
        }
    }

    private class getPlaneacionRuta extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Administrador.this, "Rutas de Inspecci\u00F3n", "Actualizando planeaci\u00F3n de rutas de inspecci\u00F3n...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                try {
                    result = _objWebServiceBP.getPlaneacionRuta(_objUsuario.RFC, _objUsuario.Clave, currentDateandTime);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Mensaje(e.toString(), getApplicationContext());
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                Mensaje("Error: La informaci\u00F3n no se pudo actualizar", getApplicationContext());
            } else {
                formatFecha = new SimpleDateFormat("yyyy-MM-dd");
                currentDate = formatFecha.format(new Date());
                refresh(currentDate);
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

    public void refresh(String fecha) {
        try {
            listaRutaInspeccion = _objRutaInspeccionBP.GetAllPlaneacionRutaImage(_objUsuario.Clave, fecha);
            customGridAdapter = new CustomGridViewAdapter(this, R.layout.activity_gridrow, listaRutaInspeccion);
            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(customGridAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callService() {
        resultReceiver = new MyResultReceiver(null);
        intent = new Intent(this, WebService.class);
        intent.putExtra("receiver", resultReceiver);
        intent.putExtra("Clave", _objUsuario.Clave);
        intent.putExtra("RFC", _objUsuario.RFC);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    class UpdateUI implements Runnable {
        public UpdateUI() {
            formatFecha = new SimpleDateFormat("yyyy-MM-dd");
            currentDate = formatFecha.format(new Date());
            refresh(currentDate);
        }

        public void run() {
        }
    }

    class MyResultReceiver extends ResultReceiver {
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                runOnUiThread(new UpdateUI());
            }
        }
    }

    //Eventos de calendario
    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateDisplay() {
        refresh(mYear + "-" + (mMonth) + "-" + mDay);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = String.valueOf(year);
                    mMonth = String.format("%02d", monthOfYear + 1);
                    mDay = String.format("%02d", dayOfMonth);
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                final Calendar c = Calendar.getInstance();
                mYear = String.valueOf(c.get(Calendar.YEAR));
                mMonth = String.valueOf(c.get(Calendar.MONTH));
                mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, Integer.parseInt(mYear), Integer.parseInt(mMonth), Integer.parseInt(mDay));
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setMinDate(c.getTimeInMillis());
                c.add(Calendar.DAY_OF_YEAR, 1);
                datePicker.setMaxDate(c.getTimeInMillis());
                return dialog;
        }
        return null;
    }

    private void confirmDialogStart(final Item objItem) {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage("\u00BFDeseas iniciar la captura de la ruta de inpecci\u00F3n?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.info);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        try {
                            //Obtener localizacion
                            GPSTracker gpsTracker = new GPSTracker(Administrador.this);
                            if (gpsTracker.getIsGPSTrackingEnabled()) {
                                //Llenar objeto filtro
                                PlaneacionRuta objFiltro = new PlaneacionRuta();
                                objFiltro.CicloClave = objItem.getCicloClave();
                                objFiltro.UsuarioClave = objItem.getUsuarioClave();
                                objFiltro.Folio = objItem.getFolio();
                                PlaneacionRuta objLote = _objRutaInspeccionBP.GetPlaneacionRuta(objFiltro);
                                if ((objLote.LoteLatitud <= (gpsTracker.getLatitude() + 0.00192) && (objLote.LoteLatitud) >= (gpsTracker.getLatitude() - 0.00192)) &&
                                        ((objLote.LoteLongitud) <= (gpsTracker.getLongitude() + 0.001802) && (objLote.LoteLongitud) >= (gpsTracker.getLongitude() - 0.001802))) {
                                    formatFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    currentDate = formatFecha.format(new Date());
                                    RutaInspeccion objRutaInspeccion = creaObjeto(objItem);
                                    RutaInspeccion objTemp = _objRutaInspeccionBP.GetRutaInspeccion(objRutaInspeccion);
                                    if (objTemp.Folio == 0) {
                                        _objRutaInspeccionBP.InsertRutaInspeccionInicio(objRutaInspeccion);
                                        iniciarRutaInspeccion(objItem);
                                    }
                                    else {
                                        confirmInicio(objItem);
                                    }
                                } else {
                                    Mensaje("Error: Su localizacion para registrar la ruta de inspeccion no es correcta", getApplicationContext());
                                }
                            } else {
                                Mensaje("Error: Favor de habilitar GPS", getApplicationContext());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    private void confirmInicio(final Item objItem) {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage("Error: La captura de la informacion inicio anteriormente pero no se concluyo");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.info);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        iniciarRutaInspeccion(objItem);
                    }
                });
        alert.show();
    }

    public RutaInspeccion creaObjeto(Item objItem) {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        objRutaInspeccion.UsuarioClave = _objUsuario.Clave;
        objRutaInspeccion.CicloClave = objItem.getCicloClave();
        objRutaInspeccion.Fecha = objItem.getFecha();
        objRutaInspeccion.Folio = objItem.getFolio();
        //Obtener fecha
        formatFecha = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = formatFecha.format(new Date());
        objRutaInspeccion.FechaInicio = currentDate;
        //Obtener hora
        formatFecha = new SimpleDateFormat("HH:mm:ss");
        currentDate = formatFecha.format(new Date());
        objRutaInspeccion.HoraInicio = currentDate;
        objRutaInspeccion.Estatus = "O";
        objRutaInspeccion.Uso = "S";
        return objRutaInspeccion;
    }

    public void Mensaje(String mensaje,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rutas de Inspecci\u00F3n");
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar",null);
        builder.create();
        builder.show();
    }

    public void iniciarRutaInspeccion(Item objItem){
        if (objItem.getTipoInspeccion() == 1) {
            //Creamos el nuevo formulario
            Intent i = new Intent(Administrador.this, InspeccionCampo.class);
            i.putExtra("CicloClave", objItem.getCicloClave());
            i.putExtra("UsuarioClave", objItem.getUsuarioClave());
            i.putExtra("Folio", objItem.getFolio());
            i.putExtra("Fecha", currentDate);
            startActivity(i);
        } else if (objItem.getTipoInspeccion() == 2) {
            //Creamos el nuevo formulario
            Intent i = new Intent(Administrador.this, DiagnosticoCultivo.class);
            i.putExtra("CicloClave", objItem.getCicloClave());
            i.putExtra("UsuarioClave", objItem.getUsuarioClave());
            i.putExtra("Folio", objItem.getFolio());
            i.putExtra("Fecha", currentDate);
            startActivity(i);
        }
    }
}
