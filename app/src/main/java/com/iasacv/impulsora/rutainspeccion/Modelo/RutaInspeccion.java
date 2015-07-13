package com.iasacv.impulsora.rutainspeccion.Modelo;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 19/06/2015.
 */
public class RutaInspeccion implements KvmSerializable{

    public int UsuarioClave;
    public String UsuarioNombre;
    public int CicloClave;
    public String CicloNombre;
    public String Fecha;
    public int Folio;
    public String FechaInicio;
    public String HoraInicio;
    public String FechaFin;
    public String HoraFin;
    public String Tiempo;
    public String RecomendacionTecnica;
    public int SistemaProduccionClave;
    public String SistemaProduccionNombre;
    public int ArregloTopologicoClave;
    public String ArregloTopologicoNombre;
    public String ProfundidadSiembra;
    public String ProfundidadSurco;
    public String ManejoAdecuado;
    public int EtapaFenologicaClave;
    public String EtapaFenologicaNombre;
    public String Exposicion;
    public int CondicionDesarrolloClave;
    public String CondicionDesarrolloNombre;
    public String OrdenCorrecto;
    public String RegulaPh;
    public String UsoAdecuado;
    public String HoraAplicacion;
    public String AguaCanal;
    public String Inundacion;
    public String BajaPoblacion;
    public String AplicacionNutrientes;
    public String AlteracionCiclo;
    public String AplicacionAgroquimicos;
    public String AltasTemperaturas;
    public String Fito;
    public String PlagasMalControladas;
    public int MalezaClave;
    public String MalezaNombre;
    public int EstadoMalezaClave;
    public String EstadoMalezaNombre;
    public int PlagaClave;
    public String PlagaNombre;
    public int EstadoPlagaClave;
    public String EstadoPlagaNombre;
    public int EnfermedadClave;
    public String EnfermedadNombre;
    public int EstadoEnfermedadClave;
    public String EstadoEnfermedadNombre;
    public int PotencialRendimientoClave;
    public String PotencialRendimientoNombre;
    public int TipoRiegoClave;
    public String TipoRiegoNombre;
    public int Capacidad;
    public String TipoRiegoOtro;
    public int RecomendacionClave;
    public String RecomendacionNombre;
    public String RecomendacionOtro;
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
                return FechaInicio;
            case 7:
                return HoraInicio;
            case 8:
                return FechaFin;
            case 9:
                return HoraFin;
            case 10:
                return Tiempo;
            case 11:
                return RecomendacionTecnica;
            case 12:
                return SistemaProduccionClave;
            case 13:
                return SistemaProduccionNombre;
            case 14:
                return ArregloTopologicoClave;
            case 15:
                return ArregloTopologicoNombre;
            case 16:
                return ProfundidadSiembra;
            case 17:
                return ProfundidadSurco;
            case 18:
                return ManejoAdecuado;
            case 19:
                return EtapaFenologicaClave;
            case 20:
                return EtapaFenologicaNombre;
            case 21:
                return Exposicion;
            case 22:
                return CondicionDesarrolloClave;
            case 23:
                return CondicionDesarrolloNombre;
            case 24:
                return OrdenCorrecto;
            case 25:
                return RegulaPh;
            case 26:
                return UsoAdecuado;
            case 27:
                return HoraAplicacion;
            case 28:
                return AguaCanal;
            case 29:
                return Inundacion;
            case 30:
                return BajaPoblacion;
            case 31:
                return AplicacionNutrientes;
            case 32:
                return AlteracionCiclo;
            case 33:
                return AplicacionAgroquimicos;
            case 34:
                return AltasTemperaturas;
            case 35:
                return Fito;
            case 36:
                return PlagasMalControladas;
            case 37:
                return MalezaClave;
            case 38:
                return MalezaNombre;
            case 39:
                return EstadoMalezaClave;
            case 40:
                return EstadoMalezaNombre;
            case 41:
                return PlagaClave;
            case 42:
                return PlagaNombre;
            case 43:
                return EstadoPlagaClave;
            case 44:
                return EstadoPlagaNombre;
            case 45:
                return EnfermedadClave;
            case 46:
                return EnfermedadNombre;
            case 47:
                return EstadoEnfermedadClave;
            case 48:
                return EstadoEnfermedadNombre;
            case 49:
                return PotencialRendimientoClave;
            case 50:
                return PotencialRendimientoNombre;
            case 51:
                return TipoRiegoClave;
            case 52:
                return TipoRiegoNombre;
            case 53:
                return Capacidad;
            case 54:
                return TipoRiegoOtro;
            case 55:
                return RecomendacionClave;
            case 56:
                return RecomendacionNombre;
            case 57:
                return RecomendacionOtro;
            case 58:
                return Estatus;
            case 59:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 60;
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
                info.name = "CicloNombre";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fecha";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Folio";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FechaInicio";
                break;
            case 7:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "HoraInicio";
                break;
            case 8:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "FechaFin";
                break;
            case 9:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "HoraFin";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Tiempo";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RecomendacionTecnica";
                break;
            case 12:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SistemaProduccionClave";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SistemaProduccionNombre";
                break;
            case 14:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ArregloTopologicoClave";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ArregloTopologicoNombre";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfundidadSiembra";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfundidadSurco";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ManejoAdecuado";
                break;
            case 19:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EtapaFenologicaClave";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EtapaFenologicaNombre";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Exposicion";
                break;
            case 22:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CondicionDesarrolloClave";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CondicionDesarrolloNombre";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OrdenCorrecto";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegulaPh";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UsoAdecuado";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "HoraAplicacion";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AguaCanal";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Inundacion";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BajaPoblacion";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AplicacionNutrientes";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AlteracionCiclo";
                break;
            case 33:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AplicacionAgroquimicos";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AltasTemperaturas";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fito";
                break;
            case 36:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PlagasMalControladas";
                break;
            case 37:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MalezaClave";
                break;
            case 38:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MalezaNombre";
                break;
            case 39:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoMalezaClave";
                break;
            case 40:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EstadoMalezaNombre";
                break;
            case 41:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PlagaClave";
                break;
            case 42:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PlagaNombre";
                break;
            case 43:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoPlagaClave";
                break;
            case 44:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EstadoPlagaNombre";
                break;
            case 45:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EnfermedadClave";
                break;
            case 46:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EnfermedadNombre";
                break;
            case 47:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoEnfermedadClave";
                break;
            case 48:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EstadoEnfermedadNombre";
                break;
            case 49:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PotencialRendimientoClave";
                break;
            case 50:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PotencialRendimientoNombre";
                break;
            case 51:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TipoRiegoClave";
                break;
            case 52:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TipoRiegoNombre";
                break;
            case 53:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Capacidad";
                break;
            case 54:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TipoRiegoOtro";
                break;
            case 55:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RecomendacionClave";
                break;
            case 56:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RecomendacionNombre";
                break;
            case 57:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RecomendacionOtro";
                break;
            case 58:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 59:
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
                CicloClave = Integer.parseInt(val.toString());
                break;
            case 2:
                Fecha = val.toString();
                break;
            case 3:
                Folio = Integer.parseInt(val.toString());
                break;
            case 4:
                FechaInicio = val.toString();
                break;
            case 5:
                HoraInicio = val.toString();
                break;
            case 6:
                FechaFin = val.toString();
                break;
            case 7:
                HoraFin = val.toString();
                break;
            case 8:
                Tiempo = val.toString();
                break;
            case 9:
                RecomendacionTecnica = val.toString();
                break;
            case 10:
                SistemaProduccionClave = Integer.parseInt(val.toString());
                break;
            case 11:
                ArregloTopologicoClave = Integer.parseInt(val.toString());
                break;
            case 12:
                ProfundidadSiembra = val.toString();
                break;
            case 13:
                ProfundidadSurco = val.toString();
                break;
            case 14:
                ManejoAdecuado = val.toString();
                break;
            case 15:
                EtapaFenologicaClave = Integer.parseInt(val.toString());
                break;
            case 16:
                Exposicion = val.toString();
                break;
            case 17:
                CondicionDesarrolloClave = Integer.parseInt(val.toString());
                break;
            case 18:
                OrdenCorrecto = val.toString();
                break;
            case 19:
                RegulaPh = val.toString();
                break;
            case 20:
                UsoAdecuado = val.toString();
                break;
            case 21:
                HoraAplicacion = val.toString();
                break;
            case 22:
                AguaCanal = val.toString();
                break;
            case 23:
                Inundacion = val.toString();
                break;
            case 24:
                BajaPoblacion = val.toString();
                break;
            case 25:
                AplicacionNutrientes = val.toString();
                break;
            case 26:
                AlteracionCiclo = val.toString();
                break;
            case 27:
                AplicacionAgroquimicos = val.toString();
                break;
            case 28:
                AltasTemperaturas = val.toString();
                break;
            case 29:
                Fito = val.toString();
                break;
            case 30:
                PlagasMalControladas = val.toString();
                break;
            case 31:
                MalezaClave = Integer.parseInt(val.toString());
                break;
            case 32:
                EstadoMalezaClave = Integer.parseInt(val.toString());
                break;
            case 33:
                PlagaClave = Integer.parseInt(val.toString());
                break;
            case 34:
                EstadoPlagaClave = Integer.parseInt(val.toString());
                break;
            case 35:
                EnfermedadClave = Integer.parseInt(val.toString());
                break;
            case 36:
                EstadoEnfermedadClave = Integer.parseInt(val.toString());
                break;
            case 37:
                PotencialRendimientoClave = Integer.parseInt(val.toString());
                break;
            case 38:
                TipoRiegoClave = Integer.parseInt(val.toString());
                break;
            case 39:
                Capacidad = Integer.parseInt(val.toString());
                break;
            case 40:
                TipoRiegoOtro = val.toString();
                break;
            case 41:
                RecomendacionClave = Integer.parseInt(val.toString());
                break;
            case 42:
                RecomendacionOtro = val.toString();
                break;
            case 43:
                Estatus = val.toString();
                break;
            case 44:
                Uso = val.toString();
                break;
            default:
                break;
        }
    }
}
