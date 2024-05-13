package com.ies.bargas.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ies.bargas.activities.shifts.AddShiftsActivity;
import com.ies.bargas.controllers.WebService;

import com.ies.bargas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuardiasTotalFragment extends Fragment {

    private ListView listViewShifts;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> guardiasList;
    private String obtenerIdDeGuardia(String guardia) {
        return guardia.split(" ")[0];
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guardias_total, container, false);
        listViewShifts = view.findViewById(R.id.listViewShifts);
        guardiasList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, guardiasList);
        listViewShifts.setAdapter(adapter);
        registerForContextMenu(listViewShifts);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Inflamos el context menu con nuestro layout
        getActivity().getMenuInflater().inflate(R.menu.context_menu_shifts, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Obtener info en el context menu del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String guardiaSeleccionada = guardiasList.get(info.position);
        if (item.getItemId() == R.id.edit) {
            // Ir a la actividad de agregar/editar guardia
            Intent intent = new Intent(getActivity(), AddShiftsActivity.class);
            intent.putExtra("guardia", guardiaSeleccionada);
            startActivity(intent);
        } else if (item.getItemId() == R.id.delete) {
            // Llamar al archivo PHP para eliminar la guardia
            String url = WebService.RAIZ + WebService.Delete;
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        // Eliminar la guardia de la lista y actualizar el ListView
                        guardiasList.remove(info.position);
                        adapter.notifyDataSetChanged();
                    },
                    error -> {
                        // Manejar errores
                        error.printStackTrace();
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", obtenerIdDeGuardia(guardiaSeleccionada));
                    return params;
                }
            };
            Volley.newRequestQueue(getActivity()).add(request);
        }
        return super.onContextItemSelected(item);
    }

    // Método para cargar datos en el ListView
    private void cargarDatos() {
        String url = WebService.RAIZ + WebService.ObtenerGuardias;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Convertir la respuesta en un JSONArray
                        JSONArray jsonArray = new JSONArray(response);

                        // Limpiar la lista de guardias
                        guardiasList.clear();

                        // Recorrer el JSONArray y añadir cada guardia a la lista
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String guardia = jsonObject.getString("fecha") + " " +
                                    jsonObject.getString("periodo") + " " +
                                    jsonObject.getString("clase") + " " +
                                    jsonObject.getString("profesor") + " " +
                                    jsonObject.getString("observaciones");
                            guardiasList.add(guardia);
                        }

                        // Notificar al adaptador que los datos han cambiado
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Manejar errores
                    error.printStackTrace();
                });
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
