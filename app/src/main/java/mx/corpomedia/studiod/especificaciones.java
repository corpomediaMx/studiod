package mx.corpomedia.studiod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.corpomedia.studiod.model.Audios;
import mx.corpomedia.studiod.model.Dientes;
import mx.corpomedia.studiod.model.DisenosModel;
import mx.corpomedia.studiod.model.Fotos;
import mx.corpomedia.studiod.model.IndicacionesModel;
import mx.corpomedia.studiod.model.MaterialesModel;
import mx.corpomedia.studiod.retrofit.DisenosInterface;
import mx.corpomedia.studiod.retrofit.MaterialesInterface;
import mx.corpomedia.studiod.sqlite.ConexionSQLiteHelper;
import mx.corpomedia.studiod.sqlite.Utilidades;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class especificaciones extends AppCompatActivity {


    private ArrayList<MaterialesModel> materialesModelArrayList;
    private ArrayList<DisenosModel> disenosModelArrayList;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private ArrayList<String> playerNames2 = new ArrayList<String>();
    private Spinner spinner, spinner2;
    private MediaRecorder recorder;
    private String archivosalida = null;
    private ImageView ilustracion;

    SharedPreferences sharedPreferences;
    String myPreferences= "MyPrefs";

    private NonScrollListView listView, listView2;
    TextView dientes ,nombre_tipo, color, observaciones;
    ImageView imagen_tipo;
    String materiales_id , disenos_id , materiales_nombre, disenos_nombre;
    CheckBox prueba;
    String mat_id ;
    String dise_id ;
    String col;
    String pru;
    String obs;
    Button grabar;
    Double precio;
    private RetroAudios retroAdapter;
    private RetroFotos retroFotos;


    private MagicalPermissions  magicalPermissions;
    private MagicalCamera magicalCamera;
    private static final int RESIZE_PHOTO_PIXELS_PERCENTAJE = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especificaciones);
        getSupportActionBar().hide();

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        magicalPermissions = new MagicalPermissions(this, permissions);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //TODO location permissions are granted code here your feature
            }
        };

        magicalPermissions.askPermissions(runnable);

        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAJE,magicalPermissions);



        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        imagen_tipo = findViewById(R.id.imagen);
        nombre_tipo = findViewById(R.id.nombre);
        color = findViewById(R.id.color);
        observaciones = findViewById(R.id.observaciones);
        prueba = findViewById(R.id.prueba);
        grabar = findViewById(R.id.record);
        listView = findViewById(R.id.lv);
        listView2 = findViewById(R.id.lv2);
        ilustracion = findViewById(R.id.ilustracion);



        String tipos_indicaciones_id = sharedPreferences.getString("tipos_indicaciones_id","");
        String nombre_t = sharedPreferences.getString("nombre_tipos","");
        String imagen_t = sharedPreferences.getString("imagen_tipos","");

        if (tipos_indicaciones_id.equals("6")){
            ilustracion.setImageResource(R.drawable.guarda);
        }
        if (tipos_indicaciones_id.equals("7")){
            ilustracion.setImageResource(R.drawable.guarda);
        }
        if (tipos_indicaciones_id.equals("8")){
            ilustracion.setImageResource(R.drawable.guarda);
        }


        Picasso.with(this)
                .load("https://www.studiodlab.com.mx/admin/app/img/" + imagen_t)
                .placeholder(R.drawable.load)
                .into(imagen_tipo);

        nombre_tipo.setText(nombre_t);

        dientes = findViewById(R.id.dientes);
        String diente = getIntent().getStringExtra("dientes");
        dientes.setText(diente);


        spinner = findViewById(R.id.spinner2);
        spinner2 = findViewById(R.id.spinner3);

        fetchJSON(tipos_indicaciones_id);
        String dientes_id = getIntent().getStringExtra("dientes_id");
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(especificaciones.this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT materiales_id, materiales_nombre, disenos_id, disenos_nombre, color, prueba, observaciones FROM dientes WHERE id ="+dientes_id, null);

        if (c != null) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                mat_id = c.getString(c.getColumnIndex("materiales_nombre"));
                dise_id = c.getString(c.getColumnIndex("disenos_nombre"));
                col = c.getString(c.getColumnIndex("color"));
                pru = c.getString(c.getColumnIndex("prueba"));
                obs = c.getString(c.getColumnIndex("observaciones"));
            } while (c.moveToNext());
        }

        //Cerramos el cursor y la conexion con la base de datos
        c.close();
        db.close();


        color.setText(col);
        if(pru.equals("Si")){
            prueba.setChecked(true);
        } else {
            prueba.setChecked(false);
        }
        observaciones.setText(obs);



        if(getCarritosDatos(dientes_id).size() == 0){

        } else {



            retroAdapter = new RetroAudios(especificaciones.this, getCarritosDatos(dientes_id ));
            listView.setAdapter(retroAdapter);
        }
        if(getCarritosDatos2(dientes_id).size() == 0){

        } else {



            retroFotos = new RetroFotos(especificaciones.this, getCarritosDatos2(dientes_id ));
            listView2.setAdapter(retroFotos);
        }

    }



    public ArrayList<Audios> getCarritosDatos(String iddiente){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ArrayList<Audios> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, dientes_id, nombre, estado, fecha from audios", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String dientes_id =  fila.getString(1);
            String nombre = fila.getString(2);
            String estado = fila.getString(3);
            String fecha = fila.getString(4);

            if(dientes_id.equals(iddiente)) {

                if(estado.equals("0")) {
                    Audios carritoadd = new Audios(id, dientes_id, "", nombre, "", estado, fecha);
                    ranking.add(carritoadd);
                }
            }



        }


        return ranking;

    }

    public ArrayList<Fotos> getCarritosDatos2(String iddiente){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ArrayList<Fotos> ranking = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, dientes_id, nombre, estado, fecha from fotos", null);

        while(fila.moveToNext()){

            String id =  fila.getString(0);
            String dientes_id =  fila.getString(1);
            String nombre = fila.getString(2);
            String estado = fila.getString(3);
            String fecha = fila.getString(4);

            if(dientes_id.equals(iddiente)) {

                if(estado.equals("0")) {
                    Fotos carritoadd = new Fotos(id, dientes_id, "", nombre, null, estado,fecha);
                    ranking.add(carritoadd);
                }
            }




        }


        return ranking;

    }


    public void atras(View v){
        onBackPressed();
    }
    public void next(View v){
        startActivity(new Intent(especificaciones.this, ordenCheck.class));
    }

    private void fetchJSON(String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MaterialesInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MaterialesInterface api = retrofit.create(MaterialesInterface.class);

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

                materialesModelArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    MaterialesModel spinnerModel = new MaterialesModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    spinnerModel.setId(dataobj.getString("id"));
                    spinnerModel.setNombre(dataobj.getString("nombre"));
                    spinnerModel.setPrecio(dataobj.getDouble("precio"));

                    materialesModelArrayList.add(spinnerModel);

                }

                for (int i = 0; i < materialesModelArrayList.size(); i++){
                    playerNames.add(materialesModelArrayList.get(i).getNombre().toString());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(especificaciones.this, android.R.layout.simple_spinner_item, playerNames);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);
                String compareValue = mat_id;
                if (compareValue != null) {

                    spinner.setSelection(spinnerArrayAdapter.getPosition(compareValue));

                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                         Log.d("Cambio id ",materialesModelArrayList.get(position).getId());

                         materiales_id = materialesModelArrayList.get(position).getId();
                        materiales_nombre = materialesModelArrayList.get(position).getNombre();

                        precio = materialesModelArrayList.get(position).getPrecio();

                        fetchJSON2(materialesModelArrayList.get(position).getId());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });





            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void fetchJSON2(String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DisenosInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        DisenosInterface api = retrofit.create(DisenosInterface.class);

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
                        spinJSON2(jsonresponse);

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

    private void spinJSON2(String response){

        playerNames2.clear();


        try {

            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                disenosModelArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    DisenosModel spinnerModel = new DisenosModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    spinnerModel.setId(dataobj.getString("id"));
                    spinnerModel.setNombre(dataobj.getString("nombre"));

                    disenosModelArrayList.add(spinnerModel);

                }

                for (int i = 0; i < disenosModelArrayList.size(); i++){
                    playerNames2.add(disenosModelArrayList.get(i).getNombre().toString());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(especificaciones.this, android.R.layout.simple_spinner_item, playerNames2);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner2.setAdapter(spinnerArrayAdapter);
                String compareValue = dise_id;
                if (compareValue != null) {

                    spinner2.setSelection(spinnerArrayAdapter.getPosition(compareValue));

                }
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        Log.d("Cambio id ",disenosModelArrayList.get(position).getId());

                        disenos_id = disenosModelArrayList.get(position).getId();
                        disenos_nombre =  disenosModelArrayList.get(position).getNombre();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void guardar(View v){

        String prueb;
        if (prueba.isChecked() == true) {
            prueb = "Si";
        } else {
            prueb = "No";
        }


        String dientes_id = getIntent().getStringExtra("dientes_id");

        Log.e("Este es el campo ",dientes_id);
        Log.e("material id ",materiales_id);
        Log.e("diseno id ",disenos_id);
        Log.e("color ",color.getText().toString());
        Log.e("observaciones ",observaciones.getText().toString());
        Log.e("prueba ",prueb);


        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"studiod",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();


        ContentValues actualizardatos = new ContentValues();
        actualizardatos.put(Utilidades.CAMPO_ORDEN_ID,"0");
        actualizardatos.put(Utilidades.CAMPO_MATERIALES_ID,materiales_id);
        actualizardatos.put(Utilidades.CAMPO_MATERIALES_NOMBRE,materiales_nombre);
        actualizardatos.put(Utilidades.CAMPO_DISENOS_ID,disenos_id);
        actualizardatos.put(Utilidades.CAMPO_DISENOS_NOMBRE,disenos_nombre);
        actualizardatos.put(Utilidades.CAMPO_COLOR,color.getText().toString());
        actualizardatos.put(Utilidades.CAMPO_PRUEBA,prueb);
        actualizardatos.put(Utilidades.CAMPO_OBSERVACIONES,observaciones.getText().toString());
        actualizardatos.put(Utilidades.CAMPO_USER_ID,sharedPreferences.getString("user_id",""));
        actualizardatos.put(Utilidades.CAMPO_PRECIO,precio);

        db.update(Utilidades.TABLE_DIENTES, actualizardatos, Utilidades.CAMPO_ID+"="+dientes_id, null);


        Intent i = new Intent(especificaciones.this, OrdenDientes.class);
        startActivity(i);
    }

    public void Recorder(View v) throws IOException {

        if(recorder == null){

            String nombre = String.valueOf(System.currentTimeMillis()/1000);
            archivosalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio_"+nombre+".mp3";
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setOutputFile(archivosalida);

            try{

                recorder.prepare();
                recorder.start();




            } catch (IOException e){

            }


            grabar.setText("Grabando...");

        } else if(recorder != null) {
                recorder.stop();
                recorder.release();

                recorder = null;

            String diente = getIntent().getStringExtra("dientes");

            String dientes_id = getIntent().getStringExtra("dientes_id");

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(especificaciones.this,"studiod",null,1);
            SQLiteDatabase db=conn.getWritableDatabase();
            String encoded = Base64.encodeToString(getBytes2(archivosalida), 0);

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_DIENTES_ID,dientes_id);
            values.put(Utilidades.CAMPO_DIENTES,diente);
            values.put(Utilidades.CAMPO_NOMBRE,archivosalida);
            values.put(Utilidades.CAMPO_ARCHIVO,encoded);
            values.put(Utilidades.CAMPO_ESTADO,"0");
            values.put(Utilidades.CAMPO_FECHA,"123");


            Long idResultante = db.insert(Utilidades.TABLE_AUDIOS,Utilidades.CAMPO_ID,values);

            retroAdapter = new RetroAudios(especificaciones.this, getCarritosDatos(dientes_id));
            listView.setAdapter(retroAdapter);

            grabar.setText("Nota de voz");

            Toast.makeText(this,"Grabación finalizada",Toast.LENGTH_SHORT).show();


        }

    }

    public void camara(View v) {

        magicalCamera.takePhoto();

    }

    public void fotos(View v) {
        magicalCamera.selectedPicture("Selleciones una foto");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode,resultCode,data);

        String namefoto = ""+System.currentTimeMillis()/1000;

        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"img_"+namefoto,"studiod", MagicalCamera.JPEG, true);

        if(path != null){
            String diente = getIntent().getStringExtra("dientes");
            String dientes_id = getIntent().getStringExtra("dientes_id");

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(especificaciones.this,"studiod",null,1);
            SQLiteDatabase db=conn.getWritableDatabase();


            String base64_imagen = Base64.encodeToString(getBytes(magicalCamera.getPhoto()), Base64.DEFAULT);

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_DIENTES_ID,dientes_id);
            values.put(Utilidades.CAMPO_DIENTES,diente);
            values.put(Utilidades.CAMPO_NOMBRE,path);
            values.put(Utilidades.CAMPO_ARCHIVO, base64_imagen);
            values.put(Utilidades.CAMPO_ESTADO,"0");
            values.put(Utilidades.CAMPO_FECHA,"123");


            Long idResultante = db.insert(Utilidades.TABLE_FOTOS,Utilidades.CAMPO_ID,values);

            retroFotos = new RetroFotos(especificaciones.this, getCarritosDatos2(dientes_id));
            listView2.setAdapter(retroFotos);


        }else{
            Toast.makeText(especificaciones.this, "No se guardo la foto", Toast.LENGTH_SHORT).show();
        }

        switch (requestCode){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }


    public static byte[] getBytes2(String fil) throws IOException {
        File file = new File(fil);
        return FileUtils.readFileToByteArray(file);
    }


}
