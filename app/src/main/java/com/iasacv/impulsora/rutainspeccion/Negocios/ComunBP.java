package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Conexion.Crypt;
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Datos.ComunDA;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Administrator on 26/06/2015.
 */
public class ComunBP {

    //Variables
    private ComunDA _objComunDA;

    public ComunBP(Context context) {
        _objComunDA = new ComunDA(context);
    }

    public boolean CheckDataBase(){
        try{
        boolean resul = _objComunDA.CheckDataBase();
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void CreateDataBase() throws IOException {
        try {
            _objComunDA.CreateDataBase();
        } catch (IOException e) {
            throw e;
        }
    }

    public String Encrypt(String plainText) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return _objComunDA.Encrypt(plainText);
    }

    //public void Mensaje(String mensaje, Context context) {
      //  Toast toastCorrecto = Toast.makeText(context,mensaje, Toast.LENGTH_LONG);
       // toastCorrecto.show();
    //}

    public void Mensaje(String mensaje,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rutas de Inspecci\u00F3n");
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();
    }
}
