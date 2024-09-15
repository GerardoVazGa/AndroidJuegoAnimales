package mx.jjpg.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private View pantComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pantComp = getWindow().getDecorView();

        pantComp.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        InicioJuego juego = findViewById(R.id.inicioJuego);

    }
}