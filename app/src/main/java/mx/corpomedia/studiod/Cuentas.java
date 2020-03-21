package mx.corpomedia.studiod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.model.DisenosModel;
import mx.corpomedia.studiod.model.IndicacionesModel;
import mx.corpomedia.studiod.model.MaterialesModel;
import mx.corpomedia.studiod.model.OrdenesModelos;
import mx.corpomedia.studiod.retrofit.MaterialesInterface;
import mx.corpomedia.studiod.retrofit.OrdenesInterface;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Cuentas extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String myPreferences = "MyPrefs";

    private ArrayList<OrdenesModelos> ordenesModelArrayList;

    private NonScrollListView listView;
    private RetroOrdenesVer retroAdapter;

    Double pagar = 0.0;
    Double pagadas = 0.0;

    TextView paciente, entrega, mordida, antagonista, porpagar, yapagadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        listView = findViewById(R.id.lv);
        porpagar = findViewById(R.id.pagar);
        yapagadas = findViewById(R.id.pagada);



        fetchJSON(sharedPreferences.getString("user_id", ""));




    }


    private void fetchJSON(String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OrdenesInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

       OrdenesInterface api = retrofit.create(OrdenesInterface.class);

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
                        spinJSON(jsonresponse);

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

    private void spinJSON(String response){

        try {

            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                ordenesModelArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {


                    OrdenesModelos modelListView = new OrdenesModelos();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    modelListView.setId(dataobj.getString("id"));
                    modelListView.setOrden_id(dataobj.getString("id"));
                    modelListView.setUsuarios_id(dataobj.getString("usuarios_id"));
                    modelListView.setPaciente(dataobj.getString("paciente"));
                    modelListView.setTipo_entrega(dataobj.getString("tipo_entrega"));
                    modelListView.setRegistro_mordida(dataobj.getString("registro_mordida"));
                    modelListView.setAntagonista(dataobj.getString("antagonista"));
                    modelListView.setTotal(String.valueOf(dataobj.getDouble("total")));
                    modelListView.setStatus(dataobj.getString("status"));
                    modelListView.setFecha(dataobj.getString("created_at"));


                    if(dataobj.getString("status").equals("Pagado")) {
                        pagadas = pagadas + dataobj.getDouble("total");
                    } else {
                        pagar = pagar + dataobj.getDouble("total");
                    }


                    ordenesModelArrayList.add(modelListView);

                }

                if(ordenesModelArrayList.size() == 0) {
                } else {
                    porpagar.setText("$ " + pagar);
                    yapagadas.setText("$ " + pagadas);


                    retroAdapter = new RetroOrdenesVer(this, ordenesModelArrayList);
                    listView.setAdapter(retroAdapter);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void atras(View v) {
        onBackPressed();
    }


}