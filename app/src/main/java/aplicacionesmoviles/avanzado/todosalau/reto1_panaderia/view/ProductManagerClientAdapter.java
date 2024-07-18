package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.R;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;

public class ProductManagerClientAdapter extends ArrayAdapter<Producto> {

    private final List<Producto> internalProducts;
    // Recursos de diseño y contexto
    private int resourceLayout;
    private Context mContext;

    public ProductManagerClientAdapter(Context context, int resource, List<Producto> items) {
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
            TextView textViewName = view.findViewById(R.id.nameProductTextView);
            TextView textViewPrice = view.findViewById(R.id.priceProductTextView);
            ImageView imageProduct = view.findViewById(R.id.imageProductClient);

            // Obtener los botones de edición y eliminación
            FloatingActionButton buttonAdd = view.findViewById(R.id.addProductButton);

            // Establecer los valores en los campos de la celda.
            textViewName.setText(product.getNombreProducto());
            String productName = formatProductName(String.valueOf(product.getNombreProducto()));
            textViewPrice.setText(String.valueOf(product.getPrecioUnidad()));
            showImage(imageProduct, ProductsName.valueOf(productName));

            // Asignar listeners a los botones
            buttonAdd.setOnClickListener(v -> {
                //TODO: Agregar al carrito
            });
        }
        return view;
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
            case AROMATICA:
                imageProduct.setImageResource(R.drawable.bebida_aromatica);
            case CAFE:
                imageProduct.setImageResource(R.drawable.bebida_cafe);
                break;
            case COCACOLA:
                imageProduct.setImageResource(R.drawable.bebida_cocacola);
                break;
            case COLOMBIANA:
                imageProduct.setImageResource(R.drawable.bebida_colombiana);
                break;
            case AGUA_CON_GAS:
                imageProduct.setImageResource(R.drawable.bebida_h2o);
                break;
            case AGUA_SIN_GAS:
                imageProduct.setImageResource(R.drawable.bebida_h2o);
                break;
            case JUGO_HIT:
                imageProduct.setImageResource(R.drawable.bebida_hit);
                break;
            default:
                imageProduct.setImageResource(R.drawable.ic_arrow_back);
        }
    }
}
