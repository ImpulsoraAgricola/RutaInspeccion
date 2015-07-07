package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.content.Context;
import android.database.SQLException;
import android.widget.Spinner;

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

    public CatalogosBP(Context context) {
        _objCicloDA = new CicloDA(context);
        _objEstadoEnfermedadDA = new EstadoEnfermedadDA(context);
        _objEstadoMalezaDA = new EstadoMalezaDA(context);
        _objEstadoPlagaDA = new EstadoPlagaDA(context);
        _objEtapaFenologicaDA = new EtapaFenologicaDA(context);
        _objPotencialRendimientoDA = new PotencialRendimientoDA(context);
        _objTipoArticuloDA = new TipoArticuloDA(context);
        _objTipoInspeccionDA = new TipoInspeccionDA(context);
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
    public List<Combo> GetAllCiclosList() {
        try {
            List<Combo> listaCiclos = _objCicloDA.GetAllCiclosList();
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
            EstadoMPE[] listaEstadoEnfermedad = _objEstadoEnfermedadDA.GetAllEstadoEnfermedad();
            return listaEstadoEnfermedad;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Estado maleza
    public EstadoMPE[] GetAllEstadoMaleza() {
        try {
            EstadoMPE[] listaEstadoMaleza = _objEstadoMalezaDA.GetAllEstadoMaleza();
            return listaEstadoMaleza;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Estado plaga
    public EstadoMPE[] GetAllEstadoPlaga() {
        try {
            EstadoMPE[] listaEstadoPlaga = _objEstadoPlagaDA.GetAllEstadoPlaga();
            return listaEstadoPlaga;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Etapa fenologica
    public List<Combo> GetAllEtapaFenologica() {
        try {
            List<Combo> listaEtapaFenologica = _objEtapaFenologicaDA.GetAllEtapaFenologica();
            return listaEtapaFenologica;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Potencial de rendimiento
    public PotencialRendimiento[] GetAllPotencialRendimiento() {
        try {
            PotencialRendimiento[]listaPotencialRendimiento = _objPotencialRendimientoDA.GetAllPotencialRendimiento();
            return listaPotencialRendimiento;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Tipo de articulo
    public TipoArticulo[] GetAllTipoArticulo() {
        try {
            TipoArticulo[]listaTipoArticulo = _objTipoArticuloDA.GetAllTipoArticulo();
            return listaTipoArticulo;
        } catch (SQLException e) {
            throw e;
        }
    }

    //Tipo de inspeccion
    public TipoInspeccion[] GetAllTipoInspeccion() {
        try {
            TipoInspeccion[]listaTipoInspeccion = _objTipoInspeccionDA.GetAllTipoInspeccion();
            return listaTipoInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }
}
