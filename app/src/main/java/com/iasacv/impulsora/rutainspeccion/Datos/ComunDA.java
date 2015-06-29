package com.iasacv.impulsora.rutainspeccion.Datos;

import android.content.Context;
import android.database.SQLException;

import com.iasacv.impulsora.rutainspeccion.Conexion.Crypt;
import com.iasacv.impulsora.rutainspeccion.Conexion.EntLibDBTools;

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
public class ComunDA {

    //Variables
    private EntLibDBTools _objEntLibTools;
    private Crypt _objCrypt = new Crypt();

    public ComunDA(Context context) {
        _objEntLibTools = new EntLibDBTools(context);
    }

    public boolean CheckDataBase(){
        try{
            boolean resul = _objEntLibTools.CheckDataBase();
            return resul;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void CreateDataBase() throws IOException {
        try {
            _objEntLibTools.CreateDataBase();
        } catch (IOException e) {
            throw e;
        }
    }

    public String Encrypt(String plainText) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return _objCrypt.Encrypt(plainText);
    }
}
