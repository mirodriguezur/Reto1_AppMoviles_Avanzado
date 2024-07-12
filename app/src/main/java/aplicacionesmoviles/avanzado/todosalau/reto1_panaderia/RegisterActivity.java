package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextCedula;
    private EditText editTextCelular;
    private EditText editTextDireccion;
    private EditText editTextCorreo;
    private EditText editTextContrasenaR;
    private EditText editTextContrasenaConfiR;
    private Button buttonRegistrar;
    private Button buttonIngresar;
    private SwitchCompat switchAdmin;
    private RadioButton terminosYCondiciones;
    private RadioButton trataimentosDeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Inicializa Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        // Inicializa Firebase Firestore
        db = FirebaseFirestore.getInstance();

        setupView();
    }

    private void setupView() {
        // Vincula los componentes de la interfaz de usuario con las variables
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextCedula = findViewById(R.id.editTextCedula);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasenaR = findViewById(R.id.editTextContrasenaR);
        editTextContrasenaConfiR = findViewById(R.id.editTextContrasenaConfiR);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        buttonIngresar = findViewById(R.id.buttonIngresar);
        terminosYCondiciones = findViewById(R.id.terminosYCondiciones);
        trataimentosDeDatos = findViewById(R.id.trataimentosDeDatos);
        switchAdmin = findViewById(R.id.switchAdmin);

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Obtener los campos del formulario de registro
        List<String> fields = new ArrayList<>();
        if (getFields() != null) {
            fields = getFields();
        } else {
            return;
        }
        boolean isAdmin = switchAdmin.isChecked();

        List<String> finalFields = fields;
        // Crea el usuario en Firebase Authentication con el correo electrónico y la contraseña proporcionada.
        mAuth.createUserWithEmailAndPassword(fields.get(5), fields.get(6))
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Usuario registrado con éxito en Firebase Authentication
                                Log.d("Firebase Auth", "Se ha registrado el usuario correctamente");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    // Registra la información del usuario en Firestore
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("nombre", finalFields.get(0));
                                    userData.put("apellido", finalFields.get(1));
                                    userData.put("cedula", finalFields.get(2));
                                    userData.put("celular", finalFields.get(3));
                                    userData.put("direccion", finalFields.get(4));
                                    userData.put("correo", finalFields.get(5));
                                    userData.put("contrasena", finalFields.get(6));
                                    userData.put("isAdmin", isAdmin ? true : false);

                                    // Guarda los datos del usuario en una colección llamada "usuarios" en Firestore, utilizando el ID único del usuario como identificador del documento.
                                    // Agrega un listener que se ejecutará cuando la operación de guardado en Firestore sea exitosa.
                                    db.collection("usuarios")
                                            .document(user.getUid())
                                            .set(userData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    // Usuario registrado exitosamente en Firestore
                                                    Log.d("Firestore", "Usuario registrado exitosamente en Firestore");
                                                    Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    cleanFields();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
                                }

                            } else {
                                Log.e("Firebase Authentication", "Error al registrar usuario en Firebase Authentication", task.getException());
                                Toast.makeText(RegisterActivity.this, "Error al registrar usuario en Firebase Authentication", Toast.LENGTH_SHORT).show();

                            }
                        }

                );

    }

    // Método para obtener la  información de los campos del formulario de registro
    private List<String> getFields() {
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String cedula = editTextCedula.getText().toString().trim();
        String celular = editTextCelular.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();
        String contrasenaR = editTextContrasenaR.getText().toString().trim();
        String contrasenaConfiR = editTextContrasenaConfiR.getText().toString().trim();

        boolean aceptoTerminos = terminosYCondiciones.isChecked();
        boolean aceptoTratamiento = trataimentosDeDatos.isChecked();

        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || celular.isEmpty() || direccion.isEmpty() || correo.isEmpty() || contrasenaR.isEmpty() || contrasenaConfiR.isEmpty()) {
            Toast.makeText(this, "Por favor diligenciar todos los campos", Toast.LENGTH_LONG).show();
            return null;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "El correo no es válido", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (contrasenaR.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
            return null;
        }
        if (celular.length() != 10) {
            Toast.makeText(this, "El celular debe tener 10 dígitos", Toast.LENGTH_LONG).show();
            return null;
        }
        if (!contrasenaR.equals(contrasenaConfiR)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return null;
        }
        if (!aceptoTerminos) {
            Toast.makeText(this, "Por favor acepte los términos y condiciones", Toast.LENGTH_LONG).show();
            return null;
        }
        if (!aceptoTratamiento) {
            Toast.makeText(this, "Por favor acepte la política de privacidad", Toast.LENGTH_LONG).show();
            return null;
        }

        return List.of(nombre, apellido, cedula, celular, direccion, correo, contrasenaR, contrasenaConfiR);
    }

    private void cleanFields() {
        // Limpia los campos después del registro exitoso
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextCedula.setText("");
        editTextCelular.setText("");
        editTextDireccion.setText("");
        editTextCorreo.setText("");
        editTextContrasenaR.setText("");
        editTextContrasenaConfiR.setText("");
    }
}