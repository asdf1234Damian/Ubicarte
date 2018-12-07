package com.example.damian.ubicarte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NuevoUsuario extends AppCompatActivity{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private EditText nombre;
    private EditText apellidos;
    private EditText telefono;
    private EditText modelo;
    private EditText placas;
    private EditText email;
    private EditText pass;
    private EditText passConf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        nombre= findViewById(R.id.inNombre);
        apellidos = findViewById(R.id.inApellidos);
        telefono = findViewById(R.id.inTelefono);
        email = findViewById(R.id.inEmail);
        pass = findViewById(R.id.inPass);
        passConf = findViewById(R.id.inPassConf);
        modelo = findViewById(R.id.inModelo);
        placas = findViewById(R.id.inPlacas);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void registrationUser_click(View view) {
        //Comprueba que las contraseñas coincidan
        if(!pass.getText().toString().equals(passConf.getText().toString())){
            Toast.makeText(this, R.string.noCoinciden, Toast.LENGTH_SHORT).show();
        }else{
            //Se crea la autentificacion usando email y contraseña
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString());
            //Se genera el ID automaticamente
            String idU=mDatabase.push().getKey();
            //Se crea nuevo usuario
            User newUser= new User(nombre.getText().toString(),apellidos.getText().toString(),telefono.getText().toString(),email.getText().toString());
            //Se crea el nuevo vehiculo con coordenadas 0,0 por default
            Vehicle newVehicle = new Vehicle(placas.getText().toString(),modelo.getText().toString(),idU,"0.0","0.0");
            //Se agregan a su respectiva base de datos
            mDatabase.child("Users").child(idU).setValue(newUser);
            mDatabase.child("Vehicles").child(placas.getText().toString()).setValue(newVehicle);
            //Mensaje de confirmacion
            Toast.makeText(this, R.string.plsStart  , Toast.LENGTH_SHORT).show();
            //Regresa a la pantalla principal
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}