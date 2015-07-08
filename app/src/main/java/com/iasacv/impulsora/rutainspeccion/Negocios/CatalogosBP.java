package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;
import android.widget.Spinner;

import com.iasacv.impulsora.rutainspeccion.Datos.ArregloTopologicoDA;
import com.iasacv.impulsora.rutainspeccion.Datos.CicloDA;
import com.iasacv.impulsora.rutainspeccion.Datos.EstadoEnfermedadDA;
import com.iasacv.impulsora.rutainspeccion.Datos.EstadoMalezaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.EstadoPlagaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.EtapaFenologicaDA;
import com.iasacv.impulsora.rutainspeccion.Datos.PotencialRendimientoDA;
import com.iasacv.impulsora.rutainspeccion.Datos.TipoArticuloDA;
import com.iasacv.impulsora.rutainspeccion.Datos.TipoInspeccionDA;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Combo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.EtapaFenologica;
import com.iasacv.impulsora.rutainspeccion.Modelo.PotencialRendimiento;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoArticulo;
import com.iasacv.impulsora.rutainspeccion.Modelo.TipoInspeccion;

import java.util.List;

/**
 * Created by Administrator on 26/06/2015.
 */
public class CatalogosBP {

    //Variables
    private CicloDA _objCicloDA;
    private EstadoEnfermedadDA _objEstadoEnfermedadDA;
    private EstadoMalezaDA _objEstadoMalezaDA;
    private EstadoPlagaDA _objEstadoPlagaDA;
    private EtapaFenologicaDA _objEtapaFenologicaDA;
    private PotencialRendimientoDA _objPotencialRendimientoDA;
    private TipoArticuloDA _objTipoArticuloDA;
    private TipoInspeccionDA _objTipoInspeccionDA;
    private ArregloTopologicoDA _objArregloTopologicoDA;

    public CatalogosBP(Context context) {
        _objCicloDA = new CicloDA(context);
        _objEstadoEnfermedadDA = new EstadoEnfermedadDA(context);
        _objEstadoMalezaDA = new EstadoMalezaDA(context);
        _objEstadoPlagaDA = new EstadoPlagaDA(context);
        _objEtapaFenologicaDA = new EtapaFenologicaDA(context);
        _objPotencialRendimientoDA = new PotencialRendimientoDA(context);
        _objTipoArticuloDA = new TipoArticuloDA(context);
        _objTipoInspeccionDA = new TipoInspeccionDA(context);
        _objArregloTopologicoDA = new ArregloTopologicoDA(context);
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
    public List<Combo> GetAllCicloCombo() {
        try {
            List<Combo> listaCiclos = _objCicloDA.GetAllCicloCombo();
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
    public List<Combo> GetAllEstadoEnfermedadCombo() {
        try {
            List<Combo> listEstadoEnfermedad = _objEstadoEnfermedadDA.GetAllEstadoEnfermedadCombo();
            return listEstadoEnfermedad;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Estado maleza
    public List<Combo> GetAllEstadoMalezaCombo() {
        try {
            List<Combo> listEstadoMaleza = _objEstadoMalezaDA.GetAllEstadoMalezaCombo();
            return listEstadoMaleza;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Estado plaga
    public List<Combo> GetAllEstadoPlagaCombo() {
        try {
            List<Combo> listEstadoPlaga = _objEstadoPlagaDA.GetAllEstadoPlagaCombo();
            return listEstadoPlaga;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Etapa fenologica
    public List<Combo> GetAllEtapaFenologicaCombo() {
        try {
            List<Combo> listEtapaFenologica = _objEtapaFenologicaDA.GetAllEtapaFenologicaCombo();
            return listEtapaFenologica;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Potencial de rendimiento
    public List<Combo> GetAllPotencialRendimientoCombo() {
        try {
            List<Combo> listPotencialRendimiento = _objPotencialRendimientoDA.GetAllPotencialRendimientoCombo();
            return listPotencialRendimiento;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Tipo de articulo
    public List<Combo> GetAllTipoArticuloCombo() {
        try {
            List<Combo> listTipoArticulo = _objTipoArticuloDA.GetAllTipoArticuloCombo();
            return listTipoArticulo;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Tipo de inspeccion
    public List<Combo> GetAllTipoInspeccionCombo() {
        try {
            List<Combo> listTipoInspeccion = _objTipoInspeccionDA.GetAllTipoInspeccionCombo();
            return listTipoInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Arreglo topologico
    public List<Combo> GetAllArregloTopologicoList() {
        try {
            List<Combo> listaGetAllArregloTopologico = _objArregloTopologicoDA.GetAllArregloTopologicoCombo();
            return listaGetAllArregloTopologico;
        } catch (SQLException e) {
            throw e;
        }
    }
}
