package com.example.proyecto6.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mano {
    private List<Carta> cartas;

    public Mano(){
        this.cartas = new ArrayList<>();
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void addCarta(Carta carta){
        this.cartas.add(carta);
    }

    public Integer getPuntaje(){
        Integer puntajeAcc = this.cartas
                .stream()
                .filter(carta -> !carta.getValor().equals("a"))
                .mapToInt(Carta::getPuntaje)
                .sum();

        Long numAces = this.cartas.stream()
                .filter(carta -> carta.getValor().equals("a"))
                .count();

        if(numAces == 0){
            return puntajeAcc;
        }

        if(numAces > 0 && puntajeAcc+11<=21){
            puntajeAcc += 11;
            numAces -= 1;
        }
        while(numAces>0){
            puntajeAcc  += 1;
        }
        return puntajeAcc;
    }

    public Integer getPuntajeSinCartaOculta(){
        Carta cartaOculta = this.cartas.remove(0);
        Integer puntaje = this.getPuntaje();
        this.cartas.add(0, cartaOculta);
        return puntaje;
    }

    public String getCartasFormateadas(){
        return this.cartas.stream()
                .map(carta -> String.format("%s%s", carta.getValor(), carta.getSimbolo()))
                .collect(Collectors.joining(", "));
    }

}
