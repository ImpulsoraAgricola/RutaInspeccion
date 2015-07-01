package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoArticuloDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoArticuloDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertTipoArticulo(TipoArticulo objTipoArticulo) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACTIART VALUES (" + objTipoArticulo.Clave + "," +
                    "'" + objTipoArticulo.Nombre + "'," +
                    "'" + objTipoArticulo.Estatus + "'," +
                    "'" + objTipoArticulo.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}
