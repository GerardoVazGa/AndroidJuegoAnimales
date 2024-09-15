package mx.jjpg.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AnimalesSelva extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CanvasSelva juego = new CanvasSelva(this);
        juego.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(juego);
    }
}

class CanvasSelva extends View {

    private final AsyncTaskAnimalSDB asyncTask;
    private List<String> nombresDisponibles;
    private Bitmap[] imgSelva;
    private boolean imagenesCargadas = false;
    private boolean presionado;
    private Bitmap btnRegreso;
    private Bitmap fondoselva;
    private Paint paintRectangulo;
    private Paint paintTexto;
    private RectF rectF;
    private String texto;
    private RectF palabraSelec;
    private float motionTouchEventY = 0f;
    private float motionTouchEventX = 0f;

    private boolean isDragging = false;
    private int draggedRectIndex = -1;

    private Random random = null;

    private List<String> nombresDisponiblesAleatorio;
    private RectF rectF1;
    private RectF rectF2;
    private RectF rectF3;
    private RectF rectF4;
    private DatabaseUsers dbAnimalSelva;
    private List<Integer> imgResourceSelva;
    private int indice;

    //Variables para sonido
    private MediaPlayer mediaPlayerAcierto;
    private MediaPlayer mediaPlayerFallo;
    private int i = 0;
    private Intent intent;
    private int puntos;

    public CanvasSelva(Context context) {
        super(context);
        dbAnimalSelva = new DatabaseUsers(context);
        inicializarRec();
        random = new Random();
        asyncTask = new AsyncTaskAnimalSDB();
        asyncTask.execute();
        cargarImagen();
        inicializarSonidos();
    }

    public CanvasSelva(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        dbAnimalSelva = new DatabaseUsers(context);
        cargarImagen();
        inicializarRec();
        random = new Random();
        asyncTask = new AsyncTaskAnimalSDB();
        asyncTask.execute();
        cargarImagen();
    }

    private void listaImagen() {
        imgResourceSelva = dbAnimalSelva.obtenerImgAnimalSelva();
    }

    private void listaNombres() {
        nombresDisponibles = dbAnimalSelva.obtenerAnimalSelva();
    }

    //funciones de sonidoo
    private void inicializarSonidos() {
        mediaPlayerAcierto = MediaPlayer.create(getContext(), R.raw.sonido_acierto);
        mediaPlayerFallo = MediaPlayer.create(getContext(), R.raw.sonido_fallo);
    }

    private void reproducirSonidoAcierto() {
        if (mediaPlayerAcierto != null) {
            mediaPlayerAcierto.start();
        }
    }

    private void reproducirSonidoFallo() {
        if (mediaPlayerFallo != null) {
            mediaPlayerFallo.start();
        }
    }
    /*--------------------------------------------------------------------- */


    public class AsyncTaskAnimalSDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            listaImagen();
            listaNombres();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (i < 5){
            if (fondoselva != null) {
                canvas.drawBitmap(fondoselva, 0, 0, null);
            }

            indice = random.nextInt(imgSelva.length);
            if (imagenesCargadas && imgSelva[indice] != null) {
                canvas.drawBitmap(imgSelva[indice], 120, 100, null);
            }

            if (btnRegreso != null) {
                canvas.drawBitmap(btnRegreso, 50, 20, null);
            }

            recuadrosResp(canvas);
            respuestas(canvas, indice);
        }else{
            intent = new Intent(getContext(), PuntosJuego.class);
            intent.putExtra("puntos", String.valueOf(puntos));
            getContext().startActivity(intent);
        }


    }


    public void recuadrosResp(Canvas canvas){
        // Obtener las dimensiones del lienzo
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // Calcular las dimensiones de los rectángulos
        int rectWidth = canvasWidth / 2;
        int rectHeight = canvasHeight / 8;


        // Dibujar los rectángulos en una matriz de 2x2
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int left = i * rectWidth + 50;
                int top = canvasHeight - (rectHeight * (j + 1)) - 50;
                int right = left + rectWidth - 100;
                int bottom = canvasHeight - (rectHeight * j) - 150;
                int cornerRadius = 60;

                RectF rectF = new RectF(left, top, right, bottom);
                paintRectangulo.setColor(Color.RED);
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paintRectangulo);

                // Asignar los rectángulos correspondientes a las variables rectF1, rectF2, etc.
                switch (i * 2 + j) {
                    case 0:
                        rectF1 = rectF;
                        break;
                    case 1:
                        rectF2 = rectF;
                        break;
                    case 2:
                        rectF3 = rectF;
                        break;
                    case 3:
                        rectF4 = rectF;
                        break;
                }
            }
        }
    }

    public void respuestas(Canvas canvas, int indice){

        if(nombresDisponibles != null && !nombresDisponibles.isEmpty()){
            nombresDisponiblesAleatorio = new ArrayList<>();

            while (nombresDisponiblesAleatorio.size() < 3){
                int indice1 = random.nextInt(nombresDisponibles.size());
                String nombreAl = nombresDisponibles.get(indice1);

                if (!nombresDisponiblesAleatorio.contains(nombreAl) && !Objects.equals(nombreAl, nombresDisponibles.get(indice))){
                    nombresDisponiblesAleatorio.add(nombreAl);
                }

            }

            nombresDisponiblesAleatorio.add(nombresDisponibles.get(indice));
            Collections.shuffle(nombresDisponiblesAleatorio);

        }

        if (nombresDisponiblesAleatorio != null){
            paintTexto.setTextSize(40);
            System.out.println("entra");

            for (int i = 0; i < nombresDisponiblesAleatorio.size(); i++) {
                RectF currentRect = null;
                switch (i) {
                    case 0:
                        currentRect = rectF1;
                        break;
                    case 1:
                        currentRect = rectF2;
                        break;
                    case 2:
                        currentRect = rectF3;
                        break;
                    case 3:
                        currentRect = rectF4;
                        break;
                    // Agrega más casos según sea necesario
                }
                if (currentRect != null) {
                    float textX = currentRect.centerX();
                    float textY = currentRect.centerY();
                    canvas.drawText(nombresDisponiblesAleatorio.get(i), textX, textY, paintTexto);
                }
            }
        } else {
            invalidate();
        }


    }

    public void cargarImagen(){
        fondoselva = BitmapFactory.decodeResource(getResources(), R.drawable.fondoselva);
        btnRegreso = BitmapFactory.decodeResource(getResources(), R.drawable.botonregresar);
        if (imgResourceSelva != null && !imgResourceSelva.isEmpty()){
            imgSelva = new Bitmap[imgResourceSelva.size()];
            for (int i = 0; i < imgResourceSelva.size(); i++) {
                imgSelva[i] = BitmapFactory.decodeResource(getResources(), imgResourceSelva.get(i));

                if (imgSelva[i] == null) {
                    Log.e("CanvasJuego", "Error al cargar la imagen " + i);
                    return; // Salir si hay un error al cargar la imagen
                }
            }
        }

        imagenesCargadas = true;

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        motionTouchEventX = event.getX();
        motionTouchEventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (motionTouchEventX > 70 && motionTouchEventX < 340 && motionTouchEventY < 340) {
                    presionado = true;
                    Toast.makeText(this.getContext(), "atras", Toast.LENGTH_SHORT).show();
                    invalidate();
                    ((Activity) getContext()).finish();
                }

                if (rectF1 != null && rectF1.contains(motionTouchEventX, motionTouchEventY)) {
                    Toast.makeText(this.getContext(), "funciona", Toast.LENGTH_SHORT).show();
                    verificarRespuesta(0);

                }

                if (rectF2 != null && rectF2.contains(motionTouchEventX, motionTouchEventY)) {
                    Toast.makeText(this.getContext(), "funciona1", Toast.LENGTH_SHORT).show();
                    verificarRespuesta(1);
                }

                if (rectF3 != null && rectF3.contains(motionTouchEventX, motionTouchEventY)) {
                    Toast.makeText(this.getContext(), "funciona2", Toast.LENGTH_SHORT).show();
                    verificarRespuesta(2);
                }

                if (rectF4 != null && rectF4.contains(motionTouchEventX, motionTouchEventY)) {
                    Toast.makeText(this.getContext(), "funciona3", Toast.LENGTH_SHORT).show();
                    verificarRespuesta(3);
                }

                break;
            case MotionEvent.ACTION_UP:
                presionado = false;
                break;
        }
        return true;
    }

    private void verificarRespuesta(int indiceRespuesta) {
        if (nombresDisponibles != null && nombresDisponiblesAleatorio != null &&
                !nombresDisponibles.isEmpty() && !nombresDisponiblesAleatorio.isEmpty() &&
                imgResourceSelva != null && imgResourceSelva.size() > indiceRespuesta &&
                nombresDisponiblesAleatorio.size() > indiceRespuesta) {

            String nombreRespuesta = nombresDisponiblesAleatorio.get(indiceRespuesta);
            String nombreImagen = getResources().getResourceEntryName(imgResourceSelva.get(indice));

            Log.d("verificarRespuesta", "Nombre respuesta: " + nombreRespuesta);
            Log.d("verificarRespuesta", "Nombre imagen: " + nombreImagen);

            if (Objects.equals(nombreRespuesta.toUpperCase(), nombreImagen.toUpperCase())){
                paintTexto.setColor(Color.GREEN);
                puntos += 100;
                reproducirSonidoAcierto();
                actualizarPuntuacion();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000); // Retraso de 1 segundo
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Invalidar la vista en el hilo principal después del retraso
                        post(new Runnable() {
                            @Override
                            public void run() {
                                i++;
                                invalidate();
                            }
                        });
                    }
                }).start();

            }else {
                paintTexto.setColor(Color.WHITE);
                puntos -= 50;
                reproducirSonidoFallo();
            }


            // Agregar un hilo para retrasar el redibujado si es necesario
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000); // Retraso de 1 segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Invalidar la vista en el hilo principal después del retraso
                    post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                }
            }).start();*/


        }else {
            System.out.println("No entra");
        }
    }

    private void actualizarPuntuacion() {
        // Mostrar puntuacion
        Toast.makeText(getContext(), "Puntos: " + puntos, Toast.LENGTH_SHORT).show();
    }

    public void inicializarRec(){
        paintRectangulo = new Paint();
        paintRectangulo.setColor(Color.BLUE);
        paintRectangulo.setStyle(Paint.Style.FILL);
        paintRectangulo.setAntiAlias(true);

        // Configurar el Paint para el texto
        paintTexto = new Paint();
        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(50);
        paintTexto.setTextAlign(Paint.Align.CENTER);
        paintTexto.setAntiAlias(true);
    }

}