package com.example.turismo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismo.fragmentos.grupos.GrupoAdapter;

import java.util.ArrayList;

public class IntegrantesAdapter extends RecyclerView.Adapter<IntegrantesAdapter.ViewHolder>{

    private ArrayList<Integrantes> listaintegrantes;


    public IntegrantesAdapter(ArrayList<Integrantes> listaintegrantes) {
        this.listaintegrantes = listaintegrantes;

    }

    public void agregarIntegrante(Integrantes integrantes){
        listaintegrantes.add(integrantes);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IntegrantesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infla el recycler
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integrante,null,false);
        //retornamos la view que acabamos de crear
        return new IntegrantesAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView alias, edad, email, telefono;
        //private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Enlazar los elementos a la tarjeta integrante
            alias = (TextView) itemView.findViewById(R.id.textView_alias_integrante);
            edad = (TextView) itemView.findViewById(R.id.textView_edad_integrante);
            email = (TextView) itemView.findViewById(R.id.textView_email_integrante);
            telefono = (TextView) itemView.findViewById(R.id.textView_telefono_integrante);

            //cardView = (CardView) itemView.findViewById(R.id.cv_grupo);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull IntegrantesAdapter.ViewHolder holder, int position) {
        //vamos a manipular nuestros componentes
        holder.alias.setText(listaintegrantes.get(position).getAlias());
        holder.edad.setText(listaintegrantes.get(position).getEdad());
        holder.email.setText(listaintegrantes.get(position).getEmail());
        holder.telefono.setText(listaintegrantes.get(position).getTelefono());

    }

    @Override
    public int getItemCount() {
        //tamaño de nuestra lista
        return listaintegrantes.size();
    }
}
