package co.edu.rapicoop_juanhorta_juancastano;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    CompraDatabaseHelper miCompra;
    ProductDatabaseHelper miProducto;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        miCompra = new CompraDatabaseHelper(this);
        miCompra.initData();

        miProducto = new ProductDatabaseHelper(this);
        miProducto.initData();

        user = getIntent().getStringExtra("user");

        String rol = getIntent().getStringExtra("rol");

        if(rol.equals("Vendor")){
            setText("Acumulado de ventas: ", getVendorValue());
        } else if(rol.equals("Client")){
            setText("Acumulado de compras: ", getClientValue());
        } else if(rol.equals("Dealer")){
            try {
                setText("Acumulado de entregas: ", getDealerValue());
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }
    }

    public void setText(String statName, String statValue){
        ((TextView) findViewById(R.id.statName)).setText(statName);
        ((TextView) findViewById(R.id.statValue)).setText(statValue);
    }

    public String getVendorValue(){

        int totalVentas = 0;
        double promedio = 0.0;

        Cursor compras = miCompra.getAllData();

        HashMap<Integer, Producto> productosVendidos = new HashMap();

        while(compras.moveToNext()){

            int colPRODUCTS = compras.getColumnIndex("PRODUCTS_ID");
            String productsID = compras.getString(colPRODUCTS);
            int colQUANTITIES = compras.getColumnIndex("QUANTITIES");
            String quantities = compras.getString(colQUANTITIES);

            int i = 0;

            for(String id : productsID.split(",")){

                Cursor producto = miProducto.getDataByID(Integer.parseInt(id));

                if (producto.getCount() == 0)
                    return null;

                producto.moveToFirst();

                int colID = producto.getColumnIndex("ID");
                int ID = Integer.parseInt(producto.getString(colID));
                int colNAME = producto.getColumnIndex("NAME");
                String NAME = producto.getString(colNAME);
                int colDESC = producto.getColumnIndex("DESCRIPTION");
                String DESC = producto.getString(colDESC);
                int colPRICE = producto.getColumnIndex("PRICE");
                Double PRICE = Double.parseDouble(producto.getString(colPRICE));
                int colEMAIL = producto.getColumnIndex("EMAIL");
                String EMAIL = producto.getString(colEMAIL);
                int colTAGS = producto.getColumnIndex("TAGS");
                String TAGS = producto.getString(colTAGS);
                int QUANTITY = Integer.parseInt(quantities.split(",")[i++]);

                Producto product = new Producto(ID, NAME, DESC, PRICE, QUANTITY, EMAIL, TAGS);

                if(!EMAIL.equals(user))
                    continue;

                if(productosVendidos.containsKey(ID)){
                    Producto p = productosVendidos.get(ID);
                    product.setQUANTITY(p.getQUANTITY() + QUANTITY);
                    productosVendidos.put(ID, product);
                } else {
                    productosVendidos.put(ID, product);
                }

                promedio += PRICE;
            }

            if(promedio == 0.0) {
                totalVentas++;
                promedio /= totalVentas;
            }
        }

        Producto masVendido = new Producto();
        int vecesVendido = 0;


        for(Integer id : productosVendidos.keySet()){
            Producto p = productosVendidos.get(id);
            if(p.getQUANTITY() > vecesVendido)
                masVendido = p;
        }

        return "Promedio de ventas: $" + promedio + "\n" +
                "Producto más vendido: " + masVendido.getNAME() + "\n" +
                masVendido.getQUANTITY() + " unidades vendidas";
    }

    public String getClientValue(){

        Double precioPromedio = 0.0;

        Cursor compras = miCompra.getDataByClient(user);

        while(compras.moveToNext()){
            int colPRICE = compras.getColumnIndex("PRICE");
            precioPromedio += Double.parseDouble(compras.getString(colPRICE)) / compras.getCount();
        }


        return "Total de compras: " + compras.getCount() + "\n" +
                "Precio promedio de compra: $" + precioPromedio;
    }

    public String getDealerValue() throws ParseException {

        Date fechaHoy = getDate(((new Date()).toString()));

        Cursor entregas = miCompra.getDataDelivered(user);

        Date[] fechas = new Date[entregas. getCount()];

        int hoy = 0;
        int semana = 0;
        int mes = 0;

        int i = 0;
        while(entregas.moveToNext()){
            int colDate = entregas.getColumnIndex("DATE");
            String date = entregas.getString(colDate);
            fechas[i] = getDate(date);

            System.out.println(i + " " + fechas[i]);

            if(fechas[i].equals(fechaHoy)){
                hoy++;
                semana++;
                mes++;
            } else {
                if(isDateInCurrentWeek(fechas[i]))
                    semana++;
                else if(isDateInCurrentMonth(fechas[i]))
                    mes++;
            }

            i++;
        }

        return "Total de entregas: " + entregas.getCount() + "\n"
                + "Hoy: " + hoy + "\n"
                + "Esta semana: " + semana + "\n"
                + "Este mes: " + mes;
    }

    public Date getDate(String date) throws ParseException {

        String dia = date.split(" ")[2];
        String mes = date.split(" ")[1];
        String año = date.split(" ")[5];

        return new SimpleDateFormat("dd/MMM/yyyy").parse(dia + "/" + mes + "/" + año);
    }

    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    public static boolean isDateInCurrentMonth(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int month = currentCalendar.get(Calendar.MONTH);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetMonth = targetCalendar.get(Calendar.MONTH);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return month == targetMonth && year == targetYear;
    }
}