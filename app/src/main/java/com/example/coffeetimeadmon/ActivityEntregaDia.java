package com.example.coffeetimeadmon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.TRANSPARENT;
import static java.lang.Boolean.TRUE;

public class ActivityEntregaDia extends AppCompatActivity implements View.OnClickListener{
    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    ListView olvDistribuidores;
    TextView otvDistribuidorSeleccionado;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_10,obTecla_20,obTecla_25,obTecla_50,obTecla_000;
    Button obEntVasosHoy,obEntAromatica,obEntInstacrem,obEntMantecada,
            obEntLiberal,obEntAlmojabana,obEntArepa,obEntMustang,obEntLucky;
    Button obDevVasosHoy,obDevAromatica,obDevInstacrem,obDevMantecada,
            obDevLiberal,obDevAlmojabana,obDevArepa,obDevMustang,obDevLucky;
    Button obDineroDia,obDineroMulta,obDineroComisionAdd;

    TextView otvEntVasosPrevios,otvDevVasosPrevios,otvVentaVasosPrevios,otvVentaVasosHoy,otvVentaAromatica,otvVentaInstacrem,
            otvVentaMantecada,otvVentaLiberal,otvVentaAlmojabana,otvVentaArepa,
            otvVentaMustang,otvVentaLucky;
    TextView otvDineroPrevio,otvComisionTotal,otvProducidoTotal,otvAbonadoTotal,otvDeudaTotal,
            otvTazasTotal,otvTortasTotal;
    
    Button obBorrarUno,obTemporal,obCerrar,obBorrarTodo,obConfirmar;

    //#########################################################################################     Variables Globales
    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;

    int iEntVasosPrevios=0,iEntVasosHoy=0,iEntAromatica=0,iEntInstacrem=0,iEntMantecada=0,
            iEntLiberal=0,iEntAlmojabana=0,iEntArepa=0,iEntMustang=0,iEntLucky=0;
    int iDevVasosPrevios=0,iDevVasosHoy=0,iDevAromatica=0,iDevInstacrem=0,iDevMantecada=0,
            iDevLiberal=0,iDevAlmojabana=0,iDevArepa=0,iDevMustang=0,iDevLucky=0;
    int iVenVasosPrevios=0,iVenVasosHoy=0,iVenAromatica=0,iVenInstacrem=0,iVenMantecada=0,
            iVenLiberal=0,iVenAlmojabana=0,iVenArepa=0,iVenMustang=0,iVenLucky=0,iVenTinto;
    int iDineroPrevio=0,iDineroDia=0,iDineroMulta=0,iDineroComisionAdd=0;
    int iComisionTotal=0,iProducidoTotal=0,iAbonadoTotal,iDeudaTotal,iTazasTotal,iTortasTotal;

    int iComisionTinto,iComisionAromatica,iComisionInstacrem;
    int iComisionMantecada,iComisionLiberal,iComisionAlmojabana,iComisionArepa;
    int iComisionMustang,iComisionLucky;
    int iProducidoTinto,iProducidoAromatica,iProducidoInstacrem;
    int iProducidoMantecada,iProducidoLiberal,iProducidoAlmojabana,iProducidoArepa;
    int iProducidoMustang,iProducidoLucky;

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_dia);
        //#####################################################################################     Relación de objetos con Layout
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);
        otvDistribuidorSeleccionado=(TextView)findViewById(R.id.tvDistribuidorSeleccionado);
        otvDineroPrevio=(TextView)findViewById(R.id.tvDineroPrevio);

        otvEntVasosPrevios=(TextView)findViewById(R.id.tvEntVasosPrevios);
        otvDevVasosPrevios=(TextView)findViewById(R.id.tvDevVasosPrevios);

        otvVentaVasosPrevios=(TextView)findViewById(R.id.tvVentaVasosPrevios);
        otvVentaVasosHoy=(TextView)findViewById(R.id.tvVentaVasosHoy);
        otvVentaAromatica=(TextView)findViewById(R.id.tvVentaAromatica);
        otvVentaInstacrem=(TextView)findViewById(R.id.tvVentaInstacrem);
        otvVentaMantecada=(TextView)findViewById(R.id.tvVentaMantecada);
        otvVentaLiberal=(TextView)findViewById(R.id.tvVentaLiberal);
        otvVentaAlmojabana=(TextView)findViewById(R.id.tvVentaAlmojabana);
        otvVentaArepa=(TextView)findViewById(R.id.tvVentaArepa);
        otvVentaMustang=(TextView)findViewById(R.id.tvVentaMustang);
        otvVentaLucky=(TextView)findViewById(R.id.tvVentaLucky);

        otvComisionTotal=(TextView)findViewById(R.id.tvComisionTotal);
        otvProducidoTotal=(TextView)findViewById(R.id.tvProducidoTotal);
        otvAbonadoTotal=(TextView)findViewById(R.id.tvAbonadoTotal);
        otvDeudaTotal=(TextView)findViewById(R.id.tvDeudaTotal);
        otvTazasTotal=(TextView)findViewById(R.id.tvTazasTotal);
        otvTortasTotal=(TextView)findViewById(R.id.tvTortasTotal);

        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_10=(Button)findViewById(R.id.bTecla_10);obTecla_20=(Button)findViewById(R.id.bTecla_20);
        obTecla_25=(Button)findViewById(R.id.bTecla_25);obTecla_50=(Button)findViewById(R.id.bTecla_50);
        obTecla_000=(Button)findViewById(R.id.bTecla_000);

        obEntVasosHoy=(Button)findViewById(R.id.bEntVasosHoy);
        obEntAromatica=(Button)findViewById(R.id.bEntAromatica);obEntInstacrem=(Button)findViewById(R.id.bEntInstacrem);
        obEntMantecada=(Button)findViewById(R.id.bEntMantecada);obEntLiberal=(Button)findViewById(R.id.bEntLiberal);
        obEntAlmojabana=(Button)findViewById(R.id.bEntAlmojabana);obEntArepa=(Button)findViewById(R.id.bEntArepa);
        obEntMustang=(Button)findViewById(R.id.bEntMustang);obEntLucky=(Button)findViewById(R.id.bEntLucky);

        obDevVasosHoy=(Button)findViewById(R.id.bDevVasosHoy);
        obDevAromatica=(Button)findViewById(R.id.bDevAromatica);obDevInstacrem=(Button)findViewById(R.id.bDevInstacrem);
        obDevMantecada=(Button)findViewById(R.id.bDevMantecada);obDevLiberal=(Button)findViewById(R.id.bDevLiberal);
        obDevAlmojabana=(Button)findViewById(R.id.bDevAlmojabana);obDevArepa=(Button)findViewById(R.id.bDevArepa);

        obDineroDia=(Button)findViewById(R.id.bDineroDia);
        obDineroMulta=(Button)findViewById(R.id.bDineroMulta);
        obDineroComisionAdd=(Button)findViewById(R.id.bDineroComisionAdd);

        obBorrarUno=(Button)findViewById(R.id.bBorrarUno);
        obTemporal=(Button)findViewById(R.id.bTemporal);
        obCerrar=(Button)findViewById(R.id.bCerrar);
        obBorrarTodo=(Button)findViewById(R.id.bBorrarTodo);
        obConfirmar=(Button)findViewById(R.id.bConfirmar);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);obTecla_10.setOnClickListener(this);obTecla_20.setOnClickListener(this);
        obTecla_25.setOnClickListener(this);obTecla_50.setOnClickListener(this);obTecla_000.setOnClickListener(this);

        obEntVasosHoy.setOnClickListener(this);
        obEntAromatica.setOnClickListener(this);obEntInstacrem.setOnClickListener(this);
        obEntMantecada.setOnClickListener(this);obEntLiberal.setOnClickListener(this);
        obEntAlmojabana.setOnClickListener(this);obEntArepa.setOnClickListener(this);
        obEntMustang.setOnClickListener(this);obEntLucky.setOnClickListener(this);

        obDevVasosHoy.setOnClickListener(this);
        obDevAromatica.setOnClickListener(this);obDevInstacrem.setOnClickListener(this);
        obDevMantecada.setOnClickListener(this);obDevLiberal.setOnClickListener(this);
        obDevAlmojabana.setOnClickListener(this);obDevArepa.setOnClickListener(this);

        obDineroDia.setOnClickListener(this);
        obDineroMulta.setOnClickListener(this);obDineroComisionAdd.setOnClickListener(this);


        //##################################################################################         Acciones iniciales

        fActualizar_LV_Distribuidores();
        fActualizar_Datos_Productos();
        fBorrarTodo();
        obConfirmar.setVisibility(View.INVISIBLE);
        //##################################################################################         Acciones de botones
        obCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obConfirmar.setVisibility(View.VISIBLE);
                obCerrar.setVisibility(View.INVISIBLE);
            }
        });
        obConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fVerificarCondiciones("Cerrado");
                //Dentro de Verificar, luego va a fAdquirirEntregados();
                //Dentro de adquirir, luego va a fGuardar();
                obConfirmar.setVisibility(View.INVISIBLE);
                obCerrar.setVisibility(View.VISIBLE);
            }
        });

        obBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obConfirmar.setVisibility(View.INVISIBLE);
                fBorrarTodo();
                fBackGround();
                obCerrar.setVisibility(View.VISIBLE);
                obConfirmar.setVisibility(View.INVISIBLE);
            }
        });
        obBorrarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (sTeclaAccionada){
                    case "EntVasosHoy":iEntVasosHoy=0;obEntVasosHoy.setText("0");break;
                    case "EntAromatica":iEntAromatica=0;obEntAromatica.setText("0");break;
                    case "EntInstacrem":iEntInstacrem=0;obEntInstacrem.setText("0");break;
                    case "EntMantecada":iEntMantecada=0;obEntMantecada.setText("0");break;
                    case "EntLiberal":iEntLiberal=0;obEntLiberal.setText("0");break;
                    case "EntAlmojabana":iEntAlmojabana=0;obEntAlmojabana.setText("0");break;
                    case "EntArepa":iEntArepa=0;obEntArepa.setText("0");break;
                    case "EntMustang":iEntMustang=0;obEntMustang.setText("0");break;
                    case "EntLucky":iEntLucky=0;obEntLucky.setText("0");break;

                    case "DevVasosHoy":iDevVasosHoy=0;obDevVasosHoy.setText("0");break;
                    case "DevAromatica":iDevAromatica=0;obDevAromatica.setText("0");break;
                    case "DevInstacrem":iDevInstacrem=0;obDevInstacrem.setText("0");break;
                    case "DevMantecada":iDevMantecada=0;obDevMantecada.setText("0");break;
                    case "DevLiberal":iDevLiberal=0;obDevLiberal.setText("0");break;
                    case "DevAlmojabana":iDevAlmojabana=0;obDevAlmojabana.setText("0");break;
                    case "DevArepa":iDevArepa=0;obDevArepa.setText("0");break;
                    case "DevMustang":iDevMustang=0;obDevMustang.setText("0");break;
                    case "DevLucky":iDevLucky=0;obDevLucky.setText("0");break;

                    case "DineroDia":iDineroDia=0;obDineroDia.setText("0");break;
                    case "DineroMulta":iDineroMulta=0;obDineroMulta.setText("0");break;
                    case "DineroComisionAdd":iDineroComisionAdd=0;obDineroComisionAdd.setText("0");break; 
                }
                iNumero=0;
                sNumero="";
                fCalculos();
            }

        });

        obTemporal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fVerificarCondiciones("Abierto");
            }
        });


        //###################################################################################       Acciones de los ListView
        olvDistribuidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView otvNombre=(TextView) view.findViewById(R.id.tv_Distribuidor_lv);
                sDistribuidorSeleccionado=otvNombre.getText().toString();

                otvDistribuidorSeleccionado.setText(sDistribuidorSeleccionado.toUpperCase());
                fAdquirirDatosDistribuidor();
            }
        });

    }
    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################

    //  ###########################################################################################     RESET DE PAGINA
    void fBorrarTodo(){
        otvDistribuidorSeleccionado.setText("");
        sDistribuidorSeleccionado="";

        otvVentaVasosPrevios.setText("0");otvVentaVasosHoy.setText("0");
        otvVentaAromatica.setText("0");otvVentaInstacrem.setText("0");otvVentaMantecada.setText("0");
        otvVentaLiberal.setText("0");otvVentaAlmojabana.setText("0");otvVentaArepa.setText("0");
        otvVentaMustang.setText("0");otvVentaLucky.setText("0");

        otvDevVasosPrevios.setText("0");obDevVasosHoy.setText("0");
        obDevAromatica.setText("0");obDevInstacrem.setText("0");obDevMantecada.setText("0");
        obDevLiberal.setText("0");obDevAlmojabana.setText("0");obDevArepa.setText("0");

        otvEntVasosPrevios.setText("0");obEntVasosHoy.setText("0");
        obEntAromatica.setText("0");obEntInstacrem.setText("0");obEntMantecada.setText("0");
        obEntLiberal.setText("0");obEntAlmojabana.setText("0");obEntArepa.setText("0");
        obEntMustang.setText("0");obEntLucky.setText("0");

        otvDineroPrevio.setText("$ 0");obDineroDia.setText("$ 0");
        obDineroMulta.setText("$ 0");obDineroComisionAdd.setText("$ 0");

        otvComisionTotal.setText("Comisión:");otvProducidoTotal.setText("Producido:");
        otvAbonadoTotal.setText("Abonado:");otvDeudaTotal.setText("Deuda:");
        otvTazasTotal.setText("Tazas:");otvTortasTotal.setText("Tortas:");
        
        iEntVasosPrevios=0;iEntVasosHoy=0;iEntAromatica=0;iEntInstacrem=0;iEntMantecada=0;
        iEntLiberal=0;iEntAlmojabana=0;iEntArepa=0;iEntMustang=0;iEntLucky=0;
        iDevVasosPrevios=0;iDevVasosHoy=0;iDevAromatica=0;iDevInstacrem=0;iDevMantecada=0;
        iDevLiberal=0;iDevAlmojabana=0;iDevArepa=0;iDevMustang=0;iDevLucky=0;
        iVenVasosPrevios=0;iVenVasosHoy=0;iVenAromatica=0;iVenInstacrem=0;iVenMantecada=0;
        iVenLiberal=0;iVenAlmojabana=0;iVenArepa=0;iVenMustang=0;iVenLucky=0;iVenTinto=0;
        iDineroPrevio=0;iDineroDia=0;iDineroMulta=0;iDineroComisionAdd=0;
        iComisionTotal=0;iProducidoTotal=0;iAbonadoTotal=0;iDeudaTotal=0;iTazasTotal=0;iTortasTotal=0;
        
        iNumero=0;sNumero="";sTeclaAccionada="";

        fBackGround();
    }

    // ############################################################################################     BASE DE DATOS DE DISTRIBUIDORES
    Distribuidor_Adaptador_Entregas myAdapterDistribuidor;
    ArrayList<String> alEntregado_SI_NO;
    void fActualizar_LV_Distribuidores(){

        final ArrayList alDatosCalendario=fCalendario();
        final String sNombreRegistro_Hoy=alDatosCalendario.get(0)+"-"+
                alDatosCalendario.get(1)+"-"+
                alDatosCalendario.get(2);

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        final int[][] iOrden = {new int[18]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    int iRevisarKeyz=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<String> aKeys=new ArrayList<String>(document.getData().keySet());
                        Collection collection=document.getData().values();

                        if (iRevisarKeyz==0) {
                            iOrden[0] = fKeysDistribuidor(aKeys);
                            iRevisarKeyz=1;
                        }
                        final List listSet=new ArrayList<>(collection);
                        listItemsDistribuidor=new Distribuidor_Entidad(
                                listSet.get(iOrden[0][0]).toString(),
                                Long.parseLong(listSet.get(iOrden[0][1]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][2]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][3]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][4]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][5]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][6]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][7]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][8]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][9]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][10]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][11]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][12]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][13]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][14]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][15]).toString()),
                                listSet.get(iOrden[0][16]).toString(),
                                listSet.get(iOrden[0][17]).toString());

                        alDistribuidores.add(listItemsDistribuidor);
                    }

                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
                myAdapterDistribuidor = new Distribuidor_Adaptador_Entregas(ActivityEntregaDia.this, alDistribuidores,sNombreRegistro_Hoy,"DIA");
                olvDistribuidores.setAdapter(myAdapterDistribuidor);
            }
        });

    }
    // ############################################################################################     BASE DE DATOS DE PRODUCTOS
    Productos_Entidad listItemsProductos;
    void fActualizar_Datos_Productos(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Productos";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        final int[][] iOrden = {new int[6]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int iRevisarKeyz=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<String> aKeys=new ArrayList<String>(document.getData().keySet());
                        Collection collection=document.getData().values();
                        if (iRevisarKeyz==0) {
                            iOrden[0] = fKeysProducto(aKeys);
                            iRevisarKeyz=1;
                        }
                        List listSet=new ArrayList<>(collection);
                        listItemsProductos=new Productos_Entidad(
                                Integer.parseInt(listSet.get(iOrden[0][0]).toString()),
                                listSet.get(iOrden[0][1]).toString(),
                                Integer.parseInt(listSet.get(iOrden[0][2]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][3]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][4]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][4]).toString()));
                        fValoresProducto(listItemsProductos.getsNombre());
                    }
                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });


    }
    int[] fKeysProducto(ArrayList<String> arrayList){
        int[] iOrden = new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){

            String sKeys=arrayList.get(i);
            switch (sKeys){
                case "Item":iOrden[0]=i;break;
                case "Nombre":iOrden[1]=i;break;
                case "Precio":iOrden[2]=i;break;
                case "Comision":iOrden[3]=i;break;
                case "Costo":iOrden[4]=i;break;
                case "Cantidad":iOrden[5]=i;break;
            }
        }
        return iOrden;
    }
    void fValoresProducto(String sNombre){

        switch(sNombre){
            case "Tinto": iComisionTinto=listItemsProductos.getiComision();
                            iProducidoTinto=listItemsProductos.getiPrecio();break;
            case "A.Panela": iComisionAromatica=listItemsProductos.getiComision();
                            iProducidoAromatica=listItemsProductos.getiPrecio();break;
            case "Instacrem": iComisionInstacrem=listItemsProductos.getiComision();
                            iProducidoInstacrem=listItemsProductos.getiPrecio();break;
            case "Mantecada": iComisionMantecada=listItemsProductos.getiComision();
                            iProducidoMantecada=listItemsProductos.getiPrecio();break;
            case "Liberal": iComisionLiberal=listItemsProductos.getiComision();
                            iProducidoLiberal=listItemsProductos.getiPrecio();break;
            case "Almojabana": iComisionAlmojabana=listItemsProductos.getiComision();
                            iProducidoAlmojabana=listItemsProductos.getiPrecio();break;
            case "Arepa": iComisionArepa=listItemsProductos.getiComision();
                            iProducidoArepa=listItemsProductos.getiPrecio();break;
            case "Mustang": iComisionMustang=listItemsProductos.getiComision();
                            iProducidoMustang=listItemsProductos.getiPrecio();break;
            case "Lucky": iComisionLucky=listItemsProductos.getiComision();
                            iProducidoLucky=listItemsProductos.getiPrecio();break;
        }
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

            case R.id.bEntVasosHoy:sTeclaAccionada="EntVasosHoy";fSeleccion();break;
            case R.id.bEntAromatica:sTeclaAccionada="EntAromatica";fSeleccion();break;
            case R.id.bEntInstacrem:sTeclaAccionada="EntInstacrem";fSeleccion();break;
            case R.id.bEntMantecada:sTeclaAccionada="EntMantecada";fSeleccion();break;
            case R.id.bEntLiberal:sTeclaAccionada="EntLiberal";fSeleccion();break;
            case R.id.bEntAlmojabana:sTeclaAccionada="EntAlmojabana";fSeleccion();break;
            case R.id.bEntArepa:sTeclaAccionada="EntArepa";fSeleccion();break;
            case R.id.bEntMustang:sTeclaAccionada="EntMustang";fSeleccion();break;
            case R.id.bEntLucky:sTeclaAccionada="EntLucky";fSeleccion();break;

            case R.id.bDevVasosHoy:sTeclaAccionada="DevVasosHoy";fSeleccion();break;
            case R.id.bDevAromatica:sTeclaAccionada="DevAromatica";fSeleccion();break;
            case R.id.bDevInstacrem:sTeclaAccionada="DevInstacrem";fSeleccion();break;
            case R.id.bDevMantecada:sTeclaAccionada="DevMantecada";fSeleccion();break;
            case R.id.bDevLiberal:sTeclaAccionada="DevLiberal";fSeleccion();break;
            case R.id.bDevAlmojabana:sTeclaAccionada="DevAlmojabana";fSeleccion();break;
            case R.id.bDevArepa:sTeclaAccionada="DevArepa";fSeleccion();break;

            case R.id.bDineroDia:sTeclaAccionada="DineroDia";fSeleccion();break;
            case R.id.bDineroMulta:sTeclaAccionada="DineroMulta";fSeleccion();break;
            case R.id.bDineroComisionAdd:sTeclaAccionada="DineroComisionAdd";fSeleccion();break;
            
        }
    }
    void fValores(){
        if(sTeclaAccionada==""){
            Toast.makeText(ActivityEntregaDia.this,"Selecciona un producto!",Toast.LENGTH_SHORT).show();
            return;
        }
        iNumero=Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        switch (sTeclaAccionada){
            case "EntVasosHoy":obEntVasosHoy.setText(sNumero);iEntVasosHoy=iNumero;break;
            case "EntAromatica":obEntAromatica.setText(sNumero);iEntAromatica=iNumero;break;
            case "EntInstacrem":obEntInstacrem.setText(sNumero);iEntInstacrem=iNumero;break;
            case "EntMantecada":obEntMantecada.setText(sNumero);iEntMantecada=iNumero;break;
            case "EntLiberal":obEntLiberal.setText(sNumero);iEntLiberal=iNumero;break;
            case "EntAlmojabana":obEntAlmojabana.setText(sNumero);iEntAlmojabana=iNumero;break;
            case "EntArepa":obEntArepa.setText(sNumero);iEntArepa=iNumero;break;
            case "EntMustang":obEntMustang.setText(sNumero);iEntMustang=iNumero;break;
            case "EntLucky":obEntLucky.setText(sNumero);iEntLucky=iNumero;break;

            case "DevVasosHoy":obDevVasosHoy.setText(sNumero);iDevVasosHoy=iNumero;break;
            case "DevAromatica":obDevAromatica.setText(sNumero);iDevAromatica=iNumero;break;
            case "DevInstacrem":obDevInstacrem.setText(sNumero);iDevInstacrem=iNumero;break;
            case "DevMantecada":obDevMantecada.setText(sNumero);iDevMantecada=iNumero;break;
            case "DevLiberal":obDevLiberal.setText(sNumero);iDevLiberal=iNumero;break;
            case "DevAlmojabana":obDevAlmojabana.setText(sNumero);iDevAlmojabana=iNumero;break;
            case "DevArepa":obDevArepa.setText(sNumero);iDevArepa=iNumero;break;
            case "DevMustang":obDevMustang.setText(sNumero);iDevMustang=iNumero;break;
            case "DevLucky":obDevLucky.setText(sNumero);iDevLucky=iNumero;break;

            case "DineroDia":obDineroDia.setText("$ "+imNumero);iDineroDia=iNumero;break;
            case "DineroMulta":obDineroMulta.setText("$ "+imNumero);iDineroMulta=iNumero;break;
            case "DineroComisionAdd":obDineroComisionAdd.setText("$ "+imNumero);iDineroComisionAdd=iNumero;break;
        }
        fCalculos();
    }
    void fSeleccion(){
        iNumero=0;
        sNumero="";
        switch (sTeclaAccionada){
            case "EntVasosHoy":fBackGround();obEntVasosHoy.setBackgroundColor(GREEN);break;
            case "EntAromatica":fBackGround();obEntAromatica.setBackgroundColor(GREEN);break;
            case "EntInstacrem":fBackGround();obEntInstacrem.setBackgroundColor(GREEN);break; 
            case "EntMantecada":fBackGround();obEntMantecada.setBackgroundColor(GREEN);break;
            case "EntLiberal":fBackGround();obEntLiberal.setBackgroundColor(GREEN);break;
            case "EntAlmojabana":fBackGround();obEntAlmojabana.setBackgroundColor(GREEN);break;
            case "EntArepa":fBackGround();obEntArepa.setBackgroundColor(GREEN);break;
            case "EntMustang":fBackGround();obEntMustang.setBackgroundColor(GREEN);break;
            case "EntLucky":fBackGround();obEntLucky.setBackgroundColor(GREEN);break;

            case "DevVasosHoy":fBackGround();obDevVasosHoy.setBackgroundColor(GREEN);break;
            case "DevAromatica":fBackGround();obDevAromatica.setBackgroundColor(GREEN);break;
            case "DevInstacrem":fBackGround();obDevInstacrem.setBackgroundColor(GREEN);break;
            case "DevMantecada":fBackGround();obDevMantecada.setBackgroundColor(GREEN);break;
            case "DevLiberal":fBackGround();obDevLiberal.setBackgroundColor(GREEN);break;
            case "DevAlmojabana":fBackGround();obDevAlmojabana.setBackgroundColor(GREEN);break;
            case "DevArepa":fBackGround();obDevArepa.setBackgroundColor(GREEN);break;
            case "DevMustang":fBackGround();obDevMustang.setBackgroundColor(GREEN);break;
            case "DevLucky":fBackGround();obDevLucky.setBackgroundColor(GREEN);break;

            case "DineroDia":fBackGround();obDineroDia.setBackgroundColor(GREEN);break;
            case "DineroMulta":fBackGround();obDineroMulta.setBackgroundColor(GREEN);break;
            case "DineroComisionAdd":fBackGround();obDineroComisionAdd.setBackgroundColor(GREEN);break;
        }
    }
    void fBackGround(){
        obEntVasosHoy.setBackgroundResource(R.drawable.boton_gris);
        obEntAromatica.setBackgroundResource(R.drawable.boton_gris);
        obEntInstacrem.setBackgroundResource(R.drawable.boton_gris);
        obEntMantecada.setBackgroundResource(R.drawable.boton_gris);
        obEntLiberal.setBackgroundResource(R.drawable.boton_gris);
        obEntAlmojabana.setBackgroundResource(R.drawable.boton_gris);
        obEntArepa.setBackgroundResource(R.drawable.boton_gris);
        obEntMustang.setBackgroundResource(R.drawable.boton_gris);
        obEntLucky.setBackgroundResource(R.drawable.boton_gris);

        obDevVasosHoy.setBackgroundResource(R.drawable.boton_gris);
        obDevAromatica.setBackgroundResource(R.drawable.boton_gris);
        obDevInstacrem.setBackgroundResource(R.drawable.boton_gris);
        obDevMantecada.setBackgroundResource(R.drawable.boton_gris);
        obDevLiberal.setBackgroundResource(R.drawable.boton_gris);
        obDevAlmojabana.setBackgroundResource(R.drawable.boton_gris);
        obDevArepa.setBackgroundResource(R.drawable.boton_gris);

        obDineroDia.setBackgroundResource(R.drawable.boton_gris);
        obDineroMulta.setBackgroundResource(R.drawable.boton_gris);
        obDineroComisionAdd.setBackgroundResource(R.drawable.boton_gris);  
    }

    //##############################################################################################    ADQUIRIENDO INFO DE DISTRIBUIDOR
    Distribuidor_Entidad listItemsDistribuidor;
    void fAdquirirDatosDistribuidor(){

        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaDia.this);
        progressDialog.setMessage("Cargando Datos del distribuidor");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado;
        DocumentReference bd_Datos=FirebaseFirestore.getInstance().document(sPath);
        final int[][] iOrden = {new int[18]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document=task.getResult();
                    if (document.exists()) {
                        int iRevisarKeyz=0;
                        ArrayList<String> aKeys = new ArrayList<String>(document.getData().keySet());
                        Collection collection = document.getData().values();

                        if (iRevisarKeyz == 0) {
                            iOrden[0] = fKeysDistribuidor(aKeys);
                            iRevisarKeyz = 1;
                        }
                        List listSet = new ArrayList<>(collection);
                        listItemsDistribuidor = new Distribuidor_Entidad(
                                listSet.get(iOrden[0][0]).toString(),
                                Long.parseLong(listSet.get(iOrden[0][1]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][2]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][3]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][4]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][5]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][6]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][7]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][8]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][9]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][10]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][11]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][12]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][13]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][14]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][15]).toString()),
                                listSet.get(iOrden[0][16]).toString(),
                                listSet.get(iOrden[0][17]).toString());
                        fCargarDatosDistribuidor();
                        progressDialog.cancel();
                        //fGuardar(listItemsDistribuidor);
                    } else {
                        Log.d("Distribuidor", "No such document");
                        Toast.makeText(ActivityEntregaDia.this,"No se encontró el nombre",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(ActivityEntregaDia.this,"Error adquiriendo los datos",Toast.LENGTH_SHORT).show();
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
        
    }
    int[] fKeysDistribuidor(ArrayList<String> arrayList){
        int[] iOrden = new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){

            String sKeys=arrayList.get(i);
            switch (sKeys){
                case "Nombre":iOrden[0]=i;break;
                case "Celular":iOrden[1]=i;break;
                case "Ahorro":iOrden[2]=i;break;
                case "Deuda Actual":iOrden[3]=i;break;
                case "Tazas":iOrden[4]=i;break;
                case "Aromaticas":iOrden[5]=i;break;
                case "Instacrem":iOrden[6]=i;break;
                case "Mantecada":iOrden[7]=i;break;
                case "Liberal":iOrden[8]=i;break;
                case "Almojabana":iOrden[9]=i;break;
                case "Arepa":iOrden[10]=i;break;
                case "Mustang":iOrden[11]=i;break;
                case "Lucky":iOrden[12]=i;break;
                case "Ent Tazas":iOrden[13]=i;break;
                case "Dev Tazas":iOrden[14]=i;break;
                case "Ent Dinero":iOrden[15]=i;break;
                case "Cierre Abierto":iOrden[16]=i;break;
                case "Ultimo Cierre":iOrden[17]=i;break;

            }
        }
        return iOrden;
    }
    void fCargarDatosDistribuidor(){
        
        if (listItemsDistribuidor.getsCierreAbierto().equals("")){
            iEntVasosPrevios =listItemsDistribuidor.getiEntTazas();
            iEntVasosHoy=100;
            iEntAromatica= listItemsDistribuidor.getiAromaticas();
            iEntInstacrem =listItemsDistribuidor.getiInstacrem();
            iEntMantecada= listItemsDistribuidor.getiMantecada();
            iEntLiberal= listItemsDistribuidor.getiLiberal();
            iEntAlmojabana= listItemsDistribuidor.getiAlmojabana();
            iEntArepa= listItemsDistribuidor.getiArepa();
            iEntMustang= listItemsDistribuidor.getiMustang();
            iEntLucky= listItemsDistribuidor.getiLucky();
            iDineroPrevio= listItemsDistribuidor.getiEntDinero();
            iDevVasosPrevios =listItemsDistribuidor.getiDevTazas();

            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String imNumero;
            imNumero = decimalFormat.format(iDineroPrevio);
            
            otvEntVasosPrevios.setText(String.valueOf(iEntVasosPrevios));
            obEntVasosHoy.setText("100");
            obEntAromatica.setText(String.valueOf(iEntAromatica));
            obEntInstacrem.setText(String.valueOf(iEntInstacrem));
            obEntMantecada.setText(String.valueOf(iEntMantecada));
            obEntLiberal.setText(String.valueOf(iEntLiberal));
            obEntAlmojabana.setText(String.valueOf(iEntAlmojabana));
            obEntArepa.setText(String.valueOf(iEntArepa));
            obEntMustang.setText(String.valueOf(iEntMustang));
            obEntLucky.setText(String.valueOf(iEntLucky));
            otvDineroPrevio.setText(imNumero);
            otvDevVasosPrevios.setText(String.valueOf(iDevVasosPrevios));
        }
        else{
            Toast.makeText(ActivityEntregaDia.this,"Se ha encontrado un Registro abierto",Toast.LENGTH_SHORT).show();
            fAdquirirDatosCierreAbierto();
        }
        fCalculos();
    }

    Cierres_Entidad listItemsCierres;
    void fAdquirirDatosCierreAbierto() {

        final ProgressDialog progressDialog = new ProgressDialog(ActivityEntregaDia.this);
        progressDialog.setMessage("Cargando Datos del cierre abierto");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cierres/" + listItemsDistribuidor.getsCierreAbierto();
        DocumentReference bd_Datos = FirebaseFirestore.getInstance().document(sPath);
        final int[][] iOrden = {new int[50]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int iRevisarKeyz = 0;
                        ArrayList<String> aKeys = new ArrayList<String>(document.getData().keySet());
                        Collection collection = document.getData().values();

                        if (iRevisarKeyz == 0) {
                            iOrden[0] = fKeysCierres(aKeys);
                            iRevisarKeyz = 1;
                        }
                        List listSet = new ArrayList<>(collection);
                        listItemsCierres = new Cierres_Entidad(
                                listSet.get(iOrden[0][0]).toString(),
                                Integer.parseInt(listSet.get(iOrden[0][1]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][2]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][3]).toString()),
                                listSet.get(iOrden[0][4]).toString(),
                                listSet.get(iOrden[0][5]).toString(),

                                Integer.parseInt(listSet.get(iOrden[0][9]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][10]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][11]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][12]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][13]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][14]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][15]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][16]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][17]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][18]).toString()),

                                Integer.parseInt(listSet.get(iOrden[0][19]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][20]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][21]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][22]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][23]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][24]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][25]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][26]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][27]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][28]).toString()),

                                Integer.parseInt(listSet.get(iOrden[0][40]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][41]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][43]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][44]).toString()));

                        fCargarDatosCierre();
                        fCalculos();
                        progressDialog.cancel();
                    } else {
                        Log.d("Cierre", "No such document");
                        Toast.makeText(ActivityEntregaDia.this, "No se encontró el cierre", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(ActivityEntregaDia.this, "Error adquiriendo los datos de cierre", Toast.LENGTH_SHORT).show();
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
    }
    int[] fKeysCierres(ArrayList<String> arrayList){
        int[] iOrden = new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){

            String sKeys=arrayList.get(i);
            switch (sKeys){

                case "NumTicket":iOrden[0]=i;break;
                case "Año":iOrden[1]=i;break;
                case "Mes":iOrden[2]=i;break;
                case "Fecha":iOrden[3]=i;break;
                case "Día":iOrden[4]=i;break;
                case "Hora":iOrden[5]=i;break;
                case "Distribuidor":iOrden[6]=i;break;
                case "Estado":iOrden[7]=i;break;
                case "Pagado":iOrden[8]=i;break;

                case "EntVasosPrevios":iOrden[9]=i;break;
                case "EntVasosHoy":iOrden[10]=i;break;
                case "EntAromatica":iOrden[11]=i;break;
                case "EntInstacrem":iOrden[12]=i;break;
                case "EntMantecada":iOrden[13]=i;break;
                case "EntLiberal":iOrden[14]=i;break;
                case "EntAlmojabana":iOrden[15]=i;break;
                case "EntArepa":iOrden[16]=i;break;
                case "EntMustang":iOrden[17]=i;break;
                case "EntLucky":iOrden[18]=i;break;

                case "DevVasosPrevios":iOrden[19]=i;break;
                case "DevVasosHoy":iOrden[20]=i;break;
                case "DevAromatica":iOrden[21]=i;break;
                case "DevInstacrem":iOrden[22]=i;break;
                case "DevMantecada":iOrden[23]=i;break;
                case "DevLiberal":iOrden[24]=i;break;
                case "DevAlmojabana":iOrden[25]=i;break;
                case "DevArepa":iOrden[26]=i;break;
                case "DevMustang":iOrden[27]=i;break;
                case "DevLucky":iOrden[28]=i;break;

                case "VenVasosPrevios":iOrden[29]=i;break;
                case "VenVasosHoy":iOrden[30]=i;break;
                case "VenAromatica":iOrden[31]=i;break;
                case "VenInstacrem":iOrden[32]=i;break;
                case "VenMantecada":iOrden[33]=i;break;
                case "VenLiberal":iOrden[34]=i;break;
                case "VenAlmojabana":iOrden[35]=i;break;
                case "VenArepa":iOrden[36]=i;break;
                case "VenMustang":iOrden[37]=i;break;
                case "VenLucky":iOrden[38]=i;break;
                case "VenTinto":iOrden[39]=i;break;

                case "DineroPrevio":iOrden[40]=i;break;
                case "DineroDia":iOrden[41]=i;break;
                case "AbonadoTotal":iOrden[42]=i;break;
                case "DineroMulta":iOrden[43]=i;break;
                case "DineroComisionAdd":iOrden[44]=i;break;
                case "ProducidoTotal":iOrden[45]=i;break;
                case "ComisionTotal":iOrden[46]=i;break;
                case "DeudaTotal":iOrden[47]=i;break;
                case "TazasTotal":iOrden[48]=i;break;
                case "TortasTotal":iOrden[49]=i;break;
            }
        }
        return iOrden;
    }
    void fCargarDatosCierre() {
        
        iEntVasosPrevios = listItemsCierres.getiEntVasosPrevios();
        iEntVasosHoy = listItemsCierres.getiEntVasosHoy();
        iEntAromatica = listItemsCierres.getiEntAromatica();
        iEntInstacrem = listItemsCierres.getiEntInstacrem();
        iEntMantecada = listItemsCierres.getiEntMantecada();
        iEntLiberal = listItemsCierres.getiEntLiberal();
        iEntAlmojabana = listItemsCierres.getiEntAlmojabana();
        iEntArepa = listItemsCierres.getiEntArepa();
        iEntMustang = listItemsCierres.getiEntMustang();
        iEntLucky = listItemsCierres.getiEntLucky();

        iDevVasosPrevios = listItemsCierres.getiDevVasosPrevios();
        iDevVasosHoy = listItemsCierres.getiDevVasosHoy();
        iDevAromatica = listItemsCierres.getiDevAromatica();
        iDevInstacrem = listItemsCierres.getiDevInstacrem();
        iDevMantecada = listItemsCierres.getiDevMantecada();
        iDevLiberal = listItemsCierres.getiDevLiberal();
        iDevAlmojabana = listItemsCierres.getiDevAlmojabana();
        iDevArepa = listItemsCierres.getiDevArepa();
        iDevMustang = listItemsCierres.getiDevMustang();
        iDevLucky = listItemsCierres.getiDevLucky();
        
        iDineroPrevio = listItemsCierres.getiDineroPrevio();
        iDineroDia=listItemsCierres.getiDineroDia();
        iDineroComisionAdd=listItemsCierres.getiDineroComisionAdd();
        iDineroMulta=listItemsCierres.getiDineroMulta();
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imDineroPrevio=decimalFormat.format(iDineroPrevio);
        String imDineroDia=decimalFormat.format(iDineroDia);
        String imDineroComisionAdd=decimalFormat.format(iDineroComisionAdd);
        String imDineroMulta=decimalFormat.format(iDineroMulta);

        otvEntVasosPrevios.setText(String.valueOf(iEntVasosPrevios));
        obEntVasosHoy.setText(String.valueOf(iEntVasosHoy));
        obEntAromatica.setText(String.valueOf(iEntAromatica));
        obEntInstacrem.setText(String.valueOf(iEntInstacrem));
        obEntMantecada.setText(String.valueOf(iEntMantecada));
        obEntLiberal.setText(String.valueOf(iEntLiberal));
        obEntAlmojabana.setText(String.valueOf(iEntAlmojabana));
        obEntArepa.setText(String.valueOf(iEntArepa));
        obEntMustang.setText(String.valueOf(iEntMustang));
        obEntLucky.setText(String.valueOf(iEntLucky));

        otvDevVasosPrevios.setText(String.valueOf(iDevVasosPrevios));
        obDevVasosHoy.setText(String.valueOf(iDevVasosHoy));
        obDevAromatica.setText(String.valueOf(iDevAromatica));
        obDevInstacrem.setText(String.valueOf(iDevInstacrem));
        obDevMantecada.setText(String.valueOf(iDevMantecada));
        obDevLiberal.setText(String.valueOf(iDevLiberal));
        obDevAlmojabana.setText(String.valueOf(iDevAlmojabana));
        obDevArepa.setText(String.valueOf(iDevArepa));

        otvDineroPrevio.setText("$ "+imDineroPrevio);
        obDineroDia.setText("$ "+imDineroDia);
        obDineroMulta.setText("$ "+imDineroMulta);
        obDineroComisionAdd.setText("$ "+imDineroComisionAdd);
    }

    // ############################################################################################  CÁLCULOS
    void fCalculos(){
        iVenVasosPrevios =iEntVasosPrevios-iDevVasosPrevios;
        iVenVasosHoy=iEntVasosHoy-iDevVasosHoy;
        iVenAromatica=iEntAromatica-iDevAromatica;
        iVenInstacrem =iEntInstacrem-iDevInstacrem;
        iVenMantecada=iEntMantecada-iDevMantecada;
        iVenLiberal=iEntLiberal-iDevLiberal;
        iVenAlmojabana=iEntAlmojabana-iDevAlmojabana;
        iVenArepa=iEntArepa-iDevArepa;
        iVenMustang=iEntMustang-iDevMustang;
        iVenLucky=iEntLucky-iDevLucky;
        iVenTinto=iVenVasosPrevios+iVenVasosHoy-iVenAromatica-iVenInstacrem;

        fCalculoComision();
        fCalculoProducido();
        iAbonadoTotal=iDineroPrevio+iDineroDia;
        iDeudaTotal=iProducidoTotal-iAbonadoTotal;
        iTazasTotal=iVenVasosPrevios+iVenVasosHoy;
        iTortasTotal=iVenMantecada+iVenLiberal+iVenAlmojabana+iVenArepa;
        fTextoVentas();

    }
    void fCalculoProducido(){
        iProducidoTotal= (iVenTinto*iProducidoTinto)+
                (iVenAromatica*iProducidoAromatica)+
                (iVenInstacrem *iProducidoInstacrem)+
                (iVenMantecada*iProducidoMantecada)+
                (iVenLiberal*iProducidoLiberal)+
                (iVenAlmojabana*iProducidoAlmojabana)+
                (iVenArepa*iProducidoArepa)+
                (iVenMustang*iProducidoMustang)+
                (iVenLucky*iProducidoLucky)+
                (iDineroMulta);
    }
    void fCalculoComision(){
        iComisionTotal= (iVenTinto*iComisionTinto)+
                (iVenAromatica*iComisionAromatica)+
                (iVenInstacrem *iComisionInstacrem)+
                (iVenMantecada*iComisionMantecada)+
                (iVenLiberal*iComisionLiberal)+
                (iVenAlmojabana*iComisionAlmojabana)+
                (iVenArepa*iComisionArepa)+
                (iVenMustang*0)+
                (iVenLucky*0)+
                (iDineroComisionAdd);
    }
    String imComisionTotal,imProducidoTotal,imAbonadoTotal,imDeudaTotal,imMultas,imNuevaDeuda;
    void fTextoVentas(){
        otvVentaVasosPrevios.setText(String.valueOf(iVenVasosPrevios));
        otvVentaVasosHoy.setText(String.valueOf(iVenVasosHoy));
        otvVentaAromatica.setText(String.valueOf(iVenAromatica));
        otvVentaInstacrem.setText(String.valueOf(iVenInstacrem));
        otvVentaMantecada.setText(String.valueOf(iVenMantecada));
        otvVentaLiberal.setText(String.valueOf(iVenLiberal));
        otvVentaAlmojabana.setText(String.valueOf(iVenAlmojabana));
        otvVentaArepa.setText(String.valueOf(iVenArepa));
        otvVentaMustang.setText(String.valueOf(iVenMustang));
        otvVentaLucky.setText(String.valueOf(iVenLucky));

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        imComisionTotal = decimalFormat.format(iComisionTotal);
        imProducidoTotal = decimalFormat.format(iProducidoTotal);
        imAbonadoTotal = decimalFormat.format(iAbonadoTotal);
        imDeudaTotal = decimalFormat.format(iDeudaTotal);
        imMultas=decimalFormat.format(iDineroMulta);
        imNuevaDeuda=decimalFormat.format(listItemsDistribuidor.getiDeuda()+iDeudaTotal);

        otvComisionTotal.setText("Comisión:\n$ "+imComisionTotal);
        otvProducidoTotal.setText("Producido:\n$ "+imProducidoTotal);
        otvAbonadoTotal.setText("Abonado:\n$ "+imAbonadoTotal);
        otvDeudaTotal.setText("Deuda:\n$ "+imDeudaTotal);
        otvTazasTotal.setText("Tazas:\n"+iTazasTotal);
        otvTortasTotal.setText("Tortas:\n"+iTortasTotal);

    }
    

    // ############################################################################################  VERIFICAR, ADQUIRIR, GUARDAR Y ENVIAR
    void fVerificarCondiciones(String sEstadoRegistro){
        if (TextUtils.isEmpty(sDistribuidorSeleccionado)) {
            Toast.makeText(ActivityEntregaDia.this,
                    "Debes seleccionar un Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        fGuardar(sEstadoRegistro);
    }
    void fGuardar(final String sEstadoRegistro){
        //Fecha
        final ArrayList alDatosCalendario=fCalendario();

        final String sNombreRegistro=alDatosCalendario.get(0)+"-"+
                                alDatosCalendario.get(1)+"-"+
                                alDatosCalendario.get(2)+"-"+
                                alDatosCalendario.get(5)+"-"+
                                sDistribuidorSeleccionado;

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaDia.this);
        progressDialog.setMessage("Guardando Registro");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //---------  Nuevo personal
        DocumentReference bd_NuevasEntregas = FirebaseFirestore.getInstance()
                .document("Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cierres/"+sNombreRegistro);

        Map<String,Object> bd_GuardarRegistro=new HashMap<String, Object>();
        bd_GuardarRegistro.put("NumTicket",sNombreRegistro);
        bd_GuardarRegistro.put("Año",alDatosCalendario.get(0));
        bd_GuardarRegistro.put("Mes",alDatosCalendario.get(1));
        bd_GuardarRegistro.put("Fecha",alDatosCalendario.get(2));
        bd_GuardarRegistro.put("Día",alDatosCalendario.get(3));
        bd_GuardarRegistro.put("Hora",alDatosCalendario.get(5));
        bd_GuardarRegistro.put("Distribuidor",sDistribuidorSeleccionado);
        bd_GuardarRegistro.put("Estado",sEstadoRegistro);
        bd_GuardarRegistro.put("Pagado","NO");
        
        bd_GuardarRegistro.put("EntVasosPrevios",iEntVasosPrevios);
        bd_GuardarRegistro.put("EntVasosHoy",iEntVasosHoy);
        bd_GuardarRegistro.put("EntAromatica",iEntAromatica);
        bd_GuardarRegistro.put("EntInstacrem",iEntInstacrem);
        bd_GuardarRegistro.put("EntMantecada",iEntMantecada);
        bd_GuardarRegistro.put("EntLiberal",iEntLiberal);
        bd_GuardarRegistro.put("EntAlmojabana",iEntAlmojabana);
        bd_GuardarRegistro.put("EntArepa",iEntArepa);
        bd_GuardarRegistro.put("EntMustang",iEntMustang);
        bd_GuardarRegistro.put("EntLucky",iEntLucky);
        
        bd_GuardarRegistro.put("DevVasosPrevios",iDevVasosPrevios);
        bd_GuardarRegistro.put("DevVasosHoy",iDevVasosHoy);
        bd_GuardarRegistro.put("DevAromatica",iDevAromatica);
        bd_GuardarRegistro.put("DevInstacrem",iDevInstacrem);
        bd_GuardarRegistro.put("DevMantecada",iDevMantecada);
        bd_GuardarRegistro.put("DevLiberal",iDevLiberal);
        bd_GuardarRegistro.put("DevAlmojabana",iDevAlmojabana);
        bd_GuardarRegistro.put("DevArepa",iDevArepa);
        bd_GuardarRegistro.put("DevMustang",iDevMustang);
        bd_GuardarRegistro.put("DevLucky",iDevLucky);
        
        bd_GuardarRegistro.put("VenVasosPrevios",iVenVasosPrevios);
        bd_GuardarRegistro.put("VenVasosHoy",iVenVasosHoy);
        bd_GuardarRegistro.put("VenAromatica",iVenAromatica);
        bd_GuardarRegistro.put("VenInstacrem",iVenInstacrem);
        bd_GuardarRegistro.put("VenMantecada",iVenMantecada);
        bd_GuardarRegistro.put("VenLiberal",iVenLiberal);
        bd_GuardarRegistro.put("VenAlmojabana",iVenAlmojabana);
        bd_GuardarRegistro.put("VenArepa",iVenArepa);
        bd_GuardarRegistro.put("VenMustang",iVenMustang);
        bd_GuardarRegistro.put("VenLucky",iVenLucky);
        bd_GuardarRegistro.put("VenTinto",iVenTinto);

        bd_GuardarRegistro.put("DineroPrevio",iDineroPrevio);
        bd_GuardarRegistro.put("DineroDia",iDineroDia);
        bd_GuardarRegistro.put("AbonadoTotal",iAbonadoTotal);
        bd_GuardarRegistro.put("DineroMulta",iDineroMulta);
        bd_GuardarRegistro.put("DineroComisionAdd",iDineroComisionAdd);
        bd_GuardarRegistro.put("ProducidoTotal",iProducidoTotal);
        bd_GuardarRegistro.put("ComisionTotal",iComisionTotal);
        bd_GuardarRegistro.put("DeudaTotal",iDeudaTotal);
        bd_GuardarRegistro.put("TazasTotal",iTazasTotal);
        bd_GuardarRegistro.put("TortasTotal",iTortasTotal);

        bd_NuevasEntregas.set(bd_GuardarRegistro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaDia.this,"Registro guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                if (sEstadoRegistro.equals("Abierto")){
                    fActualizarDistribuidor(sNombreRegistro,"");
                }
                else{
                    fActualizarDistribuidor("",sNombreRegistro);
                    fEnviarMensaje(alDatosCalendario.get(6).toString());
                    for (int i=alDistribuidores.size()-1;i>=0;i--){
                        alDistribuidores.remove(i);
                    }
                    fActualizar_LV_Distribuidores();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaDia.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fEnviarMensaje(String sFecha){

        Long lCelular=listItemsDistribuidor.getlCelular();

        String sMensaje="Hola " + sDistribuidorSeleccionado + ", tu cierre de actividades del "+
                sFecha+" arrojó los siguientes resultados:"+
                "%0A"+
                "%0ATintos y Aromáticas: "+ (iVenTinto+iVenAromatica)+", Instacrem: "+iVenInstacrem+
                "%0AMantecada: "+iVenMantecada+", Liberal: "+iVenLiberal+
                "%0AAlmojabana: "+iVenAlmojabana+", Arepa: "+iVenArepa+
                "%0A"+
                "%0ATotal de Tazas: "+iTazasTotal+
                "%0ATortas Total: "+iTortasTotal+
                "%0A"+
                "%0ATU COMISIÓN GANADA: $ "+imComisionTotal+
                "%0A"+
                "%0ADeuda Anterior: $ "+listItemsDistribuidor.getiDeuda()+
                "%0A+Multas y/u otros: $ "+imMultas+
                "%0A+Total Producido: $ "+imProducidoTotal+
                "%0A-Tus Abonos: $ "+imAbonadoTotal+
                "%0AEntonces Debes: $ "+imNuevaDeuda+
                "%0A"+
                "%0AGracias por tu trabajo, esto no sería posible sin ti... Ve por más!!!";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+lCelular +"&text="+sMensaje));
        startActivity(intent);
    }
    void fActualizarDistribuidor(String sCierreAbierto,String sUltimoCierre){
        DocumentReference bd_NuevoPersonal = FirebaseFirestore.getInstance()
                .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado);

        Map<String,Object> bd_GuardarDistribuidor=new HashMap<String, Object>();
        bd_GuardarDistribuidor.put("Deuda Actual",(listItemsDistribuidor.getiDeuda()+iDeudaTotal));
        bd_GuardarDistribuidor.put("Tazas",(listItemsDistribuidor.getiTazas()+iTazasTotal));
        bd_GuardarDistribuidor.put("Aromaticas",25);
        bd_GuardarDistribuidor.put("Instacrem",10);
        bd_GuardarDistribuidor.put("Mantecada",iDevMantecada);
        bd_GuardarDistribuidor.put("Liberal",iDevLiberal);
        bd_GuardarDistribuidor.put("Almojabana",iDevAlmojabana);
        bd_GuardarDistribuidor.put("Arepa",iDevArepa);
        bd_GuardarDistribuidor.put("Ent Tazas",0);
        bd_GuardarDistribuidor.put("Dev Tazas",0);
        bd_GuardarDistribuidor.put("Ent Dinero",0);
        bd_GuardarDistribuidor.put("Cierre Abierto",sCierreAbierto);
        bd_GuardarDistribuidor.put("Ultimo Cierre",sUltimoCierre);
        bd_GuardarDistribuidor.put("Mustang",0);
        bd_GuardarDistribuidor.put("Lucky",0);

        bd_NuevoPersonal.update(bd_GuardarDistribuidor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaDia.this,"Datos de Distribuidor actualizados",Toast.LENGTH_SHORT).show();
                fObtenerCuentas();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaDia.this,"Se presentaron fallas en el distribuidor",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ############################################################################################  ACTUALIZAR CUENTAS Y MOVIMIENTOS
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
                        fGuardarCuentas(iEfectivo, iBancos);
                    } else {
                        Log.d("Distribuidor", "No such document");
                    }
                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
    }
    void fGuardarCuentas(int iEfectivo,int iBancos){
        //Actualizar información de cuentas
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/Cuentas";
        DocumentReference bd_NuevaCuenta = FirebaseFirestore.getInstance().document(sPath);

        iEfectivo =iEfectivo+iDineroDia;

        Map<String,Object> bd_GuardarCuenta=new HashMap<String, Object>();
        bd_GuardarCuenta.put("Efectivo",iEfectivo);

        bd_NuevaCuenta.update(bd_GuardarCuenta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                fGuardarMovimiento();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaDia.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void fGuardarMovimiento(){

        final ArrayList alDatosCalendario=fCalendario();
        final String sMesColeccion=alDatosCalendario.get(0)+"-"+alDatosCalendario.get(4);
        final String sFechaDocumento=alDatosCalendario.get(2)+"-"+alDatosCalendario.get(3);
        final String sHoraDocumento=alDatosCalendario.get(8).toString();

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaDia.this);
        progressDialog.setMessage("Registrando Movimiento");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sPath="Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/Movimientos/"+
                sMesColeccion+"/"+sFechaDocumento+"/Movimientos/"+sHoraDocumento;
        //---------  Nuevo personal
        DocumentReference bd_NuevoMovimiento = FirebaseFirestore.getInstance().document(sPath);

        Map<String,Object> bd_GuardarPersonal=new HashMap<String, Object>();
        bd_GuardarPersonal.put("Mes",sMesColeccion);
        bd_GuardarPersonal.put("Fecha",sFechaDocumento);
        bd_GuardarPersonal.put("Hora",sHoraDocumento);
        bd_GuardarPersonal.put("Tipo de Movimiento","Entrada");
        bd_GuardarPersonal.put("Categoria_1","Ent. por Distribuidor");
        bd_GuardarPersonal.put("Categoria_2",sDistribuidorSeleccionado);
        bd_GuardarPersonal.put("Descripción","Entrega de Día");
        bd_GuardarPersonal.put("Cantidad",1);
        bd_GuardarPersonal.put("ValorUND",iDineroDia);
        bd_GuardarPersonal.put("Total",iDineroDia);

        bd_NuevoMovimiento.set(bd_GuardarPersonal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaDia.this,"Movimiento creado satisfactoriamente",Toast.LENGTH_SHORT).show();
                fBorrarTodo();
                progressDialog.cancel();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaDia.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }




    // ############################################################################################  CALENDARIO
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
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:");
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("HHmmddssMMyy");
        String sHora=sdf.format(Date.getTime());
        String sHoraSeg=sdf1.format(Date.getTime());
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

