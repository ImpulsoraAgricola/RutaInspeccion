package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Enfermedad;
import com.iasacv.impulsora.rutainspeccion.Modelo.Plaga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class EnfermedadDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EnfermedadDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllEnfermedadCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ENFERCVE,ENFERNOM FROM BECENFER WHERE ENFERSTS=\"A\"");
            List<Combo> listEnfermedad = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listEnfermedad.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listEnfermedad;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertEnfermedad(Enfermedad objEnfermedad) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BECENFER VALUES (" + objEnfermedad.Clave + "," +
                    "'" + objEnfermedad.Nombre + "'," +
                    "'" + objEnfermedad.Estatus + "'," +
                    "'" + objEnfermedad.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
