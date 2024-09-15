package mx.jjpg.proyecto;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PuntosJuego extends AppCompatActivity {
    private ImageView imagen;
    private Intent intent;

    private Button btnRegreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fin);

        View pantComp = getWindow().getDecorView();

        pantComp.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        TextView textViewPuntuacion = findViewById(R.id.textViewPuntuacion);

        // Recuperar el puntaje del intent
        String puntaje = getIntent().getStringExtra("puntos");

        textViewPuntuacion.setText("¡Felicidades!\nTu puntaje: " + puntaje);
        imagen = findViewById(R.id.felicidades);
        imagen.setImageResource(R.drawable.felicidades);

        btnRegreso = findViewById(R.id.btnReg);

        btnRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), MenuJuego.class);
                startActivity(intent);
            }
        });


    }
}
