package co.edu.rapicoop_juanhorta_juancastano;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    LoginDatabaseHelper miDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        miDB = new LoginDatabaseHelper(this);
        miDB.initData();
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
        String role = checkUser(email, password);
        if (role != null) {
            Intent intent;
            switch (role){
                case "Vendedor de comidas":
                    intent = new Intent(LoginActivity.this, HomeVendorActivity.class);
                    intent.putExtra(HomeVendorActivity.EXTRA_MESSAGE, email);
                    break;
                case "Comprador":
                    intent = new Intent(LoginActivity.this, HomeClientActivity.class);
                    intent.putExtra(HomeClientActivity.EXTRA_MESSAGE, email);
                    break;
                default:
                    intent = new Intent();
            }
            startActivity(intent);
        } else {
            ((TextView) findViewById(R.id.errorMessage)).setText("Usuario o contraseña inválida");
        }
    }

    public String checkUser(String email, String password) {
        Cursor cursor = miDB.getDataByEmail(email);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        int colID = cursor.getColumnIndex("PASSWORD");
        String dbPassword = cursor.getString(colID);
        int roleID = cursor.getColumnIndex("ROLE");
        String role = cursor.getString(roleID);
        if (password.equals(dbPassword))
            return role;
        else
            return null;
    }
}