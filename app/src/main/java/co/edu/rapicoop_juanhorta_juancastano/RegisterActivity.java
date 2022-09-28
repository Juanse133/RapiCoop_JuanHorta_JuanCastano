package co.edu.rapicoop_juanhorta_juancastano;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText selectdate;
    LoginDatabaseHelper miDB;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        selectdate = findViewById(R.id.birthday);
        selectdate.setOnClickListener(this);
        miDB = new LoginDatabaseHelper(this);
        miDB.initData();
    }

    @Override
    public void onClick(View v) {
        if (v == selectdate) {
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            //show dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickRegister(View view) {
        String fullname = ((EditText) findViewById(R.id.Fullname)).getText().toString();
        String username = ((EditText) findViewById(R.id.Username)).getText().toString();
        String email = ((EditText) findViewById(R.id.Email)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();
        String confPassword = ((EditText) findViewById(R.id.ConfirmPassword)).getText().toString();
        String gender = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.RadioGroup)).getCheckedRadioButtonId())).getText().toString();
        String role = ((Spinner) findViewById(R.id.role)).getSelectedItem().toString();
        String birthday = ((EditText) findViewById(R.id.birthday)).getText().toString();
        Date today = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        Date birthdayFormatted = new Date();
        try {
            birthdayFormatted = DateFor.parse(birthday);
        } catch (ParseException e) {
            System.out.println("Error en el formato de la fecha");
        }

        int diffInYears = (int) ((today.getTime() - birthdayFormatted.getTime())
                / (1000l * 60 * 60 * 24 * 365));
        System.out.println(diffInYears);


        if(diffInYears <18 && (role.equals("Vendedor de comidas")|| role.equals("Domiciliario"))){
            ((TextView) findViewById(R.id.errorMessage)).setText("Debes ser mayor de 18 años para registrarte como "+role);
        }else{
            if (password.equals(confPassword)) {

                boolean resultado = miDB.insertData(new Usuario(0, fullname, username, email, password, gender, role, birthday));

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

}