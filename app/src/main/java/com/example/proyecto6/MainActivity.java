package com.example.proyecto6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto6.util.Dealer;
import com.example.proyecto6.util.Partida;
import com.example.proyecto6.util.Carta;
import com.example.proyecto6.util.Historico;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Historico historico;
    private SharedPreferences sharedPreferences;
    private Partida partida;
    private Dealer dealer;

    private List<ImageView> imageViewsDealer;
    private List<ImageView> imageViewsPlayer;

    private Button btnNuevoJuego;
    private Button btnPedirCarta;
    private Button btnRetirarse;
    private Button btnHistorico;
    private TextView statusText;

    // Ciclo de Vida Android

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.historico = Historico.getHistorico();
        this.sharedPreferences = getSharedPreferences("Historico", MODE_PRIVATE);
        this.loadPartidas();
        this.partida = new Partida();
        this.dealer = new Dealer();
        this.loadGUIComponents();
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.savePartidas();
    }

    // Persistencia

    private void savePartidas(){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.historico.getPartidas());
        editor.putString("partidas", json);
        editor.apply();
        Log.d("HISTORICO GUARDADO", this.historico.toString());
    }

    private void loadPartidas(){
        String json = this.sharedPreferences.getString("partidas", "");
        Gson gson = new Gson();
        Partida[] savedObjects = gson.fromJson(json, Partida[].class);

        if(savedObjects!=null){
            List<Partida> partidas = new ArrayList<>();
            Arrays.stream(savedObjects)
                .forEach(object -> partidas.add((Partida) object));
            this.historico.setPartidas(partidas);
            Log.d("HISTORICO CARGADO", this.historico.toString());
        }
    }

    // Enlazamiento GUI

    private void loadGUIComponents(){
        this.btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        this.btnPedirCarta = findViewById(R.id.btnPedirCarta);
        this.btnRetirarse = findViewById(R.id.btnRetirarse);
        this.btnHistorico = findViewById(R.id.btnHistorico);
        this.statusText = findViewById(R.id.statusText);

        // ImageViews de las cartas
        this.imageViewsDealer = new ArrayList<>();
        this.imageViewsPlayer = new ArrayList<>();
        int resourceId;
        for(int i=1; i<=7; i++){
            resourceId = getResource(String.format("cardPlayer%d", i), "id");
            this.imageViewsPlayer.add(findViewById(resourceId));

            resourceId = getResource(String.format("cardDealer%d", i), "id");
            this.imageViewsDealer.add(findViewById(resourceId));
        }
    }

    // Click Listeners

    public void onNuevoJuego(View v){
        this.setLayoutJuego();
        this.limpiarTablero();
        this.setStatus("Turno: Player");
        this.empezarNuevaPartida();
    }

    public void onPlayerPideCarta(View v){
        this.partida.playerPideCarta();
        this.mostrarUltimaCartaPlayer();
        this.checkGanador();
        int numCartasPlayer = this.partida.getManoPlayer().getCartas().size();
        if(numCartasPlayer==7){
            this.onRetirarse(null);
        }
    }

    public void onRetirarse(View v){
        this.setStatus("Turno: Dealer");
        this.playDealer();
        this.partida.terminarPartida();
        this.checkGanador();
    }

    public void onHistorico(View v){
        Intent intent = new Intent(this, HistoricoActivity.class);
        startActivity(intent);
    }

    // Control del juego

    private void empezarNuevaPartida(){
        this.partida = new Partida();
        this.dealer.setPartida(partida);
        partida.iniciarPartida();
        this.ocultarPrimeraCartaDealer();
        this.mostrarUltimaCartaDealer();
        this.mostrarPrimeraCartaPlayer();
        this.mostrarUltimaCartaPlayer();
        this.checkGanador();
    }

    private void checkGanador(){
        if(partida.getGanador()!=null){
            Integer puntajePlayer = this.partida.getManoPlayer().getPuntaje();
            Integer puntajeDealer = this.partida.getManoDealer().getPuntaje();
            this.setStatus(String.format("%d vs %d - Ganador: %s", puntajeDealer, puntajePlayer, this.partida.getGanador()));
            this.mostrarPrimeraCartaDealer();
            this.historico.addPartida(this.partida);
            this.setLayoutMenu();
        }
    }

    private void playDealer() {
        while(partida.getGanador()==null && dealer.pedirCarta()){
            this.partida.dealerPideCarta();
            this.mostrarUltimaCartaDealer();
        }
    }

    // Movimientos de Carta Dealer

    private void ocultarPrimeraCartaDealer(){
        this.imageViewsDealer.get(0).setImageResource(R.drawable.card_back);
    }

    private void mostrarPrimeraCartaDealer(){
        this.dibujarCartaDealer(0);
    }

    private void mostrarUltimaCartaDealer(){
        this.dibujarCartaDealer(this.partida.getManoDealer().getCartas().size() - 1);
    }

    // Movimientos de Carta Player

    private void mostrarPrimeraCartaPlayer(){
        this.dibujarCartaPlayer(0);
    }

    private void mostrarUltimaCartaPlayer(){
        this.dibujarCartaPlayer(this.partida.getManoPlayer().getCartas().size() - 1);
    }

    // Renderizacion de Cartas

    private void dibujarCartaDealer(int i){
        ImageView imageView = this.imageViewsDealer.get(i);
        Carta carta = this.partida.getManoDealer().getCartas().get(i);
        this.dibujarCarta(imageView, carta);
    }

    private void dibujarCartaPlayer(int i){
        ImageView imageView = this.imageViewsPlayer.get(i);
        Carta carta = this.partida.getManoPlayer().getCartas().get(i);
        this.dibujarCarta(imageView, carta);
    }

    private void dibujarCarta(ImageView imageView, Carta carta){
        String resourceName = String.format("card_%s_%s", carta.getValor(), carta.getPalo());
        imageView.setImageResource(this.getResource(resourceName, "drawable"));
    }

    private void limpiarTablero(){
        int numCartasDealer = this.partida.getManoDealer().getCartas().size();
        for(int i=2; i<numCartasDealer; i++){
            this.imageViewsDealer.get(i).setImageResource(R.drawable.card_default);
        }
        int numCartasPlayer = this.partida.getManoPlayer().getCartas().size();
        for(int i=2; i<numCartasPlayer; i++){
            this.imageViewsPlayer.get(i).setImageResource(R.drawable.card_default);
        }
    }

    private int getResource(String resourceName, String resourceType){
        return getResources().getIdentifier(resourceName, resourceType, getPackageName());
    }

    // Apariencia de la Interfaz

    private void setLayoutMenu(){
        this.btnNuevoJuego.setVisibility(View.VISIBLE);
        this.btnHistorico.setVisibility(View.VISIBLE);
        this.btnPedirCarta.setVisibility(View.GONE);
        this.btnRetirarse.setVisibility(View.GONE);
    }

    private void setLayoutJuego(){
        this.btnNuevoJuego.setVisibility(View.GONE);
        this.btnHistorico.setVisibility(View.GONE);
        this.btnPedirCarta.setVisibility(View.VISIBLE);
        this.btnRetirarse.setVisibility(View.VISIBLE);
    }

    private void setStatus(String text){
        this.statusText.setText(text);
    }

}