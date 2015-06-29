package com.iasacv.impulsora.rutainspeccion.Modelo;

import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * Created by Administrator on 19/06/2015.
 */
public class Usuario implements KvmSerializable{

    public int Clave;
    public String Nombre;
    public int RolClave;
    public String FechaVencimientoPassword;
    public String FechaAlta;
    public String RFC;
    public String Password;
    public String Email;
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
                return RolClave;
            case 3:
                return FechaVencimientoPassword;
            case 4:
                return FechaAlta;
            case 5:
                return RFC;
            case 6:
                return Password;
            case 7:
                return Email;
            case 8:
                return Estatus;
            case 9:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 9;
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
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RolClave";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FechaVencimientoPassword";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FechaAlta";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RFC";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Password";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "email";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 9:
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
                RolClave = Integer.parseInt(val.toString());
                break;
            case 3:
                FechaVencimientoPassword = val.toString();
                break;
            case 4:
                FechaAlta = val.toString();
                break;
            case 5:
                RFC = val.toString();
                break;
            case 6:
                Password = val.toString();
                break;
            case 7:
                Email = val.toString();
                break;
            case 8:
                Estatus = val.toString();
                break;
            case 9:
                Uso = val.toString();
                break;
            default:
                break;
        }
    }
}
