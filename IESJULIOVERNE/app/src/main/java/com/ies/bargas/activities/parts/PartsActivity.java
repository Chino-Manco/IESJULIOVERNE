package com.ies.bargas.activities.parts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ies.bargas.R;
import com.ies.bargas.activities.LoginActivity;
import com.ies.bargas.activities.MainActivity;
import com.ies.bargas.activities.shifts.ShiftsActivity;
import com.ies.bargas.activities.UserProfileActivity;
import com.ies.bargas.fragments.AlumnosFragment;
import com.ies.bargas.fragments.ExpulsionesFragment;
import com.ies.bargas.fragments.PartesFragment;
import com.ies.bargas.util.Util;

public class PartsActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private BottomNavigationView bottomNavigationView;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);
        // inicializar las vistas y cargar los datos necesarios
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PARTES");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //shared preferences

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        rol= Util.getUserRolPrefs(prefs);
        // configurar la vista de la navegacion
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                if (id == R.id.nav_parts) {
                    // Inicia la Activity para mostrar las partes
                    intent = new Intent(PartsActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(PartsActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(PartsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(PartsActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(PartsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();  // Cierra MainActivity
                }
                // Cierra el menú de navegación después de manejar el clic
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // establecer el nombre del usuario en el header
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        setCredentialsIfExist(navUsername);





        //Manejo de los tabs

        // Configura el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation_parts);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.navigation_partes) {
                    selectedFragment = new PartesFragment();
                } else if (id == R.id.navigation_expulsiones) {
                    selectedFragment = new ExpulsionesFragment();
                } else if (id == R.id.navigation_alumnos) {
                    selectedFragment = new AlumnosFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_part, selectedFragment).commit();
                }

                return true;
            }
        });


        // Establece el fragment inicial
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_part, new PartesFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_partes);
        }


        // Obtener referencia al menú del BottomNavigationView y deshabilitar elementos
        Menu bottomMenu = bottomNavigationView.getMenu();
        MenuItem partesItem = bottomMenu.findItem(R.id.navigation_partes);
        MenuItem expulsionesItem = bottomMenu.findItem(R.id.navigation_expulsiones);
        MenuItem alumnosItem = bottomMenu.findItem(R.id.navigation_alumnos);

        // Desactivar ítems según sea necesario
        if (!rol.equals("0")){
            expulsionesItem.setEnabled(false);
            expulsionesItem.setVisible(false);
            alumnosItem.setEnabled(false);
            alumnosItem.setVisible(false);
        }


    }





    private void setCredentialsIfExist(TextView navUsername) {
        String nombre = Util.getUserNombrePrefs(prefs);
        String apellidos = Util.getUserApellidosPrefs(prefs);
        if (!nombre.isEmpty() && !apellidos.isEmpty()){
            navUsername.setText(nombre+ " "+apellidos);

        }
    }
}
