package com.example.coffeetimeadmon;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Distribuidor_Adaptador_Entregas extends BaseAdapter {
    Context context;
    String sHoy;
    String sTurno;
    ArrayList<Distribuidor_Entidad> originalArray;

    public Distribuidor_Adaptador_Entregas(Context context, ArrayList<Distribuidor_Entidad> originalArray,String sHoy,String sTurno) {
        this.context = context;
        this.originalArray = originalArray;
        this.sHoy=sHoy;
        this.sTurno=sTurno;


    }

    public String getsHoy() {
        return sHoy;
    }

    public void setsHoy(String sHoy) {
        this.sHoy = sHoy;
    }

    public String getsTurno() {
        return sTurno;
    }

    public void setsTurno(String sTurno) {
        this.sTurno = sTurno;
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
        View Item=inflater.inflate(R.layout.lv_distribuidores_entregas,null);

        TextView otvDistribuidor_lv=(TextView)Item.findViewById(R.id.tv_Distribuidor_lv);
        ImageView oiEntregado_LV=(ImageView)Item.findViewById(R.id.i_Entregado_lv);
        if (sTurno.equals("DIA")){
            if (originalArray.get(position).getsUltimoCierre().contains(sHoy)){
                oiEntregado_LV.setImageResource(R.mipmap.verificadosinfondo);
            }
        }
        else{
            if (originalArray.get(position).getiEntDinero()>0){
                oiEntregado_LV.setImageResource(R.mipmap.verificadosinfondo);
            }
        }




        otvDistribuidor_lv.setText(originalArray.get(position).getsNombre());

        return Item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}

