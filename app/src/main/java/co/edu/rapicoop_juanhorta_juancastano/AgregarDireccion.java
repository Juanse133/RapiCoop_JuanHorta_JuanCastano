package co.edu.rapicoop_juanhorta_juancastano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOError;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgregarDireccion extends AppCompatActivity {

    Button btnLocation, btnMap, btnComprar;
    TextView tv1, tv2, tv3, tv4, tv5;
    FusedLocationProviderClient fusedLocationProviderClient;
    double lat = 0.0, lon = 0.0;

    CartDatabaseHelper miCart;
    CompraDatabaseHelper miCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_direccion);

        miCart = new CartDatabaseHelper(this);
        miCompra = new CompraDatabaseHelper(this);
        miCart.initData();
        miCompra.initData();

        btnLocation = findViewById(R.id.btnLocation);
        btnMap = findViewById(R.id.btnMap);
        btnComprar = findViewById(R.id.btnComprar);
        tv1 = findViewById(R.id.tx1);
        tv2 = findViewById(R.id.tx2);
        tv3 = findViewById(R.id.tx3);
        tv4 = findViewById(R.id.tx4);
        tv5 = findViewById(R.id.tx5);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
    }

    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(AgregarDireccion.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                  location.getLatitude(), location.getLongitude(),1
                                );

                                lat = (double) addresses.get(0).getLatitude();
                                lon = (double) addresses.get(0).getLongitude();

                                tv1.setText("Latitud: " + lat);
                                tv2.setText("Longitud: " + lon);
                                tv3.setText(addresses.get(0).getCountryName());
                                tv4.setText(addresses.get(0).getLocality());
                                tv5.setText(addresses.get(0).getAddressLine(0));
                                btnMap.setVisibility(View.VISIBLE);
                                btnComprar.setVisibility(View.VISIBLE);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ActivityCompat.requestPermissions(AgregarDireccion.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                        }
                    }
                }
        );
    }

    public void onSendMaps(View view){
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + lat + "," + lon);
        Intent mapIntent= new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    /*public void onSendMaps(View view){
        Uri.Builder builder= new Uri.Builder();
        builder.scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1").appendQueryParameter("destination", 4.632339710 + "," + -74.065350);
        String url = builder.build().toString();
        Log.d("Directions", url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }*/

    public void realizarCompra(View view){

        String email = getIntent().getStringExtra("email");
        String productsid = "";

        Cursor cursorCarrito = miCart.getDataByClientEmail(email);

        try {
            while(cursorCarrito.moveToNext()){
                int colProductID = cursorCarrito.getColumnIndex("PRODUCT_ID");
                int productID = Integer.parseInt(cursorCarrito.getString(colProductID));
                productsid += productID + ",";
            }
        } finally {
            cursorCarrito.close();
        }

        Compra compra = new Compra(0, productsid, email, lat + ", " + lon, "pending", (new Date()).toString());
        boolean resultado = miCompra.insertData(compra);
        if(resultado){
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Producto creado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));
            miCart.deleteDataByEmail(email);
        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }


    }
}