package com.ies.bargas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ies.bargas.R;
import com.ies.bargas.activities.parts.ModifyExpulsionActivity;
import com.ies.bargas.model.Expulsion;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpulsionAdapter extends BaseAdapter {

    private Context context;
    private List<Expulsion> expulsiones;

    public ExpulsionAdapter(Context context, List<Expulsion> expulsiones) {
        this.context = context;
        this.expulsiones = expulsiones;
    }

    @Override
    public int getCount() {
        return expulsiones.size();
    }

    @Override
    public Object getItem(int position) {
        return expulsiones.get(position);
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

        Expulsion expulsion = expulsiones.get(position);

        TextView nombre = listItemView.findViewById(R.id.nombre_Alumno);
        TextView apellidos = listItemView.findViewById(R.id.apellidos_Alumno);
        TextView fecha = listItemView.findViewById(R.id.puntos_Incidencia);
        TextView hora = listItemView.findViewById(R.id.descripcion_incidencia);


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ModifyExpulsionActivity.class);
                intent.putExtra("cod_expulsion", expulsion.getCod_expulsion());
                intent.putExtra("cod_usuario", expulsion.getCod_usuario());
                intent.putExtra("fecha_Inicio", expulsion.getFecha_Inicio());
                intent.putExtra("Fecha_Fin", expulsion.getFecha_Fin());
                intent.putExtra("tipo_expulsion", expulsion.getTipo_expulsion());
                intent.putExtra("fecha_Insercion", expulsion.getFecha_Insercion().toString());

                intent.putExtra("matricula_Alumno", expulsion.getMatricula_del_Alumno().getMatricula());
                intent.putExtra("grupo_Alumno", expulsion.getMatricula_del_Alumno().getGrupo());
                intent.putExtra("apellidos_Alumno", expulsion.getMatricula_del_Alumno().getApellidos());
                intent.putExtra("nombre_Alumno", expulsion.getMatricula_del_Alumno().getNombre());

                context.startActivity(intent);
            }

        });




        nombre.setText("Nombre: " + expulsion.getMatricula_del_Alumno().getNombre());
        apellidos.setText("Apellidos: " + expulsion.getMatricula_del_Alumno().getApellidos());

        Timestamp timestamp = expulsion.getFecha_Insercion();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        // Obtener la fecha y la hora en formato String
        String fechaString = dateFormat.format(timestamp);
        String horaString = timeFormat.format(timestamp);

        fecha.setText("Fecha: " + fechaString);
        hora.setText("Hora: " + horaString);

        return listItemView;
    }


}
