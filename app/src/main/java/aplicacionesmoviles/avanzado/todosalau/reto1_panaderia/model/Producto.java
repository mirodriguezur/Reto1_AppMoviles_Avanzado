package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Producto implements Parcelable {
    private String idProducto;
    private String categoria;
    private String nombreProducto;
    private String descripcion;
    private int precioUnidad;
    private int cantidadStock;

    // Constructor que inicializa un producto con todos sus atributos
    public Producto(String categoria, String nombreProducto, String descripcion, int precioUnidad, int cantidadStock) {
        this.categoria = categoria;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precioUnidad = precioUnidad;
        this.cantidadStock = cantidadStock;
    }

    // Constructor vacío (por defecto)
    public Producto() {
        // Se deja vacío
    }

    // Métodos setters y getters
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(int precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    // Implementación de Parcelable
    protected Producto(Parcel in) {
        idProducto = in.readString();
        categoria = in.readString();
        nombreProducto = in.readString();
        descripcion = in.readString();
        precioUnidad = in.readInt();
        cantidadStock = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(idProducto);
        dest.writeString(categoria);
        dest.writeString(nombreProducto);
        dest.writeString(descripcion);
        dest.writeInt(precioUnidad);
        dest.writeInt(cantidadStock);
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    // Convierte las propiedades del producto en un mapa para almacenamiento o envío
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("idProducto", idProducto); // Agrega el ID al mapa
        result.put("categoria", categoria); // Agrega la categoría al mapa
        result.put("nombreProducto", nombreProducto); // Agrega el nombre del producto al mapa
        result.put("descripcion", descripcion); // Agrega la descripción al mapa
        result.put("precioUnidad", precioUnidad); // Agrega la cantidad en stock al mapa
        result.put("cantidadStock", cantidadStock); // Agrega la cantidad en stock al mapa
        return result; // Devuelve el mapa con las propiedades del producto
    }
}
