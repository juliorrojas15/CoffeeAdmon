package com.example.coffeetimeadmon;

import android.app.ProgressDialog;
import android.arch.core.executor.TaskExecutor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ActivityInforme extends AppCompatActivity {

    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    EditText oetMes,oetAño;
    Button obFiltrar;
    TextView otvFacturado,otvRecogido,otvComisiones,otvTazasTotal,otvTortasTotal,
        otvTintos,otvAromáticas,otvInstacrem,otvMantecada,otvLiberal,otvAlmojabana,
        otvArepa,otvMustang,otvLucky;

    //#########################################################################################     Variables Globales

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);
        //#####################################################################################     Relación de objetos con Layout
        oetMes=(EditText)findViewById(R.id.etMes);
        oetAño=(EditText)findViewById(R.id.etAño);
        obFiltrar=(Button)findViewById(R.id.bFiltrar);
        otvFacturado=(TextView)findViewById(R.id.tvFacturado);
        otvRecogido=(TextView)findViewById(R.id.tvRecogido);
        otvComisiones=(TextView)findViewById(R.id.tvComisiones);
        otvTazasTotal=(TextView)findViewById(R.id.tvTazasTotal);
        otvTortasTotal=(TextView)findViewById(R.id.tvTortasTotal);
        otvTintos=(TextView)findViewById(R.id.tvTintos);
        otvAromáticas=(TextView)findViewById(R.id.tvAromáticas);
        otvInstacrem=(TextView)findViewById(R.id.tvInstacrem);
        otvMantecada=(TextView)findViewById(R.id.tvMantecada);
        otvLiberal=(TextView)findViewById(R.id.tvLiberal);
        otvAlmojabana=(TextView)findViewById(R.id.tvAlmojabana);
        otvArepa=(TextView)findViewById(R.id.tvArepa);
        otvMustang=(TextView)findViewById(R.id.tvMustang);
        otvLucky=(TextView)findViewById(R.id.tvLucky);

        //##################################################################################         Acciones iniciales

        //##################################################################################         Acciones de botones
        
        obFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMes=oetMes.getText().toString();
                String sAño=oetAño.getText().toString();
                
                if (sMes.equals("") || sAño.equals("")){
                    Toast.makeText(ActivityInforme.this,"Escribe un Mes y un Año",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Integer.parseInt(sMes)>12||Integer.parseInt(sMes)<=0){
                        Toast.makeText(ActivityInforme.this,"Escribe un Mes correcto",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(Integer.parseInt(sAño)>2025||Integer.parseInt(sAño)<=2018){
                            Toast.makeText(ActivityInforme.this,"Escribe un Año correcto",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            fFiltrar(sAño,sMes);
                        }
                    }
                }
            }
        });
    }

    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################
    int iFacturado=0,iRecogido=0,iComisiones=0,iTazasTotal=0,iTortasTotal=0,
            iTintos=0,iAromáticas=0,iInstacrem=0,iMantecada=0,iLiberal=0,iAlmojabana=0,
            iArepa=0,iMustang=0,iLucky=0;
    ArrayList listValores_Keys = new ArrayList();

    void fFiltrar(String sAño,String sMes){
        final String sFechaFiltro=sAño+"-"+sMes;
        
        final ProgressDialog progressDialog=new ProgressDialog(ActivityInforme.this);
        progressDialog.setMessage("Cargando Informe");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cierres";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int iRegistros=0;
                if (task.isSuccessful()) {
                     for (QueryDocumentSnapshot document : task.getResult()) {
                        String sNombreDoc = document.getId();
                        if (sNombreDoc.contains(sFechaFiltro)) {
                            ArrayList aKeys = new ArrayList(document.getData().keySet());
                            ArrayList aDatos = new ArrayList(document.getData().values());
                            for (int i = 0; i < aKeys.size(); i++) {
                                switch (aKeys.get(i).toString()) {
                                    case "ProducidoTotal": iFacturado=iFacturado+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "AbonadoTotal":iRecogido=iRecogido+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "ComisionTotal":iComisiones=iComisiones+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "TazasTotal":iTazasTotal=iTazasTotal+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "TortasTotal":iTortasTotal=iTortasTotal+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenTinto":iTintos=iTintos+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenAromatica":iAromáticas=iAromáticas+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenInstacrem":iInstacrem=iInstacrem+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenMantecada":iMantecada=iMantecada+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenLiberal":iLiberal=iLiberal+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenAlmojabana":iAlmojabana=iAlmojabana+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenArepa":iArepa=iArepa+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenMustang":iMustang=iMustang+Integer.parseInt(aDatos.get(i).toString());break;
                                    case "VenLucky":iLucky=iLucky+Integer.parseInt(aDatos.get(i).toString());break;
                                }
                            }
                            iRegistros++;
                        }
                    }

                    if (iFacturado>0){
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        String imFacturado,imRecogido,imComisiones,imTazasTotal,imTortasTotal,
                                imTintos,imAromáticas,imInstacrem,imMantecada,imLiberal,imAlmojabana,
                                imArepa,imMustang,imLucky;
                        imFacturado = decimalFormat.format(iFacturado);
                        imRecogido = decimalFormat.format(iRecogido);
                        imComisiones = decimalFormat.format(iComisiones);
                        imTazasTotal=decimalFormat.format(iTazasTotal);
                        imTortasTotal=decimalFormat.format(iTortasTotal);
                        imTintos=decimalFormat.format(iTintos);
                        imAromáticas=decimalFormat.format(iAromáticas);
                        imInstacrem=decimalFormat.format(iInstacrem);
                        imMantecada=decimalFormat.format(iMantecada);
                        imLiberal=decimalFormat.format(iLiberal);
                        imAlmojabana=decimalFormat.format(iAlmojabana);
                        imArepa=decimalFormat.format(iArepa);
                        imMustang=decimalFormat.format(iMustang);
                        imLucky=decimalFormat.format(iLucky);
                        
                        
                        otvFacturado.setText("$ "+imFacturado);
                        otvRecogido.setText("$ "+imRecogido);
                        otvComisiones.setText("$ "+imComisiones);
                        otvTazasTotal.setText(String.valueOf(imTazasTotal));
                        otvTortasTotal.setText(String.valueOf(imTortasTotal));
                        otvTintos.setText(String.valueOf(imTintos));
                        otvAromáticas.setText(String.valueOf(imAromáticas));
                        otvInstacrem.setText(String.valueOf(imInstacrem));
                        otvMantecada.setText(String.valueOf(imMantecada));
                        otvLiberal.setText(String.valueOf(imLiberal));
                        otvAlmojabana.setText(String.valueOf(imAlmojabana));
                        otvArepa.setText(String.valueOf(imArepa));
                        otvMustang.setText(String.valueOf(imMustang));
                        otvLucky.setText(String.valueOf(imLucky));

                        progressDialog.cancel();
                    }
                    else{
                        progressDialog.cancel();
                        Toast.makeText(ActivityInforme.this,"No hay informe para este mes",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }

            }
        });
        
        
        
    }
    
    
}
