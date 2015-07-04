package com.iasacv.impulsora.rutainspeccion;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import java.util.Calendar;

/**
 * Created by Administrator on 19/06/2015.
 */
public class RutaInspeccion extends Activity {

    //Variables para controles
    private Spinner administrador_sCiclo;
    private Button administrador_btnGuardar;
    private TextView administrador_txtFecha;
    private ImageButton administrador_btnFecha;

    //Variables
    WebServiceBP _objWebServiceBP;
    CatalogosBP _objCicloBP;
    Usuario _objUsuario;
    Ciclo _objCiclo;
    RutaInspeccion _objRutaInspeccion;
    private Ciclo[] listaCiclos;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Pasar contexto a las demas instancias
        _objWebServiceBP = new WebServiceBP(this);
        _objCicloBP = new CatalogosBP(this);
        _objUsuario = new Usuario();
        _objCiclo = new Ciclo();
        _objRutaInspeccion = new RutaInspeccion();

        Bundle b = getIntent().getExtras();
        int Folio = b.getInt("Folio");

        setContentView(R.layout.activity_rutainspeccion);

        //Recuperar valores
        getPreferences();

        administrador_btnFecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        //Guardar registro
        administrador_btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _objRutaInspeccion = creaObjeto();
                insertRutaInspeccion tarea = new insertRutaInspeccion();
                tarea.execute();
            }
        });

        llenarCombos();
    }

    private class insertRutaInspeccion extends AsyncTask<String,Integer,Boolean> {

        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(RutaInspeccion.this, "", "Insertando informacion...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try
            {
                result = _objWebServiceBP.insertRutaInspeccion(_objRutaInspeccion);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result)
            {
                Toast toastCorrecto = Toast.makeText(getApplicationContext(),
                        "La informacion se inserto correctamente!!", Toast.LENGTH_LONG);
                toastCorrecto.show();
            }
            else
            {
                Toast toastError = Toast.makeText(getApplicationContext(),
                        "Error: Se produjo un error al insertar la informacion", Toast.LENGTH_LONG);
                toastError.show();
            }
            loadProgressDialog.dismiss();
        }
    }

    //Eventos de calendario
    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateDisplay() {
        this.administrador_txtFecha.setText(
                new StringBuilder()
                        .append(mDay).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    //Llenar objetos
    public RutaInspeccion creaObjeto() {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        objRutaInspeccion.UsuarioClave = _objUsuario.Clave;
        objRutaInspeccion.Fecha = this.administrador_txtFecha.getText().toString();
        objRutaInspeccion.CicloClave = _objCiclo.Clave;
        return objRutaInspeccion;
    }

    //Rellenamos la lista con los nombres de los ciclos
    public void llenarCombos(){
        //Variables
        listaCiclos = _objCicloBP.GetAllCiclos();
        String[] ciclos = new String[listaCiclos.length];
        for(int i=0; i<listaCiclos.length; i++)
            ciclos[i] = listaCiclos[i].Nombre;
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(RutaInspeccion.this, android.R.layout.simple_list_item_1, ciclos);
        administrador_sCiclo = (Spinner)findViewById(R.id.administrador_sCiclo);
        administrador_sCiclo.setAdapter(adaptador);

        //Eventos del combo
        administrador_sCiclo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        parent.getItemAtPosition(position);
                        _objCiclo = listaCiclos[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        _objCiclo = null;
                    }
                });
    }

    private void getPreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        _objUsuario = new Usuario();
        _objUsuario.RFC = prefs.getString("RFC", "");
        _objUsuario.Clave = Integer.valueOf(prefs.getString("Clave", ""));
    }

    private void getControles(){
        administrador_txtFecha = (TextView)findViewById(R.id.administrador_txtFecha);
        administrador_btnFecha = (ImageButton)findViewById(R.id.administrador_btnFecha);
        administrador_btnGuardar = (Button)findViewById(R.id.administrador_btnGuardar);
    }
}
