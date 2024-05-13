package com.ies.bargas.activities.shifts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ies.bargas.activities.LoginActivity;
import com.ies.bargas.activities.MainActivity;
import com.ies.bargas.activities.UserProfileActivity;
import com.ies.bargas.activities.parts.AddPartsActivity;
import com.ies.bargas.activities.parts.PartsActivity;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Departamento;
import com.ies.bargas.model.Guardia;
import com.ies.bargas.model.Periodo;
import com.ies.bargas.model.User;
import com.ies.bargas.util.Util;
//import com.ies.bargas.views.MultiSpinner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ies.bargas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Agregar guardias y hay que darle funcionalidad
public class AddShiftsActivity extends AppCompatActivity {

    // Declaración de las vistas
    //private MultiSpinner spinnerPeriodo;
    private Spinner spinnerUsuario;
    private Spinner spinnerPeriodo;
    private CalendarView calendario;

    private EditText editTextObservaciones;
    private Button buttonGuardar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private String idGuardia;
    private LocalDate globalFechaComunicacion=LocalDate.now();
    private List<User> globalUsuarios;
    private List<Periodo> globalPeriodos;
    private User globalUsuario;
    private Periodo globalPeriodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addshifts);

        // Inicialización de las vistas
        calendario = findViewById(R.id.calendarView);
        spinnerPeriodo = findViewById(R.id.spinnerPeriodo);
        spinnerUsuario = findViewById(R.id.spinnerUsuario);
        editTextObservaciones = findViewById(R.id.editTextObservaciones);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // configurar el diseño del layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AGREGAR GUARDÍAS");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //comprobar si esta en edit
        idGuardia = getIntent().getStringExtra("idGuardia");
        if (idGuardia != null) {
            // Estamos en modo de edición
            // Rellenar los campos con los datos de la guardia
        }

        // configurar la vista de la navegacion
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                if (id == R.id.nav_parts) {
                    // Inicia la Activity para mostrar las partes
                    intent = new Intent(AddShiftsActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(AddShiftsActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(AddShiftsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(AddShiftsActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(AddShiftsActivity.this, LoginActivity.class);
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

        //se auto-rellenan el email y contraseña en caso de haberse guardado
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist(navUsername);

       /// Configuración del Spinner de periodo
        obtenerPeriodos();
        spinnerPeriodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalPeriodo=globalPeriodos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Configuración del Spinner de usuarios
        obtenerUsuarios();
        spinnerUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalUsuario=globalUsuarios.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Manejo del evento de clic en el botón Guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarGuardia();
                // Cierra AddShiftsActivity y vuelve a ShiftsActivity
                finish();
            }
        });

        //Para volver a shiftsActivity
        Button buttonVolver = findViewById(R.id.buttonVolver);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Obtener la fecha seleccionada del CalendarView
                // y crear un objeto LocalDate
                globalFechaComunicacion= LocalDate.of(year, month + 1, dayOfMonth);
            }
        });
    }

    private void obtenerPeriodos() {
        List<Periodo> periodos = new ArrayList<Periodo>();

        // URL del script PHP

        String url = WebService.RAIZ + WebService.Periodos;

        // Crear una solicitud de tipo StringRequest con Volley
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Convierte la respuesta en una lista de periodos
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int cod_periodo = jsonObject.getInt("cod_periodo");
                            String inicio = jsonObject.getString("inicio");
                            String fin = jsonObject.getString("fin");
                            Periodo periodo = new Periodo(cod_periodo, inicio, fin);
                            periodos.add(periodo);

                        }
                        globalPeriodos=periodos;
                        // Actualizar el Spinner de periodos
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddShiftsActivity.this, android.R.layout.simple_spinner_item, Periodo.toStringNombre(periodos));
                        spinnerPeriodo.setAdapter(adapter1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Manejar el error
                    Toast.makeText(AddShiftsActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void obtenerUsuarios() {
        // URL del script PHP
        String url = WebService.RAIZ + WebService.Usuarios;
        List<User> usuarios = new ArrayList<User>();

        // Crear una solicitud de tipo StringRequest con Volley
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Convierte la respuesta en una lista de usuarios
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String nombre = jsonObject.getString("nombre");
                            String apellidos = jsonObject.getString("apellidos");
                            String dni = jsonObject.getString("dni");
                            int cod_usuario = jsonObject.getInt("cod_usuario");
                            String email = jsonObject.getString("email");
                            String clave = jsonObject.getString("clave");
                            String rol = jsonObject.getString("rol");
                            int cod_delphos = 0;

                            if(!jsonObject.getString("cod_delphos").equals("null")){
                                cod_delphos =jsonObject.getInt("cod_delphos");
                            }
                            String validar = jsonObject.getString("validar");
                            Departamento departamento = new Departamento();

                            if(!jsonObject.getString("departamento_codigo").equals("null")) {

                                int departamento_codigo = jsonObject.getInt("departamento_codigo");
                                String departamento_nombre = jsonObject.getString("departamento_nombre");
                                departamento = new Departamento(departamento_codigo, departamento_nombre);
                            }
                            String tutor_grupo ="";
                            if(!jsonObject.getString("tutor_grupo").equals("null")) {
                                tutor_grupo = jsonObject.getString("tutor_grupo");
                            }

                                User user = new User(cod_usuario, email, clave, nombre, apellidos, dni, cod_delphos, validar, departamento, tutor_grupo, rol);
                    usuarios.add(user);
                            }
                        globalUsuarios=usuarios;
                        // Actualizar el Spinner de usuarios
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddShiftsActivity.this,
                                android.R.layout.simple_spinner_item, User.toStringNombre(usuarios));
                        spinnerUsuario.setAdapter(adapter1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Manejar el error
                    Toast.makeText(AddShiftsActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void guardarGuardia() {
        // URL del script PHP addShifts.php
        // Obtener los valores de los campos
        //String fecha = editTextFecha.getText().toString();
        String observaciones = editTextObservaciones.getText().toString();
        LocalDate fechaComunicacion = globalFechaComunicacion;

        Guardia guardia = new Guardia(observaciones, globalUsuario, fechaComunicacion,  globalPeriodo);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.AddShifts + "?"
                + "cod_usuario=" + guardia.getUsuario().getCod_usuario()
                + "&fecha=" + guardia.getFecha()
                + "&observaciones=" + guardia.getObservaciones()
                + "&cod_periodo=" + guardia.getPeriodo().getCod_periodo();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddShiftsActivity.this, "La guardía ha sido añadidoa", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddShiftsActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);

    }


    private void setCredentialsIfExist(TextView navUsername) {
        String nombre = Util.getUserNombrePrefs(prefs);
        String apellidos = Util.getUserApellidosPrefs(prefs);
        if (!nombre.isEmpty() && !apellidos.isEmpty()){
            navUsername.setText(nombre+ " "+apellidos);

        }
    }
}
