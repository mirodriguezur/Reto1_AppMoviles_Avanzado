package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductCategory;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;

public class AddProductActivity extends AppCompatActivity {
    private ImageView imageProduct;
    private Spinner spnCategories;
    private Spinner spnProductName;
    private EditText editTextPrice;
    private EditText editTextAmount;
    private Button btnAddProduct;
    private Button btnListProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        setupView();
        setupSpinner();
    }

    private void setupView() {
        imageProduct = findViewById(R.id.imageProduct);
        spnCategories = findViewById(R.id.spnCategories);
        spnProductName = findViewById(R.id.spnProductName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextAmount = findViewById(R.id.editTextAmount);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnListProducts = findViewById(R.id.btnListProducts);

        //TODO: (Michael) Configurar la logica de los botones
    }

    private void setupSpinner() {
        ProductCategory[] categoriesList = ProductCategory.values();
        ProductsName[] productsList = ProductsName.values();

        ArrayAdapter<ProductCategory> adapterCategory = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoriesList);
        spnCategories.setAdapter(adapterCategory);

        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ProductCategory selectedCategory = categoriesList[position];
                List<ProductsName> filteredProducts = filterProductsByCategory(selectedCategory);

                ArrayAdapter<ProductsName> adapterProducts = new ArrayAdapter<>(AddProductActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, filteredProducts);
                spnProductName.setAdapter(adapterProducts);
                adapterProducts.notifyDataSetChanged(); // Notificar cambios al adaptador

                spnProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ProductsName selectedProduct = adapterProducts.getItem(position);
                        showImage(selectedProduct);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // No hacer nada
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });
    }

    private List<ProductsName> filterProductsByCategory(ProductCategory category) {
        List<ProductsName> filteredProducts = new ArrayList<>();
        for (ProductsName product : ProductsName.values()) {
            if (product.getCategory() == category) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    private void showImage(ProductsName product) {
        switch (product) {
            case PAN_BRETON:
                imageProduct.setImageResource(R.drawable.pan_breton);
                break;
            case PAN_CON_CHOCOLATE:
                imageProduct.setImageResource(R.drawable.pan_con_chocolate);
                break;
            case PAN_DE_MOLDE:
                imageProduct.setImageResource(R.drawable.pan_de_molde);
                break;
            case PAN_EN_TRENZA:
                imageProduct.setImageResource(R.drawable.pan_en_trenza);
                break;
            case PAN_HAMBURGUEZA:
                imageProduct.setImageResource(R.drawable.pan_hamburguesa);
                break;
            case PAN_MANCHEGO:
                imageProduct.setImageResource(R.drawable.pan_manchego);
                break;
            case POSTRE_HELADO_TENTACION:
                imageProduct.setImageResource(R.drawable.postre_helado_tentacion);
                break;
            case POSTRE_MAGDALENA:
                imageProduct.setImageResource(R.drawable.postre_magdalena);
                break;
            case POSTRE_MUFFINS:
                imageProduct.setImageResource(R.drawable.postre_muffins);
                break;
            case POSTRE_ROLLS_CANELA:
                imageProduct.setImageResource(R.drawable.postre_rolls_canela);
                break;
            case POSTRE_TARTA_INTEGRAL:
                imageProduct.setImageResource(R.drawable.postre_tarta_integral);
                break;
            case POSTRE_TARTA_ZANAHORIA:
                imageProduct.setImageResource(R.drawable.postre_tarta_zanahoria);
                break;
            case DONA_BACKED:
                imageProduct.setImageResource(R.drawable.dona_baked);
                break;
            case DONA_CHOCOLATE_BLANCO:
                imageProduct.setImageResource(R.drawable.dona_chocolate_blanco);
                break;
            case DONA_CHOCOLATE_FROSTED:
                imageProduct.setImageResource(R.drawable.dona_chocolate_frosted);
                break;
            case DONA_COMBO_BOSTON:
                imageProduct.setImageResource(R.drawable.dona_combo_boston);
                break;
            case DONA_SPUDNUT:
                imageProduct.setImageResource(R.drawable.dona_spudnut);
                break;
            case DONA_STRAWBERRY_FROSTED:
                imageProduct.setImageResource(R.drawable.dona_strawberry_frosted);
                break;
            default:
                imageProduct.setImageResource(R.drawable.ic_arrow_back);
        }
    }

}