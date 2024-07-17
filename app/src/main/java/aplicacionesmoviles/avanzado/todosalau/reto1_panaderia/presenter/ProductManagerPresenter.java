package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter;

import android.content.Context;

import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoDAO;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.ProductoDAOInterface;

public class ProductManagerPresenter {
    private ProductoDAOInterface productDAO;

    public ProductManagerPresenter(Context context) {
        this.productDAO = new ProductoDAO(context);
    }

    public void insertProductToLocalDb(String category, String productName, String description, int price, int amount, OnInsertProductListener listener) {
        String validCategory = setFormatCategoryString(category);
        String validProductName = setFormatProductNameString(productName);
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

    private String setFormatCategoryString(String category) {
        return category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
    }

    private String setFormatProductNameString(String productName) {
        String[] words = productName.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if(!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase() + word.substring(1));
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
    }
}
