package com.example.proyecto6.util;

import java.util.ArrayList;
import java.util.List;

public class Historico {

    private static Historico historico = new Historico();
    private List<Partida> partidas;

    private Historico(){
        this.partidas = new ArrayList<>();
    }

    public static Historico getHistorico(){
        return Historico.historico;
    }

    public List<Partida> getPartidas(){
        return this.partidas;
    }

    public void setPartidas(List<Partida> partidas){
        this.partidas = partidas;
    }

    public void addPartida(Partida partida){
        this.partidas.add(partida);
    }

}
