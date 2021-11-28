package com.example.turismo.fragmentos.perfil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turismo.InfoGrupoActivity;
import com.example.turismo.R;
import com.example.turismo.fragmentos.grupos.Grupo;


import java.util.ArrayList;

public class GrupoPerfilAdapter extends RecyclerView.Adapter<GrupoPerfilAdapter.ViewHolder>{

    private ArrayList<Grupo> grupoList;
    private Context context;
    private String idusuario;

    public GrupoPerfilAdapter(ArrayList<Grupo> grupoList, Context context) {
        this.grupoList = grupoList;
        this.context = context;
    }

    public void traeridusuario(String idusuario){
        this.idusuario=idusuario;
    }


    public void agregarGrupo(Grupo grupo){
        grupoList.add(grupo);
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public GrupoPerfilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infla el recycler
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo_perfil,null,false);
        //retornamos la view que acabamos de crear
        return new GrupoPerfilAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GrupoPerfilAdapter.ViewHolder holder, int position) {
        //vamos a manipular nuestros componentes
        holder.id.setText(String.valueOf(grupoList.get(position).getId()));
        holder.sitio.setText(grupoList.get(position).getSitio());
        holder.mes_estimado.setText(grupoList.get(position).getMes_estimado());
        holder.cant_min.setText(String.valueOf(grupoList.get(position).getCant_min()));
        holder.cant_max.setText(String.valueOf(grupoList.get(position).getCant_max()));
        holder.cantidad.setText(String.valueOf(grupoList.get(position).getCantidad()));
        holder.estado.setText(grupoList.get(position).getEstado());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(context, InfoGrupoActivity.class);
                i.putExtra("idgrupo",holder.id.getText());
                i.putExtra("idusuario", idusuario);
                i.putExtra("origen", "perfil");
                context.startActivity(i);

                notifyDataSetChanged();
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id, sitio, mes_estimado, cant_min, cant_max, cantidad, estado;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            //Enlazar los elementos a la tarjeta grupo
            id = (TextView) itemView.findViewById(R.id.tv_id_grupo_perfil);
            sitio = (TextView) itemView.findViewById(R.id.tv_sitio_grupo_perfil);
            mes_estimado = (TextView) itemView.findViewById(R.id.tv_mes_grupo_perfil);
            cant_min = (TextView) itemView.findViewById(R.id.tv_min_grupo_perfil);
            cant_max = (TextView) itemView.findViewById(R.id.tv_max_grupo_perfil);
            cantidad = (TextView) itemView.findViewById(R.id.tv_cant_grupo_perfil);
            estado = (TextView) itemView.findViewById(R.id.tv_estado_grupo_perfil);

            cardView = (CardView) itemView.findViewById(R.id.cv_grupo_perfil);


        }
    }

    @Override
    public int getItemCount() {
        //tamaño de nuestra lista
        return grupoList.size();
    }
}
