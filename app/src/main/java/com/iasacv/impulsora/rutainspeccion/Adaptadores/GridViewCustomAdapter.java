package com.iasacv.impulsora.rutainspeccion.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iasacv.impulsora.rutainspeccion.R;

/**
 * Created by Administrator on 30/06/2015.
 */
public class GridViewCustomAdapter extends ArrayAdapter {
    Context context;

    public GridViewCustomAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    public int getCount() {
        return 24;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_row, parent, false);
            TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
            ImageView imageViewIte = (ImageView) row.findViewById(R.id.imageView);
        }
        return row;
    }
}
