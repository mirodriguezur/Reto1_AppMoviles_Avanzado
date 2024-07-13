package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model.Producto;
import aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.R;

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
            // Obtener las vistas de nombre y precio
            TextView textViewCategory = view.findViewById(R.id.textInfoCategory);
            TextView textViewName = view.findViewById(R.id.textInfoProducName);
            TextView textViewPrice = view.findViewById(R.id.textInfoCost);
            TextView textViewAmount = view.findViewById(R.id.textInfoAmount);

            // Obtener los botones de edición y eliminación
            Button buttonEdit = view.findViewById(R.id.buttonEdit);
            Button buttonDelete = view.findViewById(R.id.buttonDelete);

            // Establecer el nombre y el precio del producto en los TextView
            textViewCategory.setText(product.getCategoria());
            textViewName.setText(product.getNombreProducto());
            textViewPrice.setText(String.valueOf(product.getPrecioUnidad()));
            textViewAmount.setText(String.valueOf(product.getCantidadStock()));

            // Asignar listeners a los botones
            buttonEdit.setOnClickListener(v -> {
                // TODO: (Michael) manejar la logica de modificar producto

            });

            buttonDelete.setOnClickListener(v -> {
                // TODO: (Michael) manejar la logica de eliminar producto
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
}
