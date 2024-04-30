package com.ies.bargas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Departamento;
import com.ies.bargas.model.User;
import com.ies.bargas.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private EditText editTextDNI;
    private EditText editTextName;
    private EditText editTextSurnames;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCodDelphos;
    private Spinner spinnerDto;
    private Spinner spinnerTutor;
    private SharedPreferences prefs;
    private LinearLayout layoutProf;
    private Button backButton;
    private Button saveButton;
    private List<Departamento> dtoGlobal;
    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Inicializa las vistas
        editTextDNI = findViewById(R.id.editTextDNI);
        editTextName = findViewById(R.id.editTextName);
        editTextSurnames = findViewById(R.id.editTextSurnames);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCodDelphos = findViewById(R.id.editTextCodDelphos);
        spinnerDto = findViewById(R.id.spinnerDto);
        spinnerTutor = findViewById(R.id.spinnerTutor);
        backButton = findViewById(R.id.buttonBack);
        saveButton = findViewById(R.id.buttonModify);
        layoutProf = findViewById(R.id.layoutProfesor);
        error = findViewById(R.id.errorModify);

        findAllDepartamentos();
        findAllCursos();

        // cargar los datos del usuario en las vistas
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("IES JULIO VERNE");
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
                    intent = new Intent(UserProfileActivity.this, PartsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_shifts) {
                    // Inicia la Activity para mostrar las guardias
                    intent = new Intent(UserProfileActivity.this, ShiftsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_main) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(UserProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_user) {
                    // Inicia la Activity para editar el perfil de usuario
                    intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    // Cierra la sesión y vuelve a la pantalla de inicio de sesión
                    Util.removeSharedPreferences(prefs);
                    intent = new Intent(UserProfileActivity.this, LoginActivity.class);
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
        // Dependiendo del rol del usuario
        String userRole = Util.getUserRolPrefs(prefs);  // Reemplaza esto con el rol del usuario
        if (userRole.equals("1") ||userRole.equals("0")) {
            layoutProf.setVisibility(View.VISIBLE);
        } else {
            layoutProf.setVisibility(View.GONE);
        }


        editTextDNI.setText(Util.getUserDniPrefs(prefs));
        editTextName.setText(Util.getUserNombrePrefs(prefs));
        editTextSurnames.setText(Util.getUserApellidosPrefs(prefs));
        editTextPassword.setText(Util.getUserClavePrefs(prefs));
        editTextEmail.setText(Util.getUserMailPrefs(prefs));
        editTextCodDelphos.setText(Util.getUsercodDelphosPrefs(prefs)+"");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
            }
        });
    }

    private void findAllDepartamentos() {
        List<Departamento> departamentos = new ArrayList<Departamento>();
        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.Departamentos;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    if(response.length()>0) {
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            int codigo = jsonObject.getInt("codigo");
                            String nombre = jsonObject.getString("nombre");
                            Departamento departamento = new Departamento(codigo, nombre);
                            departamentos.add(departamento);
                        }
                        dtoGlobal = departamentos;
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UserProfileActivity.this,
                                android.R.layout.simple_spinner_item, Departamento.toStringNombre(departamentos));
                        spinnerDto.setAdapter(adapter1);
                        spinnerDto.setSelection(Util.getUserDeparmentoCodigoPrefs(prefs)-1);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningún departamento", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfileActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
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
                    if(response.length()>0) {
                        String[] opciones = new String[response.length()+1];
                        opciones[0]="Ninguno";
                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            String grupo = jsonObject.getString("grupo");
                            opciones[i+1]=grupo;
                            //guardo la posicion para luego acceder a ella y ponerlo por defecto
                            if (Util.getUsertutorPrefs(prefs).equalsIgnoreCase(grupo)) {
                                n = i+1;
                            }
                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(UserProfileActivity.this,
                                android.R.layout.simple_spinner_item, opciones);
                        spinnerTutor.setAdapter(adapter2);
                        spinnerTutor.setSelection(n);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro ningún curso", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserProfileActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);
    }

    private void modify(){
//Obtener datos introducidos por el user
        String dni = editTextDNI.getText().toString();
        String name = editTextName.getText().toString();
        String surnames = editTextSurnames.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String dto = spinnerDto.getSelectedItem().toString();
        Departamento departamento= new Departamento();
        String tutor = spinnerTutor.getSelectedItem().toString();
        String codDelphos = editTextCodDelphos.getText().toString();
        String validar = Util.getUserValidarPrefs(prefs);

        boolean incompleto=false;
        boolean campoComun=(dni.isEmpty() || name.isEmpty() || surnames.isEmpty() || email.isEmpty());
        User newUser= new User();

        if (password.isEmpty()) {
            password = "";
        }

        if (campoComun){
            incompleto=true;
        } else {
            if(Util.getUserRolPrefs(prefs).equals("2")){
                newUser=new User(email, password, name, surnames, dni, 0, validar, null, "", Util.getUserRolPrefs(prefs));
            } else if (Util.getUserRolPrefs(prefs).equals("3")){
                newUser=new User(email, password, name, surnames, dni, 0, validar, null, "", Util.getUserRolPrefs(prefs));
            } else if (Util.getUserRolPrefs(prefs).equals("1") || Util.getUserRolPrefs(prefs).equals("0")){
                if (dto.isEmpty() || tutor.isEmpty() || codDelphos.isEmpty()){
                    incompleto=true;
                } else {
                    //aprovechando el array de Dto del metodo anterior se extrae el codigo
                    for (Departamento d: dtoGlobal){
                        if (d.getNombre().equals(dto))
                            departamento=d;
                    }
                    newUser=new User(email, password, name, surnames, dni, Integer.parseInt(codDelphos), validar, departamento, tutor, Util.getUserRolPrefs(prefs));

                }
            } else {
                incompleto=true;
            }
        }

        if (!incompleto){
            error.setText("");
            String url="";
            RequestQueue queue;
            if (Util.getUserRolPrefs(prefs).equals("1") || Util.getUserRolPrefs(prefs).equals("0")) {
                queue = Volley.newRequestQueue(this);
                url = WebService.RAIZ + WebService.Modify + "?"
                        + "email2=" + newUser.getEmail()
                        + "&clave=" + newUser.getClave()
                        + "&nombre=" + newUser.getNombre()
                        + "&apellidos=" + newUser.getApellidos()
                        + "&dni2=" + newUser.getDni()
                        + "&cod_delphos=" + newUser.getCod_delphos()
                        + "&departamento=" + newUser.getDepartamento().getCodigo()
                        + "&tutor_grupo=" + newUser.getTutor_grupo()
                        + "&rol=" + newUser.getRol()
                        + "&email=" + Util.getUserMailPrefs(prefs)
                        + "&dni=" + Util.getUserDniPrefs(prefs);
            } else {
                queue = Volley.newRequestQueue(this);
                url = WebService.RAIZ + WebService.Modify + "?"
                        + "email2=" + newUser.getEmail()
                        + "&clave=" + newUser.getClave()
                        + "&nombre=" + newUser.getNombre()
                        + "&apellidos=" + newUser.getApellidos()
                        + "&dni2=" + newUser.getDni()
                        + "&rol=" + newUser.getRol()
                        + "&email=" + Util.getUserMailPrefs(prefs)
                        + "&dni=" + Util.getUserDniPrefs(prefs);
            }


            User finalNewUser = newUser;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Aquí manejas la respuesta del servidor
                            int numero = Integer.parseInt(response);
                            if (numero == 0 ) {
                                error.setText("Correo o DNI ya registrado");
                            } else{
                                saveOnPreferences(finalNewUser);
                                Toast.makeText(getApplicationContext(), "El usuario se ha modificado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserProfileActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(stringRequest);

        } else {
            error.setText("Rellena todos los campos");
        }
    }

    private void saveOnPreferences(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", user.getEmail());
        editor.putString("clave", user.getClave());
        editor.putInt("cod_usuario", user.getCod_usuario());
        editor.putString("dni", user.getDni());
        editor.putString("nombre", user.getNombre());
        editor.putString("apellidos", user.getApellidos());
        editor.putInt("cod_delphos", user.getCod_delphos());
        editor.putString ("rol", user.getRol());
        editor.putInt("departamento_codigo", user.getDepartamento().getCodigo());
        editor.putString("departamento_nombre", user.getDepartamento().getNombre());
        editor.putString("validar", user.getValidar());
        editor.putString("tutor_grupo", user.getTutor_grupo());
        editor.apply();


    }
    private void setCredentialsIfExist(TextView navUsername) {
        String nombre = Util.getUserNombrePrefs(prefs);
        String apellidos = Util.getUserApellidosPrefs(prefs);
        if (!nombre.isEmpty() && !apellidos.isEmpty()){
            navUsername.setText(nombre+ " "+apellidos);

        }
    }
}
