package com.example.coffeetimeadmon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Productos_Adaptador_Crear extends BaseAdapter {
    Context context;
    ArrayList<Productos_Entidad> originalArray;

    public Productos_Adaptador_Crear(Context context, ArrayList<Productos_Entidad> originalArray) {
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
        View Item=inflater.inflate(R.layout.lv_crear_productos,null);

        TextView otvItem_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Item);
        TextView otvNombre_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Nombre);
        TextView otvPrecio_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Precio);
        TextView otvComision_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Comision);
        TextView otvCosto_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Costo);
        TextView otvCantidad_LV=(TextView)Item.findViewById(R.id.lv_Crear_Producto_Cantidad);


        DecimalFormat decimalFormat=new DecimalFormat("#,##0");

        Integer iPrecio=originalArray.get(position).getiPrecio();
        Integer iComision=originalArray.get(position).getiComision();
        Integer iCosto=originalArray.get(position).getiCosto();

        String imPrecio= decimalFormat.format(iPrecio);
        String imComision= decimalFormat.format(iComision);
        String imCosto= decimalFormat.format(iCosto);


        otvItem_LV.setText(String.valueOf(originalArray.get(position).getiItem()));
        otvNombre_LV.setText(originalArray.get(position).getsNombre());
        otvPrecio_LV.setText("$ "+imPrecio);
        otvComision_LV.setText("$ "+imComision);
        otvCosto_LV.setText("$ "+imCosto);
        otvCantidad_LV.setText(String.valueOf(originalArray.get(position).getiCantidad()));

        return Item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}


