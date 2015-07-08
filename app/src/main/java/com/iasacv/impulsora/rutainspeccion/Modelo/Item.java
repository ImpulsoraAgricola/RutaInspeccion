package com.iasacv.impulsora.rutainspeccion.Modelo;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 30/06/2015.
 */
public class Item {
    Bitmap image;
    String title;
    int Folio;
    int CicloClave;
    int UsuarioClave;
    int TipoInspeccion;

    public Item(int cicloClave, int usuarioClave, int folio, int tipoInspeccion, Bitmap image, String title) {
        super();
        this.Folio = folio;
        this.CicloClave = cicloClave;
        this.UsuarioClave = usuarioClave;
        this.TipoInspeccion = tipoInspeccion;
        this.image = image;
        this.title = title;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int folio) {
        this.Folio = folio;
    }

    public int getCicloClave() {
        return CicloClave;
    }

    public void setCicloClave(int cicloClave) {
        this.CicloClave = cicloClave;
    }

    public int getUsuarioClave() {
        return UsuarioClave;
    }

    public void setUsuarioClave(int usuarioClave) {
        this.UsuarioClave = usuarioClave;
    }

    public int getTipoInspeccion() {
        return TipoInspeccion;
    }

    public void setTipoInspeccion(int tipoInspeccion) {
        this.TipoInspeccion = tipoInspeccion;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
