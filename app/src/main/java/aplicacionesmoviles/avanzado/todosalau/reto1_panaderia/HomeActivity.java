package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView textViewNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        textViewNombre = findViewById(R.id.textViewNombre);
        validateUser();

    }

    private void validateUser() {
     String id = mAuth.getCurrentUser().getUid();
     db.collection("usuarios").document(id).get().addOnCompleteListener(task -> {
         if (task.isSuccessful()) {
             DocumentSnapshot document = task.getResult();
             if (document.exists()) {
                 // El documento existe
                 Map<String, Object> data = document.getData();
                 Boolean isAdmin = (Boolean) data.get("isAdmin");
                 if (Boolean.TRUE.equals(isAdmin)) {
                     // El usuario es administrador
                     Log.d("HomeActivity", "El usuario es administrador.");
                     Intent intent = new Intent(HomeActivity.this, AddProductActivity.class);
                     startActivity(intent);
                     finish();
                 } else {
                     // El usuario no es administrador
                     Log.d("HomeActivity", "El usuario no es administrador.");
                     String nombre = (String) data.get("nombre");
                     textViewNombre.setText(nombre);

                 }

             } else {
                 // El documento no existe
                 Log.d("HomeActivity", "No se encontró el usuario.");
             }
         } else {
             // Error al obtener el documento
             Log.d("HomeActivity", "Error al obtener el documento: ", task.getException());
         }
     });


    }


}