package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class ArregloTopologicoDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public ArregloTopologicoDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllArregloTopologicoCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ARTOPCVE,ARTOPNOM FROM BACARTOP WHERE ARTOPSTS=\"A\"");
            List<Combo> listArregloTopologico = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listArregloTopologico.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listArregloTopologico;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertArregloTopologico(ArregloTopologico objArregloTopologico) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACARTOP VALUES (" + objArregloTopologico.Clave + "," +
                    "'" + objArregloTopologico.Nombre + "'," +
                    "'" + objArregloTopologico.Estatus + "'," +
                    "'" + objArregloTopologico.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
