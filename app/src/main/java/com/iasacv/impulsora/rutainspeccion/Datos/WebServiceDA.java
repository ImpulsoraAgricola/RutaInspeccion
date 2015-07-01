package com.iasacv.impulsora.rutainspeccion.Datos;

import android.app.Activity;
import android.content.Context;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.EtapaFenologica;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoInspeccion;
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
        return resul;
    }

    public boolean getPlaneacionRuta(String usuario) throws IOException, XmlPullParserException {
        //Variables
        boolean resul = true;

        //Se elimina la version anterior de las tablas
        _objEntLibTools.dropTable();

        //Actualizar tabla de usuarios
        Ciclo[] listaCiclos;
        String METHOD_NAME = "GetAllPlaneacionRuta";
        String SOAP_ACTION = NAMESPACE + "GetAllPlaneacionRuta";
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
                SoapObject ic = (SoapObject) resSoap.getProperty(i);
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
