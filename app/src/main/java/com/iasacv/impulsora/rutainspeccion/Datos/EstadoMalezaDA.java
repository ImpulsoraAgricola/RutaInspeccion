package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
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

    public EstadoMPE[] GetAllEstadoMaleza() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACESTMA WHERE ESTMASTS!=\"A\"");
            EstadoMPE[] listaEstadoMaleza = new EstadoMPE[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                EstadoMPE objEstadoMPE = new EstadoMPE();
                objEstadoMPE.Clave = Integer.parseInt(objCursor.getString(0));
                objEstadoMPE.Nombre = objCursor.getString(1);
                objEstadoMPE.Estatus = objCursor.getString(2);
                objEstadoMPE.Uso = objCursor.getString(3);
                listaEstadoMaleza[i] = objEstadoMPE;
                i++;
            }
            return listaEstadoMaleza;
        } catch (SQLException e) {
            throw e;
        }
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

