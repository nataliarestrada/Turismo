package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turismo.fragmentos.grupos.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CrearGrupoActivity extends AppCompatActivity {

    Spinner sp_sitio, sp_genero, sp_mes, sp_origen, sp_cant_max, sp_cant_min, sp_region, sp_estado;
    TextView titulo;
    EditText et_descripcion, et_cant, et_id;
    Button bt_crear, bt_modificar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    long maxid = 0;
    String iduser,fragmento,idgrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        iduser=(String) getIntent().getExtras().getSerializable("iduser");
        fragmento = (String) getIntent().getExtras().getSerializable("origen");
        idgrupo = (String) getIntent().getExtras().getSerializable("idgrupo");

        //System.out.println(iduser);

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
        titulo = (TextView) findViewById(R.id.titulo_grupos);
        //botones
        bt_crear = (Button) findViewById(R.id.button_crear_grupo);
        bt_modificar = (Button) findViewById(R.id.button_modificar);

        //Cargar SPINNER
        cargarspinner();

        //inicializarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //botones segun de que fragmento vengo
        if (fragmento.equals("grupos")){
            bt_modificar.setVisibility(View.INVISIBLE);

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
        }
        if (fragmento.equals("perfil")){
            //oculto el boton
            bt_crear.setVisibility(View.INVISIBLE);
            //cambio el tirulo
            titulo.setText("Modificar Grupo");
            //cargo los campos con los datos del grupo
            llenarcampos(idgrupo);
        }

        //boton crear
        bt_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrargrupo(Integer.parseInt(String.valueOf(maxid)),iduser);
                finish();
            }
        });

        //Boton modificar
        bt_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificargrupo(idgrupo);
                finish();
            }
        });
    }

    private void llenarcampos(String id) {
        databaseReference.child("Grupos").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                Grupo g = snapshot.getValue(Grupo.class);

                //String que almacena el nombre de la fruta donde inicializara el valor ítem del spinner
                //ESTE STRING LO PODEMOS OBTENER DE DIFERENTES FORMAS (DESDE UN BASE DE DATOS, UN ARREGLO, ...)
                String sitio = g.getSitio();
                String region = g.getRegion();
                String genero = g.getGenero();
                String mes_estimado = g.getMes_estimado();
                String origen = g.getOrigen();
                String cant_min = String.valueOf(g.getCant_min());
                String cant_max = String.valueOf(g.getCant_max());

                /**
                 * setSelection(): sirve para inicializar una posición especifica(recibe un valor entero)
                 *
                 * Este método realiza la magia
                 * obtenerPosicionItem(): recibe dos parámmetros un spinner y un String
                 */
                sp_sitio.setSelection(obtenerPosicionItem(sp_sitio, sitio));
                sp_region.setSelection(obtenerPosicionItem(sp_region, region));
                sp_genero.setSelection(obtenerPosicionItem(sp_genero, genero));
                sp_mes.setSelection(obtenerPosicionItem(sp_mes, mes_estimado));
                sp_origen.setSelection(obtenerPosicionItem(sp_origen, origen));
                sp_cant_min.setSelection(obtenerPosicionItem(sp_cant_min, cant_min));
                sp_cant_max.setSelection(obtenerPosicionItem(sp_cant_max, cant_max));
                et_descripcion.setHint(g.getDescripcion());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cargarspinner(){
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
    }

    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String fruta) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
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
        grupo.put("region","Puna");
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

    private void modificargrupo(String idgrupo) {

        databaseReference.child("Grupos").child(idgrupo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                Grupo g = snapshot.getValue(Grupo.class);
                String sitio_ant=g.getSitio();
                String genero_ant = g.getGenero();
                String mes_estimado_ant=g.getMes_estimado();
                String origen_ant=g.getOrigen();
                int cant_min_ant=g.getCant_min();
                int cant_max_ant=g.getCant_max();
                String descripcion_ant=g.getDescripcion();

                comparardatosymodificar(idgrupo,sitio_ant,genero_ant,mes_estimado_ant,origen_ant,cant_min_ant,cant_max_ant,descripcion_ant);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void comparardatosymodificar(String idg, String sitio_ant, String genero_ant, String mes_estimado_ant, String origen_ant, int cant_min_ant, int cant_max_ant, String descripcion_ant) {
        String sitio = sp_sitio.getSelectedItem().toString();
        String genero = sp_genero.getSelectedItem().toString();
        String mes_estimado = sp_mes.getSelectedItem().toString();
        String origen = sp_origen.getSelectedItem().toString();
        int cant_min = Integer.parseInt(sp_cant_min.getSelectedItem().toString());
        int cant_max = Integer.parseInt(sp_cant_max.getSelectedItem().toString());
        String descripcion = et_descripcion.getText().toString();

        //comparamos y en caso de modificacion actualizamos
        if (!sitio.equals(sitio_ant)){
            databaseReference.child("Grupos").child(idg).child("sitio").setValue(sitio);
        }
        if (!genero.equals(genero_ant)){
            databaseReference.child("Grupos").child(idg).child("genero").setValue(genero);
        }
        if (!mes_estimado.equals(mes_estimado_ant)){
            databaseReference.child("Grupos").child(idg).child("mes_estimado").setValue(mes_estimado);
        }
        if (!origen.equals(origen_ant)){
            databaseReference.child("Grupos").child(idg).child("origen").setValue(origen);
        }
        if (cant_min!=cant_min_ant){
            databaseReference.child("Grupos").child(idg).child("cant_min").setValue(cant_min);
        }
        if (cant_max!=cant_max_ant){
            databaseReference.child("Grupos").child(idg).child("cant_max").setValue(cant_max);
        }
        if (!descripcion.equals("")){
            //!descripcion.equals(descripcion_ant)
            databaseReference.child("Grupos").child(idg).child("descripcion").setValue(descripcion);
        }

        Toast.makeText(this,"Grupo modificado", Toast.LENGTH_LONG).show();
    }


}