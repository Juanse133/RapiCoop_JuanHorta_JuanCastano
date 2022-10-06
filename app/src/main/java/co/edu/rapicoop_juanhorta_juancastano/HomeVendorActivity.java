package co.edu.rapicoop_juanhorta_juancastano;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeVendorActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_vendor);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.welcomeMessage)).setText("Bienvenido " + intent.getStringExtra(EXTRA_MESSAGE));
    }

    public void publishProduct(View view){
        Intent intent = new Intent(this, PublishProductActivity.class);
        intent.putExtra(PublishProductActivity.EXTRA_MESSAGE, EXTRA_MESSAGE);
        startActivity(intent);

    }
}