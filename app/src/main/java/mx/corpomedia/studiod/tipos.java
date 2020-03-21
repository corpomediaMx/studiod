package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.IndicacionesModel;
import mx.corpomedia.studiod.model.tiposIndicacionesModel;
import mx.corpomedia.studiod.retrofit.indicacionesInterface;
import mx.corpomedia.studiod.retrofit.tiposIndicacionesInterface;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class tipos extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    private ListView listView;
    private RetroAdapterTipos retroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        String indicaciones_id = getIntent().getStringExtra("indicaciones_id");
        Log.i("ESTOX ",indicaciones_id);

        listView = findViewById(R.id.lv);

        getJSONResponse(indicaciones_id);
    }
    private void getJSONResponse(String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(tiposIndicacionesInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        tiposIndicacionesInterface api = retrofit.create(tiposIndicacionesInterface.class);

        Call<String> call = api.getString(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeListView(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeListView(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                final ArrayList<tiposIndicacionesModel> modelListViewArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    tiposIndicacionesModel modelListView = new tiposIndicacionesModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    modelListView.setId(dataobj.getString("id"));
                    modelListView.setStrimagen(dataobj.getString("strimagen"));
                    modelListView.setNombre(dataobj.getString("nombre"));

                    modelListViewArrayList.add(modelListView);

                }

                retroAdapter = new RetroAdapterTipos(this, modelListViewArrayList);
                listView.setAdapter(retroAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tipos_indicaciones_id",""+modelListViewArrayList.get(position).getId());
                        editor.putString("nombre_tipos",""+modelListViewArrayList.get(position).getNombre());
                        editor.putString("imagen_tipos",""+modelListViewArrayList.get(position).getStrimagen());
                        editor.commit();

                        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(tipos.this,"studiod",null,1);
                        SQLiteDatabase db=conn.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(Utilidades.CAMPO_ORDEN_ID,"0");
                        values.put(Utilidades.CAMPO_TIPOS_INDICACIONES_ID,modelListViewArrayList.get(position).getId());
                        values.put(Utilidades.CAMPO_DIENTES,"Guarda");
                        values.put(Utilidades.CAMPO_MATERIALES_ID,"");
                        values.put(Utilidades.CAMPO_DISENOS_ID,"");
                        values.put(Utilidades.CAMPO_COLOR,"");
                        values.put(Utilidades.CAMPO_PRUEBA,"");
                        values.put(Utilidades.CAMPO_OBSERVACIONES,"");
                        values.put(Utilidades.CAMPO_USER_ID,sharedPreferences.getString("user_id", ""));
                        values.put(Utilidades.CAMPO_PRECIO,"0");



                        Long idResultante = db.insert(Utilidades.TABLE_DIENTES,Utilidades.CAMPO_ID,values);


                        Intent i = new Intent(tipos.this, OrdenDientes.class);
                        i.putExtra("tipos_indicaciones_id",""+modelListViewArrayList.get(position).getId());
                        startActivity(i);
                    }

                });

            }else {
                Toast.makeText(tipos.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void atras(View v){
        onBackPressed();
    }

    @Override
    protected void onResume() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(tipos.this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        db.delete( Utilidades.TABLE_DIENTES, Utilidades.CAMPO_ORDEN_ID +" = 0 AND "+Utilidades.CAMPO_USER_ID+" = "+sharedPreferences.getString("user_id", ""),  null);


        super.onResume();
    }

}
