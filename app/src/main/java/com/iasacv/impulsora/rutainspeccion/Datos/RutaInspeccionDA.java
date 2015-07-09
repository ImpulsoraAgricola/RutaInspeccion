package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.Modelo.RutaInspeccion;

/**
 * Created by Administrator on 08/07/2015.
 */
public class RutaInspeccionDA {

    //Variables
    private EntLibDBTools objEntLibTools;

    public RutaInspeccionDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
    }

    public String ArmaFiltro(RutaInspeccion entidad) {
        String cadena = "";
        //--Clave del usuario
        if (entidad.UsuarioClave != 0) {
            cadena += "USUARCVE=" + entidad.UsuarioClave;
        }
        //--Clave del ciclo
        if (entidad.CicloClave != 0) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "CICLOCVE=" + entidad.CicloClave;
        }
        //--Fecha
        if (entidad.Fecha != null) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "PLANEFEC='" + entidad.Fecha+"'";
        }
        //--Folio
        if (entidad.Folio != 0) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "PLADEFOL=" + entidad.Folio;
        }
        return cadena;
    }

    public RutaInspeccion GetRutaInspeccion(RutaInspeccion objFiltro) {
        try {
            String filtro = ArmaFiltro(objFiltro);
            RutaInspeccion objRutaInspeccion = new RutaInspeccion();
            Cursor objCursor = objEntLibTools.executeCursor("SELECT * FROM BATRUINS " +
                    "WHERE " + filtro);
            while (objCursor.moveToNext()) {
                objRutaInspeccion.UsuarioClave = objCursor.getInt(0);
                objRutaInspeccion.CicloClave = objCursor.getInt(1);
                objRutaInspeccion.Fecha = objCursor.getString(2);
                objRutaInspeccion.Folio = objCursor.getInt(3);
            }
            objCursor.close();
            return objRutaInspeccion;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRutaInspeccionInicio(RutaInspeccion objRutaInspeccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BATRUINS (USUARCVE,CICLOCVE,PLANEFEC,PLADEFOL,RUINSFEI,RUINSINI,RUINSSTS,RUINSUSO) VALUES (" + objRutaInspeccion.UsuarioClave + "," +
                    + objRutaInspeccion.CicloClave + "," +
                    "'" + objRutaInspeccion.Fecha + "'," +
                    + objRutaInspeccion.Folio + "," +
                    "'"+ objRutaInspeccion.FechaInicio + "'," +
                    "'"+ objRutaInspeccion.HoraInicio + "'," +
                    "'" + objRutaInspeccion.Estatus + "'," +
                    "'" + objRutaInspeccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRutaInspeccion(RutaInspeccion objRutaInspeccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BATRUINS VALUES (" + objRutaInspeccion.UsuarioClave + "," +
                    + objRutaInspeccion.CicloClave + "," +
                    "'" + objRutaInspeccion.Fecha + "'," +
                    + objRutaInspeccion.Folio + "," +
                    "'"+ objRutaInspeccion.FechaInicio + "'," +
                    "'"+ objRutaInspeccion.HoraInicio + "'," +
                    "'"+ objRutaInspeccion.FechaFin + "'," +
                    "'"+ objRutaInspeccion.HoraFin + "'," +
                    "'"+ objRutaInspeccion.Tiempo + "'," +
                    "'"+ objRutaInspeccion.RecomendacionTecnica + "'," +
                    + objRutaInspeccion.SistemaProduccionClave + "," +
                    + objRutaInspeccion.ArregloTopologicoClave + "," +
                    "'"+ objRutaInspeccion.ProfundidadSiembra + "'," +
                    "'"+ objRutaInspeccion.ProfundidadSurco + "'," +
                    "'"+ objRutaInspeccion.ManejoAdecuado + "'," +
                    + objRutaInspeccion.EtapaFenologicaClave + "," +
                    "'"+ objRutaInspeccion.Exposicion + "'," +
                    + objRutaInspeccion.CondicionDesarrolLoClave + "," +
                    "'"+ objRutaInspeccion.OrdenCorrecto + "'," +
                    "'"+ objRutaInspeccion.RegulaPh + "'," +
                    "'"+ objRutaInspeccion.UsoAdecuado + "'," +
                    "'"+ objRutaInspeccion.HoraAplicacion + "'," +
                    "'"+ objRutaInspeccion.AguaCanal + "'," +
                    "'"+ objRutaInspeccion.Inundacion + "'," +
                    "'"+ objRutaInspeccion.BajaPoblacion + "'," +
                    "'"+ objRutaInspeccion.AplicacionNutrientes + "'," +
                    "'"+ objRutaInspeccion.AlteracionCiclo + "'," +
                    "'"+ objRutaInspeccion.AplicacionAgroquimicos + "'," +
                    "'"+ objRutaInspeccion.AltasTemperaturas + "'," +
                    "'"+ objRutaInspeccion.Fito + "'," +
                    "'"+ objRutaInspeccion.PlagasMalControladas + "'," +
                    + objRutaInspeccion.MalezaClave + "," +
                    + objRutaInspeccion.EstadoMalezaClave + "," +
                    + objRutaInspeccion.PlagaClave + "," +
                    + objRutaInspeccion.EstadoPlagaClave + "," +
                    + objRutaInspeccion.EnfermedadClave + "," +
                    + objRutaInspeccion.EstadoEnfermedadClave + "," +
                    + objRutaInspeccion.PotencialRendimientoClave + "," +
                    "'" + objRutaInspeccion.Estatus + "'," +
                    "'" + objRutaInspeccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRelacionRiego(RutaInspeccion objRutaInspeccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BARRIEGO VALUES (" + objRutaInspeccion.UsuarioClave + "," +
                    + objRutaInspeccion.CicloClave + "," +
                    "'" + objRutaInspeccion.Fecha + "'," +
                    + objRutaInspeccion.Folio + "," +
                    + objRutaInspeccion.TipoRiegoClave + "," +
                    + objRutaInspeccion.Capacidad + "," +
                    "'" + objRutaInspeccion.TipoRiegoOtro + "'," +
                    "'" + objRutaInspeccion.Estatus + "'," +
                    "'" + objRutaInspeccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertRelacionRecomendacion(RutaInspeccion objRutaInspeccion) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BARRECOM VALUES (" + objRutaInspeccion.UsuarioClave + "," +
                    + objRutaInspeccion.CicloClave + "," +
                    "'" + objRutaInspeccion.Fecha + "'," +
                    + objRutaInspeccion.Folio + "," +
                    + objRutaInspeccion.RecomendacionClave + "," +
                    "'" + objRutaInspeccion.RecomendacionOtro + "'," +
                    "'" + objRutaInspeccion.Estatus + "'," +
                    "'" + objRutaInspeccion.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }
}
