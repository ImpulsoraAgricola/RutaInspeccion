package com.iasacv.impulsora.rutainspeccion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.iasacv.impulsora.rutainspeccion.Conexion.GPSTracker;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

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
    private EditText rutainspeccion_txtComentario;
    private ImageButton rutainspeccion_btnGuardar;
    private ImageButton rutainspeccion_btnFotografia;
    private ImageButton rutainspeccion_btnEnviar;

    //Variables clases
    WebServiceBP _objWebServiceBP;
    CatalogosBP _objCatalogosBP;
    RutaInspeccionBP _objRutaInspeccionBP;
    ComunBP _objComunBP;
    GPSTracker gpsTracker;

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

    //Variables camara
    private static final int CAMERA_REQUEST = 1888;
    static String str_Camera_Photo_ImagePath = "";
    private static File f;
    private static int Take_Photo = 2;
    private static String str_randomnumber = "";
    static String str_Camera_Photo_ImageName = "";
    public static String str_SaveFolderName;
    private static File wallpaperDirectory;
    Bitmap bitmap;
    int storeposition = 0;
    public static GridView gridview;
    public static ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion_campo);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Pasar contexto a las demas instancias
        _objWebServiceBP = new WebServiceBP(this);
        _objCatalogosBP = new CatalogosBP(this);
        _objRutaInspeccionBP = new RutaInspeccionBP(this);
        gpsTracker = new GPSTracker(InspeccionCampo.this);
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
                                rutainspeccion_btnFotografia.setVisibility(View.VISIBLE);
                            }
                            imageView.setVisibility(View.VISIBLE);
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
                            final AlertDialog alert = new AlertDialog.Builder(
                                    new ContextThemeWrapper(InspeccionCampo.this, android.R.style.Theme_Dialog))
                                    .create();
                            alert.setTitle("Mensaje");
                            alert.setMessage("\u00BFDeseas enviar la informaci\u00F3n a IASA?");
                            alert.setCancelable(false);
                            alert.setIcon(R.drawable.info);
                            alert.setCanceledOnTouchOutside(false);
                            alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                enviarInformacion();
                                                alert.dismiss();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            rutainspeccion_btnFotografia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gpsTracker.getIsGPSTrackingEnabled()) {
                        if (validar()) {
                            str_SaveFolderName = Environment
                                    .getExternalStorageDirectory()
                                    .getAbsolutePath()
                                    + "/RutaInspeccion/Fotografias";
                            str_randomnumber = String.valueOf(nextSessionId());
                            wallpaperDirectory = new File(str_SaveFolderName);
                            if (!wallpaperDirectory.exists())
                                wallpaperDirectory.mkdirs();
                            str_Camera_Photo_ImageName = String.format("%05d", _objRutaInspeccion.UsuarioClave) + String.format("%05d",_objRutaInspeccion.CicloClave) +
                                    _objRutaInspeccion.Fecha + String.format("%07d",_objRutaInspeccion.Folio) + str_randomnumber
                                    + ".jpg";
                            str_Camera_Photo_ImagePath = str_SaveFolderName
                                    + "/" + String.format("%05d", _objRutaInspeccion.UsuarioClave) + String.format("%05d",_objRutaInspeccion.CicloClave) +
                                    _objRutaInspeccion.Fecha + String.format("%07d",_objRutaInspeccion.Folio) + str_randomnumber + ".jpg";
                            System.err.println(" str_Camera_Photo_ImagePath  "
                                    + str_Camera_Photo_ImagePath);
                            f = new File(str_Camera_Photo_ImagePath);
                            startActivityForResult(new Intent(
                                            MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                                            MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)),
                                    Take_Photo);
                            System.err.println("f  " + f);
                        }
                    } else
                        _objComunBP.Mensaje("Error: Favor de habilitar GPS", InspeccionCampo.this);
                }
            });

            eventosCombo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Llenar objetos
    public RutaInspeccion creaObjeto() throws IOException {
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
        objRutaInspeccion.Comentario = rutainspeccion_txtComentario.getText().toString();
        objRutaInspeccion.Uso = "S";
        return objRutaInspeccion;
    }

    //Guardar recomendaciones
    private void guardarRecomendacion() throws Exception {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        Bundle b = getIntent().getExtras();
        objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        objRutaInspeccion.Folio = b.getInt("Folio");
        objRutaInspeccion.Fecha = b.getString("Fecha");
        objRutaInspeccion.RecomendacionOtro = "";
        if (rutainspeccion_chbRegar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 1);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 1);
        if (rutainspeccion_chbDesmezclar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 2);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 2);
        if (rutainspeccion_chbFungicida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 3);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 3);
        if (rutainspeccion_chbInsecticida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 4);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 4);
        if (rutainspeccion_chbHerbicida.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 5);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 5);
        if (rutainspeccion_chbDesaguar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 6);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 6);
        if (rutainspeccion_chbDescostrar.isChecked())
            agregarRelacionRecomendacion(objRutaInspeccion, 7);
        else
            eliminarRelacionRecomendacion(objRutaInspeccion, 7);
        if (rutainspeccion_chbRecomendacionOtro.isChecked()) {
            objRutaInspeccion.RecomendacionOtro = rutainspeccion_txtRecomendacionOtro.getText().toString();
            agregarRelacionRecomendacion(objRutaInspeccion, 8);
        } else
            eliminarRelacionRecomendacion(objRutaInspeccion, 8);
    }

    private void agregarRelacionRecomendacion(RutaInspeccion objRutaInspeccion, int Clave) throws IOException, XmlPullParserException {
        objRutaInspeccion.RecomendacionClave = Clave;
        RutaInspeccion objTemp = _objRutaInspeccionBP.GetRelacionRecomendacion(objRutaInspeccion);
        if (objTemp.RecomendacionClave == 0)
            try {
                _objRutaInspeccionBP.InsertRelacionRecomendacion(objRutaInspeccion);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void eliminarRelacionRecomendacion(RutaInspeccion objRutaInspeccion, int Clave) throws Exception {
        objRutaInspeccion.RecomendacionClave = Clave;
        _objRutaInspeccionBP.DeleteRelacionRecomendacion(objRutaInspeccion);
    }

    //Guardar tipos de riego
    private void guardarRiego() throws Exception {
        RutaInspeccion objRutaInspeccion = new RutaInspeccion();
        Bundle b = getIntent().getExtras();
        objRutaInspeccion.CicloClave = b.getInt("CicloClave");
        objRutaInspeccion.UsuarioClave = b.getInt("UsuarioClave");
        objRutaInspeccion.Folio = b.getInt("Folio");
        objRutaInspeccion.Fecha = b.getString("Fecha");
        objRutaInspeccion.TipoRiegoOtro = "";
        objRutaInspeccion.Capacidad = 0;
        if (rutainspeccion_chbPozo.isChecked()) {
            objRutaInspeccion.Capacidad = Integer.valueOf(rutainspeccion_txtCapacidad.getText().toString());
            agregarRelacionRiego(objRutaInspeccion, 1);
        } else
            eliminarRelacionRiego(objRutaInspeccion, 1);
        objRutaInspeccion.Capacidad = 0;
        if (rutainspeccion_chbGravedad.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 2);
        else
            eliminarRelacionRiego(objRutaInspeccion, 2);
        if (rutainspeccion_chbAspersion.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 3);
        else
            eliminarRelacionRiego(objRutaInspeccion, 3);
        if (rutainspeccion_chbGoteo.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 4);
        else
            eliminarRelacionRiego(objRutaInspeccion, 4);
        if (rutainspeccion_chbCanal.isChecked())
            agregarRelacionRiego(objRutaInspeccion, 5);
        else
            eliminarRelacionRiego(objRutaInspeccion, 5);
        if (rutainspeccion_chbRiegoOtro.isChecked()) {
            objRutaInspeccion.TipoRiegoOtro = rutainspeccion_txtRiegoOtro.getText().toString();
            agregarRelacionRiego(objRutaInspeccion, 6);
        } else
            eliminarRelacionRiego(objRutaInspeccion, 6);
    }

    private void agregarRelacionRiego(RutaInspeccion objRutaInspeccion, int Clave) throws Exception {
        objRutaInspeccion.TipoRiegoClave = Clave;
        RutaInspeccion objTemp = _objRutaInspeccionBP.GetRelacionRiego(objRutaInspeccion);
        if (objTemp.TipoRiegoClave == 0)
            _objRutaInspeccionBP.InsertRelacionRiego(objRutaInspeccion);
    }

    private void eliminarRelacionRiego(RutaInspeccion objRutaInspeccion, int Clave) throws Exception {
        objRutaInspeccion.TipoRiegoClave = Clave;
        _objRutaInspeccionBP.DeleteRelacionRiego(objRutaInspeccion);
    }

    public void llenarCombos() {
        //Ciclo
        List<Combo> listaCiclos = _objCatalogosBP.GetAllCicloCombo();
        ArrayAdapter<Combo> adapterCiclo = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaCiclos);
        rutainspeccion_sCiclo.setAdapter(adapterCiclo);

        //Sistema de produccion
        List<Combo> listaSistemaProduccion = _objCatalogosBP.GetAllSistemaProduccionCombo();
        ArrayAdapter<Combo> adapterSistemaProduccion = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaSistemaProduccion);
        rutainspeccion_sSistemaProduccion.setAdapter(adapterSistemaProduccion);

        //Arreglo topologico
        List<Combo> listaArregloTopologico = _objCatalogosBP.GetAllArregloTopologicoCombo();
        ArrayAdapter<Combo> adapterArregloTopologico = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaArregloTopologico);
        rutainspeccion_sArregloTopologico.setAdapter(adapterArregloTopologico);

        //Etapa fenologica
        List<Combo> listaEtapaFenologica = _objCatalogosBP.GetAllEtapaFenologicaCombo();
        ArrayAdapter<Combo> adapterEtapaFenologica = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaEtapaFenologica);
        rutainspeccion_sEtapa.setAdapter(adapterEtapaFenologica);

        //Condiciones de desarrollo
        List<Combo> listaCondicionDesarrollo = _objCatalogosBP.GetAllCondicionDesarrolloCombo();
        ArrayAdapter<Combo> adapterCondicionDesarrollo = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaCondicionDesarrollo);
        rutainspeccion_sCondicionDesarrollo.setAdapter(adapterCondicionDesarrollo);

        //Plagas
        List<Combo> listaPlaga = _objCatalogosBP.GetAllPlagaCombo();
        ArrayAdapter<Combo> adapterPlaga = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaPlaga);
        rutainspeccion_sPlaga.setAdapter(adapterPlaga);

        //Malezas
        List<Combo> listaMaleza = _objCatalogosBP.GetAllMalezaCombo();
        ArrayAdapter<Combo> adapterMaleza = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaMaleza);
        rutainspeccion_sMaleza.setAdapter(adapterMaleza);

        //Enfermedades
        List<Combo> listaEnfermedad = _objCatalogosBP.GetAllEnfermedadCombo();
        ArrayAdapter<Combo> adapterEnfermedad = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaEnfermedad);
        rutainspeccion_sEnfermedad.setAdapter(adapterEnfermedad);

        //Estado plagas
        List<Combo> listaEstadoPlaga = _objCatalogosBP.GetAllEstadoPlagaCombo();
        ArrayAdapter<Combo> adapterEstadoPlaga = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaEstadoPlaga);
        rutainspeccion_sEstadoPlaga.setAdapter(adapterEstadoPlaga);

        //Estado malezas
        List<Combo> listaEstadoMaleza = _objCatalogosBP.GetAllEstadoMalezaCombo();
        ArrayAdapter<Combo> adapterEstadoMaleza = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaEstadoMaleza);
        rutainspeccion_sEstadoMaleza.setAdapter(adapterEstadoMaleza);

        //Estado enfermedades
        List<Combo> listaEstadoEnfermedad = _objCatalogosBP.GetAllEstadoEnfermedadCombo();
        ArrayAdapter<Combo> adapterEstadoEnfermedad = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaEstadoEnfermedad);
        rutainspeccion_sEstadoEnfermedad.setAdapter(adapterEstadoEnfermedad);

        //Potencial de rendimiento
        List<Combo> listaPotencialRendimiento = _objCatalogosBP.GetAllPotencialRendimientoCombo();
        ArrayAdapter<Combo> adapterPotencialRendimiento = new ArrayAdapter<Combo>(InspeccionCampo.this, R.layout.activity_spinner, listaPotencialRendimiento);
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
        rutainspeccion_txtComentario = (EditText) findViewById(R.id.rutainspeccion_txtComentario);
        rutainspeccion_btnGuardar = (ImageButton) findViewById(R.id.rutainspeccion_btnGuardar);
        rutainspeccion_btnFotografia = (ImageButton) findViewById(R.id.rutainspeccion_btnFotografia);
        rutainspeccion_btnEnviar = (ImageButton) findViewById(R.id.rutainspeccion_btnEnviar);
        //Mostrar imagen
        imageView = (ImageView) this.findViewById(R.id.imageView1);
    }

    private void bloquearControles() {
        //Datos generales
        rutainspeccion_txtFolio.setEnabled(false);
        rutainspeccion_sCiclo.setClickable(false);
        rutainspeccion_sCiclo.setEnabled(false);
        rutainspeccion_txtCliente = (EditText) findViewById(R.id.rutainspeccion_txtCliente);
        rutainspeccion_txtProductor = (EditText) findViewById(R.id.rutainspeccion_txtProductor);
        rutainspeccion_txtPredio = (EditText) findViewById(R.id.rutainspeccion_txtPredio);
        rutainspeccion_txtLote = (EditText) findViewById(R.id.rutainspeccion_txtLote);
        rutainspeccion_txtFecha = (EditText) findViewById(R.id.rutainspeccion_txtFecha);
        //Ruta de inspeccion
        bloquearRadioGroup(rutainspeccion_rdRecomendacion);
        //Siembra
        rutainspeccion_sSistemaProduccion.setClickable(false);
        rutainspeccion_sSistemaProduccion.setEnabled(false);
        rutainspeccion_sArregloTopologico.setClickable(false);
        rutainspeccion_sArregloTopologico.setEnabled(false);
        //Condiciones de la siembra
        bloquearRadioGroup(rutainspeccion_rdAdecuada);
        bloquearRadioGroup(rutainspeccion_rdSurco);
        bloquearRadioGroup(rutainspeccion_rdManejo);
        //Riego
        rutainspeccion_chbCanal.setEnabled(false);
        rutainspeccion_chbGravedad.setEnabled(false);
        rutainspeccion_chbAspersion.setEnabled(false);
        rutainspeccion_chbGoteo.setEnabled(false);
        rutainspeccion_chbPozo.setEnabled(false);
        rutainspeccion_txtCapacidad.setEnabled(false);
        rutainspeccion_chbRiegoOtro.setEnabled(false);
        rutainspeccion_txtRiegoOtro.setEnabled(false);
        //Etapa fenologica
        rutainspeccion_sEtapa.setClickable(false);
        rutainspeccion_sEtapa.setEnabled(false);
        bloquearRadioGroup(rutainspeccion_rdExposicion);
        //Condiciones de desarrollo
        rutainspeccion_sCondicionDesarrollo.setClickable(false);
        rutainspeccion_sCondicionDesarrollo.setEnabled(false);
        //Recomendaciones
        rutainspeccion_chbRegar.setEnabled(false);
        rutainspeccion_chbDesmezclar.setEnabled(false);
        rutainspeccion_chbFungicida.setEnabled(false);
        rutainspeccion_chbInsecticida.setEnabled(false);
        rutainspeccion_chbHerbicida.setEnabled(false);
        rutainspeccion_chbDesaguar.setEnabled(false);
        rutainspeccion_chbDescostrar.setEnabled(false);
        rutainspeccion_chbRecomendacionOtro.setEnabled(false);
        rutainspeccion_txtRecomendacionOtro.setEnabled(false);
        //Manejo de agroquimico
        bloquearRadioGroup(rutainspeccion_rdOrden);
        bloquearRadioGroup(rutainspeccion_rdRegula);
        bloquearRadioGroup(rutainspeccion_rdUso);
        bloquearRadioGroup(rutainspeccion_rdHora);
        bloquearRadioGroup(rutainspeccion_rdAgua);
        //Problemas
        bloquearRadioGroup(rutainspeccion_rdInundacion);
        bloquearRadioGroup(rutainspeccion_rdPoblacion);
        bloquearRadioGroup(rutainspeccion_rdProblema);
        bloquearRadioGroup(rutainspeccion_rdAlteracion);
        bloquearRadioGroup(rutainspeccion_rdAplicacion);
        bloquearRadioGroup(rutainspeccion_rdTemperatura);
        bloquearRadioGroup(rutainspeccion_rdFito);
        bloquearRadioGroup(rutainspeccion_rdPlaga);
        rutainspeccion_sMaleza.setClickable(false);
        rutainspeccion_sMaleza.setEnabled(false);
        rutainspeccion_sEstadoMaleza.setClickable(false);
        rutainspeccion_sEstadoMaleza.setEnabled(false);
        rutainspeccion_sPlaga.setClickable(false);
        rutainspeccion_sPlaga.setEnabled(false);
        rutainspeccion_sEstadoPlaga.setClickable(false);
        rutainspeccion_sEstadoPlaga.setEnabled(false);
        rutainspeccion_sEnfermedad.setClickable(false);
        rutainspeccion_sEnfermedad.setEnabled(false);
        rutainspeccion_sEstadoEnfermedad.setClickable(false);
        rutainspeccion_sEstadoEnfermedad.setEnabled(false);
        //Potencial de rendimiento
        rutainspeccion_sPotencial.setClickable(false);
        rutainspeccion_sPotencial.setEnabled(false);
        rutainspeccion_txtComentario.setEnabled(false);
        rutainspeccion_btnGuardar.setVisibility(View.GONE);
        rutainspeccion_btnFotografia.setVisibility(View.GONE);
        rutainspeccion_btnEnviar.setVisibility(View.GONE);
    }

    public void bloquearRadioGroup(RadioGroup objRadioGroup) {
        for (int i = 0; i < objRadioGroup.getChildCount(); i++) {
            objRadioGroup.getChildAt(i).setEnabled(false);
        }
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
        if (_objPlaneacionFiltro.Estatus.equals("G") || _objPlaneacionFiltro.Estatus.equals("E") || _objPlaneacionFiltro.Estatus.equals("F") || _objPlaneacionFiltro.Estatus.equals("R")) {
            cargarInspeccionCampo();
            rutainspeccion_btnFotografia.setVisibility(View.VISIBLE);
        }
        if (_objPlaneacionFiltro.Estatus.equals("G")) {
            rutainspeccion_btnFotografia.setVisibility(View.VISIBLE);
            rutainspeccion_btnEnviar.setVisibility(View.GONE);
        }
        if (_objPlaneacionFiltro.Estatus.equals("F")) {
            rutainspeccion_btnFotografia.setVisibility(View.VISIBLE);
            rutainspeccion_btnEnviar.setVisibility(View.VISIBLE);
        }
        if (_objPlaneacionFiltro.Estatus.equals("E") || _objPlaneacionFiltro.Estatus.equals("R")) {
            bloquearControles();
        }
    }

    private void cargarInspeccionCampo() {
        RutaInspeccion objRutaInspeccion = _objRutaInspeccionBP.GetRutaInspeccion(_objRutaInspeccion);
        seleccionarValorRadio(rutainspeccion_rdRecomendacion, objRutaInspeccion.RecomendacionTecnica);
        seleccionarValorSpinner(rutainspeccion_sSistemaProduccion, objRutaInspeccion.SistemaProduccionClave);
        seleccionarValorSpinner(rutainspeccion_sArregloTopologico, objRutaInspeccion.ArregloTopologicoClave);
        seleccionarValorRadio(rutainspeccion_rdAdecuada, objRutaInspeccion.ProfundidadSiembra);
        seleccionarValorRadio(rutainspeccion_rdSurco, objRutaInspeccion.ProfundidadSurco);
        seleccionarValorRadio(rutainspeccion_rdManejo, objRutaInspeccion.ManejoAdecuado);
        seleccionarValorSpinner(rutainspeccion_sEtapa, objRutaInspeccion.EtapaFenologicaClave);
        seleccionarValorRadio(rutainspeccion_rdExposicion, objRutaInspeccion.Exposicion);
        seleccionarValorSpinner(rutainspeccion_sCondicionDesarrollo, objRutaInspeccion.CondicionDesarrolloClave);
        seleccionarValorRadio(rutainspeccion_rdOrden, objRutaInspeccion.OrdenCorrecto);
        seleccionarValorRadio(rutainspeccion_rdRegula, objRutaInspeccion.RegulaPh);
        seleccionarValorRadio(rutainspeccion_rdUso, objRutaInspeccion.UsoAdecuado);
        seleccionarValorRadio(rutainspeccion_rdHora, objRutaInspeccion.HoraAplicacion);
        seleccionarValorRadio(rutainspeccion_rdAgua, objRutaInspeccion.AguaCanal);
        seleccionarValorRadio(rutainspeccion_rdInundacion, objRutaInspeccion.Inundacion);
        seleccionarValorRadio(rutainspeccion_rdPoblacion, objRutaInspeccion.BajaPoblacion);
        seleccionarValorRadio(rutainspeccion_rdProblema, objRutaInspeccion.AplicacionNutrientes);
        seleccionarValorRadio(rutainspeccion_rdAlteracion, objRutaInspeccion.AlteracionCiclo);
        seleccionarValorRadio(rutainspeccion_rdAplicacion, objRutaInspeccion.AplicacionAgroquimicos);
        seleccionarValorRadio(rutainspeccion_rdTemperatura, objRutaInspeccion.AltasTemperaturas);
        seleccionarValorRadio(rutainspeccion_rdFito, objRutaInspeccion.Fito);
        seleccionarValorRadio(rutainspeccion_rdPlaga, objRutaInspeccion.PlagasMalControladas);
        seleccionarValorSpinner(rutainspeccion_sMaleza, objRutaInspeccion.MalezaClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoMaleza, objRutaInspeccion.EstadoMalezaClave);
        seleccionarValorSpinner(rutainspeccion_sPlaga, objRutaInspeccion.PlagaClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoPlaga, objRutaInspeccion.EstadoPlagaClave);
        seleccionarValorSpinner(rutainspeccion_sEnfermedad, objRutaInspeccion.EnfermedadClave);
        seleccionarValorSpinner(rutainspeccion_sEstadoEnfermedad, objRutaInspeccion.EstadoEnfermedadClave);
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
        rutainspeccion_txtComentario.setText(objRutaInspeccion.Comentario);
    }

    private void seleccionarValorSpinner(Spinner _objSpinner, int valor) {
        ArrayAdapter<Combo> objArrayAdapter = (ArrayAdapter<Combo>) _objSpinner.getAdapter();
        for (int i = 0; i < objArrayAdapter.getCount(); i++) {
            if (((Combo) objArrayAdapter.getItem(i)).getClave() == valor) {
                _objSpinner.setSelection(i);
            }
        }
    }

    private String obtenerValorRadio(RadioGroup objRadioGroup) {
        int id = objRadioGroup.getCheckedRadioButtonId();
        RadioButton objRadioButton = (RadioButton) findViewById(id);
        return String.valueOf(objRadioButton.getText().toString().charAt(0));
    }

    private void seleccionarValorRadio(RadioGroup objRadioGroup, String valor) {
        int count = objRadioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = objRadioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton objRadioButton = (RadioButton) o;
                if (objRadioButton.getText().toString().charAt(0) == valor.charAt(0))
                    objRadioButton.setChecked(true);
            }
        }
    }

    private int obtenerValorSpinner(Spinner _objSpinner) {
        Combo objCombo = (Combo) _objSpinner.getSelectedItem();
        return objCombo.getClave();
    }

    private void eventosCombo() {
        //Maleza
        rutainspeccion_sMaleza.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        Combo objCombo;
                        if (!(rutainspeccion_sMaleza.getSelectedItem() == null)) {
                            objCombo = (Combo) rutainspeccion_sMaleza.getSelectedItem();
                            if(objCombo.getClave()==-1)
                                seleccionarValorSpinner(rutainspeccion_sEstadoMaleza, 1);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        _objCiclo = null;
                    }
                });

        //Plaga
        rutainspeccion_sPlaga.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        Combo objCombo;
                        if (!(rutainspeccion_sPlaga.getSelectedItem() == null)) {
                            objCombo = (Combo) rutainspeccion_sPlaga.getSelectedItem();
                            if(objCombo.getClave()==-1)
                                seleccionarValorSpinner(rutainspeccion_sEstadoPlaga, 1);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        _objCiclo = null;
                    }
                });

        //Enfermedad
        rutainspeccion_sEnfermedad.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        Combo objCombo;
                        if (!(rutainspeccion_sEnfermedad.getSelectedItem() == null)) {
                            objCombo = (Combo) rutainspeccion_sEnfermedad.getSelectedItem();
                            if(objCombo.getClave()==-1)
                                seleccionarValorSpinner(rutainspeccion_sEstadoEnfermedad, 1);
                        }
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
            rutainspeccion_rdRecomendacion.setFocusable(true);
            rutainspeccion_rdRecomendacion.setFocusableInTouchMode(true);
            rutainspeccion_rdRecomendacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [¿El productor consult\u00F3 las recomendaciones técnicas?]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sSistemaProduccion.getSelectedItemPosition() == 0) {
            rutainspeccion_sSistemaProduccion.setFocusable(true);
            rutainspeccion_sSistemaProduccion.setFocusableInTouchMode(true);
            rutainspeccion_sSistemaProduccion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Sitema de producci\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sArregloTopologico.getSelectedItemPosition() == 0) {
            rutainspeccion_sArregloTopologico.setFocusable(true);
            rutainspeccion_sArregloTopologico.setFocusableInTouchMode(true);
            rutainspeccion_sArregloTopologico.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Arreglo topologico]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAdecuada.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAdecuada.setFocusable(true);
            rutainspeccion_rdAdecuada.setFocusableInTouchMode(true);
            rutainspeccion_rdAdecuada.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de siembra adecuada]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdSurco.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdSurco.setFocusable(true);
            rutainspeccion_rdSurco.setFocusableInTouchMode(true);
            rutainspeccion_rdSurco.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Profundidad de surco adecuada]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdManejo.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdManejo.setFocusable(true);
            rutainspeccion_rdManejo.setFocusableInTouchMode(true);
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
            rutainspeccion_sEtapa.setFocusable(true);
            rutainspeccion_sEtapa.setFocusableInTouchMode(true);
            rutainspeccion_sEtapa.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Etapa principal Zadoks]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdExposicion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdExposicion.setFocusable(true);
            rutainspeccion_rdExposicion.setFocusableInTouchMode(true);
            rutainspeccion_rdExposicion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Exposici\u00F3n de la excersi\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sCondicionDesarrollo.getSelectedItemPosition() == 0) {
            rutainspeccion_sCondicionDesarrollo.setFocusable(true);
            rutainspeccion_sCondicionDesarrollo.setFocusableInTouchMode(true);
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
            rutainspeccion_rdOrden.setFocusable(true);
            rutainspeccion_rdOrden.setFocusableInTouchMode(true);
            rutainspeccion_rdOrden.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Orden correcto de preparaci\u00F3n de mezcla]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdRegula.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdRegula.setFocusable(true);
            rutainspeccion_rdRegula.setFocusableInTouchMode(true);
            rutainspeccion_rdRegula.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Regula pH del agua]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdUso.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdUso.setFocusable(true);
            rutainspeccion_rdUso.setFocusableInTouchMode(true);
            rutainspeccion_rdUso.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Uso adecuado de boquillas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdHora.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdHora.setFocusable(true);
            rutainspeccion_rdHora.setFocusableInTouchMode(true);
            rutainspeccion_rdHora.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Hora de aplicaci\u00F3n]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAgua.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAgua.setFocusable(true);
            rutainspeccion_rdAgua.setFocusableInTouchMode(true);
            rutainspeccion_rdAgua.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Agua de canal para aplicaciones]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdInundacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdInundacion.setFocusable(true);
            rutainspeccion_rdInundacion.setFocusableInTouchMode(true);
            rutainspeccion_rdInundacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Inundación]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdPoblacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPoblacion.setFocusable(true);
            rutainspeccion_rdPoblacion.setFocusableInTouchMode(true);
            rutainspeccion_rdPoblacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Baja población de plantas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdProblema.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdProblema.setFocusable(true);
            rutainspeccion_rdProblema.setFocusableInTouchMode(true);
            rutainspeccion_rdProblema.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Problemas en la aplicaci\u00F3n de nutrientes]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAlteracion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAlteracion.setFocusable(true);
            rutainspeccion_rdAlteracion.setFocusableInTouchMode(true);
            rutainspeccion_rdAlteracion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Alteraci\u00F3n de ciclo]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdAplicacion.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdAplicacion.setFocusable(true);
            rutainspeccion_rdAplicacion.setFocusableInTouchMode(true);
            rutainspeccion_rdAplicacion.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Aplicaci\u00F3n adecuada de agroquímicos]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdTemperatura.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdTemperatura.setFocusable(true);
            rutainspeccion_rdTemperatura.setFocusableInTouchMode(true);
            rutainspeccion_rdTemperatura.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Altas temperaturas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdFito.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdFito.setFocusable(true);
            rutainspeccion_rdFito.setFocusableInTouchMode(true);
            rutainspeccion_rdFito.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Fitotoxicidad]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_rdPlaga.getCheckedRadioButtonId() == -1) {
            rutainspeccion_rdPlaga.setFocusable(true);
            rutainspeccion_rdPlaga.setFocusableInTouchMode(true);
            rutainspeccion_rdPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Plagas mal controladas]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sMaleza.setFocusable(true);
            rutainspeccion_sMaleza.setFocusableInTouchMode(true);
            rutainspeccion_sMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Maleza]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoMaleza.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoMaleza.setFocusable(true);
            rutainspeccion_sEstadoMaleza.setFocusableInTouchMode(true);
            rutainspeccion_sEstadoMaleza.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Maleza)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sPlaga.setFocusable(true);
            rutainspeccion_sPlaga.setFocusableInTouchMode(true);
            rutainspeccion_sPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Insectos]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoPlaga.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoPlaga.setFocusable(true);
            rutainspeccion_sEstadoPlaga.setFocusableInTouchMode(true);
            rutainspeccion_sEstadoPlaga.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Insectos)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEnfermedad.setFocusable(true);
            rutainspeccion_sEnfermedad.setFocusableInTouchMode(true);
            rutainspeccion_sEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Enfermedad]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sEstadoEnfermedad.getSelectedItemPosition() == 0) {
            rutainspeccion_sEstadoEnfermedad.setFocusable(true);
            rutainspeccion_sEstadoEnfermedad.setFocusableInTouchMode(true);
            rutainspeccion_sEstadoEnfermedad.requestFocus();
            _objComunBP.Mensaje("*Favor de seleccionar una opci\u00F3n. [Grado de infestaci\u00F3n (Enfermedad)]", InspeccionCampo.this);
            return validar = false;
        }
        if (rutainspeccion_sPotencial.getSelectedItemPosition() == 0) {
            rutainspeccion_sPotencial.setFocusable(true);
            rutainspeccion_sPotencial.setFocusableInTouchMode(true);
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

    //Eventos camara
    //Crear numero aleatorio
    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(60, random).toString(16);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Take_Photo) {
                String filePath = null;
                filePath = str_Camera_Photo_ImagePath;
                if (filePath != null) {
                    Bitmap faceView = (new_decode(new File(filePath)));
                    imageView.setImageBitmap(faceView);
                    RutaInspeccion objRutaInspeccion = null;
                    objRutaInspeccion = creaObjeto();
                    objRutaInspeccion.Estatus = "F";
                    boolean resul = _objRutaInspeccionBP.UpdateRutaInspeccion(objRutaInspeccion);
                    getPreferences();
                    _objPlaneacionRuta.Estatus = "F";
                    resul = _objRutaInspeccionBP.UpdatePlaneacionRutaEstatus(_objPlaneacionRuta);
                    if (gpsTracker.getIsGPSTrackingEnabled()) {
                        addGeo(gpsTracker.getLatitude(), gpsTracker.getLongitude(), filePath);
                    }
                    rutainspeccion_btnEnviar.setVisibility(View.VISIBLE);
                } else {
                    bitmap = null;
                }
            }
            gpsTracker.stopUsingGPS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap new_decode(File f) {
        //Tamano de la imagen
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inDither = false; // Disable Dithering mode
        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared
        o.inInputShareable = true; // Which kind of reference will be used to
        // recover the Bitmap data after being
        // clear, when it will be used in the future
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 300;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 1.5 < REQUIRED_SIZE && height_tmp / 1.5 < REQUIRED_SIZE)
                break;
            width_tmp /= 1.5;
            height_tmp /= 1.5;
            scale *= 1.5;
        }
        // decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        // o2.inSampleSize=scale;
        o.inDither = false; // Disable Dithering mode
        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared
        o.inInputShareable = true; // Which kind of reference will be used to
        // recover the Bitmap data after being
        // clear, when it will be used in the future
        // return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        try {
//          return BitmapFactory.decodeStream(new FileInputStream(f), null,
//                  null);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            System.out.println(" IW " + width_tmp);
            System.out.println("IHH " + height_tmp);
            int iW = width_tmp;
            int iH = height_tmp;
            return Bitmap.createScaledBitmap(bitmap, iW, iH, true);
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            e.printStackTrace();
            // clearCache();
            // System.out.println("bitmap creating success");
            System.gc();
            return null;
            // System.runFinalization();
            // Runtime.getRuntime().gc();
            // System.gc();
            // decodeFile(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void addGeo(double latitude, double longitude, String location) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(location);
            int num1Lat = (int) Math.floor(latitude);
            int num2Lat = (int) Math.floor((latitude - num1Lat) * 60);
            double num3Lat = (latitude - ((double) num1Lat + ((double) num2Lat / 60))) * 3600000;
            int num1Lon = (int) Math.floor(longitude);
            int num2Lon = (int) Math.floor((longitude - num1Lon) * 60);
            double num3Lon = (longitude - ((double) num1Lon + ((double) num2Lon / 60))) * 3600000;
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1," + num2Lat + "/1," + num3Lat + "/1000");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon + "/1," + num2Lon + "/1," + num3Lon + "/1000");
            if (latitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
            }
            if (longitude > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
            }
            exif.saveAttributes();
        } catch (IOException e) {
            Log.e("PictureActivity", e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alert;
        if (rutainspeccion_btnGuardar.getVisibility() == View.VISIBLE) {
            alert = new AlertDialog.Builder(
                    new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                    .create();
            alert.setTitle("Mensaje");
            alert.setMessage("\u00BFDeseas salir de la captura de la ruta de inpecci\u00F3n?");
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
        } else
            finish();
    }

    private void enviarInformacion() throws Exception {
        RutaInspeccion objRutaInspeccion = creaObjeto();
        objRutaInspeccion.Estatus = "E";
        _objPlaneacionRuta.Estatus = "E";
        _objRutaInspeccionBP.UpdatePlaneacionRutaEstatus(_objPlaneacionRuta);
        boolean resul = _objRutaInspeccionBP.UpdateRutaInspeccion(objRutaInspeccion);
        _objComunBP.Mensaje("La informaci\u00F3n se enviara a IASA", InspeccionCampo.this);
        rutainspeccion_btnEnviar.setVisibility(View.GONE);
        rutainspeccion_btnFotografia.setVisibility(View.GONE);
        rutainspeccion_btnGuardar.setVisibility(View.GONE);
        bloquearControles();
    }
}
