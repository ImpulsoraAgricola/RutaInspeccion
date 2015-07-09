package com.iasacv.impulsora.rutainspeccion.Modelo;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 19/06/2015.
 */
public class RutaInspeccion implements KvmSerializable{

    public int UsuarioClave;
    public int CicloClave;
    public String Fecha;
    public int Folio;
    public String FechaInicio;
    public String HoraInicio;
    public String FechaFin;
    public String HoraFin;
    public String Tiempo;
    public char RecomendacionTecnica;
    public int SistemaProduccionClave;
    public int ArregloTopologicoClave;
    public char ProfundidadSiembra;
    public char ProfundidadSurco;
    public char ManejoAdecuado;
    public int EtapaFenologicaClave;
    public char Exposicion;
    public int CondicionDesarrolLoClave;
    public char OrdenCorrecto;
    public char RegulaPh;
    public char UsoAdecuado;
    public char HoraAplicacion;
    public char AguaCanal;
    public char Inundacion;
    public char BajaPoblacion;
    public char AplicacionNutrientes;
    public char AlteracionCiclo;
    public char AplicacionAgroquimicos;
    public char AltasTemperaturas;
    public char Fito;
    public char PlagasMalControladas;
    public int MalezaClave;
    public int EstadoMalezaClave;
    public int PlagaClave;
    public int EstadoPlagaClave;
    public int EnfermedadClave;
    public int EstadoEnfermedadClave;
    public int PotencialRendimientoClave;
    public int TipoRiegoClave;
    public int Capacidad;
    public String TipoRiegoOtro;
    public int RecomendacionClave;
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
                return CicloClave;
            case 2:
                return Fecha;
            case 3:
                return Folio;
            case 4:
                return FechaInicio;
            case 5:
                return HoraInicio;
            case 6:
                return FechaFin;
            case 7:
                return HoraFin;
            case 8:
                return Tiempo;
            case 9:
                return RecomendacionTecnica;
            case 10:
                return SistemaProduccionClave;
            case 11:
                return ArregloTopologicoClave;
            case 12:
                return ProfundidadSiembra;
            case 13:
                return ProfundidadSurco;
            case 14:
                return ManejoAdecuado;
            case 15:
                return EtapaFenologicaClave;
            case 16:
                return Exposicion;
            case 17:
                return CondicionDesarrolLoClave;
            case 18:
                return OrdenCorrecto;
            case 19:
                return RegulaPh;
            case 20:
                return UsoAdecuado;
            case 21:
                return HoraAplicacion;
            case 22:
                return AguaCanal;
            case 23:
                return Inundacion;
            case 24:
                return BajaPoblacion;
            case 25:
                return AplicacionNutrientes;
            case 26:
                return AlteracionCiclo;
            case 27:
                return AplicacionAgroquimicos;
            case 28:
                return AltasTemperaturas;
            case 29:
                return Fito;
            case 30:
                return PlagasMalControladas;
            case 31:
                return MalezaClave;
            case 32:
                return EstadoMalezaClave;
            case 33:
                return PlagaClave;
            case 34:
                return EstadoPlagaClave;
            case 35:
                return EnfermedadClave;
            case 36:
                return EstadoEnfermedadClave;
            case 37:
                return PotencialRendimientoClave;
            case 38:
                return TipoRiegoClave;
            case 39:
                return Capacidad;
            case 40:
                return TipoRiegoOtro;
            case 41:
                return RecomendacionClave;
            case 42:
                return RecomendacionOtro;
            case 43:
                return Estatus;
            case 44:
                return Uso;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 45;
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
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CicloClave";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fecha";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Folio";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FechaInicio";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "HoraInicio";
                break;
            case 6:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "FechaFin";
                break;
            case 7:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "HoraFin";
                break;
            case 8:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Tiempo";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RecomendacionTecnica";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SistemaProduccionClave";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ArregloTopologicoClave";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfundidadSiembra";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProfundidadSurco";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ManejoAdecuado";
                break;
            case 15:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EtapaFenologicaClave";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Exposicion";
                break;
            case 17:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CicloClave";
                break;
            case 18:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CondicionDesarrolloClave";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OrdenCorrecto";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RegulaPh";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "UsoAdecuado";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "HoraAplicacion";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AguaCanal";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Inundacion";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BajaPoblacion";
                break;
            case 26:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AplicacionNutrientes";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AlteracionCiclo";
                break;
            case 28:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AplicacionAgroquimicos";
                break;
            case 29:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AltasTemperaturas";
                break;
            case 30:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Fito";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PlagasMalControladas";
                break;
            case 32:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "MalezaClave";
                break;
            case 33:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoMalezaClave";
                break;
            case 34:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PlagaClave";
                break;
            case 35:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoPlagaClave";
                break;
            case 36:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EnfermedadClave";
                break;
            case 37:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "EstadoEnfermedadClave";
                break;
            case 38:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PotencialRendimientoClave";
                break;
            case 39:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TipoRiegoClave";
                break;
            case 40:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Capacidad";
                break;
            case 41:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TipoRiegoOtro";
                break;
            case 42:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RecomendacionClave";
                break;
            case 43:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RecomendacionOtro";
                break;
            case 44:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Estatus";
                break;
            case 45:
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
                RecomendacionTecnica = val.toString().charAt(0);
                break;
            case 10:
                SistemaProduccionClave = Integer.parseInt(val.toString());
                break;
            case 11:
                ArregloTopologicoClave = Integer.parseInt(val.toString());
                break;
            case 12:
                ProfundidadSiembra = val.toString().charAt(0);
                break;
            case 13:
                ProfundidadSurco = val.toString().charAt(0);
                break;
            case 14:
                ManejoAdecuado = val.toString().charAt(0);
                break;
            case 15:
                EtapaFenologicaClave = Integer.parseInt(val.toString());
                break;
            case 16:
                Exposicion = val.toString().charAt(0);
                break;
            case 17:
                CondicionDesarrolLoClave = Integer.parseInt(val.toString());
                break;
            case 18:
                OrdenCorrecto = val.toString().charAt(0);
                break;
            case 19:
                RegulaPh = val.toString().charAt(0);
                break;
            case 20:
                UsoAdecuado = val.toString().charAt(0);
                break;
            case 21:
                HoraAplicacion = val.toString().charAt(0);
                break;
            case 22:
                AguaCanal = val.toString().charAt(0);
                break;
            case 23:
                Inundacion = val.toString().charAt(0);
                break;
            case 24:
                BajaPoblacion = val.toString().charAt(0);
                break;
            case 25:
                AplicacionNutrientes = val.toString().charAt(0);
                break;
            case 26:
                AlteracionCiclo = val.toString().charAt(0);
                break;
            case 27:
                AplicacionAgroquimicos = val.toString().charAt(0);
                break;
            case 28:
                AltasTemperaturas = val.toString().charAt(0);
                break;
            case 29:
                Fito = val.toString().charAt(0);
                break;
            case 30:
                PlagasMalControladas = val.toString().charAt(0);
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
