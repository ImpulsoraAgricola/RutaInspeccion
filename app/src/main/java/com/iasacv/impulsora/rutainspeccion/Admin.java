package com.iasacv.impulsora.rutainspeccion;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.iasacv.impulsora.rutainspeccion.Modelo.Ciclo;
import com.iasacv.impulsora.rutainspeccion.Negocios.CicloBP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28/06/2015.
 */
public class Admin extends Fragment {

    CicloBP objCicloBP;
    private GridView gridView;
    public static ArrayList<String> ArrayofName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        objCicloBP = new CicloBP(activity);
    }

    // this Fragment will be called from MainActivity
    public Admin(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridView);

        gridView = (GridView)rootView.findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), Administrador.class);
                startActivity(i);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayofName = new ArrayList<String>();
        List<Ciclo> listaCiclos = new ArrayList<Ciclo>();
        listaCiclos = objCicloBP.GetAllCiclosList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, ArrayofName);
        gridView.setAdapter(adapter);
    }
}
