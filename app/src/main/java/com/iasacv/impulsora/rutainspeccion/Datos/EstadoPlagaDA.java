package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoPlagaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoPlagaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertEstadoPlaga(EstadoMPE objEstadoPlaga) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACESTPL VALUES (" + objEstadoPlaga.Clave + "," +
                    "'" + objEstadoPlaga.Nombre + "'," +
                    "'" + objEstadoPlaga.Estatus + "'," +
                    "'" + objEstadoPlaga.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}
