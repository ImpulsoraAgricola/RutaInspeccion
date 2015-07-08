package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoArticuloDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public TipoArticuloDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllTipoArticuloCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT TIARTCVE,TIARTNOM FROM BACTIART WHERE TIARTSTS!=\"A\"");
            List<Combo> listTipoArticulo = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listTipoArticulo.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listTipoArticulo;
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
