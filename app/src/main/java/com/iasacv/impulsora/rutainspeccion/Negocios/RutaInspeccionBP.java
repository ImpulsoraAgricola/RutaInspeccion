package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.CicloDA;
import com.iasacv.impulsora.rutainspeccion.Datos.PlaneacionRutaDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.ImageItem;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class RutaInspeccionBP {

    //Variables
    private PlaneacionRutaDA _objPlaneacionRutaDA;

    public RutaInspeccionBP(Context context) {
        _objPlaneacionRutaDA = new PlaneacionRutaDA(context);
    }

    public ArrayList<ImageItem> GetAllPlaneacionRutaImage() {
        try {
            ArrayList<ImageItem> listaPlaneacionRuta = _objPlaneacionRutaDA.GetAllPlaneacionRutaImage();
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }
}
