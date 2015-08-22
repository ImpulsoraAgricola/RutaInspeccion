package com.iasacv.impulsora.rutainspeccion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.iasacv.impulsora.rutainspeccion.Conexion.GPSTracker;
import com.iasacv.impulsora.rutainspeccion.Servicios.WebServiceDirections;

/**
 * Created by Administrator on 19/08/2015.
 */
public class Localizacion extends android.support.v4.app.FragmentActivity {

    //Variables
    GPSTracker gpsTracker;
    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    double LatitudDestino;
    double LongitudDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
        obtenerValores();
        gpsTracker = new GPSTracker(Localizacion.this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            Localizacion();
        } else {
            final AlertDialog alert = new AlertDialog.Builder(
                    new ContextThemeWrapper(Localizacion.this, android.R.style.Theme_Dialog))
                    .create();
            alert.setTitle("Mensaje");
            alert.setMessage("El GPS no esta activado. \u00BFDesea activarlo?");
            alert.setCancelable(false);
            alert.setIcon(R.drawable.info);
            alert.setCanceledOnTouchOutside(false);
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alert.dismiss();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            Localizacion.this.startActivity(intent);
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

        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
            public boolean onMyLocationButtonClick()
            {
                Localizacion();
                return false;
            }
        });
    }

    private void Localizacion(){
        markerPoints = new ArrayList<LatLng>();
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Getting Map for the SupportMapFragment
        map = fm.getMap();
        // Configuracion del mapa
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        if (map != null) {
            //Enviar la posicion actual
            LatLng origen = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            LatLng destino = new LatLng(LatitudDestino, LongitudDestino);
            markerPoints.clear();
            map.clear();
            // Adding new item to the ArrayList
            markerPoints.add(origen);
            markerPoints.add(destino);
            // Creating MarkerOptions
            MarkerOptions markerOrigen = new MarkerOptions();
            MarkerOptions markerDestino = new MarkerOptions();
            // Setting the position of the marker
            markerOrigen.position(origen);
            markerDestino.position(destino);
            /**
             * For the start location, the color of marker is GREEN and
             * for the end location, the color of marker is RED.
             */
            markerOrigen.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            markerDestino.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            // Add new marker to the Google Map Android API V2
            map.addMarker(markerOrigen);
            map.addMarker(markerDestino);
            if(ConexionInternet()) {
                // Getting URL to the Google Directions API
                String url = getDirectionsUrl(origen, destino);
                DownloadTask downloadTask = new DownloadTask();
                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
            //Enfocar camara
            map.moveCamera(CameraUpdateFactory.newLatLng(origen));
            map.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }

    private void obtenerValores(){
        Bundle b = getIntent().getExtras();
        LatitudDestino = b.getDouble("Latitud");
        LongitudDestino = b.getDouble("Longitud");
    }

    private String getDirectionsUrl(LatLng origen, LatLng destino) {
        //Parametros
        String str_origin = "origin=" + origen.latitude + "," + origen.longitude;
        String str_dest = "destination=" + destino.latitude + "," + destino.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        // Enviar parametros a URL
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
            //Obtener distancia
            //JSONObject jsonObject = new JSONObject(sb.toString());
            //String distance = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");

        } catch (Exception e) {
            Log.d("Error descargar URL", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Leer datos de URL", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                WebServiceDirections parser = new WebServiceDirections();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            // Obteniendo todas las rutas
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }
            map.addPolyline(lineOptions);
        }
    }

    private boolean ConexionInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
