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
public class EstadoPlagaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoPlagaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllEstadoPlagaCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ESTPLCVE,ESTPLNOM FROM BACESTPL WHERE ESTPLSTS=\"A\"");
            List<Combo> listEstadoPlaga = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listEstadoPlaga.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listEstadoPlaga;
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
