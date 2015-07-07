package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;

/**
 * Created by Administrator on 01/07/2015.
 */
public class PotencialRendimientoDA {
    //Variables
    private EntLibDBTools objEntLibTools;

    public PotencialRendimientoDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public PotencialRendimiento[] GetAllPotencialRendimiento() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACPOTRE WHERE POTRESTS!=\"A\"");
            PotencialRendimiento[] listaPotencialRendimiento = new PotencialRendimiento[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                PotencialRendimiento objPotencialRendimiento = new PotencialRendimiento();
                objPotencialRendimiento.Clave = Integer.parseInt(objCursor.getString(0));
                objPotencialRendimiento.Nombre = objCursor.getString(1);
                objPotencialRendimiento.Estatus = objCursor.getString(2);
                objPotencialRendimiento.Uso = objCursor.getString(3);
                listaPotencialRendimiento[i] = objPotencialRendimiento;
                i++;
            }
            return listaPotencialRendimiento;
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
