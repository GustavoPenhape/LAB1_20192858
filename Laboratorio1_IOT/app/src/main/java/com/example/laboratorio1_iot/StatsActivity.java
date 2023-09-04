package com.example.laboratorio1_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StatsActivity extends AppCompatActivity {

    private ArrayList<String> resultadosJuegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setTitle("TeleAhorcado");

        // Recupera el LinearLayout donde mostrar los resultados
        LinearLayout linearLayoutResults = findViewById(R.id.statisticsLayout);

        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs2", MODE_PRIVATE);
        Set<String> resultadosSet = sharedPreferences2.getStringSet("resultadosJuegos", new HashSet<>());

        // No declares una nueva variable "resultadosJuegos" aquí, utiliza la variable de instancia existente
        resultadosJuegos.addAll(resultadosSet);

        // Itera sobre los resultados y crea vistas de texto para mostrarlos
        for (String resultado : resultadosJuegos) {
            TextView textViewResultado = new TextView(this);
            textViewResultado.setText(resultado);
            textViewResultado.setTextSize(24);
            linearLayoutResults.addView(textViewResultado);
        }
        // Configura el botón para navegar a GameView1
        Button botonStats = findViewById(R.id.boton_stats);
        botonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar GameView1 con contador predeterminado en 1
                Intent intent = new Intent(StatsActivity.this, GameView1.class);
                startActivity(intent);
            }
        });
    }
}

