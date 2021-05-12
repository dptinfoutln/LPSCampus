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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ScanJsonAdapter extends BaseAdapter {
    private List<JSONObject> scanList;
    private LayoutInflater inflater;

    public ScanJsonAdapter (Context context, List<JSONObject> scanList){
        this.scanList = scanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scanList.size();
    }

    @Override
    public Object getItem(int position) {
        return scanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        TextView textView8;
        TextView textView9;
        TextView textView10;
        TextView textView11;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScanJsonAdapter.ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.scan_list, null);
            holder = new ScanJsonAdapter.ViewHolder();
            holder.textView1 = convertView.findViewById(R.id.scanBSSID);
            holder.textView2 = convertView.findViewById(R.id.scanCapabilities);
            holder.textView3 = convertView.findViewById(R.id.scanCenterFreq0);
            holder.textView4 = convertView.findViewById(R.id.scanCenterFreq1);
            holder.textView5 = convertView.findViewById(R.id.scanChannelWidth);
            holder.textView6 = convertView.findViewById(R.id.scanFrequency);
            holder.textView7 = convertView.findViewById(R.id.scanLevel);
            holder.textView8 = convertView.findViewById(R.id.scanOperatorFriendlyName);
            holder.textView9 = convertView.findViewById(R.id.scanSSID);
            holder.textView10 = convertView.findViewById(R.id.scanTimestamp);
            holder.textView11 = convertView.findViewById(R.id.scanVenueName);


            convertView.setTag(holder);
        } else{
            holder = (ScanJsonAdapter.ViewHolder)convertView.getTag();
        }

        JSONObject scanRes = scanList.get(position);

        try {
            holder.textView1.setText("BSSID: " + scanRes.get("BSSID"));
            holder.textView2.setText("Capabilities: " + scanRes.get("Capabilities"));
            holder.textView3.setText("CenterFreq0: " + scanRes.get("centerFreq0"));
            holder.textView4.setText("CenterFreq1: " + scanRes.get("centerFreq1"));
            holder.textView5.setText("ChannelWidth: " + scanRes.get("channelWidth"));
            holder.textView6.setText("Frequency: " + scanRes.get("frequency"));
            holder.textView7.setText("Level: " + scanRes.get("level"));
            holder.textView8.setText("OperatorFriendlyName: " + scanRes.get("operatorFriendlyName"));
            holder.textView9.setText("SSID: " + scanRes.get("SSID"));
            holder.textView10.setText("Timestamp: " + scanRes.get("timestamp"));
            holder.textView11.setText("VenueName: " + scanRes.get("venueName"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        int c = position%2+1;
        convertView.setBackgroundColor(Color.rgb(120*c, 120*c, 120*c));


        return convertView;
    }

    public void setScanList(List<JSONObject> scanList) {
        this.scanList = scanList;
    }
}


