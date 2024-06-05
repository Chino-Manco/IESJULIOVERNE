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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.model.Asignatura;
import com.ies.bargas.model.Incidencia;
import com.ies.bargas.model.Parte;
import com.ies.bargas.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ModifyPartActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences prefs;
    private LocalDate globalFechaComunicacion=LocalDate.now();
    private Incidencia globalIncidencia = new Incidencia();
    private Asignatura globalAsignatura = new Asignatura();
    private List<Incidencia> globalIncidencias = new ArrayList<Incidencia>();
    private List<Asignatura> globalAsignaturas= new ArrayList<Asignatura>();
    private Parte globalParte = new Parte();
    private List<Alumno> globalAlumnos = new ArrayList<Alumno>();
    private Alumno globalAlumno = new Alumno();
    private Spinner spinnerCurso;
    private Spinner spinnerAlumno;
    private Spinner spinnerAsignatura;
    private Spinner spinnerIncidencia;
    private TextView incidenciaDescripcion;
    private EditText partDescripcion;
    private RadioGroup radioGroupCommunication;
    private RadioButton entrevista;
    private RadioButton llamada;
    private RadioButton mensaje;
    private RadioButton notificacion;
    private CalendarView calendarView;
    private TextView errorAddPart;
    private Button buttonBack;
    private Button buttonSave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editar Parte");


        spinnerCurso = findViewById(R.id.spinnerCurso);
        spinnerAlumno = findViewById(R.id.spinnerAlumno);
        spinnerAsignatura = findViewById(R.id.spinnerAsignatura);
        spinnerIncidencia = findViewById(R.id.spinnerIncidencia);
        incidenciaDescripcion = findViewById(R.id.incidenciaDescripcion);
        partDescripcion = findViewById(R.id.partDescripcion);
        radioGroupCommunication = findViewById(R.id.radioGroupCommunication);
        entrevista = findViewById(R.id.entrevista);
        llamada = findViewById(R.id.llamada);
        mensaje = findViewById(R.id.mensaje);
        notificacion = findViewById(R.id.notificacion);
        calendarView = findViewById(R.id.calendarView);
        errorAddPart = findViewById(R.id.errorAddPart);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSave = findViewById(R.id.buttonSave);

        recuperarParte();
        recuperarAlumno();
        findAllIncidencias();
        rellenarDatos();





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
                    intent = new Intent(ModifyPartActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(ModifyPartActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyPartActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(ModifyPartActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(ModifyPartActivity.this, LoginActivity.class);
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

        spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String palabras=(String) parent.getItemAtPosition(position);

                String[] conjunto=palabras.split(" - ");

                String grupo = conjunto[0];
                String clase = conjunto[1];
                findAlumnos(grupo);
                findAsignaturas(clase);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAlumno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalAlumno=globalAlumnos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalAsignatura = globalAsignaturas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerIncidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                globalIncidencia = globalIncidencias.get(position);
                incidenciaDescripcion.setText( globalIncidencia.getDescripcion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                globalFechaComunicacion= LocalDate.of(year, month + 1, dayOfMonth);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyPartActivity.this, PartsActivity.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });

    }

    private void findAllCursos() {

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
                            if (grupo.equals(globalAlumno.getGrupo()))
                                n=i;

                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, opciones);
                        spinnerCurso.setAdapter(adapter2);
                        spinnerCurso.setSelection(n);

                        findAlumnos(globalAlumno.getGrupo());
                        findAsignaturas(opciones[n].split(" - ")[1]);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningÃºn curso", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void findAlumnos(String grupo) {
        List<Alumno> alumnos = new ArrayList<Alumno>();
        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.Alumnos + "?grupo=" + grupo;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    int n=0;
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            String matricula = jsonObject.getString("matricula");
                            String nombre = jsonObject.getString("nombre");
                            String apellidos = jsonObject.getString("apellidos");
                            Alumno alumno = new Alumno(matricula, nombre, apellidos, grupo);
                            alumnos.add(alumno);
                            if (matricula.equals(globalAlumno.getMatricula()))
                                n=alumnos.size()-1;

                        }
                        globalAlumnos=alumnos;
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, Alumno.toStringNombre(alumnos));
                        spinnerAlumno.setAdapter(adapter1);
                        spinnerAlumno.setSelection(n);
                    } else {
                        String[] vacio = new String[0];
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, vacio);
                        spinnerAlumno.setAdapter(adapter1);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningÃºn alumno", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void findAsignaturas(String curso) {
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();
        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.Asignaturas + "?curso=" + curso;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    if (response.length() > 0) {
                        int n=0;
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            int codAsignatura= jsonObject.getInt("cod_asignatura");
                            String nombre = jsonObject.getString("nombre");
                            int horas= jsonObject.getInt("horas");
                            String curso = jsonObject.getString("curso");
                            String tipo = jsonObject.getString("tipo");
                            Asignatura asignatura = new Asignatura(codAsignatura, nombre, horas, curso, tipo);
                            asignaturas.add(asignatura);
                            if (globalParte.getMateria()==codAsignatura)
                                n=i;
                        }
                        globalAsignaturas=asignaturas;
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, Asignatura.toStringNombre(asignaturas));
                        spinnerAsignatura.setAdapter(adapter1);
                        spinnerAsignatura.setSelection(n);

                    } else {
                        globalAsignaturas=new ArrayList<Asignatura>();
                        String[] vacio = new String[0];
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, vacio);
                        spinnerAsignatura.setAdapter(adapter1);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ninguna asignatura", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void findAllIncidencias() {
        List<Incidencia> incidencias = new ArrayList<Incidencia>();
        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.Incidencias;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    if(response.length()>0) {
                        int n=0;
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            int codigo= jsonObject.getInt("cod_incidencia");
                            String nombre = jsonObject.getString("nombre");
                            int puntos = jsonObject.getInt("puntos");
                            String userDescripcion = jsonObject.getString("descripcion");
                            Incidencia incidencia = new Incidencia(codigo, nombre, puntos, userDescripcion);
                            incidencias.add(incidencia);
                            if (codigo==globalParte.getIncidencia().getCodigo())
                                n=i;
                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ModifyPartActivity.this,
                                android.R.layout.simple_spinner_item, Incidencia.toStringNombre(incidencias));
                        spinnerIncidencia.setAdapter(adapter1);
                        spinnerIncidencia.setSelection(n);
                        incidenciaDescripcion.setText(incidencias.get(n).getDescripcion());

                        globalIncidencias=incidencias;
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ninguna incidencia", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void recuperarAlumno(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.findAlumno + "?matricula=" + globalParte.getMatriculaAlumno().getMatricula();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            String nombre = jsonObject.getString("nombre");
                            String apellidos = jsonObject.getString("apellidos");
                            String grupo = jsonObject.getString("grupo");
                            globalAlumno = new Alumno(globalParte.getMatriculaAlumno().getMatricula(), nombre, apellidos, grupo);

                        }
                    }

                    findAllCursos();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningÃºn alumno", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void recuperarParte(){
         int codParte;
         int codUsuario;
         String matriculaAlumno;
         int incidencia;
         int materia;
         LocalDate fecha;
         String hora;
         String descripcion;
         LocalDate fechaComunicacion;
         String viaComunicacion;
         String tipoParte;
         int caducado;

        Intent intent = getIntent();

        codParte = intent.getIntExtra("cod_parte", 0);
        codUsuario = intent.getIntExtra("cod_usuario", 0);

        matriculaAlumno = intent.getStringExtra("matricula_Alumno");
        String grupo = intent.getStringExtra("grupo_Alumno");
        String apellidos = intent.getStringExtra("apellidos_Alumno");
        String nombre = intent.getStringExtra("nombre_Alumno");

        incidencia = intent.getIntExtra("incidencia", 0);
        String descripcionInci = intent.getStringExtra("descripcion_incidencia");
        String nombreInci = intent.getStringExtra("nombre_incidencia");
        int puntosInci = intent.getIntExtra("puntos_incidencia", 0);

        materia = intent.getIntExtra("materia", 0);
        fecha = LocalDate.parse(intent.getStringExtra("fecha"));
        hora = intent.getStringExtra("hora");
        descripcion = intent.getStringExtra("descripcion");
        fechaComunicacion = LocalDate.parse(intent.getStringExtra("fecha_Comunicacion"));
        viaComunicacion = intent.getStringExtra("via_Comunicacion");
        tipoParte = intent.getStringExtra("tipo_Parte");
        caducado = intent.getIntExtra("caducado", 0);

        globalParte= new Parte(codParte, codUsuario, new Alumno( matriculaAlumno, nombre, apellidos, grupo),
                new Incidencia(incidencia, nombreInci, puntosInci, descripcionInci), materia, fecha,
                hora, descripcion, fechaComunicacion, viaComunicacion, tipoParte, caducado);
    }

    private void rellenarDatos(){
        partDescripcion.setText(globalParte.getDescripcion());


        if (entrevista.getText().toString().equals(globalParte.getViaComunicacion()))
            entrevista.setChecked(true);
        else if (llamada.getText().toString().equals(globalParte.getViaComunicacion()))
            llamada.setChecked(true);
        else if (mensaje.getText().toString().equals(globalParte.getViaComunicacion()))
            mensaje.setChecked(true);
        else if (notificacion.getText().toString().equals(globalParte.getViaComunicacion()))
            notificacion.setChecked(true);



        calendarView.setDate(globalParte.getFechaComunicacion().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(), true, true);

    }


    private void modificar(){
        if (partDescripcion.getText().toString().isEmpty()){
            errorAddPart.setText("anade una descripcion");
        } else {
            int cod_parte= globalParte.getCod_parte();
            int cod_usuario= globalParte.getCod_usuario();
            Alumno matricula= globalAlumno;
            Incidencia incidencia= globalIncidencia;
            int materia= globalAsignatura.getCodAsignatura();
            LocalDate fecha= LocalDate.now();

            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaFormateada = horaActual.format(formatter);
            String descripcion= partDescripcion.getText().toString();
            LocalDate fechaComunicacion= globalFechaComunicacion;
            String viaComunicacion="";
            String tipoParte= globalParte.getTipoParte();
            int caducado= globalParte.isCaducado();

            if (entrevista.isChecked())
                viaComunicacion=entrevista.getText().toString();
            else if (llamada.isChecked())
                viaComunicacion=llamada.getText().toString();
            else if (mensaje.isChecked())
                viaComunicacion=mensaje.getText().toString();
            else if (notificacion.isChecked())
                viaComunicacion=notificacion.getText().toString();

            if (globalAsignaturas.isEmpty())
                materia=0;


            Parte newParte= new Parte(cod_parte, cod_usuario,matricula,incidencia, materia, fecha, horaFormateada, descripcion,
                    fechaComunicacion, viaComunicacion, tipoParte,caducado);




            RequestQueue queue = Volley.newRequestQueue(this);
            String url;


            url = WebService.RAIZ + WebService.modifyPart + "?"
                    + "cod_parte=" + newParte.getCod_parte()
                    + "&cod_usuario=" + newParte.getCod_usuario()
                    + "&matricula_Alumno=" + newParte.getMatriculaAlumno().getMatricula()
                    + "&incidencia=" + newParte.getIncidencia().getCodigo()
                    + "&materia=" + newParte.getMateria()
                    + "&fecha=" + newParte.getFecha()
                    + "&hora=" + newParte.getHora()
                    + "&descripcion=" + newParte.getDescripcion()
                    + "&fecha_Comunicacion=" + newParte.getFechaComunicacion()
                    + "&via_Comunicacion=" + newParte.getViaComunicacion()
                    + "&tipo_Parte=" + newParte.getTipoParte()
                    + "&caducado=" + newParte.isCaducado();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int respuesta= Integer.parseInt(response);
                            if (respuesta==0){
                                Toast.makeText(ModifyPartActivity.this, "No se ha podido modificar porque no existe el parte ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                            } else if (respuesta==1){
                                Toast.makeText(ModifyPartActivity.this, "El parte con codigo "+newParte.getCod_parte()+" Ha sido modificado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ModifyPartActivity.this, "Algo ha fallado durante la modificación, no preguntes el qué ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_LONG).show();
                            }
                            Intent intent = new Intent(ModifyPartActivity.this, PartsActivity.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModifyPartActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);

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
