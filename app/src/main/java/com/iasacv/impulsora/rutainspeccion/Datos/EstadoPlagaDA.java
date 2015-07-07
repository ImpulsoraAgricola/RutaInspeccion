package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
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

    public EstadoMPE[] GetAllEstadoPlaga() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACESTPLA WHERE ESTPLSTS!=\"A\"");
            EstadoMPE[] listaEstadoPlaga = new EstadoMPE[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                EstadoMPE objEstadoMPE = new EstadoMPE();
                objEstadoMPE.Clave = Integer.parseInt(objCursor.getString(0));
                objEstadoMPE.Nombre = objCursor.getString(1);
                objEstadoMPE.Estatus = objCursor.getString(2);
                objEstadoMPE.Uso = objCursor.getString(3);
                listaEstadoPlaga[i] = objEstadoMPE;
                i++;
            }
            return listaEstadoPlaga;
        } catch (SQLException e) {
            throw e;
        }
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
