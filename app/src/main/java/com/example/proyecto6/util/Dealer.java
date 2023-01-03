package com.example.proyecto6.util;

public class Dealer {

    private Partida partida;

    public void setPartida(Partida partida){
        this.partida = partida;
    }

    public boolean pedirCarta(){
        Integer puntajeVisiblePlayer = this.partida.getManoPlayer().getPuntajeSinCartaOculta();
        Integer puntajeDealer = this.partida.getManoDealer().getPuntaje();
        int numCartasDealer = this.partida.getManoDealer().getCartas().size();

        if( puntajeDealer>=18 ||
            (puntajeVisiblePlayer <=3 && puntajeDealer>=14) ||
            numCartasDealer==7){
            return false;
        }
        return true;
    }

}
