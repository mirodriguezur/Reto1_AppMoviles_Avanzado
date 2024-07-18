package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import java.util.List;

// Interfaz para el callback de obtención de productos
public interface GetProductsCallback {
    void onProductsRetrieved(List<Producto> products);
    void onError();
}
