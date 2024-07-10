package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button btnRestablecer;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    private String email ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        // Vinculación de las vistas con los objetos correspondientes
        editTextEmail = findViewById(R.id.editTextEmail);
        btnRestablecer = findViewById(R.id.btnRestablecer);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        // Configuración del botón de inicio de sesión para abrir la actividad de inicio de sesión
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
        });

        // Configuración del botón de inicio de sesión para abrir la actividad de inicio de sesión
        btnRestablecer.setOnClickListener(v -> {
            email = editTextEmail.getText().toString().trim();

            if(!email.isEmpty()){
                resetPassword();
            }else{
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

        });

    }


    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
            } else {
                Toast.makeText(this, "Error al restablecer la contraseña", Toast.LENGTH_SHORT).show();
            }
        });
        }
}