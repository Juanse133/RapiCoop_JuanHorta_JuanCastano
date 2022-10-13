package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeClientActivity extends AppCompatActivity {

    ProductDatabaseHelper miDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        miDB = new ProductDatabaseHelper(this);
        miDB.initData();
    }

    public void onClickSearch(View view){
        String name = ((EditText) findViewById(R.id.txtBuscar)).getText().toString();
        Cursor cursor = miDB.getDataByName(name);
    }
}