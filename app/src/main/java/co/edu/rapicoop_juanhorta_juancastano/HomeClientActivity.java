package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class HomeClientActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    ProductDatabaseHelper miDB;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        miDB = new ProductDatabaseHelper(this);
        miDB.initData();

        listview = findViewById(R.id.ProductsList);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Producto producto = (Producto) listview.getItemAtPosition(position);
                goToDescription(producto);
            }
        });
    }

    public void goToDescription(Producto producto){
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        intent.putExtra(ProductDescriptionActivity.EXTRA_MESSAGE, producto.getID().toString());
        startActivity(intent);
    }

    public void onClickSearch(View view) {
        String name = ((EditText) findViewById(R.id.txtBuscar)).getText().toString();

        if (name.equals(""))
            return;

        List<Producto> productos = new ArrayList<>(Arrays.asList(getProductos(name)));

        ArrayAdapter<String> adapter = new ArrayAdapter(HomeClientActivity.this, android.R.layout.simple_list_item_1, productos);
        listview.setAdapter(adapter);
        listview.setClickable(true);
    }

    public Producto[] getProductos(String name) {
        Cursor cursor = miDB.getDataByName(name);
        Producto[] productos = new Producto[cursor.getCount()];

        try {

            int cont = 0;

            while (cursor.moveToNext()) {
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

                productos[cont++] = producto;
            }
        } finally {
            cursor.close();
        }
        return productos;
    }
}