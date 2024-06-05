package com.ies.bargas.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.ies.bargas.R;
import com.ies.bargas.model.Parte;
import java.util.ArrayList;
import java.util.List;
import android.widget.CompoundButton;


public class ParteExpulsionAdapter extends BaseAdapter {

    private Context context;
    private List<Parte> partes;
    private SparseBooleanArray selectedItems;

    public ParteExpulsionAdapter(Context context, List<Parte> partes) {
        this.context = context;
        this.partes = partes;
        this.selectedItems= new SparseBooleanArray();
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.part_list_expulsion, parent, false);
        }

        Parte parte = partes.get(position);

        CheckBox checkBox = listItemView.findViewById(R.id.checkboxPart);
        TextView puntos = listItemView.findViewById(R.id.textViewPuntos);
        TextView descripcion = listItemView.findViewById(R.id.textViewDescripcion);


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });


        checkBox.setChecked(selectedItems.get(position, false));

        // Agregar un escuchador de eventos al checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Actualizar el estado del elemento seleccionado en el mapa
                selectedItems.put(position, isChecked);
            }
        });



        puntos.setText("Puntos: " + parte.getIncidencia().getPuntos());
        descripcion.setText("Incidencia: " + parte.getIncidencia().getDescripcion());

        return listItemView;
    }



    // Método para obtener los partes con el checkbox marcado
    public List<Parte> getPartesMarcadas() {
        List<Parte> parts = new ArrayList<>();
        for (int i = 0; i < partes.size(); i++) {
            if (selectedItems.get(i, false)) { // Verificar si el elemento está marcado
                parts.add(partes.get(i));
            }
        }


        return parts;
    }


    // Método para obtener los puntos de las partes con el checkbox marcado
    public int getPuntosPartesMarcadas() {
        List<Integer> puntos = new ArrayList<>();
        for (int i = 0; i < partes.size(); i++) {
            if (selectedItems.get(i, false)) { // Verificar si el elemento está marcado
                puntos.add(partes.get(i).getIncidencia().getPuntos());
            }
        }

        int n=0;
        for (Integer p: puntos){
            n+=p;
        }

        return n;
    }

}
