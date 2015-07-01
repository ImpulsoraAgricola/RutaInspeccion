package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoMalezaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoMalezaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertEstadoMaleza(EstadoMPE objEstadoMaleza) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACESTMA VALUES (" + objEstadoMaleza.Clave + "," +
                    "'" + objEstadoMaleza.Nombre + "'," +
                    "'" + objEstadoMaleza.Estatus + "'," +
                    "'" + objEstadoMaleza.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}

