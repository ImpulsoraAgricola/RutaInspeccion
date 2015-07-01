package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoEnfermedadDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoEnfermedadDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertEstadoEnfermedad(EstadoMPE objEstadoEnfermedad) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACESTEN VALUES (" + objEstadoEnfermedad.Clave + "," +
                    "'" + objEstadoEnfermedad.Nombre + "'," +
                    "'" + objEstadoEnfermedad.Estatus + "'," +
                    "'" + objEstadoEnfermedad.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}


