package com.example.turismo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CrearGrupoActivity extends AppCompatActivity {

    Spinner sp_sitio, sp_genero, sp_mes, sp_origen, sp_cant_max, sp_cant_min, sp_region, sp_estado;
    EditText et_descripcion, et_cant, et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        //decraracion y vinculacion de spinner para los filtros
        sp_sitio = (Spinner) findViewById(R.id.spinner_sitio);
        sp_genero = (Spinner) findViewById(R.id.spinner_genero);
        sp_mes = (Spinner) findViewById(R.id.spinner_mes);
        sp_origen = (Spinner) findViewById(R.id.spinner_origen);
        sp_cant_min = (Spinner) findViewById(R.id.spinner_cant_min);
        sp_cant_max = (Spinner) findViewById(R.id.spinner_cant_max);
        sp_region = (Spinner) findViewById(R.id.spinner_region);
        sp_estado = (Spinner) findViewById(R.id.spinner_estado);

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
}