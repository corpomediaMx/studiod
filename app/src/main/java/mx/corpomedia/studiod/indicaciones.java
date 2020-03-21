package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

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
import mx.corpomedia.studiod.retrofit.indicacionesInterface;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class indicaciones extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    private ListView listView;
    private RetroAdapter retroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicaciones);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        listView = findViewById(R.id.lv);

        getJSONResponse();

    }

    private void getJSONResponse(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(indicacionesInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        indicacionesInterface api = retrofit.create(indicacionesInterface.class);

        Call<String> call = api.getString();

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

                final ArrayList<IndicacionesModel> modelListViewArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    IndicacionesModel modelListView = new IndicacionesModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    modelListView.setId(dataobj.getString("id"));
                    modelListView.setStrimagen(dataobj.getString("strimagen"));
                    modelListView.setNombre(dataobj.getString("nombre"));

                    modelListViewArrayList.add(modelListView);

                }

                retroAdapter = new RetroAdapter(this, modelListViewArrayList);
                listView.setAdapter(retroAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("indicaciones_id",""+modelListViewArrayList.get(position).getId());
                        editor.putString("nombre_indicaciones",""+modelListViewArrayList.get(position).getNombre());
                        editor.putString("imagen_indicaciones",""+modelListViewArrayList.get(position).getStrimagen());
                        editor.commit();
                        if(modelListViewArrayList.get(position).getId().equals("2")){


                            Intent i = new Intent(indicaciones.this, tipos.class);
                            i.putExtra("indicaciones_id",""+modelListViewArrayList.get(position).getId());
                            startActivity(i);




                        }  else {

                            Intent i = new Intent(indicaciones.this, seleccion.class);
                            i.putExtra("indicaciones_id",""+modelListViewArrayList.get(position).getId());
                            startActivity(i);

                        }

                    }

                });

            }else {
                Toast.makeText(indicaciones.this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void atras(View v){
        onBackPressed();
    }


}
