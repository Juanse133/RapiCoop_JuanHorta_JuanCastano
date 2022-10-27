package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeClientActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    ProductDatabaseHelper miDB;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.verCarrito:
                Intent intent = new Intent(this, VerCarritoActivity.class);
                intent.putExtra(CRUDProductActivity.EXTRA_MESSAGE, this.getIntent().getStringExtra(EXTRA_MESSAGE));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToDescription(Producto producto){
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        intent.putExtra(ProductDescriptionActivity.EXTRA_MESSAGE, producto.getID().toString());
        intent.putExtra("ClientEmail", this.getIntent().getStringExtra(EXTRA_MESSAGE));
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