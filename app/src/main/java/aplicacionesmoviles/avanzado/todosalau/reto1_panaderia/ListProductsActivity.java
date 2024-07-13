package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view.ProductManagerCellAdapter;

public class ListProductsActivity extends AppCompatActivity {
    private ArrayList<Producto> products; // Lista de usuarios a mostrar
    private ProductManagerCellAdapter productCellAdapter;
    private ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_products);
        // Obtén la lista de productos del intent
        products = getIntent().getParcelableArrayListExtra("products");

        // Verifica si products es null y, si es así, inicialízala como una lista vacía
        if (products == null) {
            products = new ArrayList<>();
        }

        setupView();
    }

    private void setupView() {
        listViewProducts = findViewById(R.id.listViewProducts);
        productCellAdapter = new ProductManagerCellAdapter(this, R.layout.list_item_product, products);
        listViewProducts.setAdapter(productCellAdapter);
        updateProductList(products);
    }

    private void updateProductList(ArrayList<Producto> products) {
        productCellAdapter.clear();
        productCellAdapter.addAll(products);
    }
}