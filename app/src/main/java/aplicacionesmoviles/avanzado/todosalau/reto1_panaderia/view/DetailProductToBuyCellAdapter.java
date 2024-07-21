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
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.InfoPartialOrder;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;

public class DetailProductToBuyCellAdapter extends ArrayAdapter<InfoPartialOrder> {
    private final List<InfoPartialOrder> infoProducts;
    // Recursos de diseño y contexto
    private int resourceLayout;
    private Context mContext;

    public DetailProductToBuyCellAdapter(Context context, int resource, List<InfoPartialOrder> items) {
        super(context, resource, new ArrayList<>());
        this.resourceLayout = resource;
        this.mContext = context;
        infoProducts = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return infoProducts.size();
    }

    @Override
    public InfoPartialOrder getItem(int position) {
        return infoProducts.get(position);
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
        InfoPartialOrder infoProduct = getItem(position);

        // Si el producto es válido, establecer los valores en la vista
        if (infoProduct != null) {
            // Obtener las vistas de la celda
            TextView textViewName = view.findViewById(R.id.nameProductToBuyTextView);
            TextView textViewPrice = view.findViewById(R.id.unitPriceProductToBuyTextView);
            TextView textViewAmount = view.findViewById(R.id.amountProductToBuyTextView);
            TextView textViewTotalPrice = view.findViewById(R.id.totalPriceProductToBuyTextView);
            ImageView imageProduct = view.findViewById(R.id.imageDetailProductToBuy);

            // Obtener los botones de edición y eliminación
            FloatingActionButton deleteButton = view.findViewById(R.id.deleteProductToBuyFloatingButton);

            // Establecer los valores en los campos de la celda.
            textViewName.setText(infoProduct.getNameProduct());
            String productName = formatProductName(String.valueOf(infoProduct.getNameProduct()));
            textViewPrice.setText(String.valueOf(infoProduct.getUnitPrice()));
            textViewAmount.setText(String.valueOf(infoProduct.getAmount()));
            textViewTotalPrice.setText(String.valueOf(infoProduct.getAmount() * infoProduct.getUnitPrice()));
            showImage(imageProduct, ProductsName.valueOf(productName));

            deleteButton.setOnClickListener(v -> {
                // TODO: Borrar elemento de la lista.
            });
        }
        return view;
    }

    @Override
    public void clear() {
        super.clear();
        infoProducts.clear();
    }

    @Override
    public void addAll(@NonNull Collection<? extends InfoPartialOrder> collection) {
        super.clear();
        infoProducts.clear();
        infoProducts.addAll(collection);
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
