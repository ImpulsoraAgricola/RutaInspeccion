package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoInspeccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoInspeccionDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoInspeccionDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllTipoInspeccionCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT TIINSCVE,TIINSNOM FROM BACTIINS WHERE TIINSSTS!=\"A\"");
            List<Combo> listTipoInspeccion = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listTipoInspeccion.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listTipoInspeccion;
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
