package com.iasacv.impulsora.rutainspeccion.Negocios;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Conexion.Crypt;
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;
import com.iasacv.impulsora.rutainspeccion.Datos.ComunDA;
import com.iasacv.impulsora.rutainspeccion.R;

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

    public void Mensaje(String mensaje,Context context) {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(context, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage(mensaje);
        alert.setCancelable(false);
        alert.setIcon(R.drawable.info);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });
        alert.show();
    }
}
