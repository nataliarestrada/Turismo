package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turismo.fragmentos.grupos.Grupo;
import com.example.turismo.fragmentos.grupos.GrupoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoGrupoActivity extends AppCompatActivity {

    TextView id, sitio, region, origen, genero, descripcion, cantmax, cantmin, cantidad, estado;
    String idgrupo, idusuario;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerIntegrante;
    IntegrantesAdapter integrantesAdapter;
    ArrayList<Integrantes> listaIntegrantes;

    long maxid = 0;

    Button bt_unirse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        //vinculo el elemento
        id = findViewById(R.id.tv_id);
        sitio = findViewById(R.id.textView_sitio);
        region = findViewById(R.id.textView_region);
        origen = findViewById(R.id.textView_origen);
        genero = findViewById(R.id.textView_genero);
        descripcion = findViewById(R.id.textView_descripcion);
        cantmax = findViewById(R.id.textView_cant_max);
        cantmin = findViewById(R.id.textView_cant_min);
        cantidad = findViewById(R.id.textView_cantidad);
        estado = findViewById(R.id.textView_estado);
        //botones
        bt_unirse = (Button) findViewById(R.id.button_unirse_grupo);

        //variable id grupo que me mandaron
        idgrupo = (String) getIntent().getExtras().getSerializable("idgrupo");
        idusuario = (String) getIntent().getExtras().getSerializable("idusuario");

        //lo cargo en el elemento
        id.setText(idgrupo);


        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //cargo la info del grupo
        infogrupo(idgrupo);

        //Cargar integrantes a la listaaa
        listaIntegrantes = new ArrayList<>();
        recyclerIntegrante = (RecyclerView) findViewById(R.id.recycler_integrantes);
        recyclerIntegrante.setLayoutManager(new LinearLayoutManager(this));

        llenarListaIntegrantes(idgrupo);

        integrantesAdapter = new IntegrantesAdapter(listaIntegrantes);
        recyclerIntegrante.setAdapter(integrantesAdapter);

        //obtener ultimo id de grupos
        databaseReference.child("Grupos").child(idgrupo).child("integrantes").addValueEventListener(new ValueEventListener() {
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

        bt_unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //agregarintegrante
                unirseGrupo(Integer.parseInt(String.valueOf(maxid)),idusuario,idgrupo);
                finish();
            }
        });

    }


    private void infogrupo(String id) {

        databaseReference.child("Grupos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                Grupo g = snapshot.getValue(Grupo.class);

                sitio.setText(g.getSitio());
                region.setText(g.getRegion());
                origen.setText(g.getOrigen());
                genero.setText(g.getGenero());
                descripcion.setText(g.getDescripcion());
                cantmax.setText(String.valueOf(g.getCant_max()));
                cantmin.setText(String.valueOf(g.getCant_min()));
                cantidad.setText(String.valueOf(g.getCantidad()));
                estado.setText(g.getEstado());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void llenarListaIntegrantes(String id) {
        databaseReference.child("Grupos").child(id).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaIntegrantes.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    String pers = objSnapshot.getValue().toString();
                    llenarlista(pers);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void llenarlista(String id) {
        databaseReference.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                Integrantes i = snapshot.getValue(Integrantes.class);

                integrantesAdapter.agregarIntegrante(i);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void unirseGrupo(int maxid, String idusuario, String idgrupo){
        int idint = maxid+1;
        //actualizo cantidad de integrantes
        databaseReference.child("Grupos").child(idgrupo).child("cantidad").setValue(idint);
        //agrego integrante
        databaseReference.child("Grupos").child(idgrupo).child("integrantes").child(String.valueOf(idint)).setValue(idusuario);

        Toast.makeText(this,"Te has unido al grupo " + idgrupo, Toast.LENGTH_LONG).show();

    }

}