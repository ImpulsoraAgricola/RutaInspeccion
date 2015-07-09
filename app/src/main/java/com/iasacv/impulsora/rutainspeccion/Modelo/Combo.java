package com.iasacv.impulsora.rutainspeccion.Modelo;

/**
 * Created by Administrator on 06/07/2015.
 */
public class Combo {
    private String Nombre;
    private int Clave;

    public Combo(String nombre, int clave)
    {
        Nombre = nombre;
        Clave = clave;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public int getClave()
    {
        return Clave;
    }

    public String toString()
    {
        return Nombre;
    }
}
