package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
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

    public EstadoMPE[] GetAllEstadoEnfermedad() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACESTEN WHERE ESTENSTS!=\"A\"");
            EstadoMPE[] listaEstadoEnfermedad = new EstadoMPE[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                EstadoMPE objEstadoMPE = new EstadoMPE();
                objEstadoMPE.Clave = Integer.parseInt(objCursor.getString(0));
                objEstadoMPE.Nombre = objCursor.getString(1);
                objEstadoMPE.Estatus = objCursor.getString(2);
                objEstadoMPE.Uso = objCursor.getString(3);
                listaEstadoEnfermedad[i] = objEstadoMPE;
                i++;
            }
            return listaEstadoEnfermedad;
        } catch (SQLException e) {
            throw e;
        }
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


