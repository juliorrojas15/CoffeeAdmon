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
import android.widget.Adapter;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.TRANSPARENT;

public class ActivityEntregaInsumos extends AppCompatActivity implements View.OnClickListener{
    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    ListView olvDistribuidores;
    TextView otvDistribuidorSeleccionado;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9;
    ImageButton obAromatica,obInstacrem,obMantecada,obLiberal,obAlmojabana,obArepa,obMustang,obLucky;
    TextView otvAromatica,otvInstacrem,otvMantecada,otvLiberal,otvAlmojabana,otvArepa,otvMustang,otvLucky;
    ImageButton obEnviar;
    Button obBorrar;

    //#########################################################################################     Variables Globales
    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;
    int[] iProductosIngresados=new int[8];

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_insumos);

        //#####################################################################################     Relación de objetos con Layout
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);
        otvDistribuidorSeleccionado=(TextView)findViewById(R.id.tvDistribuidorSeleccionado);
        otvAromatica=(TextView)findViewById(R.id.tvAromatica);
        otvInstacrem=(TextView)findViewById(R.id.tvInstacrem);
        otvMantecada=(TextView)findViewById(R.id.tvMantecada);
        otvLiberal=(TextView)findViewById(R.id.tvLiberal);
        otvAlmojabana=(TextView)findViewById(R.id.tvAlmojabana);
        otvArepa=(TextView)findViewById(R.id.tvArepa);
        otvMustang=(TextView)findViewById(R.id.tvMustang);
        otvLucky=(TextView)findViewById(R.id.tvLucky);

        obTecla_0=(Button)findViewById(R.id.bTecla_0);obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);obTecla_9=(Button)findViewById(R.id.bTecla_9);

        obAromatica=(ImageButton)findViewById(R.id.bAromática);obInstacrem=(ImageButton)findViewById(R.id.bInstacrem);
        obMantecada=(ImageButton)findViewById(R.id.bMantecada);obLiberal=(ImageButton)findViewById(R.id.bLiberal);
        obAlmojabana=(ImageButton)findViewById(R.id.bAlmojabana);obArepa=(ImageButton)findViewById(R.id.bArepa);
        obMustang=(ImageButton)findViewById(R.id.bMustang);obLucky=(ImageButton)findViewById(R.id.bLucky);

        obEnviar=(ImageButton)findViewById(R.id.bEnviar);
        obBorrar=(Button)findViewById(R.id.bBorrar);

        obTecla_0.setOnClickListener(this);obTecla_1.setOnClickListener(this);obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);obTecla_4.setOnClickListener(this);obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);obTecla_7.setOnClickListener(this);obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);

        obAromatica.setOnClickListener(this);obInstacrem.setOnClickListener(this);
        obMantecada.setOnClickListener(this);obLiberal.setOnClickListener(this);
        obAlmojabana.setOnClickListener(this);obArepa.setOnClickListener(this);
        obMustang.setOnClickListener(this);obLucky.setOnClickListener(this);

        //##################################################################################         Acciones iniciales
        fActualizar_LV_Distribuidores();
        //##################################################################################         Acciones de botones
        obEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fVerificarCondiciones();
                //Dentro de Verificar, luego va a fAdquirirEntregados();
                //Dentro de adquirir, luego va a fGuardar();
            }
        });

        obBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvDistribuidorSeleccionado.setText("");
                otvAromatica.setText("0");
                otvInstacrem.setText("0");
                otvMantecada.setText("0");
                otvLiberal.setText("0");
                otvAlmojabana.setText("0");
                otvArepa.setText("0");
                otvMustang.setText("0");
                otvLucky.setText("0");
                sDistribuidorSeleccionado="";
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
                            ActivityEntregaInsumos.this,android.R.layout.simple_list_item_1,
                            android.R.id.text1,alDistribuidores);
                    olvDistribuidores.setAdapter(myAdapter);

                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });


    }

    int iValor=0;
    int iProducto=0;

    // ############################################################################################     FUNCIONAMIENTO PAGINA
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bTecla_0:iValor=0;fValores();break;
            case R.id.bTecla_1:iValor=1;fValores();break;
            case R.id.bTecla_2:iValor=2;fValores();break;
            case R.id.bTecla_3:iValor=3;fValores();break;
            case R.id.bTecla_4:iValor=4;fValores();break;
            case R.id.bTecla_5:iValor=5;fValores();break;
            case R.id.bTecla_6:iValor=6;fValores();break;
            case R.id.bTecla_7:iValor=7;fValores();break;
            case R.id.bTecla_8:iValor=8;fValores();break;
            case R.id.bTecla_9:iValor=9;fValores();break;
            case R.id.bAromática:iProducto=1;fSeleccion();break;
            case R.id.bInstacrem:iProducto=2;fSeleccion();break;
            case R.id.bMantecada:iProducto=3;fSeleccion();break;
            case R.id.bLiberal:iProducto=4;fSeleccion();break;
            case R.id.bAlmojabana:iProducto=5;fSeleccion();break;
            case R.id.bArepa:iProducto=6;fSeleccion();break;
            case R.id.bMustang:iProducto=7;fSeleccion();break;
            case R.id.bLucky:iProducto=8;fSeleccion();break;
        }
    }
    void fValores(){
        if(iProducto==0){
            Toast.makeText(ActivityEntregaInsumos.this,"Selecciona un producto!",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (iProducto){
            case 1:otvAromatica.setText(String.valueOf(iValor));break;
            case 2:otvInstacrem.setText(String.valueOf(iValor));break;
            case 3:otvMantecada.setText(String.valueOf(iValor));break;
            case 4:otvLiberal.setText(String.valueOf(iValor));break;
            case 5:otvAlmojabana.setText(String.valueOf(iValor));break;
            case 6:otvArepa.setText(String.valueOf(iValor));break;
            case 7:otvMustang.setText(String.valueOf(iValor));break;
            case 8:otvLucky.setText(String.valueOf(iValor));break;
        }
    }
    void fSeleccion(){
        switch (iProducto){
            case 1:fBackGround();otvAromatica.setBackgroundColor(GREEN);obAromatica.setBackgroundColor(GREEN);break;
            case 2:fBackGround();otvInstacrem.setBackgroundColor(GREEN);obInstacrem.setBackgroundColor(GREEN);break;
            case 3:fBackGround();otvMantecada.setBackgroundColor(GREEN);obMantecada.setBackgroundColor(GREEN);break;
            case 4:fBackGround();otvLiberal.setBackgroundColor(GREEN);obLiberal.setBackgroundColor(GREEN);break;
            case 5:fBackGround();otvAlmojabana.setBackgroundColor(GREEN);obAlmojabana.setBackgroundColor(GREEN);break;
            case 6:fBackGround();otvArepa.setBackgroundColor(GREEN);obArepa.setBackgroundColor(GREEN);break;
            case 7:fBackGround();otvMustang.setBackgroundColor(GREEN);obMustang.setBackgroundColor(GREEN);break;
            case 8:fBackGround();otvLucky.setBackgroundColor(GREEN);obLucky.setBackgroundColor(GREEN);break;
        }
    }
    void fBackGround(){
        otvAromatica.setBackgroundColor(TRANSPARENT);obAromatica.setBackgroundColor(TRANSPARENT);
        otvInstacrem.setBackgroundColor(TRANSPARENT);obInstacrem.setBackgroundColor(TRANSPARENT);
        otvMantecada.setBackgroundColor(TRANSPARENT);obMantecada.setBackgroundColor(TRANSPARENT);
        otvLiberal.setBackgroundColor(TRANSPARENT);obLiberal.setBackgroundColor(TRANSPARENT);
        otvAlmojabana.setBackgroundColor(TRANSPARENT);obAlmojabana.setBackgroundColor(TRANSPARENT);
        otvArepa.setBackgroundColor(TRANSPARENT);obArepa.setBackgroundColor(TRANSPARENT);
        otvMustang.setBackgroundColor(TRANSPARENT);obMustang.setBackgroundColor(TRANSPARENT);
        otvLucky.setBackgroundColor(TRANSPARENT);obLucky.setBackgroundColor(TRANSPARENT);
    }


    // ############################################################################################  VERIFICAR, ADQUIRIR, GUARDAR Y ENVIAR
    void fVerificarCondiciones(){
        if (TextUtils.isEmpty(sDistribuidorSeleccionado)) {
            Toast.makeText(ActivityEntregaInsumos.this,
                    "Debes seleccionar un Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        iProductosIngresados[0] = Integer.parseInt(otvAromatica.getText().toString().trim());
        iProductosIngresados[1] = Integer.parseInt(otvInstacrem.getText().toString().trim());
        iProductosIngresados[2] = Integer.parseInt(otvMantecada.getText().toString().trim());
        iProductosIngresados[3] = Integer.parseInt(otvLiberal.getText().toString().trim());
        iProductosIngresados[4] = Integer.parseInt(otvAlmojabana.getText().toString().trim());
        iProductosIngresados[5] = Integer.parseInt(otvArepa.getText().toString().trim());
        iProductosIngresados[6] = Integer.parseInt(otvMustang.getText().toString().trim());
        iProductosIngresados[7] = Integer.parseInt(otvLucky.getText().toString().trim());

        int iSumaProductos=0;
        for(int i=0;i<iProductosIngresados.length;i++){
            iSumaProductos=iSumaProductos+iProductosIngresados[i];
        }
        if (iSumaProductos==0) {
            Toast.makeText(this, "No haz agregado ningún producto", Toast.LENGTH_SHORT).show();
            return;
        }
        fAdquirirEntregados();

    }
    void fAdquirirEntregados(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado;
        DocumentReference bd_Datos=FirebaseFirestore.getInstance().document(sPath);
        //CollectionReference bd_Datos= FirebaseFirestore.getInstance().document(sPath);
        final int[][] iOrden = {new int[18]};
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
                                listSet.get(iOrden[0][16]).toString(),
                                listSet.get(iOrden[0][17]).toString());
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
                case "Ultimo Cierre":iOrden[17]=i;break;
            }
        }
        return iOrden;
    }
    void fGuardar(final Distribuidor_Entidad listDatos){
        //---------  Condiciones
        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaInsumos.this);
        progressDialog.setMessage("Guardando productos entregados");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //---------  Nuevo personal
        DocumentReference bd_NuevasEntregas = FirebaseFirestore.getInstance()
                .document("Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado);

        Map<String,Object> bd_GuardarEntregados=new HashMap<String, Object>();
        bd_GuardarEntregados.put("Aromaticas",(iProductosIngresados[0]*25)+listDatos.getiAromaticas());
        bd_GuardarEntregados.put("Instacrem",(iProductosIngresados[1]*10)+listDatos.getiInstacrem());
        bd_GuardarEntregados.put("Mantecada",iProductosIngresados[2]+listDatos.getiMantecada());
        bd_GuardarEntregados.put("Liberal",iProductosIngresados[3]+listDatos.getiLiberal());
        bd_GuardarEntregados.put("Almojabana",iProductosIngresados[4]+listDatos.getiAlmojabana());
        bd_GuardarEntregados.put("Arepa",iProductosIngresados[5]+listDatos.getiArepa());
        bd_GuardarEntregados.put("Mustang",iProductosIngresados[6]+listDatos.getiMustang());
        bd_GuardarEntregados.put("Lucky",iProductosIngresados[7]+listDatos.getiLucky());

        bd_NuevasEntregas.update(bd_GuardarEntregados).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaInsumos.this,"Registro guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
                otvDistribuidorSeleccionado.setText("");
                otvAromatica.setText("0");
                otvInstacrem.setText("0");
                otvMantecada.setText("0");
                otvLiberal.setText("0");
                otvAlmojabana.setText("0");
                otvArepa.setText("0");
                otvMustang.setText("0");
                otvLucky.setText("0");
                sDistribuidorSeleccionado="";

                progressDialog.cancel();
                fEnviarMensaje(listDatos);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaInsumos.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fEnviarMensaje(Distribuidor_Entidad listDatos){

        Long lCelular=listDatos.getlCelular();
        String[] sTextosWApp=new String[8];
        sTextosWApp[0]=fTextosWApp(iProductosIngresados[0]*25,"Aromática: ");
        sTextosWApp[1]=fTextosWApp(iProductosIngresados[1]*10,"Instacrem: ");
        sTextosWApp[2]=fTextosWApp(iProductosIngresados[2],"Mantecada: ");
        sTextosWApp[3]=fTextosWApp(iProductosIngresados[3],"Liberal: ");
        sTextosWApp[4]=fTextosWApp(iProductosIngresados[4],"Almojabana: ");
        sTextosWApp[5]=fTextosWApp(iProductosIngresados[5],"Arepa: ");
        sTextosWApp[6]=fTextosWApp(iProductosIngresados[6],"Mustang: ");
        sTextosWApp[7]=fTextosWApp(iProductosIngresados[7],"Lucky: ");


        String sMensaje="Hola " + listDatos.getsNombre() + ", acabamos de entregarte:%0A"+
                sTextosWApp[0]+sTextosWApp[1]+sTextosWApp[2]+sTextosWApp[3]+
                sTextosWApp[4]+sTextosWApp[5]+sTextosWApp[6]+sTextosWApp[7];

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+lCelular +"&text="+sMensaje));
        startActivity(intent);
    }
    String fTextosWApp(int iCant, String sProducto){
        if (iCant>0){
            return sProducto+iCant+"%0A";
        }
        else{
            return "";
        }
    }
}
