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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.TRANSPARENT;

public class ActivityEntregaNoche extends AppCompatActivity implements View.OnClickListener {
//#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    ListView olvDistribuidores;
    TextView otvDistribuidorSeleccionado;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_000;
    Button obEntregaTazas,obDevuelveTazas,obDinero,obBorrar;
    ImageButton obEnviar;

    //#########################################################################################     Variables Globales
    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;
    int iTeclaAccionada=0;

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_noche);

        //#####################################################################################     Relación de objetos con Layout
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);
        otvDistribuidorSeleccionado=(TextView)findViewById(R.id.tvDistribuidorSeleccionado);
        obEntregaTazas=(Button)findViewById(R.id.bEntregaTazas);
        obDevuelveTazas=(Button)findViewById(R.id.bDevuelveTazas);
        obDinero=(Button)findViewById(R.id.bDinero);
        obBorrar=(Button)findViewById(R.id.bBorrar);
        obEnviar=(ImageButton)findViewById(R.id.bEnviar);

        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_000=(Button)findViewById(R.id.bTecla_000);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);obTecla_000.setOnClickListener(this);


        //##################################################################################         Acciones iniciales
        fActualizar_LV_Distribuidores();
        obEntregaTazas.setText("100");
        iEntregaTazas=100;
        //##################################################################################         Acciones de botones
        obEntregaTazas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTeclaAccionada="EntregaTazas";
                obEntregaTazas.setBackgroundColor(GREEN);
                obDevuelveTazas.setBackgroundColor(GRAY);
                obDinero.setBackgroundColor(GRAY);
                sNumero="";
                iNumero=0;
            }
        });
        obDevuelveTazas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTeclaAccionada="DevuelveTazas";
                obEntregaTazas.setBackgroundColor(GRAY);
                obDevuelveTazas.setBackgroundColor(GREEN);
                obDinero.setBackgroundColor(GRAY);
                sNumero="";
                iNumero=0;
            }
        });
        obDinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTeclaAccionada="Dinero";
                obEntregaTazas.setBackgroundColor(GRAY);
                obDevuelveTazas.setBackgroundColor(GRAY);
                obDinero.setBackgroundColor(GREEN);
                sNumero="";
                iNumero=0;
            }
        });
        obBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvDistribuidorSeleccionado.setText("");
                sDistribuidorSeleccionado="";
                obEntregaTazas.setText("");
                obDevuelveTazas.setText("");
                obDinero.setText("");
                sNumero="";
                iNumero=0;
                iEntregaTazas=0;iDevuelveTazas=0;iDinero=0;
            }
        });

        obEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fVerificarCondiciones();
                //Dentro de Verificar, luego va a fAdquirirEntregados();
                //Dentro de adquirir, luego va a fGuardar();
            }
        });
        //###################################################################################       Acciones de los ListView
        olvDistribuidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object oDistribuidorSeleccionado= olvDistribuidores.getItemAtPosition(position);
                sDistribuidorSeleccionado=oDistribuidorSeleccionado.toString();

                otvDistribuidorSeleccionado.setText(sDistribuidorSeleccionado.toUpperCase());
            }
        });
    }
    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################
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
                            ActivityEntregaNoche.this,android.R.layout.simple_list_item_1,
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
    int iNumero=0,iDinero=0,iEntregaTazas=0,iDevuelveTazas=0;
    String sTeclaAccionada="";

    public void onClick(View view){
        if (sTeclaAccionada==""){
            Toast.makeText(ActivityEntregaNoche.this,"Selecciona una tecla!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            switch (view.getId()){
                case R.id.bTecla_0:sNumero=sNumero+"0";break;
                case R.id.bTecla_1:sNumero=sNumero+"1";break;
                case R.id.bTecla_2:sNumero=sNumero+"2";break;
                case R.id.bTecla_3:sNumero=sNumero+"3";break;
                case R.id.bTecla_4:sNumero=sNumero+"4";break;
                case R.id.bTecla_5:sNumero=sNumero+"5";break;
                case R.id.bTecla_6:sNumero=sNumero+"6";break;
                case R.id.bTecla_7:sNumero=sNumero+"7";break;
                case R.id.bTecla_8:sNumero=sNumero+"8";break;
                case R.id.bTecla_9:sNumero=sNumero+"9";break;
                case R.id.bTecla_000:sNumero=sNumero+"000";break;
            }
            fValores();
        }
    }
    void fValores(){
        iNumero = Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        if (sTeclaAccionada == "Dinero") {
            obDinero.setText("$ " + imNumero);
            iDinero=iNumero;
        }
        if (sTeclaAccionada == "EntregaTazas") {
            obEntregaTazas.setText(sNumero);
            iEntregaTazas=iNumero;
        }
        if (sTeclaAccionada == "DevuelveTazas") {
            obDevuelveTazas.setText(sNumero);
            iDevuelveTazas=iNumero;
        }
    }

    // ############################################################################################  VERIFICAR, ADQUIRIR, GUARDAR Y ENVIAR
    int[] iDatosIngresados=new int[3];
    void fVerificarCondiciones(){
        if (TextUtils.isEmpty(sDistribuidorSeleccionado)) {
            Toast.makeText(ActivityEntregaNoche.this,
                    "Debes seleccionar un Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        iDatosIngresados[0] = iEntregaTazas;
        iDatosIngresados[1] = iDevuelveTazas;
        iDatosIngresados[2] = iDinero;

        int iSumaProductos=0;
        for(int i=0;i<iDatosIngresados.length;i++){
            iSumaProductos=iSumaProductos+iDatosIngresados[i];
        }
        if (iSumaProductos==0) {
            Toast.makeText(this, "No haz agregado ningún valor", Toast.LENGTH_SHORT).show();
            return;
        }
        fAdquirirEntregados();

    }
    void fAdquirirEntregados(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado;
        DocumentReference bd_Datos=FirebaseFirestore.getInstance().document(sPath);
        final int[][] iOrden = {new int[17]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document=task.getResult();
                    if (document.exists()) {
                        Distribuidor_Entidad listItemsDistribuidor;
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
                                listSet.get(iOrden[0][16]).toString());
                        fGuardar(listItemsDistribuidor);
                    } else {
                        Log.d("Distribuidor", "No such document");
                    }
                } else {
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

            }
        }
        return iOrden;
    }
    void fGuardar(final Distribuidor_Entidad listDatos){
        //---------  Condiciones
        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaNoche.this);
        progressDialog.setMessage("Guardando datos entregados");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //---------  Nuevo personal
        DocumentReference bd_NuevasEntregas = FirebaseFirestore.getInstance()
                .document("Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado);

        Map<String,Object> bd_GuardarEntregados=new HashMap<String, Object>();
        bd_GuardarEntregados.put("Ent Tazas",iDatosIngresados[0]+listDatos.getiEntTazas());
        bd_GuardarEntregados.put("Dev Tazas",iDatosIngresados[1]+listDatos.getiDevTazas());
        bd_GuardarEntregados.put("Ent Dinero",iDatosIngresados[2]+listDatos.getiEntDinero());

        bd_NuevasEntregas.update(bd_GuardarEntregados).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaNoche.this,"Registro guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
                otvDistribuidorSeleccionado.setText("");
                sDistribuidorSeleccionado="";
                obEntregaTazas.setText("");
                obDevuelveTazas.setText("");
                obDinero.setText("");
                sNumero="";
                iNumero=0;
                iEntregaTazas=0;iDevuelveTazas=0;iDinero=0;

                progressDialog.cancel();
                fEnviarMensaje(listDatos);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaNoche.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fEnviarMensaje(Distribuidor_Entidad listDatos){

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iDatosIngresados[2]);

        Long lCelular=listDatos.getlCelular();
        String[] sTextosWApp=new String[4];
        sTextosWApp[0]="Tz Ent: "+iDatosIngresados[0];
        sTextosWApp[1]=",  Tz Dev: "+iDatosIngresados[1];
        sTextosWApp[2]="%0ATz Ven: "+(iDatosIngresados[0]-iDatosIngresados[0]);
        sTextosWApp[3]="%0ADinero Ent: "+imNumero;

        String sMensaje="Hola " + listDatos.getsNombre() + ", Terminaste el turno con los siguientes datos:%0A"+
                sTextosWApp[0]+sTextosWApp[1]+sTextosWApp[2]+sTextosWApp[3];

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+lCelular +"&text="+sMensaje));
        startActivity(intent);
    }
 }
