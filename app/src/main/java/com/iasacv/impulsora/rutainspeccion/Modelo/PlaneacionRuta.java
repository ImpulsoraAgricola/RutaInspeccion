package com.iasacv.impulsora.rutainspeccion.Modelo;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 01/07/2015.
 */
public class PlaneacionRuta implements KvmSerializable {

    public int UsuarioClave;
    public String UsuarioNombre;
    public int CicloClave;
    public String CicloNombre;
    public String Fecha;
    public int Folio;
    public int TipoInspeccionClave;
    public String TipoInspeccionNombre;
    public int TipoArticuloClave;
    public String TipoArticuloNombre;
    public int ClienteClave;
    public String ClienteNombre;
    public int ProductorClave;
    public String ProductorNombre;
    public int PredioClave;
    public String PredioNombre;
    public double PredioLatitud;
    public double PredioLongitud;
    public int LoteClave;
    public String LoteNombre;
    public double LoteLatitud;
    public double LoteLongitud;
    public int ArticuloSembrarClave;
    public String ArticuloSembrarNombre;
    public int ArticuloCosecharClave;
    public String ArticuloCosecharNombre;
    public String Estatus;
    public String Uso;

    @Override
    public Object getProperty(int arg0) {
        switch(arg0)
        {
            case 0:
                return UsuarioClave;
            case 1:
                return UsuarioNombre;
            case 2:
                return CicloClave;
            case 3:
                return CicloNombre;
            case 4:
                return Fecha;
            case 5:
                return Folio;
            case 6:
                return TipoInspeccionClave;
            case 7:
                return TipoInspeccionNombre;
            case 8:
                return TipoArticuloClave;
            case 9:
                return TipoArticuloNombre;
            case 10:
                return ClienteClave;
            case 11:
                return ClienteNombre;
            case 12:
                return ProductorClave;
            case 13:
                return ProductorNombre;
            case 14:
                return PredioClave;
            case 15:
                return PredioNombre;
            case 16:
                return PredioLatitud;
            case 17:
                return PredioLongitud;
            case 18:
                return LoteClave;
            case 19:
                return LoteNombre;
            case 20:
                return LoteLatitud;
            case 21:
                return LoteLongitud;
            case 22:
                return ArticuloSembrarClave;
            case 23:
                return ArticuloSembrarNombre;
            case 24:
                return ArticuloCosecharClave;
            case 25:
                return ArticuloCosecharNombre;
            case 26:
                return Estatus;
            case 27:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 28;
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
                info.name = "UsuarioNombre";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CicloClave";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CicloNombre;";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fecha;";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Folio;";
                break;
            case 6:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TipoInspeccionClave";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TipoInspeccionNombre";
                break;
            case 8:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TipoArticuloClave";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TipoArticuloNombre;";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ClienteClave";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ClienteNombre";
                break;
            case 12:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ProductorClave";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProductorNombre";
                break;
            case 14:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PredioClave";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PredioNombre";
                break;
            case 16:
                info.type = Double.class;
                info.name = "PredioLatitud";
                break;
            case 17:
                info.type = Double.class;
                info.name = "PredioLongitud";
                break;
            case 18:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "LoteClave";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LoteNombre";
                break;
            case 20:
                info.type = Double.class;
                info.name = "LoteLatitud";
                break;
            case 21:
                info.type = Double.class;
                info.name = "LoteLongitud";
                break;
            case 22:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ArticuloSembrarClave";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ArticuloSembrarNombre";
                break;
            case 24:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ArticuloCosecharClave";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ArticuloCosecharNombre";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 27:
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
                UsuarioNombre = val.toString();
                break;
            case 2:
                CicloClave = Integer.parseInt(val.toString());
                break;
            case 3:
                CicloNombre = val.toString();
                break;
            case 4:
                Fecha = val.toString();
                break;
            case 5:
                Folio = Integer.parseInt(val.toString());
                break;
            case 6:
                TipoInspeccionClave = Integer.parseInt(val.toString());
                break;
            case 7:
                TipoInspeccionNombre = val.toString();
                break;
            case 8:
                TipoArticuloClave = Integer.parseInt(val.toString());
                break;
            case 9:
                TipoArticuloNombre = val.toString();
                break;
            case 10:
                ClienteClave = Integer.parseInt(val.toString());
                break;
            case 11:
                ClienteNombre = val.toString();
                break;
            case 12:
                ProductorClave = Integer.parseInt(val.toString());
                break;
            case 13:
                ProductorNombre = val.toString();
                break;
            case 14:
                PredioClave = Integer.parseInt(val.toString());
                break;
            case 15:
                PredioNombre = val.toString();
                break;
            case 16:
                PredioLatitud = Double.parseDouble(val.toString());
                break;
            case 17:
                PredioLongitud = Double.parseDouble(val.toString());
                break;
            case 18:
                LoteClave = Integer.parseInt(val.toString());
                break;
            case 19:
                LoteNombre = val.toString();
                break;
            case 20:
                LoteLatitud = Double.parseDouble(val.toString());
                break;
            case 21:
                LoteLongitud = Double.parseDouble(val.toString());
                break;
            case 22:
                ArticuloSembrarClave = Integer.parseInt(val.toString());
                break;
            case 23:
                ArticuloSembrarNombre = val.toString();
                break;
            case 24:
                ArticuloCosecharClave = Integer.parseInt(val.toString());
                break;
            case 25:
                ArticuloCosecharNombre = val.toString();
                break;
            case 26:
                Estatus = val.toString();
                break;
            case 27:
                Uso = val.toString();
                break;
            default:
                break;
        }
    }
}
