package com.example.proyecto6.util;

import java.util.HashMap;
import java.util.Map;

public class Carta {
    public static final String[] valores = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k"};
    private static final Integer[] puntajes ={1,2,3,4,5,6,7,8,9,10,10,10,10};
    public static final String[] palos = {"diamante", "rojo", "negro", "trebol"};

    private static final Map<String, String> simbolosMap;
    private static final Map<String, Integer> puntajesMap;

    static{
        simbolosMap = new HashMap<>();
        simbolosMap.put(palos[0], "\u2666");
        simbolosMap.put(palos[1], "\u2665");
        simbolosMap.put(palos[2], "\u2660");
        simbolosMap.put(palos[3], "\u2663");

        puntajesMap = new HashMap<>();
        for(int i=0; i<valores.length; i++){
            puntajesMap.put(valores[i], puntajes[i]);
        }
    }


    private String valor;
    private String palo;


    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public String getValor() {
        return valor;
    }

    public String getPalo() {
        return palo;
    }

    public String getSimbolo() {
        return Carta.simbolosMap.get(this.getPalo());
    }

    public Integer getPuntaje(){
        return Carta.puntajesMap.get(this.getValor());
    }

}
