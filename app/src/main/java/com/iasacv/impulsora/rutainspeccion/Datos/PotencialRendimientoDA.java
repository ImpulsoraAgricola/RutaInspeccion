package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class PotencialRendimientoDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public PotencialRendimientoDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllPotencialRendimientoCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT POTRECVE,POTRENOM FROM BACPOTRE WHERE POTRESTS=\"A\"");
            List<Combo> listPotencialRendimiento = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listPotencialRendimiento.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listPotencialRendimiento;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertPotencialRendimiento(PotencialRendimiento objPotencialRendimiento) {
        boolean resul = true;
        try {
            String query = "INSERT INTO BACPOTRE VALUES (" + objPotencialRendimiento.Clave + "," +
                    "'" + objPotencialRendimiento.Nombre + "'," +
                    "'" + objPotencialRendimiento.Estatus + "'," +
                    "'" + objPotencialRendimiento.Uso + "')";
            objEntLibTools.insert(query);
        } catch (SQLException e) {
            resul = false;
            throw e;
        }
        return resul;
    }

}
