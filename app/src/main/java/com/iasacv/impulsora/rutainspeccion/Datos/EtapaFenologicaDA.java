package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.EtapaFenologica;

/**
 * Created by Administrator on 01/07/2015.
 */
public class EtapaFenologicaDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public EtapaFenologicaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
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
