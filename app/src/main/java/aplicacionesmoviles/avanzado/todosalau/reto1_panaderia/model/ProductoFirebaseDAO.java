package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoFirebaseDAO implements ProductoFirebaseInterface {
    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;

    // Constructor que inicializa la referencia a la base de datos
    public ProductoFirebaseDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
    }

    // Interfaz para el callback al agregar un producto
    public interface AddProductCallback {
        void onSuccess();
        void onError(Exception e);
    }

    // Método para agregar un producto a la base de datos
    public void addProduct(Producto product, AddProductCallback callback) {
        // Si el producto no tiene un ID, se genera uno nuevo
        if (product.getIdProducto() == null || product.getIdProducto().isEmpty()) {
            String newId = databaseReference.push().getKey();
            product.setIdProducto(newId);
        }

        // Agregar el producto a Firebase
        databaseReference.child(product.getIdProducto()).setValue(product)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onError);
    }

    // Método para obtener todos los productos de la base de datos
    public void getAllProducts(final GetProductsCallback callback) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Producto> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Producto product = productSnapshot.getValue(Producto.class);
                    if (product != null) {
                        products.add(product);
                    }
                }
                callback.onProductsRetrieved(products);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    // Método para eliminar un producto de la base de datos
    public void deleteProduct(String id, DeleteProductCallback callback) {
        // Verificar si el ID es nulo o está vacío
        if (id == null || id.isEmpty()) {
            callback.onError(new IllegalArgumentException("ID del producto es nulo o vacío."));
            return;
        }

        // Eliminar el producto de Firebase
        databaseReference.child(id).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onError);
    }

    // Método para verificar si un producto existe en la base de datos
    public void checkIfProductExists(String productId, ProductExistsCallback callback) {
        DatabaseReference productRef = databaseReference.child(productId);

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exists = snapshot.exists();
                callback.onProductExists(exists);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError();
            }
        });
    }

    // Método para actualizar un producto en la base de datos
    public void updateProduct(Producto product) {
        // Verificar si el ID del producto es nulo o está vacío
        if (product == null || product.getIdProducto() == null || product.getIdProducto().isEmpty()) {
            System.out.println("El ID del producto es nulo o vacío. No se puede actualizar el producto.");
            return; // Detener la ejecución si el ID es nulo o vacío
        }

        // Actualizar el producto en Firebase
        databaseReference.child(product.getIdProducto()).updateChildren(product.toMap());
    }
}
