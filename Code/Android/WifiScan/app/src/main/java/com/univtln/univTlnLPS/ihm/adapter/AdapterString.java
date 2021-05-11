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

public class AdapterString extends BaseAdapter {
    private List<String> stringList;
    private LayoutInflater inflater;

    private int itemSelected;

    public AdapterString(Context context, List<String> stringList){
        this.stringList = stringList;
        inflater = LayoutInflater.from(context);
        itemSelected = -1;
    }

    public void setItemSelected(int id) {
        itemSelected = id;
    }

    public int getItemSelected() {
        return itemSelected;
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
        AdapterString.ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.string_list, null);
            holder = new AdapterString.ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.stringtext1);

            convertView.setTag(holder);
        } else{
            holder = (AdapterString.ViewHolder)convertView.getTag();
        }

        String str = stringList.get(position);

        holder.textView1.setText(str);

        if (position == itemSelected) {
            convertView.setBackgroundColor(Color.rgb(0, 181, 214));
        }
        else{
            int c = position%2+1;
            convertView.setBackgroundColor(Color.rgb(120*c, 120*c, 120*c));
        }


        return convertView;
    }

    public void setStringList(List<String> strList) {
        this.stringList = strList;
    }
}
