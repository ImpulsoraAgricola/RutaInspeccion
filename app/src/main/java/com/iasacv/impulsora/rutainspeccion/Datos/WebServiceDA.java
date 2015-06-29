package com.iasacv.impulsora.rutainspeccion.Datos;

import android.app.Activity;
import android.content.Context;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
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
    private EntLibDBTools objEntLibTools;
    private CicloDA objCicloDA;

    public WebServiceDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
        objCicloDA = new CicloDA(context);
    }

    //Variable para Web Services
    String NAMESPACE = "http://192.168.10.98:8080/";
    String URL = "http://192.168.10.98:8080/Service.asmx?wsdl";

    public boolean getCatalogos() throws IOException, XmlPullParserException {
        //Variables
        boolean resul = true;
        Usuario[] listaUsuarios;
        Ciclo[] listaCiclos;

        //Se elimina la version anterior de las tablas
        objEntLibTools.dropTable();

        //Actualizar tabla de usuarios
        String METHOD_NAME = "GetAllCiclos";
        String SOAP_ACTION = "http://192.168.10.98:8080/GetAllCiclos";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
                resul = objCicloDA.InsertCiclo(objCiclo);
            }
        } catch (Exception e) {
            throw e;
        }
        return resul;
    }

    public Usuario getLogin(Usuario objUsuario) throws IOException, XmlPullParserException {
        //Variables
        boolean resul = true;
        Usuario _objUsuario = new Usuario();

        String METHOD_NAME = "GetLogin";
        String SOAP_ACTION = "http://192.168.10.98:8080/GetLogin";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("entidad");
        pi.setValue(objUsuario);
        pi.setType(RutaInspeccion.class);
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
        String SOAP_ACTION = "http://192.168.10.98:8080/InsertRutaInspeccion";
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
