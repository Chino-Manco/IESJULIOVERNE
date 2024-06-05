package com.ies.bargas.activities.parts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.ies.bargas.adapters.ParteAdapter;
import com.ies.bargas.adapters.ParteExpulsionAdapter;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.model.Expulsion;
import com.ies.bargas.model.Incidencia;
import com.ies.bargas.model.Parte;
import com.ies.bargas.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModifyExpulsionActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private TextView textViewRecomendacion;
    private Spinner spinnerExpulsion;
    private CalendarView calendarViewIni;
    private CalendarView calendarViewFin;
    private Button buttonBack;
    private Button buttonSave;
    private Expulsion globalExpulsion = new Expulsion();
    private List<Parte> globalPartes = new ArrayList<Parte>();
    private ParteExpulsionAdapter adapter;
    private ListView listViewParteExpulsion;
    private TextView error;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_expulsion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar Expulsión");

        editTextNombre = findViewById(R.id.ediTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        textViewRecomendacion = findViewById(R.id.textRecomendacion);
        spinnerExpulsion = findViewById(R.id.spinnerTipoExpulsion);
        calendarViewIni = findViewById(R.id.calendarViewInicio);
        calendarViewFin = findViewById(R.id.calendarViewFin);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSave = findViewById(R.id.buttonSave);
        listViewParteExpulsion= findViewById(R.id.listViewPartesExpulsion);
        error = findViewById(R.id.errorExpulsion);
        recuperarExpulsion();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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
                    intent = new Intent(ModifyExpulsionActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(ModifyExpulsionActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyExpulsionActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyExpulsionActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(ModifyExpulsionActivity.this, LoginActivity.class);
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


        calendarViewIni.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                globalExpulsion.setFecha_Inicio(LocalDate.of(year, month+1, dayOfMonth));
            }
        });

        calendarViewFin.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                globalExpulsion.setFecha_Fin(LocalDate.of(year, month+1, dayOfMonth));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyExpulsionActivity.this, PartsActivity.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expulsar();
            }
        });

    }

    private void recuperarExpulsion(){
        Intent intent = getIntent();

        int codExpulsion = intent.getIntExtra("cod_expulsion", 0);
        int codUsuario = intent.getIntExtra("cod_usuario", 0);
        String fechaI = intent.getStringExtra("fecha_Inicio");
        String fechaF = intent.getStringExtra("Fecha_Fin");
        String tipoExpulsion = intent.getStringExtra("tipo_expulsion");
        Timestamp fechaInsercion = Timestamp.valueOf(intent.getStringExtra("fecha_Insercion"));

        String matriculaAlumno = intent.getStringExtra("matricula_Alumno");
        String grupoAlumno = intent.getStringExtra("grupo_Alumno");
        String apellidosAlumno = intent.getStringExtra("apellidos_Alumno");
        String nombreAlumno = intent.getStringExtra("nombre_Alumno");

        if (fechaI != null &&  !fechaF.equals("null")) {

            LocalDate fechaInicio= LocalDate.parse(fechaI);
            LocalDate fechaFin = LocalDate.parse(fechaF);
            globalExpulsion = new Expulsion(codExpulsion, codUsuario,
                    new Alumno(matriculaAlumno, nombreAlumno, apellidosAlumno, grupoAlumno), fechaInicio, fechaFin, tipoExpulsion, fechaInsercion);
        } else {
            globalExpulsion = new Expulsion(codExpulsion, codUsuario,
                    new Alumno(matriculaAlumno, nombreAlumno, apellidosAlumno, grupoAlumno), tipoExpulsion, fechaInsercion);
        }


        rellenar();

    }


    private void rellenar(){
        editTextNombre.setText(globalExpulsion.getMatricula_del_Alumno().getNombre());
        editTextApellidos.setText(globalExpulsion.getMatricula_del_Alumno().getApellidos());
        editTextNombre.setEnabled(false);
        editTextApellidos.setEnabled(false);
        textViewRecomendacion.setText("Recomendación por parte del profesorado:\n"+
                globalExpulsion.getTipo_expulsion());


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyExpulsionActivity.this,
                android.R.layout.simple_spinner_item, new String[] {"Expulsión a casa", "Trabajo social educativo"});
        spinnerExpulsion.setAdapter(adapter1);

        if (globalExpulsion.getTipo_expulsion().equals("Trabajo social educativo"))
            spinnerExpulsion.setSelection(1);


        RequestQueue queue = Volley.newRequestQueue(ModifyExpulsionActivity.this);
        String url = WebService.RAIZ + WebService.findAllPartesByAlumno + "?"
                + "matricula="+ globalExpulsion.getMatricula_del_Alumno().getMatricula();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {

                    if(response.length()>0) {

                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            // Extraer atributos del objeto JSON para la tabla Partes
                            int cod_parte = jsonObject.getInt("cod_parte");
                            int cod_usuario = jsonObject.getInt("cod_usuario");
                            int materia = jsonObject.optInt("materia", 0); // Usar optInt para manejar el caso en que el atributo no esté presente
                            LocalDate fecha = LocalDate.parse(jsonObject.getString("fecha"));
                            String hora = jsonObject.getString("hora");
                            String descripcion = jsonObject.getString("descripcion");
                            LocalDate fechaComunicacion = LocalDate.parse(jsonObject.getString("fecha_Comunicacion"));
                            String viaComunicacion = jsonObject.getString("via_Comunicacion");
                            String tipoParte = jsonObject.getString("tipo_Parte");
                            int caducado = jsonObject.getInt("caducado");

                            // Extraer atributos del objeto JSON para la tabla Alumnos
                            String matricula = jsonObject.optString("matricula");
                            String nombreAlumno = jsonObject.optString("nombre");
                            String apellidos = jsonObject.optString("apellidos");
                            String grupoAlumno = jsonObject.optString("grupo"); // Cambiado para coincidir con el nombre del atributo en la tabla

                            // Extraer atributos del objeto JSON para la tabla Incidencia
                            int cod_incidencia = jsonObject.getInt("cod_incidencia");
                            String nombreIncidencia = jsonObject.getString("inci_nombre");
                            int puntos = jsonObject.getInt("puntos");
                            String descripcionIncidencia = jsonObject.getString("inci_descripcion");

                            Parte parte = new Parte(cod_parte, cod_usuario, new Alumno(matricula, nombreAlumno, apellidos, grupoAlumno),
                                    new Incidencia(cod_incidencia, nombreIncidencia, puntos, descripcionIncidencia), materia, fecha,
                                    hora, descripcion, fechaComunicacion, viaComunicacion, tipoParte, caducado);


                            globalPartes.add(parte);

                        }
                    }
                    adapter = new ParteExpulsionAdapter(ModifyExpulsionActivity.this, globalPartes);


                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250*globalPartes.size());

                    // Establecer los parámetros de diseño en el ListView
                    listViewParteExpulsion.setLayoutParams(params);


                    // Establecer el adaptador en el ListView
                    listViewParteExpulsion.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(ModifyExpulsionActivity.this, "ERROR: No se encontro ningún parte", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifyExpulsionActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsonArrayRequest);


    }


    private void expulsar(){

        globalExpulsion.setTipo_expulsion(spinnerExpulsion.getSelectedItem().toString());
        if (globalExpulsion.getFecha_Inicio()==null)
            globalExpulsion.setFecha_Inicio(LocalDate.now());
        if (globalExpulsion.getFecha_Fin()==null)
            globalExpulsion.setFecha_Fin(LocalDate.now());



        if (adapter.getPuntosPartesMarcadas() >= 10){
            List<Parte> partesUsar = adapter.getPartesMarcadas();

            //Usar Partes
            RequestQueue queue = Volley.newRequestQueue(ModifyExpulsionActivity.this);
            String url = WebService.RAIZ + WebService.usarPartes + "?";
            for (int i=0; i< partesUsar.size(); i++){
                url += "cod_parte[]=" + partesUsar.get(i).getCod_parte();
                if (i < (partesUsar.size()-1))
                    url += "&";
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ModifyExpulsionActivity.this, "Partes usados", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModifyExpulsionActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);

            //Modificar Fechas de expulsión
            queue = Volley.newRequestQueue(ModifyExpulsionActivity.this);
            url = WebService.RAIZ + WebService.modifyExpulsion + "?"
                    + "cod_expulsion=" + globalExpulsion.getCod_expulsion()
                    + "&fecha_Inicio=" + globalExpulsion.getFecha_Inicio()
                    + "&fecha_Fin=" + globalExpulsion.getFecha_Fin()
                    + "&tipo_expulsion=" + globalExpulsion.getTipo_expulsion();


            stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int respuesta= Integer.parseInt(response);
                            if (respuesta==0){
                                Toast.makeText(ModifyExpulsionActivity.this, "No se ha podido modificar porque no existe la expulsión ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                            } else if (respuesta==1){
                                Toast.makeText(ModifyExpulsionActivity.this, "la expulsión con código "+globalExpulsion.getCod_expulsion()+" Ha sido modificado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ModifyExpulsionActivity.this, "Algo ha fallado durante la modificación, no preguntes el qué ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_LONG).show();
                            }
                            Intent intent = new Intent(ModifyExpulsionActivity.this, PartsActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModifyExpulsionActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);


        } else {
            error.setText("La suma de los puntos de los partes seleccionados deben ser de al menos 10 puntos");
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
