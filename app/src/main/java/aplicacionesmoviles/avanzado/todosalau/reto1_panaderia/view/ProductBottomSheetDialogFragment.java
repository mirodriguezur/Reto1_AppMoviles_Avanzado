package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.R;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.InfoPartialOrder;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.OrderManagerPresenter;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.presenter.ProductsName;

public class ProductBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private int amountProduct;
    private static final String ARG_PRODUCT = "product";
    private Producto producto;
    private EditText amountProductTextView;
    private OrderManagerPresenter orderManagerPresenter;

    public static ProductBottomSheetDialogFragment newInstance(Producto producto) {
        ProductBottomSheetDialogFragment fragment = new ProductBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, producto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottom_sheet, container, false);

        // Accede a las vistas y actualiza con la información del producto
        TextView productNameTextView = view.findViewById(R.id.txtNameProductBottomSheet);
        TextView productPriceTextView = view.findViewById(R.id.txtProductCostBottomSheet);
        TextView descriptionProductTextView = view.findViewById(R.id.txtDescriptionProductBottomSheet);
        RoundedImageView productImageView = view.findViewById(R.id.imageProductBottomSheet);
        Button addProductButton = view.findViewById(R.id.buttonAddProductBottomSheet);
        Button decreaseProductButton = view.findViewById(R.id.buttonDecreaseProductBottomSheet);
        Button addToCartButton = view.findViewById(R.id.buttonAddProductToCart);
        amountProductTextView = view.findViewById(R.id.txtAmountNumberBottomSheet);
        amountProduct = Integer.parseInt(amountProductTextView.getText().toString());

        if (producto != null) {
            productNameTextView.setText(producto.getNombreProducto());
            int cost = producto.getPrecioUnidad();
            productPriceTextView.setText(String.valueOf(cost));
            descriptionProductTextView.setText(producto.getDescripcion());
            String productName = formatProductName(String.valueOf(producto.getNombreProducto()));
            showImage(productImageView, ProductsName.valueOf(productName));

            addProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    amountProduct++;
                    amountProductTextView.setText(String.valueOf(amountProduct));
                }
            });

            decreaseProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (amountProduct > 0) {
                        amountProduct--;
                        amountProductTextView.setText(String.valueOf(amountProduct));
                    }
                }
            });

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (amountProduct > 0) {
                        InfoPartialOrder partialOrder = new InfoPartialOrder(producto.getIdProducto(), producto.getCategoria(), producto.getNombreProducto(), amountProduct, producto.getPrecioUnidad());
                        orderManagerPresenter.savePartialOrder(partialOrder);
                        dismiss();
                    } else {
                        // TODO: Mostrar mensaje de que toca agregar al menos un producto.
                    }
                }
            });
        }

        return view;
    }

    public void setOrderManagerPresenter(OrderManagerPresenter orderManagerPresenter) {
        this.orderManagerPresenter = orderManagerPresenter;
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
