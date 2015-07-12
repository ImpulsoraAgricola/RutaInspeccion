package com.iasacv.impulsora.rutainspeccion.Datos;

import android.app.Activity;
import android.content.Context;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.CondicionDesarrollo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Enfermedad;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.EtapaFenologica;
import com.iasacv.impulsora.rutainspeccion.Modelo.Maleza;
import com.iasacv.impulsora.rutainspeccion.Modelo.Plaga;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;
import com.iasacv.impulsora.rutainspeccion.Modelo.Recomendacion;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.SistemaProduccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoRiego;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Administrator on 24/06/2015.
 */
public class WebServiceDA extends Activity {

    //Variables
    private EntLibDBTools _objEntLibTools;
    private CicloDA _objCicloDA;
    private TipoArticuloDA _objTipoArticuloDA;
    private TipoInspeccionDA _objTipoInspeccionDA;
    private EtapaFenologicaDA _objEtapaFenologicaDA;
    private PotencialRendimientoDA _objPotencialRendimientoDA;
    private EstadoMalezaDA _objEstadoMalezaDA;
    private EstadoPlagaDA _objEstadoPlagaDA;
    private EstadoEnfermedadDA _objEstadoEnfermedadDA;
    private PlaneacionRutaDA _objPlaneacionRutaDA;
    private SistemaProduccionDA _objSistemaProduccionDA;
    private TipoRiegoDA _objTipoRiegoDA;
    private CondicionDesarrolloDA _objCondicionDesarrolloDA;
    private RecomendacionDA _objRecomendacionDA;
    private PlagaDA _objPlagaDA;
    private MalezaDA _objMalezaDA;
    private EnfermedadDA _objEnfermedadDA;
    private ArregloTopologicoDA _objArregloTopologicoDA;

    public WebServiceDA(Context context) {
        _objEntLibTools = new EntLibDBTools(context);
        _objCicloDA = new CicloDA(context);
        _objTipoArticuloDA = new TipoArticuloDA(context);
        _objTipoInspeccionDA = new TipoInspeccionDA(context);
        _objEtapaFenologicaDA = new EtapaFenologicaDA(context);
        _objPotencialRendimientoDA = new PotencialRendimientoDA(context);
        _objEstadoMalezaDA = new EstadoMalezaDA(context);
        _objEstadoPlagaDA = new EstadoPlagaDA(context);
        _objEstadoEnfermedadDA = new EstadoEnfermedadDA(context);
        _objPlaneacionRutaDA = new PlaneacionRutaDA(context);
        _objSistemaProduccionDA = new SistemaProduccionDA(context);
        _objTipoRiegoDA = new TipoRiegoDA(context);
        _objCondicionDesarrolloDA = new CondicionDesarrolloDA(context);
        _objRecomendacionDA = new RecomendacionDA(context);
        _objPlagaDA = new PlagaDA(context);
        _objMalezaDA = new MalezaDA(context);
        _objEnfermedadDA = new EnfermedadDA(context);
        _objArregloTopologicoDA = new ArregloTopologicoDA(context);
    }

    //Variable para Web Services
    String NAMESPACE = "http://192.168.10.20:8082/";
    String URL = "http://192.168.10.20:8082/Service.asmx?wsdl";

    public boolean getCatalogos(String usuario) throws IOException, XmlPullParserException {
        //Variables
        boolean resul = true;

        //Se elimina la version anterior de las tablas
        _objEntLibTools.dropTable();

        //Actualizar tabla de usuarios
        Ciclo[] listaCiclos;
        String METHOD_NAME = "GetAllCiclos";
        String SOAP_ACTION = NAMESPACE+"GetAllCiclos";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaCiclos = new Ciclo[resSoap.getPropertyCount()];
            for (int i = 0; i < listaCiclos.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                Ciclo objCiclo = new Ciclo();
                objCiclo.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objCiclo.Nombre = ic.getProperty(1).toString();
                objCiclo.FechaInicio = ic.getProperty(2).toString();
                objCiclo.FechaFin = ic.getProperty(3).toString();
                objCiclo.NombreAbreviado = ic.getProperty(4).toString();
                objCiclo.Estatus = ic.getProperty(5).toString();
                objCiclo.Uso = ic.getProperty(6).toString();
                resul = _objCicloDA.InsertCiclo(objCiclo);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de tipo de articulo
        TipoArticulo[] listaTipoArticulo;
        METHOD_NAME = "GetAllTipoArticulo";
        SOAP_ACTION = NAMESPACE+"GetAllTipoArticulo";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaTipoArticulo = new TipoArticulo[resSoap.getPropertyCount()];
            for (int i = 0; i < listaTipoArticulo.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                TipoArticulo objTipoArticulo = new TipoArticulo();
                objTipoArticulo.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objTipoArticulo.Nombre = ic.getProperty(1).toString();
                objTipoArticulo.Estatus = ic.getProperty(2).toString();
                objTipoArticulo.Uso = ic.getProperty(3).toString();
                resul = _objTipoArticuloDA.InsertTipoArticulo(objTipoArticulo);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de tipo de inspeccion
        TipoInspeccion[] listaTipoInspeccion;
        METHOD_NAME = "GetAllTipoInspeccion";
        SOAP_ACTION = NAMESPACE+"GetAllTipoInspeccion";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaTipoInspeccion = new TipoInspeccion[resSoap.getPropertyCount()];
            for (int i = 0; i < listaTipoInspeccion.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                TipoInspeccion objTipoInspeccion = new TipoInspeccion();
                objTipoInspeccion.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objTipoInspeccion.Nombre = ic.getProperty(1).toString();
                objTipoInspeccion.Estatus = ic.getProperty(2).toString();
                objTipoInspeccion.Uso = ic.getProperty(3).toString();
                resul = _objTipoInspeccionDA.InsertTipoInspeccion(objTipoInspeccion);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de etapa fenologica
        EtapaFenologica[] listaEtapaFenologica;
        METHOD_NAME = "GetAllEtapaFenologica";
        SOAP_ACTION = NAMESPACE+"GetAllEtapaFenologica";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaEtapaFenologica = new EtapaFenologica[resSoap.getPropertyCount()];
            for (int i = 0; i < listaEtapaFenologica.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                EtapaFenologica objEtapaFenologica = new EtapaFenologica();
                objEtapaFenologica.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objEtapaFenologica.Nombre = ic.getProperty(1).toString();
                objEtapaFenologica.Estatus = ic.getProperty(2).toString();
                objEtapaFenologica.Uso = ic.getProperty(3).toString();
                resul = _objEtapaFenologicaDA.InsertEtapaFenologica(objEtapaFenologica);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de potencial de rendimiento
        PotencialRendimiento[] listaPotencialRendimiento;
        METHOD_NAME = "GetAllPotencialRendimiento";
        SOAP_ACTION = NAMESPACE+"GetAllPotencialRendimiento";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaPotencialRendimiento = new PotencialRendimiento[resSoap.getPropertyCount()];
            for (int i = 0; i < listaPotencialRendimiento.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                PotencialRendimiento objPotencialRendimiento = new PotencialRendimiento();
                objPotencialRendimiento.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objPotencialRendimiento.Nombre = ic.getProperty(1).toString();
                objPotencialRendimiento.Estatus = ic.getProperty(2).toString();
                objPotencialRendimiento.Uso = ic.getProperty(3).toString();
                resul = _objPotencialRendimientoDA.InsertPotencialRendimiento(objPotencialRendimiento);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de estado de malezas
        EstadoMPE[] listaEstadoMaleza;
        METHOD_NAME = "GetAllEstadoMaleza";
        SOAP_ACTION = NAMESPACE+"GetAllEstadoMaleza";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaEstadoMaleza = new EstadoMPE[resSoap.getPropertyCount()];
            for (int i = 0; i < listaEstadoMaleza.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                EstadoMPE objEstadoMaleza = new EstadoMPE();
                objEstadoMaleza.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objEstadoMaleza.Nombre = ic.getProperty(1).toString();
                objEstadoMaleza.Estatus = ic.getProperty(2).toString();
                objEstadoMaleza.Uso = ic.getProperty(3).toString();
                resul = _objEstadoMalezaDA.InsertEstadoMaleza(objEstadoMaleza);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de estado de plaga
        EstadoMPE[] listaEstadoPlaga;
        METHOD_NAME = "GetAllEstadoPlaga";
        SOAP_ACTION = NAMESPACE+"GetAllEstadoPlaga";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaEstadoPlaga = new EstadoMPE[resSoap.getPropertyCount()];
            for (int i = 0; i < listaEstadoPlaga.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                EstadoMPE objEstadoPlaga = new EstadoMPE();
                objEstadoPlaga.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objEstadoPlaga.Nombre = ic.getProperty(1).toString();
                objEstadoPlaga.Estatus = ic.getProperty(2).toString();
                objEstadoPlaga.Uso = ic.getProperty(3).toString();
                resul = _objEstadoPlagaDA.InsertEstadoPlaga(objEstadoPlaga);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de estado de enfermedad
        EstadoMPE[] listaEstadoEnfermedad;
        METHOD_NAME = "GetAllEstadoEnfermedad";
        SOAP_ACTION = NAMESPACE+"GetAllEstadoEnfermedad";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaEstadoEnfermedad = new EstadoMPE[resSoap.getPropertyCount()];
            for (int i = 0; i < listaEstadoEnfermedad.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                EstadoMPE objEstadoEnfermedad = new EstadoMPE();
                objEstadoEnfermedad.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objEstadoEnfermedad.Nombre = ic.getProperty(1).toString();
                objEstadoEnfermedad.Estatus = ic.getProperty(2).toString();
                objEstadoEnfermedad.Uso = ic.getProperty(3).toString();
                resul = _objEstadoEnfermedadDA.InsertEstadoEnfermedad(objEstadoEnfermedad);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de sistema de produccion
        SistemaProduccion[] listaSistemaProduccion;
        METHOD_NAME = "GetAllSistemaProduccion";
        SOAP_ACTION = NAMESPACE+"GetAllSistemaProduccion";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaSistemaProduccion = new SistemaProduccion[resSoap.getPropertyCount()];
            for (int i = 0; i < listaSistemaProduccion.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                SistemaProduccion objSistemaProduccion = new SistemaProduccion();
                objSistemaProduccion.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objSistemaProduccion.Nombre = ic.getProperty(1).toString();
                objSistemaProduccion.Estatus = ic.getProperty(2).toString();
                objSistemaProduccion.Uso = ic.getProperty(3).toString();
                resul = _objSistemaProduccionDA.InsertSistemaProduccion(objSistemaProduccion);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de tipo de riego
        TipoRiego[] listaTipoRiego;
        METHOD_NAME = "GetAllTipoRiego";
        SOAP_ACTION = NAMESPACE+"GetAllTipoRiego";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaTipoRiego = new TipoRiego[resSoap.getPropertyCount()];
            for (int i = 0; i < listaTipoRiego.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                TipoRiego objTipoRiego = new TipoRiego();
                objTipoRiego.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objTipoRiego.Nombre = ic.getProperty(1).toString();
                objTipoRiego.Estatus = ic.getProperty(2).toString();
                objTipoRiego.Uso = ic.getProperty(3).toString();
                resul = _objTipoRiegoDA.InsertTipoRiego(objTipoRiego);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de condicion de desarrollo
        CondicionDesarrollo[] listaCondicionDesarrollo;
        METHOD_NAME = "GetAllCondicionDesarrollo";
        SOAP_ACTION = NAMESPACE+"GetAllCondicionDesarrollo";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaCondicionDesarrollo = new CondicionDesarrollo[resSoap.getPropertyCount()];
            for (int i = 0; i < listaCondicionDesarrollo.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                CondicionDesarrollo objCondicionDesarrollo = new CondicionDesarrollo();
                objCondicionDesarrollo.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objCondicionDesarrollo.Nombre = ic.getProperty(1).toString();
                objCondicionDesarrollo.Estatus = ic.getProperty(2).toString();
                objCondicionDesarrollo.Uso = ic.getProperty(3).toString();
                resul = _objCondicionDesarrolloDA.InsertCondicionDesarrollo(objCondicionDesarrollo);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de recomendacion
        Recomendacion[] listaRecomendacion;
        METHOD_NAME = "GetAllRecomendacion";
        SOAP_ACTION = NAMESPACE+"GetAllRecomendacion";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaRecomendacion = new Recomendacion[resSoap.getPropertyCount()];
            for (int i = 0; i < listaRecomendacion.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                Recomendacion objRecomendacion = new Recomendacion();
                objRecomendacion.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objRecomendacion.Nombre = ic.getProperty(1).toString();
                objRecomendacion.Estatus = ic.getProperty(2).toString();
                objRecomendacion.Uso = ic.getProperty(3).toString();
                resul = _objRecomendacionDA.InsertRecomendacion(objRecomendacion);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de plaga
        Plaga[] listaPlaga;
        METHOD_NAME = "GetAllPlaga";
        SOAP_ACTION = NAMESPACE+"GetAllPlaga";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaPlaga = new Plaga[resSoap.getPropertyCount()];
            for (int i = 0; i < listaPlaga.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                Plaga objPlaga = new Plaga();
                objPlaga.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objPlaga.Nombre = ic.getProperty(1).toString();
                objPlaga.Estatus = ic.getProperty(2).toString();
                objPlaga.Uso = ic.getProperty(3).toString();
                resul = _objPlagaDA.InsertPlaga(objPlaga);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de enfermedad
        Enfermedad[] listaEnfermedad;
        METHOD_NAME = "GetAllEnfermedad";
        SOAP_ACTION = NAMESPACE+"GetAllEnfermedad";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaEnfermedad = new Enfermedad[resSoap.getPropertyCount()];
            for (int i = 0; i < listaEnfermedad.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                Enfermedad objEnfermedad = new Enfermedad();
                objEnfermedad.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objEnfermedad.Nombre = ic.getProperty(1).toString();
                objEnfermedad.Estatus = ic.getProperty(2).toString();
                objEnfermedad.Uso = ic.getProperty(3).toString();
                resul = _objEnfermedadDA.InsertEnfermedad(objEnfermedad);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de maleza
        Maleza[] listaMaleza;
        METHOD_NAME = "GetAllMaleza";
        SOAP_ACTION = NAMESPACE+"GetAllMaleza";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaMaleza = new Maleza[resSoap.getPropertyCount()];
            for (int i = 0; i < listaMaleza.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                Maleza objMaleza = new Maleza();
                objMaleza.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objMaleza.Nombre = ic.getProperty(1).toString();
                objMaleza.Estatus = ic.getProperty(2).toString();
                objMaleza.Uso = ic.getProperty(3).toString();
                resul = _objMalezaDA.InsertMaleza(objMaleza);
            }
        } catch (Exception e) {
            throw e;
        }

        //Actualizar tabla de arreglo topologico
        ArregloTopologico[] listaArregloTopologico;
        METHOD_NAME = "GetAllArregloTopologico";
        SOAP_ACTION = NAMESPACE+"GetAllArregloTopologico";
        request = new SoapObject(NAMESPACE, METHOD_NAME);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            listaArregloTopologico = new ArregloTopologico[resSoap.getPropertyCount()];
            for (int i = 0; i < listaArregloTopologico.length; i++) {
                SoapObject ic = (SoapObject)resSoap.getProperty(i);
                ArregloTopologico objArregloTopologico = new ArregloTopologico();
                objArregloTopologico.Clave = Integer.parseInt(ic.getProperty(0).toString());
                objArregloTopologico.Nombre = ic.getProperty(1).toString();
                objArregloTopologico.Estatus = ic.getProperty(2).toString();
                objArregloTopologico.Uso = ic.getProperty(3).toString();
                resul = _objArregloTopologicoDA.InsertArregloTopologico(objArregloTopologico);
            }
        } catch (Exception e) {
            throw e;
        }
        return resul;
    }

    public boolean getPlaneacionRuta(String usuario, int usuarioCve, String fecha) throws IOException, XmlPullParserException {

        //Variables
        boolean resul = true;

        //Actualizar tabla de planeacion de ruta de inspeccion
        PlaneacionRuta[] listaPlaneacionRuta;
        String METHOD_NAME = "GetAllPlaneacionRuta";
        String SOAP_ACTION = NAMESPACE + "GetAllPlaneacionRuta";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        request.addProperty("usuarioCve", usuarioCve);
        request.addProperty("fecha", fecha);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            _objEntLibTools.executeQuery("DELETE FROM BATPLADE WHERE PLADESTS!=\"O\"");
            listaPlaneacionRuta = new PlaneacionRuta[resSoap.getPropertyCount()];
            for (int i = 0; i < listaPlaneacionRuta.length; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                if(Integer.parseInt(ic.getProperty(0).toString())== -1)
                    resul = false;
                else {
                    //Se elimina la version anterior de las tablas
                    PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
                    objPlaneacionRuta.UsuarioClave = Integer.parseInt(ic.getProperty(0).toString());
                    objPlaneacionRuta.UsuarioNombre = ic.getProperty(1).toString();
                    objPlaneacionRuta.CicloClave = Integer.parseInt(ic.getProperty(2).toString());
                    objPlaneacionRuta.CicloNombre = ic.getProperty(3).toString();
                    objPlaneacionRuta.Fecha = ic.getProperty(4).toString();
                    objPlaneacionRuta.Folio = Integer.parseInt(ic.getProperty(5).toString());
                    objPlaneacionRuta.TipoInspeccionClave = Integer.parseInt(ic.getProperty(6).toString());
                    objPlaneacionRuta.TipoInspeccionNombre = ic.getProperty(7).toString();
                    objPlaneacionRuta.TipoArticuloClave = Integer.parseInt(ic.getProperty(8).toString());
                    objPlaneacionRuta.TipoArticuloNombre = ic.getProperty(9).toString();
                    objPlaneacionRuta.ClienteClave = Integer.parseInt(ic.getProperty(10).toString());
                    objPlaneacionRuta.ClienteNombre = ic.getProperty(11).toString();
                    objPlaneacionRuta.ProductorClave = Integer.parseInt(ic.getProperty(12).toString());
                    objPlaneacionRuta.ProductorNombre = ic.getProperty(13).toString();
                    objPlaneacionRuta.PredioClave = Integer.parseInt(ic.getProperty(14).toString());
                    objPlaneacionRuta.PredioNombre = ic.getProperty(15).toString();
                    objPlaneacionRuta.PredioLatitud = Double.parseDouble(ic.getProperty(16).toString());
                    objPlaneacionRuta.PredioLongitud = Double.parseDouble(ic.getProperty(17).toString());
                    objPlaneacionRuta.LoteClave = Integer.parseInt(ic.getProperty(18).toString());
                    objPlaneacionRuta.LoteNombre = ic.getProperty(19).toString();
                    objPlaneacionRuta.LoteLatitud = Double.parseDouble(ic.getProperty(20).toString());
                    objPlaneacionRuta.LoteLongitud = Double.parseDouble(ic.getProperty(21).toString());
                    objPlaneacionRuta.ArticuloSembrarClave = Integer.parseInt(ic.getProperty(22).toString());
                    objPlaneacionRuta.ArticuloSembrarNombre = ic.getProperty(23).toString();
                    objPlaneacionRuta.ArticuloCosecharClave = Integer.parseInt(ic.getProperty(24).toString());
                    objPlaneacionRuta.ArticuloCosecharNombre = ic.getProperty(25).toString();
                    objPlaneacionRuta.Estatus = ic.getProperty(26).toString();
                    objPlaneacionRuta.Uso = ic.getProperty(27).toString();
                    resul = _objPlaneacionRutaDA.InsertPlaneacionRuta(objPlaneacionRuta);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return resul;
    }

    public boolean getPlaneacionRuta(String usuario, int usuarioCve, String fecha) throws IOException, XmlPullParserException {

        //Variables
        boolean resul = true;

        //Actualizar tabla de planeacion de ruta de inspeccion
        PlaneacionRuta[] listaPlaneacionRuta;
        String METHOD_NAME = "GetAllPlaneacionRuta";
        String SOAP_ACTION = NAMESPACE + "GetAllPlaneacionRuta";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        request.addProperty("usuario", usuario);
        request.addProperty("usuarioCve", usuarioCve);
        request.addProperty("fecha", fecha);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject resSoap = (SoapObject) envelope.getResponse();
            _objEntLibTools.executeQuery("DELETE FROM BATPLADE WHERE PLADESTS!=\"O\"");
            listaPlaneacionRuta = new PlaneacionRuta[resSoap.getPropertyCount()];
            for (int i = 0; i < listaPlaneacionRuta.length; i++) {
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
                if(Integer.parseInt(ic.getProperty(0).toString())== -1)
                    resul = false;
                else {
                    //Se elimina la version anterior de las tablas
                    PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
                    objPlaneacionRuta.UsuarioClave = Integer.parseInt(ic.getProperty(0).toString());
                    objPlaneacionRuta.UsuarioNombre = ic.getProperty(1).toString();
                    objPlaneacionRuta.CicloClave = Integer.parseInt(ic.getProperty(2).toString());
                    objPlaneacionRuta.CicloNombre = ic.getProperty(3).toString();
                    objPlaneacionRuta.Fecha = ic.getProperty(4).toString();
                    objPlaneacionRuta.Folio = Integer.parseInt(ic.getProperty(5).toString());
                    objPlaneacionRuta.TipoInspeccionClave = Integer.parseInt(ic.getProperty(6).toString());
                    objPlaneacionRuta.TipoInspeccionNombre = ic.getProperty(7).toString();
                    objPlaneacionRuta.TipoArticuloClave = Integer.parseInt(ic.getProperty(8).toString());
                    objPlaneacionRuta.TipoArticuloNombre = ic.getProperty(9).toString();
                    objPlaneacionRuta.ClienteClave = Integer.parseInt(ic.getProperty(10).toString());
                    objPlaneacionRuta.ClienteNombre = ic.getProperty(11).toString();
                    objPlaneacionRuta.ProductorClave = Integer.parseInt(ic.getProperty(12).toString());
                    objPlaneacionRuta.ProductorNombre = ic.getProperty(13).toString();
                    objPlaneacionRuta.PredioClave = Integer.parseInt(ic.getProperty(14).toString());
                    objPlaneacionRuta.PredioNombre = ic.getProperty(15).toString();
                    objPlaneacionRuta.PredioLatitud = Double.parseDouble(ic.getProperty(16).toString());
                    objPlaneacionRuta.PredioLongitud = Double.parseDouble(ic.getProperty(17).toString());
                    objPlaneacionRuta.LoteClave = Integer.parseInt(ic.getProperty(18).toString());
                    objPlaneacionRuta.LoteNombre = ic.getProperty(19).toString();
                    objPlaneacionRuta.LoteLatitud = Double.parseDouble(ic.getProperty(20).toString());
                    objPlaneacionRuta.LoteLongitud = Double.parseDouble(ic.getProperty(21).toString());
                    objPlaneacionRuta.ArticuloSembrarClave = Integer.parseInt(ic.getProperty(22).toString());
                    objPlaneacionRuta.ArticuloSembrarNombre = ic.getProperty(23).toString();
                    objPlaneacionRuta.ArticuloCosecharClave = Integer.parseInt(ic.getProperty(24).toString());
                    objPlaneacionRuta.ArticuloCosecharNombre = ic.getProperty(25).toString();
                    objPlaneacionRuta.Estatus = ic.getProperty(26).toString();
                    objPlaneacionRuta.Uso = ic.getProperty(27).toString();
                    resul = _objPlaneacionRutaDA.InsertPlaneacionRuta(objPlaneacionRuta);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return resul;
    }

    public Usuario getLogin(Usuario objUsuario) throws IOException, XmlPullParserException {
        //Variables
        Usuario _objUsuario = new Usuario();

        String METHOD_NAME = "GetLogin";
        String SOAP_ACTION = "http://192.168.10.20:8082/GetLogin";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("entidad");
        pi.setValue(objUsuario);
        pi.setType(Usuario.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE,"Usuario",new Usuario().getClass());
        AndroidHttpTransport transporte = new AndroidHttpTransport (URL);
        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapObject ic = (SoapObject)envelope.getResponse();
            _objUsuario.Clave = Integer.parseInt(ic.getProperty(0).toString());
            _objUsuario.Nombre = ic.getProperty(1).toString();
            _objUsuario.RolClave = Integer.parseInt(ic.getProperty(2).toString());
            _objUsuario.FechaVencimientoPassword = ic.getProperty(3).toString();
            _objUsuario.FechaAlta = ic.getProperty(4).toString();
            _objUsuario.RFC = ic.getProperty(5).toString();
            _objUsuario.Password = ic.getProperty(6).toString();
            _objUsuario.Email = ic.getProperty(7).toString();
            _objUsuario.Estatus = ic.getProperty(8).toString();
            _objUsuario.Uso = ic.getProperty(9).toString();
        }
        catch (Exception e)
        {
            throw e;
        }
        return _objUsuario;
    }

    public boolean insertRutaInspeccion(RutaInspeccion objRutaInspeccion) throws IOException, XmlPullParserException {
        boolean resul = true;
        String METHOD_NAME = "InsertRutaInspeccion";
        String SOAP_ACTION = "http://192.168.10.20:8082/InsertRutaInspeccion";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("entidad");
        pi.setValue(objRutaInspeccion);
        pi.setType(RutaInspeccion.class);
        request.addProperty(pi);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE,"RutaInspeccion",new RutaInspeccion().getClass());
        AndroidHttpTransport transporte = new AndroidHttpTransport (URL);
        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            resul = Boolean.valueOf(response.toString());
        }
        catch (Exception e)
        {
            throw e;
        }
        return resul;
    }
}
