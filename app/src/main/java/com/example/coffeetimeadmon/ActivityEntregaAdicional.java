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

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;

public class ActivityEntregaAdicional extends AppCompatActivity implements View.OnClickListener{
//#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    ListView olvDistribuidores;
    TextView otvDistribuidorSeleccionado;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9,obTecla_000;
    Button obDinero,obBorrar;
    ImageButton obEnviar;

    //#########################################################################################     Variables Globales
    ArrayList alDistribuidores=new ArrayList<>();
    String sDistribuidorSeleccionado;

    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_adicional);
        //#####################################################################################     Relación de objetos con Layout
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);
        otvDistribuidorSeleccionado=(TextView)findViewById(R.id.tvDistribuidorSeleccionado);
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
        //##################################################################################         Acciones de botones
        obBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otvDistribuidorSeleccionado.setText("");
                sDistribuidorSeleccionado="";
                obDinero.setText("");
                sNumero="";
                iNumero=0;
                iDinero=0;
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
                            ActivityEntregaAdicional.this,android.R.layout.simple_list_item_1,
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
    int iNumero=0,iDinero=0;

    public void onClick(View view){
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
    void fValores(){
        iNumero = Integer.parseInt(sNumero);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iNumero);
        obDinero.setText("$ " + imNumero);
        iDinero=iNumero;
    }

    // ############################################################################################  VERIFICAR, ADQUIRIR, GUARDAR Y ENVIAR
    void fVerificarCondiciones(){
        if (TextUtils.isEmpty(sDistribuidorSeleccionado)) {
            Toast.makeText(ActivityEntregaAdicional.this,
                    "Debes seleccionar un Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        if (iDinero==0) {
            Toast.makeText(this, "No haz agregado ningún valor", Toast.LENGTH_SHORT).show();
            return;
        }
        fAdquirirEntregados();

    }
    void fAdquirirEntregados(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado;
        DocumentReference bd_Datos=FirebaseFirestore.getInstance().document(sPath);
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
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaAdicional.this);
        progressDialog.setMessage("Guardando datos entregados");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //---------  Nuevo personal
        DocumentReference bd_NuevasEntregas = FirebaseFirestore.getInstance()
                .document("Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sDistribuidorSeleccionado);

        Map<String,Object> bd_GuardarEntregados=new HashMap<String, Object>();
        bd_GuardarEntregados.put("Ent Dinero",iDinero+listDatos.getiEntDinero());

        bd_NuevasEntregas.update(bd_GuardarEntregados).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaAdicional.this,"Registro guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
                fObtenerCuentas();
                progressDialog.cancel();
                fEnviarMensaje(listDatos);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaAdicional.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fEnviarMensaje(Distribuidor_Entidad listDatos){

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String imNumero;
        imNumero = decimalFormat.format(iDinero);

        Long lCelular=listDatos.getlCelular();
        String[] sTextosWApp=new String[1];
        sTextosWApp[0]="Dinero Ent: "+imNumero;

        String sMensaje="Hola " + listDatos.getsNombre() + ", Entregaste dinero adicional por valor de:%0A"+
                sTextosWApp[0];

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+lCelular +"&text="+sMensaje));
        startActivity(intent);
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

        iEfectivo =iEfectivo+iDinero;

        Map<String,Object> bd_GuardarCuenta=new HashMap<String, Object>();
        bd_GuardarCuenta.put("Efectivo",iEfectivo);

        bd_NuevaCuenta.update(bd_GuardarCuenta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                otvDistribuidorSeleccionado.setText("");
                sDistribuidorSeleccionado="";
                obDinero.setText("");
                sNumero="";
                iNumero=0;
                iDinero=0;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaAdicional.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void fGuardarMovimiento(){

        final ArrayList alDatosCalendario=fCalendario();
        final String sMesColeccion=alDatosCalendario.get(0)+"-"+alDatosCalendario.get(4);
        final String sFechaDocumento=alDatosCalendario.get(2)+"-"+alDatosCalendario.get(3);
        final String sHoraDocumento=alDatosCalendario.get(8).toString();

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityEntregaAdicional.this);
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
        bd_GuardarPersonal.put("Descripción","Entrega Adicional");
        bd_GuardarPersonal.put("Cantidad",1);
        bd_GuardarPersonal.put("ValorUND",iDinero);
        bd_GuardarPersonal.put("Total",iDinero);

        bd_NuevoMovimiento.set(bd_GuardarPersonal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityEntregaAdicional.this,"Movimiento creado satisfactoriamente",Toast.LENGTH_SHORT).show();
                otvDistribuidorSeleccionado.setText("");
                sDistribuidorSeleccionado="";
                obDinero.setText("");
                sNumero="";
                iNumero=0;
                iDinero=0;
                progressDialog.cancel();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityEntregaAdicional.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
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
