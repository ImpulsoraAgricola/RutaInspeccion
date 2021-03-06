package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.RutaInspeccionDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Item;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class RutaInspeccionBP {

    //Variables
    private PlaneacionRutaDA _objPlaneacionRutaDA;
    private RutaInspeccionDA _objRutaInspeccionDA;

    public RutaInspeccionBP(Context context) {
        _objPlaneacionRutaDA = new PlaneacionRutaDA(context);
        _objRutaInspeccionDA = new RutaInspeccionDA(context);
    }

    //Planeacion ruta de inspeccion
    public PlaneacionRuta GetPlaneacionRuta(PlaneacionRuta objFiltro) {
        try {
            PlaneacionRuta objPlaneacionRuta = _objPlaneacionRutaDA.GetPlaneacionRuta(objFiltro);
            return objPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }

    public ArrayList<Item> GetAllPlaneacionRutaImage(int usuarioClave, String fecha) {
        try {
            ArrayList<Item> listaPlaneacionRuta = _objPlaneacionRutaDA.GetAllPlaneacionRutaImage(usuarioClave, fecha);
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean UpdatePlaneacionRutaEstatus(PlaneacionRuta objPlaneacionRuta) throws Exception {
        try {
            boolean resul = _objPlaneacionRutaDA.UpdatePlaneacionRutaEstatus(objPlaneacionRuta);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    //Ruta de inspeccion
    public RutaInspeccion GetRutaInspeccionCabecero(RutaInspeccion objFiltro) {
        try {
            RutaInspeccion objRutaInspeccion = _objRutaInspeccionDA.GetRutaInspeccionCabecero(objFiltro);
            return objRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public RutaInspeccion GetRutaInspeccion(RutaInspeccion objFiltro) {
        try {
            RutaInspeccion objRutaInspeccion = _objRutaInspeccionDA.GetRutaInspeccion(objFiltro);
            return objRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<RutaInspeccion> GetAllRutaInspeccion() {
        try {
            List<RutaInspeccion> listRutaInspeccion = _objRutaInspeccionDA.GetAllRutaInspeccion();
            return listRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRutaInspeccion(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.InsertRutaInspeccionInicio(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean UpdateRutaInspeccion(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.UpdateRutaInspeccion(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    //Relacion riego
    public RutaInspeccion GetRelacionRiego(RutaInspeccion objFiltro) {
        try {
            RutaInspeccion objRutaInspeccion = _objRutaInspeccionDA.GetRelacionRiego(objFiltro);
            return objRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public RutaInspeccion[] GetAllRelacionRiego(RutaInspeccion objRutaInspeccion) {
        try {
            RutaInspeccion[] listaRutaInspeccion = _objRutaInspeccionDA.GetAllRelacionRiego(objRutaInspeccion);
            return listaRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRelacionRiego(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.InsertRelacionRiego(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean DeleteRelacionRiego(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.DeleteRelacionRiego(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    //Relacion recomendacion
    public RutaInspeccion GetRelacionRecomendacion(RutaInspeccion objFiltro) {
        try {
            RutaInspeccion objRutaInspeccion = _objRutaInspeccionDA.GetRelacionRecomendacion(objFiltro);
            return objRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public RutaInspeccion[] GetAllRelacionRecomendacion(RutaInspeccion objRutaInspeccion) {
        try {
            RutaInspeccion[] listaRutaInspeccion = _objRutaInspeccionDA.GetAllRelacionRecomendacion(objRutaInspeccion);
            return listaRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRelacionRecomendacion(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.InsertRelacionRecomendacion(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean DeleteRelacionRecomendacion(RutaInspeccion objRutaInspeccion) throws Exception {
        try {
            boolean resul = _objRutaInspeccionDA.DeleteRelacionRecomendacion(objRutaInspeccion);
            return resul;
        } catch (Exception e) {
            throw e;
        }
    }
}
