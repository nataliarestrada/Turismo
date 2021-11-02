package com.example.turismo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.turismo.fragmentos.grupos.GruposFragment;
import com.example.turismo.fragmentos.inicio.InicioFragment;
import com.example.turismo.fragmentos.perfil.PerfilFragment;
import com.example.turismo.fragmentos.sitios.SitiosFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iduser = (String) getIntent().getExtras().getSerializable("idUsuario");

        System.out.println("Holaaaaa "+iduser);


        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new InicioFragment(iduser)).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_inicio);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                switch(id){
                    case R.id.nav_inicio:
                        fragment = new InicioFragment(iduser);
                        break;

                    case R.id.nav_sitios:
                        fragment = new SitiosFragment();
                        break;

                    case R.id.nav_grupo:
                        fragment = new GruposFragment(iduser);
                        break;

                    case R.id.nav_perfil:
                        fragment = new PerfilFragment(iduser);
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                return true;
            }
        });



    }
}

//    private ActivityMainBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_inicio, R.id.nav_sitios, R.id.nav_grupo, R.id.nav_perfil)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.frame_container);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
//    }

/*        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()){

                    case R.id.nav_inicio:
                        fragment = new InicioFragment();
                        break;

                    case R.id.nav_sitios:
                        fragment = new SitiosFragment();
                        break;

                    case R.id.nav_grupo:
                        fragment = new GruposFragment();
                        break;

                    case R.id.nav_perfil:
                        fragment = new PerfilFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                return true;
            }
        });*/