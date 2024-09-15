package mx.jjpg.proyecto;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuJuego extends AppCompatActivity {
    private View pantComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        pantComp = getWindow().getDecorView();
        pantComp.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        DibujoMenu dibujoMenu = findViewById(R.id.dibujoMenu);


    }
}

