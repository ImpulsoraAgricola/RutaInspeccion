package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Datos.CicloDA;
import com.iasacv.impulsora.rutainspeccion.Datos.EstadoEnfermedadDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;

import java.util.List;

/**
 * Created by Administrator on 26/06/2015.
 */
public class CatalogosBP {

    //Variables
    private CicloDA _objCicloDA;
    private EstadoEnfermedadDA _objEstadoEnfermedadDA;

    public CatalogosBP(Context context) {
        _objCicloDA = new CicloDA(context);
        _objEstadoEnfermedadDA = new EstadoEnfermedadDA(context);
    }

    //Ciclo
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

    //Estado enfermedad
    public EstadoMPE[] GetAllEstadoEnfermedad() {
        try {
            EstadoMPE[] listaEstadoEnfermedad = _objEstadoEnfermedadDA.get;
            return listaCiclos;
        } catch (SQLException e) {
            throw e;
        }
    }
}
