package com.example.laboratorio1_iot;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.example.laboratorio1_iot.databinding.ActivityMainBinding;
import android.view.View;
import android.widget.PopupMenu;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.BotonJugar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameView1.class);
            startActivity(intent);
        });

        // Configura el OnLongClickListener para el elemento
        binding.tituloTele.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Mostrar un menú emergente aquí
                showPopupMenu(v);
                return true;
            }
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_colors, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                int color = Color.BLACK; // Color predeterminado
                // Asignar el color seleccionado
                if (id == R.id.color_azul) {
                    color = Color.BLUE;
                } else if (id == R.id.color_verde) {
                    color = Color.GREEN;
                } else if (id == R.id.color_rojo) {
                    color = Color.RED;
                }
                // Cambiar el color del elemento "tituloTele"
                binding.tituloTele.setTextColor(color);
                return true;
            }
        });
        popupMenu.show();
    }




}