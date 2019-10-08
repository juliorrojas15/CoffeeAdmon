package com.example.coffeetimeadmon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.icu.lang.UCharacter.JoiningType.TRANSPARENT;

public class ActivityCaja extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onResume(){
        super.onResume();
        final ArrayList alDatosCalendario=fCalendario();
        final String sMesColeccion=alDatosCalendario.get(0)+"-"+alDatosCalendario.get(4);
        final String sFechaDocumento=alDatosCalendario.get(2)+"-"+alDatosCalendario.get(3);
        fActualizar_LV_Movimientos(sMesColeccion,sFechaDocumento);
    }


    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    RadioGroup orgTipoMovimiento;
    RadioButton orbEntrada,orbSalida;
    Button obContar;
    ListView olvCategoria_1,olvCategoria_2;
    TextView oetDescripcion;
    Button obCantidad,obValorUND;
    TextView otvTotal;
    Button obGuardar;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_10,obTecla_20,obTecla_25,obTecla_50,obTecla_000;
    ListView olvMovimientos;
    Button obDia,obMes,obAño,obFiltrar;

    //#########################################################################################     Variables Globales
    String sTipoMovimiento;
    ArrayList alCategoria_1 =new ArrayList<>();
    String sCategoria_1_Seleccionada="";
    ArrayList alCategoria_2 =new ArrayList<>();
    ArrayList alDefinitivaCategoria_2 =new ArrayList<>();
    String sCategoria_2_Seleccionada="";

    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;
    ArrayList alMovimientos=new ArrayList<>();

    int iCantidad=0,iValorUND=0,iTotal=0,iDia=0,iMes=0,iAño=0;

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);
        //#####################################################################################     Relación de objetos con Layout
        orgTipoMovimiento=(RadioGroup)findViewById(R.id.rgTipoMovimiento);
        orbEntrada=(RadioButton)findViewById(R.id.rbEntrada);
        orbSalida=(RadioButton)findViewById(R.id.rbSalida);
        obContar=(Button)findViewById(R.id.bContar);
        olvCategoria_1=(ListView)findViewById(R.id.lvCategoria_1);
        olvCategoria_2=(ListView)findViewById(R.id.lvCategoria_2);

        oetDescripcion=(EditText)findViewById(R.id.etDescripcion);
        obCantidad=(Button)findViewById(R.id.bCantidad);
        obValorUND=(Button)findViewById(R.id.bValorUND);
        otvTotal=(TextView)findViewById(R.id.tvTotal);
        obGuardar=(Button)findViewById(R.id.bGuardar);

        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_10=(Button)findViewById(R.id.bTecla_10);obTecla_20=(Button)findViewById(R.id.bTecla_20);
        obTecla_25=(Button)findViewById(R.id.bTecla_25);obTecla_50=(Button)findViewById(R.id.bTecla_50);
        obTecla_000=(Button)findViewById(R.id.bTecla_000);

        olvMovimientos=(ListView)findViewById(R.id.lvMovimientos);

        obDia=(Button)findViewById(R.id.bDia);
        obMes=(Button)findViewById(R.id.bMes);
        obAño=(Button)findViewById(R.id.bAño);
        obFiltrar=(Button)findViewById(R.id.bFiltrar);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);obTecla_10.setOnClickListener(this);obTecla_20.setOnClickListener(this);
        obTecla_25.setOnClickListener(this);obTecla_50.setOnClickListener(this);obTecla_000.setOnClickListener(this);

        obCantidad.setOnClickListener(this);
        obValorUND.setOnClickListener(this);
        obDia.setOnClickListener(this);
        obMes.setOnClickListener(this);
        obAño.setOnClickListener(this);

        final Window wWindow= this.getWindow();
        wWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //##################################################################################         Acciones iniciales
        fActualizar_LV_Distribuidores();


        //##################################################################################         Acciones de botones
        orgTipoMovimiento.setOnCheckedChangeListener(new  RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                wWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if(alCategoria_1.size()>0) {
                    alCategoria_1.clear();
                }
                if(alCategoria_2.size()>0) {
                    alCategoria_2.clear();
                }
                if (orbEntrada.isChecked()){
                    sTipoMovimiento="Entrada";
                }
                if (orbSalida.isChecked()){
                    sTipoMovimiento="Salidas";
                }
                fCategoria_1();

            }
        });


        obContar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCaja.this,ActivityConteo.class);
                startActivity(Destino);
            }
        });

        obGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!orbEntrada.isChecked() && !orbSalida.isChecked()){
                    Toast.makeText(ActivityCaja.this,"Selecciona un tipo de movimiento",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sCategoria_1_Seleccionada.equals("")){
                    Toast.makeText(ActivityCaja.this,"Selecciona una categoría",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (alCategoria_2.size()>0 && sCategoria_2_Seleccionada.equals("")){
                    Toast.makeText(ActivityCaja.this,"Selecciona una Sub categoría",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (iCantidad==0){
                    Toast.makeText(ActivityCaja.this,"Agrega una cantidad",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (iValorUND==0){
                    Toast.makeText(ActivityCaja.this,"Inidica un valor unitario",Toast.LENGTH_SHORT).show();
                    return;
                }
                fGuardar();
            }
        });

        //###################################################################################       Acciones de los ListView
        olvCategoria_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i= 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(TRANSPARENT);
                }
                view.setBackgroundColor(GREEN);

                sCategoria_2_Seleccionada="";
                sCategoria_1_Seleccionada=olvCategoria_1.getItemAtPosition(position).toString();
                if(alCategoria_2.size()>0) {
                    alCategoria_2.clear();
                }
                fCategoria_2();
            }
        });

        olvCategoria_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(TRANSPARENT);
                }
                view.setBackgroundColor(GREEN);

                sCategoria_2_Seleccionada=olvCategoria_2.getItemAtPosition(position).toString();
            }
        });

    }
    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################

    //  ###########################################################################################     LISTADO DE DISTRIBUIDORES
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
                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });


    }

    //  ###########################################################################################     CATEGORÍA 1
    void fCategoria_1(){

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/"+sTipoMovimiento+"/"+sTipoMovimiento;
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);

        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String sNombreDoc = document.getId();
                        alCategoria_1.add(sNombreDoc);
                    }
                    ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
                            ActivityCaja.this,android.R.layout.simple_list_item_1,
                            android.R.id.text1, alCategoria_1);
                    olvCategoria_1.setAdapter(myAdapter);

                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
    }

    //  ###########################################################################################     CATEGORÍA 2
    void fCategoria_2(){

        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/"+
                sTipoMovimiento+"/"+sTipoMovimiento;
        DocumentReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath).document(sCategoria_1_Seleccionada);

        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document=task.getResult();
                    if(document.exists()){
                        Collection collection = document.getData().keySet();
                        List listSet=new ArrayList<>(collection);
                        for(int i=0;i<listSet.size();i++){
                            alCategoria_2.add(listSet.get(i));
                        }
                        if(sTipoMovimiento.equals("Entrada")){
                            if(sCategoria_1_Seleccionada.contains("Distribuidor")){
                                alDefinitivaCategoria_2=alDistribuidores;
                            }
                            else{
                                alDefinitivaCategoria_2=alCategoria_2;
                            }
                        }
                        if(sTipoMovimiento.equals("Salidas")){
                            if(sCategoria_1_Seleccionada.contains("Nómina")||sCategoria_1_Seleccionada.contains("Préstamos")){
                                alDefinitivaCategoria_2=alDistribuidores;
                            }
                            else{
                                alDefinitivaCategoria_2=alCategoria_2;
                            }
                        }
                    }
                    ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
                            ActivityCaja.this,android.R.layout.simple_list_item_1,
                            android.R.id.text1, alDefinitivaCategoria_2);
                    olvCategoria_2.setAdapter(myAdapter);

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
            case R.id.bTecla_10:sNumero=sNumero+"10";fValores();break;
            case R.id.bTecla_20:sNumero=sNumero+"20";fValores();break;
            case R.id.bTecla_25:sNumero=sNumero+"25";fValores();break;
            case R.id.bTecla_50:sNumero=sNumero+"50";fValores();break;
            case R.id.bTecla_000:sNumero=sNumero+"000";fValores();break;

            case R.id.bCantidad:sTeclaAccionada="Cantidad";fSeleccion();break;
            case R.id.bValorUND:sTeclaAccionada="ValorUND";fSeleccion();break;
            case R.id.bDia:sTeclaAccionada="Dia";fSeleccion();break;
            case R.id.bMes:sTeclaAccionada="Mes";fSeleccion();break;
            case R.id.bAño:sTeclaAccionada="Año";fSeleccion();break;
        }
    }
    void fValores(){
        if(sTeclaAccionada==""){
            Toast.makeText(ActivityCaja.this,"Selecciona una casilla!",Toast.LENGTH_SHORT).show();
            return;
        }
        iNumero=Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        switch (sTeclaAccionada){
            case "Cantidad":obCantidad.setText(sNumero);iCantidad=iNumero;break;
            case "ValorUND":obValorUND.setText("$ "+imNumero);iValorUND=iNumero;break;
            case "Dia":obDia.setText(sNumero);iDia=iNumero;break;
            case "Mes":obMes.setText(sNumero);iMes=iNumero;break;
            case "Año":obAño.setText(sNumero);iAño=iNumero;break;
        }
        fCalculos();
    }
    void fSeleccion(){
        iNumero=0;
        sNumero="";
        switch (sTeclaAccionada){
            case "Cantidad":fBackGround();obCantidad.setBackgroundColor(GREEN);break;
            case "ValorUND":fBackGround();obValorUND.setBackgroundColor(GREEN);break;
            case "Dia":fBackGround();obDia.setBackgroundColor(GREEN);break;
            case "Mes":fBackGround();obMes.setBackgroundColor(GREEN);break;
            case "Año":fBackGround();obAño.setBackgroundColor(GREEN);break;
           }
    }
    void fBackGround(){
        obCantidad.setBackgroundResource(R.drawable.boton_gris);
        obValorUND.setBackgroundResource(R.drawable.boton_gris);
        obDia.setBackgroundResource(R.drawable.boton_gris);
        obMes.setBackgroundResource(R.drawable.boton_gris);
        obAño.setBackgroundResource(R.drawable.boton_gris);
    }

    // ############################################################################################  CÁLCULOS
    void fCalculos(){
        iTotal =iCantidad*iValorUND;
        fTextoVentas();
    }
    String imTotal;
    void fTextoVentas(){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        imTotal = decimalFormat.format(iTotal);
        otvTotal.setText("$ "+imTotal);
    }

    //#########################################################################################     MOVIMIENTO
    void fGuardar(){

        final ArrayList alDatosCalendario=fCalendario();
        final String sMesColeccion=alDatosCalendario.get(0)+"-"+alDatosCalendario.get(4);
        final String sFechaDocumento=alDatosCalendario.get(2)+"-"+alDatosCalendario.get(3);
        final String sHoraDocumento=alDatosCalendario.get(5).toString();

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityCaja.this);
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
        bd_GuardarPersonal.put("Tipo de Movimiento",sTipoMovimiento);
        bd_GuardarPersonal.put("Categoria_1",sCategoria_1_Seleccionada);
        bd_GuardarPersonal.put("Categoria_2",sCategoria_2_Seleccionada);
        bd_GuardarPersonal.put("Descripción",oetDescripcion.getText().toString());
        bd_GuardarPersonal.put("Cantidad",iCantidad);
        bd_GuardarPersonal.put("ValorUND",iValorUND);
        bd_GuardarPersonal.put("Total",iTotal);

        bd_NuevoMovimiento.set(bd_GuardarPersonal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityCaja.this,"Movimiento creado satisfactoriamente",Toast.LENGTH_SHORT).show();
                if (alMovimientos.size()>0){
                    alMovimientos.clear();
                }
                fActualizar_LV_Movimientos(sMesColeccion,sFechaDocumento);
                progressDialog.cancel();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityCaja.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }

    Movimientos_Adaptador myAdapterMovimientos;
    void fActualizar_LV_Movimientos(String sMesColeccion,String sDiaDocumento){
        String sPath="Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Cuentas/Movimientos/"+
                sMesColeccion+"/"+sDiaDocumento+"/Movimientos";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        final int[][] iOrden = {new int[10]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Movimiento_Entidad listItemsMovimientos;
                    int iRevisarKeyz=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<String> aKeys=new ArrayList<String>(document.getData().keySet());
                        Collection collection=document.getData().values();

                        if (iRevisarKeyz==0) {
                            iOrden[0] = fKeysMovimientos(aKeys);
                            iRevisarKeyz=1;
                        }
                        List listSet=new ArrayList<>(collection);
                        listItemsMovimientos=new Movimiento_Entidad(
                                listSet.get(iOrden[0][0]).toString(),
                                listSet.get(iOrden[0][1]).toString(),
                                listSet.get(iOrden[0][2]).toString(),
                                listSet.get(iOrden[0][3]).toString(),
                                listSet.get(iOrden[0][4]).toString(),
                                listSet.get(iOrden[0][5]).toString(),
                                listSet.get(iOrden[0][6]).toString(),
                                Integer.parseInt(listSet.get(iOrden[0][7]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][8]).toString()),
                                Integer.parseInt(listSet.get(iOrden[0][9]).toString()));
                        alMovimientos.add(listItemsMovimientos);
                    }
                    myAdapterMovimientos = new Movimientos_Adaptador(ActivityCaja.this, alMovimientos);
                    olvMovimientos.setAdapter(myAdapterMovimientos);
                    fObtenerCuentas();

                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });


    }
    int[] fKeysMovimientos(ArrayList<String> arrayList){
        int[] iOrden = new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){

            String sKeys=arrayList.get(i);
            switch (sKeys){
                case "Mes":iOrden[0]=i;break;
                case "Fecha":iOrden[1]=i;break;
                case "Hora":iOrden[2]=i;break;
                case "Tipo de Movimiento":iOrden[3]=i;break;
                case "Categoria_1":iOrden[4]=i;break;
                case "Categoria_2":iOrden[5]=i;break;
                case "Descripción":iOrden[6]=i;break;
                case "Cantidad":iOrden[7]=i;break;
                case "ValorUND":iOrden[8]=i;break;
                case "Total":iOrden[9]=i;break;
            }
        }
        return iOrden;
    }
    // ############################################################################################  ACTUALIZAR CUENTAS
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

        if(sCategoria_1_Seleccionada.equals("Ent. por Retiro Bancario")){
            iEfectivo=iTotal+iEfectivo;
            iBancos=iBancos-iTotal;
        }
        else{
            if(sCategoria_1_Seleccionada.equals("Sal. por Consignación")){
                iEfectivo=iEfectivo-iTotal;
                iBancos=iBancos+iTotal;
            }
            else{
                if (sCategoria_1_Seleccionada.contains("Ent")){
                    iEfectivo =iEfectivo+iTotal;
                }
                else{
                    iEfectivo =iEfectivo-iTotal;
                }

            }
        }

        Map<String,Object> bd_GuardarCuenta=new HashMap<String, Object>();
        bd_GuardarCuenta.put("Efectivo",iEfectivo);
        bd_GuardarCuenta.put("Bancos",iBancos);

        bd_NuevaCuenta.update(bd_GuardarCuenta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                oetDescripcion.setText("");
                obCantidad.setText("1");
                iCantidad=1;
                obValorUND.setText("$ 0");
                iValorUND=0;
                iTotal=0;
                iNumero=0;
                sNumero="";
                otvTotal.setText("$ 0");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityCaja.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
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
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
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
