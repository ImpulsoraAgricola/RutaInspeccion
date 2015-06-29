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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    //Variable controles
    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnIngresar;
    private Button btnCancelar;

    //Variables
    ComunBP _objComunBP;
    WebServiceBP _objWebServiceBP;
    Usuario objUsuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        objUsuario.RFC = txtUsuario.getText().toString();
                        try {
                            objUsuario.Password = _objComunBP.Encrypt(txtPassword.getText().toString());
                            getLogin tarea = new getLogin();
                            tarea.execute();
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
                        Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet");
                }
            }
        });

        txtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtUsuario.getText().toString().length() == 0){
                    txtUsuario.setError("*Favor de no dejar el campo vac\u00EDo. [Usuario]");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtUsuario.setError(null);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
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
            case R.id.menu_login_actualizar:
                //Revisar conexion a Internet
                if (ConexionInternet()) {
                    getCatalogos tarea = new getCatalogos();
                    tarea.execute();
                } else
                    Mensaje("Error: Se debe contar con una conexi\u00F3n a Internet");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class getCatalogos extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Login.this, "", "Actualizando informaci\u00F3n...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                result = _objWebServiceBP.getCatalogos();
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result)
                Mensaje("La informaci\u00F3n se actualizo correctamente!!");
            else
                Mensaje("Error: Se produjo un error al actualizar la informaci\u00F3n");
            loadProgressDialog.dismiss();
        }
    }

    private class getLogin extends AsyncTask<String, Integer, Boolean> {
        //Variables
        ProgressDialog loadProgressDialog;

        @Override
        protected void onPreExecute() {
            loadProgressDialog = ProgressDialog.show(Login.this, "", "Comprobando informaci\u00F3n...", true, false);
        }

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                objUsuario = _objWebServiceBP.getLogin(objUsuario);
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
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
                Intent i = new Intent(Login.this, MenuPrincipal.class);
                startActivity(i);
            } else {
                Mensaje("Error: Usuario incorrecto");
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
                Mensaje("Favor de actualizar la informaci\u00F3n de usuarios");
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

    private void Mensaje(String mensaje) {
        Toast toastCorrecto = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toastCorrecto.show();
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
