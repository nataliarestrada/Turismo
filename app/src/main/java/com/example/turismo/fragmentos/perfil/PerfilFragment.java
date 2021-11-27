package com.example.turismo.fragmentos.perfil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turismo.Integrantes;
import com.example.turismo.R;
import com.example.turismo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PerfilFragment extends Fragment {

    private String idusuario;
    View vista;
    ImageButton bt1, bt2, bt3;
    ImageView imagen;
    TextView alias,nombre,edad,genero,domicilio,email,telefono;
    LinearLayout layout1, layout2, layout3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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
                System.out.println(u.getGenero());

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
                    params.height = 400;
                    layout3.setLayoutParams(params);
                }
            }
        });

        return vista;
    }
}






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