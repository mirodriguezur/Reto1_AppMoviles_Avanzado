package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "panappetitBaseDeDatos";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear las tablas de datos
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE producto (idProducto INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT NOT NULL, nombreProducto TEXT NOT NULL, precioUnidad INTEGER NOT NULL, cantidadStock INTEGER NOT NULL)";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE usuario (cedula INTEGER PRIMARY KEY, nombre TEXT NOT NULL, apellido TEXT NOT NULL, numeroTelefonico INTEGER NOT NULL, direccion TEXT NOT NULL, esAdministrador BOOLEAN NOT NULL, correo TEXT UNIQUE NOT NULL, password TEXT NOT NULL)";
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE pedido (idPedido INTEGER PRIMARY KEY AUTOINCREMENT, cedulaUsuario INTEGER NOT NULL, fecha TEXT NOT NULL, totalCosto INTEGER NOT NULL, FOREIGN KEY (cedulaUsuario) REFERENCES usuario(cedula))";
    private static final String CREATE_TABLE_ORDERSPRODUCTS = "CREATE TABLE pedidoproducto (idPedido INTEGER NOT NULL, idProducto INTEGER NOT NULL, cantidadProducto INTEGER NOT NULL, precioUnitario INTEGER NOT NULL, subtotal INTEGER NOT NULL, PRIMARY KEY (idPedido, idProducto), FOREIGN KEY (idPedido) REFERENCES pedido(idPedido), FOREIGN KEY (idProducto) REFERENCES producto(idProducto))";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // SQLiteOpenHelper requiere inicializar el super.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_ORDERSPRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS pedido");
        db.execSQL("DROP TABLE IF EXISTS pedidoproducto");

        // Se crea la nueva versión de la tabla
        onCreate(db);
    }
}
