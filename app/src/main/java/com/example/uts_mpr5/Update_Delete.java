package com.example.uts_mpr5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Update_Delete extends AppCompatActivity {
    TextView text;
    EditText id, nama, nim;
    String selectedID, mNama, mNim;

    private static final String TABLE_NAME = "tb_student";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__delete);
        id = (EditText) findViewById(R.id.id);
        nama = (EditText) findViewById(R.id.nama);
        nim = (EditText) findViewById(R.id.nim);
        text = (TextView) findViewById(R.id.text);
    }

    public void delete(View view) {
        delete(selectedID);
    }

    public void select(View view) {
        selectedID = id.getText().toString();
        selectedID(selectedID);
    }

    public void update(View view) {
        mNama = nama.getText().toString();
        mNim = nim.getText().toString();
        updatedata(selectedID, mNama, mNim);
    }

    void selectedID(String selectedID){
        try{
            handler helper = new handler(this);
            SQLiteDatabase db;
            db = helper.getReadableDatabase();

            Cursor cursor = null;

            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?", new String[]{selectedID});

            if (cursor != null){
                cursor.moveToFirst();
                nama.setText(cursor.getString(1));
                nim.setText(cursor.getString(2));
            }
            db.close();
        }
        catch (Exception x){
            Toast.makeText(this, "ID SALAH", Toast.LENGTH_SHORT).show();
        }
    }

    void updatedata(String selectedID, String getNama,String getNim){
        handler helper = new handler(this);

        SQLiteDatabase db;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, getNama);
        values.put(KEY_NIM, getNim);

        db.update(TABLE_NAME, values, "id=?" , new String[]{String.valueOf(selectedID)});
        db.close();

        nama.setText("");
        nim.setText("");

        Toast.makeText(this, "SUKSES", Toast.LENGTH_SHORT).show();

    }

    void delete (String selectedID){
        handler helper = new handler(this);
        SQLiteDatabase db;
        db = helper.getWritableDatabase();

        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(selectedID)});

        db.close();

        id.setText("");
        nama.setText("");
        nim.setText("");

        Toast.makeText(this, "SUKSES", Toast.LENGTH_SHORT).show();
    }
}