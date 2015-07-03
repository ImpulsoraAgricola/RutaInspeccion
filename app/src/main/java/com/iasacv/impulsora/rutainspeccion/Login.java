package com.iasacv.impulsora.rutainspeccion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.iasacv.impulsora.rutainspeccion.Modelo.Usuario;
import com.iasacv.impulsora.rutainspeccion.Negocios.ComunBP;
import com.iasacv.impulsora.rutainspeccion.Negocios.WebServiceBP;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Login extends Activity {

    //Variables controles
    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnIngresar;
    private Button btnCancelar;

    //Variables objetos
    ComunBP _objComunBP;
    WebServiceBP _objWebServiceBP;
    Usuario objUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Revisar las preferencias
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        if (prefs.getString("Clave", "") != "") {
            //Creamos el nuevo formulario
            Intent i = new Intent(Login.this, Administrador.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_login);

            //Pasar contexto a las demas instancias
            _objComunBP = new ComunBP(this);
            _objWebServiceBP = new WebServiceBP(this);

            //Obtener controles
            GetControles();

            //Revisar si existe la base de datos
            StatusDB();

            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Validar()) {
                        if (ConexionInternet()) {
                            objUsuario = new Usuario();
                            objUsuario.RFC = txtUsuario.getText().toString();
                            try {
                                objUsuario.Password = _objComunBP.Encrypt(txtPassword.getText().toString());
                                getLogin jobGetLogin = new getLogin();
                                jobGetLogin.execute();
                            } catch (NoSuchPaddingException e) {
                                e.printStackTrace();
                            } catch (InvalidAlgorithmParameterException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IllegalBlockSizeException e) {
                                e.printStackTrace();
                            } catch (BadPaddingException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            }
                        } else
                            _objComunBP.Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet", getApplicationContext());
                    }
                }
            });

            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog();
                }
            });
        }
    }

    private class getLogin extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Login.this, "Rutas de Inspecci\u00F3n", "Comprobando informaci\u00F3n...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                objUsuario = _objWebServiceBP.getLogin(objUsuario);
                if(objUsuario.Clave != 0)
                    result = _objWebServiceBP.getCatalogos(objUsuario.RFC);
                else
                    result = false;
            } catch (Exception e) {
                _objComunBP.Mensaje(e.toString(), getApplicationContext());
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                //Guardamos las preferencias
                SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Clave", String.valueOf(objUsuario.Clave));
                editor.putString("Nombre", objUsuario.Nombre.toString().replace('#', ' '));
                editor.putString("RFC", objUsuario.RFC);
                editor.putString("Email", String.valueOf(objUsuario.Email));
                editor.commit();
                //Creamos el nuevo formulario
                Intent i = new Intent(Login.this, Administrador.class);
                startActivity(i);
                finish();
            } else {
                _objComunBP.Mensaje("Error: Usuario incorrecto",getApplicationContext());
            }
            loadProgressDialog.dismiss();
        }
    }

    private void GetControles() {
        txtUsuario = (EditText) findViewById(R.id.login_txtUsuario);
        txtPassword = (EditText) findViewById(R.id.login_txtPassword);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        btnCancelar = (Button) findViewById(R.id.login_btnCancelar);
    }

    private void StatusDB() {
        try {
            if(!_objComunBP.CheckDataBase()) {
                _objComunBP.CreateDataBase();
            }
        } catch (IOException ioe) {
            throw new Error("Error: No se pudo crear la base de datos");
        }
    }

    private boolean ConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private boolean Validar() {
        boolean validar = true;
        String usuario = txtUsuario.getText().toString();
        String pass = txtPassword.getText().toString();
        if (usuario.length() == 0){
            validar=false;
            txtUsuario.requestFocus();
            txtUsuario.setError("*Favor de no dejar el campo vac\u00EDo. [Usuario]");
        }
        else {
            if (pass.length() == 0) {
                validar = false;
                txtPassword.requestFocus();
                txtPassword.setError("*Favor de no dejar el campo vac\u00EDo. [Contrase\u00F1a]");
            }
        }
        return validar;
    }

    private void confirmDialog(){
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this,android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Mensaje");
        alert.setMessage("\u00BFDeseas salir de la aplicaci\u00F3n?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.shutdown);
        alert.setCanceledOnTouchOutside(false);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        finish();
                    }
                });
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });
        alert.show();
    }
}
