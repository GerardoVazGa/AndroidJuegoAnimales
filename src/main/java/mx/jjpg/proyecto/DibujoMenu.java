package mx.jjpg.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DibujoMenu extends View {

    private Bitmap btnRegreso;
    private Bitmap fondoRec1;
    private Bitmap fondoRec2;
    private Bitmap fondoRec3;
    private Bitmap fondoRec4;
    private Intent intent;

    public DibujoMenu(Context context) {
        super(context);
    }

    public DibujoMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public DibujoMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DibujoMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heighMeasureSpec) {
        int ancho = calcularAncho(widthMeasureSpec);
        int alto = calcularAlto(heighMeasureSpec);

        setMeasuredDimension(ancho, alto);
    }

    private int calcularAlto(int heighMeasureSpec) {
        int res = 100;
        int modo = MeasureSpec.getMode(heighMeasureSpec);
        int limite = MeasureSpec.getSize(heighMeasureSpec);
        if (modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY) {
            res = limite;
        }

        return res;
    }

    private int calcularAncho(int widthMeasureSpec) {
        int res = 100;
        int modo = MeasureSpec.getMode(widthMeasureSpec);
        int limite = MeasureSpec.getSize(widthMeasureSpec);
        if (modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY) {
            res = limite;
        }

        return res;
    }

    @Override
    public void onDraw(Canvas canvas) {
        btnRegreso = BitmapFactory.decodeResource(getResources(), R.drawable.botonregresar);
        fondoRec1 = BitmapFactory.decodeResource(getResources(), R.drawable.granja);
        fondoRec2 = BitmapFactory.decodeResource(getResources(), R.drawable.fondomar);
        fondoRec3 = BitmapFactory.decodeResource(getResources(), R.drawable.selva);
        fondoRec4 = BitmapFactory.decodeResource(getResources(), R.drawable.bosque);

        if (btnRegreso != null) {
            canvas.drawBitmap(btnRegreso, 50, 20, null);
        }

        //obtenemos las dimensiones del control
        float alto = getMeasuredHeight();
        float ancho = getMeasuredWidth();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(fondoRec1, (int) (ancho - 60) / 2, (int) (alto - 500) / 2, true);
        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(fondoRec2, (int) (ancho - 65) / 2, (int) (alto - 500) / 2, true);
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(fondoRec3, (int) (ancho - 65) / 2, (int) (alto - 500) / 2, true);
        Bitmap scaledBitmap3 = Bitmap.createScaledBitmap(fondoRec4, (int) (ancho - 65) / 2, (int) (alto - 500) / 2, true);


        //Color Blanco
        Paint pRelleno = new Paint();
        pRelleno.setStyle(Paint.Style.FILL);

        Paint paintTexto = new Paint();
        paintTexto.setColor(Color.BLACK);
        paintTexto.setTextSize(40);
        paintTexto.setTextAlign(Paint.Align.CENTER);

        float xTexto = (ancho / 2f) + ((ancho / 2f) - 25f + 25f) / 2;
        float yTexto = 250f + ((alto / 2f) - 30f - 250f) / 2 + Math.abs(paintTexto.descent() + paintTexto.ascent()) / 2;

        pRelleno.setColor(Color.GREEN);
        canvas.drawRect(25f, 250f, (ancho / 2f) - 25f, (alto / 2f) - 30f, pRelleno);
        canvas.drawBitmap(scaledBitmap, 25f, 250f, null);
        canvas.drawText("ANIMALES DE GRANJA", xTexto - 550f, yTexto, paintTexto);

        //Color Red
        pRelleno.setColor(Color.RED);
        canvas.drawRect((ancho / 2f) + 25f,
                250f,
                ancho - 25f,
                (alto / 2f) - 30f,
                pRelleno);
        canvas.drawBitmap(scaledBitmap1, ancho / 2 + 25f, 250f, null);

        canvas.drawText("ANIMALES DE MAR", xTexto, yTexto, paintTexto);

        pRelleno.setColor(Color.BLUE);
        canvas.drawRect(25f, (alto / 2f) + 30f, (ancho / 2f) - 25f, alto - 250f, pRelleno);
        canvas.drawBitmap(scaledBitmap2, 25f, (alto / 2f) + 30f, null);
        canvas.drawText("ANIMALES DE SELVA", xTexto - 550f, yTexto + 800f, paintTexto);


        //Color Red
        pRelleno.setColor(Color.LTGRAY);
        canvas.drawRect((ancho / 2f) + 25f,
                (alto / 2f) + 30f,
                ancho - 25f,
                alto - 250f,
                pRelleno);
        canvas.drawBitmap(scaledBitmap3, (ancho / 2f) + 25f, (alto / 2f) + 30f, null);
        canvas.drawText("ANIMALES DE BOSQUE", xTexto, yTexto + 800f, paintTexto);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getX() > 70 && event.getX() < 340 && event.getY() < 340) {
            Toast.makeText(this.getContext(), "atras", Toast.LENGTH_SHORT).show();
            invalidate();
            ((Activity) getContext()).finish();
        }

        if (event.getY() > 250f && event.getY() < getMeasuredHeight() / 2f - 30f) {
            if (event.getX() > 0 && event.getX() < getMeasuredWidth()) {
                if (event.getX() > 25f && event.getX() < getMeasuredWidth() / 2f - 25f) {
                    intent = new Intent(getContext(), Juego.class);
                    getContext().startActivity(intent);
                } else if (event.getX() > getMeasuredWidth() / 2f + 25f && event.getX() > 25f && event.getX() < getMeasuredWidth() - 25f) {
                    intent = new Intent(getContext(), AnimalesMar.class);
                    getContext().startActivity(intent);
                }
            }
        }else if (event.getY() > getMeasuredHeight() / 2f + 30f && event.getY() < getMeasuredHeight() - 250f) {
            if (event.getX() > 0 && event.getX() < getMeasuredWidth()) {
                if (event.getX() > 25f && event.getX() < getMeasuredWidth() / 2f - 25f) {
                    intent = new Intent(getContext(), AnimalesSelva.class);
                    getContext().startActivity(intent);
                }else if (event.getX() > getMeasuredWidth() / 2f + 25f && event.getX() < getMeasuredWidth() - 25f){
                    intent = new Intent(getContext(), AnimalesBosque.class);
                    getContext().startActivity(intent);
                }
            }

        }
        return super.onTouchEvent(event);
    }

}