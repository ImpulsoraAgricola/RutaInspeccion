package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Enfermedad;
import com.iasacv.impulsora.rutainspeccion.Modelo.Maleza;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class MalezaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public MalezaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllMalezaCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT MALEZCVE,MALEZNOM FROM BPCMALEZ WHERE MALEZSTS=\"A\"");
            List<Combo> listMaleza = new ArrayList<Combo>();
            listMaleza.add(new Combo("-- Maleza (principal) --", 0));
            while (objCursor.moveToNext()) {
                listMaleza.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listMaleza;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertMaleza(Maleza objMaleza) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BPCMALEZ VALUES (" + objMaleza.Clave + "," +
                    "'" + objMaleza.Nombre + "'," +
                    "'" + objMaleza.Estatus + "'," +
                    "'" + objMaleza.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
