package co.edu.rapicoop_juanhorta_juancastano;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PublishProductActivity extends AppCompatActivity {
    ProductDatabaseHelper miDB;
    public static final String EXTRA_MESSAGE = "mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_product);
        miDB = new ProductDatabaseHelper(this);
        miDB.initData();
    }

    public void registerProduct(View view) {
        String name = ((EditText) findViewById(R.id.productName)).getText().toString();
        String description = ((EditText) findViewById(R.id.productDescription)).getText().toString();
        String priceStr = ((EditText) findViewById(R.id.price)).getText().toString();
        String quantityStr = ((EditText) findViewById(R.id.quantity)).getText().toString();
        String tags = ((EditText) findViewById(R.id.tags)).getText().toString();
        Intent intent = getIntent();
        String email = intent.getStringExtra(EXTRA_MESSAGE);
        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        System.out.println(name+" "+description+" "+email+" "+price+" "+quantity);

        boolean resultado = miDB.insertData(new Producto(0, name, description, price, quantity, email, tags));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Producto creado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }

    }
}