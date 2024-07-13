package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

public class Producto {
    private int idProducto;
    private String categoria;
    private String nombreProducto;
    private int precioUnidad;
    private int cantidadStock;

    // Constructor que inicializa un producto con todos sus atributos
    public Producto(int idProducto, String categoria, String nombreProducto, int precioUnidad, int cantidadStock) {
        this.idProducto = idProducto;
        this.categoria = categoria;
        this.nombreProducto = nombreProducto;
        this.precioUnidad = precioUnidad;
        this.cantidadStock = cantidadStock;
    }

    // Constructor vacío (por defecto)
    public Producto() {
        // Se deja vacío
    }

    // Métodos setters y getters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
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
}
