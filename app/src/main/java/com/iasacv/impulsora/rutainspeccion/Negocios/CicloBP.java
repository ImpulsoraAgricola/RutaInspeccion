package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.CicloDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;

import java.util.List;

/**
 * Created by Administrator on 26/06/2015.
 */
public class CicloBP {

    //Variables
    private CicloDA _objCicloDA;

    public CicloBP(Context context) {
        _objCicloDA = new CicloDA(context);
    }

    public Ciclo[] GetAllCiclos() {
        try {
            Ciclo[] listaCiclos = _objCicloDA.GetAllCiclos();
            return listaCiclos;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Ciclo> GetAllCiclosList() {
        try {
            List<Ciclo> listaCiclos = _objCicloDA.GetAllCiclosList();
            return listaCiclos;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Ciclo GetCiclo(Ciclo objFiltro) {
        try {
            Ciclo objCiclo = _objCicloDA.GetCiclo(objFiltro);
            return objCiclo;
        } catch (SQLException e) {
            throw e;
        }
    }
    public boolean InsertCiclo(Ciclo objCiclo) {
        try {
            boolean resul = _objCicloDA.InsertCiclo(objCiclo);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
