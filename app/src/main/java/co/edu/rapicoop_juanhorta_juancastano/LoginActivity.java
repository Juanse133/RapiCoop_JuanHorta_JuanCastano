package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    LoginDatabaseHelper miDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        miDB = new LoginDatabaseHelper(this);
        miDB.initData();
    }

    public void onClickRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view){
        String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        if(checkUser(email, password)){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(HomeActivity.EXTRA_MESSAGE, email);
            startActivity(intent);
        } else {
            ((TextView) findViewById(R.id.errorMessage)).setText("Usuario o contraseña inválida");
        }
    }

    public boolean checkUser(String email, String password){
        Cursor cursor = miDB.getDataByEmail(email);
        if(cursor.getCount()==0)
            return false;
        cursor.moveToFirst();
        int colID = cursor.getColumnIndex("PASSWORD");
        String dbPassword = cursor.getString(colID);
        if(password.equals(dbPassword))
            return true;
        else
            return false;
    }
}