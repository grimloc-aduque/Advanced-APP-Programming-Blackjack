package com.example.proyecto6.util;

public class Partida {

    private Mano manoPlayer;
    private Mano manoDealer;
    private String ganador = null;

    private static String dealer = "Dealer";
    private static String player = "Player";

    public Partida(){
        this.manoPlayer = new Mano();
        this.manoDealer = new Mano();
    }

    public void iniciarPartida(){
        Baraja.nuevasCartas();
        this.manoPlayer.addCarta(Baraja.getNextCarta());
        this.manoPlayer.addCarta(Baraja.getNextCarta());
        Integer puntajePlayer = this.manoPlayer.getPuntaje();

        this.manoDealer.addCarta(Baraja.getNextCarta());
        this.manoDealer.addCarta(Baraja.getNextCarta());
        Integer puntajeDealer = this.manoDealer.getPuntaje();

        if(puntajeDealer == 21){
            this.setGanador(dealer);
        }
    }

    public void playerPideCarta(){
        this.manoPlayer.addCarta(Baraja.getNextCarta());
        Integer puntajePlayer = this.manoPlayer.getPuntaje();
        if(puntajePlayer > 21){
            this.setGanador(dealer);
        }
    }

    public void dealerPideCarta(){
        this.manoDealer.addCarta(Baraja.getNextCarta());
        Integer puntajeDealer = this.manoDealer.getPuntaje();
        if(puntajeDealer > 21){
            this.setGanador(player);
        }
    }

    public void terminarPartida(){
        if(this.ganador == null){
            Integer puntajePlayer = this.manoPlayer.getPuntaje();
            Integer puntajeDealer = this.manoDealer.getPuntaje();

            if(puntajeDealer >= puntajePlayer){
                this.setGanador(dealer);
            }else{
                this.setGanador(player);
            }
        }
    }

    private void setGanador(String ganador){
        this.ganador = ganador;
    }

    public String getGanador(){
        return this.ganador;
    }


    public Mano getManoPlayer(){
        return this.manoPlayer;
    }

    public Mano getManoDealer(){
        return this.manoDealer;
    }

}
