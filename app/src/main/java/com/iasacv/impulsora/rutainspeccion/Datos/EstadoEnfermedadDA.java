package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EstadoEnfermedadDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EstadoEnfermedadDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public Ciclo[] GetAllEstadoEnfermedad() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACESTEN WHERE ");
            Ciclo[] listaCiclos = new Ciclo[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                Ciclo objCiclo = new Ciclo();
                objCiclo.Clave = Integer.parseInt(objCursor.getString(0));
                objCiclo.Nombre = objCursor.getString(1);
                objCiclo.FechaInicio = objCursor.getString(2);
                objCiclo.FechaFin = objCursor.getString(3);
                objCiclo.NombreAbreviado = objCursor.getString(4);
                objCiclo.Estatus = objCursor.getString(5);
                objCiclo.Uso = objCursor.getString(6);
                listaCiclos[i] = objCiclo;
                i++;
            }
            return listaCiclos;
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


