package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class PedidoActualActivity extends AppCompatActivity {

    private OdometerService odometer;
    private boolean bound = false;
    CompraDatabaseHelper miCompra;

    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder){
            OdometerService.OdometerBinder odometerBinder =
                    (OdometerService.OdometerBinder) binder;
            odometer = odometerBinder.getOdometer();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName){
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_actual);
        miCompra = new CompraDatabaseHelper(this);
        miCompra.initData();
        displayDistance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(bound){
            unbindService(connection);
            bound = false;
        }
    }

    private void displayDistance(){
        final TextView distanceView = (TextView) findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if(bound && odometer != null){
                    distance = odometer.getDistance();
                }
                String distanceStr = String.format(Locale.getDefault(), "%1$,.2f metros", distance);

                distanceView.setText(distanceStr);
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void entregarPedido(View view){

        Cursor cursorCompra = miCompra.getDataOnGoing(getIntent().getStringExtra("email"));

        cursorCompra.moveToFirst();

        int colID = cursorCompra.getColumnIndex("ID");
        String ID = cursorCompra.getString(colID);

        boolean resultado = miCompra.updateData(new Compra(Integer.parseInt(ID),"","","","Entregado", "", "", getIntent().getStringExtra("email"), 0.0));

        if (resultado) {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Pedido entregado correctamente");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.green));

            Intent intent = new Intent(this, DelayedMessageService.class);
            intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, "Pedido Entregado");
            startService(intent);

        } else {
            ((TextView) findViewById(R.id.disclaimerMessage)).setText("Hubo un error, ponte en contacto con el administrador.");
            ((TextView) findViewById(R.id.disclaimerMessage)).setTextColor(getResources().getColor(R.color.red));
        }

    }
}