package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;

public class DetalleOrden extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    private NonScrollListView listView;
    private RetroDientesVer retroAdapter;


    TextView paciente, entrega, mordida, antagonista, total;
    Double precio = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_orden);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        listView = findViewById(R.id.lv);
        paciente = findViewById(R.id.paciente);
        entrega = findViewById(R.id.entrega);
        mordida = findViewById(R.id.mordida);
        antagonista = findViewById(R.id.antagonista);
        total = findViewById(R.id.total);


        paciente.setText(getIntent().getStringExtra("paciente"));
        entrega.setText(getIntent().getStringExtra("tipo_entrega"));
        mordida.setText(getIntent().getStringExtra("registro_mordida"));
        antagonista.setText(getIntent().getStringExtra("antagonista"));


        if(getCarritosDatos().size() == 0){

        } else {

            writeListView(getCarritosDatos());

            total.setText(" $ "+precio);

        }






    }



    private void writeListView(ArrayList<Dientes> response){


        retroAdapter = new RetroDientesVer(this, response);
        listView.setAdapter(retroAdapter);


    }

    public ArrayList<Dientes> getCarritosDatos(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ArrayList<Dientes> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, orden_id, tipos_indicaciones_id, dientes, materiales_id, materiales_nombre, disenos_id, disenos_nombre, color, prueba, observaciones, user_id, precio  from dientes", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String orden_id =  fila.getString(1);
            String tipos_indicaicones_id = fila.getString(2);
            String dientes = fila.getString(3);
            String materiales_id = fila.getString(4);
            String materiales_nombre = fila.getString(5);
            String disenos_id = fila.getString(6);
            String disenos_nombre = fila.getString(7);
            String color = fila.getString(8);
            String prueba = fila.getString(9);
            String observaciones = fila.getString(10);
            String user_id = fila.getString(11);
            String precios = fila.getString(12);

            if(orden_id.equals(getIntent().getStringExtra("orden_id"))) {
                Dientes carritoadd = new Dientes(id, "", tipos_indicaicones_id, dientes, materiales_id, materiales_nombre, disenos_id, disenos_nombre, color, prueba, observaciones, user_id, precios);
                ranking.add(carritoadd);

                precio = precio + Double.valueOf(precios);
            }



        }

        return ranking;

    }

    public void atras(View v){
        onBackPressed();
    }

}
