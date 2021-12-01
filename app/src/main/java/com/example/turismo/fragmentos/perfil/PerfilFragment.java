package com.example.turismo.fragmentos.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turismo.R;
import com.example.turismo.Usuario;
import com.example.turismo.fragmentos.grupos.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PerfilFragment extends Fragment {

    String idusuario;
    View vista;
    ImageButton bt1, bt2, bt3;
    ImageView imagen;
    TextView alias,nombre,edad,genero,domicilio,email,telefono;
    LinearLayout layout1, layout2, layout3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerGrupoPerfil;
    GrupoPerfilAdapter grupoAdapterPerfil;
    ArrayList<Grupo> listagrupoPerfil;

    public PerfilFragment(String idusuario) {
        this.idusuario=idusuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_perfil, container, false);

        bt1 = (ImageButton) vista.findViewById(R.id.button_basica);
        bt2 = (ImageButton) vista.findViewById(R.id.button_contacto);
        bt3 = (ImageButton) vista.findViewById(R.id.button_grupos);
        layout1 = (LinearLayout) vista.findViewById(R.id.layout_basica);
        layout2 = (LinearLayout) vista.findViewById(R.id.layout_contacto);
        layout3 = (LinearLayout) vista.findViewById(R.id.layout_grupos);
        imagen= (ImageView) vista.findViewById(R.id.image);
        alias = (TextView) vista.findViewById(R.id.tv_alias_usuario);
        nombre = (TextView) vista.findViewById(R.id.tv_nombre_usuario);
        edad = (TextView) vista.findViewById(R.id.tv_edad_usuario);
        genero = (TextView) vista.findViewById(R.id.tv_genero_usuario);
        domicilio = (TextView) vista.findViewById(R.id.tv_domicilio_usuario);
        email = (TextView) vista.findViewById(R.id.tv_email_usuario);
        telefono = (TextView) vista.findViewById(R.id.tv_telefono_usuario);

        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        //cargo la info del usuario
        databaseReference.child("Usuario").child(idusuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                Usuario u = snapshot.getValue(Usuario.class);
                //System.out.println(u.getGenero());

                if (u.getGenero().equals("Masculino")){

                    imagen.setBackground(getResources().getDrawable(R.drawable.perfil2));
                }else{
                    imagen.setBackground(getResources().getDrawable(R.drawable.perfil1));
                }
                alias.setText(u.getAlias());
                nombre.setText("Nombre: "+u.getNombre());
                edad.setText("Edad: "+u.getEdad());
                genero.setText("Genero: "+u.getGenero());
                domicilio.setText("Domicilio: "+u.getOrigen());
                email.setText("Email: "+u.getEmail());
                telefono.setText("Telefono: "+u.getTelefono());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //mostrar y ocultar
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(layout1.getVisibility(), 0)){
                    bt1.setBackground(getResources().getDrawable(R.drawable.ic_mostrar));
                    layout1.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = layout1.getLayoutParams();
                    params.height = 0;
                    layout1.setLayoutParams(params);

                }else {
                    bt1.setBackground(getResources().getDrawable(R.drawable.ic_ocultar));
                    layout1.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = layout1.getLayoutParams();
                    params.height = 300;
                    layout1.setLayoutParams(params);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(layout2.getVisibility(), 0)){
                    bt2.setBackground(getResources().getDrawable(R.drawable.ic_mostrar));
                    layout2.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = layout2.getLayoutParams();
                    params.height = 0;
                    layout2.setLayoutParams(params);

                }else {
                    bt2.setBackground(getResources().getDrawable(R.drawable.ic_ocultar));
                    layout2.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = layout2.getLayoutParams();
                    params.height = 200;
                    layout2.setLayoutParams(params);
                }
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(layout3.getVisibility(), 0)){
                    bt3.setBackground(getResources().getDrawable(R.drawable.ic_mostrar));
                    layout3.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = layout3.getLayoutParams();
                    params.height = 0;
                    layout3.setLayoutParams(params);

                }else {
                    bt3.setBackground(getResources().getDrawable(R.drawable.ic_ocultar));
                    layout3.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = layout3.getLayoutParams();
                    params.height = 800;
                    layout3.setLayoutParams(params);
                }
            }
        });


        //Cargar grupos a la listaaa
        listagrupoPerfil = new ArrayList<>();
        recyclerGrupoPerfil = (RecyclerView) vista.findViewById(R.id.recycler_grupos_perfil);
        recyclerGrupoPerfil.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista(idusuario);

        grupoAdapterPerfil = new GrupoPerfilAdapter(listagrupoPerfil, getContext());
        recyclerGrupoPerfil.setAdapter(grupoAdapterPerfil);
        grupoAdapterPerfil.traeridusuario(idusuario);

        return vista;
    }

    private void llenarLista(String idusuar) {

        databaseReference.child("Grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listagrupoPerfil.clear();
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
                if (personas.contains(iduser)&&estado.equals("Activo")){
                    grupoAdapterPerfil.agregarGrupo(grupo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}




    /*private void llenarLista(String idusuar) {
        databaseReference.child("Grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                grupoAdapterPerfil.limpiarlista();
                //listagrupoPerfil.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Grupo g = objSnapshot.getValue(Grupo.class);
                    g.setId(Integer.parseInt(objSnapshot.getKey()));

                    seencuentrausuario(idusuar, g); //pregunto si pertenece
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seencuentrausuario(String iduser, Grupo grupo) {

        databaseReference.child("Grupos").child(String.valueOf(grupo.getId())).child("integrantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                ArrayList<String> personas = new ArrayList<>();

                for (DataSnapshot objSnapshot1 : snapshot1.getChildren()){

                    String pers = objSnapshot1.getValue().toString();
                    personas.add(pers);
                }
                //si esta en el grupo que lo muestre
                if (personas.contains(iduser)&&grupo.getEstado().equals("Activo")){
                    //listagrupoPerfil.add(grupo);
                    grupoAdapterPerfil.agregarGrupo(grupo);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
*/





//Query consulta = databaseReference.child("Grupo").child("integrantes").orderByValue().equalTo(idusuar);
//System.out.println(consulta);
        /*Query consulta = databaseReference.child("Grupo").child(String.valueOf(g.getId())).child("integrantes").orderByValue().equalTo(idusuar);
                    consulta.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for (DataSnapshot obj : snapshot1.getChildren()){
                                String valor = obj.getValue().toString();
                                System.out.println(valor);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error1) {

                        }
                    });*/
//System.out.println(consulta.get());


/*/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/*    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/

    /*
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilFragment.
         */
/*    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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