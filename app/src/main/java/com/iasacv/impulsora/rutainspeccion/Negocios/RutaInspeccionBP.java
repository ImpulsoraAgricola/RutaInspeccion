package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Item;

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

    public ArrayList<Item> GetAllPlaneacionRutaImage() {
        try {
            ArrayList<Item> listaPlaneacionRuta = _objPlaneacionRutaDA.GetAllPlaneacionRutaImage();
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }
}
