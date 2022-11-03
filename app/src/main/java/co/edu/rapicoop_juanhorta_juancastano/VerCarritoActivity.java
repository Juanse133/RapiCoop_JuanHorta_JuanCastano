package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerCarritoActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    ProductDatabaseHelper miDB;
    CartDatabaseHelper miDBCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carrito);

        miDB = new ProductDatabaseHelper(this);
        miDB.initData();

        miDBCarrito = new CartDatabaseHelper(this);
        miDBCarrito.initData();

        List<Producto> productos = new ArrayList<>(Arrays.asList(getProductos()));

        ArrayAdapter<String> adapter = new ArrayAdapter(VerCarritoActivity.this, android.R.layout.simple_list_item_1, productos);
        ListView listview = findViewById(R.id.ProductsList);
        listview.setAdapter(adapter);
        listview.setClickable(true);
       /* listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Producto producto = (Producto) listview.getItemAtPosition(position);
                goToDescription(producto);
            }
        });*/
    }

    public void addAddress(View view){
        Intent intent = new Intent(this, AgregarDireccion.class);
        startActivity(intent);
    }

    public Producto[] getProductos() {

        Double totalPrecio = 0.0;

        Cursor cursorCarrito = miDBCarrito.getDataByClientEmail(getIntent().getStringExtra(EXTRA_MESSAGE));
        Producto[] productos = new Producto[cursorCarrito.getCount()];

        try {

            int cont = 0;

            while(cursorCarrito.moveToNext()){
                int colProductID = cursorCarrito.getColumnIndex("PRODUCT_ID");
                int productID = Integer.parseInt(cursorCarrito.getString(colProductID));
                int colQUANTITY = cursorCarrito.getColumnIndex("QUANTITY");
                int QUANTITY = Integer.parseInt(cursorCarrito.getString(colQUANTITY));

                Cursor cursor = miDB.getDataByID(productID);

                cursor.moveToFirst();

                int colID = cursor.getColumnIndex("ID");
                int ID = Integer.parseInt(cursor.getString(colID));
                int colNAME = cursor.getColumnIndex("NAME");
                String NAME = cursor.getString(colNAME);
                int colDESC = cursor.getColumnIndex("DESCRIPTION");
                String DESC = cursor.getString(colDESC);
                int colPRICE = cursor.getColumnIndex("PRICE");
                Double PRICE = Double.parseDouble(cursor.getString(colPRICE));
                int colEMAIL = cursor.getColumnIndex("EMAIL");
                String EMAIL = cursor.getString(colEMAIL);
                int colTAGS = cursor.getColumnIndex("TAGS");
                String TAGS = cursor.getString(colTAGS);

                totalPrecio += PRICE * QUANTITY;

                Producto producto = new Producto(ID, NAME, DESC, PRICE, QUANTITY, EMAIL, TAGS);

                productos[cont++] = producto;

                cursor.close();
            }

        } finally {
            cursorCarrito.close();
        }

        ((TextView) findViewById(R.id.totalTxt)).setText("Total: $" + totalPrecio);

        return productos;
    }

}