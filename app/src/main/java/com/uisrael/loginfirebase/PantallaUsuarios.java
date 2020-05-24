package com.uisrael.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaUsuarios extends AppCompatActivity {


    private TextView txtDatos;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuarios);
        txtDatos = findViewById(R.id.txtDatos);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mDatabase.child("presupuestos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if (dataSnapshot.exists()){
               String nombre=dataSnapshot.child("nombre").getValue().toString();
               String apellido=dataSnapshot.child("apellido").getValue().toString();
               String edad= dataSnapshot.child("edad").getValue().toString();
//Comprobando que funcione el controlador de versionesS
               txtDatos.setText("El NOMBRE es: "+nombre+" El apellido es: "+apellido+" Su edad es:"+edad);
           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
