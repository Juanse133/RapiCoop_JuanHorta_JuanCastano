package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CRUDProductActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    ProductDatabaseHelper miDB;
    Producto producto;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudproduct);
        miDB = new ProductDatabaseHelper(this);
        miDB.initData();
        ID = Integer.parseInt(getIntent().getStringExtra(EXTRA_MESSAGE));
        producto = getProduct(ID);
        setValues(producto);
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

    public void setValues(Producto producto){

        EditText etName = (EditText) findViewById(R.id.productName);
        EditText etDesc = (EditText) findViewById(R.id.productDescription);
        EditText etPrice = (EditText) findViewById(R.id.price);
        EditText etQuantity = (EditText) findViewById(R.id.quantity);
        EditText etTags = (EditText) findViewById(R.id.tags);

        etName.setText(producto.getNAME());
        etDesc.setText(producto.getDESCRIPTION());
        etPrice.setText(producto.getPRICE().toString());
        etQuantity.setText(producto.getQUANTITY().toString());
        etTags.setText(producto.getTAGS());
    }

    public void updateProduct(View view){

        String name = ((EditText) findViewById(R.id.productName)).getText().toString();
        String description = ((EditText) findViewById(R.id.productDescription)).getText().toString();
        String priceStr = ((EditText) findViewById(R.id.price)).getText().toString();
        String quantityStr = ((EditText) findViewById(R.id.quantity)).getText().toString();
        String tags = ((EditText) findViewById(R.id.tags)).getText().toString();
        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);


        boolean resultado = miDB.updateData(new Producto(ID, name, description, price, quantity, "", tags));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Producto actualizado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void deleteProduct(View view){

        boolean resultado = miDB.deleteData(new Producto(ID, "", "", 0.0, 0, "", ""));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Producto eliminado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }
    }
}