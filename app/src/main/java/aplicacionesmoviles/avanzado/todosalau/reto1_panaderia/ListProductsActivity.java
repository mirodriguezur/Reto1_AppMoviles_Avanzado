package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductManagerPresenter;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view.ProductManagerCellAdapter;

public class ListProductsActivity extends AppCompatActivity {
    private ArrayList<Producto> products; // Lista de usuarios a mostrar
    private ProductManagerCellAdapter productCellAdapter;
    private ListView listViewProducts;
    private ProductManagerPresenter productManagerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_products);

        productManagerPresenter = new ProductManagerPresenter(this);
        // Obtén la lista de productos del intent
        products = getIntent().getParcelableArrayListExtra("products");

        // Verifica si products es null y, si es así, inicialízala como una lista vacía
        if (products == null) {
            products = new ArrayList<>();
        }

        setupView();
    }

    private void setupView() {
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddProductActivity = new Intent(ListProductsActivity.this, AddProductActivity.class);
                startActivity(goToAddProductActivity);
            }
        });

        listViewProducts = findViewById(R.id.listViewProducts);
        productCellAdapter = new ProductManagerCellAdapter(this, R.layout.list_item_product, products);
        listViewProducts.setAdapter(productCellAdapter);
        updateProductList(products);
    }

    public void deleteProduct(Producto product) {
        productManagerPresenter.deleteProduct(product);
        loadProductsFromDatabase();
        Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show();
    }

    public void updateProduct(Producto product) {
        productManagerPresenter.updateProduct(product);
        loadProductsFromDatabase();
        Toast.makeText(this, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void updateProductList(ArrayList<Producto> products) {
        productCellAdapter.clear();
        productCellAdapter.addAll(products);
    }

    // Métodos relacionados con la carga de datos
    private void loadProductsFromDatabase() {
        List<Producto> products = productManagerPresenter.showListProducts();
        updateProductList((ArrayList<Producto>)products);
    }
}