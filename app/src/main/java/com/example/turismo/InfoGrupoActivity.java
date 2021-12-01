package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turismo.fragmentos.grupos.Grupo;
import com.example.turismo.fragmentos.grupos.GrupoAdapter;
import com.example.turismo.fragmentos.grupos.GruposFragment;
import com.example.turismo.fragmentos.inicio.InicioFragment;
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

    TextView id, sitio, region, mes_estimado, origen, genero, descripcion, cantmax, cantmin, cantidad, estado;
    String idgrupo, idusuario, fragmento;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerIntegrante;
    IntegrantesAdapter integrantesAdapter;
    ArrayList<Integrantes> listaIntegrantes;

    long maxid = 0;

    Button bt_unirse, bt_modificar, bt_cerrar, bt_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        //vinculo el elemento
        id = findViewById(R.id.tv_id);
        sitio = findViewById(R.id.textView_sitio);
        region = findViewById(R.id.textView_region);
        mes_estimado = findViewById(R.id.textView_mes);
        origen = findViewById(R.id.textView_origen);
        genero = findViewById(R.id.textView_genero);
        descripcion = findViewById(R.id.textView_descripcion);
        cantmax = findViewById(R.id.textView_cant_max);
        cantmin = findViewById(R.id.textView_cant_min);
        cantidad = findViewById(R.id.textView_cantidad);
        estado = findViewById(R.id.textView_estado);
        //botones
        bt_unirse = (Button) findViewById(R.id.button_unirse_grupo);
        bt_modificar = (Button) findViewById(R.id.button_modificar_grupo);
        bt_cerrar = (Button) findViewById(R.id.button_cerrar_grupo);
        bt_salir = (Button) findViewById(R.id.button_salir_grupo);

        //recupero variables que me mandaron
        idgrupo = (String) getIntent().getExtras().getSerializable("idgrupo");
        fragmento = (String) getIntent().getExtras().getSerializable("origen");
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

        //botones segun de que fragmento vengo
        if (fragmento.equals("grupos")){
            bt_modificar.setVisibility(View.INVISIBLE);
            bt_cerrar.setVisibility(View.INVISIBLE);
            bt_salir.setVisibility(View.INVISIBLE);

        }
        if (fragmento.equals("perfil")){
            bt_unirse.setVisibility(View.INVISIBLE);
            administrador(idgrupo,idusuario);
        }

        bt_unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //agregarintegrante
                unirseGrupo(Integer.parseInt(String.valueOf(maxid)),idusuario,idgrupo);
                System.out.println("Entre al boton unirse");
                // close this activity
                finish();
            }
        });

        bt_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getApplicationContext(), CrearGrupoActivity.class);
                i.putExtra("iduser",idusuario);
                i.putExtra("origen", "perfil");
                i.putExtra("idgrupo", idgrupo);
                startActivity(i);
            }
        });

        bt_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrargrupo(idgrupo);
                finish();
            }
        });

        bt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirdelgrupo(idgrupo,idusuario,Integer.parseInt(String.valueOf(maxid)));
                System.out.println("Entre al boton salir");
                finish();

            }
        });

    }


    private void administrador(String idg, String idusuar) {
        databaseReference.child("Grupos").child(idg).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot : snapshot.getChildren()){

                    String id = objSnapshot.getKey();
                    String usuario = objSnapshot.getValue().toString();

                    if (usuario.equals(idusuar)){
                        if (id.equals("1")){
                            System.out.println("ES administrador del grupoo");
                            bt_salir.setVisibility(View.INVISIBLE);

                        }else{
                            bt_modificar.setVisibility(View.INVISIBLE);
                            bt_cerrar.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                mes_estimado.setText(g.getMes_estimado());
                origen.setText(g.getOrigen());
                genero.setText(g.getGenero());
                descripcion.setText(g.getDescripcion());
                cantmax.setText("Cantidad Maxima: "+ String.valueOf(g.getCant_max()));
                cantmin.setText("Cantidad Minima: "+ String.valueOf(g.getCant_min()));
                cantidad.setText("Cantidad: "+ String.valueOf(g.getCantidad()));
                estado.setText("Estado: "+ g.getEstado());
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
        System.out.println("Entre al metodo unirse con "+fragmento);
        Toast.makeText(this,"Te has unido al grupo " + idgrupo, Toast.LENGTH_LONG).show();

    }

    private void salirdelgrupo(String idgrupoo, String idusuario, int cant) {

        databaseReference.child("Grupos").child(idgrupoo).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot : snapshot.getChildren()){

                    String id = objSnapshot.getKey();
                    String usuario = objSnapshot.getValue().toString();

                    if (usuario.equals(idusuario)){
                        //elimino al usurio
                        databaseReference.child("Grupos").child(idgrupoo).child("integrantes").child(id).removeValue();
                        int cantida = cant-1;
                        //actualizo cantidad de integrantes
                        databaseReference.child("Grupos").child(idgrupo).child("cantidad").setValue(cantida);
                        System.out.println("Entre al metodo salir con "+ fragmento);

                        Toast.makeText(getApplicationContext(),"Has salido del grupo "+ idgrupoo, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cerrargrupo(String idgrupo) {
        //actualizo el estado del grupo
        databaseReference.child("Grupos").child(idgrupo).child("estado").setValue("Cerrado");
        Toast.makeText(getApplicationContext(),"Has Cerrado el grupo "+ idgrupo, Toast.LENGTH_LONG).show();

    }

}


/*                            bt_salir.setClickable(false);

                            bt_modificar.setVisibility(View.VISIBLE);
                            bt_cerrar.setVisibility(View.VISIBLE);
                            bt_modificar.setClickable(true);
                            bt_cerrar.setClickable(true);*/
/*                            bt_modificar.setClickable(false);
                            bt_cerrar.setClickable(false);

                            bt_salir.setVisibility(View.VISIBLE);
                            bt_salir.setClickable(true);*/