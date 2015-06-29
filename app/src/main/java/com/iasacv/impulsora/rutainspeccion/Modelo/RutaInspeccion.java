package com.iasacv.impulsora.rutainspeccion.Modelo;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 19/06/2015.
 */
public class RutaInspeccion implements KvmSerializable{

    public int UsuarioClave;
    public String Fecha;
    public int CicloClave;
    public String Estatus;
    public String Uso;

    @Override
    public Object getProperty(int arg0) {
        switch(arg0)
        {
            case 0:
                return UsuarioClave;
            case 1:
                return Fecha;
            case 2:
                return CicloClave;
            case 3:
                return Estatus;
            case 4:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        switch(ind)
        {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "UsuarioClave";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fecha";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CicloClave";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 4:
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
                UsuarioClave = Integer.parseInt(val.toString());
                break;
            case 1:
                Fecha = val.toString();
                break;
            case 2:
                CicloClave = Integer.parseInt(val.toString());
                break;
            case 3:
                Estatus = val.toString();
                break;
            case 4:
                Uso = val.toString();
                break;
            default:
                break;
        }
    }
}
