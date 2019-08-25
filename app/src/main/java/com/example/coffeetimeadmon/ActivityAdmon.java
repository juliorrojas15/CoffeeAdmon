package com.example.coffeetimeadmon;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAdmon extends AppCompatActivity {
    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    EditText oetNombreDistribuidor,oetCelularDistribuidor;
    Button obCrearDistribuidor,obEliminarDistribuidor;
    TextView otvEliminarDistribuidor;
    ListView olvDistribuidores;

    EditText oetItemProducto,oetNombreProducto,oetPrecioProducto, oetComisiónProducto,
            oetCostoProducto,oetCantidadProducto;
    Button obCrearProducto,obEliminarProducto;
    TextView otvEliminarProducto;
    ListView olvProductos;

    //#########################################################################################     Variables Globales
    ArrayList<Distribuidor_Entidad> alDistribuidores=new ArrayList<>();
    Distribuidor_Adaptador_Crear myAdapterDistribuidor;
    int iDistribuidorSeleccionado;
    Object oArrayDistribuidorSeleccionado;
    Distribuidor_Entidad oDistribuidorSeleccionado;
    Distribuidor_Entidad listItemsDistribuidor;


    ArrayList<Productos_Entidad> alProductos=new ArrayList<>();
    Productos_Adaptador_Crear myAdapterProductos;
    int iProductoSeleccionado;
    Object oArrayProductoSeleccionado;
    Productos_Entidad oProductoSeleccionado;
    Productos_Entidad listItemsProductos;


    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admon);
        //#####################################################################################     Relación de objetos con Layout
        oetNombreDistribuidor=(EditText)findViewById(R.id.etNombreDistribuidor);
        oetCelularDistribuidor=(EditText)findViewById(R.id.etCelularDistribuidor);
        obCrearDistribuidor=(Button)findViewById(R.id.bCrearDistribuidor);
        obEliminarDistribuidor=(Button)findViewById(R.id.bEliminarDistribuidor);
        otvEliminarDistribuidor=(TextView)findViewById(R.id.tvEliminarDistribuidor);
        olvDistribuidores=(ListView)findViewById(R.id.lvDistribuidores);


        oetItemProducto=(EditText)findViewById(R.id.etItemProducto);
        oetNombreProducto=(EditText)findViewById(R.id.etNombreProducto);
        oetPrecioProducto=(EditText)findViewById(R.id.etPrecioProducto);
        oetComisiónProducto=(EditText)findViewById(R.id.etComisionProducto);
        oetCostoProducto=(EditText)findViewById(R.id.etCostoProducto);
        oetCantidadProducto=(EditText)findViewById(R.id.etCantidadProducto);
        obCrearProducto=(Button)findViewById(R.id.bCrearProducto);
        obEliminarProducto=(Button)findViewById(R.id.bEliminarProducto);
        otvEliminarProducto=(TextView)findViewById(R.id.tvEliminarProducto);
        olvProductos=(ListView)findViewById(R.id.lvProductos);

        //##################################################################################         Acciones iniciales
        fActualizar_LV_Distribuidores();
        fActualizar_LV_Productos();
        obEliminarDistribuidor.setVisibility(View.INVISIBLE);
        obEliminarProducto.setVisibility(View.INVISIBLE);

        //##################################################################################         Acciones de botones
        obCrearDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fCrearDistribuidor();
            }
        });
        obEliminarDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog=new ProgressDialog(ActivityAdmon.this);
                progressDialog.setMessage("Eliminando Distribuidor");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                DocumentReference bd_EliminarDistribuidor = FirebaseFirestore.getInstance()
                        .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+listItemsDistribuidor.getsNombre());
                bd_EliminarDistribuidor
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                myAdapterDistribuidor.remove(iDistribuidorSeleccionado);
                                obEliminarDistribuidor.setVisibility(View.INVISIBLE);
                                otvEliminarDistribuidor.setText("");
                                Log.d("Alarma", "DocumentSnapshot successfully deleted!");
                                Toast.makeText(ActivityAdmon.this,"Distribuidor eliminado",Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActivityAdmon.this,"No se ha podido eliminar el Distribuidor",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        //##############  PRODUCTOS
        obCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fCrearProductos();
            }
        });
        obEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog=new ProgressDialog(ActivityAdmon.this);
                progressDialog.setMessage("Eliminando Producto");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                DocumentReference bd_EliminarProducto = FirebaseFirestore.getInstance()
                        .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Productos/"+listItemsProductos.getsNombre());
                bd_EliminarProducto
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                myAdapterProductos.remove(iProductoSeleccionado);
                                obEliminarProducto.setVisibility(View.INVISIBLE);
                                otvEliminarProducto.setText("");
                                Log.d("Alarma", "DocumentSnapshot successfully deleted!");
                                Toast.makeText(ActivityAdmon.this,"Producto eliminado",Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActivityAdmon.this,"No se ha podido eliminar el producto",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });



        //###################################################################################       Acciones de los ListView
        olvDistribuidores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iDistribuidorSeleccionado=position;

                oArrayDistribuidorSeleccionado = olvDistribuidores.getItemAtPosition(position);
                oDistribuidorSeleccionado =(Distribuidor_Entidad) oArrayDistribuidorSeleccionado;

                listItemsDistribuidor=new Distribuidor_Entidad(
                        oDistribuidorSeleccionado.getsNombre(),
                        oDistribuidorSeleccionado.getlCelular(),
                        oDistribuidorSeleccionado.getiAhorro(),
                        oDistribuidorSeleccionado.getiDeuda(),
                        oDistribuidorSeleccionado.getiTazas(),
                        oDistribuidorSeleccionado.getiAromaticas(),
                        oDistribuidorSeleccionado.getiInstacrem(),
                        oDistribuidorSeleccionado.getiMantecada(),
                        oDistribuidorSeleccionado.getiLiberal(),
                        oDistribuidorSeleccionado.getiAlmojabana(),
                        oDistribuidorSeleccionado.getiArepa(),
                        oDistribuidorSeleccionado.getiMustang(),
                        oDistribuidorSeleccionado.getiLucky(),
                        oDistribuidorSeleccionado.getiEntTazas(),
                        oDistribuidorSeleccionado.getiDevTazas(),
                        oDistribuidorSeleccionado.getiEntDinero(),
                        oDistribuidorSeleccionado.getsCierreAbierto());
                otvEliminarDistribuidor.setText("Eliminar a "+listItemsDistribuidor.getsNombre()+"?");
                obEliminarDistribuidor.setVisibility(View.VISIBLE);
            }
        });

        olvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iProductoSeleccionado=position;

                oArrayProductoSeleccionado = olvProductos.getItemAtPosition(position);
                oProductoSeleccionado =(Productos_Entidad) oArrayProductoSeleccionado;

                listItemsProductos=new Productos_Entidad(
                        oProductoSeleccionado.getiItem(),
                        oProductoSeleccionado.getsNombre(),
                        oProductoSeleccionado.getiPrecio(),
                        oProductoSeleccionado.getiComision(),
                        oProductoSeleccionado.getiCosto(),
                        oProductoSeleccionado.getiCantidad());
                otvEliminarProducto.setText("Eliminar a "+listItemsProductos.getsNombre()+"?");
                obEliminarProducto.setVisibility(View.VISIBLE);
            }
        });

    }
    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################



    //#########################################################################################     DISTRIBUIDOR
    void fCrearDistribuidor(){
        //---------  Condiciones
        String sNombreDistribuidor = oetNombreDistribuidor.getText().toString().trim();
        String sCelularDistribuidor=oetCelularDistribuidor.getText().toString().trim();

        if (TextUtils.isEmpty(sNombreDistribuidor)) {
            Toast.makeText(this, "Debes ingresar un Nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sCelularDistribuidor.length()<12) {
            Toast.makeText(this, "Debes ingresar un número celular, " +
                    "agregar indicativo al principio", Toast.LENGTH_SHORT).show();
            return;
        }
        Long lCelularDistribuidor = Long.parseLong(sCelularDistribuidor);
        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityAdmon.this);
        progressDialog.setMessage("Creando Distriuidor");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //---------  Nuevo personal
        DocumentReference bd_NuevoPersonal = FirebaseFirestore.getInstance()
                .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores/"+sNombreDistribuidor);

        Map<String,Object> bd_GuardarPersonal=new HashMap<String, Object>();
        bd_GuardarPersonal.put("Nombre",sNombreDistribuidor);
        bd_GuardarPersonal.put("Celular",lCelularDistribuidor);
        bd_GuardarPersonal.put("Deuda Actual",0);
        bd_GuardarPersonal.put("Ahorro",0);
        bd_GuardarPersonal.put("Tazas",0);
        bd_GuardarPersonal.put("Aromaticas",0);
        bd_GuardarPersonal.put("Instacrem",0);
        bd_GuardarPersonal.put("Mantecada",0);
        bd_GuardarPersonal.put("Liberal",0);
        bd_GuardarPersonal.put("Almojabana",0);
        bd_GuardarPersonal.put("Arepa",0);
        bd_GuardarPersonal.put("Mustang",0);
        bd_GuardarPersonal.put("Lucky",0);
        bd_GuardarPersonal.put("Ent Tazas",0);
        bd_GuardarPersonal.put("Dev Tazas",0);
        bd_GuardarPersonal.put("Ent Dinero",0);
        bd_GuardarPersonal.put("Cierre Abierto","");

        bd_NuevoPersonal.set(bd_GuardarPersonal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityAdmon.this,"Distribuidor creado satisfactoriamente",Toast.LENGTH_SHORT).show();
                fActualizar_LV_Distribuidores();
                oetNombreDistribuidor.setText("");
                oetCelularDistribuidor.setText("");
                progressDialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityAdmon.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fActualizar_LV_Distribuidores(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Distribuidores";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        final int[][] iOrden = {new int[17]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Distribuidor_Entidad listItemsDistribuidor;
                    int iRevisarKeyz=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList<String> aKeys=new ArrayList<String>(document.getData().keySet());
                        Collection collection=document.getData().values();

                        if (iRevisarKeyz==0) {
                            iOrden[0] = fKeysDistribuidor(aKeys);
                            iRevisarKeyz=1;
                        }
                        List listSet=new ArrayList<>(collection);
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
                                listSet.get(iOrden[0][16]).toString());
                        alDistribuidores.add(listItemsDistribuidor);
                    }
                    myAdapterDistribuidor = new Distribuidor_Adaptador_Crear(ActivityAdmon.this, alDistribuidores);
                    olvDistribuidores.setAdapter(myAdapterDistribuidor);

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

    //#########################################################################################     PRODUCTO
    void fCrearProductos(){
        //---------  Condiciones
        String sItem = oetItemProducto.getText().toString().trim();
        String sNombre = oetNombreProducto.getText().toString().trim();
        String sPrecio = oetPrecioProducto.getText().toString().trim();
        String sComision = oetComisiónProducto.getText().toString().trim();
        String sCosto = oetCostoProducto.getText().toString().trim();
        String sCantidad = oetCantidadProducto.getText().toString().trim();

        if (TextUtils.isEmpty(sItem) || TextUtils.isEmpty(sNombre) ||
            TextUtils.isEmpty(sPrecio) || TextUtils.isEmpty(sComision) ||
            TextUtils.isEmpty(sCosto) || TextUtils.isEmpty(sCantidad)) {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int iItem=Integer.parseInt(sItem);
        int iPrecio=Integer.parseInt(sPrecio);
        int iComision=Integer.parseInt(sComision);
        int iCosto=Integer.parseInt(sCosto);
        int iCantidad=Integer.parseInt(sCantidad);

        //Mensaje
        final ProgressDialog progressDialog=new ProgressDialog(ActivityAdmon.this);
        progressDialog.setMessage("Creando Producto");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        //---------  Nuevo personal
        DocumentReference bd_NuevoProducto = FirebaseFirestore.getInstance()
                .document("/Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Productos/"+sNombre);

        Map<String,Object> bd_GuardarPersonal=new HashMap<String, Object>();
        bd_GuardarPersonal.put("Item",iItem);
        bd_GuardarPersonal.put("Nombre",sNombre);
        bd_GuardarPersonal.put("Precio",iPrecio);
        bd_GuardarPersonal.put("Comision",iComision);
        bd_GuardarPersonal.put("Costo",iCosto);
        bd_GuardarPersonal.put("Cantidad",iCantidad);

        bd_NuevoProducto.set(bd_GuardarPersonal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityAdmon.this,"Producto creado satisfactoriamente",Toast.LENGTH_SHORT).show();
                fActualizar_LV_Productos();
                oetItemProducto.setText("");
                oetNombreProducto.setText("");
                oetPrecioProducto.setText("");
                oetComisiónProducto.setText("");
                oetCostoProducto.setText("");
                oetCantidadProducto.setText("");
                progressDialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityAdmon.this,"Se presentaron fallas en el proceso",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });

    }
    void fActualizar_LV_Productos(){
        String sPath = "Usuarios/juliorrojas15@gmail.com/Tiendas/Cra.21/Productos";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);
        final int[][] iOrden = {new int[6]};
        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Productos_Entidad listItemsProductos;
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
                        alProductos.add(listItemsProductos);
                    }
                    myAdapterProductos = new Productos_Adaptador_Crear(ActivityAdmon.this, alProductos);
                    olvProductos.setAdapter(myAdapterProductos);

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
}

