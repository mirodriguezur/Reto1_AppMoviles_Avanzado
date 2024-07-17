package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.DeleteProductCallback;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.GetProductsCallback;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductExistsCallback;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoDAO;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoDAOInterface;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoFirebaseDAO;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoFirebaseInterface;

public class ProductManagerPresenter {
    private ProductoDAOInterface productDAO;
    private ProductoFirebaseInterface productoFirebaseDAO;
    private NetworkMonitor networkMonitor;

    public ProductManagerPresenter(Context context) {
        this.productDAO = new ProductoDAO(context);
        networkMonitor = new NetworkMonitor(context);
        productoFirebaseDAO = new ProductoFirebaseDAO();
    }

    public void insertProductToLocalDb(String category, String productName, String description, int price, int amount, OnInsertProductListener listener) {
        String validCategory = setFormatCategoryString(category);
        String validProductName = setFormatProductNameString(productName);

        if (productDAO.productExistsByName(validProductName)) {
            listener.onExistingProduct();
            return;
        }

        Producto product = new Producto(validCategory, validProductName, description, price, amount);
        long result =productDAO.insertProduct(product);
        if (result != -1) {
            listener.onSuccess(); //Notifica al listener que la inserción fue exitosa
        } else {
            listener.onError();  //Notifica al listener que la inserción falló
        }
    }

    public List<Producto> showListProducts() {
        return productDAO.getAllProducts();
    }

    public void deleteProduct(Producto product) {
        productDAO.deleteProduct(product.getIdProducto());
    }

    public void updateProduct(Producto product) {
        productDAO.updateProduct(product.getIdProducto(), product.getCategoria(), product.getNombreProducto(), product.getDescripcion(), product.getPrecioUnidad(), product.getCantidadStock());
    }

    //region Métodos relacionados con Firebase

    // Método para sincronizar productos con Firebase
    public void syncProductsWithFirebase(OnInsertProductsInFirebaseListener listener) {
        if (!networkMonitor.isNetworkAvailable()) {
            listener.onConnectionError();
            return;
        }
        compareAndRemoveProductsFromFirebase(listener);
        synchronizeProducts(listener);
    }

    private void compareAndRemoveProductsFromFirebase(OnInsertProductsInFirebaseListener listener) {
        productoFirebaseDAO.getAllProducts(new GetProductsCallback() {
            @Override
            public void onProductsRetrieved(List<Producto> productsFromFirebase) {
                List<Producto> productsFromSQLite = productDAO.getAllProducts();
                Set<String> sqliteProductIds = new HashSet<>();

                for (Producto sqliteProduct : productsFromSQLite) {
                    sqliteProductIds.add(sqliteProduct.getIdProducto());
                }

                List<Producto> productsToDeleteFromFirebase = new ArrayList<>();
                for (Producto firebaseProduct : productsFromFirebase) {
                    if (!sqliteProductIds.contains(firebaseProduct.getIdProducto())) {
                        productsToDeleteFromFirebase.add(firebaseProduct);
                    }
                }

                deleteProductsFromFirebase(productsToDeleteFromFirebase, listener);
            }

            @Override
            public void onError() {}
        });
    }

    private void synchronizeProducts(OnInsertProductsInFirebaseListener listener) {
        List<Producto> productsFromSQLite = productDAO.getAllProducts();
        synchronizeProductsToFirebase(productsFromSQLite, listener);
    }

    private void deleteProductsFromFirebase(List<Producto> productsToDeleteFromFirebase, OnInsertProductsInFirebaseListener listener) {
        for (Producto productToDelete : productsToDeleteFromFirebase) {
            productoFirebaseDAO.deleteProduct(productToDelete.getIdProducto(), new DeleteProductCallback() {
                @Override
                public void onSuccess() {}

                @Override
                public void onError(Exception e) {
                    listener.onErrorWhenTryingToDeleteProduct();
                }
            });
        }
    }

    private void synchronizeProductsToFirebase(List<Producto> productsFromSQLite, OnInsertProductsInFirebaseListener listener) {
        for (Producto product : productsFromSQLite) {
            productoFirebaseDAO.checkIfProductExists(product.getIdProducto(), new ProductExistsCallback() {
                @Override
                public void onProductExists(boolean exists) {
                    if (exists) {
                        productoFirebaseDAO.updateProduct(product);
                    } else {
                        productoFirebaseDAO.addProduct(product, new ProductoFirebaseDAO.AddProductCallback() {
                            @Override
                            public void onSuccess() {
                                listener.onSuccessInsertProducts();
                            }

                            @Override
                            public void onError(Exception e) {
                                listener.onErrorInsertProducts();
                            }
                        });
                    }
                }

                @Override
                public void onError() {}
            });
        }
    }

    //endregion

    private String setFormatCategoryString(String category) {
        return category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
    }

    private String setFormatProductNameString(String productName) {
        String[] words = productName.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (!word.isEmpty()) {
                if (i == 0) { // Solo capitalizar la primera palabra
                    result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
                } else {
                    result.append(word); // Dejar las demás palabras en minúsculas
                }
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    public interface OnInsertProductListener {
        void onSuccess();
        void onError();
        void onExistingProduct();
    }

    public interface OnInsertProductsInFirebaseListener {
        void onConnectionError();
        void onErrorWhenTryingToDeleteProduct();
        void onSuccessInsertProducts();
        void onErrorInsertProducts();
    }
}
