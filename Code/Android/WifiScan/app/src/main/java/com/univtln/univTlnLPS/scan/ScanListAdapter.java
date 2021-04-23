package com.univtln.univTlnLPS.scan;

import com.univtln.univTlnLPS.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScanListAdapter extends BaseAdapter{
    private List<ScanResult> scanList;
    private LayoutInflater inflater;

    public ScanListAdapter (Context context, List<ScanResult> scanList){
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
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.scan_list, null);
            holder = new ViewHolder();
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
            holder = (ViewHolder)convertView.getTag();
        }

        ScanResult scanRes = scanList.get(position);

        holder.textView1.setText("BSSID: " + scanRes.BSSID);
        holder.textView2.setText("Capabilities: " + scanRes.capabilities);
        holder.textView3.setText("CenterFreq0: " + scanRes.centerFreq0);
        holder.textView4.setText("CenterFreq1: " + scanRes.centerFreq1);
        holder.textView5.setText("ChannelWidth: " + scanRes.channelWidth);
        holder.textView6.setText("Frequency: " + scanRes.frequency);
        holder.textView7.setText("Level: " + scanRes.level);
        holder.textView8.setText("OperatorFriendlyName: " + scanRes.operatorFriendlyName);
        holder.textView9.setText("SSID: " + scanRes.SSID);
        holder.textView10.setText("Timestamp: " + scanRes.timestamp);
        holder.textView11.setText("VenueName: " + scanRes.venueName);

        int c = position%2+1;
        convertView.setBackgroundColor(Color.rgb(120*c, 120*c, 120*c));


        return convertView;
    }

    public void setScanList(List<ScanResult> scanList) {
        this.scanList = scanList;
    }
}


