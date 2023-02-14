package com.example.bluedoll.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluedoll.R;
import com.example.bluedoll.model.imageDoll;

import java.util.ArrayList;


public class SpinnerAdapter extends ArrayAdapter<imageDoll> {
    public SpinnerAdapter(Context context, ArrayList<imageDoll> imageDolls) {
        super(context, 0, imageDolls);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_value_layout,parent,false
            );
        }
        imageDoll selecteddoll = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.text_view_doll);
        textView.setText(selecteddoll.getName());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_doll);
        imageView.setImageResource(selecteddoll.getImage());

        return convertView;
    }
}
