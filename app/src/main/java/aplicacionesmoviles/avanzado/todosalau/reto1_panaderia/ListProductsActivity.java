package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private ProductManagerPresenter productManagerPresenter;
    private Button btnSync;

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
        btnSync = findViewById(R.id.buttonSynch);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddProductActivity = new Intent(ListProductsActivity.this, AddProductActivity.class);
                startActivity(goToAddProductActivity);
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productManagerPresenter.syncProductsWithFirebase(new ProductManagerPresenter.OnInsertProductsInFirebaseListener(){

                    @Override
                    public void onConnectionError() {
                        Toast.makeText(ListProductsActivity.this, "No hay conexión a internet, intente más tarde", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorWhenTryingToDeleteProduct() {
                        Toast.makeText(ListProductsActivity.this, "No fue posilbe eliminar los productos, intente más tarde", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccessInsertProducts() {
                        Toast.makeText(ListProductsActivity.this, "Los productos fueron agregados exitosamente a Firebase", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorInsertProducts() {
                        Toast.makeText(ListProductsActivity.this, "No se pudo agregar los productos a Firebase, intente más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ListView listViewProducts = findViewById(R.id.listViewProducts);
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