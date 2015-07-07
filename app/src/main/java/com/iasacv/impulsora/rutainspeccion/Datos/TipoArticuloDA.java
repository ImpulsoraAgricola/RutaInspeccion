package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoArticuloDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoArticuloDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public TipoArticulo[] GetAllTipoArticulo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BACTIART WHERE TIARTSTS!=\"A\"");
            TipoArticulo[] listaTipoArticulo = new TipoArticulo[objCursor.getCount()];
            int i = 0;
            while (objCursor.moveToNext()) {
                TipoArticulo objTipoArticulo = new TipoArticulo();
                objTipoArticulo.Clave = Integer.parseInt(objCursor.getString(0));
                objTipoArticulo.Nombre = objCursor.getString(1);
                objTipoArticulo.Estatus = objCursor.getString(2);
                objTipoArticulo.Uso = objCursor.getString(3);
                listaTipoArticulo[i] = objTipoArticulo;
                i++;
            }
            return listaTipoArticulo;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertTipoArticulo(TipoArticulo objTipoArticulo) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACTIART VALUES (" + objTipoArticulo.Clave + "," +
                    "'" + objTipoArticulo.Nombre + "'," +
                    "'" + objTipoArticulo.Estatus + "'," +
                    "'" + objTipoArticulo.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}
