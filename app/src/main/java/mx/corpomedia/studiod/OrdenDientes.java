package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mx.corpomedia.studiod.model.Audios;
import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.model.DientesModelos;
import mx.corpomedia.studiod.model.Fotos;
import mx.corpomedia.studiod.model.IndicacionesModel;
import mx.corpomedia.studiod.retrofit.indicacionesInterface;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OrdenDientes extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    private NonScrollListView listView;
    private RetroDientes retroAdapter;

    int serverResponseCode = 1;


    TextView paciente, entrega, mordida, antagonista, total;
    Double precio = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_dientes);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        listView = findViewById(R.id.lv);
        paciente = findViewById(R.id.paciente);
        entrega = findViewById(R.id.entrega);
        mordida = findViewById(R.id.mordida);
        antagonista = findViewById(R.id.antagonista);
        total = findViewById(R.id.total);

        paciente.setText(sharedPreferences.getString("paciente", ""));
        entrega.setText(sharedPreferences.getString("tipo_entrega", ""));
        mordida.setText(sharedPreferences.getString("registro_mordida", ""));
        antagonista.setText(sharedPreferences.getString("antagonista", ""));



        writeListView(getCarritosDatos());

        total.setText(" $ "+precio);



    }



    private void writeListView(ArrayList<Dientes> response){


        retroAdapter = new RetroDientes(this, response);
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

            if(orden_id.equals("0")) {
                Dientes carritoadd = new Dientes(id, "", tipos_indicaicones_id, dientes, materiales_id, materiales_nombre, disenos_id, disenos_nombre, color, prueba, observaciones, user_id, precios);
                ranking.add(carritoadd);

                precio = precio + Double.valueOf(precios);
            }



        }

        return ranking;

    }

    public ArrayList<Fotos> getCarritosDatos2(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ArrayList<Fotos> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, dientes_id, dientes, nombre, archivo, estado, fecha from fotos", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String dientes_id =  fila.getString(1);
            String dientes =  fila.getString(2);
            String nombre = fila.getString(3);
            String archivo = fila.getString(4);
            String estado = fila.getString(5);
            String fecha = fila.getString(6);

            if(estado.equals("0")) {
                Fotos carritoadd = new Fotos(id, dientes_id, dientes, nombre, archivo, estado, fecha);
                ranking.add(carritoadd);
            }




        }


        return ranking;

    }

    public ArrayList<Audios> getCarritosDatos3(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ArrayList<Audios> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, dientes_id, dientes, nombre, archivo, estado, fecha from audios", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String dientes_id =  fila.getString(1);
            String dientes =  fila.getString(2);
            String nombre = fila.getString(3);
            String archivo = fila.getString(4);
            String estado = fila.getString(5);
            String fecha = fila.getString(6);

            if(estado.equals("0")) {
                Audios carritoadd = new Audios(id, dientes_id, dientes, nombre, archivo, estado, fecha);
                ranking.add(carritoadd);
            }




        }


        return ranking;

    }

    public void atras(View v){
        onBackPressed();
    }
    public void enviar(View v){



        ArrayList<Dientes> data = new ArrayList<Dientes>();
        data = getCarritosDatos();
        Gson gson = new Gson();
        final String newDataArray = gson.toJson(data);


        ArrayList<Fotos> data2 = new ArrayList<Fotos>();
        data2 = getCarritosDatos2();
        Gson gson2 = new Gson();
        final String newDataArray2 = gson2.toJson(data2);

        ArrayList<Audios> data3 = new ArrayList<Audios>();
        data3 = getCarritosDatos3();
        Gson gson3 = new Gson();
        final String newDataArray3 = gson3.toJson(data3);

        Log.d("America ",newDataArray3);
        final String tag = "registerCompra";

        String server_url = "https://www.studiodlab.com.mx/admin/app/index.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String orden = jsonObject.getString("id");
                            String mensaje = jsonObject.getString("error_msg");

                            if(success.equals("1")){

                                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(OrdenDientes.this,"studiod",null,1);
                                SQLiteDatabase db=conn.getWritableDatabase();

                                ContentValues actualizardatos = new ContentValues();
                                actualizardatos.put(Utilidades.CAMPO_ORDEN_ID, orden);

                                db.update(Utilidades.TABLE_DIENTES, actualizardatos, Utilidades.CAMPO_ORDEN_ID + "=0 AND "+Utilidades.CAMPO_USER_ID+"="+sharedPreferences.getString("user_id", "") , null);


                                ContentValues actualizardatos2 = new ContentValues();
                                actualizardatos2.put(Utilidades.CAMPO_ESTADO, "1");

                                db.update(Utilidades.TABLE_FOTOS, actualizardatos2, Utilidades.CAMPO_ESTADO + "=0", null);


                                ContentValues actualizardatos3 = new ContentValues();
                                actualizardatos3.put(Utilidades.CAMPO_ESTADO, "1");

                                db.update(Utilidades.TABLE_AUDIOS, actualizardatos3, Utilidades.CAMPO_ESTADO + "=0", null);

                                DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
                                String date = df.format(Calendar.getInstance().getTime());

                                ContentValues values = new ContentValues();
                                values.put(Utilidades.CAMPO_ORDEN_ID,orden);
                                values.put(Utilidades.CAMPO_USUARIOS_ID,sharedPreferences.getString("user_id", ""));
                                values.put(Utilidades.CAMPO_PACIENTE,sharedPreferences.getString("paciente", ""));
                                values.put(Utilidades.CAMPO_TIPO_ENTREGA,sharedPreferences.getString("tipo_entrega", ""));
                                values.put(Utilidades.CAMPO_REGISTRO_MORDIDA,sharedPreferences.getString("registro_mordida", ""));
                                values.put(Utilidades.CAMPO_ANTAGONISTA,sharedPreferences.getString("antagonista", ""));
                                values.put(Utilidades.CAMPO_TOTAL,precio);
                                values.put(Utilidades.CAMPO_STATUS,"Enviada");
                                values.put(Utilidades.CAMPO_FECHA,date);

                                Long idResultante = db.insert(Utilidades.TABLE_ORDENES,Utilidades.CAMPO_ID,values);

                                Intent intent =new Intent(OrdenDientes.this, menu.class);
                                startActivity(intent);
                                Toast.makeText(OrdenDientes.this, mensaje, Toast.LENGTH_SHORT).show();
                            }
                            if(success.equals("0")){
                                Toast.makeText(OrdenDientes.this,mensaje, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tag", tag);
                params.put("usuarios_id", sharedPreferences.getString("user_id", ""));
                params.put("paciente", sharedPreferences.getString("paciente", ""));
                params.put("tipo_entrega", sharedPreferences.getString("tipo_entrega", ""));
                params.put("arrayCarrito", newDataArray);
                params.put("arrayFotos", newDataArray2);
                params.put("arrayAudios", newDataArray3);
                params.put("registro_mordida",sharedPreferences.getString("registro_mordida", ""));
                params.put("antagonista",sharedPreferences.getString("antagonista", ""));
                params.put("total", String.valueOf(precio));
                params.put("status","Enviada");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void cancelar(View v){


        AlertDialog.Builder builder = new AlertDialog.Builder(OrdenDientes.this);
        builder.setMessage("¿Confirma qué desea cancelar el pedido?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent =new Intent(OrdenDientes.this, menu.class);
                        startActivity(intent);
                                     }
                                     })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                 dialog.dismiss();
                 }
                 });

        AlertDialog alert = builder.create();
        alert.show();

    }



}
