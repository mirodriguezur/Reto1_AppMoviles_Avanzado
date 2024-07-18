package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

// Interfaz para el callback al eliminar un producto
public interface DeleteProductCallback {
    void onSuccess();
    void onError(Exception e);
}
