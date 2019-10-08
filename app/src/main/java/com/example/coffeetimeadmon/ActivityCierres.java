package com.example.coffeetimeadmon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityCierres extends AppCompatActivity {

    @Override
    protected void onResume(){
        super.onResume();
        fFiltrar();
    }



    Button obEntregaNoche,obEntregaAdicional,obEntregaDia;
    TextView otvEntregaNoche,otvEntregaAdicional,otvEntregaDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cierres);

        obEntregaNoche=(Button)findViewById(R.id.bEntregaNoche);
        obEntregaAdicional=(Button)findViewById(R.id.bEntregaAdicional);
        obEntregaDia=(Button)findViewById(R.id.bEntregaDia);
        otvEntregaDia=(TextView)findViewById(R.id.tvEntregaDia);
        otvEntregaNoche=(TextView)findViewById(R.id.tvEntregaNoche);
        otvEntregaAdicional=(TextView)findViewById(R.id.tvEntregaAdicional);



        obEntregaNoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCierres.this,ActivityEntregaNoche.class);
                startActivity(Destino);

            }
        });
        obEntregaAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCierres.this,ActivityEntregaAdicional.class);
                startActivity(Destino);

            }
        });
        obEntregaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCierres.this,ActivityEntregaDia.class);
                startActivity(Destino);

            }
        });

    }
    int iTotal=0;
    String sDescripcion,sCategoaria1;
    int iEntregaDia =0, iEntregaNoche =0, iEntregaAdicional =0;

    void fFiltrar(){

        iTotal=0;
        iEntregaDia =0;
        iEntregaNoche =0;
        iEntregaAdicional =0;

        final ArrayList alDatosCalendario=fCalendario();
        final String sMesColeccion=alDatosCalendario.get(0)+"-"+alDatosCalendario.get(4);
        final String sFechaDocumento=alDatosCalendario.get(2)+"-"+alDatosCalendario.get(3);

        final ProgressDialog progressDialog=new ProgressDialog(ActivityCierres.this);
        progressDialog.setMessage("Cargando Dineros recogidos");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/Movimientos/"+
                sMesColeccion+"/"+sFechaDocumento+"/Movimientos";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList aKeys = new ArrayList(document.getData().keySet());
                        ArrayList aDatos = new ArrayList(document.getData().values());
                        for (int i = 0; i < aKeys.size(); i++) {
                            switch (aKeys.get(i).toString()) {
                                case "Categoria_1":sCategoaria1=aDatos.get(i).toString();break;
                                case "Descripción":sDescripcion=aDatos.get(i).toString();break;
                                case "Total":iTotal=Integer.parseInt(aDatos.get(i).toString());break;
                            }
                        }
                        if (sCategoaria1.contains("Ent. por Distribuidor")){
                            if (sDescripcion.contains("Día")){
                                iEntregaDia = iEntregaDia +iTotal;
                            }
                            if (sDescripcion.contains("Noche")){
                                iEntregaNoche = iEntregaNoche +iTotal;
                            }
                            if (sDescripcion.contains("Adicional")){
                                iEntregaAdicional = iEntregaAdicional +iTotal;
                            }
                        }
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    String imEntragaDia,imEntregaNoche,imEntregaAdicional;
                    imEntragaDia = decimalFormat.format(iEntregaDia);
                    imEntregaNoche = decimalFormat.format(iEntregaNoche);
                    imEntregaAdicional = decimalFormat.format(iEntregaAdicional);


                    otvEntregaDia.setText("$ "+imEntragaDia);
                    otvEntregaNoche.setText("$ "+imEntregaNoche);
                    otvEntregaAdicional.setText("$ "+imEntregaAdicional);
                    progressDialog.cancel();
                }else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }

            }
        });



    }

    ArrayList fCalendario(){

        ArrayList alDatosCalendario=new ArrayList();

        Calendar Date=Calendar.getInstance();
        int iAño= Date.get(Calendar.YEAR);
        int iMes=Date.get(Calendar.MONTH)+1;
        int iFecha=Date.get(Calendar.DAY_OF_MONTH);
        int iDia=Date.get(Calendar.DAY_OF_WEEK);
        String sDia="",sMes="";
        switch (iDia){
            case 1:sDia="Dom";break;
            case 2:sDia="Lun";break;
            case 3:sDia="Mar";break;
            case 4:sDia="Mie";break;
            case 5:sDia="Jue";break;
            case 6:sDia="Vie";break;
            case 7:sDia="Sab";break;
        }
        switch (iMes){
            case 1:sMes="Ene";break;
            case 2:sMes="Feb";break;
            case 3:sMes="Mar";break;
            case 4:sMes="Abr";break;
            case 5:sMes="May";break;
            case 6:sMes="Jun";break;
            case 7:sMes="Jul";break;
            case 8:sMes="Ago";break;
            case 9:sMes="Sep";break;
            case 10:sMes="Oct";break;
            case 11:sMes="Nov";break;
            case 12:sMes="Dic";break;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("HHmmddssMMyy");
        String sHora=sdf.format(Date.getTime());
        String sHoraSeg=sdf.format(Date.getTime());
        String sTodo=sdf2.format(Date.getTime());
        String sFecha=sDia +" "+ iFecha +" de "+ sMes+ " de " + iAño;

        alDatosCalendario.add(iAño);
        alDatosCalendario.add(iMes);
        alDatosCalendario.add(iFecha);
        alDatosCalendario.add(sDia);
        alDatosCalendario.add(sMes);
        alDatosCalendario.add(sHora);
        alDatosCalendario.add(sFecha);
        alDatosCalendario.add(sTodo);
        alDatosCalendario.add(sHoraSeg);
        return(alDatosCalendario);
    }


}
