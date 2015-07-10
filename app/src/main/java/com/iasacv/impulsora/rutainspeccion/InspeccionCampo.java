package com.iasacv.impulsora.rutainspeccion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.CatalogosBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.RutaInspeccionBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Administrator on 19/06/2015.
 */

public class InspeccionCampo extends ActionBarActivity {

    //Variables para controles
    //Datos generales
    private EditText rutainspeccion_txtFolio;
    private Spinner rutainspeccion_sCiclo;
    private EditText rutainspeccion_txtCliente;
    private EditText rutainspeccion_txtProductor;
    private EditText rutainspeccion_txtPredio;
    private EditText rutainspeccion_txtLote;
    private EditText rutainspeccion_txtFecha;
    //Ruta de inspeccion
    private RadioGroup rutainspeccion_rdRecomendacion;
    //Siembra
    private Spinner rutainspeccion_sSistemaProduccion;
    private Spinner rutainspeccion_sArregloTopologico;
    //Condiciones de la siembra
    private RadioGroup rutainspeccion_rdAdecuada;
    private RadioGroup rutainspeccion_rdSurco;
    private RadioGroup rutainspeccion_rdManejo;
    //Riego
    private CheckBox rutainspeccion_chbCanal;
    private CheckBox rutainspeccion_chbGravedad;
    private CheckBox rutainspeccion_chbAspersion;
    private CheckBox rutainspeccion_chbGoteo;
    private CheckBox rutainspeccion_chbPozo;
    private EditText rutainspeccion_txtCapacidad;
    private CheckBox rutainspeccion_chbRiegoOtro;
    private EditText rutainspeccion_txtRiegoOtro;
    //Etapa fenologica
    private Spinner rutainspeccion_sEtapa;
    private RadioGroup rutainspeccion_rdExposicion;
    //Condiciones de desarrollo
    private Spinner rutainspeccion_sCondicionDesarrollo;
    //Recomendaciones
    private CheckBox rutainspeccion_chbRegar;
    private CheckBox rutainspeccion_chbDesmezclar;
    private CheckBox rutainspeccion_chbFungicida;
    private CheckBox rutainspeccion_chbInsecticida;
    private CheckBox rutainspeccion_chbHerbicida;
    private CheckBox rutainspeccion_chbDesaguar;
    private CheckBox rutainspeccion_chbDescostrar;
    private CheckBox rutainspeccion_chbRecomendacionOtro;
    private EditText rutainspeccion_txtRecomendacionOtro;
    //Manejo de agroquimico
    private RadioGroup rutainspeccion_rdOrden;
    private RadioGroup rutainspeccion_rdRegula;
    private RadioGroup rutainspeccion_rdUso;
    private RadioGroup rutainspeccion_rdHora;
    private RadioGroup rutainspeccion_rdAgua;
    //Problemas
    private RadioGroup rutainspeccion_rdInundacion;
    private RadioGroup rutainspeccion_rdPoblacion;
    private RadioGroup rutainspeccion_rdProblema;
    private RadioGroup rutainspeccion_rdAlteracion;
    private RadioGroup rutainspeccion_rdAplicacion;
    private RadioGroup rutainspeccion_rdTemperatura;
    private RadioGroup rutainspeccion_rdFito;
    private RadioGroup rutainspeccion_rdPlaga;
    private Spinner rutainspeccion_sMaleza;
    private Spinner rutainspeccion_sEstadoMaleza;
    private Spinner rutainspeccion_sPlaga;
    private Spinner rutainspeccion_sEstadoPlaga;
    private Spinner rutainspeccion_sEnfermedad;
    private Spinner rutainspeccion_sEstadoEnfermedad;
    //Potencial de rendimiento
    private Spinner rutainspeccion_sPotencial;
    private Button rutainspeccion_btnGuardar;

    //Variables clases
    WebServiceBP _objWebServiceBP;
    CatalogosBP _objCatalogosBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    ComunBP _objComunBP;

    //Variables objetos
    Usuario _objUsuario;
    Ciclo _objCiclo;
    RutaInspeccion _objRutaInspeccion = new RutaInspeccion();
    PlaneacionRuta _objPlaneacionRuta = new PlaneacionRuta();

    private Ciclo[] listaCiclos;
    int CicloClave;
    int UsuarioClave;
    int Folio;
    String Fecha;

    static final int DATE_DIALOG_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_campo);

        //Pasar contexto a las demas instancias
        _objWebServiceBP = new WebServiceBP(this);
        _objCatalogosBP = new CatalogosBP(this);
        _objRutaInspeccionBP = new RutaInspeccionBP(this);
        _objComunBP = new ComunBP(this);
        _objUsuario = new Usuario();
        _objCiclo = new Ciclo();

        try {
            //Recuperar valores
            getPreferences();

            //Obtener controles
            getControles();

            llenarCombos();

            //eventosCombo();

            cargarCabecero();

            rutainspeccion_btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        try {
                            if (validar()) {
                                RutaInspeccion objRutaInspeccion = creaObjeto();
                                boolean resul = _objRutaInspeccionBP.UpdateRutaInspeccion(objRutaInspeccion);
                                _objComunBP.Mensaje("La informaci\u00F3n se guardo correctamente", InspeccionCampo.this);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class insertRutaInspeccion extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(InspeccionCampo.this, "", "Insertando informacion...", true, false);
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
        _objRutaInspeccion = _objRutaInspeccionBP.GetRutaInspeccionCabecero(_objRutaInspeccion);
        _objRutaInspeccion.RecomendacionTecnica = obtenerValorRadio(rutainspeccion_rdRecomendacion);
        _objRutaInspeccion.SistemaProduccionClave = obtenerValorSpinner(rutainspeccion_sSistemaProduccion);
        _objRutaInspeccion.ArregloTopologicoClave = obtenerValorSpinner(rutainspeccion_sArregloTopologico);
        _objRutaInspeccion.ProfundidadSiembra = obtenerValorRadio(rutainspeccion_rdAdecuada);
        _objRutaInspeccion.ProfundidadSurco = obtenerValorRadio(rutainspeccion_rdSurco);
        _objRutaInspeccion.ManejoAdecuado = obtenerValorRadio(rutainspeccion_rdAdecuada);
        _objRutaInspeccion.EtapaFenologicaClave = obtenerValorSpinner(rutainspeccion_sEtapa);
        _objRutaInspeccion.Exposicion = obtenerValorRadio(rutainspeccion_rdExposicion);
        _objRutaInspeccion.CondicionDesarrolloClave = obtenerValorSpinner(rutainspeccion_sCondicionDesarrollo);
        _objRutaInspeccion.OrdenCorrecto = obtenerValorRadio(rutainspeccion_rdOrden);
        _objRutaInspeccion.RegulaPh = obtenerValorRadio(rutainspeccion_rdRegula);
        _objRutaInspeccion.UsoAdecuado = obtenerValorRadio(rutainspeccion_rdUso);
        _objRutaInspeccion.HoraAplicacion = obtenerValorRadio(rutainspeccion_rdHora);
        _objRutaInspeccion.AguaCanal = obtenerValorRadio(rutainspeccion_rdAgua);
        _objRutaInspeccion.Inundacion = obtenerValorRadio(rutainspeccion_rdInundacion);
        _objRutaInspeccion.BajaPoblacion = obtenerValorRadio(rutainspeccion_rdPoblacion);
        _objRutaInspeccion.AplicacionNutrientes = obtenerValorRadio(rutainspeccion_rdProblema);
        _objRutaInspeccion.AlteracionCiclo = obtenerValorRadio(rutainspeccion_rdAlteracion);
        _objRutaInspeccion.AplicacionAgroquimicos = obtenerValorRadio(rutainspeccion_rdAplicacion);
        _objRutaInspeccion.AltasTemperaturas = obtenerValorRadio(rutainspeccion_rdTemperatura);
        _objRutaInspeccion.Fito = obtenerValorRadio(rutainspeccion_rdFito);
        _objRutaInspeccion.PlagasMalControladas = obtenerValorRadio(rutainspeccion_rdPlaga);
        _objRutaInspeccion.MalezaClave = obtenerValorSpinner(rutainspeccion_sMaleza);
        _objRutaInspeccion.EstadoMalezaClave = obtenerValorSpinner(rutainspeccion_sEstadoMaleza);
        _objRutaInspeccion.PlagaClave = obtenerValorSpinner(rutainspeccion_sPlaga);
        _objRutaInspeccion.EstadoPlagaClave = obtenerValorSpinner(rutainspeccion_sEstadoPlaga);
        _objRutaInspeccion.EnfermedadClave = obtenerValorSpinner(rutainspeccion_sEnfermedad);
        _objRutaInspeccion.EstadoEnfermedadClave = obtenerValorSpinner(rutainspeccion_sEstadoEnfermedad);
        _objRutaInspeccion.PotencialRendimientoClave = obtenerValorSpinner(rutainspeccion_sPotencial);
        _objRutaInspeccion.Estatus = "I";
        _objRutaInspeccion.Uso = "S";
        return _objRutaInspeccion;
    }

    public void llenarCombos() {
        //Ciclo
        List<Combo> listaCiclos = _objCatalogosBP.GetAllCicloCombo();
        ArrayAdapter<Combo> adapterCiclo = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaCiclos);
        rutainspeccion_sCiclo.setAdapter(adapterCiclo);

        //Sistema de produccion
        List<Combo> listaSistemaProduccion = _objCatalogosBP.GetAllSistemaProduccionCombo();
        ArrayAdapter<Combo> adapterSistemaProduccion = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaSistemaProduccion);
        rutainspeccion_sSistemaProduccion.setAdapter(adapterSistemaProduccion);

        //Arreglo topologico
        List<Combo> listaArregloTopologico = _objCatalogosBP.GetAllArregloTopologicoCombo();
        ArrayAdapter<Combo> adapterArregloTopologico = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaArregloTopologico);
        rutainspeccion_sArregloTopologico.setAdapter(adapterArregloTopologico);

        //Etapa fenologica
        List<Combo> listaEtapaFenologica = _objCatalogosBP.GetAllEtapaFenologicaCombo();
        ArrayAdapter<Combo> adapterEtapaFenologica = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaEtapaFenologica);
        rutainspeccion_sEtapa.setAdapter(adapterEtapaFenologica);

        //Condiciones de desarrollo
        List<Combo> listaCondicionDesarrollo = _objCatalogosBP.GetAllCondicionDesarrolloCombo();
        ArrayAdapter<Combo> adapterCondicionDesarrollo = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaCondicionDesarrollo);
        rutainspeccion_sCondicionDesarrollo.setAdapter(adapterCondicionDesarrollo);

        //Plagas
        List<Combo> listaPlaga = _objCatalogosBP.GetAllPlagaCombo();
        ArrayAdapter<Combo> adapterPlaga = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaPlaga);
        rutainspeccion_sPlaga.setAdapter(adapterPlaga);

        //Malezas
        List<Combo> listaMaleza = _objCatalogosBP.GetAllMalezaCombo();
        ArrayAdapter<Combo> adapterMaleza = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaMaleza);
        rutainspeccion_sMaleza.setAdapter(adapterMaleza);

        //Enfermedades
        List<Combo> listaEnfermedad = _objCatalogosBP.GetAllEnfermedadCombo();
        ArrayAdapter<Combo> adapterEnfermedad = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaEnfermedad);
        rutainspeccion_sEnfermedad.setAdapter(adapterEnfermedad);

        //Estado plagas
        List<Combo> listaEstadoPlaga = _objCatalogosBP.GetAllEstadoPlagaCombo();
        ArrayAdapter<Combo> adapterEstadoPlaga = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaEstadoPlaga);
        rutainspeccion_sEstadoPlaga.setAdapter(adapterEstadoPlaga);

        //Estado malezas
        List<Combo> listaEstadoMaleza = _objCatalogosBP.GetAllEstadoMalezaCombo();
        ArrayAdapter<Combo> adapterEstadoMaleza = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaEstadoMaleza);
        rutainspeccion_sEstadoMaleza.setAdapter(adapterEstadoMaleza);

        //Estado enfermedades
        List<Combo> listaEstadoEnfermedad = _objCatalogosBP.GetAllEstadoEnfermedadCombo();
        ArrayAdapter<Combo> adapterEstadoEnfermedad = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaEstadoEnfermedad);
        rutainspeccion_sEstadoEnfermedad.setAdapter(adapterEstadoEnfermedad);

        //Potencial de rendimiento
        List<Combo> listaPotencialRendimiento = _objCatalogosBP.GetAllPotencialRendimientoCombo();
        ArrayAdapter<Combo> adapterPotencialRendimiento = new ArrayAdapter<Combo>(InspeccionCampo.this, android.R.layout.simple_spinner_item, listaPotencialRendimiento);
        rutainspeccion_sPotencial.setAdapter(adapterPotencialRendimiento);
    }

    private void getPreferences() {
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        _objUsuario = new Usuario();
        _objUsuario.RFC = prefs.getString("RFC", "");
        _objUsuario.Clave = Integer.valueOf(prefs.getString("Clave", ""));
        Bundle b = getIntent().getExtras();
        _objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        _objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        _objRutaInspeccion.Folio = b.getInt("Folio");
        _objRutaInspeccion.Fecha = b.getString("Fecha");
        _objPlaneacionRuta.CicloClave = b.getInt("CicloClave");
        _objPlaneacionRuta.UsuarioClave = b.getInt("UsuarioClave");
        _objPlaneacionRuta.Folio = b.getInt("Folio");
        _objPlaneacionRuta.Fecha = b.getString("Fecha");
    }

    private void getControles() {
        //Datos generales
        rutainspeccion_txtFolio = (EditText) findViewById(R.id.rutainspeccion_txtFolio);
        rutainspeccion_sCiclo = (Spinner) findViewById(R.id.rutainspeccion_sCiclo);
        rutainspeccion_txtCliente = (EditText) findViewById(R.id.rutainspeccion_txtCliente);
        rutainspeccion_txtProductor = (EditText) findViewById(R.id.rutainspeccion_txtProductor);
        rutainspeccion_txtPredio = (EditText) findViewById(R.id.rutainspeccion_txtPredio);
        rutainspeccion_txtLote = (EditText) findViewById(R.id.rutainspeccion_txtLote);
        rutainspeccion_txtFecha = (EditText) findViewById(R.id.rutainspeccion_txtFecha);
        //Ruta de inspeccion
        rutainspeccion_rdRecomendacion = (RadioGroup) findViewById(R.id.rutainspeccion_rdRecomendacion);
        //Siembra
        rutainspeccion_sSistemaProduccion = (Spinner) findViewById(R.id.rutainspeccion_sSistemaProduccion);
        rutainspeccion_sArregloTopologico = (Spinner) findViewById(R.id.rutainspeccion_sArregloTopologico);
        //Condiciones de la siembra
        rutainspeccion_rdAdecuada = (RadioGroup) findViewById(R.id.rutainspeccion_rdAdecuada);
        rutainspeccion_rdSurco = (RadioGroup) findViewById(R.id.rutainspeccion_rdSurco);
        rutainspeccion_rdManejo = (RadioGroup) findViewById(R.id.rutainspeccion_rdManejo);
        //Riego
        rutainspeccion_chbCanal = (CheckBox) findViewById(R.id.rutainspeccion_chbCanal);
        rutainspeccion_chbGravedad = (CheckBox) findViewById(R.id.rutainspeccion_chbGravedad);
        rutainspeccion_chbAspersion = (CheckBox) findViewById(R.id.rutainspeccion_chbAspersion);
        rutainspeccion_chbGoteo = (CheckBox) findViewById(R.id.rutainspeccion_chbGoteo);
        rutainspeccion_chbPozo = (CheckBox) findViewById(R.id.rutainspeccion_chbPozo);
        rutainspeccion_txtCapacidad = (EditText) findViewById(R.id.rutainspeccion_txtCapacidad);
        rutainspeccion_chbRiegoOtro = (CheckBox) findViewById(R.id.rutainspeccion_chbRiegoOtro);
        rutainspeccion_txtRiegoOtro = (EditText) findViewById(R.id.rutainspeccion_txtRiegoOtro);
        //Etapa fenologica
        rutainspeccion_sEtapa = (Spinner) findViewById(R.id.rutainspeccion_sEtapa);
        rutainspeccion_rdExposicion = (RadioGroup) findViewById(R.id.rutainspeccion_rdExposicion);
        //Condiciones de desarrollo
        rutainspeccion_sCondicionDesarrollo = (Spinner) findViewById(R.id.rutainspeccion_sCondicionDesarrollo);
        //Recomendaciones
        rutainspeccion_chbRegar = (CheckBox) findViewById(R.id.rutainspeccion_chbRegar);
        rutainspeccion_chbDesmezclar = (CheckBox) findViewById(R.id.rutainspeccion_chbDesmezclar);
        rutainspeccion_chbFungicida = (CheckBox) findViewById(R.id.rutainspeccion_chbFungicida);
        rutainspeccion_chbInsecticida = (CheckBox) findViewById(R.id.rutainspeccion_chbInsecticida);
        rutainspeccion_chbHerbicida = (CheckBox) findViewById(R.id.rutainspeccion_chbHerbicida);
        rutainspeccion_chbDesaguar = (CheckBox) findViewById(R.id.rutainspeccion_chbDesaguar);
        rutainspeccion_chbDescostrar = (CheckBox) findViewById(R.id.rutainspeccion_chbDescostrar);
        rutainspeccion_chbRecomendacionOtro = (CheckBox) findViewById(R.id.rutainspeccion_chbRecomendacionOtro);
        rutainspeccion_txtRecomendacionOtro = (EditText) findViewById(R.id.rutainspeccion_txtRecomendacionOtro);
        //Manejo de agroquimico
        rutainspeccion_rdOrden = (RadioGroup) findViewById(R.id.rutainspeccion_rdOrden);
        rutainspeccion_rdRegula = (RadioGroup) findViewById(R.id.rutainspeccion_rdRegula);
        rutainspeccion_rdUso = (RadioGroup) findViewById(R.id.rutainspeccion_rdUso);
        rutainspeccion_rdHora = (RadioGroup) findViewById(R.id.rutainspeccion_rdHora);
        rutainspeccion_rdAgua = (RadioGroup) findViewById(R.id.rutainspeccion_rdAgua);
        //Problemas
        rutainspeccion_rdInundacion = (RadioGroup) findViewById(R.id.rutainspeccion_rdInundacion);
        rutainspeccion_rdPoblacion = (RadioGroup) findViewById(R.id.rutainspeccion_rdPoblacion);
        rutainspeccion_rdProblema = (RadioGroup) findViewById(R.id.rutainspeccion_rdProblema);
        rutainspeccion_rdAlteracion = (RadioGroup) findViewById(R.id.rutainspeccion_rdAlteracion);
        rutainspeccion_rdAplicacion = (RadioGroup) findViewById(R.id.rutainspeccion_rdAplicacion);
        rutainspeccion_rdTemperatura = (RadioGroup) findViewById(R.id.rutainspeccion_rdTemperatura);
        rutainspeccion_rdFito = (RadioGroup) findViewById(R.id.rutainspeccion_rdFito);
        rutainspeccion_rdPlaga = (RadioGroup) findViewById(R.id.rutainspeccion_rdPlaga);
        rutainspeccion_sMaleza = (Spinner) findViewById(R.id.rutainspeccion_sMaleza);
        rutainspeccion_sEstadoMaleza = (Spinner) findViewById(R.id.rutainspeccion_sEstadoMaleza);
        rutainspeccion_sPlaga = (Spinner) findViewById(R.id.rutainspeccion_sPlaga);
        rutainspeccion_sEstadoPlaga = (Spinner) findViewById(R.id.rutainspeccion_sEstadoPlaga);
        rutainspeccion_sEnfermedad = (Spinner) findViewById(R.id.rutainspeccion_sEnfermedad);
        rutainspeccion_sEstadoEnfermedad = (Spinner) findViewById(R.id.rutainspeccion_sEstadoEnfermedad);
        //Potencial de rendimiento
        rutainspeccion_sPotencial = (Spinner) findViewById(R.id.rutainspeccion_sPotencial);
        rutainspeccion_btnGuardar = (Button) findViewById(R.id.rutainspeccion_btnGuardar);

    }

    private void cargarCabecero() {
        PlaneacionRuta _objPlaneacionFiltro = _objRutaInspeccionBP.GetPlaneacionRuta(_objPlaneacionRuta);
        rutainspeccion_txtFolio.setText(String.valueOf(_objPlaneacionFiltro.Folio));
        seleccionarValorSpinner(rutainspeccion_sCiclo, _objPlaneacionFiltro.CicloClave);
        rutainspeccion_txtCliente.setText(_objPlaneacionFiltro.ClienteNombre);
        rutainspeccion_txtProductor.setText(_objPlaneacionFiltro.ProductorNombre);
        rutainspeccion_txtPredio.setText(_objPlaneacionFiltro.PredioNombre);
        rutainspeccion_txtLote.setText(_objPlaneacionFiltro.LoteNombre);
        rutainspeccion_txtFecha.setText(_objPlaneacionFiltro.Fecha);
    }

    private void seleccionarValorSpinner(Spinner _objSpinner, int valor) {
        ArrayAdapter<Combo> objArrayAdapter = (ArrayAdapter<Combo>) _objSpinner.getAdapter();
        for (int i = 0; i < objArrayAdapter.getCount(); i++) {
            if (((Combo) objArrayAdapter.getItem(i)).getClave()==valor) {
                _objSpinner.setSelection(i);
            }
        }
    }

    private Character obtenerValorRadio(RadioGroup objRadioGroup) {
        int id = objRadioGroup.getCheckedRadioButtonId();
        RadioButton objRadioButton = (RadioButton) findViewById(id);
        return objRadioButton.getText().toString().charAt(0);
    }

    private int obtenerValorSpinner(Spinner _objSpinner) {
        Combo objCombo = (Combo) rutainspeccion_sSistemaProduccion.getSelectedItem();
        return objCombo.getClave();
    }

    private void eventosCombo() {
        //Sistema de produccion
        rutainspeccion_sSistemaProduccion.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        Combo objCombo;
                        if (!(rutainspeccion_sSistemaProduccion.getSelectedItem() == null))
                            objCombo = (Combo) rutainspeccion_sSistemaProduccion.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        _objCiclo = null;
                    }
                });
    }

    private boolean validar() {
        boolean validar=true;
        if (rutainspeccion_rdRecomendacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdRecomendacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [¿El productor consult\u00F3 las recomendaciones técnicas?]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sSistemaProduccion.getSelectedItemPosition() == 0) {
            rutainspeccion_sSistemaProduccion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Sitema de producci\u00F3n]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sArregloTopologico.getSelectedItemPosition() == 0) {
            rutainspeccion_sArregloTopologico.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Arreglo topologico]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdAdecuada.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAdecuada.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de siembra adecuada]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdSurco.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdSurco.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de surco adecuada]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdManejo.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdManejo.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Manejo adecuado de la siembra]", InspeccionCampo.this);
            return validar=false;
        }
        if (!rutainspeccion_chbCanal.isChecked() && !rutainspeccion_chbGravedad.isChecked() && !rutainspeccion_chbAspersion.isChecked() &&
                !rutainspeccion_chbGoteo.isChecked() && !rutainspeccion_chbPozo.isChecked() && !rutainspeccion_chbRiegoOtro.isChecked()) {
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Riego]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_chbPozo.isChecked()) {
            if (rutainspeccion_txtCapacidad.getText().toString().length() == 0) {
                rutainspeccion_txtCapacidad.requestFocus();
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Capacidad de la bomba en pulgadas]", InspeccionCampo.this);
                return validar=false;
            }
        }
        if (rutainspeccion_chbRiegoOtro.isChecked()) {
            if (rutainspeccion_txtRiegoOtro.getText().toString().length() == 0) {
                rutainspeccion_txtRiegoOtro.requestFocus();
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Otro (Riego)]", InspeccionCampo.this);
                return validar=false;
            }
        }
        if (rutainspeccion_sEtapa.getSelectedItemPosition() == 0) {
            rutainspeccion_sEtapa.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Etapa principal Zadoks]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdExposicion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdExposicion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Exposici\u00F3n de la excersi\u00F3n]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sCondicionDesarrollo.getSelectedItemPosition() == 0) {
            rutainspeccion_sCondicionDesarrollo.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Condiciones de desarrollo]", InspeccionCampo.this);
            return validar=false;
        }
        if (!rutainspeccion_chbRegar.isChecked() && !rutainspeccion_chbDesmezclar.isChecked() && !rutainspeccion_chbFungicida.isChecked() &&
                !rutainspeccion_chbInsecticida.isChecked() && !rutainspeccion_chbHerbicida.isChecked() && !rutainspeccion_chbDesaguar.isChecked() &&
                !rutainspeccion_chbDescostrar.isChecked() && !rutainspeccion_chbRecomendacionOtro.isChecked()) {
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Recomendaciones]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_chbRecomendacionOtro.isChecked()) {
            if (rutainspeccion_txtRecomendacionOtro.getText().toString().length() == 0) {
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Otro (Recomendaciones)]", InspeccionCampo.this);
                return validar=false;
            }
        }
        if (rutainspeccion_rdOrden.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdOrden.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Orden correcto de preparaci\u00F3n de mezcla]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdRegula.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdRegula.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Regula pH del agua]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdUso.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdUso.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Uso adecuado de boquillas]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdHora.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdHora.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Hora de aplicaci\u00F3n]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdAgua.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAgua.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Agua de canal para aplicaciones]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdInundacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdInundacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Inundación]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdPoblacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPoblacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Baja población de plantas]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdProblema.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdProblema.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Problemas en la aplicaci\u00F3n de nutrientes]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdAlteracion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAlteracion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Alteraci\u00F3n de ciclo]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdAplicacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAplicacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Aplicaci\u00F3n adecuada de agroquímicos]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdTemperatura.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdTemperatura.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Altas temperaturas]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdFito.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdFito.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Fitotoxicidad]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_rdPlaga.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Plagas mal controladas]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Maleza]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sEstadoMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Maleza)]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Insectos]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sEstadoPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Insectos)]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Enfermedad]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sEstadoEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Enfermedad)]", InspeccionCampo.this);
            return validar=false;
        }
        if (rutainspeccion_sPotencial.getSelectedItemPosition() == 0) {
            rutainspeccion_sPotencial.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Potencial de rendimiento del cultivo]", InspeccionCampo.this);
            return validar=false;
        }
        return validar;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.rutainspeccion_chbPozo:
                if (checked)
                    rutainspeccion_txtCapacidad.setEnabled(true);
                else {
                    rutainspeccion_txtCapacidad.setEnabled(false);
                    rutainspeccion_txtCapacidad.setText("");
                }
                break;

            case R.id.rutainspeccion_chbRiegoOtro:
                if (checked)
                    rutainspeccion_txtRiegoOtro.setEnabled(true);
                else {
                    rutainspeccion_txtRiegoOtro.setEnabled(false);
                    rutainspeccion_txtRiegoOtro.setText("");
                }
                break;

            case R.id.rutainspeccion_chbRecomendacionOtro:
                if (checked)
                    rutainspeccion_txtRecomendacionOtro.setEnabled(true);
                else {
                    rutainspeccion_txtRecomendacionOtro.setEnabled(false);
                    rutainspeccion_txtRecomendacionOtro.setText("");
                }
                break;
        }
    }
}
