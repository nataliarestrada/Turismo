package com.example.turismo.fragmentos.grupos;

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


import java.util.ArrayList;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.ViewHolder> {

    private ArrayList<Grupo> grupoList;
    private Context context;
    private String idusuario;

    //int posmarcada=0;

    public GrupoAdapter(ArrayList<Grupo> grupoList, Context context) {
        this.grupoList = grupoList;
        this.context = context;
        //this.idusuario=idusuario;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infla el recycler
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo,null,false);
        //retornamos la view que acabamos de crear
        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id, sitio, region, genero, mes_estimado, origen, cant_min, cant_max, cantidad, descripcion, estado;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            //Enlazar los elementos a la tarjeta grupo
            id = (TextView) itemView.findViewById(R.id.tv_id_grupo);
            sitio = (TextView) itemView.findViewById(R.id.tv_sitio_grupo);
            region = (TextView) itemView.findViewById(R.id.tv_region_grupo);
            genero = (TextView) itemView.findViewById(R.id.tv_genero_grupo);
            mes_estimado = (TextView) itemView.findViewById(R.id.tv_mes_grupo);
            origen = (TextView) itemView.findViewById(R.id.tv_origen_grupo);
            cant_min = (TextView) itemView.findViewById(R.id.tv_min_grupo);
            cant_max = (TextView) itemView.findViewById(R.id.tv_max_grupo);
            cantidad = (TextView) itemView.findViewById(R.id.tv_cant_grupo);
            descripcion = (TextView) itemView.findViewById(R.id.tv_descripcion_grupo);
            estado = (TextView) itemView.findViewById(R.id.tv_estado_grupo);

            cardView = (CardView) itemView.findViewById(R.id.cv_grupo);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //vamos a manipular nuestros componentes
        holder.id.setText(String.valueOf(grupoList.get(position).getId()));
        holder.sitio.setText(grupoList.get(position).getSitio());
        holder.region.setText(grupoList.get(position).getRegion());
        holder.genero.setText(grupoList.get(position).getGenero());
        holder.mes_estimado.setText(grupoList.get(position).getMes_estimado());
        holder.origen.setText(grupoList.get(position).getOrigen());
        holder.cant_min.setText(String.valueOf(grupoList.get(position).getCant_min()));
        holder.cant_max.setText(String.valueOf(grupoList.get(position).getCant_max()));
        holder.cantidad.setText(String.valueOf(grupoList.get(position).getCantidad()));
        holder.descripcion.setText(grupoList.get(position).getDescripcion());
        holder.estado.setText(grupoList.get(position).getEstado());

        //final int pos = position;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("ingreso al card");
                //posmarcada=pos;
                Intent i;
                i = new Intent(context, InfoGrupoActivity.class);
                i.putExtra("idgrupo",holder.id.getText());
                i.putExtra("idusuario", idusuario);
                i.putExtra("origen", "grupos");
                context.startActivity(i);

                notifyDataSetChanged();
            }
        });

/*        if (posmarcada==position){
            holder.descripcion.setText("holaaa");
        } else {
            holder.descripcion.setText("hola 1");
        }*/

    }

    @Override
    public int getItemCount() {
        //tamaño de nuestra lista
        return grupoList.size();
    }


}
