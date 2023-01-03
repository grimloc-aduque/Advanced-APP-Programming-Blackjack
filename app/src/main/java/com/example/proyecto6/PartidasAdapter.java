package com.example.proyecto6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto6.util.Partida;

import java.util.List;

public class PartidasAdapter extends RecyclerView.Adapter<PartidasAdapter.ViewHolder>{

    List<Partida> partidas;

    public PartidasAdapter(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCartasDealer;
        TextView textCartasPlayer;
        TextView textGanador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textCartasDealer = (TextView) itemView.findViewById(R.id.textCartasDealer);
            this.textCartasPlayer = (TextView) itemView.findViewById(R.id.textCartasPlayer);
            this.textGanador = (TextView) itemView.findViewById(R.id.textGanador);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partida partida = partidas.get((partidas.size()-1) - position);
        holder.textCartasDealer.setText("Cartas Dealer: " + partida.getManoDealer().getCartasFormateadas());
        holder.textCartasPlayer.setText("Cartas Player: " + partida.getManoPlayer().getCartasFormateadas());
        holder.textGanador.setText("Ganador: " + partida.getGanador());
    }

    @Override
    public int getItemCount() {
        return this.partidas.size();
    }
}
