package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeliveryDescriptionActivity extends AppCompatActivity {

    CompraDatabaseHelper miCompra;
    ProductDatabaseHelper miProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_description);

        miCompra = new CompraDatabaseHelper(this);
        miProducto = new ProductDatabaseHelper(this);

        miCompra.initData();
        miProducto.initData();

        List<Producto> productos = new ArrayList<>(Arrays.asList(getProductos()));

        ArrayAdapter<String> adapter = new ArrayAdapter(DeliveryDescriptionActivity.this, android.R.layout.simple_list_item_1, productos);
        ListView listview = findViewById(R.id.ProductsList);
        listview.setAdapter(adapter);
    }

    public Producto[] getProductos() {

        Cursor cursorCompra = miCompra.getDataByID(Integer.parseInt(getIntent().getStringExtra("compraID")));

        cursorCompra.moveToFirst();

        int colNAME = cursorCompra.getColumnIndex("PRODUCTS_ID");
        String productlist = cursorCompra.getString(colNAME);
        int colQuantity = cursorCompra.getColumnIndex("QUANTITIES");
        String quantitylist = cursorCompra.getString(colQuantity);
        int colPrice = cursorCompra.getColumnIndex("PRICE");
        String precio = cursorCompra.getString(colPrice);

        ((TextView) findViewById(R.id.totalTxt)).setText("Total: $" + precio);


        String[] ids = productlist.split(",");

        Producto[] productos = new Producto[ids.length];

        for (int i = 0; i < ids.length; i++) {
            productos[i] = getProducto(ids[i]);
            productos[i].setQUANTITY(Integer.parseInt(quantitylist.split(",")[i]));
            productos[i].setPRICE(productos[i].getPRICE() * Double.parseDouble(quantitylist.split(",")[i]));
        }


        return productos;
    }

    public Producto getProducto(String id){
        Cursor cursor = miProducto.getDataByID(Integer.parseInt(id));

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

    public void onMap(View view){

        Cursor cursorCompra = miCompra.getDataByID(Integer.parseInt(getIntent().getStringExtra("compraID")));

        cursorCompra.moveToFirst();

        int colAddress = cursorCompra.getColumnIndex("ADDRESS");
        String address = cursorCompra.getString(colAddress);

        Uri.Builder builder= new Uri.Builder();
        builder.scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1").appendQueryParameter("destination", 4.717149 + "," + -74.121913);
        String url = builder.build().toString();
        Log.d("Directions", url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void aceptarPedido(View view){

        boolean resultado = miCompra.updateData(new Compra(Integer.parseInt(getIntent().getStringExtra("compraID")),"","","","En Camino", "", "", getIntent().getStringExtra("email"), 0.0));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Compra aceptada correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }
    }
}