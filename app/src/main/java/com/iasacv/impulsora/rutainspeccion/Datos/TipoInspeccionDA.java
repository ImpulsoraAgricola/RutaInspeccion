package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoInspeccion;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoInspeccionDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoInspeccionDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertTipoInspeccion(TipoInspeccion objTipoInspeccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACTIINS VALUES (" + objTipoInspeccion.Clave + "," +
                    "'" + objTipoInspeccion.Nombre + "'," +
                    "'" + objTipoInspeccion.Estatus + "'," +
                    "'" + objTipoInspeccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
