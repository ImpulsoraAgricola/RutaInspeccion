package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Item;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;

import java.util.ArrayList;

/**
 * Created by Administrator on 01/07/2015.
 */
public class RutaInspeccionBP {

    //Variables
    private PlaneacionRutaDA _objPlaneacionRutaDA;

    public RutaInspeccionBP(Context context) {
        _objPlaneacionRutaDA = new PlaneacionRutaDA(context);
    }

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
            ArrayList<Item> listaPlaneacionRuta = _objPlaneacionRutaDA.GetAllPlaneacionRutaImage(usuarioClave,fecha);
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }
}
