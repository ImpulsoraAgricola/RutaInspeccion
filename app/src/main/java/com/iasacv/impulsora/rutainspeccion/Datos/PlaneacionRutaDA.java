package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.Item;
import com.iasacv.impulsora.rutainspeccion.Modelo.PlaneacionRuta;
import com.iasacv.impulsora.rutainspeccion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01/07/2015.
 */
public class PlaneacionRutaDA {
    //Variables
    private EntLibDBTools objEntLibTools;
    Bitmap objInspection;
    Bitmap objSurvey;

    public PlaneacionRutaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
        objInspection=BitmapFactory.decodeResource(context.getResources(), R.drawable.inspection);
        objSurvey=BitmapFactory.decodeResource(context.getResources(), R.drawable.survey);
    }

    public String ArmaFiltro(PlaneacionRuta entidad) {
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
            cadena += "A.PLANEFEC=" + entidad.Fecha;
        }
        //--Folio
        if (entidad.Folio != 0) {
            if (cadena != "") { cadena += " AND "; }
            cadena += "A.PLADEFOL=" + entidad.Folio;
        }
        return cadena;
    }

    public PlaneacionRuta GetPlaneacionRuta(PlaneacionRuta objFiltro) {
        try {
            String filtro = ArmaFiltro(objFiltro);
            PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
            Cursor objCursor = objEntLibTools.executeCursor("SELECT USUARCVE,USUARNOM,A.CICLOCVE,CICLONOM,PLANEFEC,PLADEFOL,A.TIINSCVE," +
                    "TIINSNOM,A.TIARTCVE,TIARTNOM,PERSOCVE,PERSONOM,PRODUCVE,PRODUNOM,PREDICVE,PREDINOM,PREDILAT,PREDILON,LOTESCVE," +
                    "LOTESNOM,LOTESLAT,LOTESLON,PLADEASE,ARTICNOS,PLADEACO,ARTICNOC,PLADESTS,PLADEUSO " +
                    "FROM BATPLADE A INNER JOIN IGMCICLO B ON (A.CICLOCVE=B.CICLOCVE) " +
                    "INNER JOIN BACTIINS C ON (A.TIINSCVE=C.TIINSCVE) " +
                    "INNER JOIN BACTIART D ON (A.TIARTCVE=D.TIARTCVE) " +
                    "WHERE " + filtro);
            while (objCursor.moveToNext()) {
                objPlaneacionRuta.UsuarioClave = Integer.parseInt(objCursor.getString(0));
                objPlaneacionRuta.UsuarioNombre = objCursor.getString(1);
                objPlaneacionRuta.CicloClave = Integer.parseInt(objCursor.getString(2));
                objPlaneacionRuta.CicloNombre = objCursor.getString(3);
                objPlaneacionRuta.Fecha = objCursor.getString(4);
                objPlaneacionRuta.Folio = Integer.parseInt(objCursor.getString(5));
                objPlaneacionRuta.TipoInspeccionClave = Integer.parseInt(objCursor.getString(6));
                objPlaneacionRuta.TipoInspeccionNombre = objCursor.getString(7);
                objPlaneacionRuta.TipoArticuloClave = Integer.parseInt(objCursor.getString(8));
                objPlaneacionRuta.TipoArticuloNombre = objCursor.getString(9);
                objPlaneacionRuta.ClienteClave = Integer.parseInt(objCursor.getString(10));
                objPlaneacionRuta.ClienteNombre = objCursor.getString(11);
                objPlaneacionRuta.ProductorClave = Integer.parseInt(objCursor.getString(12));
                objPlaneacionRuta.ProductorNombre = objCursor.getString(13);
                objPlaneacionRuta.PredioClave = Integer.parseInt(objCursor.getString(14));
                objPlaneacionRuta.PredioNombre = objCursor.getString(15);
                objPlaneacionRuta.PredioLatitud = Double.parseDouble(objCursor.getString(16));
                objPlaneacionRuta.PredioLongitud = Double.parseDouble(objCursor.getString(17));
                objPlaneacionRuta.LoteClave = Integer.parseInt(objCursor.getString(18));
                objPlaneacionRuta.LoteNombre = objCursor.getString(19);
                objPlaneacionRuta.LoteLatitud = Double.parseDouble(objCursor.getString(20));
                objPlaneacionRuta.LoteLongitud = Double.parseDouble(objCursor.getString(21));
                objPlaneacionRuta.ArticuloSembrarClave = Integer.parseInt(objCursor.getString(22));
                objPlaneacionRuta.ArticuloSembrarNombre = objCursor.getString(23);
                objPlaneacionRuta.ArticuloCosecharClave = Integer.parseInt(objCursor.getString(24));
                objPlaneacionRuta.ArticuloCosecharNombre = objCursor.getString(25);
                objPlaneacionRuta.Estatus = objCursor.getString(26);
                objPlaneacionRuta.Uso = objCursor.getString(27);
            }
            objCursor.close();
            return objPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<PlaneacionRuta> GetAllPlaneacionRutaList() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT PLADEFOL,PERSONOM,PRODUNOM,PREDINOM,LOTESNOM,PLADESTS FROM BATPLADE");
            List<PlaneacionRuta> listaPlaneacionRuta = new ArrayList<PlaneacionRuta>();
            while (objCursor.moveToNext()) {
                PlaneacionRuta objPlaneacionRuta = new PlaneacionRuta();
                objPlaneacionRuta.Folio = Integer.parseInt(objCursor.getString(0));
                objPlaneacionRuta.ClienteNombre = objCursor.getString(1);
                objPlaneacionRuta.ProductorNombre = objCursor.getString(2);
                objPlaneacionRuta.PredioNombre = objCursor.getString(3);
                objPlaneacionRuta.LoteNombre = objCursor.getString(4);
                objPlaneacionRuta.Estatus = objCursor.getString(5);
                listaPlaneacionRuta.add(objPlaneacionRuta);
            }
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }

    public ArrayList<Item> GetAllPlaneacionRutaImage(int usuarioClave, String fecha) {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT PLADEFOL,PERSONOM,PRODUNOM,PREDINOM,LOTESNOM,PLADESTS FROM BATPLADE WHERE USUARCVE="+usuarioClave+" AND PLANEFEC='"+fecha+"' ORDER BY PLADEFOL");
            ArrayList<Item> listaPlaneacionRuta = new ArrayList<Item>();
            while (objCursor.moveToNext()) {
                if(objCursor.getString(5).toString().equals("I"))
                    listaPlaneacionRuta.add(new Item(Integer.parseInt(objCursor.getString(0)),objInspection,"Folio: "+objCursor.getString(0)+"\nCliente: "+objCursor.getString(1)+"\nProductor: "+
                            objCursor.getString(2)+"\nPredio: "+objCursor.getString(3)+"\nLote: "+objCursor.getString(4)));
                else
                    listaPlaneacionRuta.add(new Item(Integer.parseInt(objCursor.getString(0)),objSurvey,"Folio: "+objCursor.getString(0)+"\nCliente: "+objCursor.getString(1)+"\nProductor: "+
                            objCursor.getString(2)+"\nPredio: "+objCursor.getString(3)+"\nLote: "+objCursor.getString(4)));
            }
            return listaPlaneacionRuta;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean InsertPlaneacionRuta(PlaneacionRuta objPlaneacionRuta) {
        try {
            boolean resul = true;
            String query = "INSERT INTO BATPLADE VALUES (" + objPlaneacionRuta.UsuarioClave + "," +
                    "'" + objPlaneacionRuta.UsuarioNombre + "'," +
                    + objPlaneacionRuta.CicloClave + "," +
                    "'" + objPlaneacionRuta.Fecha + "'," +
                    + objPlaneacionRuta.Folio + "," +
                    + objPlaneacionRuta.TipoInspeccionClave + "," +
                    + objPlaneacionRuta.TipoArticuloClave + "," +
                    + objPlaneacionRuta.ClienteClave + "," +
                    "'" + objPlaneacionRuta.ClienteNombre + "'," +
                    + objPlaneacionRuta.ProductorClave + "," +
                    "'" + objPlaneacionRuta.ProductorNombre + "'," +
                    + objPlaneacionRuta.PredioClave + "," +
                    "'" + objPlaneacionRuta.PredioNombre + "'," +
                    + objPlaneacionRuta.PredioLatitud + "," +
                    + objPlaneacionRuta.PredioLongitud + "," +
                    + objPlaneacionRuta.LoteClave + "," +
                    "'" + objPlaneacionRuta.LoteNombre + "'," +
                    + objPlaneacionRuta.LoteLatitud + "," +
                    + objPlaneacionRuta.LoteLongitud + "," +
                    + objPlaneacionRuta.ArticuloSembrarClave + "," +
                    "'" + objPlaneacionRuta.ArticuloSembrarNombre + "'," +
                    + objPlaneacionRuta.ArticuloCosecharClave + "," +
                    "'" + objPlaneacionRuta.ArticuloCosecharNombre + "'," +
                    "'" + objPlaneacionRuta.Estatus + "'," +
                    "'" + objPlaneacionRuta.Uso + "')";
            objEntLibTools.insert(query);
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

}



