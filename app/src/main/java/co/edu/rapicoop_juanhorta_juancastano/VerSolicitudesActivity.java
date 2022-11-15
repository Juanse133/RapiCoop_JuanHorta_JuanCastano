package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerSolicitudesActivity extends AppCompatActivity {

    CompraDatabaseHelper miDB;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_solicitudes);

        miDB = new CompraDatabaseHelper(this);
        miDB.initData();

        List<Compra> compras = new ArrayList<>(Arrays.asList(getCompras()));

        ArrayAdapter<String> adapter = new ArrayAdapter(VerSolicitudesActivity.this, android.R.layout.simple_list_item_1, compras);
        ListView listview = findViewById(R.id.solicitudesList);
        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Compra compra = (Compra) listview.getItemAtPosition(position);
                goToDescription(compra);
            }
        });
    }

    private Compra[] getCompras() {
        Cursor cursor = miDB.getAllData();
        Compra[] compras = new Compra[cursor.getCount()];

        try {

            int cont = 0;

            while (cursor.moveToNext()) {
                int colID = cursor.getColumnIndex("ID");
                int ID = Integer.parseInt(cursor.getString(colID));
                int colProd = cursor.getColumnIndex("PRODUCTS_ID");
                String PRODUCTS_ID = cursor.getString(colProd);
                int colEmail = cursor.getColumnIndex("CLIENT_EMAIL");
                String CLIENT_EMAIL = cursor.getString(colEmail);
                int colAddress = cursor.getColumnIndex("ADDRESS");
                String ADDRESS = cursor.getString(colAddress);
                int colStatus = cursor.getColumnIndex("STATUS");
                String STATUS = cursor.getString(colStatus);
                int colDate = cursor.getColumnIndex("DATE");
                String DATE = cursor.getString(colDate);
                int colQuant = cursor.getColumnIndex("QUANTITIES");
                String QUANTITIES = cursor.getString(colQuant);
                int colDealer = cursor.getColumnIndex("DEALER");
                String DEALER = cursor.getString(colDealer);
                int colPrice = cursor.getColumnIndex("PRICE");
                double PRICE = Double.parseDouble(cursor.getString(colPrice));

                Compra compra = new Compra(ID, PRODUCTS_ID, CLIENT_EMAIL, ADDRESS, STATUS, DATE, QUANTITIES, DEALER, PRICE);

                compras[cont++] = compra;
            }
        } finally {
            cursor.close();
        }
        return compras;
    }

    public void goToDescription(Compra compra){
        Intent intent = new Intent(this, VerPeticionActivity.class);
        intent.putExtra("compraID", compra.getID().toString());
        startActivity(intent);
    }
}