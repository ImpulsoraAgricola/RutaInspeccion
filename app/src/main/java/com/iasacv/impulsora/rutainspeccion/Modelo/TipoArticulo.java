package com.iasacv.impulsora.rutainspeccion.Modelo;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 01/07/2015.
 */
public class TipoArticulo implements KvmSerializable {

    public int Clave;
    public String Nombre;
    public String Estatus;
    public String Uso;

    @Override
    public Object getProperty(int arg0) {
        switch(arg0)
        {
            case 0:
                return Clave;
            case 1:
                return Nombre;
            case 2:
                return Estatus;
            case 3:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        switch(ind)
        {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Clave";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Nombre";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Uso";
                break;
            default:break;
        }
    }

    @Override
    public void setProperty(int ind, Object val) {
        switch(ind)
        {
            case 0:
                Clave = Integer.parseInt(val.toString());
                break;
            case 1:
                Nombre = val.toString();
                break;
            case 2:
                Estatus = val.toString();
                break;
            case 3:
                Uso = val.toString();
                break;
            default:
                break;
        }
    }
}
