package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductCategory;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductManagerPresenter;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view.ProductManagerCellAdapter;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view.ProductManagerClientAdapter;

public class HomeClient extends AppCompatActivity {

    private List<Producto> listProducts = new ArrayList<>();
    private ProductManagerPresenter productManagerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_client);
        productManagerPresenter = new ProductManagerPresenter(this);
        productManagerPresenter.listProducts.observe(this, products -> {
            listProducts.addAll(products);
            setAdapter(products);
        });
        productManagerPresenter.getAllProductsFromFirebase();

        Button btnPan = findViewById(R.id.panFilterButton);
        Button btnDona = findViewById(R.id.donaFilterButton);
        Button btnPostres = findViewById(R.id.postreFilterButton);
        Button btnBebidas = findViewById(R.id.bebidasFilterButton);

        btnBebidas.setOnClickListener(v -> {
            setAdapter(filterProductsByCategory(ProductCategory.BEBIDAS));
        });

        btnPostres.setOnClickListener(v -> {
            setAdapter(filterProductsByCategory(ProductCategory.POSTRES));
        });

        btnDona.setOnClickListener(v -> {
            setAdapter(filterProductsByCategory(ProductCategory.DONAS));
        });

        btnPan.setOnClickListener(v -> {
            setAdapter(filterProductsByCategory(ProductCategory.PANES));
        });
    }

    public void setAdapter( List<Producto> products ) {
        ProductManagerClientAdapter adapter = new ProductManagerClientAdapter(this, R.layout.client_list_item_product, products);
        ListView listViewProducts = findViewById(R.id.listViewProductsClient);
        listViewProducts.setAdapter(adapter);
    }
    private List<Producto> filterProductsByCategory(ProductCategory category) {
        List<Producto> filteredProducts = new ArrayList<>();
        Log.d("Producto", "cantidad: " + listProducts.size());
        for (int i = 0; i < listProducts.size(); i++) {
            Producto product = listProducts.get(i);
            Log.d("Producto", "filterProductsByCategory: " + product.getCategoria() + " contra " + category.name());
            if (product.getCategoria().equalsIgnoreCase(category.name())) {
                filteredProducts.add(product);
            }
        }
        Log.d("Producto", "fin");
        return filteredProducts;
    }
}
