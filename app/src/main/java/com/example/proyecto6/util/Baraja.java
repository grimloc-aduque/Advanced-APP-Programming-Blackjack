package com.example.proyecto6.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Baraja {

    private static Random random = new Random();
    private static List<Carta> cartas;
    private static List<Carta> cartasUsadas;

    static{
        cartas = new ArrayList<>();
        Arrays.stream(Carta.valores).forEach(
            valor -> {
                Arrays.stream(Carta.palos).forEach(
                    palo -> cartas.add(new Carta(valor, palo))
                );
            }
        );
        Baraja.cartasUsadas = new ArrayList<>();
    }

    public static Carta getNextCarta(){
        Carta carta = cartas.remove(random.nextInt(cartas.size()));
        cartasUsadas.add(carta);
        logBaraja();
        return carta;
    }

    public static void nuevasCartas(){
        cartas.addAll(cartasUsadas);
        cartasUsadas.removeAll(cartasUsadas);
        logBaraja();
    }

    private static void logBaraja(){
        Log.d("Baraja", String.format("Numero de Cartas: %d", cartas.size()));
    }
}
