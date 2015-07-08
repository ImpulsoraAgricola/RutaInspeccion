package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.CondicionDesarrollo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Recomendacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class RecomendacionDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public RecomendacionDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllRecomendacionCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT RECOMCVE,RECOMNOM FROM BACRECOM WHERE RECOMSTS!=\"A\"");
            List<Combo> listRecomendacion = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listRecomendacion.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listRecomendacion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRecomendacion(Recomendacion objRecomendacion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACRECOM VALUES (" + objRecomendacion.Clave + "," +
                    "'" + objRecomendacion.Nombre + "'," +
                    "'" + objRecomendacion.Estatus + "'," +
                    "'" + objRecomendacion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
