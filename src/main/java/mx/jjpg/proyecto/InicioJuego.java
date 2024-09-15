package mx.jjpg.proyecto;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InicioJuego extends ConstraintLayout {
    ImageView imagen;

    Button btnInicio;

    Animation animation;

    private Intent intent;

    public InicioJuego(Context context){
        super(context);
        inicializar();
    }

    public InicioJuego(Context context, AttributeSet attrs){
        super(context, attrs);
        inicializar();
    }
    public void inicializar(){
        LayoutInflater li = LayoutInflater.from(getContext());
        li.inflate(R.layout.pantalla_start, this, true);

        imagen = findViewById(R.id.logo);

        btnInicio = findViewById(R.id.button);
        asignar();
    }

    public void asignar(){
        imagen.setImageResource(R.drawable.logo);
        btnInicio.setOnClickListener(evento);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_boton_inicio);

    }

    private View.OnClickListener evento = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v == btnInicio){
                btnInicio.startAnimation(animation);
                intent = new Intent(v.getContext(), RegistroJuego.class);
                getContext().startActivity(intent);
            }
        }


    };


}
