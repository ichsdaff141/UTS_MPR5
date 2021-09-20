package com.example.uts_mpr5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity2 extends AppCompatActivity {
    Button btninput, btnfitur;
    String list_nama [] ={
            "JW MARIOT",
            "SANTIKA",
            "ASTON",
            "IBIS",
            "HERMES",
            "PODOMORO",
            "GRAND DHIKA"
    };
    String harga[] ={
            "Rp 300.000 / hari",
            "Rp 500.000 / hari",
            "Rp 350.000 / hari",
            "Rp 375.000 / hari",
            "Rp 600.000 / hari",
            "Rp 800.000 / hari",
            "Rp 750.000 / hari"
    };
    int list_gambar [] ={
            R.drawable.santika,
            R.drawable.santika,
            R.drawable.santika,
            R.drawable.santika,
            R.drawable.santika,
            R.drawable.santika,
            R.drawable.santika
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btninput = (Button) findViewById(R.id.btninput);
        btnfitur = (Button) findViewById(R.id.btnfitur);

        ListView list  = (ListView) findViewById(R.id.listview);

        com.example.uts_mpr5.adapterlist adapter = new com.example.uts_mpr5.adapterlist(this,list_nama,harga,list_gambar);

          list.setAdapter(adapter);

        ImageView arrowback;
        arrowback = (ImageView) findViewById(R.id.arrow);
        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewinput = new Intent(MainActivity2.this, sqlite.class);
                MainActivity2.this.startActivity(viewinput);
                finish();
            }
        });
        btnfitur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewinput = new Intent(MainActivity2.this, Features.class);
                MainActivity2.this.startActivity(viewinput);
                finish();
            }
        });
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah  = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(pindah);
            }
        });


    }


}