package com.example.uts_mpr5;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ReadData extends AppCompatActivity {
    TextView id, nama, nim;

    private static final String TABLE_NAME = "tb_student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Read Activity");
        }

        id = (TextView) findViewById(R.id.id);
        nama = (TextView) findViewById(R.id.nama);
        nim = (TextView) findViewById(R.id.nim);

        ReadDatabase();
    }

    private void ReadDatabase() {
        handler helper = new handler(this);
        SQLiteDatabase db;
        db = helper.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        String iid = "ID", mNama = "Nama", mNim = "Nim";

        if (cursor != null){
            cursor.moveToFirst();
            do{
                iid = iid + "\n" + cursor.getString(0);
                mNama = mNama + "\n" + cursor.getString(1);
                mNim = mNim + "\n" + cursor.getString(2);
            } while (cursor.moveToNext());
        }
        id.setText(iid);
        nama.setText(mNama);
        nim.setText(mNim);
        db.close();
    }
}