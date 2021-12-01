package com.example.turismo.fragmentos.grupos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.turismo.CrearGrupoActivity;
import com.example.turismo.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GruposFragment extends Fragment {

    String idusuario;
    Spinner filter, filter1;
    View vista;
    ArrayList<Grupo> filtrarLista = null;
    ArrayList<Grupo> listagrupo;
    RecyclerView recyclerGrupo;
    GrupoAdapter grupoAdapter;

    ExtendedFloatingActionButton bflotante;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public GruposFragment(String idusuario) {
        this.idusuario=idusuario;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_grupos, container, false);

        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //decraracion y vinculacion de spinner para los filtros
        filter = (Spinner)vista.findViewById(R.id.spinner_filter);
        filter1 = (Spinner)vista.findViewById(R.id.spinner_filter1);

        //Cargar SPINNER PARA Los filtros.
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.filter, android.R.layout.simple_spinner_item );
        filter.setAdapter(arrayAdapter);



        //cargar segundo spinner
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Sirve para obtener el valor del spinner
                String tipo = filter.getItemAtPosition(position).toString();

                if (tipo.equals("Filtrar por")){
                    grupoAdapter.filtrar(listagrupo);
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.filter1, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Region")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.regiones, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Genero")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.generos, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Destino")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.sitios, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Origen")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.origen, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Cantidad Maxima")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.numeros, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Cantidad Minima")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.numeros, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);

                }   else if(tipo.equals("Mes Estimado")){
                    ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.mes, android.R.layout.simple_spinner_item );
                    filter1.setAdapter(arrayAdapter2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //FILTRADO - BUSQUEDA
        filter1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipo = filter1.getItemAtPosition(position).toString();
                filtrarLista = new ArrayList<>();
                if (tipo.equals("Quebrada")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getRegion().equals("Quebrada")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Puna")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getRegion().equals("Puna")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Yungas")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getRegion().equals("Yungas")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Valles")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getRegion().equals("Valles")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Mixto")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getGenero().equals("Mixto")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Masculino")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getGenero().equals("Masculino")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Femenino")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getGenero().equals("Femenino")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Lgbtq")) {
                    for (Grupo grupo : listagrupo){
                        if(grupo.getGenero().equals("Lgbtq")){
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Otros")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getGenero().equals("Otros")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("San Salvador de Jujuy")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("San Salvador de Jujuy")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Palpala")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("Palpala")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Perico")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("Perico")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("El Carmen")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("El Carmen")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("San Pedro")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("San Pedro")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Ledesma")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getOrigen().equals("Ledesma")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Enero")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Enero")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Febrero")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Febrero")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Marzo")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Marzo")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Abril")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Abril")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Mayo")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Mayo")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Junio")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Junio")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Julio")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Julio")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Agosto")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Agosto")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Septiembre")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Septiembre")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Octubre")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Octubre")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Noviembre")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Noviembre")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
                if (tipo.equals("Diciembre")) {
                    for (Grupo grupo : listagrupo) {
                        if (grupo.getMes_estimado().equals("Diciembre")) {
                            filtrarLista.add(grupo);
                        }
                    }
                    grupoAdapter.filtrar(filtrarLista);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //Cargar grupos a la listaaa
        listagrupo = new ArrayList<>();
        recyclerGrupo = (RecyclerView) vista.findViewById(R.id.recycler_grupos);
        recyclerGrupo.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista(idusuario);

        grupoAdapter = new GrupoAdapter(listagrupo, getContext());
        recyclerGrupo.setAdapter(grupoAdapter);
        grupoAdapter.traeridusuario(idusuario);

        //boton flotante

        bflotante= (ExtendedFloatingActionButton) vista.findViewById(R.id.fb_crear_grupo);
        bflotante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getContext(), CrearGrupoActivity.class);
                i.putExtra("iduser",idusuario);
                i.putExtra("origen", "grupos");
                startActivity(i);
            }
        });

        return vista;
    }

    private void llenarLista(String idusuar) {

        databaseReference.child("Grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listagrupo.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Grupo g = objSnapshot.getValue(Grupo.class);
                    g.setId(Integer.parseInt(objSnapshot.getKey()));

                    seencuentrausuario(String.valueOf(g.getId()), idusuar, g, g.getEstado()); //pregunto si pertenece
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seencuentrausuario(String idgrupo, String iduser, Grupo grupo, String estado) {
        databaseReference.child("Grupos").child(idgrupo).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> personas = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()){

                    String pers = objSnapshot.getValue().toString();
                    personas.add(pers);
                }
                //si no esta en el grupo que lo muestre
                if (!personas.contains(iduser)&&estado.equals("Activo")){
                    grupoAdapter.agregarGrupo(grupo);
                    //System.out.println("Cargaaa grupooo "+ idgrupo);
                }
                //System.out.println("grupooo "+ idgrupo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}






/*
        //Cargar grupos a la listaaa

        listagrupo = new ArrayList<>();
        recyclerGrupo = (RecyclerView) vista.findViewById(R.id.recycler_grupos);
        recyclerGrupo.setLayoutManager(new LinearLayoutManager(getContext()));

        //grupoAdapter = new GrupoAdapter(listagrupo, getContext());
        llenarLista(idusuario);
        grupoAdapter = new GrupoAdapter(listagrupo, getContext());
        recyclerGrupo.setAdapter(grupoAdapter);
        grupoAdapter.traeridusuario(idusuario);

        //grupoAdapter.llevarfrag(getChildFragmentManager());*/

/*    private void llenarLista(String idusuar) {

        databaseReference.child("Grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //grupoAdapter.limpiarlista();
                listagrupo.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Grupo g = objSnapshot.getValue(Grupo.class);
                    g.setId(Integer.parseInt(objSnapshot.getKey()));

                    //seencuentrausuario(idusuar, g); //pregunto si pertenece
                    databaseReference.child("Grupos").child(String.valueOf(g.getId())).child("integrantes").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ArrayList<String> personas = new ArrayList<>();
                            for (DataSnapshot objSnapshot : snapshot.getChildren()){

                                String pers = objSnapshot.getValue().toString();
                                personas.add(pers);
                            }

                            //si no esta en el grupo que lo muestre
                            if (!personas.contains(idusuar)&&g.getEstado().equals("Activo")){
                                listagrupo.add(g);
                                grupoAdapter.cargarlista(listagrupo);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seencuentrausuario(String iduser, Grupo grupo) {
        *//*databaseReference.child("Grupos").child(String.valueOf(grupo.getId())).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> personas = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()){

                    String pers = objSnapshot.getValue().toString();
                    personas.add(pers);
                }

                //si no esta en el grupo que lo muestre
                if (!personas.contains(iduser)&&grupo.getEstado().equals("Activo")){
                    listagrupo.add(grupo);
                }
                grupoAdapter.cargarlista(listagrupo);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*//*
    }*/




/*/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GruposFragment#newInstance} factory method to
 * create an instance of this fragment.*/

/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

/*    /**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment GruposFragment.
 */
/*    // TODO: Rename and change types and number of parameters
   public static GruposFragment newInstance(String param1, String param2) {
        GruposFragment fragment = new GruposFragment(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

/*        listagrupo.add(new Grupo(1,"Garganta del diablo", "Quebrada", "Mixto",
                "Diciembre", "San Salvador de jujuy", 3, 5, 2,
                "No viajamos para ir a un lugar en particular, sino por ir. Viajamos por el placer de viajar. La cuestión es movernos."
                ,"activo"));

        listagrupo.add(new Grupo(2,"Inca Cueva", "Quebrada", "Mixto",
                "Enero", "Ledesma", 2, 4, 1,
                "descripcion 2","activo"));

        listagrupo.add(new Grupo(3,"Cerro El Huancar de Abra Pampa", "Quebrada", "Lgbtq",
                "Diciembre", "Palpala", 3, 5, 2,
                "descripcion 3","activo"));

        listagrupo.add(new Grupo(4,"Serranía de Hornocal ", "Quebrada", "Femenino",
                "Febrero", "Perico", 3, 6, 1,
                "descripcion 4","activo"));*/