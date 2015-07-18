package com.iasacv.impulsora.rutainspeccion;

import android.app.ActionBar;
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
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class Login extends ActionBarActivity {

    //Variables controles
    private EditText txtUsuario;
    private EditText txtPassword;
    private ImageButton btnIngresar;

    //Variables objetos
    ComunBP _objComunBP;
    WebServiceBP _objWebServiceBP;
    Usuario objUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Revisar las preferencias
        SharedPreferences prefs = getSharedPreferences("RutaInspeccion", Context.MODE_PRIVATE);
        if (prefs.getString("Clave", "") != "") {
            //Creamos el nuevo formulario
            Intent i = new Intent(Login.this, Administrador.class);
            startActivity(i);
        } else {

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
                            _objComunBP.Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet", Login.this);
                    }
                }
            });
        }
    }

    private class getLogin extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Login.this, "Rutas de Inspecci\u00F3n", "Iniciando sesi\u00F3n. Por favor un momento...", true, false);
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
                result =false;
                _objComunBP.Mensaje(e.toString(),Login.this);
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
                _objComunBP.Mensaje("Error: Usuario y/o contrasena incorrecto",Login.this);
            }
            loadProgressDialog.dismiss();
        }
    }

    private void GetControles() {
        txtUsuario = (EditText) findViewById(R.id.login_txtUsuario);
        txtPassword = (EditText) findViewById(R.id.login_txtPassword);
        btnIngresar = (ImageButton) findViewById(R.id.login_btnIngresar);
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
        alert.setTitle("Rutas de Inspecci\u00F3n");
        alert.setMessage("\u00BFDeseas salir de la aplicaci\u00d1n?");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_login_salir:
                confirmDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alert = new AlertDialog.Builder(
                new ContextThemeWrapper(this, android.R.style.Theme_Dialog))
                .create();
        alert.setTitle("Rutas de Inspecci\u00F3n");
        alert.setMessage("\u00BFDeseas salir de la captura de la ruta de inpecci\u00F3n?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.info);
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
