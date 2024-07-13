package aplicacionesmoviles.avanzado.todosalau.reto1_panaderia.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "panappetitBaseDeDatos";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear las tablas de datos
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE producto (id_producto INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT NOT NULL, nombre_producto TEXT NOT NULL, precio_unidad INTEGER NOT NULL, cantidad_stock INTEGER NOT NULL)";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE usuario (cedula INTEGER PRIMARY KEY, nombre TEXT NOT NULL, apellido TEXT NOT NULL, numero_telefonico INTEGER NOT NULL, direccion TEXT NOT NULL, es_administrador BOOLEAN NOT NULL, correo TEXT UNIQUE NOT NULL, contraseña TEXT NOT NULL)";
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE pedido (id_pedido INTEGER PRIMARY KEY AUTOINCREMENT, cedula_usuario INTEGER NOT NULL, fecha TEXT NOT NULL, total_costo INTEGER NOT NULL, FOREIGN KEY (cedula_usuario) REFERENCES usuario(cedula))";
    private static final String CREATE_TABLE_ORDERSPRODUCTS = "CREATE TABLE pedidoproducto (id_pedido INTEGER NOT NULL, id_producto INTEGER NOT NULL, cantidad_producto INTEGER NOT NULL, precio_unitario INTEGER NOT NULL, subtotal INTEGER NOT NULL, PRIMARY KEY (id_pedido, id_producto), FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido), FOREIGN KEY (id_producto) REFERENCES producto(id_producto))";

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
