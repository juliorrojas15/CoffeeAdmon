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

public class ActivityNomina extends AppCompatActivity implements View.OnClickListener{
    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    ListView olvDistribuidores,olvCierres;
    TextView otvDistribuidorSeleccionado;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_000;
    Button obCoffeeExpress,obDescuentos,obBonificaciones;
    TextView otvSubTotal,otvDeuda,otvPrestamos,otvTotalPago,otvPromedioTz;
    Button obBorrarUno,obBorrarTodo,obPagar,obConfirmar;
    //#########################################################################################     Variables Globales
    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;

    int iSubTotal=0,iDeuda=0,iPrestamos=0,iTotalPago=0;
    float fPromedioTz =0;
    int iCoffeeExpress,iDescuentos,iBonificaciones;
    int iTotalTz;

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomina);

        //#####################################################################################     Relación de objetos con Layout
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);
        otvDistribuidorSeleccionado=(TextView)findViewById(R.id.tvDistribuidorSeleccionado);
        olvCierres=(ListView)findViewById(R.id.lvCierres);

        otvSubTotal=(TextView)findViewById(R.id.tvSubTotal);
        otvDeuda=(TextView)findViewById(R.id.tvDeuda);
        otvPrestamos=(TextView)findViewById(R.id.tvPrestamos);
        otvTotalPago=(TextView)findViewById(R.id.tvTotalPago);
        otvPromedioTz=(TextView)findViewById(R.id.tvPromedioTz);

        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_000=(Button)findViewById(R.id.bTecla_000);

        obCoffeeExpress=(Button)findViewById(R.id.bCoffeeExpress);
        obDescuentos=(Button)findViewById(R.id.bDescuentos);
        obBonificaciones=(Button)findViewById(R.id.bBonificaciones);

        obBorrarUno=(Button)findViewById(R.id.bBorrarUno);
        obPagar=(Button)findViewById(R.id.bPagar);
        obBorrarTodo=(Button)findViewById(R.id.bBorrarTodo);
        obConfirmar=(Button)findViewById(R.id.bConfirmar);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);obTecla_000.setOnClickListener(this);
        obCoffeeExpress.setOnClickListener(this);obDescuentos.setOnClickListener(this);
        obBonificaciones.setOnClickListener(this);
        //##################################################################################         Acciones iniciales
        fActualizar_LV_Distribuidores();
        fBorrarTodo();

        obConfirmar.setVisibility(View.INVISIBLE);
        //##################################################################################         Acciones de botones
        obPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obConfirmar.setVisibility(View.VISIBLE);
                obPagar.setVisibility(View.INVISIBLE);
            }
        });
        obConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fVerificarCondiciones();
                //Dentro de Verificar, luego va a fAdquirirEntregados();
                //Dentro de adquirir, luego va a fGuardar();
                obConfirmar.setVisibility(View.INVISIBLE);
                obPagar.setVisibility(View.VISIBLE);

            }
        });

        obBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obConfirmar.setVisibility(View.INVISIBLE);
                fBorrarTodo();
                fBackGround();
                obPagar.setVisibility(View.VISIBLE);
                obConfirmar.setVisibility(View.INVISIBLE);
            }
        });
        obBorrarUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (sTeclaAccionada){
                    case "CoffeeExpress":iCoffeeExpress=0;obCoffeeExpress.setText("0");break;
                    case "Descuentos":iDescuentos=0;obDescuentos.setText("0");break;
                    case "Bonificaciones":iBonificaciones=0;obBonificaciones.setText("0");break;
                }
                iNumero=0;
                sNumero="";
                fCalculos();
            }

        });


        //###################################################################################       Acciones de los ListView

        olvDistribuidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object oDistribuidorSeleccionado= olvDistribuidores.getItemAtPosition(position);
                sDistribuidorSeleccionado=oDistribuidorSeleccionado.toString();

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

        otvSubTotal.setText("$ 0");otvDeuda.setText("$ 0");otvPrestamos.setText("$ 0");
        otvTotalPago.setText("$ 0");otvPromedioTz.setText("$ 0");

        obCoffeeExpress.setText("$ 0");obDescuentos.setText("$ 0");obBonificaciones.setText("$ 0");

        if (olvCierres.getCount()>0){
            for (int i=0;i<myAdapterCierres.getCount();i++){
                myAdapterCierres.remove(i);
            }
            olvCierres.setAdapter(myAdapterCierres);
        }

        iSubTotal=0;iDeuda=0;iPrestamos=0;
        iTotalPago=0;
        fPromedioTz =0;
        iCoffeeExpress=0;iDescuentos=0;iBonificaciones=0;

        iNumero=0;sNumero="";sTeclaAccionada="";

        fBackGround();
    }

    // ############################################################################################     BASE DE DATOS DE DISTRIBUIDORES
    void fActualizar_LV_Distribuidores(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);

        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String sNombreDoc = document.getId();
                        alDistribuidores.add(sNombreDoc);
                    }
                    ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
                            ActivityNomina.this,android.R.layout.simple_list_item_1,
                            android.R.id.text1,alDistribuidores);
                    olvDistribuidores.setAdapter(myAdapter);

                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
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
            case R.id.bTecla_000:sNumero=sNumero+"000";fValores();break;

            case R.id.bCoffeeExpress:sTeclaAccionada="CoffeeExpress";fSeleccion();break;
            case R.id.bDescuentos:sTeclaAccionada="Descuentos";fSeleccion();break;
            case R.id.bBonificaciones:sTeclaAccionada="Bonificaciones";fSeleccion();break;

        }
    }
    void fValores(){
        if(sTeclaAccionada==""){
            Toast.makeText(ActivityNomina.this,"Selecciona un producto!",Toast.LENGTH_SHORT).show();
            return;
        }
        iNumero=Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        switch (sTeclaAccionada){
            case "CoffeeExpress":obCoffeeExpress.setText("$ "+imNumero);iCoffeeExpress=iNumero;break;
            case "Descuentos":obDescuentos.setText("$ "+imNumero);iDescuentos=iNumero;break;
            case "Bonificaciones":obBonificaciones.setText("$ "+imNumero);iBonificaciones=iNumero;break;
        }
        fCalculos();
    }
    void fSeleccion(){
        iNumero=0;
        sNumero="";
        switch (sTeclaAccionada){
            case "CoffeeExpress":fBackGround();obCoffeeExpress.setBackgroundColor(GREEN);break;
            case "Descuentos":fBackGround();obDescuentos.setBackgroundColor(GREEN);break;
            case "Bonificaciones":fBackGround();obBonificaciones.setBackgroundColor(GREEN);break;
        }
    }
    void fBackGround(){
        obCoffeeExpress.setBackgroundResource(R.drawable.boton_gris);
        obDescuentos.setBackgroundResource(R.drawable.boton_gris);
        obBonificaciones.setBackgroundResource(R.drawable.boton_gris);
    }

    //##############################################################################################    ADQUIRIENDO INFO DE DISTRIBUIDOR
    Distribuidor_Entidad listItemsDistribuidor;
    void fAdquirirDatosDistribuidor(){

        final ProgressDialog progressDialog=new ProgressDialog(ActivityNomina.this);
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
                        Toast.makeText(ActivityNomina.this,"No se encontró el nombre",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                } else {
                    progressDialog.cancel();
                    Toast.makeText(ActivityNomina.this,"Error adquiriendo los datos",Toast.LENGTH_SHORT).show();
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
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String imNumero;
            imNumero = decimalFormat.format(listItemsDistribuidor.getiDeuda());
            otvDeuda.setText("$ "+imNumero);
            iDeuda=listItemsDistribuidor.getiDeuda();
            fCargarCierres();
        }
        else{
            Toast.makeText(ActivityNomina.this,"Se ha encontrado un Registro abierto, primero cierra ese registros y vuelve a nomina",Toast.LENGTH_SHORT).show();
        }
    }
    Cierres_Entidad_Nomina listCierres;
    Cierres_Adaptador_Nomina myAdapterCierres;
    ArrayList<Cierres_Entidad_Nomina> alCierres;
    void fCargarCierres(){

        final ProgressDialog progressDialog=new ProgressDialog(ActivityNomina.this);
        progressDialog.setMessage("Cargando Cierres del distribuidor");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        iSubTotal=0;
        fPromedioTz =0;
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cierres";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    alCierres = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String sNombreDoc = document.getId();
                        if (sNombreDoc.contains(listItemsDistribuidor.getsNombre())) {
                            ArrayList aKeys = new ArrayList(document.getData().keySet());
                            ArrayList aDatos = new ArrayList(document.getData().values());
                            ArrayList listValores_Keys = new ArrayList();
                            String sKey = "";
                            for (int i = 0; i < aKeys.size(); i++) {
                                if (aKeys.get(i).equals("Pagado")) {
                                    if (aDatos.get(i).equals("NO")){
                                        for (int j = 0; j < 6; j++) {
                                            switch (j) {
                                                case 0:
                                                    sKey = "Fecha";
                                                    break;
                                                case 1:
                                                    sKey = "Mes";
                                                    break;
                                                case 2:
                                                    sKey = "Año";
                                                    break;
                                                case 3:
                                                    sKey = "TazasTotal";
                                                    break;
                                                case 4:
                                                    sKey = "ComisionTotal";
                                                    break;
                                                case 5:
                                                    sKey = "NumTicket";
                                                    break;
                                            }
                                            for (int k = 0; k < aKeys.size(); k++) {
                                                if (aKeys.get(k).equals(sKey)) {
                                                    listValores_Keys.add(aDatos.get(k));
                                                }
                                            }
                                        }
                                        listCierres = new Cierres_Entidad_Nomina(
                                                listValores_Keys.get(0) + "/" +
                                                        listValores_Keys.get(1) + "/" +
                                                        listValores_Keys.get(2),
                                                Integer.parseInt(listValores_Keys.get(3).toString()),
                                                Integer.parseInt(listValores_Keys.get(4).toString()),
                                                listValores_Keys.get(5).toString());
                                        alCierres.add(listCierres);
                                        iSubTotal=iSubTotal+Integer.parseInt(listValores_Keys.get(4).toString());
                                        fPromedioTz = fPromedioTz +Integer.parseInt(listValores_Keys.get(3).toString());
                                    } else {
                                        i = aKeys.size();
                                    }
                                }
                            }
                        }
                    }
                    myAdapterCierres = new Cierres_Adaptador_Nomina(ActivityNomina.this, alCierres);
                    olvCierres.setAdapter(myAdapterCierres);

                    if (alCierres.size()>0){
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        String imNumero;
                        imNumero = decimalFormat.format(iSubTotal);
                        otvSubTotal.setText("$ "+imNumero);
                        otvPromedioTz.setText(String.valueOf(fPromedioTz /olvCierres.getCount()));

                        progressDialog.cancel();
                        fCalculos();
                    }
                    else{
                        progressDialog.cancel();
                        Toast.makeText(ActivityNomina.this,"No hay Registros de este distribuidor",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }

            }
        });
    }

    // ############################################################################################  CÁLCULOS
    void fCalculos(){
        iTotalPago=iSubTotal-iDeuda-iCoffeeExpress-iPrestamos-iDescuentos+iBonificaciones;
        if (olvCierres.getCount()>0){
            fPromedioTz =iTotalTz/olvCierres.getCount();
        }
        else{
            fPromedioTz =0;
        }
        fTextoVentas();

    }
    String imTotalPago;
    void fTextoVentas(){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        imTotalPago = decimalFormat.format(iTotalPago);

        otvTotalPago.setText("$ "+imTotalPago);
        otvPromedioTz.setText(String.valueOf(fPromedioTz));
    }
    // ############################################################################################  CÁLCULOSVERIFICAR, ADQUIRIR, GUARDAR Y ENVIAR
    void fVerificarCondiciones(){
        if (TextUtils.isEmpty(sDistribuidorSeleccionado)) {
            Toast.makeText(ActivityNomina.this,
                    "Debes seleccionar un Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        fGuardar();
    }
    void fGuardar(){
        //Fecha
        final ArrayList alDatosCalendario=fCalendario();

        final String sNombreRegistro=alDatosCalendario.get(0)+"-"+
                alDatosCalendario.get(1)+"-"+
                alDatosCalendario.get(2)+"-"+
                alDatosCalendario.get(5)+"-"+
                sDistribuidorSeleccionado;

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityNomina.this);
        progressDialog.setMessage("Guardando Registro de Nomina");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        Map<String, Object> mRegistrosTotal = new HashMap<>();


        for (int i=0;i<alCierres.size();i++){
            Map<String, Object> mRegistro = new HashMap<>();
            mRegistro.put("NumTicket", alCierres.get(i).getsNombreCierre());
            mRegistro.put("Fecha", alCierres.get(i).getsFecha());
            mRegistro.put("TazasTotal", alCierres.get(i).getiTazas());
            mRegistro.put("ComisionTotal", alCierres.get(i).getiComision());

            mRegistrosTotal.put("R"+i,mRegistro);
        }

        //---------  Nuevo personal
        DocumentReference bd_NuevosCierres = FirebaseFirestore.getInstance()
                .document("Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Nomina/"+sNombreRegistro);

        Map<String,Object> bd_GuardarRegistro=new HashMap<String, Object>();
        bd_GuardarRegistro.put("NumRegistro",sNombreRegistro);
        bd_GuardarRegistro.put("Año",alDatosCalendario.get(0));
        bd_GuardarRegistro.put("Mes",alDatosCalendario.get(1));
        bd_GuardarRegistro.put("Fecha",alDatosCalendario.get(2));
        bd_GuardarRegistro.put("Día",alDatosCalendario.get(3));
        bd_GuardarRegistro.put("Hora",alDatosCalendario.get(5));
        bd_GuardarRegistro.put("Distribuidor",sDistribuidorSeleccionado);

        bd_GuardarRegistro.put("Coleccion",mRegistrosTotal);
        bd_GuardarRegistro.put("SubTotal",iSubTotal);
        bd_GuardarRegistro.put("Deuda",iDeuda);
        bd_GuardarRegistro.put("Prestamos",iPrestamos);
        bd_GuardarRegistro.put("CoffeeExpress",iCoffeeExpress);
        bd_GuardarRegistro.put("Descuentos",iDescuentos);
        bd_GuardarRegistro.put("Bonificaciones",iBonificaciones);
        bd_GuardarRegistro.put("TotalPago",iTotalPago);
        bd_GuardarRegistro.put("PromedioTazas", fPromedioTz);

        bd_NuevosCierres.set(bd_GuardarRegistro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityNomina.this,"Registro guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                fActualizarDistribuidor("");
                fEnviarMensaje(alDatosCalendario.get(6).toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(ActivityNomina.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();

            }
        });

    }
    void fEnviarMensaje(String sFecha){

        Long lCelular=listItemsDistribuidor.getlCelular();
        String sTextoListado="Fecha         Tazas     Comisión";
        for(int i=0;i<alCierres.size();i++){
            sTextoListado=sTextoListado+
                            "%0A"+
                            alCierres.get(i).getsFecha()+"   "+
                            alCierres.get(i).iTazas+"     "+
                            alCierres.get(i).iComision;

        }
        String sMensaje="Hola " + sDistribuidorSeleccionado +
                ", tu cierre de Nomina del " + alDatosCalendario.get(6)+ " arrojó los siguientes resultados:"+
                "%0A%0A"+
                sTextoListado+
                "%0A"+
                "%0A"+"Subtotal: "+otvSubTotal.getText()+
                "%0A"+"- Deudas: "+otvDeuda.getText()+
                "%0A"+"- Préstamos: "+otvPrestamos.getText()+
                "%0A"+"- Coffee Express: "+obCoffeeExpress.getText()+
                "%0A"+"- Descuentos: "+obDescuentos.getText()+
                "%0A"+"+ Bonificaciones: "+obBonificaciones.getText()+
                "%0A"+
                "%0A"+"Pago Total: "+otvTotalPago.getText()+
                "%0A"+"Promedio de Tz: "+otvPromedioTz.getText()+
                "%0A"+
                "%0A"+"Tu Ahorro actual: "+listItemsDistribuidor.getiAhorro()+
                "%0A"+
                "%0AGracias por tu trabajo, esto no sería posible sin ti... Ve por más!!!";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+lCelular +"&text="+sMensaje));
        startActivity(intent);
    }
    void fActualizarDistribuidor(String sCierreAbierto){
        DocumentReference bd_NuevoPersonal = FirebaseFirestore.getInstance()
                .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado);

        Map<String,Object> bd_GuardarDistribuidor=new HashMap<String, Object>();
        bd_GuardarDistribuidor.put("Deuda Actual",(0));

        bd_NuevoPersonal.update(bd_GuardarDistribuidor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityNomina.this,"Datos de Distribuidor actualizados",Toast.LENGTH_SHORT).show();
                fActualizarCierres();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityNomina.this,"Se presentaron fallas en el distribuidor",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void fActualizarCierres(){


        String sNombreCierre;

        for (int i=0;i<myAdapterCierres.getCount();i++){
            sNombreCierre=alCierres.get(i).getsNombreCierre();

            DocumentReference bd_NuevoPersonal = FirebaseFirestore.getInstance()
                    .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cierres/"+sNombreCierre);

            Map<String,Object> bd_GuardarDistribuidor=new HashMap<String, Object>();
            bd_GuardarDistribuidor.put("Pagado","SI");

            bd_NuevoPersonal.update(bd_GuardarDistribuidor).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ActivityNomina.this,"Datos de Distribuidor actualizados",Toast.LENGTH_SHORT).show();
                    fBorrarTodo();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ActivityNomina.this,"Se presentaron fallas en el distribuidor",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    // ############################################################################################  CALENDARIO
    ArrayList alDatosCalendario=new ArrayList();
    ArrayList fCalendario(){

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
        SimpleDateFormat sdf2=new SimpleDateFormat("HHmmddssMMyy");
        String sHora=sdf.format(Date.getTime());
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
        return(alDatosCalendario);
    }

}
