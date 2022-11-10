package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

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

public class VerPeticionActivity extends AppCompatActivity {

    ProductDatabaseHelper miDB;
    CompraDatabaseHelper miCompra;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_peticion);

        miDB = new ProductDatabaseHelper(this);
        miDB.initData();

        miCompra = new CompraDatabaseHelper(this);
        miCompra.initData();

        List<Producto> productos = new ArrayList<>(Arrays.asList(getProductos()));

        ArrayAdapter<String> adapter = new ArrayAdapter(VerPeticionActivity.this, android.R.layout.simple_list_item_1, productos);
        ListView listview = findViewById(R.id.productosPedidos);
        listview.setAdapter(adapter);
    }

    public Producto[] getProductos() {

        Cursor cursorCompra = miCompra.getDataByID(Integer.parseInt(getIntent().getStringExtra("compraID")));

        cursorCompra.moveToFirst();

        int colNAME = cursorCompra.getColumnIndex("PRODUCTS_ID");
        String productosID = cursorCompra.getString(colNAME);
        int colEMAIL = cursorCompra.getColumnIndex("CLIENT_EMAIL");
        String client = cursorCompra.getString(colEMAIL);

        ((TextView) findViewById(R.id.cliente)).setText("Comprador: " + client);

        String[] ids = productosID.split(",");

        Producto[] productos = new Producto[ids.length];

        for (int i = 0; i < ids.length; i++) {
            productos[i] = getProducto(ids[i]);
            System.out.println(productos[i].toString());
        }


        return productos;
    }

    public Producto getProducto(String id){
        Cursor cursor = miDB.getDataByID(Integer.parseInt(id));

        cursor.moveToFirst();

        int colID = cursor.getColumnIndex("ID");
        int ID = Integer.parseInt(cursor.getString(colID));
        int colNAME = cursor.getColumnIndex("NAME");
        String NAME = cursor.getString(colNAME);
        int colDESC = cursor.getColumnIndex("DESCRIPTION");
        String DESC = cursor.getString(colDESC);
        int colPRICE = cursor.getColumnIndex("PRICE");
        Double PRICE = Double.parseDouble(cursor.getString(colPRICE));
        int colQUANTITY = cursor.getColumnIndex("QUANTITY");
        int QUANTITY = Integer.parseInt(cursor.getString(colQUANTITY));
        int colEMAIL = cursor.getColumnIndex("EMAIL");
        String EMAIL = cursor.getString(colEMAIL);
        int colTAGS = cursor.getColumnIndex("TAGS");
        String TAGS = cursor.getString(colTAGS);

        Producto producto = new Producto(ID, NAME, DESC, PRICE, QUANTITY, EMAIL, TAGS);

        return producto;
    }

    public void aceptarSolicitud(View view){

        boolean resultado = miCompra.updateData(new Compra(Integer.parseInt(getIntent().getStringExtra("compraID")),"","","","Confirmado", ""));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Compra aceptada correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }
    }
}