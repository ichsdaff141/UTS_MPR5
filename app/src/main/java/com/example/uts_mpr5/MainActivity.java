package com.example.uts_mpr5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username,pass;
        Button login;

        username = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(username.getText().toString().equals("daffa")){

                        if(pass.getText().toString().equals("siregar")){
                            Intent pindah = new Intent(MainActivity.this,MainActivity2.class);
                            startActivity(pindah);
                        }else{
                            Toast.makeText(MainActivity.this,"PASSWORD SALAH",Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        Toast.makeText(MainActivity.this,"USERNAME SALAH",Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
}