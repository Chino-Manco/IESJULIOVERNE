package com.ies.bargas.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.AddPartsActivity;
import com.ies.bargas.adapters.ParteAdapter;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.model.Incidencia;
import com.ies.bargas.model.Parte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PartesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton floatAction;
    private List<Parte> globalPartes= new ArrayList<Parte>();
    private ListView listViewPartes;
    private ParteAdapter adapter;
    private Context context;

    public PartesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alumnos.
     */
    // TODO: Rename and change types and number of parameters
    public static PartesFragment newInstance(String param1, String param2) {
        PartesFragment fragment = new PartesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partes, container, false);

        floatAction= view.findViewById(R.id.floatingAddParts);
        listViewPartes= view.findViewById(R.id.listViewPartes);
        context=requireContext();

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = WebService.RAIZ + WebService.findAllParts;

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
                    adapter = new ParteAdapter(context, globalPartes);
                    // Establecer el adaptador en el ListView
                    listViewPartes.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(context, "ERROR: No se encontro ningún parte", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsonArrayRequest);


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddPartsActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
