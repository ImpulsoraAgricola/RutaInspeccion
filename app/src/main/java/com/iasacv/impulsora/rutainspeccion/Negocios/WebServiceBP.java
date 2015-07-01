package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;

import com.iasacv.impulsora.rutainspeccion.Datos.WebServiceDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;
import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Administrator on 26/06/2015.
 */
public class WebServiceBP {

    //Variables
    private WebServiceDA _objWebServiceDA;

    public WebServiceBP(Context context) {
        _objWebServiceDA = new WebServiceDA(context);
    }

    public boolean getCatalogos(String usuario) throws IOException, XmlPullParserException {
        try {
            boolean resul = _objWebServiceDA.getCatalogos(usuario);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    public Usuario getLogin(Usuario objUsuario) throws IOException, XmlPullParserException {
        try {
            Usuario resul = _objWebServiceDA.getLogin(objUsuario);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean insertRutaInspeccion(RutaInspeccion objRutaInspeccion) throws IOException, XmlPullParserException {
        try {
            boolean resul = _objWebServiceDA.insertRutaInspeccion(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }
}
