package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoRiego;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class TipoRiegoDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoRiegoDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllTipoRiegoCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT TIRIECVE,TIRIENOM FROM BACTIRIE WHERE TIRIESTS!=\"A\"");
            List<Combo> listTipoRiego = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listTipoRiego.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listTipoRiego;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertTipoRiego(TipoRiego objTipoRiego) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACTIRIE VALUES (" + objTipoRiego.Clave + "," +
                    "'" + objTipoRiego.Nombre + "'," +
                    "'" + objTipoRiego.Estatus + "'," +
                    "'" + objTipoRiego.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}

