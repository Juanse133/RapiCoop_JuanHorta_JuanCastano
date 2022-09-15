package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        ((TextView) findViewById(R.id.welcomeMessage)).setText("Bienvenido " + intent.getStringExtra(EXTRA_MESSAGE));
    }
}