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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // Inicialización de Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        setupView();
    }

    private void setupView() {
        // Vinculación de las vistas con los objetos correspondientes
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonIngresar);
        btnRegister = findViewById(R.id.buttonRegistar);

        // Configuración del botón de registro para abrir la actividad de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
            }
        });

        // Configuración del botón de inicio de sesión para llamar al método loginUser()
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    // Método para iniciar sesión
    private void loginUser() {
        // Obtener la información de los textfields del formulario login
        List<String> fields = new ArrayList<>();
        if (getFields() != null) {
            fields = getFields();
        } else {
            return;
        }

        // Iniciar sesión con Firebase Authentication
        //Agrega un listener que se ejecutará cuando la tarea de inicio de sesión se complete (exitosamente o no).
        mAuth.signInWithEmailAndPassword(fields.get(0), fields.get(1))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso, mostrar mensaje de éxito y redirigir a la actividad principal
                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            // TODO: Redirigir A actividad AddProductActivity en caso de qu sea admin, si no, redirigir a la actividad OrderActivity
                            Intent intent = new Intent(LoginActivity.this, AddProductActivity.class);
                            startActivity(intent);
                            finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
                        } else {
                            // Si falla el inicio de sesión, mostrar mensaje de error
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Método para obtener la informacion de los textfields de login
    private List<String> getFields() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // verifica que todos los campos estén completos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Verifica que el email tenga un formato válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "El email no es válido", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Verifica que la contraseña tenga al menos 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return null;
        }

        return List.of(email, password);
    }
}