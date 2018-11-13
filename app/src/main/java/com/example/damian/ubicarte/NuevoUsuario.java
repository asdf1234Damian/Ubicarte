package com.example.damian.ubicarte;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NuevoUsuario extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText pass;
    private EditText passConf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        email = findViewById(R.id.inEmail);
        pass = findViewById(R.id.inPass);
        passConf = findViewById(R.id.inPassConf);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registrationUser_click(View view) {
        if(!pass.getText().toString().equals(passConf.getText().toString())){
            Toast.makeText(this, R.string.noCoinciden, Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString());
        }

    }
}
