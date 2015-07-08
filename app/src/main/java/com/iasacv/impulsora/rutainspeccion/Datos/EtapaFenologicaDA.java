package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.EtapaFenologica;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EtapaFenologicaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EtapaFenologicaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public List<Combo> GetAllEtapaFenologicaCombo() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT ETAFECVE,ETAFENOM FROM BACETAFE WHERE ETAFESTS=\"A\"");
            List<Combo> listEtapaFenologica = new ArrayList<Combo>();
            while (objCursor.moveToNext()) {
                listEtapaFenologica.add(new Combo(objCursor.getString(1), Integer.parseInt(objCursor.getString(0))));
            }
            return listEtapaFenologica;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertEtapaFenologica(EtapaFenologica objEtapaFenologica) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BACETAFE VALUES (" + objEtapaFenologica.Clave + "," +
                    "'" + objEtapaFenologica.Nombre + "'," +
                    "'" + objEtapaFenologica.Estatus + "'," +
                    "'" + objEtapaFenologica.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}
