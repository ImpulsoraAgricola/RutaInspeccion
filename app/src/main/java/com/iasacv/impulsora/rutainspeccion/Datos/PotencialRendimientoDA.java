package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;

/**
 * Created by Administrator on 01/07/2015.
 */
public class PotencialRendimientoDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public PotencialRendimientoDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public boolean InsertPotencialRendimiento(PotencialRendimiento objPotencialRendimiento) {
        boolean resul = true;
        try {
            String query = "INSERT INTO BACPOTRE VALUES (" + objPotencialRendimiento.Clave + "," +
                    "'" + objPotencialRendimiento.Nombre + "'," +
                    "'" + objPotencialRendimiento.Estatus + "'," +
                    "'" + objPotencialRendimiento.Uso + "')";
            objEntLibTools.insert(query);
        } catch (SQLException e) {
            resul = false;
            throw e;
        }
        return resul;
    }

}
