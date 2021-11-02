package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button btn_entrar;
    private Button btn_crear;

    EditText editText_usuario,editText_contraseniaL;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //decraracion y vinculacion de los elementos
        editText_usuario = (EditText)findViewById(R.id.editText_usuario);
        editText_contraseniaL = (EditText)findViewById(R.id.editText_contraseniaL);


        //inicializa el autenticador
        mAuth = FirebaseAuth.getInstance();
        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i;
                i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);*/
                iniciarSesion(v);
                //verificarUsuarioIngresado();

            }
        });

        btn_crear = (Button) findViewById(R.id.btn_crearCuenta);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(LoginActivity.this,RegistrarUsuarioActivity.class);
                startActivity(i);
            }
        });
    }

    public void verificarUsuarioIngresado(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent i;
            i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);

            Toast.makeText(this,"Bienvenido1", Toast.LENGTH_LONG).show();

        }

    }

    public void iniciarSesion(View view){
        String usuario = editText_usuario.getText().toString().trim();
        String contraseniaL = editText_contraseniaL.getText().toString().trim();

        if(!usuario.isEmpty()&&!contraseniaL.isEmpty()){
            mAuth.signInWithEmailAndPassword(usuario,contraseniaL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //obtengo el id del usuario que se logueo
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String iduser = user.getUid();


                        Toast.makeText(getApplicationContext(), "Bienvenid@ a All Travel Jujuy", Toast.LENGTH_LONG).show();
                        //limpiarCampos();
                        // if the user created intent to login activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("idUsuario", iduser);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecto", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(this,"Hay datos vacíos", Toast.LENGTH_LONG).show();
        }

    }
}