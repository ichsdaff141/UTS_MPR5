package com.example.uts_mpr5;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uts_mpr5.MainActivity2;
import com.example.uts_mpr5.R;

import androidx.annotation.NonNull;

public class adapterlist extends ArrayAdapter {
    String nama[],harga[];
    int gambar[];
    Activity aktifiti;

    public adapterlist(MainActivity2 main, String[] nama, String[] harga, int[] gambar) {
        super(main, R.layout.item_layout,nama);

        this.nama=nama;
        this.harga=harga;
        this.gambar=gambar;
        aktifiti=main;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)aktifiti.getLayoutInflater();
        View v = inflater.inflate(R.layout.item_layout, null);

        ImageView gambar;
        TextView nama,harga;

        gambar = (ImageView)v.findViewById(R.id.gambaritem);
        nama = (TextView)v.findViewById(R.id.namahotel);
        harga  = (TextView)v.findViewById(R.id.harga);

        gambar.setImageResource(this.gambar[position]);

        nama.setText(this.nama[position]);
        harga.setText(this.harga[position]);

        return v;
    }
}