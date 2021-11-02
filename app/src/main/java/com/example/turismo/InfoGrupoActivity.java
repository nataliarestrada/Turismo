package com.example.turismo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoGrupoActivity extends AppCompatActivity {

    TextView id;
    int idg;
    String aaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        id = findViewById(R.id.tv_id);
        //idg = (int) getIntent().getExtras().getSerializable("idgrupo");

        aaa = (String) getIntent().getExtras().getSerializable("idgrupo");

        id.setText(aaa);
        System.out.println(aaa);

        idg = Integer.parseInt(aaa);
        System.out.println(idg);

    }
}