package com.example.coffeetimeadmon;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.graphics.Color.GREEN;

public class ActivityConteo extends AppCompatActivity implements View.OnClickListener{
    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_10,obTecla_20,obTecla_25,obTecla_50,obTecla_000;
    Button ob50_N,ob50_V,ob100_N,ob100_V,ob200_N,ob200_V,ob500,ob1000;
    Button ob1000b,ob2000,ob5000,ob10000,ob20000,ob50000,ob100000;

    TextView otv50_N,otv50_V,otv100_N,otv100_V,otv200_N,otv200_V,otv500,otv1000;
    TextView otv1000b,otv2000,otv5000,otv10000,otv20000,otv50000,otv100000;
    TextView otvMonedas,otvBilletes,otvDineroContado,otvDineroSistema,otvDineroDiferencia;

    Button obBorrarTodo;

    //#########################################################################################     Variables Globales
    int i50_N=0,i50_V=0,i100_N=0,i100_V=0,i200_N=0,i200_V=0,i500=0,i1000=0;
    int i1000b=0,i2000=0,i5000=0,i10000=0,i20000=0,i50000=0,i100000=0;

    int iT50_N=0,iT50_V=0,iT100_N=0,iT100_V=0,iT200_N=0,iT200_V=0,iT500=0,iT1000=0;
    int iT1000b=0,iT2000=0,iT5000=0,iT10000=0,iT20000=0,iT50000=0,iT100000=0;

    int iMonedas=0,iBilletes=0, iDineroContado=0,iDineroSistema=0,iDineroDiferencia=0;
    
    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);

        //#####################################################################################     Relación de objetos con Layout
        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_10=(Button)findViewById(R.id.bTecla_10);obTecla_20=(Button)findViewById(R.id.bTecla_20);
        obTecla_25=(Button)findViewById(R.id.bTecla_25);obTecla_50=(Button)findViewById(R.id.bTecla_50);
        obTecla_000=(Button)findViewById(R.id.bTecla_000);

        ob50_N=(Button)findViewById(R.id.b50_N);ob50_V=(Button)findViewById(R.id.b50_V);
        ob100_N=(Button)findViewById(R.id.b100_N);ob100_V=(Button)findViewById(R.id.b100_V);
        ob200_N=(Button)findViewById(R.id.b200_N);ob200_V=(Button)findViewById(R.id.b200_V);
        ob500=(Button)findViewById(R.id.b500);ob1000=(Button)findViewById(R.id.b1000);

        ob1000b=(Button)findViewById(R.id.b1000b);ob2000=(Button)findViewById(R.id.b2000);
        ob5000=(Button)findViewById(R.id.b5000);ob10000=(Button)findViewById(R.id.b10000);
        ob20000=(Button)findViewById(R.id.b20000);ob50000=(Button)findViewById(R.id.b50000);
        ob100000=(Button)findViewById(R.id.b100000);

        obBorrarTodo=(Button)findViewById(R.id.bBorrarTodo);

        otv50_N=(TextView)findViewById(R.id.tv50_N);otv50_V=(TextView)findViewById(R.id.tv50_V);
        otv100_N=(TextView)findViewById(R.id.tv100_N);otv100_V=(TextView)findViewById(R.id.tv100_V);
        otv200_N=(TextView)findViewById(R.id.tv200_N);otv200_V=(TextView)findViewById(R.id.tv200_V);
        otv500=(TextView)findViewById(R.id.tv500);otv1000=(TextView)findViewById(R.id.tv1000);

        otv1000b=(TextView)findViewById(R.id.tv1000b);otv2000=(TextView)findViewById(R.id.tv2000);
        otv5000=(TextView)findViewById(R.id.tv5000);otv10000=(TextView)findViewById(R.id.tv10000);
        otv20000=(TextView)findViewById(R.id.tv20000);otv50000=(TextView)findViewById(R.id.tv50000);
        otv100000=(TextView)findViewById(R.id.tv100000);

        otvMonedas=(TextView)findViewById(R.id.tvMonedas);
        otvBilletes=(TextView)findViewById(R.id.tvBilletes);
        otvDineroContado=(TextView)findViewById(R.id.tvDineroContado);
        otvDineroSistema=(TextView)findViewById(R.id.tvDineroSistema);
        otvDineroDiferencia=(TextView)findViewById(R.id.tvDineroDiferencia);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);obTecla_10.setOnClickListener(this);obTecla_20.setOnClickListener(this);
        obTecla_25.setOnClickListener(this);obTecla_50.setOnClickListener(this);obTecla_000.setOnClickListener(this);

        ob50_N.setOnClickListener(this);ob50_V.setOnClickListener(this);
        ob100_N.setOnClickListener(this);ob100_V.setOnClickListener(this);
        ob200_N.setOnClickListener(this);ob200_V.setOnClickListener(this);
        ob500.setOnClickListener(this);ob1000.setOnClickListener(this);

        ob1000b.setOnClickListener(this);ob2000.setOnClickListener(this);
        ob5000.setOnClickListener(this);ob10000.setOnClickListener(this);
        ob20000.setOnClickListener(this);ob50000.setOnClickListener(this);
        ob100000.setOnClickListener(this);

        //##################################################################################         Acciones iniciales
        fBorrarTodo();
        fObtenerCuentas();

        //##################################################################################         Acciones de botones
        obBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fBorrarTodo();
                fBackGround();
            }
        });
        //###################################################################################       Acciones de los ListView
    }

    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################

    //  ###########################################################################################     RESET DE PAGINA
    void fBorrarTodo(){
        
        ob50_N.setText("0");ob50_V.setText("0");
        ob100_N.setText("0");ob100_V.setText("0");
        ob200_N.setText("0");ob200_V.setText("0");
        ob500.setText("0");ob1000.setText("0");

        ob1000b.setText("0");ob2000.setText("0");
        ob5000.setText("0");ob10000.setText("0");
        ob20000.setText("0");ob50000.setText("0");
        ob100000.setText("0");

        otv50_N.setText("$ 0");otv50_V.setText("$ 0");
        otv100_N.setText("$ 0");otv100_V.setText("$ 0");
        otv200_N.setText("$ 0");otv200_V.setText("$ 0");
        otv500.setText("$ 0"); otv1000.setText("$ 0");

        otv1000b.setText("$ 0");otv2000.setText("$ 0");
        otv5000.setText("$ 0");otv10000.setText("$ 0");
        otv20000.setText("$ 0");otv50000.setText("$ 0");
        otv100000.setText("$ 0");

        otvMonedas.setText("$ 0");
        otvBilletes.setText("$ 0");
        otvDineroContado.setText("$ 0");
        otvDineroSistema.setText("$ 0");
        otvDineroDiferencia.setText("$ 0");

        i50_N=0;i50_V=0;i100_N=0;i100_V=0;i200_N=0;i200_V=0;i500=0;i1000=0;
        i1000b=0;i2000=0;i5000=0;i10000=0;i20000=0;i50000=0;i100000=0;

        iT50_N=0;iT50_V=0;iT100_N=0;iT100_V=0;iT200_N=0;iT200_V=0;iT500=0;iT1000=0;
        iT1000b=0;iT2000=0;iT5000=0;iT10000=0;iT20000=0;iT50000=0;iT100000=0;
        
        iMonedas=0;iBilletes=0;iDineroContado=0;iDineroSistema=0;iDineroDiferencia=0;
        
        iNumero=0;sNumero="";sTeclaAccionada="";

        fBackGround();
    }

    // ############################################################################################     FUNCIONAMIENTO PAGINA
    String sNumero="";
    int iNumero=0;
    String sTeclaAccionada="";
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bTecla_0:sNumero=sNumero+"0";fValores();break;
            case R.id.bTecla_1:sNumero=sNumero+"1";fValores();break;
            case R.id.bTecla_2:sNumero=sNumero+"2";fValores();break;
            case R.id.bTecla_3:sNumero=sNumero+"3";fValores();break;
            case R.id.bTecla_4:sNumero=sNumero+"4";fValores();break;
            case R.id.bTecla_5:sNumero=sNumero+"5";fValores();break;
            case R.id.bTecla_6:sNumero=sNumero+"6";fValores();break;
            case R.id.bTecla_7:sNumero=sNumero+"7";fValores();break;
            case R.id.bTecla_8:sNumero=sNumero+"8";fValores();break;
            case R.id.bTecla_9:sNumero=sNumero+"9";fValores();break;
            case R.id.bTecla_10:sNumero=sNumero+"10";fValores();break;
            case R.id.bTecla_20:sNumero=sNumero+"20";fValores();break;
            case R.id.bTecla_25:sNumero=sNumero+"25";fValores();break;
            case R.id.bTecla_50:sNumero=sNumero+"50";fValores();break;
            case R.id.bTecla_000:sNumero=sNumero+"000";fValores();break;

            case R.id.b50_N:sTeclaAccionada="b50_N";fSeleccion();break;
            case R.id.b50_V:sTeclaAccionada="b50_V";fSeleccion();break;
            case R.id.b100_N:sTeclaAccionada="b100_N";fSeleccion();break;
            case R.id.b100_V:sTeclaAccionada="b100_V";fSeleccion();break;
            case R.id.b200_N:sTeclaAccionada="b200_N";fSeleccion();break;
            case R.id.b200_V:sTeclaAccionada="b200_V";fSeleccion();break;
            case R.id.b500:sTeclaAccionada="b500";fSeleccion();break;
            case R.id.b1000:sTeclaAccionada="b1000";fSeleccion();break;

            case R.id.b1000b:sTeclaAccionada="b1000b";fSeleccion();break;
            case R.id.b2000:sTeclaAccionada="b2000";fSeleccion();break;
            case R.id.b5000:sTeclaAccionada="b5000";fSeleccion();break;
            case R.id.b10000:sTeclaAccionada="b10000";fSeleccion();break;
            case R.id.b20000:sTeclaAccionada="b20000";fSeleccion();break;
            case R.id.b50000:sTeclaAccionada="b50000";fSeleccion();break;
            case R.id.b100000:sTeclaAccionada="b100000";fSeleccion();break;
        }
    }
    void fValores(){
        if(sTeclaAccionada==""){
            Toast.makeText(ActivityConteo.this,"Selecciona una denominación!",Toast.LENGTH_SHORT).show();
            return;
        }
        iNumero=Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        switch (sTeclaAccionada){

            case "b50_N":ob50_N.setText(sNumero);i50_N=iNumero;break;
            case "b50_V":ob50_V.setText(sNumero);i50_V=iNumero;break;
            case "b100_N":ob100_N.setText(sNumero);i100_N=iNumero;break;
            case "b100_V":ob100_V.setText(sNumero);i100_V=iNumero;break;
            case "b200_N":ob200_N.setText(sNumero);i200_N=iNumero;break;
            case "b200_V":ob200_V.setText(sNumero);i200_V=iNumero;break;
            case "b500":ob500.setText(sNumero);i500=iNumero;break;
            case "b1000":ob1000.setText(sNumero);i1000=iNumero;break;

            case "b1000b":ob1000b.setText(sNumero);i1000b=iNumero;break;
            case "b2000":ob2000.setText(sNumero);i2000=iNumero;break;
            case "b5000":ob5000.setText(sNumero);i5000=iNumero;break;
            case "b10000":ob10000.setText(sNumero);i10000=iNumero;break;
            case "b20000":ob20000.setText(sNumero);i20000=iNumero;break;
            case "b50000":ob50000.setText(sNumero);i50000=iNumero;break;
            case "b100000":ob100000.setText(sNumero);i100000=iNumero;break;

        }
        fCalculos();
    }
    void fSeleccion(){
        iNumero=0;
        sNumero="";
        switch (sTeclaAccionada){

            case "b50_N":fBackGround();ob50_N.setBackgroundColor(GREEN);break;
            case "b50_V":fBackGround();ob50_V.setBackgroundColor(GREEN);break;
            case "b100_N":fBackGround();ob100_N.setBackgroundColor(GREEN);break;
            case "b100_V":fBackGround();ob100_V.setBackgroundColor(GREEN);break;
            case "b200_N":fBackGround();ob200_N.setBackgroundColor(GREEN);break;
            case "b200_V":fBackGround();ob200_V.setBackgroundColor(GREEN);break;
            case "b500":fBackGround();ob500.setBackgroundColor(GREEN);break;
            case "b1000":fBackGround();ob1000.setBackgroundColor(GREEN);break;

            case "b1000b":fBackGround();ob1000b.setBackgroundColor(GREEN);break;
            case "b2000":fBackGround();ob2000.setBackgroundColor(GREEN);break;
            case "b5000":fBackGround();ob5000.setBackgroundColor(GREEN);break;
            case "b10000":fBackGround();ob10000.setBackgroundColor(GREEN);break;
            case "b20000":fBackGround();ob20000.setBackgroundColor(GREEN);break;
            case "b50000":fBackGround();ob50000.setBackgroundColor(GREEN);break;
            case "b100000":fBackGround();ob100000.setBackgroundColor(GREEN);break;
        }
    }
    void fBackGround(){
        ob50_N.setBackgroundResource(R.drawable.boton_gris);
        ob50_V.setBackgroundResource(R.drawable.boton_gris);
        ob100_N.setBackgroundResource(R.drawable.boton_gris);
        ob100_V.setBackgroundResource(R.drawable.boton_gris);
        ob200_N.setBackgroundResource(R.drawable.boton_gris);
        ob200_V.setBackgroundResource(R.drawable.boton_gris);
        ob500.setBackgroundResource(R.drawable.boton_gris);
        ob1000.setBackgroundResource(R.drawable.boton_gris);

        ob1000b.setBackgroundResource(R.drawable.boton_gris);
        ob2000.setBackgroundResource(R.drawable.boton_gris);
        ob5000.setBackgroundResource(R.drawable.boton_gris);
        ob10000.setBackgroundResource(R.drawable.boton_gris);
        ob20000.setBackgroundResource(R.drawable.boton_gris);
        ob50000.setBackgroundResource(R.drawable.boton_gris);
        ob100000.setBackgroundResource(R.drawable.boton_gris);
    }

    // ############################################################################################  CÁLCULOS
    void fCalculos(){
        
        iT50_N=i50_N*50;
        iT50_V=i50_V*50;
        iT100_N=i100_N*100;
        iT100_V=i100_V*100;
        iT200_N=i200_N*200;
        iT200_V=i200_V*200;
        iT500=i500*500;
        iT1000=i1000*1000;

        iT1000b=i1000b*1000;
        iT2000=i2000*2000;
        iT5000=i5000*5000;
        iT10000=i10000*10000;
        iT20000=i20000*20000;
        iT50000=i50000*50000;
        iT100000=i100000*100000;

        iMonedas=iT50_N+iT50_V+iT100_N+iT100_V+iT200_N+iT200_V+iT500+iT1000;
        iBilletes=iT1000b+iT2000+iT5000+iT10000+iT20000+iT50000+iT100000;
        
        iDineroContado=iMonedas+iBilletes;
        iDineroDiferencia=iDineroContado-iDineroSistema;
        fTextoValores();

    }
    void fTextoValores(){

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        String imT50_N=decimalFormat.format(iT50_N);
        String imT50_V=decimalFormat.format(iT50_V);
        String imT100_N=decimalFormat.format(iT100_N);
        String imT100_V=decimalFormat.format(iT100_V);
        String imT200_N=decimalFormat.format(iT200_N);
        String imT200_V=decimalFormat.format(iT200_V);
        String imT500=decimalFormat.format(iT500);
        String imT1000=decimalFormat.format(iT1000);

        String imT1000b=decimalFormat.format(iT1000b);
        String imT2000=decimalFormat.format(iT2000);
        String imT5000=decimalFormat.format(iT5000);
        String imT10000=decimalFormat.format(iT10000);
        String imT20000=decimalFormat.format(iT20000);
        String imT50000=decimalFormat.format(iT50000);
        String imT100000=decimalFormat.format(iT100000);

        String imMonedas=decimalFormat.format(iMonedas);
        String imBilletes=decimalFormat.format(iBilletes);
        String imDineroContado=decimalFormat.format(iDineroContado);
        String imDineroDiferencia=decimalFormat.format(iDineroDiferencia);

        otv50_N.setText("$ "+imT50_N);
        otv50_V.setText("$ "+imT50_V);
        otv100_N.setText("$ "+imT100_N);
        otv100_V.setText("$ "+imT100_V);
        otv200_N.setText("$ "+imT200_N);
        otv200_V.setText("$ "+imT200_V);
        otv500.setText("$ "+imT500);
        otv1000.setText("$ "+imT1000);

        otv1000b.setText("$ "+imT1000b);
        otv2000.setText("$ "+imT2000);
        otv5000.setText("$ "+imT5000);
        otv10000.setText("$ "+imT10000);
        otv20000.setText("$ "+imT20000);
        otv50000.setText("$ "+imT50000);
        otv100000.setText("$ "+imT100000);

        otvMonedas.setText("$ "+imMonedas);
        otvBilletes.setText("$ "+imBilletes);
        otvDineroContado.setText("$ "+imDineroContado);
        otvDineroDiferencia.setText("$ "+imDineroDiferencia);

    }

    int iEfectivo=0,iBancos=0;
    void fObtenerCuentas() {

        //Adquirir información actual de cuentas
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/Cuentas";
        DocumentReference bd_Datos = FirebaseFirestore.getInstance().document(sPath);
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> aKeys = new ArrayList<String>(document.getData().keySet());
                        ArrayList aValues = new ArrayList(document.getData().values());

                        if (aKeys.get(0).equals("Efectivo")) {
                            iEfectivo = Integer.parseInt(aValues.get(0).toString());
                            iBancos = Integer.parseInt(aValues.get(1).toString());
                        } else {
                            iEfectivo = Integer.parseInt(aValues.get(1).toString());
                            iBancos = Integer.parseInt(aValues.get(0).toString());
                        }
                        iDineroSistema=iEfectivo;
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        String imDineroSistema=decimalFormat.format(iDineroSistema);
                        otvDineroSistema.setText("$ "+imDineroSistema);


                    } else {
                        Log.d("Distribuidor", "No such document");
                    }
                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
    }
    
}
