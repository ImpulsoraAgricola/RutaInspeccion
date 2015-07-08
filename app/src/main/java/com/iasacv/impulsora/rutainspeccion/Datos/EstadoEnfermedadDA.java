package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoEnfermedadDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoEnfermedadDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllEstadoEnfermedadCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ESTENCVE,ESTENNOM FROM BACESTEN WHERE ESTENSTS=\"A\"");
            List<Combo> listEstadoEnfermedad = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listEstadoEnfermedad.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listEstadoEnfermedad;
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


