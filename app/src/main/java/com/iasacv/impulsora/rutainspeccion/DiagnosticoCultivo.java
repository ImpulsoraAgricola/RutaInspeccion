package com.iasacv.impulsora.rutainspeccion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import java.util.List;

/**
 * Created by Administrator on 19/06/2015.
 */

public class DiagnosticoCultivo extends ActionBarActivity {

    //Variables para controles
    private EditText rutainspeccion_txtFolio;
    private Spinner rutainspeccion_sCiclo;
    private EditText rutainspeccion_txtCliente;
    private EditText rutainspeccion_txtProductor;
    private EditText rutainspeccion_txtPredio;
    private EditText rutainspeccion_txtLote;
    private EditText rutainspeccion_txtFecha;
    private Button rutainspeccion_btnGuardar;
    private Spinner rutainspeccion_sSistemaProduccion;
    private Spinner rutainspeccion_sDesarrollo;

    //Variables
    WebServiceBP _objWebServiceBP;
    CatalogosBP _objCicloBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    Usuario _objUsuario;
    Ciclo _objCiclo;
    RutaInspeccion _objRutaInspeccion;
    PlaneacionRuta _objPlaneacionRuta;
    private Ciclo[] listaCiclos;

    int CicloClave;
    int UsuarioClave;
    int Folio;
    String Fecha;
    private String mYear;
    private String mMonth;
    private String mDay;

    static final int DATE_DIALOG_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_cultivo);

        //Pasar contexto a las demas instancias
        _objWebServiceBP = new WebServiceBP(this);
        _objCicloBP = new CatalogosBP(this);
        _objRutaInspeccionBP = new RutaInspeccionBP(this);
        _objUsuario = new Usuario();
        _objCiclo = new Ciclo();
        _objRutaInspeccion = new RutaInspeccion();
        _objPlaneacionRuta = new PlaneacionRuta();

        try {
            //Recuperar valores
            getPreferences();

            //Obtener controles
            getControles();

            llenarCombos();

            eventosCombo();

            cargarCabecero();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class insertRutaInspeccion extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(DiagnosticoCultivo.this, "", "Insertando informacion...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                result = _objWebServiceBP.insertRutaInspeccion(_objRutaInspeccion);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast toastCorrecto = Toast.makeText(getApplicationContext(),
                        "La informacion se inserto correctamente!!", Toast.LENGTH_LONG);
                toastCorrecto.show();
            } else {
                Toast toastError = Toast.makeText(getApplicationContext(),
                        "Error: Se produjo un error al insertar la informacion", Toast.LENGTH_LONG);
                toastError.show();
            }
            loadProgressDialog.dismiss();
        }
    }

    //Llenar objetos
    public RutaInspeccion creaObjeto() {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        objRutaInspeccion.UsuarioClave = _objUsuario.Clave;
        objRutaInspeccion.Fecha = this.rutainspeccion_txtFecha.getText().toString();
        objRutaInspeccion.CicloClave = _objCiclo.Clave;
        return objRutaInspeccion;
    }

    public void llenarCombos() {
        List<Combo> listaCiclos = _objCicloBP.GetAllCicloCombo();
        ArrayAdapter<Combo> adapterCiclo = new ArrayAdapter<Combo>(DiagnosticoCultivo.this, android.R.layout.simple_spinner_item, listaCiclos);
        rutainspeccion_sCiclo.setAdapter(adapterCiclo);

        //Desarrolo de cultivo
        List<Combo> listaEtapaFenologica = _objCicloBP.GetAllEtapaFenologicaCombo();
        ArrayAdapter<Combo> adapterEtapaFenologica = new ArrayAdapter<Combo>(DiagnosticoCultivo.this, android.R.layout.simple_spinner_item, listaEtapaFenologica);
        rutainspeccion_sDesarrollo.setAdapter(adapterEtapaFenologica);
    }

    private void getPreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        _objUsuario = new Usuario();
        _objUsuario.RFC = prefs.getString("RFC", "");
        _objUsuario.Clave = Integer.valueOf(prefs.getString("Clave", ""));
        Bundle b = getIntent().getExtras();
        CicloClave = b.getInt("CicloClave");
        UsuarioClave = b.getInt("UsuarioClave");
        Folio = b.getInt("Folio");
        Fecha = b.getString("Fecha");
    }

    private void getControles() {
        rutainspeccion_txtFolio = (EditText) findViewById(R.id.rutainspeccion_txtFolio);
        rutainspeccion_sCiclo = (Spinner) findViewById(R.id.rutainspeccion_sCiclo);
        rutainspeccion_txtCliente = (EditText) findViewById(R.id.rutainspeccion_txtCliente);
        rutainspeccion_txtProductor = (EditText) findViewById(R.id.rutainspeccion_txtProductor);
        rutainspeccion_txtPredio = (EditText) findViewById(R.id.rutainspeccion_txtPredio);
        rutainspeccion_txtLote = (EditText) findViewById(R.id.rutainspeccion_txtLote);
        rutainspeccion_txtFecha = (EditText) findViewById(R.id.rutainspeccion_txtFecha);
        rutainspeccion_btnGuardar = (Button) findViewById(R.id.rutainspeccion_btnGuardar);
        //rutainspeccion_sSistemaProduccion = (Spinner) findViewById(R.id.rutainspeccion_sSistemaProduccion);
        rutainspeccion_sDesarrollo = (Spinner) findViewById(R.id.rutainspeccion_sDesarrollo);
    }

    private void cargarCabecero() {
        PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
        objPlaneacionRuta.Folio = Folio;
        _objPlaneacionRuta = _objRutaInspeccionBP.GetPlaneacionRuta(objPlaneacionRuta);
        rutainspeccion_txtFolio.setText(String.valueOf(_objPlaneacionRuta.Folio));
        seleccionarValor(rutainspeccion_sCiclo, String.valueOf(_objPlaneacionRuta.CicloNombre));
        rutainspeccion_txtCliente.setText(_objPlaneacionRuta.ClienteNombre);
        rutainspeccion_txtProductor.setText(_objPlaneacionRuta.ProductorNombre);
        rutainspeccion_txtPredio.setText(_objPlaneacionRuta.PredioNombre);
        rutainspeccion_txtLote.setText(_objPlaneacionRuta.LoteNombre);
        rutainspeccion_txtFecha.setText(_objPlaneacionRuta.Fecha);
    }

    private void seleccionarValor(Spinner _objSpinner, String valor) {
        ArrayAdapter<Combo> objArrayAdapter = (ArrayAdapter<Combo>) _objSpinner.getAdapter();
        for (int i = 0; i < objArrayAdapter.getCount(); i++) {
            if (((Combo) objArrayAdapter.getItem(i)).getNombre().equals(valor)) {
                _objSpinner.setSelection(i);
            }
        }
    }

    private void eventosCombo() {
        //Etapa fenologica
        rutainspeccion_sDesarrollo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        Combo objCombo;
                        if(!(rutainspeccion_sDesarrollo.getSelectedItem() == null))
                            objCombo = (Combo)rutainspeccion_sDesarrollo.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        _objCiclo = null;
                    }
                });
    }
}

