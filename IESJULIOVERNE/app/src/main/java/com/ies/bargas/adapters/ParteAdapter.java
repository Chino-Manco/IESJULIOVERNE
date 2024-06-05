package com.ies.bargas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.ModifyPartActivity;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Parte;
import com.ies.bargas.model.Parte;

import java.util.List;

public class ParteAdapter extends BaseAdapter {

    private Context context;
    private List<Parte> partes;

    public ParteAdapter(Context context, List<Parte> partes) {
        this.context = context;
        this.partes = partes;
    }

    @Override
    public int getCount() {
        return partes.size();
    }

    @Override
    public Object getItem(int position) {
        return partes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.part_list, parent, false);
        }

        Parte parte = partes.get(position);

        TextView nombre = listItemView.findViewById(R.id.nombre_Alumno);
        TextView apellidos = listItemView.findViewById(R.id.apellidos_Alumno);
        TextView puntos = listItemView.findViewById(R.id.puntos_Incidencia);
        TextView descripcion = listItemView.findViewById(R.id.descripcion_incidencia);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModifyPartActivity.class);
                intent.putExtra("cod_parte", parte.getCod_parte());
                intent.putExtra("cod_usuario", parte.getCod_usuario());

                intent.putExtra("matricula_Alumno", parte.getMatriculaAlumno().getMatricula());
                intent.putExtra("grupo_Alumno", parte.getMatriculaAlumno().getGrupo());
                intent.putExtra("apellidos_Alumno", parte.getMatriculaAlumno().getApellidos());
                intent.putExtra("nombre_Alumno", parte.getMatriculaAlumno().getNombre());

                intent.putExtra("incidencia", parte.getIncidencia().getCodigo());
                intent.putExtra("descripcion_incidencia", parte.getIncidencia().getDescripcion());
                intent.putExtra("nombre_incidencia", parte.getIncidencia().getNombre());
                intent.putExtra("puntos_incidencia", parte.getIncidencia().getPuntos());

                intent.putExtra("materia", parte.getMateria());
                intent.putExtra("fecha", parte.getFecha().toString());
                intent.putExtra("hora", parte.getHora());
                intent.putExtra("descripcion", parte.getDescripcion());
                intent.putExtra("fecha_Comunicacion", parte.getFechaComunicacion().toString());
                intent.putExtra("via_Comunicacion", parte.getViaComunicacion());
                intent.putExtra("tipo_Parte", parte.getTipoParte());
                intent.putExtra("caducado", parte.isCaducado());
                context.startActivity(intent);
            }
        });


        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Crear un objeto PopupMenu
                PopupMenu popupMenu = new PopupMenu(context, v);
                // Inflar el archivo de menú XML
                popupMenu.getMenuInflater().inflate(R.menu.menu_emergente_partes, popupMenu.getMenu());

                MenuItem titulo= popupMenu.getMenu().findItem(R.id.titulo);
                MenuItem caducar= popupMenu.getMenu().findItem(R.id.caducar);
                titulo.setTitle("Codigo: "+parte.getCod_parte());
                titulo.setEnabled(false);
                int caducidad=0;
                if (parte.isCaducado()==0){
                    caducar.setTitle("Caducar");
                    caducidad=1;
                } else if (parte.isCaducado()==1){
                    caducar.setTitle("Rehabilitar");
                } else {
                    caducar.setTitle("Usado");
                    caducar.setEnabled(false);
                }

                // Configurar un listener para manejar la selección de elementos del menú
                final int finalCaducidad = caducidad;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.eliminar) {


                            RequestQueue queue = Volley.newRequestQueue(context);
                            String url = WebService.RAIZ + WebService.deleteParte + "?"
                                    + "cod_parte=" + parte.getCod_parte();

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            int respuesta= Integer.parseInt(response);
                                            if (respuesta==0){
                                                Toast.makeText(context, "No se ha podido eliminar porque no existe el parte ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                                            } else if (respuesta==1){
                                                Toast.makeText(context, "El parte "+ parte.getCod_parte()+" Ha sido eliminado correctamente", Toast.LENGTH_SHORT).show();
                                                partes.remove(parte);
                                                notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(context, "Algo ha fallado durante la eliminación, no preguntes el qué ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            queue.add(stringRequest);


                            return true;
                        }
                        else if (item.getItemId() == R.id.caducar){
                            RequestQueue queue = Volley.newRequestQueue(context);
                            String url = WebService.RAIZ + WebService.caducarParte + "?"
                                    + "cod_parte=" + parte.getCod_parte()
                                    + "&caducado=" + finalCaducidad;

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            int respuesta= Integer.parseInt(response);
                                            if (respuesta==0){
                                                Toast.makeText(context, "No se ha podido modificar la caducidad porque no existe el parte ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                                            } else if (respuesta==1){
                                                Toast.makeText(context, "La caducidad del parte "+ parte.getCod_parte()+" Ha sido modificado correctamente", Toast.LENGTH_SHORT).show();
                                                partes.get(partes.indexOf(parte)).setCaducado(finalCaducidad);
                                                notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(context, "Algo ha fallado durante la modificacion de la caducidad, no preguntes el qué ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            queue.add(stringRequest);


                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        nombre.setText("Nombre: " + parte.getMatriculaAlumno().getNombre());
        apellidos.setText("Apellidos: " + parte.getMatriculaAlumno().getApellidos());
        puntos.setText("Puntos: " + parte.getIncidencia().getPuntos());
        descripcion.setText("Incidencia: " + parte.getIncidencia().getDescripcion());

        return listItemView;
    }


}
