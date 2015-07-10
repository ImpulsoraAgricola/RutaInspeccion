package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoMalezaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoMalezaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllEstadoMalezaCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ESTMACVE,ESTMANOM FROM BACESTMA WHERE ESTMASTS=\"A\"");
            List<Combo> listEstadoMaleza = new ArrayList<Combo>();
            listEstadoMaleza.add(new Combo("-- Grado de insfestaci\u00F3n --", 0));
            while (objCursor.moveToNext()) {
                listEstadoMaleza.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listEstadoMaleza;
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

