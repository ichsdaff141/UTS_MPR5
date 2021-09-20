package com.example.uts_mpr5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sqlite extends AppCompatActivity {
    Button readBtn, insertBtn, delupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        readBtn = findViewById(R.id.readBtn);
        insertBtn = findViewById(R.id.insertBtn);
        delupBtn = findViewById(R.id.delupBtn);

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sqlite.this, ReadData.class);
                startActivity(intent);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sqlite.this, Insert.class);
                startActivity(intent);
            }
        });

        delupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sqlite.this, Update_Delete.class);
                startActivity(intent);
            }
        });
    }
}
