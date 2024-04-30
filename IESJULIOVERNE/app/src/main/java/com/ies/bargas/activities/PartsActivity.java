package com.ies.bargas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ies.bargas.R;
import com.ies.bargas.adapters.PagerAdapterParts;
import com.ies.bargas.util.Util;

public class PartsActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private TextView titulo;
    private FloatingActionButton floatAction;

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

        setTabLayout();

        floatAction=findViewById(R.id.addParts);

        //shared preferences

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
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



        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapterParts adapter = new PagerAdapterParts(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Manejo de los tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(PartsActivity.this, "Seleccionado -> "+tab.getPosition(), Toast.LENGTH_SHORT).show();
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartsActivity.this, AddPartsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setTabLayout() {
        tabLayout = findViewById(R.id.tabPartsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Partes"));
        tabLayout.addTab(tabLayout.newTab().setText("Expulsiones"));
        tabLayout.addTab(tabLayout.newTab().setText("Alumnos"));

        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_anadir_persona);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_bandera);

    }

    private void setCredentialsIfExist(TextView navUsername) {
        String nombre = Util.getUserNombrePrefs(prefs);
        String apellidos = Util.getUserApellidosPrefs(prefs);
        if (!nombre.isEmpty() && !apellidos.isEmpty()){
            navUsername.setText(nombre+ " "+apellidos);

        }
    }
}
