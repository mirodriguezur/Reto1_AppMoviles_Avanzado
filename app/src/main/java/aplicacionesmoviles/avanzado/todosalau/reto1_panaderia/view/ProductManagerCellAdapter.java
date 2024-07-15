package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.ListProductsActivity;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.R;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;

public class ProductManagerCellAdapter extends ArrayAdapter<Producto> {
    private final List<Producto> internalProducts;
    // Recursos de diseño y contexto
    private int resourceLayout;
    private Context mContext;

    public ProductManagerCellAdapter(Context context, int resource, List<Producto> items) {
        super(context, resource, new ArrayList<>());
        this.resourceLayout = resource;
        this.mContext = context;
        internalProducts = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return internalProducts.size();
    }

    @Override
    public Producto getItem(int position) {
        return internalProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Método para obtener la vista del adaptador para un elemento específico
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // Si la vista es nula, inflar el diseño
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(resourceLayout, parent, false);
        }

        // Obtener el producto actual
        Producto product = getItem(position);

        // Si el producto es válido, establecer los valores en la vista
        if (product != null) {
            // Obtener las vistas de la celda
            TextView textViewCategory = view.findViewById(R.id.textInfoCategory);
            TextView textViewName = view.findViewById(R.id.textInfoProducName);
            TextView textViewPrice = view.findViewById(R.id.textInfoCost);
            TextView textViewAmount = view.findViewById(R.id.textInfoAmount);
            ImageView imageProduct = view.findViewById(R.id.imageViewProduct);

            // Obtener los botones de edición y eliminación
            Button buttonEdit = view.findViewById(R.id.buttonEdit);
            Button buttonDelete = view.findViewById(R.id.buttonDelete);

            // Establecer los valores en los campos de la celda.
            textViewCategory.setText(product.getCategoria());
            textViewName.setText(product.getNombreProducto());
            String productName = formatProductName(String.valueOf(product.getNombreProducto()));
            textViewPrice.setText(String.valueOf(product.getPrecioUnidad()));
            textViewAmount.setText(String.valueOf(product.getCantidadStock()));
            showImage(imageProduct, ProductsName.valueOf(productName));

            // Asignar listeners a los botones
            buttonEdit.setOnClickListener(v -> {
                getNewProduct(product);
            });

            buttonDelete.setOnClickListener(v -> {
                if (mContext instanceof ListProductsActivity) {
                    ((ListProductsActivity) mContext).deleteProduct(product);
                }
            });
        }
        return view;
    }

    public void getNewProduct( Producto product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.update_product, null);
        EditText editTextAmountEdit = dialogView.findViewById(R.id.editTextAmountEdit);
        EditText editTextPriceEdit = dialogView.findViewById(R.id.editTextPriceEdit);

        ImageView imageProduct = dialogView.findViewById(R.id.imageModifProduct);
        showImage(imageProduct, ProductsName.valueOf(formatProductName(String.valueOf(product.getNombreProducto()))));
        builder.setView(dialogView);
        builder.setTitle("Edicion Producto");
        builder.setMessage("Ingresa los datos del producto");
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            product.setCantidadStock( Integer.parseInt(editTextAmountEdit.getText().toString()));
            product.setPrecioUnidad( Integer.parseInt(editTextPriceEdit.getText().toString()));
            if (mContext instanceof ListProductsActivity) {
                ((ListProductsActivity) mContext).updateProduct(product);
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void clear() {
        super.clear();
        internalProducts.clear();
    }

    @Override
    public void addAll(@NonNull Collection<? extends Producto> collection) {
        super.clear();
        internalProducts.clear();
        internalProducts.addAll(collection);
        notifyDataSetChanged();
    }

    private String formatProductName(String productName) {
        String productStringFormated = productName.toUpperCase();
        return productStringFormated.replace(" ", "_");
    }

    private void showImage(ImageView imageProduct, ProductsName product) {
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
