package com.example.bhavikvashi.quickreportapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Event> {
    public EarthquakeAdapter(Context context, ArrayList<Event> arrayList)
    {
        super(context,0,arrayList);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Event n1=getItem(position);

        if(convertView==null)
        {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.event_layout, parent, false);
        }

        TextView mag=(TextView)convertView.findViewById(R.id.mag);
        TextView palce=(TextView)convertView.findViewById(R.id.place);
        mag.setText(n1.mag);
        palce.setText(n1.city);

        return convertView;
    }
}

