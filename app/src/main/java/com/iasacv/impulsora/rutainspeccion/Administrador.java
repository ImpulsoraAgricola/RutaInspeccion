package com.iasacv.impulsora.rutainspeccion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
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
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Conexion.GPSTracker;
import com.iasacv.impulsora.rutainspeccion.Modelo.*;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;
import com.iasacv.impulsora.rutainspeccion.Servicios.WebServicePlaneacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 28/06/2015.
 */
public class Administrador extends ActionBarActivity {

    //Variables
    ComunBP _objComunBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    WebServiceBP _objWebServiceBP;
    Usuario _objUsuario;
    EntLibDBTools _objEntLibDBTools;
    GPSTracker gpsTracker;

    GridView gridView;
    ArrayList<Item> listaRutaInspeccion;
    CustomGridViewAdapter customGridAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Intent intent;
    MyResultReceiver resultReceiver;
    ProgressDialog loadProgressDialog;

    Calendar c;
    private String mYear;
    private String mMonth;
    private String mDay;
    static final int DATE_DIALOG_ID = 0;
    public static boolean flagStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        try {
            _objComunBP = new ComunBP(Administrador.this);
            _objRutaInspeccionBP = new RutaInspeccionBP(Administrador.this);
            gridView = (GridView) findViewById(R.id.gridView);

            //Inicializar fecha
            c = Calendar.getInstance();
            mYear = String.valueOf(c.get(Calendar.YEAR));
            mMonth = String.format("%02d", c.get(Calendar.MONTH)+ 1);
            mDay = String.format("%02d", c.get(Calendar.DAY_OF_MONTH));

            //Obtener usuario
            getPreferences();

            if (!flagStart) {
                flagStart = true;
                //Llenar grid
                _objWebServiceBP = new WebServiceBP(Administrador.this);
                getPlaneacionRuta();
            }
            else
                refresh();

            //Iniciar servicio
            callService();

            //Crear el refrescar al momento de deslizar
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        getPlaneacionRuta();
                        mSwipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    try {
                        Item objItem = listaRutaInspeccion.get(position);
                        RutaInspeccion objRutaInspeccion = creaObjeto(objItem);
                        RutaInspeccion objTemp = _objRutaInspeccionBP.GetRutaInspeccionCabecero(objRutaInspeccion);
                        if (objTemp.Estatus != null) {
                            if (objTemp.Estatus.equals("G") || objTemp.Estatus.equals("E") || objTemp.Estatus.equals("F") || objTemp.Estatus.equals("R"))
                                iniciarRutaInspeccion(objItem);
                            else
                                confirmDialogStart(objItem);
                        } else
                            confirmDialogStart(objItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_administrador_actualizar:
                getPlaneacionRuta();
                return true;
            case R.id.menu_administrador_respaldo:
                try {
                    _objEntLibDBTools = new EntLibDBTools(Administrador.this);
                    _objEntLibDBTools.exportDataBase();
                    _objComunBP.Mensaje("Se ha realizado el respaldo correctamente", Administrador.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
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
                refresh();
                _objComunBP.Mensaje("Se debe contar con una conexi\u00F3n a Internet", Administrador.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                result = _objWebServiceBP.getPlaneacionRuta(_objUsuario.RFC, _objUsuario.Clave, currentDateandTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                _objComunBP.Mensaje("Error: La informaci\u00F3n no se pudo actualizar. Favor de revisar la hora de su dispositivo", Administrador.this);
            } else {
                refresh();
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

    public void refresh() {
        try {
            String fecha = mYear + "-" + (mMonth) + "-" + mDay;
            listaRutaInspeccion = _objRutaInspeccionBP.GetAllPlaneacionRutaImage(_objUsuario.Clave, fecha);
            customGridAdapter = new CustomGridViewAdapter(this, R.layout.activity_gridrow, listaRutaInspeccion);
            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(customGridAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callService() {
        resultReceiver = new MyResultReceiver(new Handler());
        intent = new Intent(Administrador.this, WebServicePlaneacion.class);
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
            refresh();
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
        refresh();
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
                DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, Integer.parseInt(mYear), Integer.parseInt(mMonth)-1, Integer.parseInt(mDay));
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
                    public void onClick(final DialogInterface dialog, int which) {
                        alert.dismiss();
                        try {
                            //Obtener localizacion
                            gpsTracker = new GPSTracker(Administrador.this);
                            if (gpsTracker.getIsGPSTrackingEnabled()) {
                                //Llenar objeto filtro
                                PlaneacionRuta objFiltro = new PlaneacionRuta();
                                objFiltro.CicloClave = objItem.getCicloClave();
                                objFiltro.UsuarioClave = objItem.getUsuarioClave();
                                objFiltro.Folio = objItem.getFolio();
                                objFiltro.Fecha = objItem.getFecha();
                                PlaneacionRuta objLote = _objRutaInspeccionBP.GetPlaneacionRuta(objFiltro);
                                if ((objLote.LoteLatitud <= (gpsTracker.getLatitude() + 0.00192) && (objLote.LoteLatitud) >= (gpsTracker.getLatitude() - 0.00192)) &&
                                        ((objLote.LoteLongitud) <= (gpsTracker.getLongitude() + 0.001802) && (objLote.LoteLongitud) >= (gpsTracker.getLongitude() - 0.001802))) {
                                    RutaInspeccion objRutaInspeccion = creaObjeto(objItem);
                                    RutaInspeccion objTemp = _objRutaInspeccionBP.GetRutaInspeccionCabecero(objRutaInspeccion);
                                    if (objTemp.Folio == 0) {
                                        try {
                                            _objRutaInspeccionBP.InsertRutaInspeccion(objRutaInspeccion);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        iniciarRutaInspeccion(objItem);
                                    } else if (objTemp.Estatus.equals("O")) {
                                        confirmInicio(objItem);
                                    }
                                } else {
                                    _objComunBP.Mensaje("Error: Su localizacion para registrar la ruta de inspeccion no es correcta", Administrador.this);
                                }
                                gpsTracker.stopUsingGPS();
                            } else {
                                final AlertDialog alert = new AlertDialog.Builder(
                                        new ContextThemeWrapper(Administrador.this, android.R.style.Theme_Dialog))
                                        .create();
                                alert.setTitle("Mensaje");
                                alert.setMessage("El GPS no esta activado. \u00BFDesea activarlo?");
                                alert.setCancelable(false);
                                alert.setIcon(R.drawable.info);
                                alert.setCanceledOnTouchOutside(false);
                                alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                alert.dismiss();
                                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                Administrador.this.startActivity(intent);
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
                            loadProgressDialog.dismiss();
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
        objRutaInspeccion.Estatus = "O";
        return objRutaInspeccion;
    }

    public void iniciarRutaInspeccion(Item objItem) {
        if (objItem.getTipoInspeccion() == 1) {
            //Creamos el nuevo formulario
            Intent i = new Intent(Administrador.this, InspeccionCampo.class);
            i.putExtra("CicloClave", objItem.getCicloClave());
            i.putExtra("UsuarioClave", objItem.getUsuarioClave());
            i.putExtra("Folio", objItem.getFolio());
            i.putExtra("Fecha", objItem.getFecha());
            startActivity(i);
        } else if (objItem.getTipoInspeccion() == 2) {
            //Creamos el nuevo formulario
            Intent i = new Intent(Administrador.this, DiagnosticoCultivo.class);
            i.putExtra("CicloClave", objItem.getCicloClave());
            i.putExtra("UsuarioClave", objItem.getUsuarioClave());
            i.putExtra("Folio", objItem.getFolio());
            i.putExtra("Fecha", objItem.getFecha());
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage("\u00BFDeseas salir de la aplicaci\u00F3n?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.info);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
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
}
