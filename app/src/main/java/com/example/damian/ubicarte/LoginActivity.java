package com.example.damian.ubicarte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    //UI
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    //Firebase
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    //Metodo para guardar los datos del usuario que ha logeado
    public void recuperaDatos(final String email){
        //Recibe la referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Se agrega un listener para un solo cambio (no se actualiza con cada cambio)
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Recupera el id del usuario
                DataSnapshot users = dataSnapshot.child("Users");
                //Recorre la base buscando el usuario con el mismo correo
                for (DataSnapshot ds: users.getChildren()){
                    if (ds.child("CorreoElectronico").getValue().toString().equals(email)){
                        Global.id=ds.getKey().toString();
                        Toast.makeText(LoginActivity.this, Global.id, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (Global.id.isEmpty()){
                    Toast.makeText(LoginActivity.this, "No se pudo recuperar el dato del usuario :c", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
//            Intent intent = new Intent(LoginActivity.this, MapaPrincipal.class);
//            startActivity(intent);
//            finish();
//        }
        // set the view now
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progress);
        Button btnSignup = findViewById(R.id.email_sign_in_button);
        Button btnLogin = findViewById(R.id.email_log_in_button);
        Button btnReset = findViewById(R.id.reset_Contraseña);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, NuevoUsuario.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.error_invalid_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.error_incorrect_password), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    recuperaDatos(email);
                                    Intent intent = new Intent(LoginActivity.this, MapaPrincipal.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}