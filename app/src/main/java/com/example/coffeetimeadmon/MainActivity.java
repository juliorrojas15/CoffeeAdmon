package com.example.coffeetimeadmon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onResume(){
        super.onResume();
        obAdministracion.setVisibility(View.INVISIBLE);
        obCierres.setVisibility(View.INVISIBLE);
        obEntregarInsumos.setVisibility(View.INVISIBLE);
        iClaveIngresada=0;
        iPosClave=0;
        for(int i=0;i<4;i++){
            iClaveValor[i]=0;
        }
        otvClave.setText("----");
    }

    //#########################################################################################     //Variables que se traen de la pagina de Productos

    //#########################################################################################     //Keys de la base de datos

    //#########################################################################################     //Objetos del Layout
    Button obAdministracion,obCierres,obEntregarInsumos;
    Button obTecla_0,obTecla_1,obTecla_2,obTecla_3,obTecla_4,obTecla_5,obTecla_6,obTecla_7,
            obTecla_8,obTecla_9;
    Button obTecla_Borrar,obTecla_Entrar;
    TextView otvClave;

    //#########################################################################################     Variables Globales
    List<Personal_Entidad> listItemsPersonal;
    int iPosClave=0;
    int []iClaveValor=new int[4];
    int iClaveIngresada;
    String sPersonal,sPerfil;
    //#########################################################################################################################################
    //#########################################################################################     ON CREATE
    //#########################################################################################################################################
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //#####################################################################################     Relación de objetos con Layout
        obAdministracion=(Button)findViewById(R.id.bAdministracion);
        obCierres=(Button)findViewById(R.id.bCierres);
        obEntregarInsumos=(Button)findViewById(R.id.bEntregarInsumos);
        obTecla_0=(Button)findViewById(R.id.bTecla_0);
        obTecla_1=(Button)findViewById(R.id.bTecla_1);
        obTecla_2=(Button)findViewById(R.id.bTecla_2);
        obTecla_3=(Button)findViewById(R.id.bTecla_3);
        obTecla_4=(Button)findViewById(R.id.bTecla_4);
        obTecla_5=(Button)findViewById(R.id.bTecla_5);
        obTecla_6=(Button)findViewById(R.id.bTecla_6);
        obTecla_7=(Button)findViewById(R.id.bTecla_7);
        obTecla_8=(Button)findViewById(R.id.bTecla_8);
        obTecla_9=(Button)findViewById(R.id.bTecla_9);
        obTecla_Borrar=(Button)findViewById(R.id.bTecla_Borrar);
        obTecla_Entrar=(Button)findViewById(R.id.bTecla_Entrar);

        obTecla_0.setOnClickListener(this);
        obTecla_1.setOnClickListener(this);
        obTecla_2.setOnClickListener(this);
        obTecla_3.setOnClickListener(this);
        obTecla_4.setOnClickListener(this);
        obTecla_5.setOnClickListener(this);
        obTecla_6.setOnClickListener(this);
        obTecla_7.setOnClickListener(this);
        obTecla_8.setOnClickListener(this);
        obTecla_9.setOnClickListener(this);

        otvClave=(TextView)findViewById(R.id.tvClave);
        //##################################################################################         Acciones iniciales
        obAdministracion.setVisibility(View.INVISIBLE);
        obCierres.setVisibility(View.INVISIBLE);
        obEntregarInsumos.setVisibility(View.INVISIBLE);
        obTecla_Entrar.setVisibility(View.INVISIBLE);

        for(int i=0;i<4;i++){
            iClaveValor[i]=0;
        }
        //Descargar base de datos de Usuarios
        String sPath = "Usuarios/"+"juliorrojas15@gmail.com/"+"Tiendas/"+"Cra.21/"+"Personal";
        CollectionReference bd_Datos= FirebaseFirestore.getInstance().collection(sPath);

        bd_Datos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    listItemsPersonal=new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Collection collection=document.getData().values();
                        List listSet=new ArrayList<>(collection);
                        int iPrimero=2,iSegundo=0,iTercero=1;
                        listItemsPersonal.add(new Personal_Entidad(listSet.get(iPrimero).toString(),
                                listSet.get(iSegundo).toString(),
                                Integer.valueOf(listSet.get(iTercero).toString())));
                        Log.d("Campos: ", listItemsPersonal.toString());
                    }
                    obTecla_Entrar.setVisibility(View.VISIBLE);
                } else {
                    Log.d("Actividad", "Error adquiriendo documentos: ", task.getException());
                }
            }
        });
        //##################################################################################         Acciones de botones
        obAdministracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(MainActivity.this,ActivityAdmon.class);
                startActivity(Destino);
            }
        });
        obCierres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(MainActivity.this,ActivityCierres.class);
                startActivity(Destino);
            }
        });
        obEntregarInsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(MainActivity.this,ActivityEntregaInsumos.class);
                startActivity(Destino);
            }
        });

        obTecla_Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPosClave=0;
                for(int i=0;i<4;i++){
                    iClaveValor[i]=0;
                }
                otvClave.setText("----");
                iClaveIngresada=0;
                obAdministracion.setVisibility(View.INVISIBLE);
                obCierres.setVisibility(View.INVISIBLE);
                obEntregarInsumos.setVisibility(View.INVISIBLE);
            }
        });
        obTecla_Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fCompararClaves();
            }
        });

    }
    //#########################################################################################################################################
    //#########################################################################################
    //#########################################################################################################################################

    @Override
    public void onClick(View view){
        if (iPosClave==0){
            otvClave.setText("");
        }
        if (iPosClave<4){
            switch (view.getId()){
                case R.id.bTecla_0:iClaveValor[iPosClave]=0;iPosClave++;break;
                case R.id.bTecla_1:iClaveValor[iPosClave]=1;iPosClave++;break;
                case R.id.bTecla_2:iClaveValor[iPosClave]=2;iPosClave++;break;
                case R.id.bTecla_3:iClaveValor[iPosClave]=3;iPosClave++;break;
                case R.id.bTecla_4:iClaveValor[iPosClave]=4;iPosClave++;break;
                case R.id.bTecla_5:iClaveValor[iPosClave]=5;iPosClave++;break;
                case R.id.bTecla_6:iClaveValor[iPosClave]=6;iPosClave++;break;
                case R.id.bTecla_7:iClaveValor[iPosClave]=7;iPosClave++;break;
                case R.id.bTecla_8:iClaveValor[iPosClave]=8;iPosClave++;break;
                case R.id.bTecla_9:iClaveValor[iPosClave]=9;iPosClave++;break;
            }
            otvClave.setText(otvClave.getText()+"X");
        }
        iClaveIngresada=(iClaveValor[0]*1000)+(iClaveValor[1]*100)+(iClaveValor[2]*10)+iClaveValor[3];
    }

    void fCompararClaves(){
        for(int i=0;i<listItemsPersonal.size();i++){
            if (listItemsPersonal.get(i).getiClave()==iClaveIngresada){
                sPersonal=listItemsPersonal.get(i).getsNombre();
                sPerfil=listItemsPersonal.get(i).getsPerfil();
                if (sPerfil.equals("Administrador")) {
                    obAdministracion.setVisibility(View.VISIBLE);
                }
                obCierres.setVisibility(View.VISIBLE);
                obEntregarInsumos.setVisibility(View.VISIBLE);
                Toast.makeText(this,"Hola "+sPersonal+", Bienvenido!",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this,"Clave no asignada a usuarios",Toast.LENGTH_SHORT).show();
        iPosClave=0;
        for(int i=0;i<4;i++){
            iClaveValor[i]=0;
        }
        otvClave.setText("----");
        iClaveIngresada=0;
    }
}
