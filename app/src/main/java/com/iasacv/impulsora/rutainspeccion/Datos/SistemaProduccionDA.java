package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.SistemaProduccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class SistemaProduccionDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public SistemaProduccionDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllSistemaProduccionCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT SIPROCVE,SIPRONOM FROM BACSIPRO WHERE SIPROSTS =\"A\"");
            List<Combo> listSistemaProduccion = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listSistemaProduccion.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listSistemaProduccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertSistemaProduccion(SistemaProduccion objSistemaProduccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACSIPRO VALUES (" + objSistemaProduccion.Clave + "," +
                    "'" + objSistemaProduccion.Nombre + "'," +
                    "'" + objSistemaProduccion.Estatus + "'," +
                    "'" + objSistemaProduccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
