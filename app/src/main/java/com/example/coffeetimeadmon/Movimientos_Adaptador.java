package com.example.coffeetimeadmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class Movimientos_Adaptador extends BaseAdapter {
    Context context;
    ArrayList<Movimiento_Entidad> originalArray;

    public Movimientos_Adaptador(Context context, ArrayList<Movimiento_Entidad> originalArray) {
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
        View Item=inflater.inflate(R.layout.lv_movimiento,null);

        TextView otvHora_LV=(TextView)Item.findViewById(R.id.tvHora_LV);
        TextView otvMovimiento_LV=(TextView)Item.findViewById(R.id.tvMovimiento_LV);
        TextView otvTotal_LV=(TextView)Item.findViewById(R.id.tvTotal_LV);

        DecimalFormat decimalFormat=new DecimalFormat("#,##0");

        Integer iTotal=originalArray.get(position).getiTotal();
        String imTotal= decimalFormat.format(iTotal);
        String sMovimiento=originalArray.get(position).sCategoria_1+"\n"+originalArray.get(position).sCategoria_2+"\n"+
                            originalArray.get(position).sDescripción;

        otvHora_LV.setText(originalArray.get(position).getsHora());
        otvMovimiento_LV.setText(sMovimiento);
        otvTotal_LV.setText("$ "+imTotal);

        if(originalArray.get(position).sCategoria_1.contains("Ent")){
            otvHora_LV.setTextColor(GREEN);
            otvMovimiento_LV.setTextColor(GREEN);
            otvTotal_LV.setTextColor(GREEN);
        }
        if(originalArray.get(position).sCategoria_1.contains("Sal")){
            otvHora_LV.setTextColor(RED);
            otvMovimiento_LV.setTextColor(RED);
            otvTotal_LV.setTextColor(RED);
        }



        return Item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
