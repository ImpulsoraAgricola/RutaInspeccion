package com.iasacv.impulsora.rutainspeccion.Modelo;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 30/06/2015.
 */
public class Item {
    Bitmap image;
    String title;
    int Folio;

    public Item(int folio,Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
        this.Folio = folio;
    }
    public int getFolio() {
        return Folio;
    }
    public void setFolio(int folio) {
        this.Folio = folio;
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
