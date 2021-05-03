package com.univtln.univTlnLPS.ihm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;

import java.util.List;

public class AdapterSalles extends BaseAdapter {
    private List<String> stringList;
    private LayoutInflater inflater;

    public AdapterSalles (Context context, List<String> stringList){
        this.stringList = stringList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView textView1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterSalles.ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.string_list, null);
            holder = new AdapterSalles.ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.stringtext1);

            convertView.setTag(holder);
        } else{
            holder = (AdapterSalles.ViewHolder)convertView.getTag();
        }

        String str = stringList.get(position);

        holder.textView1.setText(str);

        int c = position%2+1;
        convertView.setBackgroundColor(Color.rgb(120*c, 120*c, 120*c));


        return convertView;
    }

    public void setStringList(List<String> strList) {
        this.stringList = strList;
    }
}
