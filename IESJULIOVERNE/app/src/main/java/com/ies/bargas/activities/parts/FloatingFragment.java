package com.ies.bargas.activities.parts;

import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ies.bargas.R;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.model.Expulsion;

import java.sql.Timestamp;
import java.util.Date;

public class FloatingFragment extends DialogFragment {

    private static Alumno globalAlumno;
    private static int globalCod_usuario;
    private TextView mensaje;
    private Button botonExpulsion;
    private Button botonTrabajo;

    // Método estático para crear una nueva instancia del fragmento
    public static FloatingFragment newInstance(Alumno alumno, int codigo) {
        globalAlumno =alumno;
        globalCod_usuario=codigo;
        return new FloatingFragment();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Crea un diálogo sin permitir que se cancele al tocar fuera de él
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño de la pantalla flotante
        View view = inflater.inflate(R.layout.fragment_floating_parts, container, false);



        mensaje= view.findViewById(R.id.mensajeFlotante);
        botonExpulsion= view.findViewById(R.id.botonExpulsion);
        botonTrabajo = view.findViewById(R.id.botonTrabajo);


        mensaje.setText(globalAlumno.getNombre()+" "+globalAlumno.getApellidos()+ "\n" +
                "Ya ha acumalado 10 puntos por lo que va a ser expulsador," +
                " por favor elija el método de expulsión que crea conveniente");

        botonTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpulsion(botonTrabajo.getText().toString());
            }
        });

        botonExpulsion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpulsion(botonExpulsion.getText().toString());
            }
        });



        return view;
    }

    private void addExpulsion(String tipo){
        //se auto-rellenan el email y contraseña en caso de haberse guardado
        int cod_usuario= globalCod_usuario;
        Alumno matricula= globalAlumno;
        Timestamp timestamp= new Timestamp((new Date()).getTime());

        Expulsion expulsion = new Expulsion(cod_usuario, matricula, tipo, timestamp);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = WebService.RAIZ + WebService.InsertExpulsiones + "?"
                + "cod_usuario=" + expulsion.getCod_usuario()
                + "&matricula_del_Alumno=" + expulsion.getMatricula_del_Alumno().getMatricula()
                + "&tipo_expulsion=" + expulsion.getTipo_expulsion()
                + "&fecha_Insercion=" + expulsion.getFecha_Insercion();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), globalAlumno.getNombre()+" Ha sido expulsado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), PartsActivity.class);
                        startActivity(intent);
                        dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);
    }
}
