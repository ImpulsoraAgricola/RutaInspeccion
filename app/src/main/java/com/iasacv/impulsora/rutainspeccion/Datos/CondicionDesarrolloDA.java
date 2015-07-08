package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.CondicionDesarrollo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class CondicionDesarrolloDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public CondicionDesarrolloDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllCondicionDesarrolloCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT CONDICVE,CONDINOM FROM BACCONDI WHERE CONDISTS!=\"A\"");
            List<Combo> listCondicionDesarrollo = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listCondicionDesarrollo.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listCondicionDesarrollo;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertCondicionDesarrollo(CondicionDesarrollo objCondicionDesarrollo) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACCONDI VALUES (" + objCondicionDesarrollo.Clave + "," +
                    "'" + objCondicionDesarrollo.Nombre + "'," +
                    "'" + objCondicionDesarrollo.Estatus + "'," +
                    "'" + objCondicionDesarrollo.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}

