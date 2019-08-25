package com.example.coffeetimeadmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Distribuidor_Adaptador_Crear extends BaseAdapter{
    Context context;
    ArrayList<Distribuidor_Entidad> originalArray;

    public Distribuidor_Adaptador_Crear(Context context, ArrayList<Distribuidor_Entidad> originalArray) {
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
        View Item=inflater.inflate(R.layout.lv_crear_distribuidores,null);

        TextView otvNombre_LV=(TextView)Item.findViewById(R.id.lv_Crear_Distr_Nombre);
        TextView otvCelular_LV=(TextView)Item.findViewById(R.id.lv_Crear_Distr_Celular);
        TextView otvAhorro_LV=(TextView)Item.findViewById(R.id.lv_Crear_Distr_Ahorro);
        TextView otvDeuda_LV=(TextView)Item.findViewById(R.id.lv_Crear_Distr_Deuda);
        TextView otvTazas_LV=(TextView)Item.findViewById(R.id.lv_Crear_Distr_Tz);


        DecimalFormat decimalFormat=new DecimalFormat("#,##0");

        Integer iAhorro=originalArray.get(position).getiAhorro();
        Integer iDeuda=originalArray.get(position).getiDeuda();

        String imAhorro= decimalFormat.format(iAhorro);
        String imDeuda= decimalFormat.format(iDeuda);

        otvNombre_LV.setText(originalArray.get(position).getsNombre());
        otvCelular_LV.setText(String.valueOf(originalArray.get(position).getlCelular()));
        otvAhorro_LV.setText("$ "+imAhorro);
        otvDeuda_LV.setText("$ "+imDeuda);
        otvTazas_LV.setText(String.valueOf(originalArray.get(position).getiTazas()));

        return Item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}

