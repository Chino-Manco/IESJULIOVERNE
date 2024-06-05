package com.ies.bargas.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.AddPartsActivity;
import com.ies.bargas.adapters.ExpulsionAdapter;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;
import com.ies.bargas.model.Expulsion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ExpulsionesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Expulsion> globalExpulsiones= new ArrayList<Expulsion>();
    private ListView listViewExpulsiones;
    private ExpulsionAdapter adapter;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpulsionesFragment() {
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
    public static ExpulsionesFragment newInstance(String param1, String param2) {
        ExpulsionesFragment fragment = new ExpulsionesFragment();
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
        View view = inflater.inflate(R.layout.fragment_expulsiones, container, false);


        listViewExpulsiones= view.findViewById(R.id.listViewExpulsiones);
        context= requireContext();


        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = WebService.RAIZ + WebService.findAllExpulsiones;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {

                    if(response.length()>0) {

                        for (int i = 0; i < response.length(); i++) {

                            jsonObject = response.getJSONObject(i);

                            Expulsion expulsion= new Expulsion();
                            // Extraer atributos del objeto JSON para la tabla expulsiones
                            int codExpulsion = jsonObject.getInt("cod_expulsion");
                            int codUsuario = jsonObject.getInt("cod_usuario");

                            String fechaI = jsonObject.getString("fecha_Inicio");
                            String fechaF = jsonObject.getString("Fecha_Fin");

                            String tipoExpulsion = jsonObject.getString("tipo_expulsion");
                            Timestamp fechaInsercion = Timestamp.valueOf(jsonObject.getString("fecha_Insercion"));

                            //Atributos de alumno
                            String matricula = jsonObject.getString("matricula");
                            String nombre = jsonObject.getString("nombre");
                            String apellidos = jsonObject.getString("apellidos");
                            String grupo = jsonObject.getString("grupo");

                            if (!fechaF.equals("null") && fechaI != null) {
                                LocalDate fechaInicio = LocalDate.parse(jsonObject.getString("fecha_Inicio"));
                                LocalDate fechaFin = LocalDate.parse(jsonObject.getString("Fecha_Fin"));

                                expulsion = new Expulsion(codExpulsion, codUsuario, new Alumno(matricula, nombre, apellidos, grupo),
                                        fechaInicio, fechaFin, tipoExpulsion, fechaInsercion);
                            } else {
                                expulsion = new Expulsion(codExpulsion, codUsuario,
                                        new Alumno(matricula, nombre, apellidos, grupo),tipoExpulsion, fechaInsercion);
                            }



                            globalExpulsiones.add(expulsion);

                        }
                    }
                    adapter = new ExpulsionAdapter(context, globalExpulsiones);
                    // Establecer el adaptador en el ListView
                    listViewExpulsiones.setAdapter(adapter);


                } catch (JSONException e) {
                    Toast.makeText(context, "ERROR: No se encontro ninguna expulsión", Toast.LENGTH_LONG).show();
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

        // Inflate the layout for this fragment
        return view;
    }
}
