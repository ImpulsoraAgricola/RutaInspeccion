package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Spinner;

import com.iasacv.impulsora.rutainspeccion.Administrador;
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24/06/2015.
 */
public class CicloDA{

    //Variables
    private EntLibDBTools objEntLibTools;

    public CicloDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public String ArmaFiltro(Ciclo entidad) {
        String cadena = "";
        //--Clave del ciclo
        if (entidad.Clave != 0) {
            cadena = "CICLOCVE=" + entidad.Clave;
        }
        return cadena;
    }

    public Ciclo[] GetAllCiclos() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM IGMCICLO");
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

    public List<Combo> GetAllCicloCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT CICLOCVE,CICLONOM FROM IGMCICLO");
            List<Combo> listCiclo = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listCiclo.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listCiclo;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Ciclo GetCiclo(Ciclo objFiltro) {
        try {
            String filtro = ArmaFiltro(objFiltro);
            Ciclo objCiclo = new Ciclo();
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM IGMCICLO WHERE " + filtro);
            while (objCursor.moveToNext()) {
                objCiclo.Clave = Integer.parseInt(objCursor.getString(0));
                objCiclo.Nombre = objCursor.getString(1);
                objCiclo.FechaInicio = objCursor.getString(2);
                objCiclo.FechaFin = objCursor.getString(3);
                objCiclo.NombreAbreviado = objCursor.getString(4);
                objCiclo.Estatus = objCursor.getString(5);
                objCiclo.Uso = objCursor.getString(6);
            }
            objCursor.close();
            return objCiclo;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertCiclo(Ciclo objCiclo) {
        try {
            boolean resul = true;
            String query = "INSERT INTO IGMCICLO VALUES (" + objCiclo.Clave + "," +
                    "'" + objCiclo.Nombre + "'," +
                    "'" + objCiclo.FechaInicio + "'," +
                    "'" + objCiclo.FechaFin + "'," +
                    "'" + objCiclo.NombreAbreviado + "'," +
                    "'" + objCiclo.Estatus + "'," +
                    "'" + objCiclo.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
