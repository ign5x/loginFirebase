package com.uisrael.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegistrar;
    //Variables que vamos a registrar
    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando método de autentificacion
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtName = (EditText) findViewById(R.id.txtUsuario);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegistrar = (Button) findViewById(R.id.btnIngresar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();


                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6) {
                        registrarusuario();

                    } else {
                        Toast.makeText(MainActivity.this, "El password tiene que tener al menos 6 caracteres", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Debe completar los datos", Toast.LENGTH_LONG).show();


                }


            }
        });
    }

    private void registrarusuario() {
        Log.i("sms0","ss"+email);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("sms1","ss"+email);
                    Map<String, Object> map = new HashMap<>();


                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, PantallaUsuarios.class));
                                finish();

                            } else {
                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                }
                else {
                    Log.i("sms3","ss"+email);
                    Toast.makeText(MainActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
