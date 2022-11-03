package co.edu.rapicoop_juanhorta_juancastano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AgregarDireccion extends AppCompatActivity {

    Button btnLocation;
    TextView tv1, tv2, tv3, tv4, tv5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_direccion);

        btnLocation = findViewById(R.id.btnLocation);
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

                                tv1.setText("Latitud: " + (double) addresses.get(0).getLatitude());
                                tv2.setText("Longitud: " + (double) addresses.get(0).getLongitude());
                                tv3.setText(addresses.get(0).getCountryName());
                                tv4.setText(addresses.get(0).getLocality());
                                tv5.setText(addresses.get(0).getAddressLine(0));
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
}