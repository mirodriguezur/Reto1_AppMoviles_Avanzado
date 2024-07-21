package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.content.Context;

public class OrderDAO implements OrderDAOInterface {
    private DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Métodos para operaciones CRUD (Create, Read, Update, Delete)
}
