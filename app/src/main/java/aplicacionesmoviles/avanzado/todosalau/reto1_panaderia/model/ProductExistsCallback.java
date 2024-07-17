package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

// Interfaz para el callback de existencia de un producto
public interface ProductExistsCallback {
    void onProductExists(boolean exists);
    void onError();
}
