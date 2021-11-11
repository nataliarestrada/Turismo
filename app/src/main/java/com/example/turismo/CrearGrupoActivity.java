package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CrearGrupoActivity extends AppCompatActivity {

    Spinner sp_sitio, sp_genero, sp_mes, sp_origen, sp_cant_max, sp_cant_min, sp_region, sp_estado;
    EditText et_descripcion, et_cant, et_id;
    Button bt_crear, bt_cancelar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    long maxid = 0;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        iduser=(String) getIntent().getExtras().getSerializable("iduser");

        System.out.println(iduser);

        //decraracion y vinculacion de spinner para los filtros
        sp_sitio = (Spinner) findViewById(R.id.spinner_sitio);
        sp_genero = (Spinner) findViewById(R.id.spinner_genero);
        sp_mes = (Spinner) findViewById(R.id.spinner_mes);
        sp_origen = (Spinner) findViewById(R.id.spinner_origen);
        sp_cant_min = (Spinner) findViewById(R.id.spinner_cant_min);
        sp_cant_max = (Spinner) findViewById(R.id.spinner_cant_max);
        sp_region = (Spinner) findViewById(R.id.spinner_region);
        sp_estado = (Spinner) findViewById(R.id.spinner_estado);
        //editext
        et_descripcion = (EditText) findViewById(R.id.editText_descripcion);
        et_cant = (EditText) findViewById(R.id.editText_cantidad);
        //botones
        bt_crear = (Button) findViewById(R.id.button_crear_grupo);


        //Cargar SPINNER
        ArrayAdapter<CharSequence> arraySitios = ArrayAdapter.createFromResource(this, R.array.sitios, android.R.layout.simple_spinner_item );
        sp_sitio.setAdapter(arraySitios);

        ArrayAdapter<CharSequence> arrayGenero = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item );
        sp_genero.setAdapter(arrayGenero);

        ArrayAdapter<CharSequence> arrayMes = ArrayAdapter.createFromResource(this, R.array.mes, android.R.layout.simple_spinner_item );
        sp_mes.setAdapter(arrayMes);

        ArrayAdapter<CharSequence> arrayOrigen = ArrayAdapter.createFromResource(this, R.array.origen, android.R.layout.simple_spinner_item );
        sp_origen.setAdapter(arrayOrigen);

        ArrayAdapter<CharSequence> arrayCantMin = ArrayAdapter.createFromResource(this, R.array.numeros, android.R.layout.simple_spinner_item );
        sp_cant_min.setAdapter(arrayCantMin);

        ArrayAdapter<CharSequence> arrayCantMax = ArrayAdapter.createFromResource(this, R.array.numeros, android.R.layout.simple_spinner_item );
        sp_cant_max.setAdapter(arrayCantMax);

        ArrayAdapter<CharSequence> arrayRegion = ArrayAdapter.createFromResource(this, R.array.regiones, android.R.layout.simple_spinner_item );
        sp_region.setAdapter(arrayRegion);

        ArrayAdapter<CharSequence> arrayEstado = ArrayAdapter.createFromResource(this, R.array.estado, android.R.layout.simple_spinner_item );
        sp_estado.setAdapter(arrayEstado);

        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //obtener ultimo id de grupos
        databaseReference.child("Grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bt_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrargrupo(Integer.parseInt(String.valueOf(maxid)),iduser);

                finish();
                //registrargrupo();

            }
        });




    }

    private void registrargrupo(int maxid,String idusuario) {
    //private void registrargrupo() {
        int id = maxid+1;
        String sitio = sp_sitio.getSelectedItem().toString();
        String region = sp_region.getSelectedItem().toString();
        String genero = sp_genero.getSelectedItem().toString();
        String mes_estimado = sp_mes.getSelectedItem().toString();
        String origen = sp_origen.getSelectedItem().toString();
        int cant_min = Integer.parseInt(sp_cant_min.getSelectedItem().toString());
        int cant_max = Integer.parseInt(sp_cant_max.getSelectedItem().toString());
        //int cantidad = Integer.parseInt(et_cant.getText().toString());
        String descripcion = et_descripcion.getText().toString();
        //String estado = sp_estado.getSelectedItem().toString();
        //String idusuario = (String) getIntent().getExtras().getSerializable("iduser");

        Map<String,String> integrante = new HashMap<>();
        integrante.put("1",idusuario);


        Map<String,Object> grupo= new HashMap<>();
        grupo.put("sitio",sitio);
        grupo.put("region",region);
        grupo.put("genero",genero);
        grupo.put("mes_estimado",mes_estimado);
        grupo.put("origen",origen);
        grupo.put("cant_min",cant_min);
        grupo.put("cant_max",cant_max);
        grupo.put("cantidad",1);
        grupo.put("descripcion",descripcion);
        grupo.put("estado","Activo");
        grupo.put("integrantes", integrante);

        databaseReference.child("Grupos").child(String.valueOf(id)).setValue(grupo);
        Toast.makeText(this,"Grupo registrado", Toast.LENGTH_LONG).show();

    }
}