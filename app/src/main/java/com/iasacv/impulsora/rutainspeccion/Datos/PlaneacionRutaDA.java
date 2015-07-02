package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.iasacv.impulsora.rutainspeccion.Administrador;
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Modelo.EstadoMPE;
import com.iasacv.impulsora.rutainspeccion.Modelo.ImageItem;
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
    Bitmap objBit;

    public PlaneacionRutaDA(Context context) {
        objEntLibTools = new EntLibDBTools(context);
        objBit=BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_home);
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

    public ArrayList<ImageItem> GetAllPlaneacionRutaImage() {
        try {
            Cursor objCursor = objEntLibTools.executeCursor("SELECT PLADEFOL,PERSONOM,PRODUNOM,PREDINOM,LOTESNOM,PLADESTS FROM BATPLADE");
            ArrayList<ImageItem> listaPlaneacionRuta = new ArrayList<ImageItem>();
            while (objCursor.moveToNext()) {
                listaPlaneacionRuta.add(new ImageItem(objBit,objCursor.getString(0)+" "+objCursor.getString(1)));
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



