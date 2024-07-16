package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements ProductoDAOInterface {
    private DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    public ProductoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Métodos para operaciones CRUD (Create, Read, Update, Delete)
    // Método para insertar un nuevo producto en la base de datos
    public long insertProduct(Producto product) {
        // Verificar si el producto ya existe antes de agregarlo
        /*if (productExists(product.getNombreProducto())) {
            // El producto ya existe, no se hace nada
            Log.i("ProductDatabaseHelper", "Producto con ID " + product.getNombreProducto() + " ya existe.");
            return;
        }*/
        SQLiteDatabase db = null;
        long newRowId;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("categoria", product.getCategoria());
            values.put("nombreProducto", product.getNombreProducto());
            values.put("precioUnidad", product.getPrecioUnidad());
            values.put("cantidadStock", product.getCantidadStock());
            return db.insert("producto", null, values);
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error al agregar producto", e);
            return -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Método para obtener todos los productos de la base de datos
    public List<Producto> getAllProducts() {
        List<Producto> products = new ArrayList<>(); // Lista para almacenar los productos obtenidos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM producto", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Obtener datos de cada producto
                Producto product = new Producto();
                product.setIdProducto(cursor.getString(0));
                product.setCategoria(cursor.getString(1));
                product.setNombreProducto(cursor.getString(2));
                product.setPrecioUnidad(cursor.getInt(3));
                product.setCantidadStock(cursor.getInt(4));
                products.add(product);
            } while (cursor.moveToNext());
        }
        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return products;
    }

    // Método para actualizar un producto en la base de datos
    public void updateProduct(String idProduct, String category, String productName, int price, int amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("categoria", category);
        values.put("nombreProducto", productName);
        values.put("precioUnidad", price);
        values.put("cantidadStock", amount);

        // Actualizar el producto en la base de datos
        int rowsUpdated = db.update("producto", values, "idProducto" + "=?", new String[]{idProduct});
        db.close();

        // Verificar si la actualización fue exitosa
        if (rowsUpdated == 0) {
            Log.e("ProductDatabaseHelper", "No se actualizó ninguna fila. ID de producto inválido.");
        }
    }

    // Método para eliminar un producto de la base de datos
    public void deleteProduct(String id) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            // Eliminar el producto
            int rowsDeleted = db.delete("producto", "idProducto" + "=?", new String[]{id});

            // Verificar si la eliminación fue exitosa
            if (rowsDeleted > 0) {
                Log.i("ProductDatabaseHelper", "Producto eliminado exitosamente.");
            } else {
                Log.e("ProductDatabaseHelper", "No se eliminó ningún producto. ID no válido: " + id);
            }
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error al eliminar el producto", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Metodo para Modificar un producto de la BD
    public void updateProduct(Producto product) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("categoria", product.getCategoria());
            values.put("nombreProducto", product.getNombreProducto());
            values.put("precioUnidad", product.getPrecioUnidad());
            values.put("cantidadStock", product.getCantidadStock());
            db.update("producto", values, "idProducto" + "=?", new String[]{product.getIdProducto()});
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error al modificar el producto: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Método para verificar si un producto existe en la base de datos
    /*public boolean productExists(String id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;
        try {
            db = dbHelper.getReadableDatabase();
            // Consulta para verificar si el producto existe
            cursor = db.rawQuery("SELECT COUNT(*) FROM producto"  + " WHERE idProducto" + "=?", new String[]{id});
            // Si el cursor tiene datos, obtener el conteo
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                exists = count > 0;
            }
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error al verificar si el producto existe: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return exists;
    }*/
}
