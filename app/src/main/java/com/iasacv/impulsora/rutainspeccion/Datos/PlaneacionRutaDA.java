package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
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



