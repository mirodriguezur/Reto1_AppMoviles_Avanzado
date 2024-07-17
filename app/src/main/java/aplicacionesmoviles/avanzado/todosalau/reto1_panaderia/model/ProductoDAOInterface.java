package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import java.util.List;

public interface ProductoDAOInterface {
    long insertProduct(Producto product);
    List<Producto> getAllProducts();
    void updateProduct(String idProduct, String category, String productName, String description, int price, int amount);
    void deleteProduct(String id);
    boolean productExistsByName(String name);
}
