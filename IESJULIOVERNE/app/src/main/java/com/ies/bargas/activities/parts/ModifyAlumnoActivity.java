package com.ies.bargas.activities.parts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ies.bargas.R;
import com.ies.bargas.activities.LoginActivity;
import com.ies.bargas.activities.MainActivity;
import com.ies.bargas.activities.UserProfileActivity;
import com.ies.bargas.activities.shifts.ShiftsActivity;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyAlumnoActivity extends AppCompatActivity {

    private EditText matricula;
    private EditText nombre;
    private EditText apellidos;
    private Spinner spinnerCurso;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private Button volver;
    private Button guardar;
    private TextView error;
    private String globalGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumno);

        matricula= findViewById(R.id.editTextMatricula);
        nombre= findViewById(R.id.editTextNombre);
        apellidos= findViewById(R.id.editTextApellidos);
        spinnerCurso= findViewById(R.id.spinnerCurso);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        volver= findViewById(R.id.buttonBackAlumno);
        guardar= findViewById(R.id.buttonSaveAlumno);
        error= findViewById(R.id.errorAddAlumno);

        Intent intent = getIntent();

        matricula.setText(intent.getStringExtra("matricula"));
        nombre.setText(intent.getStringExtra("nombre"));
        apellidos.setText(intent.getStringExtra("apellidos"));
        findAllCursos(intent.getStringExtra("grupo"));

        matricula.setEnabled(false);


        spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene el valor seleccionado del Spinner
                String palabras=(String) parent.getItemAtPosition(position);

                String[] conjunto=palabras.split(" - ");

                globalGrupo = conjunto[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyAlumnoActivity.this, PartsActivity.class);
                startActivity(intent);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modify();
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar Usuario");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
                    intent = new Intent(ModifyAlumnoActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(ModifyAlumnoActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyAlumnoActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyAlumnoActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(ModifyAlumnoActivity.this, LoginActivity.class);
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

    }



    private void findAllCursos(String grupo2) {

        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.Cursos;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    int n=0;
                    //Del array obtenido de la bbdd se obtiene el grupo para guardarlo directamente en un array de String
                    if (response.length() > 0) {
                        String[] opciones = new String[response.length()];
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            String grupo = jsonObject.getString("grupo");
                            String curso="";
                            if(!jsonObject.getString("curso").equals("null")){
                                curso = jsonObject.getString("curso");
                            }
                            opciones[i] = grupo+" - "+curso;
                            if (grupo.equals(grupo2)){
                                n=i;
                            }

                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ModifyAlumnoActivity.this,
                                android.R.layout.simple_spinner_item, opciones);
                        spinnerCurso.setAdapter(adapter2);
                        spinnerCurso.setSelection(n);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningÃºn curso", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ModifyAlumnoActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void setCredentialsIfExist(TextView navUsername) {
        String nombre = Util.getUserNombrePrefs(prefs);
        String apellidos = Util.getUserApellidosPrefs(prefs);
        if (!nombre.isEmpty() && !apellidos.isEmpty()){
            navUsername.setText(nombre+ " "+apellidos);

        }
    }

    private void Modify() {
        if (nombre.getText().toString().isEmpty() || apellidos.getText().toString().isEmpty()){
            error.setText("Completa todos los campos antes de guardar");
        } else {
            String mat=matricula.getText().toString();
            String nom= nombre.getText().toString();
            String ape= apellidos.getText().toString();
            String gru= globalGrupo;

            Alumno newAlumno= new Alumno(mat, nom, ape, gru);


            RequestQueue queue = Volley.newRequestQueue(ModifyAlumnoActivity.this);
            String url = WebService.RAIZ + WebService.ModificarAlumno + "?"
                    + "matricula=" + newAlumno.getMatricula()
                    + "&nombre=" + newAlumno.getNombre()
                    + "&apellidos=" + newAlumno.getApellidos()
                    + "&grupo=" + newAlumno.getGrupo();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int respuesta= Integer.parseInt(response);
                            if (respuesta==0){
                                Toast.makeText(ModifyAlumnoActivity.this, "No se ha podido modificar porque no existe el usuario ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ModifyAlumnoActivity.this, PartsActivity.class);
                                startActivity(intent);
                            } else if (respuesta==1){
                                Toast.makeText(ModifyAlumnoActivity.this, newAlumno.getNombre()+" Ha sido modificado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ModifyAlumnoActivity.this, PartsActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ModifyAlumnoActivity.this, "Algo ha fallado durante la modificación, no preguntes el qué ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModifyAlumnoActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);

        }
    }

}
