package mx.jjpg.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUsers extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_USERS = "usuarios";
    public static final String COLUMN_USER = "usuario";
    public static final String COLUMN_PASSWORD = "password";

    //Nombre tabla y columnas Animales Granja
    public static final String TABLE_ANIMFARM = "Animales_Granja";
    public static  String COLUMN_ANIMFID = "id";
    public static final String COLUMN_ANIMFNOM= "Nombre";
    public static final String COLUMN_ANIMFID_RESOURCE = "imagen";

    public static final String TABLE_ANIMMAR = "Animales_Mar";
    public static  String COLUMN_ANIMMID = "id";
    public static final String COLUMN_ANIMMNOM= "Nombre";
    public static final String COLUMN_ANIMMID_RESOURCE = "imagen";

    public static final String TABLE_ANIMSELVA = "Animales_Selva";
    public static  String COLUMN_ANIMSID = "id";
    public static final String COLUMN_ANIMSNOM= "Nombre";
    public static final String COLUMN_ANIMSID_RESOURCE = "imagen";

    public static final String TABLE_ANIMBOSQUE = "Animales_Bosque";
    public static  String COLUMN_ANIMBID = "id";
    public static final String COLUMN_ANIMBNOM= "Nombre";
    public static final String COLUMN_ANIMBID_RESOURCE = "imagen";

    // Consulta SQL para crear la tabla
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_USERS + "(" + COLUMN_USER
            + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD
            + " TEXT NOT NULL);";


    public DatabaseUsers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        String DATABASE_CREATE_ANIMFARM = "CREATE TABLE "
                + TABLE_ANIMFARM + " (" +
                COLUMN_ANIMFID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANIMFNOM  + " TEXT, " +
                COLUMN_ANIMFID_RESOURCE  + " INTEGER NOT NULL);";

        database.execSQL(DATABASE_CREATE_ANIMFARM);
        agregarAnimalFarm(database, "Caballo", R.drawable.caballo);
        agregarAnimalFarm(database, "Cerdo", R.drawable.cerdo);
        agregarAnimalFarm(database, "Gallo", R.drawable.gallo);
        agregarAnimalFarm(database, "Vaca", R.drawable.vaca);
        agregarAnimalFarm(database, "Oveja", R.drawable.oveja);

        String DATABASE_CREATE_ANIMMAR = "CREATE TABLE "
                + TABLE_ANIMMAR + " (" +
                COLUMN_ANIMMID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANIMMNOM  + " TEXT, " +
                COLUMN_ANIMMID_RESOURCE  + " INTEGER NOT NULL);";

        database.execSQL(DATABASE_CREATE_ANIMMAR);
        agregarAnimalMar(database, "Ballena", R.drawable.ballena);
        agregarAnimalMar(database, "Delfin", R.drawable.delfin);
        agregarAnimalMar(database, "Pez", R.drawable.pez);
        agregarAnimalMar(database, "Tiburon", R.drawable.tiburon);
        agregarAnimalMar(database, "Tortuga", R.drawable.tortuga);

        String DATABASE_CREATE_ANIMSELV = "CREATE TABLE "
                + TABLE_ANIMSELVA + " (" +
                COLUMN_ANIMSID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANIMSNOM  + " TEXT, " +
                COLUMN_ANIMSID_RESOURCE  + " INTEGER NOT NULL);";

        database.execSQL(DATABASE_CREATE_ANIMSELV);
        agregarAnimalSelva(database, "Jirafa", R.drawable.jirafa);
        agregarAnimalSelva(database, "Gorila", R.drawable.gorila);
        agregarAnimalSelva(database, "Leon", R.drawable.leon);
        agregarAnimalSelva(database, "Tigre", R.drawable.tigre);
        agregarAnimalSelva(database, "Cebra", R.drawable.cebra);

        String DATABASE_CREATE_ANIMBOSQUE = "CREATE TABLE "
                + TABLE_ANIMBOSQUE + " (" +
                COLUMN_ANIMBID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANIMBNOM  + " TEXT, " +
                COLUMN_ANIMBID_RESOURCE  + " INTEGER NOT NULL);";

        database.execSQL(DATABASE_CREATE_ANIMBOSQUE);
        agregarAnimalBosque(database, "Ardilla", R.drawable.ardilla);
        agregarAnimalBosque(database, "Oso", R.drawable.oso);
        agregarAnimalBosque(database, "Mapache", R.drawable.mapache);
        agregarAnimalBosque(database, "Zorro", R.drawable.zorro);
        agregarAnimalBosque(database, "Venado", R.drawable.venado);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar las actualizaciones de la base de datos si es necesario
    }

    public boolean userExists(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USER};
        String selection = COLUMN_USER + " = ?";
        String[] selectionArgs = {user};

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void saveUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, user);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void agregarAnimalFarm(SQLiteDatabase db, String nombre, int recurso){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIMFNOM, nombre);
        values.put(COLUMN_ANIMFID_RESOURCE, recurso);
        db.insert(TABLE_ANIMFARM, null, values);

    }

    public List<String> obtenerAnimalFarm() {
        List<String> nombresAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMFNOM, COLUMN_ANIMFID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMFARM,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombreAnimal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMFNOM));

                // Añadir el nombre del animal y el recurso de imagen a la lista en algún formato adecuado
                nombresAnimales.add(nombreAnimal);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return nombresAnimales;
    }

    public void agregarAnimalMar(SQLiteDatabase db, String nombre, int recurso){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIMMNOM, nombre);
        values.put(COLUMN_ANIMMID_RESOURCE, recurso);
        db.insert(TABLE_ANIMMAR, null, values);

    }

    public List<String> obtenerAnimalMar() {
        List<String> nombresAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMMNOM, COLUMN_ANIMMID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMMAR,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombreAnimal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMMNOM));

                // Añadir el nombre del animal y el recurso de imagen a la lista en algún formato adecuado
                nombresAnimales.add(nombreAnimal);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return nombresAnimales;
    }

    public List<Integer> obtenerImgAnimalFarm() {
        List<Integer> recursoAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMFNOM, COLUMN_ANIMFID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMFARM,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recursoImagen = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMFID_RESOURCE));
                recursoAnimales.add(recursoImagen);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recursoAnimales;
    }

    public List<Integer> obtenerImgAnimalMar() {
        List<Integer> recursoAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMMNOM, COLUMN_ANIMMID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMMAR,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recursoImagen = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMMID_RESOURCE));
                recursoAnimales.add(recursoImagen);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recursoAnimales;
    }

    public void agregarAnimalSelva(SQLiteDatabase db, String nombre, int recurso){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIMSNOM, nombre);
        values.put(COLUMN_ANIMSID_RESOURCE, recurso);
        db.insert(TABLE_ANIMSELVA, null, values);

    }

    public List<String> obtenerAnimalSelva() {
        List<String> nombresAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMSNOM, COLUMN_ANIMSID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMSELVA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombreAnimal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMSNOM));

                // Añadir el nombre del animal y el recurso de imagen a la lista en algún formato adecuado
                nombresAnimales.add(nombreAnimal);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return nombresAnimales;
    }

    public List<Integer> obtenerImgAnimalSelva() {
        List<Integer> recursoAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMSNOM, COLUMN_ANIMSID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMSELVA,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recursoImagen = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMSID_RESOURCE));
                recursoAnimales.add(recursoImagen);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recursoAnimales;
    }

    public void agregarAnimalBosque(SQLiteDatabase db, String nombre, int recurso){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANIMBNOM, nombre);
        values.put(COLUMN_ANIMBID_RESOURCE, recurso);
        db.insert(TABLE_ANIMBOSQUE, null, values);

    }

    public List<String> obtenerAnimalBosque() {
        List<String> nombresAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMBNOM, COLUMN_ANIMBID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMBOSQUE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombreAnimal = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMBNOM));

                // Añadir el nombre del animal y el recurso de imagen a la lista en algún formato adecuado
                nombresAnimales.add(nombreAnimal);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return nombresAnimales;
    }

    public List<Integer> obtenerImgAnimalBosque() {
        List<Integer> recursoAnimales = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ANIMBNOM, COLUMN_ANIMBID_RESOURCE};
        Cursor cursor = db.query(
                TABLE_ANIMBOSQUE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Iterar a través de los resultados del cursor y agregar los nombres a la lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int recursoImagen = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMBID_RESOURCE));
                recursoAnimales.add(recursoImagen);

                Log.d("VALOR_IMAGEN", "Recurso de imagen: " + recursoImagen);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return recursoAnimales;
    }
}
