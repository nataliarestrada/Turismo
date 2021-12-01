package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    Button btn_crear, btn_inicio;
    EditText editText_nombre,editText_alias, editText_edad, editText_telefono, editText_email, editText_contrasenia, editText_confContrasenia;
    Spinner spinner_genero, spinner_origen;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        //decraracion y vinculacion de los elementos
        editText_nombre = (EditText)findViewById(R.id.editText_nombre);
        editText_alias = (EditText)findViewById(R.id.editText_alias);
        spinner_genero = (Spinner)findViewById(R.id.spinner_genero);
        spinner_origen = (Spinner)findViewById(R.id.spinner_origen);
        editText_edad = (EditText)findViewById(R.id.editText_edad);
        editText_telefono = (EditText)findViewById(R.id.editText_telefono);
        editText_email = (EditText)findViewById(R.id.editText_email);
        editText_contrasenia = (EditText)findViewById(R.id.editText_contrasenia);
        editText_confContrasenia = (EditText)findViewById(R.id.editText_confContrasenia);
        btn_crear = (Button) findViewById(R.id.btn_crear);
        btn_inicio = (Button) findViewById(R.id.btn_inicio);


        //Cargar SPINNER
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.generos_usuario, android.R.layout.simple_spinner_item );
        spinner_genero.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.origen_usuarios, android.R.layout.simple_spinner_item );
        spinner_origen.setAdapter(arrayAdapter2);


        //inicializa el autenticador
        mAuth = FirebaseAuth.getInstance();
        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarusuario(v);
            }
        });

        //
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(RegistrarUsuarioActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }

/*    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }*/

    public void registrarusuario(View view){

        String nombre = editText_nombre.getText().toString();
        String alias = editText_alias.getText().toString();
        String genero = spinner_genero.getSelectedItem().toString();
        String origen = spinner_origen.getSelectedItem().toString();
        String edad = editText_edad.getText().toString();
        String telefono = editText_telefono.getText().toString();
        String email = editText_email.getText().toString().trim();
        String contrasenia = editText_contrasenia.getText().toString().trim();
        String confcontrasenia = editText_confContrasenia.getText().toString().trim();

        if(!nombre.isEmpty()&&!genero.isEmpty()&&!origen.isEmpty()&&!edad.isEmpty()&&!telefono.isEmpty()&&!email.isEmpty()&&!contrasenia.isEmpty()&&!confcontrasenia.isEmpty()){

            if (contrasenia.equals(confcontrasenia)) {
                // create new user or register new user
                mAuth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {

                            Map<String,Object> usuario= new HashMap<>();
                            usuario.put("nombre",nombre);
                            usuario.put("alias",alias);
                            usuario.put("genero",genero);
                            usuario.put("origen",origen);
                            usuario.put("edad",edad);
                            usuario.put("telefono",telefono);
                            usuario.put("email",email);
                            usuario.put("contrasenia",contrasenia);

                            FirebaseUser user = mAuth.getCurrentUser();
                            //String id = Objects.requireNonNull(user.getUid());
                            String id = user.getUid();

                            databaseReference.child("Usuario").child(id).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task1) {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_LONG).show();
                                        limpiarCampos();
                                        // if the user created intent to login activity
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            // Registration failed
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }
    }

    public void limpiarCampos() {
        editText_nombre.setText("");
        editText_alias.setText("");
        spinner_genero.getItemAtPosition(1);
        spinner_origen.getItemAtPosition(1);
        editText_edad.setText("");
        editText_telefono.setText("");
        editText_email.setText("");
        editText_contrasenia.setText("");
        editText_confContrasenia.setText("");
        editText_nombre.requestFocus();
    }

}


/*    private void inicializarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }*/

/*    public void  registrarusuario(View view){

        if (editText_contrasenia.getText().toString().equals(editText_confContrasenia.getText().toString())) {
            // create new user or register new user
            mAuth.createUserWithEmailAndPassword(editText_email.getText().toString().trim(), editText_contrasenia.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful()) {
                        //Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                        // hide the progress bar
                        //progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_LONG).show();

                        FirebaseUser user = mAuth.getCurrentUser();
                        // if the user created intent to login activity
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }else{

                        // Registration failed
                        Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();

                        // hide the progress bar
                        //progressBar.setVisibility(View.GONE);
                    }
                }
            });


        }else {
            Toast.makeText(getApplicationContext(), "Contraseñas no coinciden!", Toast.LENGTH_LONG).show();
        }

    }*/