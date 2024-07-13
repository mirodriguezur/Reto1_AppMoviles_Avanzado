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

    public void insertProductToLocalDb(String category, String productName, int price, int amount) {
        String validCategory = setFormatCategoryString(category);
        String validProductName = setFormatProductNameString(productName);
        Producto product = new Producto(validCategory, validProductName, price, amount);
        productDAO.insertProduct(product);
    }

    public List<Producto> showListProducts() {
        return productDAO.getAllProducts();
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
}
