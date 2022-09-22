package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    LoginDatabaseHelper miDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        miDB = new LoginDatabaseHelper(this);
        miDB.initData();
    }

    public void onClickRegister(View view){
        String fullname = ((EditText) findViewById(R.id.Fullname)).getText().toString();
        String username = ((EditText) findViewById(R.id.Username)).getText().toString();
        String email = ((EditText) findViewById(R.id.Email)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        String confPassword = ((EditText) findViewById(R.id.ConfirmPassword)).getText().toString();
        String gender = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.RadioGroup)).getCheckedRadioButtonId())).getText().toString();

        if(password.equals(confPassword)){

            boolean resultado = miDB.insertData(new Usuario(0,fullname,username,email,password,gender));

            if (resultado) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra(HomeActivity.EXTRA_MESSAGE, username);
                startActivity(intent);
            } else {
                ((TextView) findViewById(R.id.errorMessage)).setText("Registro inválido");
            }

        } else {
            ((TextView) findViewById(R.id.errorMessage)).setText("Las contraseñas no coinciden");
        }
    }
}