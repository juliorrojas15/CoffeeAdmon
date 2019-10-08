package com.example.coffeetimeadmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cierres_Adaptador_Nomina extends BaseAdapter {
    Context context;
    ArrayList<Cierres_Entidad_Nomina> originalArray;

    public Cierres_Adaptador_Nomina(Context context, ArrayList<Cierres_Entidad_Nomina> originalArray) {
        this.context = context;
        this.originalArray = originalArray;
    }

    public void remove(int position){
        originalArray.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return originalArray.get(position);
    }
    @Override
    public int getCount()    {
        return originalArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View Item=inflater.inflate(R.layout.lv_cierres,null);

        TextView otvFecha=(TextView)Item.findViewById(R.id.tvFecha_lv);
        TextView otvTazas_LV=(TextView)Item.findViewById(R.id.tvTazas_lv);
        TextView otvComision_LV=(TextView)Item.findViewById(R.id.tvComision_lv);

        DecimalFormat decimalFormat=new DecimalFormat("#,##0");

        Integer iComision=originalArray.get(position).getiComision();
        String imComision= decimalFormat.format(iComision);

        otvFecha.setText(originalArray.get(position).getsFecha());
        otvTazas_LV.setText(String.valueOf(originalArray.get(position).getiTazas()));
        otvComision_LV.setText("$ "+imComision);

        return Item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
