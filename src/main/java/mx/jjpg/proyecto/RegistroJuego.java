package mx.jjpg.proyecto;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroJuego  extends AppCompatActivity {

    private DatabaseUsers dbUsers;

    private Intent intent;

    private View pantallaCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_layout);

        pantallaCom = getWindow().getDecorView();

        pantallaCom.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        dbUsers = new DatabaseUsers(this);

        Button btnRegistrar = findViewById(R.id.registerButton);
        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        final EditText Usuario = findViewById(R.id.usuario);
        final EditText Password = findViewById(R.id.password);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = Usuario.getText().toString();
                String password = Password.getText().toString();

                if (usuarioExiste(usuario)) {
                    Toast.makeText(RegistroJuego.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    guardarUsuario(usuario, password);
                    Toast.makeText(RegistroJuego.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = Usuario.getText().toString();

                if (usuarioExiste(usuario)) {
                    // Aquí puedes implementar la lógica para iniciar sesión
                    intent = new Intent(view.getContext(), MenuJuego.class);
                    startActivity(intent);
                    Toast.makeText(RegistroJuego.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegistroJuego.this, "Usuario no registrado. Por favor, regístrate.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean usuarioExiste(String usuario) {
        SQLiteDatabase db = dbUsers.getReadableDatabase();
        String[] projection = {DatabaseUsers.COLUMN_USER};
        String selection = DatabaseUsers.COLUMN_USER + " = ?";
        String[] selectionArgs = {usuario};

        Cursor cursor = db.query(
                DatabaseUsers.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    private void guardarUsuario(String usuario, String contraseña) {
        SQLiteDatabase db = dbUsers.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseUsers.COLUMN_USER, usuario);
        values.put(DatabaseUsers.COLUMN_PASSWORD, contraseña);

        db.insert(DatabaseUsers.TABLE_USERS, null, values);
    }
}
