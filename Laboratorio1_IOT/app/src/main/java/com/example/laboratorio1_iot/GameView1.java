package com.example.laboratorio1_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameView1 extends AppCompatActivity {
    private int contadorJuegos = 1; // Declarar contadorJuegos aquí
    private ArrayList<String> resultadosJuegos = new ArrayList<>();


    private WebView webView;
    private StringBuilder palabraRevelada; // Declarar palabraRevelada como variable de instancia

    private String palabraOculta;
    /*private String[] listaDePalabras = {"BATI"};
*/
    private String[] listaDePalabras = {"REDES", "PROPA", "PUCP", "TELITO", "TELECO","BATI"};
    private ArrayList<String> letrasDeshabilitadas = new ArrayList<>();
    private ImageView cabeza;
    private ImageView torso;
    private ImageView brazo_derecho;
    private ImageView brazo_izquierdo;
    private ImageView pierna_derecha;
    private ImageView pierna_izquierda;
    private int errores = 0;
    private long tiempoInicio = 0;
    private long tiempoFin = 0;
    private boolean juegoCancelado = false;
    private boolean juegoGanado = false;
    private boolean juegoPerdido = false;

    private final String[] letras = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view1);
        getSupportActionBar().setTitle("TeleAhorcado");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        contadorJuegos = sharedPreferences.getInt("contadorJuegos", 1);


        // Obtener el TextView donde mostrar la palabra oculta
        TextView textViewPalabraOculta = findViewById(R.id.textViewPalabraOculta);

        // Tu lista de palabras (puedes declararla aquí o en otro lugar)

        Button buttonReiniciar = findViewById(R.id.button_nuevo_juego);
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si el juego no ha terminado (ni ganado ni perdido), establece juegoCancelado en true
                SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs2", MODE_PRIVATE);
                Set<String> resultadosSet = sharedPreferences2.getStringSet("resultadosJuegos", new HashSet<>());
                resultadosJuegos.addAll(resultadosSet);
                if (!juegoGanado && !juegoPerdido) {
                    String resultado = "Juego " + contadorJuegos + ": Canceló";
                    resultadosJuegos.add(resultado);
                    contadorJuegos++;
                }
                // Guarda la lista actualizada en SharedPreferences
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                Set<String> resultadosSetActualizados = new HashSet<>(resultadosJuegos);
                editor2.putStringSet("resultadosJuegos", resultadosSetActualizados);
                editor2.apply();

                // Guarda el valor actualizado de contadorJuegos en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("contadorJuegos", contadorJuegos);
                editor.apply();
                // Llama a recreate() para reiniciar la actividad actual
                recreate();
            }
        });

        cabeza = findViewById(R.id.cabeza);
        torso = findViewById(R.id.torso);
        pierna_derecha = findViewById(R.id.pierna_derecha);
        pierna_izquierda = findViewById(R.id.pierna_izquierda);
        brazo_derecho = findViewById(R.id.brazo_derecho);
        brazo_izquierdo = findViewById(R.id.brazo_izquierdo);
        // Elegir una palabra al azar
        Random random = new Random();
        int indicePalabra = random.nextInt(listaDePalabras.length);
        palabraOculta = listaDePalabras[indicePalabra];


        // Inicializa palabraRevelada sin espacios en blanco
        palabraRevelada = new StringBuilder(palabraOculta.length());
        Log.d("MiApp", "palabraRevelada: " + palabraRevelada);


        // Inicializa palabraVisual con espacios en blanco
        StringBuilder palabraVisual = new StringBuilder();
        for (int i = 0; i < palabraOculta.length(); i++) {
            palabraRevelada.append("_ ");
        }


        // Crear una cadena con guiones bajos en lugar de letras
        StringBuilder palabraOcultaConGuiones = new StringBuilder();
        for (int i = 0; i < palabraOculta.length(); i++) {
            palabraOcultaConGuiones.append("_ ");
        }

        tiempoInicio = System.currentTimeMillis();

        textViewPalabraOculta.setText(palabraOcultaConGuiones.toString());
        for (String letra : letras) {
            int buttonId = getResources().getIdentifier("button_" + letra, "id", getPackageName());
            Button button = findViewById(buttonId);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    letraPresionada(v,letra);
                }
            });
        }

    }
    public void letraPresionada(View v, String letra) {
        boolean letraAdivinada = false; // Variable para verificar si se adivinó alguna letra en este clic
        // Lógica para manejar el clic de una letra
        Log.d("MiApp", "Botón " + letra + " presionado");

        for (int i = 0; i < palabraOculta.length(); i++) {
            if (palabraOculta.charAt(i) == letra.charAt(0)) {
                palabraRevelada.setCharAt(i*2, letra.charAt(0));
                Log.d("MiApp", "palabraRevelada: " + palabraRevelada.toString());
                letraAdivinada=true;
            }
        }
        String palabraVisual = palabraRevelada.toString();
        TextView textViewPalabraOculta = findViewById(R.id.textViewPalabraOculta);
/*
        textViewPalabraOculta.setText(palabraRevelada.toString());
*/
        textViewPalabraOculta.setText(palabraVisual);
        if (palabraOculta.equals(palabraVisual.replace(" ", ""))) {
            tiempoFin = System.currentTimeMillis();
            long tiempoTranscurrido = (tiempoFin - tiempoInicio) / 1000;

            // Recupera los resultados existentes de SharedPreferences
            SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs2", MODE_PRIVATE);
            Set<String> resultadosSet = sharedPreferences2.getStringSet("resultadosJuegos", new HashSet<>());
            resultadosJuegos.addAll(resultadosSet);


            String resultado = "Juego " + contadorJuegos + ": Terminó en " + tiempoTranscurrido + "s";
            resultadosJuegos.add(resultado);

            // Guarda la lista actualizada en SharedPreferences
            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            Set<String> resultadosSetActualizados = new HashSet<>(resultadosJuegos);
            editor2.putStringSet("resultadosJuegos", resultadosSetActualizados);
            editor2.apply();

            contadorJuegos++;
            juegoGanado = true; // Establece que el juego se ha ganado
            juegoPerdido = false; // Asegúrate de que el juego no se marque como perdido
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("contadorJuegos", contadorJuegos);
            editor.apply();

            TextView textViewResultado = findViewById(R.id.textViewResultado);
            textViewResultado.setText("GANASTE | Tiempo: " + tiempoTranscurrido + " segundos");
            textViewResultado.setVisibility(View.VISIBLE);

            // Deshabilitar todos los botones
            for (String letraBoton : letras) {
                int buttonId = getResources().getIdentifier("button_" + letraBoton, "id", getPackageName());
                Button button = findViewById(buttonId);
                button.setEnabled(false);
            }
        }else if (!letraAdivinada){
            Log.d("MiApp", "palabraRevelada: " + palabraRevelada.toString());
            errores = errores+1;
            if(errores == 1){
                cabeza.setVisibility(View.VISIBLE);
            } else if (errores == 2) {
                torso.setVisibility(View.VISIBLE);
            } else if (errores == 3) {
                brazo_izquierdo.setVisibility(View.VISIBLE);
            } else if (errores == 4) {
                brazo_derecho.setVisibility(View.VISIBLE);
            } else if (errores == 5) {
                pierna_izquierda.setVisibility(View.VISIBLE);
            } else if (errores == 6) {
                tiempoFin = System.currentTimeMillis();
                pierna_derecha.setVisibility(View.VISIBLE);

                long tiempoTranscurrido = (tiempoFin - tiempoInicio) / 1000; // Dividido por 1000 para obtener segundos

                TextView textViewResultado = findViewById(R.id.textViewResultado);

                textViewResultado.setText("PERDISTE | Terminó en " + tiempoTranscurrido + " segundos");

                textViewResultado.setVisibility(View.VISIBLE); // Hacerlo visible
                // Recupera los resultados existentes de SharedPreferences
                SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs2", MODE_PRIVATE);
                Set<String> resultadosSet = sharedPreferences2.getStringSet("resultadosJuegos", new HashSet<>());
                resultadosJuegos.addAll(resultadosSet);

                // Aquí agrega la lógica para guardar el resultado cuando el jugador pierde
                String resultado = "Juego " + contadorJuegos + ": Terminó en " + tiempoTranscurrido + "s";
                resultadosJuegos.add(resultado);

                // Guarda la lista actualizada en SharedPreferences
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                Set<String> resultadosSetActualizados = new HashSet<>(resultadosJuegos);
                editor2.putStringSet("resultadosJuegos", resultadosSetActualizados);
                editor2.apply();

                contadorJuegos++;

                juegoGanado = false; // Asegúrate de que el juego no se marque como ganado
                juegoPerdido = true; // Establece que el juego se ha perdido

                // Guarda el valor actualizado de contadorJuegos en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("contadorJuegos", contadorJuegos);
                editor.apply();

                // Deshabilitar todos los botones
                for (final String letraBoton : letras) {
                    int buttonId = getResources().getIdentifier("button_" + letraBoton, "id", getPackageName());
                    Button button = findViewById(buttonId);
                    button.setEnabled(false);
                }
            }
        }
        v.setEnabled(false);
        letrasDeshabilitadas.add(letra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_query) {
            // El elemento de menú "Stats" ha sido seleccionado
            // Inicia la actividad StatsActivity
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}