package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
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

    public TipoInspeccion[] GetAllTipoInspeccion() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACTIINS WHERE TIINSSTS!=\"A\"");
            TipoInspeccion[] listaTipoInspeccion = new TipoInspeccion[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                TipoInspeccion objTipoInspeccion = new TipoInspeccion();
                objTipoInspeccion.Clave = Integer.parseInt(objCursor.getString(0));
                objTipoInspeccion.Nombre = objCursor.getString(1);
                objTipoInspeccion.Estatus = objCursor.getString(2);
                objTipoInspeccion.Uso = objCursor.getString(3);
                listaTipoInspeccion[i] = objTipoInspeccion;
                i++;
            }
            return listaTipoInspeccion;
        } catch (SQLException e) {
            throw e;
        }
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
