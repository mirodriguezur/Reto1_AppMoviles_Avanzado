package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

public interface ProductoFirebaseInterface {
    void addProduct(Producto product, ProductoFirebaseDAO.AddProductCallback callback);
    void getAllProducts(final GetProductsCallback callback);
    void deleteProduct(String id, DeleteProductCallback callback);
    void checkIfProductExists(String productId, ProductExistsCallback callback);
    void updateProduct(Producto product);
}
