package com.ies.bargas.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.ModifyAlumnoActivity;
import com.ies.bargas.activities.parts.PartsActivity;
import com.ies.bargas.controllers.WebService;
import com.ies.bargas.model.Alumno;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {

    private Context context;
    private List<Alumno> alumnos;

    public AlumnoAdapter(@NonNull Context context, List<Alumno> alumnos) {
        super(context, 0, alumnos);
        this.context = context;
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.alumno_list, parent, false);
        }

        Alumno currentAlumno = alumnos.get(position);

        TextView matricula = listItemView.findViewById(R.id.matricula);
        TextView nombre = listItemView.findViewById(R.id.nombre);
        TextView apellidos = listItemView.findViewById(R.id.apellidos);
        TextView grupo = listItemView.findViewById(R.id.grupo);
        matricula.setText(currentAlumno.getMatricula());
        nombre.setText(currentAlumno.getNombre());
        apellidos.setText(currentAlumno.getApellidos());
        grupo.setText(currentAlumno.getGrupo());

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModifyAlumnoActivity.class);
                intent.putExtra("matricula", currentAlumno.getMatricula());
                intent.putExtra("nombre", currentAlumno.getNombre());
                intent.putExtra("apellidos", currentAlumno.getApellidos());
                intent.putExtra("grupo", currentAlumno.getGrupo());
                context.startActivity(intent);
            }
        });

        listItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Crear un objeto PopupMenu
                PopupMenu popupMenu = new PopupMenu(context, v);
                // Inflar el archivo de menú XML
                popupMenu.getMenuInflater().inflate(R.menu.menu_emergente_alumnos, popupMenu.getMenu());

                MenuItem titulo= popupMenu.getMenu().findItem(R.id.titulo);
                titulo.setTitle(currentAlumno.getNombre());
                titulo.setEnabled(false);

                // Configurar un listener para manejar la selección de elementos del menú
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.eliminar) {


                            RequestQueue queue = Volley.newRequestQueue(context);
                            String url = WebService.RAIZ + WebService.DeleteAlumno + "?"
                                    + "matricula=" + currentAlumno.getMatricula();

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            int respuesta= Integer.parseInt(response);
                                            if (respuesta==0){
                                                Toast.makeText(context, "No se ha podido eliminar porque no existe el usuario ¯\\_( ͡° ͜ʖ ͡°)_/¯", Toast.LENGTH_SHORT).show();
                                            } else if (respuesta==1){
                                                Toast.makeText(context, currentAlumno.getNombre()+" Ha sido eliminado correctamente", Toast.LENGTH_SHORT).show();
                                                alumnos.remove(currentAlumno);
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
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
        return listItemView;
    }
}
