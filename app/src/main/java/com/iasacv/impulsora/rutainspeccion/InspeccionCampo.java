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
    private Button rutainspeccion_btnEnviar;

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

            cargarCabecero();

            rutainspeccion_btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (validar()) {
                            RutaInspeccion objRutaInspeccion = creaObjeto();
                            objRutaInspeccion.Estatus = "G";
                            boolean resul = _objRutaInspeccionBP.UpdateRutaInspeccion(objRutaInspeccion);
                            _objPlaneacionRuta.Estatus = "G";
                            resul = _objRutaInspeccionBP.UpdatePlaneacionRutaEstatus(_objPlaneacionRuta);
                            guardarRecomendacion();
                            guardarRiego();
                            if (resul) {
                                _objComunBP.Mensaje("La informaci\u00F3n se guardo correctamente", InspeccionCampo.this);
                                rutainspeccion_btnEnviar.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (IOException e) {
                        _objComunBP.Mensaje(e.toString(), InspeccionCampo.this);
                    } catch (XmlPullParserException e) {
                        _objComunBP.Mensaje(e.toString(), InspeccionCampo.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            rutainspeccion_btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (validar()) {
                            RutaInspeccion objRutaInspeccion = creaObjeto();
                            objRutaInspeccion.Estatus = "E";
                            boolean resul = _objRutaInspeccionBP.UpdateRutaInspeccion(objRutaInspeccion);
                            _objComunBP.Mensaje("La informaci\u00F3n se enviara a IASA", InspeccionCampo.this);
                            rutainspeccion_btnEnviar.setVisibility(View.INVISIBLE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
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
    public RutaInspeccion creaObjeto() throws IOException{
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        Bundle b = getIntent().getExtras();
        objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        objRutaInspeccion.Folio = b.getInt("Folio");
        objRutaInspeccion.Fecha = b.getString("Fecha");
        objRutaInspeccion = _objRutaInspeccionBP.GetRutaInspeccionCabecero(objRutaInspeccion);
        objRutaInspeccion.RecomendacionTecnica = obtenerValorRadio(rutainspeccion_rdRecomendacion);
        objRutaInspeccion.SistemaProduccionClave = obtenerValorSpinner(rutainspeccion_sSistemaProduccion);
        objRutaInspeccion.ArregloTopologicoClave = obtenerValorSpinner(rutainspeccion_sArregloTopologico);
        objRutaInspeccion.ProfundidadSiembra = obtenerValorRadio(rutainspeccion_rdAdecuada);
        objRutaInspeccion.ProfundidadSurco = obtenerValorRadio(rutainspeccion_rdSurco);
        objRutaInspeccion.ManejoAdecuado = obtenerValorRadio(rutainspeccion_rdAdecuada);
        objRutaInspeccion.EtapaFenologicaClave = obtenerValorSpinner(rutainspeccion_sEtapa);
        objRutaInspeccion.Exposicion = obtenerValorRadio(rutainspeccion_rdExposicion);
        objRutaInspeccion.CondicionDesarrolloClave = obtenerValorSpinner(rutainspeccion_sCondicionDesarrollo);
        objRutaInspeccion.OrdenCorrecto = obtenerValorRadio(rutainspeccion_rdOrden);
        objRutaInspeccion.RegulaPh = obtenerValorRadio(rutainspeccion_rdRegula);
        objRutaInspeccion.UsoAdecuado = obtenerValorRadio(rutainspeccion_rdUso);
        objRutaInspeccion.HoraAplicacion = obtenerValorRadio(rutainspeccion_rdHora);
        objRutaInspeccion.AguaCanal = obtenerValorRadio(rutainspeccion_rdAgua);
        objRutaInspeccion.Inundacion = obtenerValorRadio(rutainspeccion_rdInundacion);
        objRutaInspeccion.BajaPoblacion = obtenerValorRadio(rutainspeccion_rdPoblacion);
        objRutaInspeccion.AplicacionNutrientes = obtenerValorRadio(rutainspeccion_rdProblema);
        objRutaInspeccion.AlteracionCiclo = obtenerValorRadio(rutainspeccion_rdAlteracion);
        objRutaInspeccion.AplicacionAgroquimicos = obtenerValorRadio(rutainspeccion_rdAplicacion);
        objRutaInspeccion.AltasTemperaturas = obtenerValorRadio(rutainspeccion_rdTemperatura);
        objRutaInspeccion.Fito = obtenerValorRadio(rutainspeccion_rdFito);
        objRutaInspeccion.PlagasMalControladas = obtenerValorRadio(rutainspeccion_rdPlaga);
        objRutaInspeccion.MalezaClave = obtenerValorSpinner(rutainspeccion_sMaleza);
        objRutaInspeccion.EstadoMalezaClave = obtenerValorSpinner(rutainspeccion_sEstadoMaleza);
        objRutaInspeccion.PlagaClave = obtenerValorSpinner(rutainspeccion_sPlaga);
        objRutaInspeccion.EstadoPlagaClave = obtenerValorSpinner(rutainspeccion_sEstadoPlaga);
        objRutaInspeccion.EnfermedadClave = obtenerValorSpinner(rutainspeccion_sEnfermedad);
        objRutaInspeccion.EstadoEnfermedadClave = obtenerValorSpinner(rutainspeccion_sEstadoEnfermedad);
        objRutaInspeccion.PotencialRendimientoClave = obtenerValorSpinner(rutainspeccion_sPotencial);
        objRutaInspeccion.Uso = "S";
        return objRutaInspeccion;
    }

    //Guardar recomendaciones
    private  void guardarRecomendacion () throws Exception {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        Bundle b = getIntent().getExtras();
        objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        objRutaInspeccion.Folio = b.getInt("Folio");
        objRutaInspeccion.Fecha = b.getString("Fecha");
        objRutaInspeccion.RecomendacionOtro="";
        if(rutainspeccion_chbRegar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,1);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,1);
        if(rutainspeccion_chbDesmezclar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,2);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,2);
        if(rutainspeccion_chbFungicida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,3);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,3);
        if(rutainspeccion_chbInsecticida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,4);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,4);
        if(rutainspeccion_chbHerbicida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,5);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,5);
        if(rutainspeccion_chbDesaguar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,6);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,6);
        if(rutainspeccion_chbDescostrar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion,7);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,7);
        if(rutainspeccion_chbRecomendacionOtro.isChecked()) {
            objRutaInspeccion.RecomendacionOtro=rutainspeccion_txtRecomendacionOtro.getText().toString();
            agregarRelacionRecomendacion(objRutaInspeccion,8);
        }
        else
            eliminarRelacionRecomendacion(objRutaInspeccion,8);
    }

    private void agregarRelacionRecomendacion(RutaInspeccion objRutaInspeccion,int Clave) throws IOException, XmlPullParserException{
        objRutaInspeccion.RecomendacionClave = Clave;
        RutaInspeccion objTemp = _objRutaInspeccionBP.GetRelacionRecomendacion(objRutaInspeccion);
        if(objTemp.RecomendacionClave==0)
            try {
                _objRutaInspeccionBP.InsertRelacionRecomendacion(objRutaInspeccion);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void eliminarRelacionRecomendacion(RutaInspeccion objRutaInspeccion,int Clave) throws Exception {
        objRutaInspeccion.RecomendacionClave = Clave;
        _objRutaInspeccionBP.DeleteRelacionRecomendacion(objRutaInspeccion);
    }

    //Guardar tipos de riego
    private  void guardarRiego () throws Exception {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        Bundle b = getIntent().getExtras();
        objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        objRutaInspeccion.Folio = b.getInt("Folio");
        objRutaInspeccion.Fecha = b.getString("Fecha");
        objRutaInspeccion.TipoRiegoOtro="";
        objRutaInspeccion.Capacidad=0;
        if(rutainspeccion_chbPozo.isChecked()) {
            objRutaInspeccion.Capacidad = Integer.valueOf(rutainspeccion_txtCapacidad.getText().toString());
            agregarRelacionRiego(objRutaInspeccion, 1);
        }
        else
            eliminarRelacionRiego(objRutaInspeccion, 1);
        if(rutainspeccion_chbGravedad.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 2);
        else
            eliminarRelacionRiego(objRutaInspeccion,2);
        if(rutainspeccion_chbAspersion.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 3);
        else
            eliminarRelacionRiego(objRutaInspeccion, 3);
        if(rutainspeccion_chbGoteo.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 4);
        else
            eliminarRelacionRiego(objRutaInspeccion, 4);
        if(rutainspeccion_chbCanal.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 5);
        else
            eliminarRelacionRiego(objRutaInspeccion, 5);
        if(rutainspeccion_chbRiegoOtro.isChecked()) {
            objRutaInspeccion.TipoRiegoOtro = rutainspeccion_txtRiegoOtro.getText().toString();
            agregarRelacionRiego(objRutaInspeccion, 6);
        }
        else
            eliminarRelacionRiego(objRutaInspeccion, 6);
    }

    private void agregarRelacionRiego(RutaInspeccion objRutaInspeccion,int Clave) throws Exception {
        objRutaInspeccion.TipoRiegoClave = Clave;
        RutaInspeccion objTemp = _objRutaInspeccionBP.GetRelacionRiego(objRutaInspeccion);
        if(objTemp.TipoRiegoClave==0)
            _objRutaInspeccionBP.InsertRelacionRiego(objRutaInspeccion);
    }

    private void eliminarRelacionRiego(RutaInspeccion objRutaInspeccion,int Clave) throws Exception {
        objRutaInspeccion.TipoRiegoClave = Clave;
        _objRutaInspeccionBP.DeleteRelacionRiego(objRutaInspeccion);
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
        rutainspeccion_btnEnviar = (Button) findViewById(R.id.rutainspeccion_btnEnviar);
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
        if (_objPlaneacionFiltro.Estatus.equals("G") || _objPlaneacionFiltro.Estatus.equals("E"))
            cargarInspeccionCampo();
    }

    private void cargarInspeccionCampo() {
        RutaInspeccion objRutaInspeccion = _objRutaInspeccionBP.GetRutaInspeccion(_objRutaInspeccion);
        seleccionarValorRadio(rutainspeccion_rdRecomendacion, objRutaInspeccion.RecomendacionTecnica);
        seleccionarValorSpinner(rutainspeccion_sSistemaProduccion, objRutaInspeccion.SistemaProduccionClave);
        seleccionarValorSpinner(rutainspeccion_sArregloTopologico, objRutaInspeccion.ArregloTopologicoClave);
        seleccionarValorRadio(rutainspeccion_rdAdecuada, objRutaInspeccion.ProfundidadSiembra);
        seleccionarValorRadio(rutainspeccion_rdSurco,objRutaInspeccion.ProfundidadSurco);
        seleccionarValorRadio(rutainspeccion_rdManejo, objRutaInspeccion.ManejoAdecuado);
        seleccionarValorSpinner(rutainspeccion_sEtapa, objRutaInspeccion.EtapaFenologicaClave);
        seleccionarValorRadio(rutainspeccion_rdExposicion, objRutaInspeccion.Exposicion);
        seleccionarValorSpinner(rutainspeccion_sCondicionDesarrollo, objRutaInspeccion.CondicionDesarrolloClave);
        seleccionarValorRadio(rutainspeccion_rdOrden, objRutaInspeccion.OrdenCorrecto);
        seleccionarValorRadio(rutainspeccion_rdRegula,objRutaInspeccion.RegulaPh);
        seleccionarValorRadio(rutainspeccion_rdUso, objRutaInspeccion.UsoAdecuado);
        seleccionarValorRadio(rutainspeccion_rdHora, objRutaInspeccion.HoraAplicacion);
        seleccionarValorRadio(rutainspeccion_rdAgua, objRutaInspeccion.AguaCanal);
        seleccionarValorRadio(rutainspeccion_rdInundacion,objRutaInspeccion.Inundacion);
        seleccionarValorRadio(rutainspeccion_rdPoblacion, objRutaInspeccion.BajaPoblacion);
        seleccionarValorRadio(rutainspeccion_rdProblema,objRutaInspeccion.AplicacionNutrientes);
        seleccionarValorRadio(rutainspeccion_rdAlteracion,objRutaInspeccion.AlteracionCiclo);
        seleccionarValorRadio(rutainspeccion_rdAplicacion, objRutaInspeccion.AplicacionAgroquimicos);
        seleccionarValorRadio(rutainspeccion_rdTemperatura,objRutaInspeccion.AltasTemperaturas);
        seleccionarValorRadio(rutainspeccion_rdFito,objRutaInspeccion.Fito);
        seleccionarValorRadio(rutainspeccion_rdPlaga,objRutaInspeccion.PlagasMalControladas);
        seleccionarValorSpinner(rutainspeccion_sMaleza,objRutaInspeccion.MalezaClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoMaleza,objRutaInspeccion.EstadoMalezaClave);
        seleccionarValorSpinner(rutainspeccion_sPlaga,objRutaInspeccion.PlagaClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoPlaga,objRutaInspeccion.EstadoPlagaClave);
        seleccionarValorSpinner(rutainspeccion_sEnfermedad,objRutaInspeccion.EnfermedadClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoEnfermedad,objRutaInspeccion.EstadoEnfermedadClave);
        seleccionarValorSpinner(rutainspeccion_sPotencial, objRutaInspeccion.PotencialRendimientoClave);
        RutaInspeccion[] listaRelacionRecomendacion = _objRutaInspeccionBP.GetAllRelacionRecomendacion(_objRutaInspeccion);
        for (int i = 0; i < listaRelacionRecomendacion.length; i++) {
            RutaInspeccion objTemp = new RutaInspeccion();
            objTemp = (RutaInspeccion) listaRelacionRecomendacion[i];
            switch (objTemp.RecomendacionClave) {
                case 1:
                    rutainspeccion_chbRegar.setChecked(true);
                    break;
                case 2:
                    rutainspeccion_chbDesmezclar.setChecked(true);
                    break;
                case 3:
                    rutainspeccion_chbFungicida.setChecked(true);
                    break;
                case 4:
                    rutainspeccion_chbInsecticida.setChecked(true);
                    break;
                case 5:
                    rutainspeccion_chbHerbicida.setChecked(true);
                    break;
                case 6:
                    rutainspeccion_chbDesaguar.setChecked(true);
                    break;
                case 7:
                    rutainspeccion_chbDescostrar.setChecked(true);
                    break;
                case 8:
                    rutainspeccion_chbRecomendacionOtro.setChecked(true);
                    rutainspeccion_txtRecomendacionOtro.setEnabled(true);
                    rutainspeccion_txtRecomendacionOtro.setText(objTemp.RecomendacionOtro);
                    break;
            }
        }
        RutaInspeccion[] listaRelacionRiego = _objRutaInspeccionBP.GetAllRelacionRiego(_objRutaInspeccion);
        for (int i = 0; i < listaRelacionRiego.length; i++) {
            RutaInspeccion objTemp = new RutaInspeccion();
            objTemp = (RutaInspeccion) listaRelacionRiego[i];
            switch (objTemp.TipoRiegoClave) {
                case 1:
                    rutainspeccion_chbPozo.setChecked(true);
                    rutainspeccion_txtCapacidad.setEnabled(true);
                    rutainspeccion_txtCapacidad.setText(String.valueOf(objTemp.Capacidad));
                    break;
                case 2:
                    rutainspeccion_chbGravedad.setChecked(true);
                    break;
                case 3:
                    rutainspeccion_chbAspersion.setChecked(true);
                    break;
                case 4:
                    rutainspeccion_chbGoteo.setChecked(true);
                    break;
                case 5:
                    rutainspeccion_chbCanal.setChecked(true);
                    break;
                case 6:
                    rutainspeccion_chbRiegoOtro.setChecked(true);
                    rutainspeccion_txtRiegoOtro.setEnabled(true);
                    rutainspeccion_txtRiegoOtro.setText(objTemp.TipoRiegoOtro);
                    break;
            }
        }
    }

    private void seleccionarValorSpinner(Spinner _objSpinner, int valor) {
        ArrayAdapter<Combo> objArrayAdapter = (ArrayAdapter<Combo>) _objSpinner.getAdapter();
        for (int i = 0; i < objArrayAdapter.getCount(); i++) {
            if (((Combo) objArrayAdapter.getItem(i)).getClave() == valor) {
                _objSpinner.setSelection(i);
            }
        }
    }

    private Character obtenerValorRadio(RadioGroup objRadioGroup) {
        int id = objRadioGroup.getCheckedRadioButtonId();
        RadioButton objRadioButton = (RadioButton) findViewById(id);
        return objRadioButton.getText().toString().charAt(0);
    }

    private void seleccionarValorRadio(RadioGroup objRadioGroup, Character valor) {
        int count = objRadioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = objRadioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton objRadioButton =  (RadioButton)o;
                if(objRadioButton.getText().toString().charAt(0)==valor)
                    objRadioButton.setChecked(true);
            }
        }
    }

    private int obtenerValorSpinner(Spinner _objSpinner) {
        Combo objCombo = (Combo) _objSpinner.getSelectedItem();
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
        boolean validar = true;
        if (rutainspeccion_rdRecomendacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdRecomendacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [¿El productor consult\u00F3 las recomendaciones técnicas?]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sSistemaProduccion.getSelectedItemPosition() == 0) {
            rutainspeccion_sSistemaProduccion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Sitema de producci\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sArregloTopologico.getSelectedItemPosition() == 0) {
            rutainspeccion_sArregloTopologico.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Arreglo topologico]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAdecuada.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAdecuada.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de siembra adecuada]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdSurco.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdSurco.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de surco adecuada]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdManejo.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdManejo.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Manejo adecuado de la siembra]", InspeccionCampo.this);
            return validar = false;
        }
        if (!rutainspeccion_chbCanal.isChecked() && !rutainspeccion_chbGravedad.isChecked() && !rutainspeccion_chbAspersion.isChecked() &&
                !rutainspeccion_chbGoteo.isChecked() && !rutainspeccion_chbPozo.isChecked() && !rutainspeccion_chbRiegoOtro.isChecked()) {
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Riego]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_chbPozo.isChecked()) {
            if (rutainspeccion_txtCapacidad.getText().toString().length() == 0) {
                rutainspeccion_txtCapacidad.requestFocus();
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Capacidad de la bomba en pulgadas]", InspeccionCampo.this);
                return validar = false;
            }
        }
        if (rutainspeccion_chbRiegoOtro.isChecked()) {
            if (rutainspeccion_txtRiegoOtro.getText().toString().length() == 0) {
                rutainspeccion_txtRiegoOtro.requestFocus();
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Otro (Riego)]", InspeccionCampo.this);
                return validar = false;
            }
        }
        if (rutainspeccion_sEtapa.getSelectedItemPosition() == 0) {
            rutainspeccion_sEtapa.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Etapa principal Zadoks]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdExposicion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdExposicion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Exposici\u00F3n de la excersi\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sCondicionDesarrollo.getSelectedItemPosition() == 0) {
            rutainspeccion_sCondicionDesarrollo.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Condiciones de desarrollo]", InspeccionCampo.this);
            return validar = false;
        }
        if (!rutainspeccion_chbRegar.isChecked() && !rutainspeccion_chbDesmezclar.isChecked() && !rutainspeccion_chbFungicida.isChecked() &&
                !rutainspeccion_chbInsecticida.isChecked() && !rutainspeccion_chbHerbicida.isChecked() && !rutainspeccion_chbDesaguar.isChecked() &&
                !rutainspeccion_chbDescostrar.isChecked() && !rutainspeccion_chbRecomendacionOtro.isChecked()) {
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Recomendaciones]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_chbRecomendacionOtro.isChecked()) {
            if (rutainspeccion_txtRecomendacionOtro.getText().toString().length() == 0) {
                _objComunBP.Mensaje("*Favor de no dejar el campo vac\u00EDo. [Otro (Recomendaciones)]", InspeccionCampo.this);
                return validar = false;
            }
        }
        if (rutainspeccion_rdOrden.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdOrden.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Orden correcto de preparaci\u00F3n de mezcla]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdRegula.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdRegula.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Regula pH del agua]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdUso.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdUso.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Uso adecuado de boquillas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdHora.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdHora.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Hora de aplicaci\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAgua.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAgua.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Agua de canal para aplicaciones]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdInundacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdInundacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Inundación]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdPoblacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPoblacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Baja población de plantas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdProblema.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdProblema.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Problemas en la aplicaci\u00F3n de nutrientes]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAlteracion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAlteracion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Alteraci\u00F3n de ciclo]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAplicacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAplicacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Aplicaci\u00F3n adecuada de agroquímicos]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdTemperatura.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdTemperatura.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Altas temperaturas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdFito.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdFito.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Fitotoxicidad]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdPlaga.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Plagas mal controladas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Maleza]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Maleza)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Insectos]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Insectos)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Enfermedad]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Enfermedad)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sPotencial.getSelectedItemPosition() == 0) {
            rutainspeccion_sPotencial.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Potencial de rendimiento del cultivo]", InspeccionCampo.this);
            return validar = false;
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
