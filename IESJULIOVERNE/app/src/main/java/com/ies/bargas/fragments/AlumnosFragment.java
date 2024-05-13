package com.ies.bargas.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.AddAlumnosActivity;
import com.ies.bargas.activities.parts.AddPartsActivity;
import com.ies.bargas.activities.parts.FloatingFragment;
import com.ies.bargas.activities.parts.PartsActivity;
import com.ies.bargas.adapters.AlumnoAdapter;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AlumnosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton floatAction;
    private ListView listViewAlumnos;
    private List<Alumno> globalAlumnos=new ArrayList<Alumno>();



    public AlumnosFragment() {
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
    public static AlumnosFragment newInstance(String param1, String param2) {
        AlumnosFragment fragment = new AlumnosFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        listViewAlumnos = view.findViewById(R.id.listViewAlumnos);
        floatAction = view.findViewById(R.id.floatingAddAlumnos);


        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = WebService.RAIZ + WebService.SelectAlumnos;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        try {
                            if(response.length()>0) {
                                for (int i = 0; i < response.length(); i++) {

                                    jsonObject = response.getJSONObject(i);

                                    String matricula = jsonObject.getString("matricula");
                                    String nombre = jsonObject.getString("nombre");
                                    String apellidos = jsonObject.getString("apellidos");
                                    String grupo = jsonObject.getString("grupo");

                                    globalAlumnos.add(new Alumno(matricula,nombre,apellidos,grupo));

                                }

                            }

                            // Crear un adaptador personalizado para el ListView
                            AlumnoAdapter adapter = new AlumnoAdapter(requireContext(), globalAlumnos);

                            // Establecer el adaptador en el ListView
                            listViewAlumnos.setAdapter(adapter);


                        } catch (JSONException e) {
                            Toast.makeText(requireContext(), "ERROR: No se encontro ningún alumno", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(requireContext(), AddAlumnosActivity.class);
                startActivity(intent);
            }
        });

        // Devolver la vista inflada
        return view;
    }

}
