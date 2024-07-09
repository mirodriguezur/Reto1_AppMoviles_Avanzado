package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActividadInicioSesion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText contrasena;
    private Button btnInicio;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);
        // Inicialización de Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        setupView();
    }
    private void setupView() {
        // Vinculación de las vistas con los objetos correspondientes
        correo = findViewById(R.id.editTextUsuario);
        contrasena = findViewById(R.id.editTextPassword);
        btnInicio = findViewById(R.id.buttonIngresar);
        btnRegistro = findViewById(R.id.buttonRegistar);

        // Configuración del botón de registro para abrir la actividad de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadInicioSesion.this, ActividadRegistro.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
            }
        });

        // Configuración del botón de inicio de sesión para llamar al método loginUser()
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    // Método para iniciar sesión
    private void loginUser() {
        String email = correo.getText().toString().trim();
        String password = contrasena.getText().toString().trim();

        // Validación de campos de entrada
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión con Firebase Authentication
        //Agrega un listener que se ejecutará cuando la tarea de inicio de sesión se complete (exitosamente o no).
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso, mostrar mensaje de éxito y redirigir a la actividad principal
                            Toast.makeText(ActividadInicioSesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            // TODO: Redirigir A actividad AddProductActivity en caso de qu sea admin, si no, redirigir a la actividad OrderActivity
                            Intent intent = new Intent(ActividadInicioSesion.this, AddProductActivity.class);
                            startActivity(intent);
                            finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
                        } else {
                            // Si falla el inicio de sesión, mostrar mensaje de error
                            Toast.makeText(ActividadInicioSesion.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
