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
            cadena += "A.USUARCVE=" + entidad.UsuarioClave;
        }
        //--Clave del ciclo
        if (entidad.CicloClave != 0) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "A.CICLOCVE=" + entidad.CicloClave;
        }
        //--Fecha
        if (entidad.Fecha != null) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "A.PLANEFEC='" + entidad.Fecha+"'";
        }
        //--Folio
        if (entidad.Folio != 0) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "A.PLADEFOL=" + entidad.Folio;
        }
        return cadena;
    }

    public RutaInspeccion GetRutaInspeccionCabecero(RutaInspeccion objFiltro) {
        try {
            String filtro = ArmaFiltro(objFiltro);
            RutaInspeccion objRutaInspeccion = new RutaInspeccion();
            Cursor objCursor = objEntLibTools.executeCursor("SELECT USUARCVE,CICLOCVE,PLANEFEC,PLADEFOL  " +
                    "FROM BATRUINS A " +
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

    public RutaInspeccion GetRutaInspeccion(RutaInspeccion objFiltro) {
        try {
            String filtro = ArmaFiltro(objFiltro);
            RutaInspeccion objRutaInspeccion = new RutaInspeccion();
            Cursor objCursor = objEntLibTools.executeCursor("SELECT USUARCVE,CICLOCVE,PLANEFEC,PLADEFOL," +
                    "RUINSREC,A.SIPROCVE,SIPRONOM,A.ARTOPCVE,ARTOPNOM,RUINSSIA,RUINSSUA,RUINSMAN,A.ETAFECVE,ETAFENOM," +
                    "RUINSEXP,A.CONDICVE,CONDINOM,RUINSORD,RUINSREG,RUINSUSA,RUINSHOR,RUINSAGU,RUINSINU,RUINSPOB,RUINSPRO,RUINSALT," +
                    "RUINSAPL,RUINSTEM,RUINSFIT,RUINSPLA,A.MALEZCVE,MALEZNOM,A.ESTMACVE,ESTMANOM,A.PLAGACVE,PLAGANOM,A.ESTPLCVE," +
                    "ESTPLNOM,A.ENFERCVE,ENFERNOM,A.ESTENCVE,ESTENNOM,A.POTRECVE,POTRENOM,RUINSSTS,RUINSUSO\n" +
                    "FROM BATRUINS A LEFT JOIN BACSIPRO B ON (A.SIPROCVE=B.SIPROCVE)\n" +
                    "LEFT JOIN BACARTOP C ON (A.ARTOPCVE=C.ARTOPCVE)\n" +
                    "LEFT JOIN BACETAFE D ON (A.ETAFECVE=D.ETAFECVE)\n" +
                    "LEFT JOIN BACCONDI E ON (A.CONDICVE=E.CONDICVE)\n" +
                    "LEFT JOIN BPCMALEZ F ON (A.MALEZCVE=F.MALEZCVE)\n" +
                    "LEFT JOIN BACESTMA G ON (A.ESTMACVE=G.ESTMACVE)\n" +
                    "LEFT JOIN BLCPLAGA H ON (A.PLAGACVE=H.PLAGACVE)\n" +
                    "LEFT JOIN BACESTPL I ON (A.ESTPLCVE=I.ESTPLCVE)\n" +
                    "LEFT JOIN BECENFER J ON (A.ENFERCVE=J.ENFERCVE)\n" +
                    "LEFT JOIN BACESTEN K ON (A.ESTENCVE=K.ESTENCVE)\n" +
                    "LEFT JOIN BACPOTRE L ON (A.POTRECVE=L.POTRECVE) " +
                    "WHERE " + filtro);
            while (objCursor.moveToNext()) {
                objRutaInspeccion.UsuarioClave = objCursor.getInt(0);
                objRutaInspeccion.CicloClave = objCursor.getInt(1);
                objRutaInspeccion.Fecha = objCursor.getString(2);
                objRutaInspeccion.Folio = objCursor.getInt(3);
                objRutaInspeccion.RecomendacionTecnica = objCursor.getString(4).charAt(0);
                objRutaInspeccion.SistemaProduccionClave = objCursor.getInt(5);
                objRutaInspeccion.SistemaProduccionNombre = objCursor.getString(6);
                objRutaInspeccion.ArregloTopologicoClave = objCursor.getInt(7);
                objRutaInspeccion.ArregloTopologicoNombre = objCursor.getString(8);
                objRutaInspeccion.ProfundidadSiembra = objCursor.getString(9).charAt(0);
                objRutaInspeccion.ProfundidadSurco = objCursor.getString(10).charAt(0);
                objRutaInspeccion.ManejoAdecuado = objCursor.getString(11).charAt(0);
                objRutaInspeccion.EtapaFenologicaClave = objCursor.getInt(12);
                objRutaInspeccion.EtapaFenologicaNombre = objCursor.getString(13);
                objRutaInspeccion.Exposicion = objCursor.getString(14).charAt(0);
                objRutaInspeccion.CondicionDesarrolloClave = objCursor.getInt(15);
                objRutaInspeccion.CondicionDesarrolloNombre = objCursor.getString(16);
                objRutaInspeccion.OrdenCorrecto = objCursor.getString(17).charAt(0);
                objRutaInspeccion.RegulaPh = objCursor.getString(18).charAt(0);
                objRutaInspeccion.UsoAdecuado = objCursor.getString(19).charAt(0);
                objRutaInspeccion.HoraAplicacion = objCursor.getString(20).charAt(0);
                objRutaInspeccion.AguaCanal = objCursor.getString(21).charAt(0);
                objRutaInspeccion.Inundacion = objCursor.getString(22).charAt(0);
                objRutaInspeccion.BajaPoblacion = objCursor.getString(23).charAt(0);
                objRutaInspeccion.AplicacionNutrientes = objCursor.getString(23).charAt(0);
                objRutaInspeccion.AlteracionCiclo = objCursor.getString(24).charAt(0);
                objRutaInspeccion.AplicacionAgroquimicos = objCursor.getString(25).charAt(0);
                objRutaInspeccion.AltasTemperaturas = objCursor.getString(27).charAt(0);
                objRutaInspeccion.Fito = objCursor.getString(28).charAt(0);
                objRutaInspeccion.PlagasMalControladas = objCursor.getString(29).charAt(0);
                objRutaInspeccion.MalezaClave = objCursor.getInt(30);
                objRutaInspeccion.MalezaNombre = objCursor.getString(31);
                objRutaInspeccion.EstadoMalezaClave = objCursor.getInt(32);
                objRutaInspeccion.EstadoMalezaNombre = objCursor.getString(33);
                objRutaInspeccion.PlagaClave = objCursor.getInt(34);
                objRutaInspeccion.PlagaNombre = objCursor.getString(35);
                objRutaInspeccion.EstadoPlagaClave = objCursor.getInt(36);
                objRutaInspeccion.EstadoPlagaNombre = objCursor.getString(37);
                objRutaInspeccion.EnfermedadClave = objCursor.getInt(38);
                objRutaInspeccion.EnfermedadNombre = objCursor.getString(39);
                objRutaInspeccion.EstadoEnfermedadClave = objCursor.getInt(40);
                objRutaInspeccion.EstadoEnfermedadNombre = objCursor.getString(41);
                objRutaInspeccion.PotencialRendimientoClave = objCursor.getInt(42);
                objRutaInspeccion.PotencialRendimientoNombre = objCursor.getString(43);
                objRutaInspeccion.Estatus = objCursor.getString(44);
                objRutaInspeccion.Uso = objCursor.getString(45);
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
            String query = "INSERT INTO BATRUINS (USUARCVE,CICLOCVE,PLANEFEC,PLADEFOL,RUINSFEI,RUINSSTS) VALUES (" + objRutaInspeccion.UsuarioClave + "," +
                    + objRutaInspeccion.CicloClave + "," +
                    "'" + objRutaInspeccion.Fecha + "'," +
                    + objRutaInspeccion.Folio + "," +
                    "DATETIME('NOW','LOCALTIME')," +
                    "'" + objRutaInspeccion.Estatus + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean UpdateRutaInspeccion(RutaInspeccion objRutaInspeccion) {
        try {
            boolean resul = true;
            String query = "UPDATE BATRUINS SET " +
                    "RUINSFEF=DATETIME('NOW','LOCALTIME'),"+
                    "RUINSREC='"+ objRutaInspeccion.RecomendacionTecnica + "'," +
                    "SIPROCVE="+ objRutaInspeccion.SistemaProduccionClave + "," +
                    "ARTOPCVE="+ objRutaInspeccion.ArregloTopologicoClave + "," +
                    "RUINSSIA='"+ objRutaInspeccion.ProfundidadSiembra + "'," +
                    "RUINSSUA='"+ objRutaInspeccion.ProfundidadSurco + "'," +
                    "RUINSMAN='"+ objRutaInspeccion.ManejoAdecuado + "'," +
                    "ETAFECVE="+ objRutaInspeccion.EtapaFenologicaClave + "," +
                    "RUINSEXP='"+ objRutaInspeccion.Exposicion + "'," +
                    "CONDICVE="+ objRutaInspeccion.CondicionDesarrolloClave + "," +
                    "RUINSORD='"+ objRutaInspeccion.OrdenCorrecto + "'," +
                    "RUINSREG='"+ objRutaInspeccion.RegulaPh + "'," +
                    "RUINSUSA='"+ objRutaInspeccion.UsoAdecuado + "'," +
                    "RUINSHOR='"+ objRutaInspeccion.HoraAplicacion + "'," +
                    "RUINSAGU='"+ objRutaInspeccion.AguaCanal + "'," +
                    "RUINSINU='"+ objRutaInspeccion.Inundacion + "'," +
                    "RUINSPOB='"+ objRutaInspeccion.BajaPoblacion + "'," +
                    "RUINSPRO='"+ objRutaInspeccion.AplicacionNutrientes + "'," +
                    "RUINSALT='"+ objRutaInspeccion.AlteracionCiclo + "'," +
                    "RUINSAPL='"+ objRutaInspeccion.AplicacionAgroquimicos + "'," +
                    "RUINSTEM='"+ objRutaInspeccion.AltasTemperaturas + "'," +
                    "RUINSFIT='"+ objRutaInspeccion.Fito + "'," +
                    "RUINSPLA='"+ objRutaInspeccion.PlagasMalControladas + "'," +
                    "MALEZCVE="+ objRutaInspeccion.MalezaClave + "," +
                    "ESTMACVE="+ objRutaInspeccion.EstadoMalezaClave + "," +
                    "PLAGACVE="+ objRutaInspeccion.PlagaClave + "," +
                    "ESTPLCVE="+ objRutaInspeccion.EstadoPlagaClave + "," +
                    "ENFERCVE="+ objRutaInspeccion.EnfermedadClave + "," +
                    "ESTENCVE="+ objRutaInspeccion.EstadoEnfermedadClave + "," +
                    "POTRECVE="+ objRutaInspeccion.PotencialRendimientoClave + "," +
                    "RUINSSTS='" + objRutaInspeccion.Estatus + "'" +
                    "WHERE USUARCVE="+objRutaInspeccion.UsuarioClave+
                    " AND CICLOCVE="+objRutaInspeccion.CicloClave+
                    " AND PLANEFEC='"+objRutaInspeccion.Fecha+"'"+
                    " AND PLADEFOL="+objRutaInspeccion.Folio+";";
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
