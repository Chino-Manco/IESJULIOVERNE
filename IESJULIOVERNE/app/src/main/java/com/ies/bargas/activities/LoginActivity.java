package com.ies.bargas.activities;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Departamento;
import com.ies.bargas.model.User;
import com.ies.bargas.R;
import com.ies.bargas.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private TextView textViewRecover;

    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewRecover = findViewById(R.id.textViewRecover);

        //se auto-rellenan el email y contraseña en caso de haberse guardado
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a RegistrarActivity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        textViewRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a RegistrarActivity
                Intent intent = new Intent(LoginActivity.this, RecoverActivity.class);
                startActivity(intent);            }
        });
    }

    private void login() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        //recuperar datos
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = WebService.RAIZ + WebService.LOGIN + "?email=" + email + "&password=" + password;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                try {
                    if(response.length()>0) {
                        jsonObject = response.getJSONObject(0);
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
                            if (validar.equals("si")) {

                            User user = new User(cod_usuario, email, clave, nombre, apellidos, dni, cod_delphos, validar, departamento, tutor_grupo, rol);
                            saveOnPreferences(user);
                            Toast.makeText(getApplicationContext(), "Usuario seleccionado " + nombre, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario no validado", Toast.LENGTH_LONG).show();

                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();

                    }
                    } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se encontro el usuario", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });
        queue.add(jsonArrayRequest);

    }

    //método que fija el email y contraseña que se hayan guardado
    private void setCredentialsIfExist() {
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserClavePrefs(prefs);
        if (!email.isEmpty() && !password.isEmpty()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    //método que guarda el email y contraseña introducidos
    private void saveOnPreferences(User user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", user.getEmail());
        editor.putString("clave", user.getClave());
        editor.putInt("cod_usuario", user.getCod_usuario());
        editor.putString("dni", user.getDni());
        editor.putString("nombre", user.getNombre());
        editor.putString("apellidos", user.getApellidos());
        editor.putInt("cod_delphos", user.getCod_delphos());
        editor.putString("rol", user.getRol());
        editor.putInt("departamento_codigo", user.getDepartamento().getCodigo());
        editor.putString("departamento_nombre", user.getDepartamento().getNombre());
        editor.putString("validar", user.getValidar());
        editor.putString("tutor_grupo", user.getTutor_grupo());
        editor.apply();


    }
}

        // verificar las credenciales del usuario
        // Si el usuario es válido, proceder con el inicio de sesión
