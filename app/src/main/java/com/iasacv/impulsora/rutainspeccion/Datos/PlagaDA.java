package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.ArregloTopologico;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Plaga;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 07/07/2015.
 */
public class PlagaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public PlagaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllPlagaCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT PLAGACVE,PLAGANOM FROM BLCPLAGA WHERE PLAGASTS=\"A\"");
            List<Combo> listPlaga = new ArrayList<Combo>();
            listPlaga.add(new Combo("-- Insectos (principal) --", 0));
            while (objCursor.moveToNext()) {
                listPlaga.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            listPlaga.add(new Combo("Ninguno", -1));
            objCursor.close();
            return listPlaga;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertPlaga(Plaga objPlaga) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BLCPLAGA VALUES (" + objPlaga.Clave + "," +
                    "'" + objPlaga.Nombre + "'," +
                    "'" + objPlaga.Estatus + "'," +
                    "'" + objPlaga.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
