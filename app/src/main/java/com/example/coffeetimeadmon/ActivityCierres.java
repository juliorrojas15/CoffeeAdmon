package com.example.coffeetimeadmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityCierres extends AppCompatActivity {

    Button obEntregaNoche,obEntregaAdicional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cierres);

        obEntregaNoche=(Button)findViewById(R.id.bEntregaNoche);
        obEntregaAdicional=(Button)findViewById(R.id.bEntregaAdicional);


        obEntregaNoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCierres.this,ActivityEntregaNoche.class);
                startActivity(Destino);

            }
        });
        obEntregaAdicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Destino=new Intent(ActivityCierres.this,ActivityEntregaAdicional.class);
                startActivity(Destino);

            }
        });

    }
}
