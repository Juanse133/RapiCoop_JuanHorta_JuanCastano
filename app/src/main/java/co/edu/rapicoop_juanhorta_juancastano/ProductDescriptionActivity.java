package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ProductDescriptionActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    ProductDatabaseHelper miDB;
    CartDatabaseHelper miDBCarrito;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        miDB = new ProductDatabaseHelper(this);
        miDB.initData();

        miDBCarrito = new CartDatabaseHelper(this);
        miDBCarrito.initData();

        producto = getProduct(Integer.parseInt(getIntent().getStringExtra(EXTRA_MESSAGE)));
        setValues(producto);
    }

    public void setValues(Producto producto){

        TextView name = (TextView) findViewById(R.id.pName);
        name.setText(producto.getNAME());
        TextView description = (TextView) findViewById(R.id.pDescription);
        description.setText(producto.getDESCRIPTION());
        TextView price = (TextView) findViewById(R.id.pPrice);
        price.setText("$" + producto.getPRICE().toString());
        TextView tags = (TextView) findViewById(R.id.pTags);
        tags.setText("Tags: " + producto.getTAGS());

        NumberPicker quantityPicker = (NumberPicker) findViewById(R.id.quantityPicker);
        quantityPicker.setMinValue(1);
        quantityPicker.setMaxValue(producto.getQUANTITY());
    }

    public Producto getProduct(int ID){

        Cursor cursor = miDB.getDataByID(ID);

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

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

    public void addToCart(View view){

        NumberPicker quantityPicker = (NumberPicker) findViewById(R.id.quantityPicker);

        boolean resultado = miDBCarrito.insertData(new Carrito(0, producto.getID(), quantityPicker.getValue(), this.getIntent().getStringExtra("ClientEmail")));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Producto creado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }

    }
}