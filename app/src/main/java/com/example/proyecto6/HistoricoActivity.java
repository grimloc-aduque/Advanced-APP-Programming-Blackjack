package com.example.proyecto6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto6.util.Historico;

public class HistoricoActivity extends AppCompatActivity {
    Historico historico;
    SharedPreferences sharedPreferences;
    RecyclerView partidasRecyclerView;
    PartidasAdapter partidasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        this.historico = Historico.getHistorico();
        this.sharedPreferences = getSharedPreferences("Historico", MODE_PRIVATE);;

        this.partidasRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewPartidas);
        this.partidasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.partidasAdapter = new PartidasAdapter(this.historico.getPartidas());
        this.partidasRecyclerView.setAdapter(this.partidasAdapter);
    }

    public void onRegresar(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onBorrarHistorico(View v){
        this.historico.getPartidas().removeAll(this.historico.getPartidas());
        this.partidasAdapter.notifyDataSetChanged();

        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove("partidas");
        editor.apply();

        Log.d("HISTORICO ELIMINADO", this.historico.toString());
    }
}


